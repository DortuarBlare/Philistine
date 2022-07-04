package levels;

import content.Storage;
import content.Texture;
import managers.LevelManager;
import mobs.Mob;
import mobs.Player;
import objects.Furniture;
import objects.Gate;
import objects.Object;
import physics.AABB;
import physics.CollisionMessage;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class DungeonBranchingLevel implements Level {
    private ArrayList<Object> objects;

    @Override
    public void init() {
        objects = new ArrayList<>();
        Gate movingGate = new Gate("verticalGate", 0, 195);
        movingGate.setFinalY(99);

        // Добавление объектов на второй уровень
        objects.add(new Furniture("gate3", 198, 54));
        objects.add(new Furniture("gate3", 246, 54));
        objects.add(new Gate     ("verticalGate", 0, 99));
        objects.add(movingGate);

        // Обновление хитбоксов стен для второго уровня
        for (int i = 0, j = 0; i < 13; i++, j += 4) {
            Storage.aabbMap.get("wall" + i).update(
                    Storage.dungeonBranchingLevelWalls[j],
                    Storage.dungeonBranchingLevelWalls[j + 1],
                    Storage.dungeonBranchingLevelWalls[j + 2],
                    Storage.dungeonBranchingLevelWalls[j + 3]
            );
        }
    }

    @Override
    public void update() {
        for (Object object : objects) {
            // Обновление объектов
            object.update();

            if (object.getTexture().equals("gate3") && LevelManager.canChangeLevel) {
                object.setDrawable(false);
                object.setNoclip(true);
            }
        }

        // Проверка возможности перехода на другой уровень
        if (SingletonMobs.mobList.size() == 1)
            LevelManager.canChangeLevel = true;

        // Проверка перехода на первый уровень
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("entranceToFirstLevel")))
            LevelManager.dungeonBranchingRoomToVestibule();

        // Проверка перехода на 3 уровень
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("entranceToThirdLevel")))
            LevelManager.dungeonBranchingRoomToTreasureRoom();

        // Проверка перехода на 4 уровень
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("entranceToForthLevel")))
            LevelManager.dungeonBranchingRoomToBossRoom();
    }

    @Override
    public void draw() {
        // Фон
        Texture.draw(Storage.textureMap.get("level1"), new AABB(0, 0, 640, 360));

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
                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall3")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall8")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall11")))
                    mob.stopRight();

                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall1")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall5")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall7")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall10")))
                    mob.stopLeft();

                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall0")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall2")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall9")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall12")))
                    mob.stopUp();

                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall6")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall4")))
                    mob.stopDown();

                for (Object object : objects) {
                    if (!object.isNoclip() && AABB.AABBvsAABB2(mob.getCollisionBox(), object.getCollisionBox()))
                        mob.stop(CollisionMessage.getMessage());
                }
            }
        }
    }
}
