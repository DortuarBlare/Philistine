package objects;

import managers.LevelManager;
import physics.AABB;

import java.util.TimerTask;

public class Gate extends Object {
    private int finalY;
    private boolean activated = false;

    private final TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            moveUp();

            if (getMinY() <= finalY)
                animationTask.cancel();
        }
    };

    public Gate(String texture, int minX, int minY) {
        super(texture, false, true, minX, minY, minX + 12, minY + 96, new AABB());
        getCollisionBox().update(getMinX(), getMinY(), getMaxX(), getMaxY());
    }

    @Override
    public void update() {
        if (LevelManager.canChangeLevel && !activated) {
            getTimer().schedule(animationTask, 0, 20);
            activated = true;
        }
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }
}
