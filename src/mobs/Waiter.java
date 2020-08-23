package mobs;

import math.AABB;

public class Waiter extends Mob {

    public Waiter(int x, int y, int speed) {
        super(x, y, speed, 100, 0, 0, new AABB(), new AABB());
    }
}
