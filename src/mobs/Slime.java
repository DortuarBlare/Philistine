package mobs;

import content.AudioMaster;
import content.Source;
import content.Storage;
import math.AABB;

import java.util.Timer;
import java.util.TimerTask;

public class Slime extends Mob {
    private int knockbackTime = 0, animationTime = 1;
    private String knockbackDirection;
    private Source hurtSound;
    private int hurtSoundId;
    private boolean animationTaskStarted = false, knockbackTaskStarted = false;
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            setImmortal(true);
            knockbackTaskStarted = true;
            switch (knockbackDirection) {
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
            knockbackTime++;
            if (knockbackTime >= 25) stopTimer();
        }
    };
    private TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            animationTaskStarted = true;
            animationTime++;
            if (animationTime == 4) animationTime = 1;
        }
    };

    public Slime(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
        getHitbox().update(getX() + 3, getY() + 2, getX() + 14, getY() + 10);
        getCollisionBox().update(getX() + 1, getY() + 1, getX() + 16, getY() + 11);
        setMoveDirection("left");
        hurtSound = new Source(0);
        hurtSoundId = AudioMaster.loadSound("sounds/" + Storage.slimeSoundString[0]);
    }

    public void stopTimer() {
        knockbackTime = 0;
        setImmortal(false);
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        knockbackTask = new TimerTask() {
            @Override
            public void run() {
                setImmortal(true);
                knockbackTaskStarted = true;
                switch (knockbackDirection) {
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
                knockbackTime++;
                if (knockbackTime >= 25) stopTimer();
            }
        };
        animationTask = new TimerTask() {
            @Override
            public void run() {
                animationTaskStarted = true;
                animationTime++;
                if (animationTime == 4) animationTime = 1;
            }
        };
        knockbackTaskStarted = false;
        animationTaskStarted = false;
    }

    public synchronized void simulate() {
        if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x &&
                animationTime == 3) moveUpLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x &&
                animationTime == 3) moveUpRight();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x &&
                animationTime == 3) moveDownLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x &&
                animationTime == 3) moveDownRight();

        else if (SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x &&
                animationTime == 3) moveLeft();

        else if (SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x &&
                 animationTime == 3) moveRight();

        else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                animationTime == 3) moveUp();

        else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                animationTime == 3) moveDown();
    }

    public void update() {
        // Слизень получает урон от игрока
        if (AABB.AABBvsAABB(SingletonPlayer.player.getAttackBox(), getHitbox()) && !isImmortal()) {
            if (SingletonPlayer.player.isAttackLeft()) setKnockbackDirection("left");
            else if (SingletonPlayer.player.isAttackRight()) setKnockbackDirection("right");
            else if (SingletonPlayer.player.isAttackUp()) setKnockbackDirection("up");
            else if (SingletonPlayer.player.isAttackDown()) setKnockbackDirection("down");
            setHealth(getHealth() - SingletonPlayer.player.getDamage());
            if (!isKnockbackTaskStarted()) getTimer().schedule(getKnockbackTask(), 0, 10);
            getHurtSound().play(getHurtSoundId());
        }

        if (!isAnimationTaskStarted()) getTimer().schedule(getAnimationTask(), 0, 300);
        getHitbox().update(getX() + 3, getY() + 2, getX() + 14, getY() + 10);
        getCollisionBox().update(getX() + 1, getY() + 1, getX() + 16, getY() + 11);

        if (!SingletonPlayer.player.isScrollMenu()) {
            if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                    SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x &&
                    animationTime == 3) moveUpLeft();

            else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                    SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x &&
                    animationTime == 3) moveUpRight();

            else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                    SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x &&
                    animationTime == 3) moveDownLeft();

            else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                    SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x &&
                    animationTime == 3) moveDownRight();

            else if (SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x &&
                    animationTime == 3) moveLeft();

            else if (SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x &&
                    animationTime == 3) moveRight();

            else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                    animationTime == 3) moveUp();

            else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                    animationTime == 3) moveDown();
        }
    }

    @Override
    public void moveUp() { setY(getY() - getSpeed()); }

    @Override
    public void moveDown() { setY(getY() + getSpeed()); }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public int getKnockbackTime() { return knockbackTime; }

    public int getAnimationTime() { return animationTime; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public void setAnimationTaskStarted(boolean animationTaskStarted) { this.animationTaskStarted = animationTaskStarted; }

    public void setKnockbackDirection(String knockbackDirection) { this.knockbackDirection = knockbackDirection; }

    public boolean isKnockbackTaskStarted() { return knockbackTaskStarted; }

    public void setKnockbackTaskStarted(boolean knockbackTaskStarted) { this.knockbackTaskStarted = knockbackTaskStarted; }

    public int getHurtSoundId() { return hurtSoundId; }

    public Source getHurtSound() { return hurtSound; }
}
