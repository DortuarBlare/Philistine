package mobs;

import math.AABB;
import math.CollisionMessage;

import java.util.Timer;
import java.util.TimerTask;

public class Spider extends Mob {
    private int knockbackTime = 0, animationTime = 2, hitAnimationTime = 1;
    private String knockbackDirection;
    private boolean animationTaskStarted = false, knockbackTaskStarted = false, hitAnimationTaskStarted = false;
    private boolean isAttackRight = false, isAttackLeft = false, isAttackUp = false, isAttackDown = false;
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
                if (isAttackLeft) attackBox.update(getX() + 4, getY() + 24, getX() + 14, getY() + 48);
                else if (isAttackRight) attackBox.update(getX() + 49, getY() + 24, getX() + 59, getY() + 48);
                else if (isAttackUp) attackBox.update(getX() + 16, getY() + 11, getX() + 45, getY() + 19);
                else if (isAttackDown) attackBox.update(getX() + 16, getY() + 43, getX() + 45, getY() + 54);
            }
            if (hitAnimationTime == 4) {
                hitAnimationTime = 1;
                isAttackLeft = isAttackRight = isAttackUp = isAttackDown = false;
                stopTimer();
            }
        }
    };

    public Spider(int x, int y, int speed, int health, int armor, int damage) {
        super(x, y, speed, health, armor, damage, new AABB(), new AABB());
        attackBox = new AABB();
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
                    if (isAttackLeft) attackBox.update(getX() + 4, getY() + 24, getX() + 14, getY() + 48);
                    else if (isAttackRight) attackBox.update(getX() + 49, getY() + 24, getX() + 59, getY() + 48);
                    else if (isAttackUp) attackBox.update(getX() + 16, getY() + 11, getX() + 45, getY() + 19);
                    else if (isAttackDown) attackBox.update(getX() + 16, getY() + 43, getX() + 45, getY() + 54);
                }
                if (hitAnimationTime == 4) {
                    isAttackLeft = isAttackRight = isAttackUp = isAttackDown = false;
                    hitAnimationTime = 1;
                    stopTimer();
                }
            }
        };
        animationTaskStarted = false;
        hitAnimationTaskStarted = false;
    }

    public void simulate() {
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

    public void update() {
        if (knockbackTime >= 25) stopTimer();

        // Паук получает урон от игрока
        if (AABB.AABBvsAABB(SingletonPlayer.player.getAttackBox(), getHitbox()) && !isImmortal()) {
            if (SingletonPlayer.player.isAttackLeft()) setKnockbackDirection("left");
            else if (SingletonPlayer.player.isAttackRight()) setKnockbackDirection("right");
            else if (SingletonPlayer.player.isAttackUp()) setKnockbackDirection("up");
            else if (SingletonPlayer.player.isAttackDown()) setKnockbackDirection("down");
            setHealth(getHealth() - SingletonPlayer.player.getDamage());
            if (!isKnockbackTaskStarted()) getTimer().schedule(getKnockbackTask(), 0, 10);
        }

        if (AABB.AABBvsAABB2(getHitbox(), SingletonPlayer.player.getHitbox())) {
            if (CollisionMessage.getMessage().equals("left")) {
                isAttackLeft = true;
                if (!hitAnimationTaskStarted) getTimer().schedule(hitAnimationTask, 0, 300);
            }
            else if (CollisionMessage.getMessage().equals("right")) {
                isAttackRight = true;
                if (!hitAnimationTaskStarted) getTimer().schedule(hitAnimationTask, 0, 300);
            }
            else if (CollisionMessage.getMessage().equals("up")) {
                isAttackUp = true;
                if (!hitAnimationTaskStarted) getTimer().schedule(hitAnimationTask, 0, 300);
            }
            else if (CollisionMessage.getMessage().equals("down")) {
                isAttackDown = true;
                if (!hitAnimationTaskStarted) getTimer().schedule(hitAnimationTask, 0, 300);
            }
        }

        for (Mob mob : SingletonMobs.mobList) {
            if (!(mob instanceof Player) && AABB.AABBvsAABB2(getCollisionBox(), mob.getCollisionBox()))
                stop(CollisionMessage.getMessage());
        }

        if (!isAnimationTaskStarted()) getTimer().schedule(getAnimationTask(), 0, 150);
        getHitbox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);
        getCollisionBox().update(getX() + 10, getY() + 15, getX() + 51, getY() + 49);

        if (!SingletonPlayer.player.isScrollMenu() && (!isAttackLeft || !isAttackRight || !isAttackUp || !isAttackDown)) {
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

    public TimerTask getKnockbackTask() { return knockbackTask; }

    public TimerTask getAnimationTask() { return animationTask; }

    public int getAnimationTime() { return animationTime; }

    public int getHitAnimationTime() { return hitAnimationTime; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public void setKnockbackDirection(String knockbackDirection) { this.knockbackDirection = knockbackDirection; }

    public boolean isKnockbackTaskStarted() { return knockbackTaskStarted; }

    public void setKnockbackTaskStarted(boolean knockbackTaskStarted) { this.knockbackTaskStarted = knockbackTaskStarted; }

    public boolean isHitAnimationTaskStarted() { return hitAnimationTaskStarted; }

    public void setHitAnimationTaskStarted(boolean hitAnimationTaskStarted) { this.hitAnimationTaskStarted = hitAnimationTaskStarted; }

    public boolean isAttackRight() { return isAttackRight; }

    public boolean isAttackLeft() { return isAttackLeft; }

    public boolean isAttackUp() { return isAttackUp; }

    public boolean isAttackDown() { return isAttackDown; }

    public AABB getAttackBox() { return attackBox; }
}
