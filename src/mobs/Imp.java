package mobs;

import content.AudioMaster;
import content.AudioSource;
import content.Storage;
import content.Texture;
import physics.AABB;
import physics.CollisionMessage;
import singletons.SingletonPlayer;

import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Imp extends Mob {
    private AudioSource hurtSound, hitSound;
    private int hurtSoundId, hitSoundId, deathSoundId;

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
            if (getAnimationTime() == 5) setAnimationTime(1);
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
        setDeathAnimationTime(1);
        setKnockBackTime(0);
        getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        setMoveDirection("left");
        hurtSound = new AudioSource(0);
        hitSound = new AudioSource(0);
        hurtSoundId = AudioMaster.loadSound("sounds/impHurt");
        hitSoundId = AudioMaster.loadSound("sounds/impAttack");
        deathSoundId = AudioMaster.loadSound("sounds/impDeath");
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
                if (getAnimationTime() == 5) setAnimationTime(1);
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
        setKnockBackTime(0);
        setImmortal(false);
        setAnimationTaskStarted(false);
        setHitAnimationTaskStarted(false);
        setKnockBackTaskStarted(false);
        getAttackBox().update(0,0,0,0);
    }

    @Override
    public void update() {
        if (getKnockBackTime() >= 25)
            stopTimer();

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
            // Имп наносит удар при соприкосновении с игроком
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

            if (!isAnimationTaskStarted()) {
                setAnimationTaskStarted(true);
                getTimer().schedule(getAnimationTask(), 0, 150);
            }
            getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
            getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);

            // Преследование игрока
            if (!SingletonPlayer.player.isScrollMenu() && !isKnockBackTaskStarted() && !isAttack())
                moveTo(AABB.getFirstBoxPosition(SingletonPlayer.player.getHitbox(), getHitbox()));
        }
    }

    @Override
    public void draw() {
        String frame;
        int tempHealth = getHealth() % 10 == 0 ? getHealth() : getHealth() - (getHealth() % 10) + 10;

        // Полоска здоровья
        if (tempHealth > 0) {
            Texture.draw(
                    Storage.textureMap.get("enemyHp" + tempHealth),
                    new AABB(197, 0, 442, 30)
            );
        }

        if (!isDead()) {
            if (isAttack())
                frame = "imp_" + getMoveDirection() + "_attack_0" + getHitAnimationTime();
            else
                frame = "imp_" + getMoveDirection() + "_move_0" + getAnimationTime();
        }
        else
            frame = "imp_death_0" + getDeathAnimationTime();

        Texture.draw(
                Storage.textureMap.get(frame),
                new AABB(getX(), getY(), getX() + 64, getY() + 64)
        );
    }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public TimerTask getDeathTask() { return deathTask; }
}
