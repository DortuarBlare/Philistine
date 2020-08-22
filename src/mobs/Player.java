package mobs;

import content.AudioMaster;
import content.Source;
import content.Storage;
import math.AABB;
import objects.Armor;
import objects.Weapon;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.opengl.GL11.glTranslated;

public class Player extends Mob {
    private HashMap<String, Integer> playerSounds;
    private Source stepSound, hitSound, choiceSound, menuSound;
    private Armor head, shoulders, torso, belt, hands, legs, feet;
    private Weapon weapon;
    private AABB attackBox;
    private int money, keys;
    private String bodyAnimation, weaponAnimation, headAnimation, shouldersAnimation, torsoAnimation, beltAnimation, handsAnimation, legsAnimation, feetAnimation;
    private boolean isAttackRight = false, isAttackLeft = false, isAttackUp = false, isAttackDown = false;
    private int player_animation_move_left_i = 2, player_animation_move_right_i = 2, player_animation_move_up_i = 2, player_animation_move_down_i = 2;
    private int player_animation_move_g = 0, player_animation_death_i = 1, player_animation_death_g = 0;
    private int forPlacingCamera = 0;
    private boolean choiceBubble = false;
    private boolean scrollMenu = false;
    private boolean yes = false;
    private String menuChoice = "Resume";
    private int knockbackTime = 0, hitAnimationTime = 1;
    private boolean hitAnimationTaskStarted = false, knockbackTaskStarted = false;
    private String knockbackDirection;
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            setImmortal(true);
            knockbackTaskStarted = true;
            if (knockbackDirection.equals("left")) knockBackRight();
            else if (knockbackDirection.equals("right")) knockBackLeft();
            else if (knockbackDirection.equals("up")) knockBackDown();
            else if (knockbackDirection.equals("down")) knockBackUp();
            knockbackTime++;
            if (knockbackTime >= 15) stopTimer();
        }
    };
    private TimerTask hitAnimationTask = new TimerTask() {
        @Override
        public void run() {
            hitAnimationTime++;
            switch (weapon.getAttackType()) {
                case "slash": {
                    if (hitAnimationTime == 7) {
                        hitAnimationTime = 1;
                        isAttackLeft = isAttackRight = isAttackUp = isAttackDown = false;
                        getAttackBox().update(0, 0, 0, 0);
                        stopTimer();
                    }
                    break;
                }
                case "thrust": {
                    if (hitAnimationTime == 9) {
                        hitAnimationTime = 1;
                        isAttackLeft = isAttackRight = isAttackUp = isAttackDown = false;
                        stopTimer();
                    }
                    break;
                }
            }
            switch (weapon.getTexture()) {
                case "rapier":
                case "longsword":
                    if (isAttackLeft) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() - 49, getY() + 21, getX() + 17, getY() + 45);
                    }
                    else if (isAttackRight) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 47, getY() + 21, getX() + 113, getY() + 45);
                    }
                    else if (isAttackUp) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() - 11, getY() - 4, getX() + 70, getY() + 33);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 27, getY() - 1, getX() + 95, getY() + 38);

                    }
                    else if (isAttackDown) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() - 9, getY() + 44, getX() + 72, getY() + 81);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 28, getY() + 39, getX() + 96, getY() + 78);
                    }
                    break;
                case "long_spear":
                    if (isAttackLeft) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() - 27, getY() + 42, getX() + 15, getY() + 46);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() - 39, getY() + 42, getX() + 12, getY() + 46);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() - 27, getY() + 42, getX() + 15, getY() + 46);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    else if (isAttackRight) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 49, getY() + 42, getX() + 101, getY() + 46);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 51, getY() + 43, getX() + 103, getY() + 46);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() + 49, getY() + 42, getX() + 101, getY() + 46);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    else if (isAttackUp) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 38, getY() - 29, getX() + 42, getY() + 18);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 38, getY() - 37, getX() + 42, getY() + 18);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() + 38, getY() - 29, getX() + 42, getY() + 18);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    else if (isAttackDown) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 26, getY() + 49, getX() + 30, getY() + 95);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 27, getY() + 51, getX() + 31, getY() + 103);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() + 26, getY() + 49, getX() + 30, getY() + 95);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    break;
                case "spear":
                    if (isAttackLeft) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 3, getY() + 42, getX() + 15, getY() + 45);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX(), getY() + 42, getX() + 12, getY() + 46);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() + 3, getY() + 42, getX() + 15, getY() + 45);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    else if (isAttackRight) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 48, getY() + 42, getX() + 60, getY() + 46);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 51, getY() + 42, getX() + 63, getY() + 46);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() + 48, getY() + 42, getX() + 60, getY() + 46);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    else if (isAttackUp) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 38, getY() + 5, getX() + 42, getY() + 19);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 38, getY() + 1, getX() + 42, getY() + 19);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() + 38, getY() + 5, getX() + 42, getY() + 19);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    else if (isAttackDown) {

                    }
                    break;
                case "stick":
                    if (isAttackLeft) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 6, getY() + 43, getX() + 15, getY() + 45);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 1, getY() + 43, getX() + 12, getY() + 45);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() + 6, getY() + 43, getX() + 15, getY() + 45);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    else if (isAttackRight) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 48, getY() + 43, getX() + 58, getY() + 45);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 52, getY() + 43, getX() + 62, getY() + 45);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() + 48, getY() + 43, getX() + 58, getY() + 45);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    else if (isAttackUp) {
                        if (hitAnimationTime == 5)
                            getAttackBox().update(getX() + 39, getY() + 2, getX() + 42, getY() + 19);
                        if (hitAnimationTime == 6)
                            getAttackBox().update(getX() + 39, getY(), getX() + 42, getY() + 19);
                        if (hitAnimationTime == 7)
                            getAttackBox().update(getX() + 39, getY() + 2, getX() + 42, getY() + 19);
                        if (hitAnimationTime == 8)
                            getAttackBox().update(0,0,0,0);
                    }
                    else if (isAttackDown) {

                    }
                    break;
            }
        }
    };

    public Player(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
        setMoveDirection("down");
        attackBox = new AABB();
        money = 10;
        keys = 1;
        weapon = new Weapon("long_spear", "thrust", 10, new AABB());
        head = new Armor("nothing", "head", 0, true);
        shoulders = new Armor("plate_shoulderPads", "shoulders", 5, true);
        torso = new Armor("plate_armor", "torso", 5, true);
        belt = new Armor("leather", "belt", 2, true);
        hands = new Armor("leather_bracers", "hands", 2, true);
        legs = new Armor("plate_pants", "legs", 5, true);
        feet = new Armor("plate_shoes", "feet", 5, true);
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
        choiceSound = new Source(0);
        menuSound = new Source(0);
        for (int i = 0, id = 0; i < Storage.playerSoundString.length; i++)
            playerSounds.put(Storage.playerSoundString[i], id = AudioMaster.loadSound("sounds/" + Storage.playerSoundString[i]));
    }

    public void stopTimer() {
        knockbackTime = 0;
        hitAnimationTime = 1;
        hitAnimationTaskStarted = false;
        isAttackLeft = isAttackRight = isAttackUp = isAttackDown = false;
        setImmortal(false);
        getAttackBox().update(0,0,0,0);
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        knockbackTask = new TimerTask() {
            @Override
            public void run() {
                setImmortal(true);
                knockbackTaskStarted = true;
                if (knockbackDirection.equals("left")) moveLeft();
                else if (knockbackDirection.equals("right")) moveRight();
                else if (knockbackDirection.equals("up")) moveUp();
                else if (knockbackDirection.equals("down")) moveDown();
                knockbackTime++;
                if (knockbackTime >= 15) stopTimer();
            }
        };
        hitAnimationTask = new TimerTask() {
            @Override
            public void run() {
                hitAnimationTime++;
                switch (weapon.getAttackType()) {
                    case "slash": {
                        if (hitAnimationTime == 7) {
                            hitAnimationTime = 1;
                            isAttackLeft = isAttackRight = isAttackUp = isAttackDown = false;
                            getAttackBox().update(0, 0, 0, 0);
                            stopTimer();
                        }
                        break;
                    }
                    case "thrust": {
                        if (hitAnimationTime == 9) {
                            hitAnimationTime = 1;
                            isAttackLeft = isAttackRight = isAttackUp = isAttackDown = false;
                            stopTimer();
                        }
                        break;
                    }
                }
                switch (weapon.getTexture()) {
                    case "rapier":
                    case "longsword":
                        if (isAttackLeft) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() - 49, getY() + 21, getX() + 17, getY() + 45);
                        }
                        else if (isAttackRight) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 47, getY() + 21, getX() + 113, getY() + 45);
                        }
                        else if (isAttackUp) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() - 11, getY() - 4, getX() + 70, getY() + 33);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 27, getY() - 1, getX() + 95, getY() + 38);

                        }
                        else if (isAttackDown) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() - 9, getY() + 44, getX() + 72, getY() + 81);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 28, getY() + 39, getX() + 96, getY() + 78);
                        }
                        break;
                    case "long_spear":
                        if (isAttackLeft) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() - 27, getY() + 42, getX() + 15, getY() + 46);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() - 39, getY() + 42, getX() + 12, getY() + 46);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() - 27, getY() + 42, getX() + 15, getY() + 46);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        else if (isAttackRight) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 49, getY() + 42, getX() + 101, getY() + 46);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 51, getY() + 43, getX() + 103, getY() + 46);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() + 49, getY() + 42, getX() + 101, getY() + 46);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        else if (isAttackUp) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 38, getY() - 29, getX() + 42, getY() + 18);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 38, getY() - 37, getX() + 42, getY() + 18);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() + 38, getY() - 29, getX() + 42, getY() + 18);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        else if (isAttackDown) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 26, getY() + 49, getX() + 30, getY() + 95);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 27, getY() + 51, getX() + 31, getY() + 103);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() + 26, getY() + 49, getX() + 30, getY() + 95);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        break;
                    case "spear":
                        if (isAttackLeft) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 3, getY() + 42, getX() + 15, getY() + 45);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX(), getY() + 42, getX() + 12, getY() + 46);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() + 3, getY() + 42, getX() + 15, getY() + 45);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        else if (isAttackRight) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 48, getY() + 42, getX() + 60, getY() + 46);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 51, getY() + 42, getX() + 63, getY() + 46);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() + 48, getY() + 42, getX() + 60, getY() + 46);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        else if (isAttackUp) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 38, getY() + 5, getX() + 42, getY() + 19);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 38, getY() + 1, getX() + 42, getY() + 19);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() + 38, getY() + 5, getX() + 42, getY() + 19);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        else if (isAttackDown) {

                        }
                        break;
                    case "stick":
                        if (isAttackLeft) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 6, getY() + 43, getX() + 15, getY() + 45);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 1, getY() + 43, getX() + 12, getY() + 45);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() + 6, getY() + 43, getX() + 15, getY() + 45);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        else if (isAttackRight) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 48, getY() + 43, getX() + 58, getY() + 45);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 52, getY() + 43, getX() + 62, getY() + 45);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() + 48, getY() + 43, getX() + 58, getY() + 45);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        else if (isAttackUp) {
                            if (hitAnimationTime == 5)
                                getAttackBox().update(getX() + 39, getY() + 2, getX() + 42, getY() + 19);
                            if (hitAnimationTime == 6)
                                getAttackBox().update(getX() + 39, getY(), getX() + 42, getY() + 19);
                            if (hitAnimationTime == 7)
                                getAttackBox().update(getX() + 39, getY() + 2, getX() + 42, getY() + 19);
                            if (hitAnimationTime == 8)
                                getAttackBox().update(0,0,0,0);
                        }
                        else if (isAttackDown) {

                        }
                        break;
                }
            }
        };
    }

    public void update(long window) {
        if (!isDead()) {
            bodyAnimation = "player_stand_" + getMoveDirection();
            headAnimation = "HEAD_" + getHeadTexture() + "_" + getMoveDirection() + "_move_01";
            shouldersAnimation = "SHOULDERS_" + getShouldersTexture() + "_" + getMoveDirection() + "_move_01";
            torsoAnimation = "TORSO_" + getTorsoTexture() + "_" + getMoveDirection() + "_move_01";
            beltAnimation = "BELT_" + getBeltTexture() + "_" + getMoveDirection() + "_move_01";
            handsAnimation = "HANDS_" + getHandsTexture() + "_" + getMoveDirection() + "_move_01";
            legsAnimation = "LEGS_" + getLegsTexture() + "_" + getMoveDirection() + "_move_01";
            feetAnimation = "FEET_" + getFeetTexture() + "_" + getMoveDirection() + "_move_01";

            if ( (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) ) {
                if (player_animation_move_left_i == 10) player_animation_move_left_i = 2;
                if (player_animation_move_left_i == 3 || player_animation_move_left_i == 6) stepSound.play(playerSounds.get("stepStone"));
                bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
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
            else if ( (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) ) {
                if (player_animation_move_right_i == 10) player_animation_move_right_i = 2;
                if (player_animation_move_right_i == 3 || player_animation_move_right_i == 6) stepSound.play(playerSounds.get("stepStone"));
                bodyAnimation = "player_walk_right_0" + player_animation_move_right_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_right_move_0" + player_animation_move_right_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_move_0" + player_animation_move_right_i;
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
            else if ( (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) ) {
                if (player_animation_move_left_i == 10) player_animation_move_left_i = 2;
                if (player_animation_move_left_i == 3 || player_animation_move_left_i == 6) stepSound.play(playerSounds.get("stepStone"));
                bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
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
            else if ( (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) ) {
                if (player_animation_move_right_i == 10) player_animation_move_right_i = 2;
                if (player_animation_move_right_i == 3 || player_animation_move_right_i == 6) stepSound.play(playerSounds.get("stepStone"));
                bodyAnimation = "player_walk_right_0" + player_animation_move_right_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_right_move_0" + player_animation_move_right_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_move_0" + player_animation_move_right_i;
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
                if (player_animation_move_left_i == 3 || player_animation_move_left_i == 6) stepSound.play(playerSounds.get("stepStone"));
                bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
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
                if (player_animation_move_right_i == 3 || player_animation_move_right_i == 6) stepSound.play(playerSounds.get("stepStone"));
                bodyAnimation = "player_walk_right_0" + player_animation_move_right_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_right_move_0" + player_animation_move_right_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_move_0" + player_animation_move_right_i;
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
                if (player_animation_move_up_i == 4 || player_animation_move_up_i == 8) stepSound.play(playerSounds.get("stepStone"));
                bodyAnimation = "player_walk_up_0" + player_animation_move_up_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_up_move_0" + player_animation_move_up_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_up_move_0" + player_animation_move_up_i;
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
                if (player_animation_move_down_i == 4 || player_animation_move_down_i == 8) stepSound.play(playerSounds.get("stepStone"));
                bodyAnimation = "player_walk_down_0" + player_animation_move_down_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_down_move_0" + player_animation_move_down_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_down_move_0" + player_animation_move_down_i;
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
            if (isAttackLeft) {
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
                    }
                }
                if (hitAnimationTime == 4) hitSound.play(playerSounds.get("swish"));
                bodyAnimation = "player_" + getWeapon().getAttackType() + "_left_0" + hitAnimationTime;
                weaponAnimation = "weapon_" + getWeapon().getTexture() + "_left_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                headAnimation =  "HEAD_" + getHeadTexture() + "_left_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_left_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                beltAnimation = "BELT_" + getBeltTexture() + "_left_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                handsAnimation = "HANDS_" + getHandsTexture() + "_left_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                legsAnimation = "LEGS_" + getLegsTexture() + "_left_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                feetAnimation = "FEET_" + getFeetTexture() + "_left_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
            }
            else if (isAttackRight) {
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
                    }
                }
                if (hitAnimationTime == 4) hitSound.play(playerSounds.get("swish"));
                bodyAnimation = "player_" + getWeapon().getAttackType() + "_right_0" + hitAnimationTime;
                weaponAnimation = "weapon_" + getWeapon().getTexture() + "_right_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                headAnimation =  "HEAD_" + getHeadTexture() + "_right_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_right_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                beltAnimation = "BELT_" + getBeltTexture() + "_right_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                handsAnimation = "HANDS_" + getHandsTexture() + "_right_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                legsAnimation = "LEGS_" + getLegsTexture() + "_right_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                feetAnimation = "FEET_" + getFeetTexture() + "_right_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
            }
            else if (isAttackUp) {
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
                    }
                }
                if (hitAnimationTime == 4) hitSound.play(playerSounds.get("swish"));
                bodyAnimation = "player_" + getWeapon().getAttackType() + "_up_0" + hitAnimationTime;
                weaponAnimation = "weapon_" + getWeapon().getTexture() + "_up_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                headAnimation =  "HEAD_" + getHeadTexture() + "_up_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_up_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_up_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                beltAnimation = "BELT_" + getBeltTexture() + "_up_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                handsAnimation = "HANDS_" + getHandsTexture() + "_up_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                legsAnimation = "LEGS_" + getLegsTexture() + "_up_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                feetAnimation = "FEET_" + getFeetTexture() + "_up_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
            }
            else if (isAttackDown) {
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
                    }
                }
                if (hitAnimationTime == 4) hitSound.play(playerSounds.get("swish"));
                bodyAnimation = "player_" + getWeapon().getAttackType() + "_down_0" + hitAnimationTime;
                weaponAnimation = "weapon_" + getWeapon().getTexture() + "_down_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                headAnimation =  "HEAD_" + getHeadTexture() + "_down_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_down_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_down_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                beltAnimation = "BELT_" + getBeltTexture() + "_down_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                handsAnimation = "HANDS_" + getHandsTexture() + "_down_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                legsAnimation = "LEGS_" + getLegsTexture() + "_down_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
                feetAnimation = "FEET_" + getFeetTexture() + "_down_" + getWeapon().getAttackType() + "_0" + hitAnimationTime;
            }

            if (getMoveDirection().equals("left")) getHitbox().update(getX() + 23, getY() + 15, getX() + 42, getY() + 59);
            else if (getMoveDirection().equals("right")) getHitbox().update(getX() + 21, getY() + 15, getX() + 40, getY() + 59);
            else if (getMoveDirection().equals("up")) getHitbox().update(getX() + 20, getY() + 15, getX() + 43, getY() + 60);
            else if (getMoveDirection().equals("down")) getHitbox().update(getX() + 20, getY() + 15, getX() + 43, getY() + 61);
            getCollisionBox().update(getX() + 17, getY() + 46, getX() + 46, getY() + 61);
            reArmor();
        }
        else {
            bodyAnimation = "player_hurt_0" + player_animation_death_i;
            headAnimation = "HEAD_" + getHeadTexture() + "_hurt_0" + player_animation_death_i;
            shouldersAnimation = "SHOULDERS_" + getShouldersTexture() + "_hurt_0" + player_animation_death_i;
            torsoAnimation = "TORSO_" + getTorsoTexture() + "_hurt_0" + player_animation_death_i;
            beltAnimation = "BELT_" + getBeltTexture() + "_hurt_0" + player_animation_death_i;
            handsAnimation = "HANDS_" + getHandsTexture() + "_hurt_0" + player_animation_death_i;
            legsAnimation = "LEGS_" + getLegsTexture() + "_hurt_0" + player_animation_death_i;
            feetAnimation = "FEET_" + getFeetTexture() + "_hurt_0" + player_animation_death_i;
            if (player_animation_death_i != 6) {
                if (player_animation_death_g == 8) {
                    player_animation_death_i++;
                    player_animation_death_g = 0;
                }
                player_animation_death_g++;
            }
            getTimer().cancel();
            getTimer().purge();
        }
    }

    public void updateForMainMenu(String direction) {
        if (direction.equals("right")) {
            if (player_animation_move_right_i == 10) player_animation_move_right_i = 2;
            bodyAnimation = "player_walk_right_0" + player_animation_move_right_i;
            headAnimation = "HEAD_" + getHeadTexture() + "_right_move_0" + player_animation_move_right_i;
            shouldersAnimation = "SHOULDERS_" + getShouldersTexture() + "_right_move_0" + player_animation_move_right_i;
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
        else if (direction.equals("left")) {
            if (player_animation_move_left_i == 10) player_animation_move_left_i = 2;
            bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
            headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
            shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
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

    public void updateForTown(long window) {
        bodyAnimation = "player_stand_" + getMoveDirection();
        headAnimation = "HEAD_" + getHeadTexture() + "_" + getMoveDirection() + "_move_01";
        shouldersAnimation = "SHOULDERS_" + getShouldersTexture() + "_" + getMoveDirection() + "_move_01";
        torsoAnimation = "TORSO_" + getTorsoTexture() + "_" + getMoveDirection() + "_move_01";
        beltAnimation = "BELT_" + getBeltTexture() + "_" + getMoveDirection() + "_move_01";
        handsAnimation = "HANDS_" + getHandsTexture() + "_" + getMoveDirection() + "_move_01";
        legsAnimation = "LEGS_" + getLegsTexture() + "_" + getMoveDirection() + "_move_01";
        feetAnimation = "FEET_" + getFeetTexture() + "_" + getMoveDirection() + "_move_01";

        if (!choiceBubble) {
            if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
                if (player_animation_move_left_i == 10) player_animation_move_left_i = 2;
                if (player_animation_move_left_i == 3 || player_animation_move_left_i == 6) stepSound.play(playerSounds.get("stepStone"));
                bodyAnimation = "player_walk_left_0" + player_animation_move_left_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_left_move_0" + player_animation_move_left_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_move_0" + player_animation_move_left_i;
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

    public void reArmor() {
        this.setArmor(head.getDefense() + shoulders.getDefense() + torso.getDefense() + belt.getDefense() +
                hands.getDefense() + legs.getDefense() + feet.getDefense());
    }

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

    public Weapon getWeapon() { return weapon; }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public int getForPlacingCamera() { return forPlacingCamera; }

    public void setForPlacingCamera(int forPlacingCamera) { this.forPlacingCamera = forPlacingCamera; }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public AABB getAttackBox() { return attackBox; }

    public int getKnockbackTime() { return knockbackTime; }

    public void setKnockbackDirection(String knockbackDirection) { this.knockbackDirection = knockbackDirection; }

    public String getHeadTexture() { return head.getTexture(); }

    public String getShouldersTexture() { return shoulders.getTexture(); }

    public String getTorsoTexture() { return torso.getTexture(); }

    public String getBeltTexture() { return belt.getTexture(); }

    public String getHandsTexture() { return hands.getTexture(); }

    public String getLegsTexture() { return legs.getTexture(); }

    public String getFeetTexture() { return feet.getTexture(); }

    public String getBodyAnimation() { return bodyAnimation; }

    public String getWeaponAnimation() { return weaponAnimation; }

    public String getHeadAnimation() { return headAnimation; }

    public String getShouldersAnimation() { return shouldersAnimation; }

    public String getTorsoAnimation() { return torsoAnimation; }

    public String getBeltAnimation() { return beltAnimation; }

    public String getHandsAnimation() { return handsAnimation; }

    public String getLegsAnimation() { return legsAnimation; }

    public String getFeetAnimation() { return feetAnimation; }

    public boolean isAttackLeft() { return isAttackLeft; }

    public void setAttackLeft(boolean isAttackLeft) { this.isAttackLeft = isAttackLeft; }

    public boolean isAttackRight() { return isAttackRight; }

    public void setAttackRight(boolean isAttackRight) { this.isAttackRight = isAttackRight; }

    public boolean isAttackUp() { return isAttackUp; }

    public void setAttackUp(boolean isAttackUp) { this.isAttackUp = isAttackUp; }

    public boolean isAttackDown() { return isAttackDown; }

    public void setAttackDown(boolean isAttackDown) { this.isAttackDown = isAttackDown; }

    public int getMoney() { return money; }

    public void setMoney(int money) { this.money = money; }

    public int getKeys() { return keys; }

    public void setKeys(int keys) { this.keys = keys; }

    public boolean isYes() { return yes; }

    public void setYes(boolean yes) {
        this.yes = yes;
        choiceSound.play(playerSounds.get("selectionClick"));
    }

    public boolean isChoiceBubble() { return choiceBubble; }

    public void setChoiceBubble(boolean choiceBubble) { this.choiceBubble = choiceBubble; }

    public boolean isScrollMenu() { return scrollMenu; }

    public void setScrollMenu(boolean scrollMenu) {
        this.scrollMenu = scrollMenu;
        menuSound.play(playerSounds.get("openMenu"));
    }

    public String getMenuChoice() { return menuChoice; }

    public void setMenuChoice(String menuChoice) {
        this.menuChoice = menuChoice;
        choiceSound.play(playerSounds.get("selectionClick"));
    }

    public boolean isKnockbackTaskStarted() { return knockbackTaskStarted; }

    public void setKnockbackTaskStarted(boolean knockbackTaskStarted) { this.knockbackTaskStarted = knockbackTaskStarted; }
}