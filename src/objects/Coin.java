package objects;

import content.Storage;
import content.Texture;
import managers.SoundManager;
import physics.AABB;
import singletons.SingletonPlayer;

import java.util.ArrayList;
import java.util.TimerTask;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Coin extends Object {
    private int animationTime = 1;
    private boolean animationTaskStarted = false;

    private final TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            animationTaskStarted = true;
            animationTime++;
            if (animationTime == 9) animationTime = 1;
        }
    };

    public Coin(String texture, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
    }

    @Override
    public void interact(ArrayList<Object> objects) {
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), getCollisionBox())) {
            getTimer().cancel();
            getTimer().purge();
            objects.remove(this);

            SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() + 10);
            SoundManager.environmentSoundSource.play(Storage.soundMap.get("pickedCoin"));
        }
    }

    @Override
    public void draw() {
        if (!animationTaskStarted)
            getTimer().schedule(animationTask, 0, 120);

        setTexture("coin_0" + animationTime);
        super.draw();
    }

    public TimerTask getAnimationTask() { return animationTask; }

    public int getAnimationTime() { return animationTime; }

    public boolean isAnimationTaskStarted() { return animationTaskStarted; }

    public void setAnimationTaskStarted(boolean animationTaskStarted) { this.animationTaskStarted = animationTaskStarted; }
}
