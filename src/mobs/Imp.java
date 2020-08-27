package mobs;

import content.AudioMaster;
import content.Source;
import math.AABB;
import math.CollisionMessage;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class Imp extends Mob {
    private Source hurtSound, hitSound;
    private int hurtSoundId, hitSoundId, deathSoundId;
    private boolean animationTaskStarted = false, knockbackTaskStarted = false, hitAnimationTaskStarted = false;
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            incrementKnockBackTime();
            setImmortal(true);
            knockbackTaskStarted = true;
            if (getKnockBackDirection().equals("left")) knockBackLeft();
            else if (getKnockBackDirection().equals("right")) knockBackRight();
            else if (getKnockBackDirection().equals("up")) knockBackUp();
            else if (getKnockBackDirection().equals("down")) knockBackDown();
            if (getKnockBackTime() >= 25) stopTimer();
        }
    };
    private TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            animationTaskStarted = true;
            incrementAnimationTime();
            if (getAnimationTime() == 5) setAnimationTime(1);
        }
    };
    private TimerTask hitAnimationTask = new TimerTask() {
        @Override
        public void run() {
            hitAnimationTaskStarted = true;
            incrementHitAnimationTime();
            if (getHitAnimationTime() == 2) {
                if (isAttackLeft()) getAttackBox().update(getX() + 4, getY() + 24, getX() + 14, getY() + 48);
                else if (isAttackRight()) getAttackBox().update(getX() + 49, getY() + 24, getX() + 59, getY() + 48);
                else if (isAttackUp()) getAttackBox().update(getX() + 16, getY() + 11, getX() + 45, getY() + 19);
                else if (isAttackDown()) getAttackBox().update(getX() + 16, getY() + 43, getX() + 45, getY() + 54);
            }
            else if (getHitAnimationTime() == 5) {
                setHitAnimationTime(1);
                setAttackLeft(false);
                setAttackRight(false);
                setAttackUp(false);
                setAttackDown(false);
                stopTimer();
            }
        }
    };
    private final TimerTask deathTask = new TimerTask() {
        @Override
        public void run() {
            incrementDeathAnimationTime();
            if (getDeathAnimationTime() == 8) deathTask.cancel();
        }
    };


    public Imp(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage);
        setAnimationTime(1);
        setHitAnimationTime(1);
        setDeathAnimationTime(0);
        setKnockBackTime(0);
        getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        setMoveDirection("left");
        hurtSound = new Source(0);
        hitSound = new Source(0);
        hurtSoundId = AudioMaster.loadSound("sounds/impHurt");
        hitSoundId = AudioMaster.loadSound("sounds/impAttack");
        deathSoundId = AudioMaster.loadSound("sounds/impDeath");
    }

    public void stopTimer() {
        setKnockBackTime(0);
        setImmortal(false);
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        knockbackTask = new TimerTask() {
            @Override
            public void run() {
                incrementKnockBackTime();
                setImmortal(true);
                knockbackTaskStarted = true;
                if (getKnockBackDirection().equals("left")) knockBackLeft();
                else if (getKnockBackDirection().equals("right")) knockBackRight();
                else if (getKnockBackDirection().equals("up")) knockBackUp();
                else if (getKnockBackDirection().equals("down")) knockBackDown();
                if (getKnockBackTime() >= 25) stopTimer();
            }
        };
        animationTask = new TimerTask() {
            @Override
            public void run() {
                animationTaskStarted = true;
                incrementAnimationTime();
                if (getAnimationTime() == 5) setAnimationTime(1);
            }
        };
        hitAnimationTask = new TimerTask() {
            @Override
            public void run() {
                hitAnimationTaskStarted = true;
                incrementHitAnimationTime();
                if (getHitAnimationTime() == 2) {
                    if (isAttackLeft()) getAttackBox().update(getX() + 4, getY() + 24, getX() + 14, getY() + 48);
                    else if (isAttackRight()) getAttackBox().update(getX() + 49, getY() + 24, getX() + 59, getY() + 48);
                    else if (isAttackUp()) getAttackBox().update(getX() + 16, getY() + 11, getX() + 45, getY() + 19);
                    else if (isAttackDown()) getAttackBox().update(getX() + 16, getY() + 43, getX() + 45, getY() + 54);
                }
                else if (getHitAnimationTime() == 5) {
                    setHitAnimationTime(1);
                    setAttackLeft(false);
                    setAttackRight(false);
                    setAttackUp(false);
                    setAttackDown(false);
                    stopTimer();
                }
            }
        };
        animationTaskStarted = false;
        hitAnimationTaskStarted = false;
        knockbackTaskStarted = false;
    }

    public void update() {
        // Имп получает урон от игрока
        if (AABB.AABBvsAABB(SingletonPlayer.player.getAttackBox(), getHitbox()) && !isImmortal()) {
            if (SingletonPlayer.player.isAttackLeft()) setKnockBackDirection("left");
            else if (SingletonPlayer.player.isAttackRight()) setKnockBackDirection("right");
            else if (SingletonPlayer.player.isAttackUp()) setKnockBackDirection("up");
            else if (SingletonPlayer.player.isAttackDown()) setKnockBackDirection("down");

            setHealth(getHealth() - SingletonPlayer.player.getDamage());
            if (getHealth() <= 0) {
                setDead(true);
                knockbackTask.cancel();
                animationTask.cancel();
                hitAnimationTask.cancel();
                getAttackBox().clear();
                getHitbox().clear();
                getCollisionBox().clear();
                hurtSound.stop(hurtSoundId);
                hurtSound.play(deathSoundId);
                getTimer().schedule(getDeathTask(), 0, 120);
            }
            else {
                if (!isKnockbackTaskStarted()) getTimer().schedule(getKnockbackTask(), 0, 10);
                hurtSound.play(hurtSoundId);
            }
        }

        if (!isDead()) {
            // Имп наносит удар при соприкосновении с игроком
            if (AABB.AABBvsAABB2(getHitbox(), SingletonPlayer.player.getHitbox())) {
                if (CollisionMessage.getMessage().equals("left")) {
                    setAttackLeft(true);
                    if (!hitAnimationTaskStarted) {
                        hitSound.play(hitSoundId);
                        getTimer().schedule(hitAnimationTask, 0, 300);
                    }
                }
                else if (CollisionMessage.getMessage().equals("right")) {
                    setAttackRight(true);
                    if (!hitAnimationTaskStarted) {
                        hitSound.play(hitSoundId);
                        getTimer().schedule(hitAnimationTask, 0, 300);
                    }
                }
                else if (CollisionMessage.getMessage().equals("up")) {
                    setAttackUp(true);
                    if (!hitAnimationTaskStarted) {
                        hitSound.play(hitSoundId);
                        getTimer().schedule(hitAnimationTask, 0, 300);
                    }
                }
                else if (CollisionMessage.getMessage().equals("down")) {
                    setAttackDown(true);
                    if (!hitAnimationTaskStarted) {
                        hitSound.play(hitSoundId);
                        getTimer().schedule(hitAnimationTask, 0, 300);
                    }
                }
            }

            if (!isAnimationTaskStarted()) getTimer().schedule(getAnimationTask(), 0, 150);
            getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
            getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);

            // Преследование игрока
            if (!SingletonPlayer.player.isScrollMenu() &&
                    (!isAttackLeft() || !isAttackRight() || !isAttackUp() || !isAttackDown()) && !knockbackTaskStarted)
                moveTo(AABB.getFirstBoxPosition(SingletonPlayer.player.getHitbox(), getHitbox()));
        }
    }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public TimerTask getDeathTask() { return deathTask; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public boolean isKnockbackTaskStarted() { return knockbackTaskStarted; }

    public void setHitAnimationTaskStarted(boolean hitAnimationTaskStarted) { this.hitAnimationTaskStarted = hitAnimationTaskStarted; }
}
