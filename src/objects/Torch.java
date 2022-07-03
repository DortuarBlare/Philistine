package objects;

import physics.AABB;

import java.util.TimerTask;

public class Torch extends Object {
    private static int animationTime = 1;
    private static TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            animationTime++;
            if (animationTime == 10) animationTime = 1;
        }
    };

    public Torch(int minX, int minY) {
        super("torch_01", true, true, minX, minY, minX + 32, minY + 64, new AABB());
    }

    public static TimerTask getAnimationTask() { return animationTask; }

    public int getAnimationTime() { return animationTime; }

    public void setAnimationTime(int animationTime) { this.animationTime = animationTime; }
}
