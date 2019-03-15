package test.game.rpg.core.map;

import java.io.Serializable;

/**
 * Created by Maks on 10.03.2019.
 * Wraps coordinates
 */
public class Vector implements Serializable {

    protected int x;
    protected int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Vector with(int x, int y){
        return new Vector(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        if (x != vector.x) return false;
        return y == vector.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
