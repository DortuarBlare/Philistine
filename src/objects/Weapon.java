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

    public Weapon(String texture, String attackType, int damage, AABB collisionBox) {
        super(texture, 0, 0, 0, 0, collisionBox);
        this.damage = damage;
        this.attackType = attackType;
        switch (texture) {
            case "longsword":
            case "rapier": {
                setMinX(64);
                setMinY(64);
                setMaxX(128);
                setMaxY(128);
                break;
            }
            case "dagger":
            case "spear": {
                setMinX(0);
                setMinY(0);
                setMaxX(64);
                setMaxY(64);
                break;
            }
        }
    }

    public int getDamage() { return damage; }

    public void setDamage(int damage) { this.damage = damage; }

    public String getAttackType() { return attackType; }
}
