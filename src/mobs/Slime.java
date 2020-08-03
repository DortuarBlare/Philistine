package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Slime extends Mob {
    private int time = 0;
    private String direction;
    private Timer timerSlime = new Timer();
    private TimerTask timerTaskSlime = new TimerTask() {
        @Override
        public void run() {
//            if (direction.equals("Left")) moveRight();
//            else if (direction.equals("Right")) moveLeft();
//            else if (direction.equals("Up")) moveDown();
//            else if (direction.equals("Down")) moveUp();
            moveRight();
            time++;
        }
    };

    public Slime(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
    }

    public void stopTimerSlime() {
        time = 0;
        timerSlime.cancel();
        timerSlime.purge();
        timerSlime = new Timer();
        timerTaskSlime = new TimerTask() {
            @Override
            public void run() {
                moveRight();
                time++;
            }
        };
    }

    public Timer getTimerSlime() { return timerSlime; }

    public TimerTask getTimerTaskSlime() { return timerTaskSlime; }

    public int getTime() { return time; }
}
