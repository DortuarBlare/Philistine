package objects;

import math.AABB;

public abstract class Object {
    private int minX, minY;
    private int maxX, maxY;
    private AABB collisionBox;
    private String texture;
    private boolean isLying;
    private boolean isNoclip;

    public Object(String texture, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        this.texture = texture;
        this.isNoclip = isNoclip;
        this.isLying = isLying;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.collisionBox = collisionBox;
    }

    public Object(String texture, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        this.texture = texture;
        this.isNoclip = true;
        this.isLying = false;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.collisionBox = collisionBox;
    }

    public void moveLeft() {
        minX -= 1;
        maxX -= 1;
        collisionBox.update(collisionBox.getMin().x - 1, collisionBox.getMin().y,
                collisionBox.getMax().x - 1, collisionBox.getMax().y);
    }

    public void moveRight() {
        minX += 1;
        maxX += 1;
        collisionBox.update(collisionBox.getMin().x + 1, collisionBox.getMin().y,
                collisionBox.getMax().x + 1, collisionBox.getMax().y);
    }

    public void moveUp() {
        minY -= 1;
        maxY -= 1;
        collisionBox.update(collisionBox.getMin().x, collisionBox.getMin().y - 1,
                collisionBox.getMax().x, collisionBox.getMax().y - 1);
    }

    public void moveDown() {
        minY += 1;
        maxY += 1;
        collisionBox.update(collisionBox.getMin().x, collisionBox.getMin().y + 1,
                collisionBox.getMax().x, collisionBox.getMax().y + 1);
    }


    public void stopLeft() {
        minX += 1;
        maxX += 1;
        collisionBox.update(collisionBox.getMin().x + 1, collisionBox.getMin().y,
                collisionBox.getMax().x + 1, collisionBox.getMax().y);
    }

    public void stopRight() {
        minX -= 1;
        maxX -= 1;
        collisionBox.update(collisionBox.getMin().x - 1, collisionBox.getMin().y,
                collisionBox.getMax().x - 1, collisionBox.getMax().y);
    }

    public void stopUp() {
        minY += 1;
        maxY += 1;
        collisionBox.update(collisionBox.getMin().x, collisionBox.getMin().y + 1,
                collisionBox.getMax().x, collisionBox.getMax().y + 1);
    }

    public void stopDown() {
        minY -= 1;
        maxY -= 1;
        collisionBox.update(collisionBox.getMin().x, collisionBox.getMin().y - 1,
                collisionBox.getMax().x, collisionBox.getMax().y - 1);
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

    public boolean isLying() { return isLying; }

    public void setIsLying(boolean isLying) { this.isLying = isLying; }

    public boolean isNoclip() { return isNoclip; }
}
