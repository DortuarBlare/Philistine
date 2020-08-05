package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Mob {
    private int time = 1;
    private String knockbackDirection, direction;
    private String head, shoulders, hands, feet, torso, legs, belt;
    private AABB attackBox;
    private Timer timerPlayer = new Timer();
    private TimerTask timerTaskPlayer = new TimerTask() {
        @Override
        public void run() {
            if (knockbackDirection.equals("Left")) moveLeft();
            else if (knockbackDirection.equals("Right")) moveRight();
            else if (knockbackDirection.equals("Up")) moveUp();
            else if (knockbackDirection.equals("Down")) moveDown();
            time++;
        }
    };

    public Player(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
        attackBox = new AABB();
        direction = "down";
        head = shoulders = hands = feet = torso = belt = "nothing";
        legs = "pants_greenish";
    }

    public void stopTimerPlayer() {
        time = 1;
        timerPlayer.cancel();
        timerPlayer.purge();
        timerPlayer = new Timer();
        timerTaskPlayer = new TimerTask() {
            @Override
            public void run() {
                if (knockbackDirection.equals("Left")) moveLeft();
                else if (knockbackDirection.equals("Right")) moveRight();
                else if (knockbackDirection.equals("Up")) moveUp();
                else if (knockbackDirection.equals("Down")) moveDown();
                time++;
            }
        };
    }

    public Timer getTimerPlayer() { return timerPlayer; }

    public TimerTask getTimerTaskPlayer() { return timerTaskPlayer; }

    public AABB getAttackBox() { return attackBox; }

    public int getTime() { return time; }

    public void setKnockbackDirection(String knockbackDirection) { this.knockbackDirection = knockbackDirection; }

    public String getDirection() { return direction; }

    public void setDirection(String direction) { this.direction = direction; }

    public String getLegs() {
        return legs;
    }

    public void setLegs(String legs) {
        this.legs = legs;
    }
}