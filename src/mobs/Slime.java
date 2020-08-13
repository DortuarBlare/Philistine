package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Slime extends Mob {
    private int knockbackTime = 0, animationTime = 1;
    private String direction;
    private boolean animationTaskStarted = false;
    private Timer timer = new Timer();
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            if (direction.equals("Left")) moveLeft();
            else if (direction.equals("Right")) moveRight();
            else if (direction.equals("Up")) moveUp();
            else if (direction.equals("Down")) moveDown();
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

    public void stopTimerSlime() {
        knockbackTime = 0;
        animationTaskStarted = false;
        timer.cancel();
        timer.purge();
        timer = new Timer();
        knockbackTask = new TimerTask() {
            @Override
            public void run() {
                if (direction.equals("Left")) moveLeft();
                else if (direction.equals("Right")) moveRight();
                else if (direction.equals("Up")) moveUp();
                else if (direction.equals("Down")) moveDown();
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

    public Timer getTimer() { return timer; }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public int getKnockbackTime() { return knockbackTime; }

    public int getAnimationTime() { return animationTime; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public void setAnimationTaskStarted(boolean animationTaskStarted) { this.animationTaskStarted = animationTaskStarted; }

    public void setDirection(String direction) { this.direction = direction; }
}
