package mobs;

import content.AudioMaster;
import content.AudioSource;
import physics.AABB;
import physics.CollisionMessage;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class Slime extends Mob {
    private AudioSource hurtSound;
    private int hurtSoundId, deathSoundId;
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
            if (getAnimationTime() == 4) setAnimationTime(1);
        }
    };
    private final TimerTask deathTask = new TimerTask() {
        @Override
        public void run() {
            incrementDeathAnimationTime();
            if (getDeathAnimationTime() == 2) deathTask.cancel();
        }
    };


    public Slime(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage);
        setAnimationTime(1);
        setDeathAnimationTime(1);
        setKnockBackTime(0);
        getAttackBox().update(getX() + 3, getY() + 2, getX() + 14, getY() + 10);
        getHitbox().update(getX() + 3, getY() + 2, getX() + 14, getY() + 10);
        getCollisionBox().update(getX() + 1, getY() + 1, getX() + 16, getY() + 11);
        setMoveDirection("left");
        hurtSound = new AudioSource(0);
        hurtSoundId = AudioMaster.loadSound("sounds/slimeHurt");
        deathSoundId = AudioMaster.loadSound("sounds/slimeDead");
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
                if (getAnimationTime() == 4) setAnimationTime(1);
            }
        };
        setKnockBackTime(0);
        setImmortal(false);
        setAnimationTaskStarted(false);
        setKnockBackTaskStarted(false);
    }

    public void update() {
        if (getKnockBackTime() >= 25) stopTimer();

        // Слизень получает урон от игрока
        if (AABB.AABBvsAABB(SingletonPlayer.player.getAttackBox(), getHitbox()) && !isImmortal()) {
            if (SingletonPlayer.player.isAttackLeft()) setKnockBackDirection("left");
            else if (SingletonPlayer.player.isAttackRight()) setKnockBackDirection("right");
            else if (SingletonPlayer.player.isAttackUp()) setKnockBackDirection("up");
            else if (SingletonPlayer.player.isAttackDown()) setKnockBackDirection("down");
            else setKnockBackDirection("right");

            setHealth(getHealth() - SingletonPlayer.player.getDamage());
            if (getHealth() <= 0) {
                setDead(true);
                knockbackTask.cancel();
                animationTask.cancel();
                getAttackBox().clear();
                getHitbox().clear();
                getCollisionBox().clear();
                hurtSound.play(deathSoundId);
                getTimer().schedule(getDeathTask(), 0, 120);
            }
            else {
                if (!isKnockBackTaskStarted()) {
                    setImmortal(true);
                    setKnockBackTaskStarted(true);
                    getTimer().schedule(getKnockbackTask(), 0, 10);
                }
                hurtSound.play(hurtSoundId);
            }
        }

        if (!isDead()) {
            // Столкновение с другими мобами
            for (Mob mob : SingletonMobs.mobList) {
                if (!(mob instanceof Player) && !mob.isDead() && AABB.AABBvsAABB2(getCollisionBox(), mob.getCollisionBox()))
                    stop(CollisionMessage.getMessage());
            }

            if (!isAnimationTaskStarted()) {
                setAnimationTaskStarted(true);
                getTimer().schedule(getAnimationTask(), 0, 300);
            }
            getAttackBox().update(getX() + 3, getY() + 2, getX() + 14, getY() + 10);
            getHitbox().update(getX() + 3, getY() + 2, getX() + 14, getY() + 10);
            getCollisionBox().update(getX() + 1, getY() + 1, getX() + 16, getY() + 11);

            // Преследование игрока
            if (!SingletonPlayer.player.isScrollMenu() && !isKnockBackTaskStarted() && getAnimationTime() == 3)
                moveTo(AABB.getFirstBoxPosition(SingletonPlayer.player.getHitbox(), getHitbox()));
        }
    }

    public void moveLeft() {
        setAttackLeft(true);
        setAttackRight(false);
        setAttackUp(false);
        setAttackDown(false);
        setX(getX() - getSpeed());
        setMoveDirection("left");
    }

    public void moveRight() {
        setAttackLeft(false);
        setAttackRight(true);
        setAttackUp(false);
        setAttackDown(false);
        setX(getX() + getSpeed());
        setMoveDirection("right");
    }

    public void moveUp() {
        setAttackLeft(false);
        setAttackRight(false);
        setAttackUp(true);
        setAttackDown(false);
        setY(getY() - getSpeed());
    }

    public void moveUpRight() {
        setAttackLeft(false);
        setAttackRight(true);
        setAttackUp(false);
        setAttackDown(false);
        setY(getY() - getSpeed());
        setX(getX() + getSpeed());
        setMoveDirection("right");
    }

    public void moveUpLeft() {
        setAttackLeft(true);
        setAttackRight(false);
        setAttackUp(false);
        setAttackDown(false);
        setY(getY() - getSpeed());
        setX(getX() - getSpeed());
        setMoveDirection("left");
    }

    public void moveDown() {
        setAttackLeft(false);
        setAttackRight(false);
        setAttackUp(false);
        setAttackDown(true);
        setY(getY() + getSpeed());
    }

    public void moveDownRight() {
        setAttackLeft(false);
        setAttackRight(true);
        setAttackUp(false);
        setAttackDown(false);
        setY(getY() + getSpeed());
        setX(getX() + getSpeed());
        setMoveDirection("right");
    }

    public void moveDownLeft() {
        setAttackLeft(true);
        setAttackRight(false);
        setAttackUp(false);
        setAttackDown(false);
        setY(getY() + getSpeed());
        setX(getX() - getSpeed());
        setMoveDirection("left");
    }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public TimerTask getDeathTask() { return deathTask; }
}
