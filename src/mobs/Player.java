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

public class Player extends Mob {
    private HashMap<String, Integer> playerSounds;
    private Source stepSound, hitSound;
    private Armor head, shoulders, torso, belt, hands, legs, feet;
    private Weapon weapon;
    private AABB attackBox;
    private String bodyAnimation, weaponAnimation, headAnimation, shouldersAnimation, torsoAnimation, beltAnimation, handsAnimation, legsAnimation, feetAnimation;
    private boolean isAttackRight = false, isAttackLeft = false, isAttackUp = false, isAttackDown = false;
    private int player_animation_move_left_i = 2, player_animation_move_right_i = 2, player_animation_move_up_i = 2, player_animation_move_down_i = 2;
    private int player_animation_move_g = 0, player_animation_attack_g = 0, player_animation_death_g = 0, player_animation_death_i = 1;
    private int player_animation_attack_left_i = 1, player_animation_attack_right_i = 1, player_animation_attack_up_i = 1,  player_animation_attack_down_i = 1;
    private int time = 0;
    private String knockbackDirection;
//    private Timer timer = new Timer();
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            if (knockbackDirection.equals("left")) moveRight();
            else if (knockbackDirection.equals("right")) moveLeft();
            else if (knockbackDirection.equals("up")) moveDown();
            else if (knockbackDirection.equals("down")) moveUp();
            time++;
        }
    };

    public Player(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
        setMoveDirection("down");
        attackBox = new AABB();
        weapon = new Weapon("longsword", "slash", 10, new AABB());
        head = new Armor("plate_helmet", "head", 5, true);
        shoulders = new Armor("plate_armor", "shoulders", 5, true);
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
        for (int i = 0, id = 0; i < Storage.playerSoundString.length; i++)
            playerSounds.put(Storage.playerSoundString[i], id = AudioMaster.loadSound("sounds/" + Storage.playerSoundString[i]));
    }

    public void stopTimer() {
        time = 0;
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        knockbackTask = new TimerTask() {
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
                if (player_animation_attack_left_i == 7) {
                    player_animation_attack_left_i = 1;
                    getAttackBox().update(0, 0, 0, 0);
                    isAttackLeft = false;
                }
                if (player_animation_attack_left_i == 5) getAttackBox().update(getX() - 50, getY() + 20, getX() - 50 + 69, getY() + 20 + 27);
                if (player_animation_attack_left_i == 4) hitSound.play(playerSounds.get("swish"));
                bodyAnimation = "player_" + getWeapon().getAttackType() + "_left_0" + player_animation_attack_left_i;
                weaponAnimation = "weapon_" + getWeapon().getTexture() + "_left_" + getWeapon().getAttackType() + "_0" + player_animation_attack_left_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_left_" + getWeapon().getAttackType() + "_0" + player_animation_attack_left_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_left_" + getWeapon().getAttackType() + "_0" + player_animation_attack_left_i;
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_left_" + getWeapon().getAttackType() + "_0" + player_animation_attack_left_i;
                beltAnimation = "BELT_" + getBeltTexture() + "_left_" + getWeapon().getAttackType() + "_0" + player_animation_attack_left_i;
                handsAnimation = "HANDS_" + getHandsTexture() + "_left_" + getWeapon().getAttackType() + "_0" + player_animation_attack_left_i;
                legsAnimation = "LEGS_" + getLegsTexture() + "_left_" + getWeapon().getAttackType() + "_0" + player_animation_attack_left_i;
                feetAnimation = "FEET_" + getFeetTexture() + "_left_" + getWeapon().getAttackType() + "_0" + player_animation_attack_left_i;
                if (player_animation_attack_g == 5) {
                    player_animation_attack_left_i++;
                    player_animation_attack_g = 0;
                }
                player_animation_attack_g++;
            }
            else if (isAttackRight) {
                if (player_animation_attack_right_i == 7) {
                    player_animation_attack_right_i = 1;
                    getAttackBox().update(0, 0, 0, 0);
                    isAttackRight = false;
                }
                if (player_animation_attack_right_i == 5) getAttackBox().update(getX() + 55, getY() + 20, getX() + 55 + 60, getY() + 20 + 28);
                if (player_animation_attack_right_i == 4) hitSound.play(playerSounds.get("swish"));
                bodyAnimation = "player_" + getWeapon().getAttackType() + "_right_0" + player_animation_attack_right_i;
                weaponAnimation = "weapon_" + getWeapon().getTexture() + "_right_" + getWeapon().getAttackType() + "_0" + player_animation_attack_right_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_right_" + getWeapon().getAttackType() + "_0" + player_animation_attack_right_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_right_" + getWeapon().getAttackType() + "_0" + player_animation_attack_right_i;
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_right_" + getWeapon().getAttackType() + "_0" + player_animation_attack_right_i;
                beltAnimation = "BELT_" + getBeltTexture() + "_right_" + getWeapon().getAttackType() + "_0" + player_animation_attack_right_i;
                handsAnimation = "HANDS_" + getHandsTexture() + "_right_" + getWeapon().getAttackType() + "_0" + player_animation_attack_right_i;
                legsAnimation = "LEGS_" + getLegsTexture() + "_right_" + getWeapon().getAttackType() + "_0" + player_animation_attack_right_i;
                feetAnimation = "FEET_" + getFeetTexture() + "_right_" + getWeapon().getAttackType() + "_0" + player_animation_attack_right_i;
                if (player_animation_attack_g == 5) {
                    player_animation_attack_g = 0;
                    player_animation_attack_right_i++;
                }
                player_animation_attack_g++;
            }
            else if (isAttackUp) {
                if (player_animation_attack_up_i == 7) {
                    player_animation_attack_up_i = 1;
                    getAttackBox().update(0, 0, 0, 0);
                    isAttackUp = false;
                }
                if (player_animation_attack_up_i == 5) getAttackBox().update(getX() + 18, getY() - 10, getX() + 18 + 56, getY() - 10 + 21);
                if (player_animation_attack_up_i == 4) hitSound.play(playerSounds.get("swish"));
                bodyAnimation = "player_" + getWeapon().getAttackType() + "_up_0" + player_animation_attack_up_i;
                weaponAnimation = "weapon_" + getWeapon().getTexture() + "_up_" + getWeapon().getAttackType() + "_0" + player_animation_attack_up_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_up_" + getWeapon().getAttackType() + "_0" + player_animation_attack_up_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_up_" + getWeapon().getAttackType() + "_0" + player_animation_attack_up_i;
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_up_" + getWeapon().getAttackType() + "_0" + player_animation_attack_up_i;
                beltAnimation = "BELT_" + getBeltTexture() + "_up_" + getWeapon().getAttackType() + "_0" + player_animation_attack_up_i;
                handsAnimation = "HANDS_" + getHandsTexture() + "_up_" + getWeapon().getAttackType() + "_0" + player_animation_attack_up_i;
                legsAnimation = "LEGS_" + getLegsTexture() + "_up_" + getWeapon().getAttackType() + "_0" + player_animation_attack_up_i;
                feetAnimation = "FEET_" + getFeetTexture() + "_up_" + getWeapon().getAttackType() + "_0" + player_animation_attack_up_i;
                if (player_animation_attack_g == 5) {
                    player_animation_attack_g = 0;
                    player_animation_attack_up_i++;
                }
                player_animation_attack_g++;
            }
            else if (isAttackDown) {
                if (player_animation_attack_down_i == 7) {
                    player_animation_attack_down_i = 1;
                    getAttackBox().update(0, 0, 0, 0);
                    isAttackDown = false;
                }
                if (player_animation_attack_down_i == 5) getAttackBox().update(getX() + 10, getY() + 20, getX() + 10 + 55, getY() + 45 + 39);
                if (player_animation_attack_down_i == 4) hitSound.play(playerSounds.get("swish"));
                bodyAnimation = "player_" + getWeapon().getAttackType() + "_down_0" + player_animation_attack_down_i;
                weaponAnimation = "weapon_" + getWeapon().getTexture() + "_down_" + getWeapon().getAttackType() + "_0" + player_animation_attack_down_i;
                headAnimation =  "HEAD_" + getHeadTexture() + "_down_" + getWeapon().getAttackType() + "_0" + player_animation_attack_down_i;
                shouldersAnimation =  "SHOULDERS_" + getShouldersTexture() + "_down_" + getWeapon().getAttackType() + "_0" + player_animation_attack_down_i;
                torsoAnimation = "TORSO_" + getTorsoTexture() + "_down_" + getWeapon().getAttackType() + "_0" + player_animation_attack_down_i;
                beltAnimation = "BELT_" + getBeltTexture() + "_down_" + getWeapon().getAttackType() + "_0" + player_animation_attack_down_i;
                handsAnimation = "HANDS_" + getHandsTexture() + "_down_" + getWeapon().getAttackType() + "_0" + player_animation_attack_down_i;
                legsAnimation = "LEGS_" + getLegsTexture() + "_down_" + getWeapon().getAttackType() + "_0" + player_animation_attack_down_i;
                feetAnimation = "FEET_" + getFeetTexture() + "_down_" + getWeapon().getAttackType() + "_0" + player_animation_attack_down_i;
                if (player_animation_attack_g == 5) {
                    player_animation_attack_g = 0;
                    player_animation_attack_down_i++;
                }
                player_animation_attack_g++;
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

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public AABB getAttackBox() { return attackBox; }

    public int getTime() { return time; }

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
}