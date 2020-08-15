package objects;

import math.AABB;

public class Furniture extends Object {

    public Furniture(String texture, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
    }
}