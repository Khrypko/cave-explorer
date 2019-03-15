package test.game.rpg.core.combat;

import test.game.rpg.core.combat.armour.Armour;
import test.game.rpg.core.combat.command.AttackEvent;
import test.game.rpg.core.combat.command.DeathEvent;
import test.game.rpg.core.combat.weapon.Weapon;
import test.game.rpg.core.messaging.Message;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.component.GameSystem;
import test.game.rpg.core.map.GameMap;
import test.game.rpg.core.map.Vector;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Maks on 12.03.2019.
 */
public class CombatSystem implements GameSystem {

    private Game game;
    private List<AttackEvent> attackCommands = new LinkedList<>();

    @Override
    public void run() {
        attackCommands.forEach(this::handleAttack);
        attackCommands.clear();
    }

    private void handleAttack(AttackEvent attackCommand) {
        GameMap map = game.getWorld().getCurrentMap();

        GameObject attacker = attackCommand.getAttacker();
        if (map.isObjectOnMap(attacker)){
            List<GameObject> targets = defineAttackTargets(attackCommand.getAttackCoord(), map);
            performAttack(attacker, targets);
        }
    }

    private List<GameObject> defineAttackTargets(List<Vector> attackCoord, GameMap map) {
        return attackCoord.stream()
                .map(map::getObjectOn)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void performAttack(GameObject attacker, List<GameObject> targets) {
        targets.forEach(target -> attack(attacker, target));
    }

    private void attack(GameObject attacker, GameObject target) {
        attacker.getComponent(CombatComponent.class)
                .ifPresent(attackerCombatComp -> {
                    int damageAmount = calculateDamageAmount(attacker, target);
                    if (damageAmount > 0){
                        applyDamage(target, damageAmount);
                    }
                });
    }

    private void applyDamage(GameObject target, int damageAmount) {
        Optional<Health> targetHealth = target.getComponent(Health.class);
        if (targetHealth.isPresent()){
            Health health = targetHealth.get();
            health.setCurrentHealth(health.getCurrentHealth() - damageAmount);
            game.getMessenger().send(new Message(String.format("%s received %s damage", target.getId(), damageAmount)));

            if (health.getCurrentHealth() <= 0){
                killTarget(target);
            }

        }
    }

    private void killTarget(GameObject target) {
        GameMap map = game.getWorld().getCurrentMap();
        map.removeObjectFromMap(target);
        putLootOnMap(target, map);

        game.getMessenger().send(new DeathEvent(target));
        game.getMessenger().send(new Message("Enemy killed!"));
    }

    private void putLootOnMap(GameObject target, GameMap map) {
        //TODO: implement
    }

    private int calculateDamageAmount(GameObject attacker, GameObject target) {
        Weapon weapon = getWeapon(attacker);
        if (weapon == null) return 0;

        AttackParams attackParams = calculateAttackParams(attacker, target);
        if (attackParams.hit == 0) return 0;
        int armourClass = calculateArmourClass(target);

        if (attackParams.hit <= armourClass){
            game.getMessenger().send(new Message(String.format("%s missed! hit roll: %s, armor roll: %s", attacker.getId(), attackParams.hit, armourClass)));
            return 0;
        }

        return attackParams.damage;

    }

    private Weapon getWeapon(GameObject attacker) {
        CombatComponent attackerCombatComponent = attacker.getComponent(CombatComponent.class).get();
        return attackerCombatComponent.getWeapon();
    }

    private AttackParams calculateAttackParams(GameObject attacker, GameObject target) {
        CombatComponent attackerCombatComponent = attacker.getComponent(CombatComponent.class).get();
        Weapon weapon = attackerCombatComponent.getWeapon();

        if (!weapon.getType().isAttackReachesTarget(attacker, target, game.getWorld().getCurrentMap())){
            this.game.getMessenger().send(new Message("Enemy is out of the weapon reach"));
            return AttackParams.dummy();
        }

        Optional<Stats> stats = attacker.getComponent(Stats.class);
        if (!stats.isPresent()) return AttackParams.dummy();

        Optional<Treat> treat = attacker.getComponent(Treat.class);
        if (!treat.isPresent()) return AttackParams.dummy();

        Random random = new Random();
        int baseHit = random.nextInt(19) + 1;
        int statModifier = weapon.getType().takeProperModifier(stats.get());
        int hit = baseHit + statModifier + treat.get().getBaseAttackModifier();

        int damage = calculateDamage(weapon, baseHit);

        return new AttackParams(hit, damage);
    }

    private int calculateDamage(Weapon weapon, int baseHit) {
        Random random = new Random();
        int damage = 0;
        for (int i = 0; i < weapon.getDemageBone().getX(); i++){
            damage += random.nextInt(weapon.getDemageBone().getY()) + 1;
        }

        if (weapon.getCritOn().contains(baseHit)){
            damage = damage * weapon.getCritModifier();
        }

        return damage;
    }

    private int calculateArmourClass(GameObject target) {
        Optional<Treat> treat = target.getComponent(Treat.class);
        Optional<CombatComponent> combatComponent = target.getComponent(CombatComponent.class);

        if (!treat.isPresent() || !combatComponent.isPresent()){
            return Integer.MAX_VALUE;
        }

        int armourClass = 0;

        Armour armour = combatComponent.get().getArmour();
        if (armour != null){
            armourClass = armour.getDefenceBonus();
        }

        Random random = new Random();
        return  armourClass + (random.nextInt(19) + 1) + treat.get().getDefenceClass();
    }

    @Override
    public void initialize(Game game) {
        this.game = game;
        this.game.getMessenger().subscribe(AttackEvent.class, command -> attackCommands.add(command));
    }

    private static class AttackParams {
        int hit;
        int damage;

        AttackParams(int hit, int damage) {
            this.hit = hit;
            this.damage = damage;
        }

        static AttackParams dummy(){
            return new AttackParams(0, 0);
        }
    }
}
