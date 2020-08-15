package objects;

import math.AABB;

import java.util.ArrayList;

import static java.lang.Math.random;

public class Container extends Object {
    public ArrayList<Object> loot;
    public Container(String texture, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
        loot = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            double b = random();
            if (b >= 0 && b < 0.3){
                loot.add(loot.size() ,new Coin("coin_01", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 15, this.getMaxY() + 16, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 15, this.getMaxY() + 16)));
                System.out.println("coin");
            } else if (b >= 0.3 && b < 0.4){
                loot.add(loot.size() ,new Potion("potionRed", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 10, this.getMaxY() + 16, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 10, this.getMaxY() + 16)));
                System.out.println("potion");
            } else if (b >= 0.4 && b < 0.55){
                loot.add(loot.size() ,new Scroll("scroll5", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 20, this.getMaxY() + 16, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 20, this.getMaxY() + 16)));
                System.out.println("scroll");
            } else if (b >= 0.55 && b < 0.75){
                loot.add(loot.size() ,new Armor("shirt_white", "torso", 1, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64)));
                System.out.println("armor");
            } else if (b >= 0.75 && b <= 1){
                System.out.println("nothing");
            }
        }
    }
}