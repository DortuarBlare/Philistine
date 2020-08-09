package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Mob {
    private int time = 0;
    private String knockbackDirection;
    private String head, shoulders, torso, belt, hands, legs, feet;
    private AABB attackBox;
    public Timer animationTimer = new Timer();
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
        head = "plate_helmet";
        shoulders = "plate_armor";
        torso = "plate_armor";
        belt = "leather";
        hands = "leather_bracers";
        legs = "plate_pants";
        feet = "plate_shoes";
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

    public void stopAnimationTimer() {
        PlayerTask.animationTime = 1;
        animationTimer.cancel();
        animationTimer.purge();
        animationTimer = new Timer();
        PlayerTask.animationTask = new TimerTask() {
            @Override
            public void run() {
                PlayerTask.animationTime++;
                if (PlayerTask.animationTime == 10) PlayerTask.animationTime = 1;
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

    public String getShoulders() { return shoulders; }

    public void setShoulders(String shoulders) { this.shoulders = shoulders; }

    public String getTorso() { return torso; }

    public void setTorso(String torso) { this.torso = torso; }

    public String getBelt() { return belt; }

    public void setBelt(String belt) { this.belt = belt; }

    public String getHands() { return hands; }

    public void setHands(String hands) { this.hands = hands; }

    public String getLegs() { return legs; }

    public void setLegs(String legs) { this.legs = legs; }

    public String getFeet() { return feet; }

    public void setFeet(String feet) { this.feet = feet; }
}