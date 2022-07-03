package objects;

import physics.AABB;

public class Lever extends Object {
    private String state = "Off";

    public Lever(String texture, int minX, int minY) {
        super(texture, false, true, minX, minY, minX + 30, minY + 22, new AABB(minX, minY, minX + 30, minY + 22));
    }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }
}