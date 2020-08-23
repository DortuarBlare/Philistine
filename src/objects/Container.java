package objects;

import math.AABB;

import java.util.ArrayList;

import static java.lang.Math.random;

public class Container extends Object {
    public ArrayList<Object> loot;
    private String state;

    public Container(String texture, boolean isOnlyClothes, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
        state = "Closed";
        loot = new ArrayList<Object>();
        for (int i = 0; i < 3; i++) {
            double b = random();
            if (!isOnlyClothes) {
                if (b >= 0 && b < 0.3) {
                    loot.add(loot.size(), new Coin("coin_01", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 11, this.getMaxY() + 12, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 9, this.getMaxY() + 9)));
                    System.out.println("coin");
                }
                else if (b >= 0.3 && b < 0.4) {
                    loot.add(loot.size(), new Potion("potionRed", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 10, this.getMaxY() + 16, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 10, this.getMaxY() + 16)));
                    System.out.println("potion");
                }
                else if (b >= 0.4 && b < 0.55) {
                    loot.add(loot.size(), new Scroll("scroll5", true, true, this.getMaxX(), this.getMaxY(), this.getMaxX() + 20, this.getMaxY() + 16, new AABB(this.getMaxX(), this.getMaxY(), this.getMaxX() + 20, this.getMaxY() + 16)));
                    System.out.println("scroll");
                }
                else if (b >= 0.55 && b < 0.75) {
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
            else {
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
                armor.setPositionOnTradeTable(i + 1);
                loot.add(armor);
                if (i == 2) {
                    for (Object object : loot) {
                        Armor armorForShop = (Armor) object;
                        if (armorForShop.getPositionOnTradeTable() == 1) {
                            switch (armorForShop.getTexture()) {
                                case "leather_hat":
                                case "plate_helmet":
                                    armorForShop.setSize(296, 225, 296 + 64, 225 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "robe_hood":
                                    armorForShop.setSize(295, 222, 295 + 64, 222 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "chain_helmet":
                                    armorForShop.setSize(295, 226, 295 + 64, 226 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "chain_hood":
                                    armorForShop.setSize(296, 222, 296 + 64, 222 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "leather_shoulderPads":
                                    armorForShop.setSize(295, 218, 295 + 64, 218 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "plate_shoulderPads":
                                    armorForShop.setSize(296, 213, 296 + 64, 213 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "robe_shirt_brown":
                                case "shirt_white":
                                    armorForShop.setSize(295, 213, 295 + 64, 213 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "leather_armor":
                                case "plate_armor":
                                case "chain_jacket_purple":
                                    armorForShop.setSize(296, 210, 296 + 64, 210 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "chain_armor":
                                    armorForShop.setSize(296, 209, 296 + 64, 209 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "rope":
                                case "leather":
                                    armorForShop.setSize(296, 208, 296 + 64, 208 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "leather_bracers":
                                case "plate_gloves":
                                    armorForShop.setSize(296, 207, 296 + 64, 207 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "plate_pants":
                                case "pants_greenish":
                                    armorForShop.setSize(296, 204, 296 + 64, 204 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "robe_skirt":
                                    armorForShop.setSize(295, 202, 295 + 64, 202 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "shoes_brown":
                                    armorForShop.setSize(296, 197, 296 + 64, 197 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                                case "plate_shoes":
                                    armorForShop.setSize(296, 196, 296 + 64, 196 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x + 10, armorForShop.getCollisionBox().getMax().y);
                                    break;
                            }
                        }
                        else if (armorForShop.getPositionOnTradeTable() == 2) {
                            switch (armorForShop.getTexture()) {
                                case "leather_hat":
                                    armorForShop.setSize(331, 186, 331 + 64, 186 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "robe_hood":
                                    armorForShop.setSize(331, 180, 331 + 64, 180 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "chain_helmet":
                                    armorForShop.setSize(331, 184, 331 + 64, 184 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "chain_hood":
                                    armorForShop.setSize(332, 180, 332 + 64, 180 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "plate_helmet":
                                    armorForShop.setSize(331, 183, 331 + 64, 183 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "leather_shoulderPads":
                                    armorForShop.setSize(332, 174, 332 + 64, 174 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "plate_shoulderPads":
                                    armorForShop.setSize(329, 170, 329 + 64, 170 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "robe_shirt_brown":
                                case "shirt_white":
                                    armorForShop.setSize(332, 169, 332 + 64, 169 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "leather_armor":
                                case "plate_armor":
                                    armorForShop.setSize(330, 169, 330 + 64, 169 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "chain_jacket_purple":
                                    armorForShop.setSize(329, 167, 329 + 64, 167 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "chain_armor":
                                    armorForShop.setSize(331, 166, 331 + 64, 166 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "rope":
                                case "leather":
                                    armorForShop.setSize(331, 167, 331 + 64, 167 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "leather_bracers":
                                case "plate_gloves":
                                    armorForShop.setSize(331, 163, 331 + 64, 163 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "plate_pants":
                                case "pants_greenish":
                                    armorForShop.setSize(331, 160, 331 + 64, 160 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "robe_skirt":
                                    armorForShop.setSize(330, 158, 330 + 64, 158 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "shoes_brown":
                                    armorForShop.setSize(331, 153, 331 + 64, 153 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "plate_shoes":
                                    armorForShop.setSize(332, 151, 332 + 64, 151 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                            }
                        }
                        else if (armorForShop.getPositionOnTradeTable() == 3) {
                            switch (armorForShop.getTexture()) {
                                case "leather_hat":
                                    armorForShop.setSize(374, 186, 374 + 64, 186 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "robe_hood":
                                    armorForShop.setSize(372, 180, 372 + 64, 180 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "chain_helmet":
                                    armorForShop.setSize(373, 184, 373 + 64, 184 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "chain_hood":
                                    armorForShop.setSize(375, 180, 375 + 64, 180 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "plate_helmet":
                                    armorForShop.setSize(375, 183, 375 + 64, 183 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "leather_shoulderPads":
                                    armorForShop.setSize(373, 173, 373 + 64, 173 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "plate_shoulderPads":
                                    armorForShop.setSize(374, 170, 374 + 64, 170 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "robe_shirt_brown":
                                case "shirt_white":
                                    armorForShop.setSize(371, 169, 371 + 64, 169 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "leather_armor":
                                case "plate_armor":
                                    armorForShop.setSize(374, 169, 374 + 64, 169 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "chain_jacket_purple":
                                    armorForShop.setSize(375, 167, 375 + 64, 167 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "chain_armor":
                                    armorForShop.setSize(372, 166, 372 + 64, 166 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "rope":
                                case "leather":
                                    armorForShop.setSize(374, 167, 374 + 64, 167 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "leather_bracers":
                                case "plate_gloves":
                                    armorForShop.setSize(374, 162, 374 + 64, 162 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "plate_pants":
                                case "pants_greenish":
                                    armorForShop.setSize(374, 160, 374 + 64, 160 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "robe_skirt":
                                    armorForShop.setSize(373, 158, 373 + 64, 158 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "shoes_brown":
                                    armorForShop.setSize(371, 152, 371 + 64, 152 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                                case "plate_shoes":
                                    armorForShop.setSize(371, 151, 371 + 64, 151 + 64);
                                    armorForShop.correctCollisionBox();
                                    armorForShop.getCollisionBox().update(armorForShop.getCollisionBox().getMin().x, armorForShop.getCollisionBox().getMin().y, armorForShop.getCollisionBox().getMax().x, armorForShop.getCollisionBox().getMax().y + 20);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }
}