package mobs;

import math.AABB;

public class EnemyThread extends Thread {

    public EnemyThread(String threadName) {
        super(threadName);
        start();
    }

    @Override
    public synchronized void run() {
        while (true) {
            for (Mob mob : SingletonMobs.mobList) {
                mob.follow();
            }
            try { Thread.sleep(35); } // Скорость движения
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
