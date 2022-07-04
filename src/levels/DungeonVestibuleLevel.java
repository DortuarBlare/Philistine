package levels;

import content.Storage;
import content.Texture;
import managers.LevelManager;
import managers.UserInputManager;
import mobs.Mob;
import mobs.Player;
import objects.*;
import objects.Object;
import physics.AABB;
import physics.CollisionMessage;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class DungeonVestibuleLevel implements Level {
    private ArrayList<Object> objects;

    @Override
    public void init() {
        objects = new ArrayList<>();
        Gate movingGate = new Gate("verticalGate", 628, 243);
        movingGate.setFinalY(147);

        // Добавление объектов на первый уровень
        objects.add(new Furniture("bones", 113, 173));
        objects.add(new Furniture("trash", 462, 173));
        objects.add(new Gate     ("verticalGate", 628, 147));
        objects.add(movingGate);

        // Установление хитбоксов стен первого уровня
        for (int i = 0, j = 0; i < 13; i++, j += 4) {
            Storage.aabbMap.get("wall" + i).update(
                    Storage.dungeonVestibuleLevelWalls[j],
                    Storage.dungeonVestibuleLevelWalls[j + 1],
                    Storage.dungeonVestibuleLevelWalls[j + 2],
                    Storage.dungeonVestibuleLevelWalls[j + 3]
            );
        }
    }

    @Override
    public void update() {
        for (Object object : objects) {
            // Обновление объектов
            object.update();
        }

        // Проверка возможности перехода на другой уровень
        if (SingletonMobs.mobList.size() == 1)
            LevelManager.canChangeLevel = true;

        // Проверка перехода на второй уровень
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("entranceToSecondLevel")))
            LevelManager.dungeonVestibuleToBranchingRoom();
    }

    @Override
    public void draw() {
        // Фон
        Texture.draw(Storage.textureMap.get("level0"), new AABB(0, 0, 640, 360));

        // Отрисовка всех объектов
        for (Object object : objects) {
            if (object.isDrawAble())
                object.draw();
        }
    }

    @Override
    public void collide() {
        // Проверка всех мобов на столкновение со стенами, объектами
        for (Mob mob : SingletonMobs.mobList) {
            if (!mob.isDead()) {
                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall0")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall2")))
                    mob.stopUp();

                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall1")))
                    mob.stopRight();

                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall4")))
                    mob.stopLeft();

                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall3")))
                    mob.stopDown();

                for (Object object : objects) {
                    if (!object.isNoclip() && AABB.AABBvsAABB2(mob.getCollisionBox(), object.getCollisionBox()))
                        mob.stop(CollisionMessage.getMessage());
                }
            }
        }
    }
}
