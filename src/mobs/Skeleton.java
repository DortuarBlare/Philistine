package mobs;

import java.util.Timer;
import java.util.TimerTask;

public class Skeleton extends Mob {
    private int knockbackTime = 0, animationTime = 2;
    private String direction;
    private Timer timer = new Timer();
    private TimerTask knockbackTimerTask = new TimerTask() {
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
            animationTime++;
            if (animationTime == 10) animationTime = 2;
        }
    };

    public Skeleton(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage);
        setMoveDirection("left");
    }

    public void stopTimer() {
        knockbackTime = 0;
        timer.cancel();
        timer.purge();
        timer = new Timer();
        knockbackTimerTask = new TimerTask() {
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
                animationTime++;
                if (animationTime == 10) animationTime = 2;
            }
        };
    }

    public Timer getTimer() { return timer; }

    public TimerTask getKnockbackTimerTask() { return knockbackTimerTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public int getKnockbackTime() { return knockbackTime; }

    public int getAnimationTime() { return animationTime; }

    public void setDirection(String direction) { this.direction = direction; }
}
