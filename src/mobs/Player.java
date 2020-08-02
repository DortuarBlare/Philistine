package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Mob {
    private boolean immortal = false;
    private int time = 0;
    private Timer timerPlayer = new Timer();
    private TimerTask timerTaskPlayer = new TimerTask() {
        @Override
        public void run() {
            moveLeft();
            time++;
        }
    };

    public Player(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
    }

    public void stopTimerPlayer() {
        time = 0;
        timerPlayer.cancel();
        timerPlayer.purge();
        timerPlayer = new Timer();
        timerTaskPlayer = new TimerTask() {
            @Override
            public void run() {
                setX(getX() - getSpeed());
                time++;
            }
        };
    }

    public Timer getTimerPlayer() { return timerPlayer; }

    public TimerTask getTimerTaskPlayer() { return timerTaskPlayer; }

    public int getTime() { return time; }

    public boolean getImmortal() { return immortal; }

    public void setImmortal(boolean immortal) { this.immortal = immortal; }
}