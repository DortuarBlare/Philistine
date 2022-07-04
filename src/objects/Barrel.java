package objects;

import physics.AABB;

import static java.lang.Math.random;

public class Barrel extends Container {
    public Barrel(String texture, int minX, int minY) {
        super(texture, false, true, minX, minY, 0, 0, new AABB());
        setMaxX(minX + 20);
        setMaxY(minY + 30);
        getCollisionBox().update(minX, minY, getMaxX(), getMaxY());

        for (int i = 0; i < 3; i++) {
            double b = random();
            if (b >= 0 && b < 0.3) {
                loot.add(loot.size(), new Coin("coin_01", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 11, this.getMaxY() + 12, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 9, this.getMaxY() + 9)));
                System.out.println("coin");
            }
            else if (b >= 0.3 && b < 0.4) {
                loot.add(loot.size(), new Potion("potionRed", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 12, this.getMaxY() + 18, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 12, this.getMaxY() + 18)));
                System.out.println("potion");
            }
            else if (b >= 0.4 && b <= 1) System.out.println("nothing");
        }
    }
}
