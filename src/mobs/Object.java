package mobs;

import math.AABB;

public class Object {
    private int x, y;
    private AABB collisionBox;
    private String texture;

    public Object(int x, int y, AABB collisionBox, String texture) {
        this.x = x;
        this.y = y;
        this.collisionBox = collisionBox;
        this.texture = texture;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public AABB getCollisionBox() { return collisionBox; }

    public String getTexture() { return texture; }

    public void setTexture(String texture) { this.texture = texture; }
}
