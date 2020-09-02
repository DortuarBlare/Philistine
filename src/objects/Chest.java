package objects;

import math.AABB;

import static java.lang.Math.random;

public class Chest extends Container {
    public Chest(String texture, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
        setIsNeedKey(true);
        for (int i = 0; i < 3; i++) {
            double b = random();
                if (b >= 0 && b < 0.1) {
                    loot.add(loot.size(), new Coin("coin_01", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 11, this.getMaxY() + 12, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 9, this.getMaxY() + 9)));
                    System.out.println("coin");
                }
                else if (b >= 0.1 && b < 0.2) {
                    loot.add(loot.size(), new Potion("potionRed", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 10, this.getMaxY() + 16, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 10, this.getMaxY() + 16)));
                    System.out.println("potion");
                }
                else if (b >= 0.2 && b < 0.4){
                    Weapon weapon;
                    int randomNumber = (int) (Math.random() * 3);
                    switch (randomNumber){
                        case 0:
                            weapon = new Weapon("longsword", "slash", 30, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 192, this.getMaxY() + 192, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 192, this.getMaxY() + 192));
                            break;
                        case 1:
                            weapon = new Weapon("rapier", "slash", 30, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 192, this.getMaxY() + 192, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 192, this.getMaxY() + 192));
                            break;
                        case 2:
                            weapon = new Weapon("long_spear", "thrust", 30, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 192, this.getMaxY() + 192, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 192, this.getMaxY() + 192));
                            break;
                        case 3:
                            weapon = new Weapon("stick", "thrust", 30, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 192, this.getMaxY() + 192, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 192, this.getMaxY() + 192));
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + randomNumber);
                    }
                    weapon.correctCollisionBox();
                    loot.add(loot.size(), weapon);

                }
                else if (b >= 0.4 && b < 0.75) {
                    Armor armor;
                    int randomNumber = (int) (Math.random() * 22);
                    switch (randomNumber) {
                        case 0:
                            armor = new Armor("plate_shoes", "feet", 5, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 1:
                            armor = new Armor("leather_hat", "head", 2, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 2:
                            armor = new Armor("robe_hood", "head", 2, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 3:
                            armor = new Armor("chain_helmet", "head", 4, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 4:
                            armor = new Armor("chain_hood", "head", 4, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 5:
                            armor = new Armor("plate_helmet", "head", 5, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 6:
                            armor = new Armor("leather_shoulderPads", "shoulders", 2, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 7:
                            armor = new Armor("plate_shoulderPads", "shoulders", 5, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 8:
                            armor = new Armor("shirt_white", "torso", 1, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 9:
                            armor = new Armor("robe_shirt_brown", "torso", 1, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 10:
                            armor = new Armor("leather_armor", "overTorso", 2, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 11:
                            armor = new Armor("chain_jacket_purple", "overTorso", 4, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 12:
                            armor = new Armor("chain_armor", "torso", 4, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 13:
                            armor = new Armor("plate_armor", "overTorso", 5, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 14:
                            armor = new Armor("leather", "belt", 2, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 15:
                            armor = new Armor("rope", "belt", 1, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 16:
                            armor = new Armor("plate_gloves", "hands", 5, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 17:
                            armor = new Armor("leather_bracers", "hands", 2, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 18:
                            armor = new Armor("pants_greenish", "legs", 1, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 19:
                            armor = new Armor("robe_skirt", "legs", 2, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 20:
                            armor = new Armor("plate_pants", "legs", 5, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        case 21:
                            armor = new Armor("shoes_brown", "feet", 1, true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 64, this.getMaxY() + 64, 10);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + randomNumber);
                    }
                    loot.add(loot.size(), armor);
                    System.out.println("armor");
                }
                else if (b >= 0.75 && b <= 1) System.out.println("nothing");
        }
    }
}
