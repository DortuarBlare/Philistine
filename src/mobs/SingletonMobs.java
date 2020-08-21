package mobs;

import java.util.ArrayList;

public class SingletonMobs {
    public static ArrayList<Mob> mobList;
    private static SingletonMobs instance;

    private SingletonMobs() { mobList = new ArrayList<Mob>(); }

    public static SingletonMobs getInstance() {
        if (instance == null) { instance = new SingletonMobs(); }
        return instance;
    }
}
