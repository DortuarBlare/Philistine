package levels;

import content.Storage;
import content.Texture;
import managers.LevelManager;
import managers.SoundManager;
import managers.UserInputManager;
import math.SimpleMath;
import mobs.Blacksmith;
import objects.Armor;
import objects.Object;
import objects.Shop;
import physics.AABB;
import singletons.SingletonPlayer;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class ForgeLevel implements Level {
    private ArrayList<Object> objectsForSale;
    private Shop shop;
    private Blacksmith blacksmith;
    private int forgeFurnace_i = 1, forgeFurnace_g = 0;

    @Override
    public void init() {
        objectsForSale = new ArrayList<>();
        shop = new Shop();
        blacksmith = new Blacksmith(176, 126, 1);
    }

    @Override
    public void update() {
        collide();
        blacksmith.simulateBehavior();

        if (shop.loot.size() != 0) {
            objectsForSale.addAll(shop.loot);
            shop.loot.clear();
        }

        for (int i = 0; i < objectsForSale.size(); i++) {
            Object object = objectsForSale.get(i);

            if (object instanceof Armor && AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(),
                    object.getCollisionBox())) {
                Armor armorToBuy = (Armor) object;

                if (UserInputManager.pressedKeyE && SingletonPlayer.player.getMoney() >= armorToBuy.getPrice()) {
                    SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() - armorToBuy.getPrice());
                    SingletonPlayer.player.setArmor(armorToBuy);
                    objectsForSale.remove(object);
                    SoundManager.environmentSoundSource.play(Storage.soundMap.get("changingArmor"));
                }
            }
        }

        // Проверка выхода в город
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("entranceFromForgeToTown")))
            LevelManager.forgeToTown();
    }

    @Override
    public void draw() {
        // Фон
        Texture.draw(Storage.textureMap.get("forge"), new AABB(0, 0, 640, 360));

        // Анимация печки
        if (forgeFurnace_i == 4)
            forgeFurnace_i = 1;

        Texture.draw(
                Storage.textureMap.get("furnace_burn_0" + forgeFurnace_i),
                new AABB(223, 142, 259, 173)
        );

        if (forgeFurnace_g++ == 12) {
            forgeFurnace_i++;
            forgeFurnace_g = 0;
        }

        // Отрисовка кузнеца
        blacksmith.draw();

        // Отрисовка эмоции при остановке кузнеца
        if (blacksmith.isWaitingTaskStarted()) {
            Texture.draw(
                    Storage.textureMap.get("emotion_question"),
                    new AABB(blacksmith.getX() + 31, blacksmith.getY(), blacksmith.getX() + 51, blacksmith.getY() + 20)
            );
        }

        // Отрисовка продаваемых объектов
        for (Object object : objectsForSale) {
            if (object.isDrawAble()) {
                Texture.draw(
                        Storage.textureMap.get(object.getTexture()),
                        new AABB(object.getMinX(), object.getMinY(), object.getMaxX(), object.getMaxY())
                );

                if (object instanceof Armor && AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(),
                        object.getCollisionBox())) {
                    Armor armorToBuy = (Armor) object;

                    Texture.draw(
                            Storage.textureMap.get("price"),
                            new AABB(
                                    armorToBuy.getMinX() + 15,
                                    armorToBuy.getCollisionBox().getMin().y - 27,
                                    armorToBuy.getMinX() + 45,
                                    armorToBuy.getCollisionBox().getMin().y
                            )
                    );

                    int minX = armorToBuy.getMinX() + 37 - 7;
                    int maxX = armorToBuy.getMinX() + 37;
                    int tempPrice = armorToBuy.getPrice();

                    for (int i = 0; i < SimpleMath.getCountsOfDigits(armorToBuy.getPrice()); i++) {
                        Texture.draw(
                                Storage.textureMap.get("number_" + tempPrice % 10),
                                new AABB(
                                        minX,
                                        armorToBuy.getCollisionBox().getMin().y - 13,
                                        maxX,
                                        armorToBuy.getCollisionBox().getMin().y - 3
                                )
                        );

                        tempPrice /= 10;
                        minX -= 7;
                        maxX -= 7;
                    }
                }
            }
        }

    }

    @Override
    public void collide() {
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall6")) ||
                AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall4")))
            SingletonPlayer.player.stopRight();

        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall0")) ||
                AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall2")))
            SingletonPlayer.player.stopLeft();

        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall1")) ||
                AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall5")))
            SingletonPlayer.player.stopDown();

        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall3")))
            SingletonPlayer.player.stopUp();
    }
}
