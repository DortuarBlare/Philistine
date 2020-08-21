package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Slime extends Mob {
    private int knockbackTime = 0, animationTime = 1;
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
            if (animationTime == 4) animationTime = 1;
        }
    };

    public Slime(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
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
                if (animationTime == 4) animationTime = 1;
            }
        };
        animationTaskStarted = false;
    }

    public void follow() {
        if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x &&
                animationTime == 3) moveUpLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x &&
                animationTime == 3) moveUpRight();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x &&
                animationTime == 3) moveDownLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x &&
                animationTime == 3) moveDownRight();

        else if (SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x &&
                animationTime == 3) moveLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x &&
                 animationTime == 3) moveRight();

        else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                animationTime == 3) moveUp();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                animationTime == 3) moveDown();
    }

    @Override
    public void moveUp() { setY(getY() - getSpeed()); }

    @Override
    public void moveDown() { setY(getY() + getSpeed()); }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public int getKnockbackTime() { return knockbackTime; }

    public int getAnimationTime() { return animationTime; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public void setAnimationTaskStarted(boolean animationTaskStarted) { this.animationTaskStarted = animationTaskStarted; }

    public void setKnockbackDirection(String knockbackDirection) { this.knockbackDirection = knockbackDirection; }

    public boolean isKnockbackTaskStarted() { return knockbackTaskStarted; }

    public void setKnockbackTaskStarted(boolean knockbackTaskStarted) { this.knockbackTaskStarted = knockbackTaskStarted; }
}
