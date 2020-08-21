package mobs;

public class SingletonPlayer {
    public static Player player;
    private static SingletonPlayer instance;

    private SingletonPlayer() { player = new Player(290, 192, 1, 100, 0, 10); }

    public static SingletonPlayer getInstance() {
        if (instance == null) { instance = new SingletonPlayer(); }
        return instance;
    }
}
