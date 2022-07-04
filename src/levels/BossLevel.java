package levels;

import content.Storage;
import content.Texture;
import managers.LevelManager;
import mobs.Mob;
import mobs.Player;
import objects.Furniture;
import objects.Object;
import objects.Shop;
import physics.AABB;
import physics.CollisionMessage;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

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

        // Обновление хитбоксов стен для 4 уровня
        for (int i = 0, j = 0; i < 13; i++, j += 4) {
            Storage.aabbMap.get("wall" + i).update(
                    Storage.bossLevelWalls[j],
                    Storage.bossLevelWalls[j + 1],
                    Storage.bossLevelWalls[j + 2],
                    Storage.bossLevelWalls[j + 3]
            );
        }
    }

    @Override
    public void update() {
        collide();

        for (Object object : objects) {
            // Обновление объектов
            object.update();

            if (LevelManager.canChangeLevel && (object.getTexture().equals("gate2") || object.getTexture().equals("gate3"))) {
                object.setDrawable(false);
                object.setNoclip(true);
            }
        }

        // Проверка возможности перехода на другой уровень
        if (SingletonMobs.mobList.size() == 1)
            LevelManager.canChangeLevel = true;

        // Проверка перехода на второй уровень
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("entranceFromForthToSecondLevel")))
            LevelManager.dungeonBossRoomToBranchingRoom();

        // Проверка перехода в город
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("entranceFromFourthToTownLevel")))
            LevelManager.dungeonBossRoomToTown();
    }

    @Override
    public void draw() {
        // Фон
        Texture.draw(Storage.textureMap.get("level3"), new AABB(0, 0, 640, 360));

        // Отрисовка всех объектов
        for (Object object : objects) {
            if (object.isDrawAble())
                object.draw();
        }
    }

    @Override
    public void collide() {
        // Столкновение мобов с препятствиями
        for (Mob mob : SingletonMobs.mobList) {
            if (!mob.isDead()) {
                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall1")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall8")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall6")))
                    mob.stopRight();

                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall3")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall7")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall5")))
                    mob.stopLeft();

                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall0")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall9")))
                    mob.stopUp();

                if (AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall2")) ||
                        AABB.AABBvsAABB(mob.getCollisionBox(), Storage.aabbMap.get("wall4")))
                    mob.stopDown();

                for (Object object : objects) {
                    // Столкновение мобов с !noclip объектами
                    if (!object.isNoclip() && AABB.AABBvsAABB2(mob.getCollisionBox(), object.getCollisionBox()))
                        mob.stop(CollisionMessage.getMessage());
                }
            }
        }
    }
}
