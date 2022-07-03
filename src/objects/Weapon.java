package objects;

import physics.AABB;

public class Weapon extends Object {
    private int damage;
    private String attackType;

    public Weapon(String texture, String attackType, int damage, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
        this.damage = damage;
        this.attackType = attackType;
    }

    public Weapon(String texture, String attackType, int damage, AABB collisionBox) {
        super(texture, 0, 0, 0, 0, collisionBox);
        this.damage = damage;
        this.attackType = attackType;
        switch (texture) {
            case "stick":
            case "long_spear":
            case "longsword":
            case "rapier": {
                setMinX(64);
                setMinY(64);
                setMaxX(128);
                setMaxY(128);
                break;
            }
        }
    }

    public void resize() {
        switch (getTexture()) {
            case "stick":
            case "long_spear":
            case "longsword":
            case "rapier": {
                setMinX(64);
                setMinY(64);
                setMaxX(128);
                setMaxY(128);
                break;
            }
        }
    }

    public void correctCollisionBox() {
        switch (getTexture()) {
            case "longsword":
                getCollisionBox().update(getMinX() + 81, getMinY() + 81, getMinX() + 109, getMinY() + 109);
                break;
            case "rapier":
                getCollisionBox().update(getMinX() + 96, getMinY() + 84, getMinX() + 125, getMinY() + 116);
                break;
            case "long_spear":
                getCollisionBox().update(getMinX() + 77, getMinY() + 63, getMinX() + 137, getMinY() + 122);
                break;
            case "stick":
                getCollisionBox().update(getMinX() + 76, getMinY() + 83, getMinX() + 116, getMinY() + 123);
                break;
        }
    }

    public int getDamage() { return damage; }

    public void setDamage(int damage) { this.damage = damage; }

    public String getAttackType() { return attackType; }
}
