package mobs;

import content.AudioMaster;
import content.Source;
import content.Storage;
import math.AABB;
import objects.Armor;
import objects.Weapon;
import singletons.SingletonMobs;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.opengl.GL11.glTranslated;

public class Player extends Mob {
    private HashMap<String, Integer> playerSounds;
    private Source stepSound, hitSound, selectionMenuSound, openMenuSound;
    private Armor head, shoulders, overTorso, torso, belt, hands, legs, feet;
    private Weapon weapon;
    private int money, keys;
    private String bodyAnimation, weaponAnimation, headAnimation, shouldersAnimation, overTorsoAnimation, torsoAnimation, beltAnimation, handsAnimation, legsAnimation, feetAnimation;
    private int player_animation_move_left_i = 2, player_animation_move_right_i = 2, player_animation_move_up_i = 2, player_animation_move_down_i = 2;
    private int player_animation_move_g = 0;
    private int forPlacingCamera = 0;
    private boolean dialogBubble = false;
    private boolean scrollMenu = false;
    private boolean dialogBubbleChoice = false;
    private boolean mainMenuDirection = true;
    private String menuChoice = "Resume";
    private boolean hitAnimationTaskStarted = false, knockbackTaskStarted = false;
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            setImmortal(true);
            knockbackTaskStarted = true;
            if (getKnockBackTime() <= 15) {
                switch (getKnockBackDirection()) {
                    case "left":
                        knockBackLeft();
                        break;
                    case "right":
                        knockBackRight();
                        break;
                    case "up":
                        knockBackUp();
                        break;
                    case "down":
                        knockBackDown();
                        break;
                }
            }
            incrementKnockBackTime();
        }
    };
    private TimerTask hitAnimationTask = new TimerTask() {
        @Override
        public void run() {
            incrementHitAnimationTime();
            switch (weapon.getTexture()) {
                case "rapier":
                case "longsword":
                    if (isAttackLeft()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() - 49, getY() + 21, getX() + 17, getY() + 45);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(0,0,0,0);
                        if (getHitAnimationTime() == 7) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            getAttackBox().update(0,0,0,0);
                            stopTimer();
                        }
                    }
                    else if (isAttackRight()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() + 47, getY() + 21, getX() + 113, getY() + 45);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(0,0,0,0);
                        if (getHitAnimationTime() == 7) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            getAttackBox().update(0,0,0,0);
                            stopTimer();
                        }
                    }
                    else if (isAttackUp()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() - 11, getY() - 4, getX() + 70, getY() + 33);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(getX() + 27, getY() - 1, getX() + 95, getY() + 38);
                        if (getHitAnimationTime() == 7) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            getAttackBox().update(0,0,0,0);
                            stopTimer();
                        }
                    }
                    else if (isAttackDown()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() - 9, getY() + 44, getX() + 72, getY() + 81);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(getX() + 28, getY() + 39, getX() + 96, getY() + 78);
                        if (getHitAnimationTime() == 7) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            getAttackBox().update(0,0,0,0);
                            stopTimer();
                        }
                    }
                    break;
                case "long_spear":
                    if (isAttackLeft()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() - 27, getY() + 42, getX() + 15, getY() + 46);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(getX() - 39, getY() + 42, getX() + 12, getY() + 46);
                        if (getHitAnimationTime() == 7)
                            getAttackBox().update(getX() - 27, getY() + 42, getX() + 15, getY() + 46);
                        if (getHitAnimationTime() == 8)
                            getAttackBox().update(0,0,0,0);
                        if (getHitAnimationTime() == 9) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            stopTimer();
                        }
                    }
                    else if (isAttackRight()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() + 49, getY() + 42, getX() + 101, getY() + 46);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(getX() + 51, getY() + 43, getX() + 103, getY() + 46);
                        if (getHitAnimationTime() == 7)
                            getAttackBox().update(getX() + 49, getY() + 42, getX() + 101, getY() + 46);
                        if (getHitAnimationTime() == 8)
                            getAttackBox().update(0,0,0,0);
                        if (getHitAnimationTime() == 9) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            stopTimer();
                        }
                    }
                    else if (isAttackUp()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() + 38, getY() - 29, getX() + 42, getY() + 18);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(getX() + 38, getY() - 37, getX() + 42, getY() + 18);
                        if (getHitAnimationTime() == 7)
                            getAttackBox().update(getX() + 38, getY() - 29, getX() + 42, getY() + 18);
                        if (getHitAnimationTime() == 8)
                            getAttackBox().update(0,0,0,0);
                        if (getHitAnimationTime() == 9) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            stopTimer();
                        }
                    }
                    else if (isAttackDown()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() + 26, getY() + 49, getX() + 30, getY() + 95);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(getX() + 27, getY() + 51, getX() + 31, getY() + 103);
                        if (getHitAnimationTime() == 7)
                            getAttackBox().update(getX() + 26, getY() + 49, getX() + 30, getY() + 95);
                        if (getHitAnimationTime() == 8)
                            getAttackBox().update(0,0,0,0);
                        if (getHitAnimationTime() == 9) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            stopTimer();
                        }
                    }
                    break;
                case "stick":
                    if (isAttackLeft()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() + 6, getY() + 43, getX() + 15, getY() + 45);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(getX() + 1, getY() + 43, getX() + 12, getY() + 45);
                        if (getHitAnimationTime() == 7)
                            getAttackBox().update(getX() + 6, getY() + 43, getX() + 15, getY() + 45);
                        if (getHitAnimationTime() == 8)
                            getAttackBox().update(0,0,0,0);
                        if (getHitAnimationTime() == 9) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            stopTimer();
                        }
                    }
                    else if (isAttackRight()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() + 48, getY() + 43, getX() + 58, getY() + 45);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(getX() + 52, getY() + 43, getX() + 62, getY() + 45);
                        if (getHitAnimationTime() == 7)
                            getAttackBox().update(getX() + 48, getY() + 43, getX() + 58, getY() + 45);
                        if (getHitAnimationTime() == 8)
                            getAttackBox().update(0,0,0,0);
                        if (getHitAnimationTime() == 9) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            stopTimer();
                        }
                    }
                    else if (isAttackUp()) {
                        if (getHitAnimationTime() == 5)
                            getAttackBox().update(getX() + 39, getY() + 2, getX() + 42, getY() + 19);
                        if (getHitAnimationTime() == 6)
                            getAttackBox().update(getX() + 39, getY(), getX() + 42, getY() + 19);
                        if (getHitAnimationTime() == 7)
                            getAttackBox().update(getX() + 39, getY() + 2, getX() + 42, getY() + 19);
                        if (getHitAnimationTime() == 8)
                            getAttackBox().update(0,0,0,0);
                        if (getHitAnimationTime() == 9) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            stopTimer();
                        }
                    }
                    else if (isAttackDown()) {
                        if (getHitAnimationTime() == 9) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            stopTimer();
                        }
                    }
                    break;
                case "nothing":
                    if (getHitAnimationTime() == 7) {
                        setHitAnimationTime(1);
                        setAttackLeft(false);
                        setAttackRight(false);
                        setAttackUp(false);
                        setAttackDown(false);
                        getAttackBox().update(0,0,0,0);
                        stopTimer();
                    }
                    break;
            }
        }
    };
    private final TimerTask deathAnimationTask = new TimerTask() {
        @Override
        public void run() {
            incrementDeathAnimationTime();
            if (getDeathAnimationTime() == 6) {
                getTimer().cancel();
                getTimer().purge();
            }
        }
    };

    public Player(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage);
        setMoveDirection("down");
        setHitAnimationTime(1);
        setDeathAnimationTime(0);
        setKnockBackTime(0);
        money = 10;
        keys = 1;
        weapon = new Weapon("nothing", "slash", 10, new AABB());
        head = new Armor("nothing", "head", 0, true);
        shoulders = new Armor("nothing", "shoulders", 0, true);
        overTorso = new Armor("nothing", "overTorso", 0, true);
        torso = new Armor("shirt_white", "torso", 1, true);
        belt = new Armor("nothing", "belt", 0, true);
        hands = new Armor("nothing", "hands", 0, true);
        legs = new Armor("pants_greenish", "legs", 1, true);
        feet = new Armor("nothing", "feet", 0, true);
        bodyAnimation = "player_stand_" + getMoveDirection();
        headAnimation = "HEAD_" + getHeadTexture() + "_" + getMoveDirection() + "_move_01";
        shouldersAnimation = "SHOULDERS_" + getShouldersTexture() + "_" + getMoveDirection() + "_move_01";
        torsoAnimation = "TORSO_" + getTorsoTexture() + "_" + getMoveDirection() + "_move_01";
        beltAnimation = "BELT_" + getBeltTexture() + "_" + getMoveDirection() + "_move_01";
        handsAnimation = "HANDS_" + getHandsTexture() + "_" + getMoveDirection() + "_move_01";
        legsAnimation = "LEGS_" + getLegsTexture() + "_" + getMoveDirection() + "_move_01";
        feetAnimation = "FEET_" + getFeetTexture() + "_" + getMoveDirection() + "_move_01";
        playerSounds = new HashMap<String, Integer>();
        stepSound = new Source(0);
        hitSound = new Source(0);
        selectionMenuSound = new Source(0);
        openMenuSound = new Source(0);
        for (int i = 0, id = 0; i < Storage.playerSoundString.length; i++)
            playerSounds.put(Storage.playerSoundString[i], id = AudioMaster.loadSound("sounds/" + Storage.playerSoundString[i]));
    }

    public void stopTimer() {
        setKnockBackTime(0);
        setHitAnimationTime(1);
        setAttackLeft(false);
        setAttackRight(false);
        setAttackUp(false);
        setAttackDown(false);
        setImmortal(false);
        getAttackBox().update(0,0,0,0);
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        knockbackTask = new TimerTask() {
            @Override
            public void run() {
                incrementKnockBackTime();
                knockbackTaskStarted = true;
                if (getKnockBackTime() <= 15) {
                    switch (getKnockBackDirection()) {
                        case "left":
                            knockBackLeft();
                            break;
                        case "right":
                            knockBackRight();
                            break;
                        case "up":
                            knockBackUp();
                            break;
                        case "down":
                            knockBackDown();
                            break;
                    }
                }
            }
        };
        hitAnimationTask = new TimerTask() {
            @Override
            public void run() {
                incrementHitAnimationTime();
                switch (weapon.getTexture()) {
                    case "rapier":
                    case "longsword":
                        if (isAttackLeft()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() - 49, getY() + 21, getX() + 17, getY() + 45);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(0,0,0,0);
                            if (getHitAnimationTime() == 7) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                getAttackBox().update(0,0,0,0);
                                stopTimer();
                            }
                        }
                        else if (isAttackRight()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() + 47, getY() + 21, getX() + 113, getY() + 45);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(0,0,0,0);
                            if (getHitAnimationTime() == 7) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                getAttackBox().update(0,0,0,0);
                                stopTimer();
                            }
                        }
                        else if (isAttackUp()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() - 11, getY() - 4, getX() + 70, getY() + 33);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(getX() + 27, getY() - 1, getX() + 95, getY() + 38);
                            if (getHitAnimationTime() == 7) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                getAttackBox().update(0,0,0,0);
                                stopTimer();
                            }
                        }
                        else if (isAttackDown()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() - 9, getY() + 44, getX() + 72, getY() + 81);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(getX() + 28, getY() + 39, getX() + 96, getY() + 78);
                            if (getHitAnimationTime() == 7) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                getAttackBox().update(0,0,0,0);
                                stopTimer();
                            }
                        }
                        break;
                    case "long_spear":
                        if (isAttackLeft()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() - 27, getY() + 42, getX() + 15, getY() + 46);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(getX() - 39, getY() + 42, getX() + 12, getY() + 46);
                            if (getHitAnimationTime() == 7)
                                getAttackBox().update(getX() - 27, getY() + 42, getX() + 15, getY() + 46);
                            if (getHitAnimationTime() == 8)
                                getAttackBox().update(0,0,0,0);
                            if (getHitAnimationTime() == 9) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                stopTimer();
                            }
                        }
                        else if (isAttackRight()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() + 49, getY() + 42, getX() + 101, getY() + 46);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(getX() + 51, getY() + 43, getX() + 103, getY() + 46);
                            if (getHitAnimationTime() == 7)
                                getAttackBox().update(getX() + 49, getY() + 42, getX() + 101, getY() + 46);
                            if (getHitAnimationTime() == 8)
                                getAttackBox().update(0,0,0,0);
                            if (getHitAnimationTime() == 9) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                stopTimer();
                            }
                        }
                        else if (isAttackUp()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() + 38, getY() - 29, getX() + 42, getY() + 18);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(getX() + 38, getY() - 37, getX() + 42, getY() + 18);
                            if (getHitAnimationTime() == 7)
                                getAttackBox().update(getX() + 38, getY() - 29, getX() + 42, getY() + 18);
                            if (getHitAnimationTime() == 8)
                                getAttackBox().update(0,0,0,0);
                            if (getHitAnimationTime() == 9) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                stopTimer();
                            }
                        }
                        else if (isAttackDown()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() + 26, getY() + 49, getX() + 30, getY() + 95);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(getX() + 27, getY() + 51, getX() + 31, getY() + 103);
                            if (getHitAnimationTime() == 7)
                                getAttackBox().update(getX() + 26, getY() + 49, getX() + 30, getY() + 95);
                            if (getHitAnimationTime() == 8)
                                getAttackBox().update(0,0,0,0);
                            if (getHitAnimationTime() == 9) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                stopTimer();
                            }
                        }
                        break;
                    case "stick":
                        if (isAttackLeft()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() + 6, getY() + 43, getX() + 15, getY() + 45);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(getX() + 1, getY() + 43, getX() + 12, getY() + 45);
                            if (getHitAnimationTime() == 7)
                                getAttackBox().update(getX() + 6, getY() + 43, getX() + 15, getY() + 45);
                            if (getHitAnimationTime() == 8)
                                getAttackBox().update(0,0,0,0);
                            if (getHitAnimationTime() == 9) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                stopTimer();
                            }
                        }
                        else if (isAttackRight()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() + 48, getY() + 43, getX() + 58, getY() + 45);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(getX() + 52, getY() + 43, getX() + 62, getY() + 45);
                            if (getHitAnimationTime() == 7)
                                getAttackBox().update(getX() + 48, getY() + 43, getX() + 58, getY() + 45);
                            if (getHitAnimationTime() == 8)
                                getAttackBox().update(0,0,0,0);
                            if (getHitAnimationTime() == 9) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                stopTimer();
                            }
                        }
                        else if (isAttackUp()) {
                            if (getHitAnimationTime() == 5)
                                getAttackBox().update(getX() + 39, getY() + 2, getX() + 42, getY() + 19);
                            if (getHitAnimationTime() == 6)
                                getAttackBox().update(getX() + 39, getY(), getX() + 42, getY() + 19);
                            if (getHitAnimationTime() == 7)
                                getAttackBox().update(getX() + 39, getY() + 2, getX() + 42, getY() + 19);
                            if (getHitAnimationTime() == 8)
                                getAttackBox().update(0,0,0,0);
                            if (getHitAnimationTime() == 9) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                stopTimer();
                            }
                        }
                        else if (isAttackDown()) {
                            if (getHitAnimationTime() == 9) {
                                setHitAnimationTime(1);
                                setAttackLeft(false);
                                setAttackRight(false);
                                setAttackUp(false);
                                setAttackDown(false);
                                stopTimer();
                            }
                        }
                        break;
                    case "nothing":
                        if (getHitAnimationTime() == 7) {
                            setHitAnimationTime(1);
                            setAttackLeft(false);
                            setAttackRight(false);
                            setAttackUp(false);
                            setAttackDown(false);
                            getAttackBox().update(0,0,0,0);
                            stopTimer();
                        }
                        break;
                }
            }
        };
        knockbackTaskStarted = false;
        hitAnimationTaskStarted = false;
    }

    public void update(long window, String level) {
        if (!isDead()) {
            if (!level.equals("MainMenu") && !level.equals("Town") && !scrollMenu) {
                if (getKnockBackTime() >= 50) stopTimer();

                // Получение урона от мобов
                for (Mob mob : SingletonMobs.mobList) {
                    if (!(mob instanceof Player) && !mob.isDead() && AABB.AABBvsAABB(getHitbox(), mob.getAttackBox()) &&
                            !isDead() && !isImmortal()) {
                        if (mob.isAttackLeft()) setKnockBackDirection("left");
                        else if (mob.isAttackRight()) setKnockBackDirection("right");
                        else if (mob.isAttackUp()) setKnockBackDirection("up");
                        else if (mob.isAttackDown()) setKnockBackDirection("down");

                        takeDamage(mob.getDamage());
                        if (getHealth() <= 0) {
                            setDead(true);
                            getTimer().schedule(deathAnimationTask, 0, 120);
                        }
                        else if (!isKnockbackTaskStarted()) {
                            setImmortal(true);
                            getTimer().schedule(knockbackTask, 0, 10);
                        }
                    }
                }

                bodyAnimation = "player_stand_" + getMoveDirection();
                headAnimation = "HEAD_" + getHeadTexture() + "_" + getMoveDirection() + "_move_01";
                shouldersAnimation = "SHOULDERS_" + getShouldersTexture() + "_" + getMoveDirection() + "_move_01";
                overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_" + getMoveDirection() + "_move_01";
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_" + getMoveDirection() + "_move_01";
                beltAnimation = "BELT_" + getBeltTexture() + "_" + getMoveDirection() + "_move_01";
                handsAnimation = "HANDS_" + getHandsTexture() + "_" + getMoveDirection() + "_move_01";
                legsAnimation = "LEGS_" + getLegsTexture() + "_" + getMoveDirection() + "_move_01";
                feetAnimation = "FEET_" + getFeetTexture() + "_" + getMoveDirection() + "_move_01";

                if ((glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)) {
                    if (player_animation_move_left_i == 10) player_animation_move_left_i = 2;
                    if (player_animation_move_left_i == 3 || player_animation_move_left_i == 6) {
                        if (level.equals("tavern") || level.equals("forge")) stepSound.play(playerSounds.get("stepWood"));
                        else stepSound.play(playerSounds.get("stepStone"));
                    }
                    bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
                    headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_left_move_0" + player_animation_move_left_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_left_move_0" + player_animation_move_left_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_left_move_0" + player_animation_move_left_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_left_move_0" + player_animation_move_left_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_left_move_0" + player_animation_move_left_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_left_move_0" + player_animation_move_left_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_left_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveUpLeft();
                }
                else if ((glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)) {
                    if (player_animation_move_right_i == 10) player_animation_move_right_i = 2;
                    if (player_animation_move_right_i == 3 || player_animation_move_right_i == 6) {
                        if (level.equals("tavern") || level.equals("forge")) stepSound.play(playerSounds.get("stepWood"));
                        else stepSound.play(playerSounds.get("stepStone"));
                    }
                    bodyAnimation = "player_walk_right_0" + player_animation_move_right_i;
                    headAnimation =  "HEAD_" + getHeadTexture() + "_right_move_0" + player_animation_move_right_i;
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_move_0" + player_animation_move_right_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_right_move_0" + player_animation_move_right_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_right_move_0" + player_animation_move_right_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_right_move_0" + player_animation_move_right_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_right_move_0" + player_animation_move_right_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_right_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveUpRight();
                }
                else if ((glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)) {
                    if (player_animation_move_left_i == 10) player_animation_move_left_i = 2;
                    if (player_animation_move_left_i == 3 || player_animation_move_left_i == 6) {
                        if (level.equals("tavern") || level.equals("forge")) stepSound.play(playerSounds.get("stepWood"));
                        else stepSound.play(playerSounds.get("stepStone"));
                    }
                    bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
                    headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_left_move_0" + player_animation_move_left_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_left_move_0" + player_animation_move_left_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_left_move_0" + player_animation_move_left_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_left_move_0" + player_animation_move_left_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_left_move_0" + player_animation_move_left_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_left_move_0" + player_animation_move_left_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_left_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveDownLeft();
                }
                else if ((glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)) {
                    if (player_animation_move_right_i == 10) player_animation_move_right_i = 2;
                    if (player_animation_move_right_i == 3 || player_animation_move_right_i == 6) {
                        if (level.equals("tavern") || level.equals("forge")) stepSound.play(playerSounds.get("stepWood"));
                        else stepSound.play(playerSounds.get("stepStone"));
                    }
                    bodyAnimation = "player_walk_right_0" + player_animation_move_right_i;
                    headAnimation =  "HEAD_" + getHeadTexture() + "_right_move_0" + player_animation_move_right_i;
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_move_0" + player_animation_move_right_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_right_move_0" + player_animation_move_right_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_right_move_0" + player_animation_move_right_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_right_move_0" + player_animation_move_right_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_right_move_0" + player_animation_move_right_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_right_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveDownRight();
                }
                else if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
                    if (player_animation_move_left_i == 10) player_animation_move_left_i = 2;
                    if (player_animation_move_left_i == 3 || player_animation_move_left_i == 6) {
                        if (level.equals("tavern") || level.equals("forge")) stepSound.play(playerSounds.get("stepWood"));
                        else stepSound.play(playerSounds.get("stepStone"));
                    }
                    bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
                    headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_left_move_0" + player_animation_move_left_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_left_move_0" + player_animation_move_left_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_left_move_0" + player_animation_move_left_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_left_move_0" + player_animation_move_left_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_left_move_0" + player_animation_move_left_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_left_move_0" + player_animation_move_left_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_left_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveLeft();
                }
                else if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
                    if (player_animation_move_right_i == 10) player_animation_move_right_i = 2;
                    if (player_animation_move_right_i == 3 || player_animation_move_right_i == 6) {
                        if (level.equals("tavern") || level.equals("forge")) stepSound.play(playerSounds.get("stepWood"));
                        else stepSound.play(playerSounds.get("stepStone"));
                    }
                    bodyAnimation = "player_walk_right_0" + player_animation_move_right_i;
                    headAnimation =  "HEAD_" + getHeadTexture() + "_right_move_0" + player_animation_move_right_i;
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_move_0" + player_animation_move_right_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_right_move_0" + player_animation_move_right_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_right_move_0" + player_animation_move_right_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_right_move_0" + player_animation_move_right_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_right_move_0" + player_animation_move_right_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_right_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveRight();
                }
                else if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
                    if (player_animation_move_up_i == 10) player_animation_move_up_i = 2;
                    if (player_animation_move_up_i == 4 || player_animation_move_up_i == 8) {
                        if (level.equals("tavern") || level.equals("forge")) stepSound.play(playerSounds.get("stepWood"));
                        else stepSound.play(playerSounds.get("stepStone"));
                    }
                    bodyAnimation = "player_walk_up_0" + player_animation_move_up_i;
                    headAnimation =  "HEAD_" + getHeadTexture() + "_up_move_0" + player_animation_move_up_i;
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_up_move_0" + player_animation_move_up_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_up_move_0" + player_animation_move_up_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_up_move_0" + player_animation_move_up_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_up_move_0" + player_animation_move_up_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_up_move_0" + player_animation_move_up_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_up_move_0" + player_animation_move_up_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_up_move_0" + player_animation_move_up_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_up_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveUp();
                }
                else if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
                    if (player_animation_move_down_i == 10) player_animation_move_down_i = 2;
                    if (player_animation_move_down_i == 4 || player_animation_move_down_i == 8) {
                        if (level.equals("tavern") || level.equals("forge")) stepSound.play(playerSounds.get("stepWood"));
                        else stepSound.play(playerSounds.get("stepStone"));
                    }
                    bodyAnimation = "player_walk_down_0" + player_animation_move_down_i;
                    headAnimation =  "HEAD_" + getHeadTexture() + "_down_move_0" + player_animation_move_down_i;
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_down_move_0" + player_animation_move_down_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_down_move_0" + player_animation_move_down_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_down_move_0" + player_animation_move_down_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_down_move_0" + player_animation_move_down_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_down_move_0" + player_animation_move_down_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_down_move_0" + player_animation_move_down_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_down_move_0" + player_animation_move_down_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_down_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveDown();
                }
                if (isAttackLeft()) {
                    if (!hitAnimationTaskStarted) {
                        hitAnimationTaskStarted = true;
                        switch (weapon.getTexture()) {
                            case "longsword":
                                getTimer().schedule(hitAnimationTask, 0, 100);
                                break;
                            case "rapier":
                                getTimer().schedule(hitAnimationTask, 0, 90);
                                break;
                            case "long_spear":
                                getTimer().schedule(hitAnimationTask, 0, 120);
                                break;
                            case "spear":
                                getTimer().schedule(hitAnimationTask, 0, 85);
                                break;
                            case "stick":
                                getTimer().schedule(hitAnimationTask, 0, 80);
                                break;
                            case "nothing":
                                getTimer().schedule(hitAnimationTask, 0, 70);
                                break;
                        }
                    }
                    if (getHitAnimationTime() == 4) hitSound.play(playerSounds.get("swish"));
                    bodyAnimation = "player_" + getWeapon().getAttackType() + "_left_0" + getHitAnimationTime();
                    if (weapon.getTexture().equals("nothing")) weaponAnimation = "player_slash_left_0" + getHitAnimationTime();
                    else weaponAnimation = "weapon_" + getWeapon().getTexture() + "_left_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    headAnimation =  "HEAD_" + getHeadTexture() + "_left_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_left_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_left_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    beltAnimation = "BELT_" + getBeltTexture() + "_left_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    handsAnimation = "HANDS_" + getHandsTexture() + "_left_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    legsAnimation = "LEGS_" + getLegsTexture() + "_left_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    feetAnimation = "FEET_" + getFeetTexture() + "_left_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                }
                else if (isAttackRight()) {
                    if (!hitAnimationTaskStarted) {
                        hitAnimationTaskStarted = true;
                        switch (weapon.getTexture()) {
                            case "longsword":
                                getTimer().schedule(hitAnimationTask, 0, 100);
                                break;
                            case "rapier":
                                getTimer().schedule(hitAnimationTask, 0, 90);
                                break;
                            case "long_spear":
                                getTimer().schedule(hitAnimationTask, 0, 120);
                                break;
                            case "spear":
                                getTimer().schedule(hitAnimationTask, 0, 85);
                                break;
                            case "stick":
                                getTimer().schedule(hitAnimationTask, 0, 80);
                                break;
                            case "nothing":
                                getTimer().schedule(hitAnimationTask, 0, 70);
                                break;
                        }
                    }
                    if (getHitAnimationTime() == 4) hitSound.play(playerSounds.get("swish"));
                    bodyAnimation = "player_" + getWeapon().getAttackType() + "_right_0" + getHitAnimationTime();
                    if (weapon.getTexture().equals("nothing")) weaponAnimation = "player_slash_right_0" + getHitAnimationTime();
                    else weaponAnimation = "weapon_" + getWeapon().getTexture() + "_right_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    headAnimation =  "HEAD_" + getHeadTexture() + "_right_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_right_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_right_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    beltAnimation = "BELT_" + getBeltTexture() + "_right_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    handsAnimation = "HANDS_" + getHandsTexture() + "_right_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    legsAnimation = "LEGS_" + getLegsTexture() + "_right_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    feetAnimation = "FEET_" + getFeetTexture() + "_right_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                }
                else if (isAttackUp()) {
                    if (!hitAnimationTaskStarted) {
                        hitAnimationTaskStarted = true;
                        switch (weapon.getTexture()) {
                            case "longsword":
                                getTimer().schedule(hitAnimationTask, 0, 100);
                                break;
                            case "rapier":
                                getTimer().schedule(hitAnimationTask, 0, 90);
                                break;
                            case "long_spear":
                                getTimer().schedule(hitAnimationTask, 0, 120);
                                break;
                            case "spear":
                                getTimer().schedule(hitAnimationTask, 0, 85);
                                break;
                            case "stick":
                                getTimer().schedule(hitAnimationTask, 0, 80);
                                break;
                            case "nothing":
                                getTimer().schedule(hitAnimationTask, 0, 70);
                                break;
                        }
                    }
                    if (getHitAnimationTime() == 4) hitSound.play(playerSounds.get("swish"));
                    bodyAnimation = "player_" + getWeapon().getAttackType() + "_up_0" + getHitAnimationTime();
                    if (weapon.getTexture().equals("nothing")) weaponAnimation = "player_slash_up_0" + getHitAnimationTime();
                    else weaponAnimation = "weapon_" + getWeapon().getTexture() + "_up_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    headAnimation =  "HEAD_" + getHeadTexture() + "_up_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_up_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_up_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_up_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    beltAnimation = "BELT_" + getBeltTexture() + "_up_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    handsAnimation = "HANDS_" + getHandsTexture() + "_up_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    legsAnimation = "LEGS_" + getLegsTexture() + "_up_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    feetAnimation = "FEET_" + getFeetTexture() + "_up_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                }
                else if (isAttackDown()) {
                    if (!hitAnimationTaskStarted) {
                        hitAnimationTaskStarted = true;
                        switch (weapon.getTexture()) {
                            case "longsword":
                                getTimer().schedule(hitAnimationTask, 0, 100);
                                break;
                            case "rapier":
                                getTimer().schedule(hitAnimationTask, 0, 90);
                                break;
                            case "long_spear":
                                getTimer().schedule(hitAnimationTask, 0, 120);
                                break;
                            case "spear":
                                getTimer().schedule(hitAnimationTask, 0, 85);
                                break;
                            case "stick":
                                getTimer().schedule(hitAnimationTask, 0, 80);
                                break;
                            case "nothing":
                                getTimer().schedule(hitAnimationTask, 0, 70);
                                break;
                        }
                    }
                    if (getHitAnimationTime() == 4) hitSound.play(playerSounds.get("swish"));
                    bodyAnimation = "player_" + getWeapon().getAttackType() + "_down_0" + getHitAnimationTime();
                    if (weapon.getTexture().equals("nothing")) weaponAnimation = "player_slash_down_0" + getHitAnimationTime();
                    else weaponAnimation = "weapon_" + getWeapon().getTexture() + "_down_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    headAnimation =  "HEAD_" + getHeadTexture() + "_down_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_down_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_down_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_down_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    beltAnimation = "BELT_" + getBeltTexture() + "_down_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    handsAnimation = "HANDS_" + getHandsTexture() + "_down_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    legsAnimation = "LEGS_" + getLegsTexture() + "_down_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                    feetAnimation = "FEET_" + getFeetTexture() + "_down_" + getWeapon().getAttackType() + "_0" + getHitAnimationTime();
                }

                switch (getMoveDirection()) {
                    case "left":
                        getHitbox().update(getX() + 23, getY() + 15, getX() + 42, getY() + 59);
                        break;
                    case "right":
                        getHitbox().update(getX() + 21, getY() + 15, getX() + 40, getY() + 59);
                        break;
                    case "up":
                        getHitbox().update(getX() + 20, getY() + 15, getX() + 43, getY() + 60);
                        break;
                    case "down":
                        getHitbox().update(getX() + 20, getY() + 15, getX() + 43, getY() + 61);
                        break;
                }
                getCollisionBox().update(getX() + 17, getY() + 52, getX() + 46, getY() + 60);
                reArmor();
            }
            else if (level.equals("Town")) {
                bodyAnimation = "player_stand_" + getMoveDirection();
                headAnimation = "HEAD_" + getHeadTexture() + "_" + getMoveDirection() + "_move_01";
                shouldersAnimation = "SHOULDERS_" + getShouldersTexture() + "_" + getMoveDirection() + "_move_01";
                overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_" + getMoveDirection() + "_move_01";
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_" + getMoveDirection() + "_move_01";
                beltAnimation = "BELT_" + getBeltTexture() + "_" + getMoveDirection() + "_move_01";
                handsAnimation = "HANDS_" + getHandsTexture() + "_" + getMoveDirection() + "_move_01";
                legsAnimation = "LEGS_" + getLegsTexture() + "_" + getMoveDirection() + "_move_01";
                feetAnimation = "FEET_" + getFeetTexture() + "_" + getMoveDirection() + "_move_01";

                if (!dialogBubble && !scrollMenu) {
                    if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
                        if (player_animation_move_left_i == 10) player_animation_move_left_i = 2;
                        if (player_animation_move_left_i == 3 || player_animation_move_left_i == 6) stepSound.play(playerSounds.get("stepStone"));
                        bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
                        headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
                        shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
                        overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_left_move_0" + player_animation_move_left_i;
                        torsoAnimation = "TORSO_" + getTorsoTexture() + "_left_move_0" + player_animation_move_left_i;
                        beltAnimation = "BELT_" + getBeltTexture() + "_left_move_0" + player_animation_move_left_i;
                        handsAnimation = "HANDS_" + getHandsTexture() + "_left_move_0" + player_animation_move_left_i;
                        legsAnimation = "LEGS_" + getLegsTexture() + "_left_move_0" + player_animation_move_left_i;
                        feetAnimation = "FEET_" + getFeetTexture() + "_left_move_0" + player_animation_move_left_i;
                        if (player_animation_move_g == 8) {
                            player_animation_move_left_i++;
                            player_animation_move_g = 0;
                        }
                        player_animation_move_g++;
                        moveLeft();
                        if (!(getX() <= 290) && !(getX() > 1186)) {
                            glTranslated(1, 0, 0);
                            forPlacingCamera--;
                        }
                        if (getX() + 40 > 1410 && getX() + 40 < 1503) {
                            if (getY() != 192) setY(getY() + 1);
                        }
                    }
                    else if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
                        if (player_animation_move_right_i == 10) player_animation_move_right_i = 2;
                        if (player_animation_move_right_i == 3 || player_animation_move_right_i == 6) stepSound.play(playerSounds.get("stepStone"));
                        bodyAnimation = "player_walk_right_0" + player_animation_move_right_i;
                        headAnimation =  "HEAD_" + getHeadTexture() + "_right_move_0" + player_animation_move_right_i;
                        shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_move_0" + player_animation_move_right_i;
                        overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                        torsoAnimation = "TORSO_" + getTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                        beltAnimation = "BELT_" + getBeltTexture() + "_right_move_0" + player_animation_move_right_i;
                        handsAnimation = "HANDS_" + getHandsTexture() + "_right_move_0" + player_animation_move_right_i;
                        legsAnimation = "LEGS_" + getLegsTexture() + "_right_move_0" + player_animation_move_right_i;
                        feetAnimation = "FEET_" + getFeetTexture() + "_right_move_0" + player_animation_move_right_i;
                        if (player_animation_move_g == 8) {
                            player_animation_move_right_i++;
                            player_animation_move_g = 0;
                        }
                        player_animation_move_g++;
                        moveRight();
                        if (!(getX() > 1186) && !(getX() <= 290)) {
                            glTranslated(-1, 0, 0);
                            forPlacingCamera++;
                        }
                        if (getX() + 40 > 1410 && getX() + 40 < 1503) {
                            if (getY() != 100) setY(getY() - 1);
                        }
                    }
                }
            }
            else if (level.equals("MainMenu")) {
                if (mainMenuDirection) {
                    if (player_animation_move_right_i == 10) player_animation_move_right_i = 2;
                    bodyAnimation = "player_walk_right_0" + player_animation_move_right_i;
                    headAnimation = "HEAD_" + getHeadTexture() + "_right_move_0" + player_animation_move_right_i;
                    shouldersAnimation = "SHOULDERS_" + getShouldersTexture() + "_right_move_0" + player_animation_move_right_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_right_move_0" + player_animation_move_right_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_right_move_0" + player_animation_move_right_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_right_move_0" + player_animation_move_right_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_right_move_0" + player_animation_move_right_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_right_move_0" + player_animation_move_right_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_right_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveRight();
                }
                else if (!mainMenuDirection) {
                    if (player_animation_move_left_i == 10) player_animation_move_left_i = 2;
                    bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
                    headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
                    shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
                    overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_right_move_0" + player_animation_move_left_i;
                    torsoAnimation = "TORSO_" + getTorsoTexture() + "_left_move_0" + player_animation_move_left_i;
                    beltAnimation = "BELT_" + getBeltTexture() + "_left_move_0" + player_animation_move_left_i;
                    handsAnimation = "HANDS_" + getHandsTexture() + "_left_move_0" + player_animation_move_left_i;
                    legsAnimation = "LEGS_" + getLegsTexture() + "_left_move_0" + player_animation_move_left_i;
                    feetAnimation = "FEET_" + getFeetTexture() + "_left_move_0" + player_animation_move_left_i;
                    if (player_animation_move_g == 8) {
                        player_animation_move_left_i++;
                        player_animation_move_g = 0;
                    }
                    player_animation_move_g++;
                    moveLeft();
                }
            }
        }
        else {
            bodyAnimation = "player_hurt_0" + getDeathAnimationTime();
            headAnimation = "HEAD_" + getHeadTexture() + "_hurt_0" + getDeathAnimationTime();
            shouldersAnimation = "SHOULDERS_" + getShouldersTexture() + "_hurt_0" + getDeathAnimationTime();
            overTorsoAnimation = "TORSO_" + getOverTorsoTexture() + "_hurt_0" + getDeathAnimationTime();
            torsoAnimation = "TORSO_" + getTorsoTexture() + "_hurt_0" + getDeathAnimationTime();
            beltAnimation = "BELT_" + getBeltTexture() + "_hurt_0" + getDeathAnimationTime();
            handsAnimation = "HANDS_" + getHandsTexture() + "_hurt_0" + getDeathAnimationTime();
            legsAnimation = "LEGS_" + getLegsTexture() + "_hurt_0" + getDeathAnimationTime();
            feetAnimation = "FEET_" + getFeetTexture() + "_hurt_0" + getDeathAnimationTime();
        }
    }

    public void reArmor() {
        this.setArmor(head.getDefense() + shoulders.getDefense() + overTorso.getDefense() + torso.getDefense() + belt.getDefense() +
                hands.getDefense() + legs.getDefense() + feet.getDefense());
    }

    public Armor getArmorType(Armor armor) {
        switch (armor.getType()) {
            case "head": return head;
            case "shoulders": return shoulders;
            case "overTorso": return overTorso;
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
            case "overTorso":
                overTorso = armor;
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

    public Weapon getWeapon() { return weapon; }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public int getForPlacingCamera() { return forPlacingCamera; }

    public void setForPlacingCamera(int forPlacingCamera) { this.forPlacingCamera = forPlacingCamera; }

    public String getHeadTexture() { return head.getTexture(); }

    public String getShouldersTexture() { return shoulders.getTexture(); }

    public String getOverTorsoTexture() { return overTorso.getTexture(); }

    public String getTorsoTexture() { return torso.getTexture(); }

    public String getBeltTexture() { return belt.getTexture(); }

    public String getHandsTexture() { return hands.getTexture(); }

    public String getLegsTexture() { return legs.getTexture(); }

    public String getFeetTexture() { return feet.getTexture(); }

    public String getBodyAnimation() { return bodyAnimation; }

    public String getWeaponAnimation() { return weaponAnimation; }

    public String getHeadAnimation() { return headAnimation; }

    public String getShouldersAnimation() { return shouldersAnimation; }

    public String getOverTorsoAnimation() { return overTorsoAnimation; }

    public String getTorsoAnimation() { return torsoAnimation; }

    public String getBeltAnimation() { return beltAnimation; }

    public String getHandsAnimation() { return handsAnimation; }

    public String getLegsAnimation() { return legsAnimation; }

    public String getFeetAnimation() { return feetAnimation; }

    public int getMoney() { return money; }

    public void setMoney(int money) { this.money = money; }

    public int getKeys() { return keys; }

    public void setKeys(int keys) { this.keys = keys; }

    public boolean isDialogBubbleChoice() { return dialogBubbleChoice; }

    public void setDialogBubbleChoice(boolean dialogBubbleChoice) {
        this.dialogBubbleChoice = dialogBubbleChoice;
        selectionMenuSound.play(playerSounds.get("selectionClick"));
    }

    public boolean isDialogBubble() { return dialogBubble; }

    public void setDialogBubble(boolean dialogBubble) { this.dialogBubble = dialogBubble; }

    public boolean isScrollMenu() { return scrollMenu; }

    public void setScrollMenu(boolean scrollMenu) {
        this.scrollMenu = scrollMenu;
        openMenuSound.play(playerSounds.get("openMenu"));
    }

    public String getMenuChoice() { return menuChoice; }

    public void setMenuChoice(String menuChoice) {
        this.menuChoice = menuChoice;
        selectionMenuSound.play(playerSounds.get("selectionClick"));
    }

    public boolean isKnockbackTaskStarted() { return knockbackTaskStarted; }

    public void setMainMenuDirection(boolean mainMenuDirection) { this.mainMenuDirection = mainMenuDirection; }
}