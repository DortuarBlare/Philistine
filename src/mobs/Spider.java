package mobs;

import content.AudioMaster;
import content.Source;
import math.AABB;
import math.CollisionMessage;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class Spider extends Mob {
    private int knockbackTime = 0, animationTime = 2, hitAnimationTime = 0, deathAnimationTime = 0;
    private String knockbackDirection;
    private Source hurtSound;
    private int hurtSoundId, deathSoundId;
    private boolean animationTaskStarted = false, knockbackTaskStarted = false, hitAnimationTaskStarted = false;
    private AABB attackBox;
    private TimerTask knockbackTask = new TimerTask() {
        @Override
        public void run() {
            setImmortal(true);
            knockbackTaskStarted = true;
            if (knockbackDirection.equals("left")) knockBackLeft();
            else if (knockbackDirection.equals("right")) knockBackRight();
            else if (knockbackDirection.equals("up")) knockBackUp();
            else if (knockbackDirection.equals("down")) knockBackDown();
            knockbackTime++;
            if (knockbackTime >= 25) stopTimer();
        }
    };
    private TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            animationTaskStarted = true;
            animationTime++;
            if (animationTime == 8) animationTime = 2;
        }
    };
    private TimerTask hitAnimationTask = new TimerTask() {
        @Override
        public void run() {
            hitAnimationTaskStarted = true;
            hitAnimationTime++;
            if (hitAnimationTime == 2) {
                if (isAttackLeft()) attackBox.update(getX() + 4, getY() + 24, getX() + 14, getY() + 48);
                else if (isAttackRight()) attackBox.update(getX() + 49, getY() + 24, getX() + 59, getY() + 48);
                else if (isAttackUp()) attackBox.update(getX() + 16, getY() + 11, getX() + 45, getY() + 19);
                else if (isAttackDown()) attackBox.update(getX() + 16, getY() + 43, getX() + 45, getY() + 54);
            }
            if (hitAnimationTime == 4) {
                hitAnimationTime = 0;
                setAttackLeft(false);
                setAttackRight(false);
                setAttackUp(false);
                setAttackDown(false);
                stopTimer();
            }
        }
    };
    private TimerTask deathTask = new TimerTask() {
        @Override
        public void run() {
            deathAnimationTime++;
            if (deathAnimationTime == 4) deathTask.cancel();
        }
    };

    public Spider(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
        attackBox = new AABB();
        hurtSound = new Source(0);
        hurtSoundId = AudioMaster.loadSound("sounds/spiderHurt");
        deathSoundId = AudioMaster.loadSound("sounds/spiderDeath");
        getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        setMoveDirection("left");
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
                if (knockbackDirection.equals("left")) knockBackLeft();
                else if (knockbackDirection.equals("right")) knockBackRight();
                else if (knockbackDirection.equals("up")) knockBackUp();
                else if (knockbackDirection.equals("down")) knockBackDown();
                knockbackTime++;
                if (knockbackTime >= 25) stopTimer();
            }
        };
        animationTask = new TimerTask() {
            @Override
            public void run() {
                animationTaskStarted = true;
                animationTime++;
                if (animationTime == 8) animationTime = 2;
            }
        };
        hitAnimationTask = new TimerTask() {
            @Override
            public void run() {
                hitAnimationTaskStarted = true;
                hitAnimationTime++;
                if (hitAnimationTime == 2) {
                    if (isAttackLeft()) attackBox.update(getX() + 4, getY() + 24, getX() + 14, getY() + 48);
                    else if (isAttackRight()) attackBox.update(getX() + 49, getY() + 24, getX() + 59, getY() + 48);
                    else if (isAttackUp()) attackBox.update(getX() + 16, getY() + 11, getX() + 45, getY() + 19);
                    else if (isAttackDown()) attackBox.update(getX() + 16, getY() + 43, getX() + 45, getY() + 54);
                }
                if (hitAnimationTime == 4) {
                    setAttackLeft(false);
                    setAttackRight(false);
                    setAttackUp(false);
                    setAttackDown(false);
                    hitAnimationTime = 0;
                    stopTimer();
                }
            }
        };
        animationTaskStarted = false;
        hitAnimationTaskStarted = false;
        knockbackTaskStarted = false;
    }

    public void update() {
        if (!isDead()) {
            // Паук получает урон от игрока
            if (AABB.AABBvsAABB(SingletonPlayer.player.getAttackBox(), getHitbox()) && !isImmortal()) {
                if (SingletonPlayer.player.isAttackLeft()) setKnockbackDirection("left");
                else if (SingletonPlayer.player.isAttackRight()) setKnockbackDirection("right");
                else if (SingletonPlayer.player.isAttackUp()) setKnockbackDirection("up");
                else if (SingletonPlayer.player.isAttackDown()) setKnockbackDirection("down");
                setHealth(getHealth() - SingletonPlayer.player.getDamage());

                if (getHealth() <= 0) {
                    hurtSound.stop(hurtSoundId);
                    hurtSound.play(deathSoundId);
                    attackBox.update(0,0,0,0);
                }
                else {
                    hurtSound.play(hurtSoundId);
                    if (!isKnockbackTaskStarted()) getTimer().schedule(getKnockbackTask(), 0, 10);
                }
            }

            // Паук наносит удар при соприкосновении с игроком
            if (AABB.AABBvsAABB2(getHitbox(), SingletonPlayer.player.getHitbox())) {
                switch (CollisionMessage.getMessage()) {
                    case "left":
                        setAttackLeft(true);
                        if (!hitAnimationTaskStarted) getTimer().schedule(hitAnimationTask, 0, 300);
                        break;
                    case "right":
                        setAttackRight(true);
                        if (!hitAnimationTaskStarted) getTimer().schedule(hitAnimationTask, 0, 300);
                        break;
                    case "up":
                        setAttackUp(true);
                        if (!hitAnimationTaskStarted) getTimer().schedule(hitAnimationTask, 0, 300);
                        break;
                    case "down":
                        setAttackDown(true);
                        if (!hitAnimationTaskStarted) getTimer().schedule(hitAnimationTask, 0, 300);
                        break;
                }
            }

            for (Mob mob : SingletonMobs.mobList) {
                if (!(mob instanceof Player) && AABB.AABBvsAABB2(getCollisionBox(), mob.getCollisionBox()))
                    stop(CollisionMessage.getMessage());
            }

            if (!isAnimationTaskStarted()) getTimer().schedule(getAnimationTask(), 0, 150);
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
            if (!SingletonPlayer.player.isScrollMenu() && (!isAttackLeft() || !isAttackRight() || !isAttackUp() || !isAttackDown()) &&
                    !knockbackTaskStarted) {
                if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                        SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x) moveUpLeft();

                else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y &&
                        SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x) moveUpRight();

                else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                        SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x) moveDownLeft();

                else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y &&
                        SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x) moveDownRight();

                else if (SingletonPlayer.player.getHitbox().getMin().x < getHitbox().getMin().x) moveLeft();

                else if (SingletonPlayer.player.getHitbox().getMin().x > getHitbox().getMin().x) moveRight();

                else if (SingletonPlayer.player.getHitbox().getMin().y < getHitbox().getMin().y) moveUp();

                else if (SingletonPlayer.player.getHitbox().getMin().y > getHitbox().getMin().y) moveDown();
            }
        }
    }

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public TimerTask getDeathTask() { return deathTask; }

    public int getAnimationTime() { return animationTime; }

    public int getHitAnimationTime() { return hitAnimationTime; }

    public int getDeathAnimationTime() { return deathAnimationTime; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public void setKnockbackDirection(String knockbackDirection) { this.knockbackDirection = knockbackDirection; }

    public boolean isKnockbackTaskStarted() { return knockbackTaskStarted; }

    public void setKnockbackTaskStarted(boolean knockbackTaskStarted) { this.knockbackTaskStarted = knockbackTaskStarted; }

    public boolean isHitAnimationTaskStarted() { return hitAnimationTaskStarted; }

    public void setHitAnimationTaskStarted(boolean hitAnimationTaskStarted) { this.hitAnimationTaskStarted = hitAnimationTaskStarted; }

    public AABB getAttackBox() { return attackBox; }
}
