package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Spider extends Mob {
    private int knockbackTime = 0, animationTime = 2;
    private String knockbackDirection;
    private boolean animationTaskStarted = false;
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            setImmortal(true);
            if (knockbackDirection.equals("left")) moveLeft();
            else if (knockbackDirection.equals("right")) moveRight();
            else if (knockbackDirection.equals("up")) moveUp();
            else if (knockbackDirection.equals("down")) moveDown();
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
        setMoveDirection("left");
    }

    public void stopTimer() {
        knockbackTime = 0;
        animationTaskStarted = false;
        setImmortal(false);
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        knockbackTask = new TimerTask() {
            @Override
            public void run() {
                setImmortal(true);
                if (knockbackDirection.equals("left")) moveLeft();
                else if (knockbackDirection.equals("right")) moveRight();
                else if (knockbackDirection.equals("up")) moveUp();
                else if (knockbackDirection.equals("down")) moveDown();
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
    }

    public void follow(Player player) {
        if (player.getHitbox().getMin().y < getHitbox().getMin().y &&
                player.getHitbox().getMin().x < getHitbox().getMin().x) moveUpLeft();

        else if (player.getHitbox().getMin().y < getHitbox().getMin().y &&
                player.getHitbox().getMin().x > getHitbox().getMin().x) moveUpRight();

        else if (player.getHitbox().getMin().y > getHitbox().getMin().y &&
                player.getHitbox().getMin().x < getHitbox().getMin().x) moveDownLeft();

        else if (player.getHitbox().getMin().y > getHitbox().getMin().y &&
                player.getHitbox().getMin().x > getHitbox().getMin().x) moveDownRight();

        else if (player.getHitbox().getMin().x < getHitbox().getMin().x) moveLeft();

        else if (player.getHitbox().getMin().x > getHitbox().getMin().x) moveRight();

        else if (player.getHitbox().getMin().y < getHitbox().getMin().y) moveUp();

        else if (player.getHitbox().getMin().y > getHitbox().getMin().y) moveDown();
    }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public int getAnimationTime() { return animationTime; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public void setKnockbackDirection(String knockbackDirection) { this.knockbackDirection = knockbackDirection; }
}
