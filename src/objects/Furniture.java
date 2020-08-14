package objects;

import math.AABB;

public class Furniture extends Object {

    public Furniture(String texture, boolean noclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, noclip, isLying, minX, minY, maxX, maxY, collisionBox);
    }
}
