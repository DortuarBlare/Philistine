package mobs;

import math.AABB;

import java.util.Timer;

public abstract class Mob {
    private int x, y;
    private int speed;
    private int health, armor;
    private int damage;
    private AABB hitBox, collisionBox;
    private String moveDirection;
    private boolean dead;
    private boolean immortal;
    private Timer timer = new Timer();

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
        immortal = false;
    }

    public void moveLeft() {
        x -= speed;
        moveDirection = "left";
    }

    public void moveRight() {
        x += speed;
        moveDirection = "right";
    }

    public void moveUp() {
        y -= speed;
        moveDirection = "up";
    }

    public void moveUpRight() {
        y -= speed;
        x += speed;
        moveDirection = "right";
    }

    public void moveUpLeft() {
        y -= speed;
        x -= speed;
        moveDirection = "left";
    }

    public void moveDown() {
        y += speed;
        moveDirection = "down";
    }

    public void moveDownRight() {
        y += speed;
        x += speed;
        moveDirection = "right";
    }

    public void moveDownLeft() {
        y += speed;
        x -= speed;
        moveDirection = "left";
    }

    public void moveSomewhere() {
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                knockBackLeft();
                break;
            case 1:
                knockBackRight();
                break;
            case 2:
                knockBackUp();
                break;
            case 3:
                knockBackDown();
                break;
        }
    }

    public void knockBackLeft() { x -= speed; }

    public void knockBackRight() { x += speed; }

    public void knockBackUp() { y -= speed; }

    public void knockBackDown() { y += speed; }

    public void stop(String direction) {
        if (direction.equals("left")) stopLeft();
        else if (direction.equals("right")) stopRight();
        else if (direction.equals("up")) stopUp();
        else if (direction.equals("down")) stopDown();
    }

    public void stopLeft() { x += speed; }

    public void stopRight() { x -= speed; }

    public void stopUp() { y += speed; }

    public void stopDown() { y -= speed; }

    public void simulate() {}

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

    public String getMoveDirection() { return moveDirection; }

    public void setMoveDirection(String moveDirection) { this.moveDirection = moveDirection; }

    public boolean isDead() { return dead; }

    public void setDead(boolean dead) { this.dead = dead; }

    public boolean isImmortal() { return immortal; }

    public void setImmortal(boolean immortal) { this.immortal = immortal; }

    public Timer getTimer() { return timer; }

    public void setTimer(Timer timer) { this.timer = timer; }

    public void takeDamage(int Damage) {this.health = this.health - (int)(Damage * ((double)(100 - (armor * 2)) / 100));}
}