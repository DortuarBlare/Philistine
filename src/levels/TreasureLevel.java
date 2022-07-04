package levels;

import content.Storage;
import content.Texture;
import managers.LevelManager;
import managers.UserInputManager;
import objects.*;
import objects.Object;
import physics.AABB;
import physics.CollisionMessage;
import singletons.SingletonPlayer;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class TreasureLevel implements Level {
    private ArrayList<Object> objects;

    @Override
    public void init() {
        objects = new ArrayList<>();

        // Добавление объектов на третий уровень
        objects.add(new Chest    ("chest",279, 215));
        objects.add(new Box      ("box", 369, 127));
        objects.add(new Box      ("box", 451, 153));
        objects.add(new Box      ("box", 414, 246));
        objects.add(new Barrel   ("barrel", 150, 236));
        objects.add(new Barrel   ("barrel", 205, 260));
        objects.add(new Barrel   ("barrel", 110, 278));
        objects.add(new Furniture("bagBig", 171, 131));
        objects.add(new Furniture("bagMedium", 199, 132));
        objects.add(new Furniture("bagSmall", 113, 210));
        objects.add(new Furniture("altar0", 460, 164));
        objects.add(new Furniture("altar1", 129, 170));

        // Обновление хитбоксов стен для 3 уровня
        for (int i = 0, j = 0; i < 13; i++, j += 4) {
            Storage.aabbMap.get("wall" + i).update(
                    Storage.treasureLevelWalls[j],
                    Storage.treasureLevelWalls[j + 1],
                    Storage.treasureLevelWalls[j + 2],
                    Storage.treasureLevelWalls[j + 3]
            );
        }
    }

    @Override
    public void update() {
        for (Object object : objects) {
            // Обновление объектов
            object.update();

            // Взаимодействие с предметами
            object.interact(objects);
        }

        // Проверка перехода на второй уровень
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("entranceFromThirdToSecondLevel")))
            LevelManager.dungeonTreasureRoomToBranchingRoom();
    }

    @Override
    public void draw() {
        // Фон
        Texture.draw(Storage.textureMap.get("level2"), new AABB(0, 0, 640, 360));

        // Отрисовка всех объектов
        for (Object object : objects) {
            if (object.isDrawAble())
                object.draw();
        }
    }

    @Override
    public void collide() {
        // Столкновение игрока со стенами
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall1")) ||
                AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall6")))
            SingletonPlayer.player.stopRight();

        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall3")) ||
                AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall5")))
            SingletonPlayer.player.stopLeft();

        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall0")) ||
                AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall4")))
            SingletonPlayer.player.stopUp();

        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall2")))
            SingletonPlayer.player.stopDown();

        for (Object object : objects) {
            // Столкновение игрока с !noclip объектами
            if (!object.isNoclip() && AABB.AABBvsAABB2(SingletonPlayer.player.getCollisionBox(), object.getCollisionBox()))
                SingletonPlayer.player.stop(CollisionMessage.getMessage());

            // Столкновение объектов со стенами
            if (object.isNoclip() && AABB.AABBvsAABB(object.getCollisionBox(), Storage.aabbMap.get("wall3")))
                object.stopLeft();
            if (object.isNoclip() && AABB.AABBvsAABB(object.getCollisionBox(), Storage.aabbMap.get("wall1")))
                object.stopRight();
            if (object.isNoclip() && AABB.AABBvsAABB(object.getCollisionBox(), Storage.aabbMap.get("wall0")))
                object.stopUp();
            if (object.isNoclip() && AABB.AABBvsAABB(object.getCollisionBox(), Storage.aabbMap.get("wall2")))
                object.stopDown();

            // Столкновение объектов с объектами
            for (Object object2 : objects) {
                if (AABB.AABBvsAABB(object.getCollisionBox(), object2.getCollisionBox())) {
                    if (object.isNoclip() && object2.isNoclip()) {
                        object.moveLeft();
                        object2.moveRight();
                    }
                    else if (object.isNoclip() && !object2.isNoclip()) object.moveRight();
                    else if (!object.isNoclip() && object2.isNoclip()) object2.moveRight();
                }
            }
        }
    }
}
