package objects;

import math.AABB;

import java.util.ArrayList;

import static java.lang.Math.random;

public class Container extends Object {
    public ArrayList<Object> loot;
    private String state;

    public Container(String texture, boolean isOnlyClothes, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
        state = "Closed";
        loot = new ArrayList<Object>();
    }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }
}