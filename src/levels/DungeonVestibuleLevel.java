package levels;

import objects.Furniture;
import objects.Gate;
import objects.Object;

import java.util.ArrayList;

public class DungeonVestibuleLevel implements Level {
    private ArrayList<Object> objects;

    @Override
    public void init() {
        objects = new ArrayList<>();

        // Добавление объектов на первый уровень
        objects.add(new Furniture("barrelOpened", 118, 135));
        objects.add(new Furniture("bagMedium", 137, 134));
        objects.add(new Furniture("boxOpened", 308, 129));
        objects.add(new Furniture("chair1", 399, 172));
        objects.add(new Furniture("chair3", 431, 226));
        objects.add(new Furniture("table1", 425, 162));
        objects.add(new Furniture("littleBag", 426, 160));
        objects.add(new Furniture("bookRed", 434, 186));
        objects.add(new Furniture("bones", 113, 173));
        objects.add(new Furniture("trash", 462, 173));
        objects.add(new Gate     ("verticalGate", 628, 147));
        objects.add(new Gate     ("verticalGate", 628, 243));
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
