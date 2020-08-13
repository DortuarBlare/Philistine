package mobs;

import math.AABB;
import objects.Armor;

import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Player extends Mob {
    private int time = 0;
    private String knockbackDirection;
    private Armor head, shoulders, torso, belt, hands, legs, feet;
    private String weapon, attackType;
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
        head = new Armor("head", 5, true, false, 0,0,0,0, new AABB(),"plate_helmet");
        shoulders = new Armor("shoulders", 5, true, false, 0,0,0,0, new AABB(),"plate_armor");
        torso = new Armor("torso", 5, true, false, 0,0,0,0, new AABB(),"plate_armor");
        belt = new Armor("belt", 2, true, false, 0,0,0,0, new AABB(),"leather");
        hands = new Armor("hands", 2, true, false, 0,0,0,0, new AABB(),"leather_bracers");
        legs = new Armor("legs", 5, true, false, 0,0,0,0, new AABB(),"plate_pants");
        feet = new Armor("feet", 5, true, false, 0,0,0,0, new AABB(),"plate_shoes");
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

    public String getHead() { return head.getTexture(); }

    public void setHead(String head) { this.head.setTexture(head); }

    public String getShoulders() { return shoulders.getTexture(); }

    public void setShoulders(String shoulders) { this.shoulders.setTexture(shoulders); }

    public String getTorso() { return torso.getTexture(); }

    public void setTorso(String torso) { this.torso.setTexture(torso); }

    public String getBelt() { return belt.getTexture(); }

    public void setBelt(String belt) { this.belt.setTexture(belt); }

    public String getHands() { return hands.getTexture(); }

    public void setHands(String hands) { this.hands.setTexture(hands); }

    public String getLegs() { return legs.getTexture(); }

    public void setLegs(String legs) { this.legs.setTexture(legs); }

    public String getFeet() { return feet.getTexture(); }

    public void setFeet(String feet) { this.feet.setTexture(feet); }

    public Armor getArmorType(Armor armor) {
        switch (armor.getType()) {
            case "head": return head;
            case "shoulders": return shoulders;
            case "torso": return torso;
            case "belt": return belt;
            case "hands": return hands;
            case "legs": return legs;
            case "feet": return feet;
        }
        return null;
    }

    public void setArmor(Armor armor) {
        switch (armor.getType()) {
            case "head":
                head = armor;
                break;
            case "shoulders":
                shoulders = armor;
                break;
            case "torso":
                torso = armor;
                break;
            case "belt":
                belt = armor;
                break;
            case "hands":
                hands = armor;
                break;
            case "legs":
                legs = armor;
                break;
            case "feet":
                feet = armor;
                break;
        }
    }

    public void reArmor() { this.setArmor(head.getDefense() + shoulders.getDefense() + torso.getDefense() + belt.getDefense() + hands.getDefense() + legs.getDefense() + feet.getDefense()); }
}