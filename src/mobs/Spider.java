package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Spider extends Mob {
    private int knockbackTime = 0, animationTime = 2;
    private String knockbackDirection;
    private boolean animationTaskStarted = false, knockbackTaskStarted = false;
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            setImmortal(true);
            knockbackTaskStarted = true;
            if (knockbackDirection.equals("left")) knockBackLeft();
            else if (knockbackDirection.equals("right")) knockBackRight();
            else if (knockbackDirection.equals("up")) knockBackUp();
            else if (knockbackDirection.equals("down")) knockBackDown();
            knockbackTime++;
            if (knockbackTime >= 25) stopTimer();
        }
    };
    private TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            animationTaskStarted = true;
            animationTime++;
            if (animationTime == 8) animationTime = 2;
        }
    };

    public Spider(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
        getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        setMoveDirection("left");
    }

    public void stopTimer() {
        knockbackTime = 0;
        setImmortal(false);
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        knockbackTask = new TimerTask() {
            @Override
            public void run() {
                setImmortal(true);
                knockbackTaskStarted = true;
                if (knockbackDirection.equals("left")) knockBackLeft();
                else if (knockbackDirection.equals("right")) knockBackRight();
                else if (knockbackDirection.equals("up")) knockBackUp();
                else if (knockbackDirection.equals("down")) knockBackDown();
                knockbackTime++;
                if (knockbackTime >= 25) stopTimer();
            }
        };
        animationTask = new TimerTask() {
            @Override
            public void run() {
                animationTaskStarted = true;
                animationTime++;
                if (animationTime == 8) animationTime = 2;
            }
        };
        animationTaskStarted = false;
    }

    public void simulate() {
        if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x) moveUpLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x) moveUpRight();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x) moveDownLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x) moveDownRight();

        else if (SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x) moveLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x) moveRight();

        else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y) moveUp();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y) moveDown();
    }

    public void update() {
        // Паук получает урон от игрока
        if (AABB.AABBvsAABB(SingletonPlayer.player.getAttackBox(), getHitbox()) && !isImmortal()) {
            if (SingletonPlayer.player.isAttackLeft()) setKnockbackDirection("left");
            else if (SingletonPlayer.player.isAttackRight()) setKnockbackDirection("right");
            else if (SingletonPlayer.player.isAttackUp()) setKnockbackDirection("up");
            else if (SingletonPlayer.player.isAttackDown()) setKnockbackDirection("down");
            setHealth(getHealth() - SingletonPlayer.player.getDamage());
            if (!isKnockbackTaskStarted()) getTimer().schedule(getKnockbackTask(), 0, 10);
        }

        if (!isAnimationTaskStarted()) getTimer().schedule(getAnimationTask(), 0, 150);
        getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);

        if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x) moveUpLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x) moveUpRight();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x) moveDownLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x) moveDownRight();

        else if (SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x) moveLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x) moveRight();

        else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y) moveUp();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y) moveDown();
    }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public int getAnimationTime() { return animationTime; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public void setKnockbackDirection(String knockbackDirection) { this.knockbackDirection = knockbackDirection; }

    public boolean isKnockbackTaskStarted() { return knockbackTaskStarted; }

    public void setKnockbackTaskStarted(boolean knockbackTaskStarted) { this.knockbackTaskStarted = knockbackTaskStarted; }
}
