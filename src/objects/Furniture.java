package objects;

import physics.AABB;

public class Furniture extends Object {

    public Furniture(String texture, int minX, int minY) {
        super(texture, false, true, minX, minY, 0, 0, new AABB());
        switch (texture) {
            case "table0": {
                setMaxX(minX + 64);
                setMaxY(minY + 50);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "gate2": {
                setMaxX(minX + 48);
                setMaxY(minY + 24);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "gate3": {
                setMaxX(minX + 36);
                setMaxY(minY + 84);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "water": {
                setMaxX(minX + 32);
                setMaxY(minY + 32);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "altar0": {
                setMaxX(minX + 34);
                setMaxY(minY + 66);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "altar1": {
                setMaxX(minX + 34);
                setMaxY(minY + 62);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "table1": {
                setMaxX(minX + 34);
                setMaxY(minY + 82);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "chair0":
            case "chair1": {
                setMaxX(minX + 20);
                setMaxY(minY + 46);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "chair2": {
                setMaxX(minX + 20);
                setMaxY(minY + 44);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "chair3": {
                setMaxX(minX + 22);
                setMaxY(minY + 38);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "bookBlue": {
                setNoclip(false);
                setMaxX(minX + 14);
                setMaxY(minY + 22);
                break;
            }
            case "bookPink":
            case "bookGreen": {
                setNoclip(false);
                setMaxX(minX + 14);
                setMaxY(minY + 18);
                break;
            }
            case "bookRed": {
                setNoclip(false);
                setMaxX(minX + 20);
                setMaxY(minY + 28);
                break;
            }
            case "bagSmall": {
                setMaxX(minX + 18);
                setMaxY(minY + 22);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "bagMedium": {
                setMaxX(minX + 22);
                setMaxY(minY + 26);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "bagBig": {
                setMaxX(minX + 30);
                setMaxY(minY + 28);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "bones":
            case "trash": {
                setNoclip(false);
                setMaxX(minX + 32);
                setMaxY(minY + 32);
                break;
            }
            case "boxOpened": {
                setMaxX(minX + 24);
                setMaxY(minY + 30);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "barrelOpened": {
                setMaxX(minX + 20);
                setMaxY(minY + 30);
                getCollisionBox().update(minX, minY, getMaxX(), getMaxY());
                break;
            }
            case "littleBag": {
                setMaxX(minX + 28);
                setMaxY(minY + 22);
                break;
            }
        }
    }
}