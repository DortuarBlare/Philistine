package mobs;

import content.AudioMaster;
import content.AudioSource;
import physics.AABB;
import physics.CollisionMessage;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class Spider extends Mob {
    private AudioSource hurtSound, hitSound;
    private final int hurtSoundId, hitSoundId, deathSoundId;
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            incrementKnockBackTime();
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
    };
    private TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            incrementAnimationTime();
            if (getAnimationTime() == 8) setAnimationTime(2);
        }
    };
    private TimerTask hitAnimationTask = new TimerTask() {
        @Override
        public void run() {
            incrementHitAnimationTime();
            if (getHitAnimationTime() == 2) {
                if (isAttackLeft()) getAttackBox().update(getX() + 4, getY() + 24, getX() + 14, getY() + 48);
                else if (isAttackRight()) getAttackBox().update(getX() + 49, getY() + 24, getX() + 59, getY() + 48);
                else if (isAttackUp()) getAttackBox().update(getX() + 16, getY() + 11, getX() + 45, getY() + 19);
                else if (isAttackDown()) getAttackBox().update(getX() + 16, getY() + 43, getX() + 45, getY() + 54);
            }
            else if (getHitAnimationTime() == 4) {
                setAttackLeft(false);
                setAttackRight(false);
                setAttackUp(false);
                setAttackDown(false);
                getAttackBox().update(0,0,0,0);
                setHitAnimationTime(1);
                stopTimer();
            }
        }
    };
    private final TimerTask deathTask = new TimerTask() {
        @Override
        public void run() {
            incrementDeathAnimationTime();
            if (getDeathAnimationTime() == 4) deathTask.cancel();
        }
    };

    public Spider(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage);
        setAnimationTime(2);
        setHitAnimationTime(1);
        setDeathAnimationTime(1);
        setKnockBackTime(0);
        getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        setMoveDirection("left");
        hurtSound = new AudioSource(0);
        hitSound = new AudioSource(0);
        hurtSoundId = AudioMaster.loadSound("sounds/spiderHurt");
        hitSoundId = AudioMaster.loadSound("sounds/spiderAttack");
        deathSoundId = AudioMaster.loadSound("sounds/spiderDeath");
    }

    public void stopTimer() {
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        knockbackTask = new TimerTask() {
            @Override
            public void run() {
                incrementKnockBackTime();
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
        };
        animationTask = new TimerTask() {
            @Override
            public void run() {
                incrementAnimationTime();
                if (getAnimationTime() == 8) setAnimationTime(2);
            }
        };
        hitAnimationTask = new TimerTask() {
            @Override
            public void run() {
                incrementHitAnimationTime();
                if (getHitAnimationTime() == 2) {
                    if (isAttackLeft()) getAttackBox().update(getX() + 4, getY() + 24, getX() + 14, getY() + 48);
                    else if (isAttackRight()) getAttackBox().update(getX() + 49, getY() + 24, getX() + 59, getY() + 48);
                    else if (isAttackUp()) getAttackBox().update(getX() + 16, getY() + 11, getX() + 45, getY() + 19);
                    else if (isAttackDown()) getAttackBox().update(getX() + 16, getY() + 43, getX() + 45, getY() + 54);
                }
                else if (getHitAnimationTime() == 4) {
                    setAttackLeft(false);
                    setAttackRight(false);
                    setAttackUp(false);
                    setAttackDown(false);
                    getAttackBox().update(0,0,0,0);
                    setHitAnimationTime(1);
                    stopTimer();
                }
            }
        };
        setKnockBackTime(0);
        setHitAnimationTime(1);
        setImmortal(false);
        setAnimationTaskStarted(false);
        setHitAnimationTaskStarted(false);
        setKnockBackTaskStarted(false);
        getAttackBox().update(0,0,0,0);
    }

    public void update() {
        if (getKnockBackTime() >= 25) stopTimer();

        // Паук получает урон от игрока
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
                if (!isKnockBackTaskStarted()) {
                    setImmortal(true);
                    setKnockBackTaskStarted(true);
                    setAttackToFalse();
                    getAttackBox().update(0,0,0,0);
                    getTimer().schedule(getKnockbackTask(), 0, 10);
                }
                hurtSound.play(hurtSoundId);
            }
        }

        if (!isDead()) {
            // Паук наносит удар при соприкосновении с игроком
            if (AABB.AABBvsAABB2(getHitbox(), SingletonPlayer.player.getHitbox())) {
                switch (CollisionMessage.getMessage()) {
                    case "left":
                        setAttackLeft(true);
                        if (!isHitAnimationTaskStarted()) {
                            setHitAnimationTaskStarted(true);
                            hitSound.play(hitSoundId);
                            getTimer().schedule(hitAnimationTask, 0, 300);
                        }
                        break;
                    case "right":
                        setAttackRight(true);
                        if (!isHitAnimationTaskStarted()) {
                            setHitAnimationTaskStarted(true);
                            hitSound.play(hitSoundId);
                            getTimer().schedule(hitAnimationTask, 0, 300);
                        }
                        break;
                    case "up":
                        setAttackUp(true);
                        if (!isHitAnimationTaskStarted()) {
                            setHitAnimationTaskStarted(true);
                            hitSound.play(hitSoundId);
                            getTimer().schedule(hitAnimationTask, 0, 300);
                        }
                        break;
                    case "down":
                        setAttackDown(true);
                        if (!isHitAnimationTaskStarted()) {
                            setHitAnimationTaskStarted(true);
                            hitSound.play(hitSoundId);
                            getTimer().schedule(hitAnimationTask, 0, 300);
                        }
                        break;
                }
            }

            // Столкновение с мобами
            for (Mob mob : SingletonMobs.mobList) {
                if (!(mob instanceof Player) && !mob.isDead() &&
                        AABB.AABBvsAABB2(getCollisionBox(), mob.getCollisionBox()))
                    stop(CollisionMessage.getMessage());
            }

            if (!isAnimationTaskStarted()) {
                setAnimationTaskStarted(true);
                getTimer().schedule(getAnimationTask(), 0, 150);
            }
            switch (getMoveDirection()) {
                case "left":
                    getHitbox().update(getX() + 17, getY() + 16, getX() + 47, getY() + 51);
                    getCollisionBox().update(getX() + 17, getY() + 16, getX() + 47, getY() + 51);
                    break;
                case "right":
                    getHitbox().update(getX() + 16, getY() + 16, getX() + 46, getY() + 51);
                    getCollisionBox().update(getX() + 16, getY() + 16, getX() + 46, getY() + 51);
                    break;
                case "up":
                    getHitbox().update(getX() + 10, getY() + 19, getX() + 51, getY() + 50);
                    getCollisionBox().update(getX() + 10, getY() + 19, getX() + 51, getY() + 50);
                    break;
                case "down":
                    getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
                    getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
                    break;
            }

            // Преследование игрока
            if (!SingletonPlayer.player.isScrollMenu() && !isKnockBackTaskStarted() && !isAttack())
                moveTo(AABB.getFirstBoxPosition(SingletonPlayer.player.getHitbox(), getHitbox()));
        }
    }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public TimerTask getDeathTask() { return deathTask; }
}
