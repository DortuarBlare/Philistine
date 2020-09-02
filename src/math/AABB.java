package math;

import java.awt.*;

public class AABB {
    Point min;
    Point max;

    public AABB() {
        min = new Point();
        max = new Point();
    }

    public AABB (int minX, int minY, int maxX, int maxY) {
        min = new Point();
        max = new Point();
        min.x = minX;
        min.y = minY;
        max.x = maxX;
        max.y = maxY;
    }

    public static boolean AABBvsAABB(AABB first, AABB second) {
        // Выходим без пересечения, потому что найдена разделяющая ось
        if (first.max.x < second.min.x || first.min.x > second.max.x) return false;
        if (first.max.y < second.min.y || first.min.y > second.max.y) return false;
        // Разделяющая ось не найдена, поэтому существует по крайней мере одна пересекающая ось
        return true;
    }

    public static boolean AABBvsAABB2(AABB first, AABB second) {
        if ( (first.min.x >= second.max.x - 5 && first.min.x <= second.max.x) &&
                ( ( (first.min.y >= second.min.y) && (first.min.y <= second.max.y) ) ||
                        ( (first.max.y >= second.min.y) && (first.max.y <= second.max.y) ) ||
                        ( (getCenter(first.min.y, first.max.y) >= second.min.y) && (getCenter(first.min.y, first.max.y) <= second.max.y) ) ) ) {
            CollisionMessage.setMessage("left");
            return true;
        }
        else if ( (first.max.x <= second.min.x + 5 && first.max.x >= second.min.x) &&
                ( ( (first.min.y >= second.min.y) && (first.min.y <= second.max.y) ) ||
                        ( (first.max.y >= second.min.y) && (first.max.y <= second.max.y) ) ||
                        ( (getCenter(first.min.y, first.max.y) >= second.min.y) && (getCenter(first.min.y, first.max.y) <= second.max.y) ) ) ) {
            CollisionMessage.setMessage("right");
            return true;
        }
        else if ( (first.min.y >= second.max.y - 5 && first.min.y <= second.max.y) &&
                ( ( (first.min.x >= second.min.x) && (first.min.x <= second.max.x) ) ||
                        ( (first.max.x >= second.min.x) && (first.max.x <= second.max.x) ) ||
                        ( (getCenter(first.min.x, first.max.x) >= second.min.x) && (getCenter(first.min.x, first.max.x) <= second.max.x) ) ) ) {
            CollisionMessage.setMessage("up");
            return true;
        }
        else if ( (first.max.y <= second.min.y + 5 && first.max.y >= second.min.y) &&
                ( ( (first.min.x >= second.min.x) && (first.min.x <= second.max.x) ) ||
                        ( (first.max.x >= second.min.x) && (first.max.x <= second.max.x) ) ||
                        ( (getCenter(first.min.x, first.max.x) >= second.min.x) && (getCenter(first.min.x, first.max.x) <= second.max.x) ) ) ) {
            CollisionMessage.setMessage("down");
            return true;
        }
        return false;
    }

    public static boolean toInteract(AABB first, AABB second) {
        if ( (first.min.x >= second.max.x && first.min.x <= second.max.x + 2) && // Контейнер слева
                ( ( (first.min.y >= second.min.y) && (first.min.y <= second.max.y) ) ||
                        ( (first.max.y >= second.min.y) && (first.max.y <= second.max.y) ) ||
                        ( (getCenter(first.min.y, first.max.y) >= second.min.y) && (getCenter(first.min.y, first.max.y) <= second.max.y) )) ) {
            return true;
        }
        else if ( (first.max.x <= second.min.x && first.max.x >= second.min.x - 2) && // Контейнер справа
                ( ( (first.min.y >= second.min.y) && (first.min.y <= second.max.y) ) ||
                        ( (first.max.y >= second.min.y) && (first.max.y <= second.max.y) ) ||
                        ( (getCenter(first.min.y, first.max.y) >= second.min.y) && (getCenter(first.min.y, first.max.y) <= second.max.y) )) ) {
            return true;
        }
        else if ( (first.min.y >= second.max.y && first.min.y <= second.max.y + 2) && // Контейнер сверху
                ( ( (first.min.x >= second.min.x) && (first.min.x <= second.max.x) ) ||
                        ( (first.max.x >= second.min.x) && (first.max.x <= second.max.x) ) ||
                        ( (getCenter(first.min.x, first.max.x) >= second.min.x) && (getCenter(first.min.x, first.max.x) <= second.max.x) )) ) {
            return true;
        }
        else if ( (first.max.y <= second.min.y && first.max.y >= second.min.y - 2) && // Контейнер снизу
                ( ( (first.min.x >= second.min.x) && (first.min.x <= second.max.x) ) ||
                        ( (first.max.x >= second.min.x) && (first.max.x <= second.max.x) ) ||
                        ( (getCenter(first.min.x, first.max.x) >= second.min.x) && (getCenter(first.min.x, first.max.x) <= second.max.x) )) ) {
            return true;
        }
        return false;
    }

    public void update(int minX, int minY, int maxX, int maxY) {
        min.x = minX;
        min.y = minY;
        max.x = maxX;
        max.y = maxY;
    }

    public static String getFirstBoxPosition(AABB first, AABB second) {
        if (first.getCenterPoint().x < second.getCenterPoint().x &&
                first.getCenterPoint().y < second.getCenterPoint().y)
            return "upLeft";

        else if (first.getCenterPoint().x > second.getCenterPoint().x &&
                first.getCenterPoint().y < second.getCenterPoint().y)
            return "upRight";

        else if (first.getCenterPoint().x < second.getCenterPoint().x &&
                first.getCenterPoint().y > second.getCenterPoint().y)
            return "downLeft";

        else if (first.getCenterPoint().x > second.getCenterPoint().x &&
                first.getCenterPoint().y > second.getCenterPoint().y)
            return "downRight";

        else if (first.getCenterPoint().x < second.getCenterPoint().x &&
                first.getCenterPoint().y == second.getCenterPoint().y)
            return "left";

        else if (first.getCenterPoint().x > second.getCenterPoint().x &&
                first.getCenterPoint().y == second.getCenterPoint().y)
            return "right";

        else if (first.getCenterPoint().x == second.getCenterPoint().x &&
                first.getCenterPoint().y < second.getCenterPoint().y)
            return "up";

        else if (first.getCenterPoint().x == second.getCenterPoint().x &&
                first.getCenterPoint().y > second.getCenterPoint().y)
            return "down";

        else return "someWhere";
    }

    private Point getCenterPoint() {
        Point point = new Point();
        point.x = Math.abs(min.x + ((max.x - min.x) / 2));
        point.y = Math.abs(min.y + ((max.y - min.y) / 2));
        return point;
    }

    private static int getCenter(int firstCoordinate, int secondCoordinate) {
        return Math.abs(firstCoordinate + ((secondCoordinate - firstCoordinate) / 2));
    }

    public void clear() {
        min = null;
        max = null;
    }

    public Point getMin() { return min; }

    public Point getMax() { return max; }
}