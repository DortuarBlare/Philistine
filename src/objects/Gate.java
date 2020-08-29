package objects;

import math.AABB;

import java.util.TimerTask;

public class Gate extends Object {
    private int finalY;
    private boolean activated = false;
    private final TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            moveUp();
            if (getMinY() == finalY) animationTask.cancel();
        }
    };

    public Gate(String texture, int minX, int minY) {
        super(texture, false, true, minX, minY, 0, 0, new AABB());
        switch (texture) {
            case "verticalGate":
                setMaxX(minX + 12);
                setMaxY(minY + 96);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
        }
    }

    public void update() {
        if (!activated) getTimer().schedule(animationTask, 0, 20);
        activated = true;
    }

    public void setFinalY(int finalY) { this.finalY = finalY; }
}
