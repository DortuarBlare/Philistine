package levels;

import mobs.Blacksmith;
import objects.Object;
import objects.Shop;

import java.util.ArrayList;

public class ForgeLevel {
    private ArrayList<Object> objectsForSale;
    private Shop shop;
    private Blacksmith blacksmith;

    public void init() {
        objectsForSale = new ArrayList<>();
        shop = new Shop();
        blacksmith = new Blacksmith(176, 126, 1);
    }
}
