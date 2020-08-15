package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Slime extends Mob {
    private int knockbackTime = 0, animationTime = 1;
    private String knockbackDirection;
    private boolean animationTaskStarted = false;
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            if (knockbackDirection.equals("left")) moveLeft();
            else if (knockbackDirection.equals("right")) moveRight();
            else if (knockbackDirection.equals("up")) moveUp();
            else if (knockbackDirection.equals("down")) moveDown();
            knockbackTime++;
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
        animationTaskStarted = false;
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        knockbackTask = new TimerTask() {
            @Override
            public void run() {
                if (knockbackDirection.equals("left")) moveLeft();
                else if (knockbackDirection.equals("right")) moveRight();
                else if (knockbackDirection.equals("up")) moveUp();
                else if (knockbackDirection.equals("down")) moveDown();
                knockbackTime++;
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
}
