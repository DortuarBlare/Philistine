package objects;

import physics.AABB;
import java.util.Timer;

public abstract class Object {
    private int minX, minY;
    private int maxX, maxY;
    private AABB collisionBox;
    private String texture;
    private boolean drawAble;
    private boolean isNoclip;
    private Timer timer = new Timer();

    public Object(String texture, boolean isNoclip, boolean drawAble, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        this.texture = texture;
        this.isNoclip = isNoclip;
        this.drawAble = drawAble;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.collisionBox = collisionBox;
    }

    public Object(String texture, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        this.texture = texture;
        this.isNoclip = true;
        this.drawAble = false;
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

    public Timer getTimer() { return timer; }

    public int getMinX() { return minX; }

    public int getMinY() { return minY; }

    public int getMaxX() { return maxX; }

    public int getMaxY() { return maxY; }

    public void setMinX(int minX) { this.minX = minX; }

    public void setMinY(int minY) { this.minY = minY; }

    public void setMaxX(int maxX) { this.maxX = maxX; }

    public void setMaxY(int maxY) { this.maxY = maxY; }

    public void setSize(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public AABB getCollisionBox() { return collisionBox; }

    public String getTexture() { return texture; }

    public void setTexture(String texture) { this.texture = texture; }

    public boolean isDrawAble() { return drawAble; }

    public void setIsLying(boolean isLying) { this.drawAble = isLying; }

    public boolean isNoclip() { return isNoclip; }

    public void setNoclip(boolean noclip) { this.isNoclip = noclip; }
}
