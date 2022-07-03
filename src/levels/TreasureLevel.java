package levels;

import objects.*;
import objects.Object;

import java.util.ArrayList;

public class TreasureLevel implements Level {
    private ArrayList<Object> objects;

    @Override
    public void init() {
        objects = new ArrayList<>();

        // Добавление объектов на третий уровень
        objects.add(new Chest("chest",279, 215));
        objects.add(new Box("box", 369, 127));
        objects.add(new Box("box", 451, 153));
        objects.add(new Box("box", 414, 246));
        objects.add(new Barrel("barrel", 150, 236));
        objects.add(new Barrel("barrel", 205, 260));
        objects.add(new Barrel("barrel", 110, 278));
        objects.add(new Furniture("bagBig", 171, 131));
        objects.add(new Furniture("bagMedium", 199, 132));
        objects.add(new Furniture("bagSmall", 113, 210));
        objects.add(new Furniture("altar0", 460, 164));
        objects.add(new Furniture("altar1", 129, 170));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }

    @Override
    public void collide() {

    }
}
