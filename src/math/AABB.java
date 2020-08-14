package math;

import java.awt.*;

public class AABB {
    Point min;
    Point max;

    public AABB() {
        min = new Point();
        max = new Point();
    }

    public AABB (int xmin, int ymin, int xmax, int ymax) {
        min = new Point();
        max = new Point();
        min.x = xmin;
        min.y = ymin;
        max.x = xmax;
        max.y = ymax;
    }

    public static boolean AABBvsAABB(AABB first, AABB second) {
        // Выходим без пересечения, потому что найдена разделяющая ось
        if (first.max.x < second.min.x || first.min.x > second.max.x) return false;
        if (first.max.y < second.min.y || first.min.y > second.max.y) return false;
        // Разделяющая ось не найдена, поэтому существует по крайней мере одна пересекающая ось
        return true;
    }

    public static boolean AABBvsAABB2(AABB first, AABB second) {
        if ( (first.min.x == second.max.x || first.min.x - 1 == second.max.x) &&
                ( ( (first.min.y >= second.min.y) && (first.min.y <= second.max.y) ) ||
                        ( (first.max.y >= second.min.y) && (first.max.y <= second.max.y) ) ) ) {
            CollisionMessage.setMessage("left");
            return true;
        }
        if ( (first.max.x == second.min.x || first.max.x + 1 == second.min.x) &&
                ( ( (first.min.y >= second.min.y) && (first.min.y <= second.max.y) ) ||
                        ( (first.max.y >= second.min.y) && (first.max.y <= second.max.y) ) ) ) {
            CollisionMessage.setMessage("right");
            return true;
        }
        if ( (first.min.y == second.max.y || first.min.y + 1 == second.max.y) &&
                ( ( (first.min.x >= second.min.x) && (first.min.x <= second.max.x) ) ||
                        ( (first.max.x >= second.min.x) && (first.max.x <= second.max.x) ) ) ) {
            CollisionMessage.setMessage("up");
            return true;
        }
        if ( (first.max.y == second.min.y || first.max.y - 1 == second.min.y) &&
                ( ( (first.min.x >= second.min.x) && (first.min.x <= second.max.x) ) ||
                        ( (first.max.x >= second.min.x) && (first.max.x <= second.max.x) ) ) ) {
            CollisionMessage.setMessage("down");
            return true;
        }
        return false;
    }

    /*public static String getFirstAABBPosition(AABB first, AABB second) {
        if (first.max.x < second.min.x)
    }*/

    public void update(int xmin, int ymin, int xmax, int ymax) {
        min.x = xmin;
        min.y = ymin;
        max.x = xmax;
        max.y = ymax;
    }

    public Point getMin() { return min; }

    public Point getMax() { return max; }
}