package mobs;

import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Mob {
    private int time = 0;
    private String knockbackDirection;
    private String weapon, attackType, head, shoulders, torso, belt, hands, legs, feet;
    private AABB attackBox;
    private Timer timer = new Timer();
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
        weapon = "longsword";
        attackType = "slash";
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
        timer.cancel();
        timer.purge();
        timer = new Timer();
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

    public Timer getTimer() { return timer; }

    public TimerTask getTimerTaskPlayer() { return timerTaskPlayer; }

    public AABB getAttackBox() { return attackBox; }

    public int getTime() { return time; }

    public void setKnockbackDirection(String knockbackDirection) { this.knockbackDirection = knockbackDirection; }

    public String getWeapon() { return weapon; }

    public void setWeapon(String weapon) { this.weapon = weapon; }

    public String getAttackType() { return attackType; }

    public void setAttackType(String attackType) { this.attackType = attackType; }

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