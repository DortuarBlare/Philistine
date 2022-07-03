package levels;

import objects.Furniture;
import objects.Gate;
import objects.Object;

import java.util.ArrayList;

public class DungeonBranchingLevel {
    private ArrayList<Object> objects;

    public void init() {
        objects = new ArrayList<>();

        // Добавление объектов на второй уровень
        objects.add(new Furniture("gate3", 198, 54));
        objects.add(new Furniture("gate3", 246, 54));
        objects.add(new Gate     ("verticalGate", 0, 99));
        objects.add(new Gate     ("verticalGate", 0, 195));
    }
}
