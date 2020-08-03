package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Mob {
    private int time = 0;
    private String direction;
    private AABB attackBox;
    private Timer timerPlayer = new Timer();
    private TimerTask timerTaskPlayer = new TimerTask() {
        @Override
        public void run() {
            if (direction.equals("Right")) moveRight();
            else if (direction.equals("Left")) moveLeft();
            else if (direction.equals("Up")) moveUp();
            else if (direction.equals("Down")) moveDown();
            time++;
        }
    };

    public Player(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
        attackBox = new AABB();
    }

    public void stopTimerPlayer() {
        time = 0;
        timerPlayer.cancel();
        timerPlayer.purge();
        timerPlayer = new Timer();
        timerTaskPlayer = new TimerTask() {
            @Override
            public void run() {
                if (direction.equals("Right")) moveRight();
                else if (direction.equals("Left")) moveLeft();
                else if (direction.equals("Up")) moveUp();
                else if (direction.equals("Down")) moveDown();
                time++;
            }
        };
    }

    public Timer getTimerPlayer() { return timerPlayer; }

    public TimerTask getTimerTaskPlayer() { return timerTaskPlayer; }

    public AABB getAttackBox() { return attackBox; }

    public int getTime() { return time; }

    public void setDirection(String direction) { this.direction = direction; }
}