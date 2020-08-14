package objects;

import math.AABB;

public abstract class Object {
    private int minX, minY;
    private int maxX, maxY;
    private AABB collisionBox;
    private String texture;
    private boolean isInteractive;
    private boolean isLying;
    private boolean noclip;

    public Object(String texture, boolean noclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        this.texture = texture;
        this.noclip = noclip;
        this.isLying = isLying;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.collisionBox = collisionBox;
    }

    public Object(String texture, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        this.texture = texture;
        this.noclip = true;
        this.isLying = false;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.collisionBox = collisionBox;
    }

    public int getMinX() { return minX; }

    public int getMinY() { return minY; }

    public int getMaxX() { return maxX; }

    public int getMaxY() { return maxY; }

    public void setMinX(int minX) { this.minX = minX; }

    public void setMinY(int minY) { this.minY = minY; }

    public void setMaxX(int maxX) { this.maxX = maxX; }

    public void setMaxY(int maxY) { this.maxY = maxY; }

    public AABB getCollisionBox() { return collisionBox; }

    public String getTexture() { return texture; }

    public void setTexture(String texture) { this.texture = texture; }

    public boolean getIsInteractive() { return isInteractive; }

    public void setIsInteractive(boolean isInteractive) { this.isInteractive = isInteractive; }

    public boolean getIsLying() { return isLying; }

    public void setIsLying(boolean isLying) { this.isLying = isLying; }

    public boolean getIsNoclip() { return noclip; }
}
