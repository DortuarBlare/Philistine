package objects;

import math.AABB;

public class Weapon extends Object {
    private int damage;
    private String attackType;

    public Weapon(int damage, String attackType, boolean noclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox, String texture) {
        super(noclip, isLying, minX, minY, maxX, maxY, collisionBox, texture);
        this.damage = damage;
        this.attackType = attackType;
    }

    public int getDamage() { return damage; }

    public void setDamage(int damage) { this.damage = damage; }

    public String getAttackType() { return attackType; }
}
