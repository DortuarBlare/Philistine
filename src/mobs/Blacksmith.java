package mobs;

import java.util.Timer;
import java.util.TimerTask;

public class Blacksmith extends Mob {
    private int waitingTime = 0;
    private int animationTime = 2;
    private boolean waitingTaskStarted = false, animationTaskStarted = false;
    private String directionToMove = "right";
    private String path = "leftCorner_Customer";

    private TimerTask waitingTask = new TimerTask() {
        @Override
        public void run() {
            waitingTaskStarted = true;
            directionToMove = "stand";
            waitingTime++;
        }
    };

    private TimerTask animationTask = new TimerTask() {
        @Override
        public void run() {
            animationTaskStarted = true;
            animationTime++;
            if (animationTime == 10) animationTime = 2;
        }
    };

    public Blacksmith(int x, int y, int speed) {
        super(x, y, speed, 100, 0, 0);
        getTimer().schedule(animationTask, 0, 120);
        setMoveDirection("right");
    }

    public void stopTimer() {
        waitingTaskStarted = false;
        animationTaskStarted = false;
        waitingTime = 0;
        animationTime = 2;
        getTimer().cancel();
        getTimer().purge();
        setTimer(new Timer());
        waitingTask = new TimerTask() {
            @Override
            public void run() {
                waitingTaskStarted = true;
                directionToMove = "stand";
                waitingTime++;
            }
        };
        animationTask = new TimerTask() {
            @Override
            public void run() {
                animationTaskStarted = true;
                animationTime++;
                if (animationTime == 10) animationTime = 2;
            }
        };
        getTimer().schedule(animationTask, 0, 120);
        path = "customer_rightCorner";
    }

    public void simulateBehavior() {
        switch (path) {
            case "leftCorner_Customer": {
                if (getX() == 176 && getY() == 126) directionToMove = "right";
                else if (getX() == 252 && getY() == 126) directionToMove = "down";
                else if (getX() == 252 && getY() == 191) directionToMove = "right";
                else if (getX() == 270 && getY() == 191) { // Пришел к покупателю
                    directionToMove = "stand";
                    path = "wait";
                }
                break;
            }
            case "customer_leftCorner": {
                if (getX() == 270 && getY() == 191) directionToMove = "left";
                else if (getX() == 252 && getY() == 191) directionToMove = "up";
                else if (getX() == 252 && getY() == 126) directionToMove = "left";
                else if (getX() == 176 && getY() == 126) {
                    directionToMove = "stand";
                    path = "leftCorner_Customer";
                }
                break;
            }
            case "customer_rightCorner": {
                if (getX() == 270 && getY() == 191) directionToMove = "left";
                else if (getX() == 252 && getY() == 191) directionToMove = "up";
                else if (getX() == 252 && getY() == 126) directionToMove = "right";
                else if (getX() == 375 && getY() == 126) {
                    directionToMove = "stand";
                    path = "rightCorner_LeftCorner";
                }
                break;
            }
            case "rightCorner_LeftCorner": {
                if (getX() == 375 && getY() == 126) directionToMove = "left";
                if (getX() == 176 && getY() == 126) {
                    directionToMove = "stand";
                    path = "leftCorner_Customer";
                }
                break;
            }
            case "wait": {
                if (!waitingTaskStarted) {
                    animationTask.cancel();
                    animationTime = 1;
                    getTimer().schedule(waitingTask, 0, 5000);
                }
                if (waitingTime >= 2) stopTimer();
                break;
            }
        }
        if (directionToMove.equals("left")) moveLeft();
        else if (directionToMove.equals("right")) moveRight();
        else if (directionToMove.equals("up")) moveUp();
        else if (directionToMove.equals("down")) moveDown();
    }

    public int getAnimationTime() { return animationTime; }

    public boolean isWaitingTaskStarted() { return waitingTaskStarted; }
}