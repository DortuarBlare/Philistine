package mobs;

import math.AABB;

import java.util.Timer;

public abstract class Mob {
    private int x, y;
    private int speed;
    private int health, armor;
    private int damage;
    private boolean dead = false;
    private boolean immortal = false;
    private AABB attackBox, hitBox, collisionBox;
    private String moveDirection, knockBackDirection;
    private int animationTime, hitAnimationTime, deathAnimationTime, knockBackTime;
    private boolean animationTaskStarted = false, hitAnimationTaskStarted = false, knockBackTaskStarted = false;
    private boolean isAttackRight = false, isAttackLeft = false, isAttackUp = false, isAttackDown = false;
    private Timer timer = new Timer();

    public Mob(int x, int y, int speed, int health, int armor, int damage) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.armor = armor;
        this.damage = damage;
        attackBox = new AABB();
        hitBox = new AABB();
        collisionBox = new AABB();
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

    public void moveUpLeft() {
        y -= speed;
        x -= speed;
        moveDirection = "left";
    }

    public void moveUpRight() {
        y -= speed;
        x += speed;
        moveDirection = "right";
    }

    public void moveDown() {
        y += speed;
        moveDirection = "down";
    }

    public void moveDownLeft() {
        y += speed;
        x -= speed;
        moveDirection = "left";
    }

    public void moveDownRight() {
        y += speed;
        x += speed;
        moveDirection = "right";
    }

    public void moveTo(String direction) {
        switch (direction)  {
            case "left":
                moveLeft();
                break;
            case "right":
                moveRight();
                break;
            case "up":
                moveUp();
                break;
            case "upLeft":
                moveUpLeft();
                break;
            case "upRight":
                moveUpRight();
                break;
            case "down":
                moveDown();
                break;
            case "downLeft":
                moveDownLeft();
                break;
            case "downRight":
                moveDownRight();
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

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public int getSpeed() { return speed; }

    public void setSpeed(int speed) { this.speed = speed; }

    public int getHealth() { return health; }

    public void setHealth(int health) { this.health = health; }

    public int getArmor() { return armor; }

    public void setArmor(int armor) { this.armor = armor; }

    public int getDamage() { return damage; }

    public void setDamage(int damage) { this.damage = damage; }

    public AABB getAttackBox() { return attackBox; }

    public AABB getHitbox() { return hitBox; }

    public AABB getCollisionBox() { return collisionBox; }

    public String getMoveDirection() { return moveDirection; }

    public void setMoveDirection(String moveDirection) { this.moveDirection = moveDirection; }

    public String getKnockBackDirection() { return knockBackDirection; }

    public void setKnockBackDirection(String knockBackDirection) { this.knockBackDirection = knockBackDirection; }

    public int getAnimationTime() { return animationTime; }

    public void setAnimationTime(int animationTime) { this.animationTime = animationTime; }

    public void incrementAnimationTime() { this.animationTime++; }

    public int getHitAnimationTime() { return  hitAnimationTime; }

    public void setHitAnimationTime(int hitAnimationTime) { this.hitAnimationTime = hitAnimationTime; }

    public void incrementHitAnimationTime() { this.hitAnimationTime++; }

    public int getDeathAnimationTime() { return deathAnimationTime; }

    public void setDeathAnimationTime(int deathAnimationTime) { this.deathAnimationTime = deathAnimationTime; }

    public void incrementDeathAnimationTime() { this.deathAnimationTime++; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public void setAnimationTaskStarted(boolean animationTaskStarted) { this.animationTaskStarted = animationTaskStarted; }

    public boolean isHitAnimationTaskStarted() { return hitAnimationTaskStarted; }

    public void setHitAnimationTaskStarted(boolean hitAnimationTaskStarted) { this.hitAnimationTaskStarted = hitAnimationTaskStarted; }

    public boolean isKnockBackTaskStarted() { return knockBackTaskStarted; }

    public void setKnockBackTaskStarted(boolean knockBackTaskStarted) { this.knockBackTaskStarted = knockBackTaskStarted; }

    public int getKnockBackTime() { return knockBackTime; }

    public void setKnockBackTime(int knockBackTime) { this.knockBackTime = knockBackTime; }

    public void incrementKnockBackTime() { this.knockBackTime++; }

    public boolean isDead() { return dead; }

    public void setDead(boolean dead) { this.dead = dead; }

    public boolean isImmortal() { return immortal; }

    public void setImmortal(boolean immortal) { this.immortal = immortal; }

    public Timer getTimer() { return timer; }

    public void setTimer(Timer timer) { this.timer = timer; }

    public void takeDamage(int Damage) {this.health = this.health - (int)(Damage * ((double)(100 - (armor * 2)) / 100));}

    public boolean isAttack() {
        if (isAttackLeft) return true;
        else if (isAttackRight) return true;
        else if (isAttackUp) return true;
        else if (isAttackDown) return true;
        return false;
    }

    public void setAttackToFalse() {
        isAttackLeft = false;
        isAttackRight = false;
        isAttackUp = false;
        isAttackDown = false;
    }

    public boolean isAttackLeft() { return isAttackLeft; }

    public void setAttackLeft(boolean attackLeft) { isAttackLeft = attackLeft; }

    public boolean isAttackRight() { return isAttackRight; }

    public void setAttackRight(boolean attackRight) { isAttackRight = attackRight; }

    public boolean isAttackUp() { return isAttackUp; }

    public void setAttackUp(boolean attackUp) { isAttackUp = attackUp; }

    public boolean isAttackDown() { return isAttackDown; }

    public void setAttackDown(boolean attackDown) { isAttackDown = attackDown; }
}