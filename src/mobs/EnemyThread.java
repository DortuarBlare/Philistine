package mobs;

public class EnemyThread extends Thread {
    private boolean threadRunning, threadWaiting;

    public EnemyThread(String threadName) {
        super(threadName);
        start();
        threadRunning = true;
        threadWaiting = false;
    }

    @Override
    public synchronized void run() {
        while (threadRunning) {
            // Остановка, если игрок в меню
            while (threadWaiting) {
                try {
                    wait();
//                    threadRunning = false;
                }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            // Движение мобов
            for (Mob mob : SingletonMobs.mobList) mob.follow();
            try { Thread.sleep(35); } // Скорость движения
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public synchronized void resumeThread() {
        threadRunning = true;
        threadWaiting = false;
        notify();
    }

    public boolean isThreadWaiting() { return threadWaiting; }

    public void setThreadWaiting(boolean threadWaiting) { this.threadWaiting = threadWaiting; }
}