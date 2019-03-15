package test.game.rpg.core.combat.weapon;

import test.game.rpg.core.combat.Stats;
import test.game.rpg.core.map.TransformComponent;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.GameMap;
import test.game.rpg.core.map.Vector;

/**
 * Created by Maks on 12.03.2019.
 */
public enum WeaponType {

    SWORD, AXE, SPEAR, BOW, DAGGER;

    public boolean isAttackReachesTarget(GameObject attacker, GameObject target, GameMap gameMap){
        TransformComponent attackertransform = attacker.getComponent(TransformComponent.class).get();
        TransformComponent targetTransform = target.getComponent(TransformComponent.class).get();

        if (this != WeaponType.BOW){
            if (this == WeaponType.SPEAR){
                return isSpearReachesTarget(attackertransform.getPosition(), targetTransform.getPosition(), gameMap);
            }

            return isWeaponReachesTarget(attackertransform.getPosition(), targetTransform.getPosition(), 1);

        }

        return hasLineOfSight(attackertransform.getPosition(), targetTransform.getPosition(), gameMap);
    }

    public int takeProperModifier(Stats stats){
        if (this == BOW){
            return stats.getDexterity();
        }

        return stats.getStrenght();
    }

    private boolean isWeaponReachesTarget(Vector attackerPosition, Vector targetPosition, int range) {
        return Math.abs(attackerPosition.getX() - targetPosition.getX()) <= range
                && Math.abs(attackerPosition.getY() - targetPosition.getY()) <= range;
    }

    private boolean isSpearReachesTarget(Vector attackerPosition, Vector targetPosition, GameMap gameMap) {
        boolean inRange = isWeaponReachesTarget(attackerPosition, targetPosition, 2);
        if (inRange){
            return isWeaponReachesTarget(attackerPosition, targetPosition, 1)
                    ? true
                    : hasLineOfSight(attackerPosition, targetPosition, gameMap);
        }

        return false;
    }

    private boolean hasLineOfSight(Vector attackerPosition, Vector targetPosition, GameMap gameMap) {
        //TODO implement proper calculation of line of site
        return true;
    }

}
