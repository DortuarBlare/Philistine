package objects;

import math.AABB;

public class Armor extends Object {
    private String type;
    private int defense;

    public Armor(String type, int defense, boolean noclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox, String texture) {
        super(noclip, isLying, minX, minY, maxX, maxY, collisionBox, texture);
        this.type = type;
        this.defense = defense;
    }

    public String getType() { return type; }

    public int getDefense() { return defense; }

    public void setDefense(int defense) { this.defense = defense; }
}
