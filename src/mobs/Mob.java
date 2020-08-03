package mobs;

import math.AABB;

public abstract class Mob {
    private int x, y;
    private int speed;
    private int health, armor;
    private int damage;
    private AABB hitBox, collisionBox;
    private boolean dead;
    private boolean immortal = false;

    public Mob(int x, int y, int speed, int health, int armor, int damage, AABB hitBox, AABB collisionBox) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.armor = armor;
        this.damage = damage;
        this.hitBox = hitBox;
        this.collisionBox = collisionBox;
        dead = false;
    }

    public void moveLeft() { x -= speed; }

    public void moveRight() { x += speed; }

    public void moveUp() { y -= speed; }

    public void moveDown() { y += speed; }

    public int getX() { return x; };

    public void setX(int x) { this.x = x; };

    public int getY() { return y; };

    public void setY(int y) { this.y = y; };

    public int getSpeed() { return speed; };

    public void setSpeed(int speed) { this.speed = speed; };

    public int getHealth() { return health; };

    public void setHealth(int health) { this.health = health; };

    public int getArmor() { return armor; };

    public void setArmor(int armor) { this.armor = armor; };

    public int getDamage() { return damage; };

    public void setDamage(int damage) { this.damage = damage; };

    public AABB getHitbox() { return hitBox; };

    public void setHitBox(AABB hitBox) { this.hitBox = hitBox; };

    public AABB getCollisionBox() { return collisionBox; }

    public boolean getDead() { return dead; }

    public void setDead(boolean dead) { this.dead = dead; }

    public boolean getImmortal() { return immortal; }

    public void setImmortal(boolean immortal) { this.immortal = immortal; }
}