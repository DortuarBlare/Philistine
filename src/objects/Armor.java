package objects;

import math.AABB;

public class Armor extends Object {
    private String type;
    private int defense;

    public Armor(String texture, String type, int defense, boolean noclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, noclip, isLying, minX, minY, maxX, maxY, collisionBox);
        this.type = type;
        this.defense = defense;
    }

    public Armor(String texture, String type, int defense, boolean noclip) {
        super(texture, noclip, false, 0, 0, 0, 0, new AABB());
        this.type = type;
        this.defense = defense;
    }

    public String getType() { return type; }

    public int getDefense() { return defense; }

    public void setDefense(int defense) { this.defense = defense; }
}
