package objects;

import math.AABB;

public class Armor extends Object {
    private String type;
    private int defense;
    private int price;

    public Armor(String texture, String type, int defense, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, int price) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, new AABB());
        this.type = type;
        this.defense = defense;
        this.price = price;
        switch (type) {
            case "head":
                getCollisionBox().update(getMinX() + 21, getMinY() + 13, getMinX() + 42, getMinY() + 36);
                break;
            case "shoulders":
                getCollisionBox().update(getMinX() + 17, getMinY() + 32, getMinX() + 46, getMinY() + 45);
                break;
            case "torso":
                getCollisionBox().update(getMinX() + 17, getMinY() + 32, getMinX() + 46, getMinY() + 54);
                break;
            case "belt":
                getCollisionBox().update(getMinX() + 23, getMinY() + 43, getMinX() + 40, getMinY() + 48);
                break;
            case "hands":
                getCollisionBox().update(getMinX() + 17, getMinY() + 39, getMinX() + 46, getMinY() + 44);
                break;
            case "legs":
                getCollisionBox().update(getMinX() + 22, getMinY() + 45, getMinX() + 41, getMinY() + 56);
                break;
            case "feet":
                getCollisionBox().update(getMinX() + 20, getMinY() + 55, getMinX() + 43, getMinY() + 63);
                break;
        }
    }

    public Armor(String texture, String type, int defense, boolean isNoclip) {
        super(texture, isNoclip, false, 0, 0, 0, 0, new AABB());
        this.type = type;
        this.defense = defense;
    }

    public void correctCollisionBox() {
        switch (type) {
            case "head":
                getCollisionBox().update(getMinX() + 21, getMinY() + 13, getMinX() + 42, getMinY() + 36);
                break;
            case "shoulders":
                getCollisionBox().update(getMinX() + 17, getMinY() + 32, getMinX() + 46, getMinY() + 45);
                break;
            case "torso":
                getCollisionBox().update(getMinX() + 17, getMinY() + 32, getMinX() + 46, getMinY() + 54);
                break;
            case "belt":
                getCollisionBox().update(getMinX() + 23, getMinY() + 43, getMinX() + 40, getMinY() + 48);
                break;
            case "hands":
                getCollisionBox().update(getMinX() + 17, getMinY() + 39, getMinX() + 46, getMinY() + 44);
                break;
            case "legs":
                getCollisionBox().update(getMinX() + 22, getMinY() + 45, getMinX() + 41, getMinY() + 56);
                break;
            case "feet":
                getCollisionBox().update(getMinX() + 20, getMinY() + 55, getMinX() + 43, getMinY() + 63);
                break;
        }
    }

    public String getType() { return type; }

    public int getDefense() { return defense; }

    public void setDefense(int defense) { this.defense = defense; }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}