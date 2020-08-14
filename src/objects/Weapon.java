package objects;

import math.AABB;

public class Weapon extends Object {
    private int damage;
    private String attackType;

    public Weapon(String texture, String attackType, int damage, boolean noclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, noclip, isLying, minX, minY, maxX, maxY, collisionBox);
        this.damage = damage;
        this.attackType = attackType;
    }

    public int getDamage() { return damage; }

    public void setDamage(int damage) { this.damage = damage; }

    public String getAttackType() { return attackType; }
}
