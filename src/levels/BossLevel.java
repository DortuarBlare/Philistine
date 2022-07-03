package levels;

import objects.Furniture;
import objects.Object;

import java.util.ArrayList;

public class BossLevel implements Level {
    private ArrayList<Object> objects;

    @Override
    public void init() {
        objects = new ArrayList<>();

        // Добавление объектов на четвертый уровень
        objects.add(new Furniture("gate2", 292, 336));
        objects.add(new Furniture("gate3", 198, 54));
        objects.add(new Furniture("trash", 118, 288));
        objects.add(new Furniture("bones", 466, 163));
        objects.add(new Furniture("water", 466, 163));
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
