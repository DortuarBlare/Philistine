package objects;

import math.AABB;

import java.util.TimerTask;

public class Gate extends Object {
    private TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            setMinY(getMinY() - 1);
            setMaxY(getMaxY() - 1);
        }
    };

    public Gate(String texture, int minX, int minY) {
        super(texture, true, true, minX, minY, 0, 0, new AABB());
        switch (texture) {
            case "verticalGate":
                setMaxX(minX + 12);
                setMaxY(minY + 96);
                break;
        }
    }
}
