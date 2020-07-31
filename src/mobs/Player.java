package mobs;

import math.AABB;

public class Player extends Mob {

    public Player(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(x, y, x + 42, y + 64));

    }

}