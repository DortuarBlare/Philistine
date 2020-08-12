package objects;

import math.AABB;

public class Furniture extends Object {

    public Furniture(boolean noclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox, String texture) {
        super(noclip, isLying, minX, minY, maxX, maxY, collisionBox, texture);
    }
}
