package objects;

import physics.AABB;

public class Potion extends Object {
    public Potion(String texture, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
    }
}
