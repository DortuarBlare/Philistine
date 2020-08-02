package mobs;

import math.AABB;

public class Slime extends Mob {

    public Slime(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
    }
}
