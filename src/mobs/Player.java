package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Mob {
    private int time = 0;
    private String knockbackDirection;
    private String head, torso, belt, hands, legs, feet;
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
        setMoveDirection("down");
        head = "hair";
        torso = "shirt_white";
        feet = "shoes_brown";
        hands = belt = legs = "nothing";
    }

    public void stopTimerPlayer() {
        time = 0;
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

    public String getHead() { return head; }

    public void setHead(String head) { this.head = head; }

    public String getTorso() { return torso; }

    public void setTorso(String torso) { this.torso = torso; }

    public String getLegs() { return legs; }

    public void setLegs(String legs) { this.legs = legs; }

    public String getFeet() { return feet; }

    public void setFeet(String feet) { this.feet = feet; }
}