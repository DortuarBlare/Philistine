package mobs;

import java.util.TimerTask;

public class PlayerTask {
    static public String task;
    static public int animationTime = 1;
    static public TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            animationTime++;
            if (animationTime == 10) animationTime = 1;
        }
    };
}
