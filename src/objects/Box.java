package objects;

import math.AABB;

import static java.lang.Math.random;

public class Box extends Container {
    public Box(String texture, boolean isOnlyClothes, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isOnlyClothes, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
        for (int i = 0; i < 3; i++) {
            double b = random();
            if (b >= 0 && b < 0.3) {
                loot.add(loot.size(), new Coin("coin_01", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 11, this.getMaxY() + 12, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 9, this.getMaxY() + 9)));
                System.out.println("coin");
            }
            else if (b >= 0.3 && b < 0.4) {
                loot.add(loot.size(), new Potion("potionRed", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 10, this.getMaxY() + 16, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 10, this.getMaxY() + 16)));
                System.out.println("potion");
            }
            else if (b >= 0.4 && b <= 1) System.out.println("nothing");
        }
    }
}
