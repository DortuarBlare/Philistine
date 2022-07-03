package managers;

import content.Storage;
import levels.*;
import mobs.*;
import objects.*;
import objects.Object;
import physics.AABB;
import physics.CollisionMessage;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import static org.lwjgl.opengl.GL11.*;

public class LevelManager {
    public static Level currentLevel;

    static {
        currentLevel = new MainMenuLevel();
    }

    public static void update() {
        currentLevel.update();
        currentLevel.draw();
        // Переключение уровня
        switch (level) {
            case "MainMenu": {
                glBindTexture(GL_TEXTURE_2D, textureMap.get("MainMenu")); // Фон главного меню
                createQuadTexture(0, 0, 1536, 360);

                // Анимация охранника
                if (guard_i == 11) guard_i = 1;
                glBindTexture(GL_TEXTURE_2D, textureMap.get("guard_stand_0" + guard_i));
                createQuadTexture(30, 190, 62, 224);
                if (guard_g == 10) {
                    guard_i++;
                    guard_g = 0;
                }
                guard_g++;

                glBindTexture(GL_TEXTURE_2D, textureMap.get("Philistine"));
                createQuadTexture(SingletonPlayer.player.getX() - 68, SingletonPlayer.player.getY() - 156, SingletonPlayer.player.getX() + 64 + 68, SingletonPlayer.player.getY() - 100);
                glBindTexture(GL_TEXTURE_2D, textureMap.get("Press_enter"));
                createQuadTexture(SingletonPlayer.player.getX() - 18, SingletonPlayer.player.getY() - 100, SingletonPlayer.player.getX() + 82, SingletonPlayer.player.getY() - 72);
                if (SingletonPlayer.player.getX() == 1186) forMainMenu = false;
                else if (SingletonPlayer.player.getX() == 290) forMainMenu = true;
                if (forMainMenu) {
                    glTranslated(-1, 0, 0);
                    SingletonPlayer.player.setForPlacingCamera(SingletonPlayer.player.getForPlacingCamera() + 1);
                    SingletonPlayer.player.setMainMenuDirection(true);
                }
                else {
                    glTranslated(1, 0, 0);
                    SingletonPlayer.player.setForPlacingCamera(SingletonPlayer.player.getForPlacingCamera() - 1);
                    SingletonPlayer.player.setMainMenuDirection(false);
                }
                break;
            }
            case "Town": {
                glBindTexture(GL_TEXTURE_2D, textureMap.get("MainMenu")); // Фон главного меню
                createQuadTexture(0, 0, 1536, 360);

                // Анимация охранника
                if (guard_i == 11) guard_i = 1;
                glBindTexture(GL_TEXTURE_2D, textureMap.get("guard_stand_0" + guard_i));
                createQuadTexture(30, 190, 62, 224);
                if (guard_g == 10) {
                    guard_i++;
                    guard_g = 0;
                }
                guard_g++;

                // Левая граница города
                if (SingletonPlayer.player.getX() <= 48) SingletonPlayer.player.stopLeft();
                if (SingletonPlayer.player.getX() <= 50) {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("you_Shall_Not_Pass"));
                    createQuadTexture(34, 136, 94, 195);
                }

                // Переход в данж
                if (SingletonPlayer.player.getX() + 32 == 1519) {
                    SingletonPlayer.player.setDialogBubble(true);
                    if (SingletonPlayer.player.isDialogBubbleChoice())
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("enterTheDungeon_Yes"));
                    else
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("enterTheDungeon_No"));
                    createQuadTexture(SingletonPlayer.player.getX() - 20, SingletonPlayer.player.getY() - 55, SingletonPlayer.player.getX() + 40, SingletonPlayer.player.getY());
                }

                if (key_E_Pressed) {
                    // Вхождение в таверну
                    if (SingletonPlayer.player.getX() + 32 > 305 && SingletonPlayer.player.getX() + 32 < 341) {
                        level = "tavern";
                        glTranslated(SingletonPlayer.player.getForPlacingCamera(), 0, 0);
                        SingletonPlayer.player.setForPlacingCamera(3);
                        backgroundMusic.stop(soundMap.get("mainMenuTheme"));
                        backgroundMusic.play(soundMap.get("tavernTheme"));

                        // Обновление хитбоксов стен для tavern
                        for (int i = 0, j = 0; i < 13; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.tavernLevelWalls[j], Storage.tavernLevelWalls[j + 1],
                                    Storage.tavernLevelWalls[j + 2], Storage.tavernLevelWalls[j + 3]);
                        }
                        doorSound.play(soundMap.get("doorOpen"));
                        SingletonPlayer.player.setX(210);
                        SingletonPlayer.player.setY(254);
                        SingletonPlayer.player.setMoveDirection("up");
                    }
                    // Вхождение в кузницу
                    else if (SingletonPlayer.player.getX() + 32 > 620 && SingletonPlayer.player.getX() + 32 < 651) {
                        level = "forge";
                        glTranslated(SingletonPlayer.player.getForPlacingCamera(), 0, 0);
                        SingletonPlayer.player.setForPlacingCamera(315);

                        // Обновление хитбоксов стен для forge
                        for (int i = 0, j = 0; i < 13; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.forgeLevelWalls[j], Storage.forgeLevelWalls[j + 1],
                                    Storage.forgeLevelWalls[j + 2], Storage.forgeLevelWalls[j + 3]);
                        }
                        doorSound.play(soundMap.get("doorOpen"));
                        backgroundMusic.stop(soundMap.get("townTheme"));
                        backgroundMusic.play(soundMap.get("forgeTheme"));
                        SingletonPlayer.player.setX(368);
                        SingletonPlayer.player.setY(254);
                        SingletonPlayer.player.setMoveDirection("up");
                    }
                }
                key_E_Pressed = false;
                break;
            }
            case "forge": {
                glBindTexture(GL_TEXTURE_2D, textureMap.get("forge")); // Фон кузницы
                createQuadTexture(0, 0, 640, 360);

                // Анимация печки
                if (forgeFurnace_i == 4) forgeFurnace_i = 1;
                glBindTexture(GL_TEXTURE_2D, textureMap.get("furnace_burn_0" + forgeFurnace_i));
                createQuadTexture(223, 142, 259, 173);
                if (forgeFurnace_g == 12) {
                    forgeFurnace_i++;
                    forgeFurnace_g = 0;
                }
                forgeFurnace_g++;

                // Отрисовка кузнеца и остановка около прилавка
                glBindTexture(GL_TEXTURE_2D, textureMap.get("blacksmith_" + blacksmith.getMoveDirection() + "_move_0" + blacksmith.getAnimationTime()));
                createQuadTexture(blacksmith.getX(), blacksmith.getY(), blacksmith.getX() + 64, blacksmith.getY() + 64);
                blacksmith.simulateBehavior();
                if (blacksmith.isWaitingTaskStarted()) {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("emotion_question"));
                    createQuadTexture(blacksmith.getX() + 31, blacksmith.getY(), blacksmith.getX() + 51, blacksmith.getY() + 20);
                }

                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall6")) || AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall4")))
                    SingletonPlayer.player.stopRight();
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall2")))
                    SingletonPlayer.player.stopLeft();
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall3")))
                    SingletonPlayer.player.stopUp();
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall1")) || AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall5")))
                    SingletonPlayer.player.stopDown();

                if (shop.loot.size() != 0) {
                    shopObjectList.addAll(shop.loot);
                    shop.loot.clear();
                }

                for (Object object : shopObjectList) {
                    if (!object.isDrawAble()) continue;
                    else glBindTexture(GL_TEXTURE_2D, textureMap.get(object.getTexture()));
                    createQuadTexture(object.getMinX(), object.getMinY(), object.getMaxX(), object.getMaxY());
                }

                for (int i = 0; i < shopObjectList.size(); i++) {
                    if (!shopObjectList.get(i).isDrawAble()) continue;
                    if (shopObjectList.get(i) instanceof Armor && AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(),
                            shopObjectList.get(i).getCollisionBox())) {
                        Armor tempArmor = (Armor) shopObjectList.get(i);
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("price"));
                        createQuadTexture(tempArmor.getMinX() + 15, tempArmor.getCollisionBox().getMin().y - 27, tempArmor.getMinX() + 45, tempArmor.getCollisionBox().getMin().y);
                        int tempX0, tempX1, tempY0, tempY1;
                        tempX0 = tempArmor.getMinX() + 37 - 7;
                        tempX1 = tempArmor.getMinX() + 37;
                        tempY0 = tempArmor.getCollisionBox().getMin().y - 13;
                        tempY1 = tempArmor.getCollisionBox().getMin().y - 3;
                        int tempPrice = tempArmor.getPrice();
                        for (int j = 0; j < getCountsOfDigits(tempArmor.getPrice()); j++) {
                            switch (tempPrice % 10) {
                                case 0:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_0"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;
                                case 1:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_1"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;
                                case 2:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_2"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;
                                case 3:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_3"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;
                                case 4:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_4"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;
                                case 5:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_5"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;
                                case 6:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_6"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;
                                case 7:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_7"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;
                                case 8:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_8"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;
                                case 9:
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("number_9"));
                                    createQuadTexture(tempX0, tempY0, tempX1, tempY1);
                                    break;

                            }
                            tempPrice = tempPrice / 10;
                            tempX0 -= 7;
                            tempX1 -= 7;
                        }
                        if (key_E_Pressed && SingletonPlayer.player.getMoney() >= tempArmor.getPrice()) {
                            SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() - tempArmor.getPrice());
                            SingletonPlayer.player.setArmor(tempArmor);
                            shopObjectList.get(i).setIsLying(false);
                            shopObjectList.remove(i);
                            armorChange.play(soundMap.get("changingArmor"));
                        }
                    }
                }
                key_E_Pressed = false;

                // Проверка выхода в город
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceFromForgeToTown"))) {
                    level = "Town";

                    // Обновление хитбоксов стен для города
                    for (int i = 0, j = 0; i < 13; i++, j += 4) {
                        aabbMap.get("wall" + i).update(Storage.townLevelWalls[j], Storage.townLevelWalls[j + 1],
                                Storage.townLevelWalls[j + 2], Storage.townLevelWalls[j + 3]);
                    }
                    doorSound.play(soundMap.get("doorOpen"));
                    SingletonPlayer.player.getStepSound().stop(SingletonPlayer.player.getPlayerSounds().get("stepWood"));
                    backgroundMusic.stop(soundMap.get("forgeTheme"));
                    backgroundMusic.play(soundMap.get("townTheme"));
                    glTranslated(-SingletonPlayer.player.getForPlacingCamera(), 0, 0);
                    SingletonPlayer.player.setX(605);
                    SingletonPlayer.player.setY(192);
                    SingletonPlayer.player.setMoveDirection("right");
                }
                break;
            }
            case "FirstLevel": {
                glBindTexture(GL_TEXTURE_2D, textureMap.get("level0")); // Фон первого уровня
                createQuadTexture(0, 0, 640, 360);

                if (!firstLevelMobSpawning) {
                    firstLevelMobSpawning = true;
                    mobTimer.schedule(mobSpawnTask, 0, 5000);
                }

                // Отрисовка всех объектов
                for (Object object : firstLevelObjectList) {
                    if (!object.isDrawAble()) continue;
                    if (object instanceof Coin) {
                        Coin coin = (Coin) object;
                        if (!coin.isAnimationTaskStarted())
                            coin.getTimer().schedule(coin.getAnimationTask(), 0, 120);
                        coin.setTexture("coin_0" + coin.getAnimationTime());
                        glBindTexture(GL_TEXTURE_2D, textureMap.get(coin.getTexture()));
                    }
                    else if (object instanceof Container) {
                        Container container = (Container) object;
                        glBindTexture(GL_TEXTURE_2D, textureMap.get(container.getTexture() + container.getState()));
                    }
                    else if (object instanceof Gate) {
                        Gate gate = (Gate) object;
                        if (canChangeLevel && firstLevelMobSpawningStopped && gate.getMinY() == 243) {
                            gate.setFinalY(147);
                            gate.update();
                        }
                        glBindTexture(GL_TEXTURE_2D, textureMap.get(gate.getTexture()));
                    }
                    else glBindTexture(GL_TEXTURE_2D, textureMap.get(object.getTexture()));
                    createQuadTexture(object.getMinX(), object.getMinY(), object.getMaxX(), object.getMaxY());
                }

                // Подбор всех возможных предметов с клавишей Е
                if (key_E_Pressed) {
                    for (int i = 0; i < firstLevelObjectList.size(); i++) {
                        if (firstLevelObjectList.get(i) instanceof Armor) {
                            Armor changingArmor = (Armor) firstLevelObjectList.get(i);
                            if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), changingArmor.getCollisionBox())) {
                                Armor playerArmor = SingletonPlayer.player.getArmorType(changingArmor);
                                changingArmor.setIsLying(false);
                                SingletonPlayer.player.setArmor(changingArmor);
                                armorChange.play(soundMap.get("changingArmor"));
                                changingArmor = playerArmor;
                                if (changingArmor.getTexture().equals("nothing")) firstLevelObjectList.remove(i);
                                else {
                                    changingArmor.setMinX(SingletonPlayer.player.getCollisionBox().getMin().x - 20);
                                    changingArmor.setMinY(SingletonPlayer.player.getCollisionBox().getMin().y - 30);
                                    changingArmor.setMaxX(SingletonPlayer.player.getCollisionBox().getMin().x + 44);
                                    changingArmor.setMaxY(SingletonPlayer.player.getCollisionBox().getMin().y + 34);
                                    changingArmor.setIsLying(true);
                                    changingArmor.correctCollisionBox();
                                    firstLevelObjectList.set(i, changingArmor);
                                }
                                break;
                            }
                        } else if (firstLevelObjectList.get(i) instanceof Weapon) {
                            Weapon changingWeapon = (Weapon) firstLevelObjectList.get(i);
                            if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), changingWeapon.getCollisionBox())) {
                                Weapon playerWeapon = SingletonPlayer.player.getWeapon();
                                changingWeapon.setIsLying(false);
                                changingWeapon.resize();
                                SingletonPlayer.player.setWeapon(changingWeapon);
                                SingletonPlayer.player.updateDamage();
                                armorChange.play(soundMap.get("changingArmor"));
                                changingWeapon = playerWeapon;
                                if (changingWeapon.getTexture().equals("nothing")) firstLevelObjectList.remove(i);
                                else {
                                    changingWeapon.setMinX(SingletonPlayer.player.getCollisionBox().getMin().x - 64);
                                    changingWeapon.setMinY(SingletonPlayer.player.getCollisionBox().getMin().y - 64);
                                    changingWeapon.setMaxX(SingletonPlayer.player.getCollisionBox().getMin().x + 128);
                                    changingWeapon.setMaxY(SingletonPlayer.player.getCollisionBox().getMin().y + 128);
                                    changingWeapon.setIsLying(true);
                                    changingWeapon.correctCollisionBox();
                                    firstLevelObjectList.set(i, changingWeapon);
                                }
                                break;
                            }
                        } else if (firstLevelObjectList.get(i) instanceof Lever) {
                            Lever change = (Lever) firstLevelObjectList.get(i);
                            if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), change.getCollisionBox())) {
                                change.setState("On");
                                break;
                            }
                        }
                    }
                    key_E_Pressed = false;
                }

                // Все операции с мобами
                for (int i = 1; i < SingletonMobs.mobList.size(); i++) {
                    if (SingletonMobs.mobList.get(i) instanceof Slime) {
                        Slime slime = (Slime) SingletonMobs.mobList.get(i);
                        if (!slime.isDead()) {
                            slime.update();
                            glBindTexture(GL_TEXTURE_2D, textureMap.get("slime_" + slime.getMoveDirection() + "_0" + slime.getAnimationTime()));
                        } else
                            glBindTexture(GL_TEXTURE_2D, textureMap.get("slime_death_0" + slime.getDeathAnimationTime()));
                        createQuadTexture(slime.getX(), slime.getY(), slime.getX() + 18, slime.getY() + 12);
                    }
                }

                // Проверка всех мобов на столкновение со стенами, объектами. Столкновения объектов с объектами
                for (int i = 0; i < SingletonMobs.mobList.size(); i++) {
                    Mob mob = SingletonMobs.mobList.get(i);

                    if (!mob.isDead()) {
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")))
                            mob.stopRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.stopLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")))
                            mob.stopUp();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")))
                            mob.stopDown();
                    }

                    for (int j = 0; j < firstLevelObjectList.size(); j++) {
                        Object object = firstLevelObjectList.get(j);
                        if (!(object instanceof Furniture) && !(object instanceof Container) && !(object instanceof Gate)) {
                            // Столкновение объектов со стенами
                            if (AABB.AABBvsAABB(object.getCollisionBox(), aabbMap.get("wall1")))
                                object.stopRight();
                            if (AABB.AABBvsAABB(object.getCollisionBox(), aabbMap.get("wall4")))
                                object.stopLeft();
                            if (AABB.AABBvsAABB(object.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(object.getCollisionBox(), aabbMap.get("wall2")))
                                object.stopUp();
                            if (AABB.AABBvsAABB(object.getCollisionBox(), aabbMap.get("wall3")))
                                object.stopDown();

                            // Столкновение объектов с объектами
                            for (int k = j + 1; k < firstLevelObjectList.size(); k++) {
                                Object object2 = firstLevelObjectList.get(k);
                                if (!(object2 instanceof Furniture) && !(object2 instanceof Container) && !(object2 instanceof Gate)) {
                                    if (AABB.AABBvsAABB(object.getCollisionBox(), object2.getCollisionBox()))
                                        object.moveLeft();
                                }
                            }
                        }

                        if (!mob.isDead() && AABB.AABBvsAABB(mob.getCollisionBox(), object.getCollisionBox())) {
                            if ((mob instanceof Player) && (object instanceof Coin)) {
                                object.getTimer().cancel();
                                object.getTimer().purge();
                                firstLevelObjectList.remove(object);
                                SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() + 10);
                                coinSound.play(soundMap.get("pickedCoin"));
                                break;
                            } else if ((mob instanceof Player) && (object instanceof Potion)) {
                                object.getTimer().cancel();
                                object.getTimer().purge();
                                firstLevelObjectList.remove(object);
                                if (SingletonPlayer.player.getHealth() > 90) SingletonPlayer.player.setHealth(100);
                                else SingletonPlayer.player.setHealth(SingletonPlayer.player.getHealth() + 10);
                                potionSound.play(soundMap.get("pickedPotion"));
                                break;
                            }
                        }

                        if (object.isNoclip()) continue;
                        if (!mob.isDead() && AABB.AABBvsAABB2(mob.getCollisionBox(), object.getCollisionBox()))
                            mob.stop(CollisionMessage.getMessage());
                    }
                }

                // Проверка возможности перехода на другой уровень
                if (firstLevelMobSpawningStopped) {
                    for (Mob mob : SingletonMobs.mobList) {
                        if (mob.isDead() && !(mob instanceof Player)) deathsCounter++;
                    }
                    if (deathsCounter == SingletonMobs.mobList.size() - 1) canChangeLevel = true;
                    else canChangeLevel = false;
                    deathsCounter = 0;
                }

                // Проверка перехода на второй уровень
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceToSecondLevel"))) {
                    if (canChangeLevel) {
                        SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player)); // Удаление трупов

                        // Обновление хитбоксов стен для второго уровня
                        for (int i = 0, j = 0; i < 13; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.dungeonBranchingLevelWalls[j], Storage.dungeonBranchingLevelWalls[j + 1],
                                    Storage.dungeonBranchingLevelWalls[j + 2], Storage.dungeonBranchingLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(-12);
                        SingletonPlayer.player.setY(225);
                        level = "SecondLevel";
                        canChangeLevel = false;
                    }
                }
                break;
            }
            case "SecondLevel": {
                glBindTexture(GL_TEXTURE_2D, textureMap.get("level1")); // Фон второго уровня
                createQuadTexture(0, 0, 640, 360);

                if (!secondLevelMobSpawning) {
                    secondLevelMobSpawning = true;
                    mobTimer.schedule(mobSpawnTask, 3000, 10000);
                }

                // Отрисовка всех объектов
                for (Object object : secondLevelObjectList) {
                    if (!object.isDrawAble()) continue;
                    if (object instanceof Gate) {
                        Gate gate = (Gate) object;
                        if (canChangeLevel && secondLevelMobSpawningStopped && gate.getMinY() == 195) {
                            gate.setFinalY(99);
                            gate.update();
                        }
                        glBindTexture(GL_TEXTURE_2D, textureMap.get(gate.getTexture()));
                    }
                    else if (object.getTexture().equals("gate3") && canChangeLevel && secondLevelMobSpawningStopped) {
                        object.setIsLying(false);
                        object.setNoclip(true);
                    }
                    glBindTexture(GL_TEXTURE_2D, textureMap.get(object.getTexture()));
                    createQuadTexture(object.getMinX(), object.getMinY(), object.getMaxX(), object.getMaxY());
                }

                // Все операции с мобами
                for (int i = 1; i < SingletonMobs.mobList.size(); i++) {
                    if (SingletonMobs.mobList.get(i) instanceof Spider) {
                        Spider spider = (Spider) SingletonMobs.mobList.get(i);
                        if (!spider.isDead()) {
                            spider.update();
                            if (spider.isAttack()) glBindTexture(GL_TEXTURE_2D, textureMap.get("spider_" + spider.getMoveDirection() + "_attack_0" + spider.getHitAnimationTime()));
                            else glBindTexture(GL_TEXTURE_2D, textureMap.get("spider_" + spider.getMoveDirection() + "_move_0" + spider.getAnimationTime()));
                        }
                        else glBindTexture(GL_TEXTURE_2D, textureMap.get("spider_death_0" + spider.getDeathAnimationTime()));
                        createQuadTexture(spider.getX(), spider.getY(), spider.getX() + 64, spider.getY() + 64);
                    }
                }

                // Проверка всех мобов на столкновение со стенами
                for (int i1 = 0; i1 < SingletonMobs.mobList.size(); i1++) {
                    Mob mob = SingletonMobs.mobList.get(i1);
                    if (!mob.isDead()) {
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall8")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall11")))
                            mob.stopRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall5")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall7")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall10")))
                            mob.stopLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall9")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall12")))
                            mob.stopUp();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall6")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.stopDown();

                        for (Object object : secondLevelObjectList) {
                            // Столкновение мобов с !noclip объектами
                            if (AABB.AABBvsAABB2(mob.getCollisionBox(), object.getCollisionBox()) && !object.isNoclip())
                                mob.stop(CollisionMessage.getMessage());
                        }
                    }
                }

                // Проверка возможности перехода на другой уровень
                if (secondLevelMobSpawningStopped) {
                    for (Mob mob : SingletonMobs.mobList) {
                        if (mob.isDead() && !(mob instanceof Player)) deathsCounter++;
                    }
                    if (deathsCounter == SingletonMobs.mobList.size() - 1) canChangeLevel = true;
                    else canChangeLevel = false;
                    deathsCounter = 0;
                }

                // Проверка перехода на первый уровень
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceToFirstLevel"))) {
                    if (canChangeLevel) {
                        SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player)); // Удаление трупов

                        // Обновление хитбоксов стен для первого уровня
                        for (int i = 0, j = 0; i < 13; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.dungeonVestibuleLevelWalls[j], Storage.dungeonVestibuleLevelWalls[j + 1],
                                    Storage.dungeonVestibuleLevelWalls[j + 2], Storage.dungeonVestibuleLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(580);
                        SingletonPlayer.player.setY(281);
                        level = "FirstLevel";
                    }
                }

                // Проверка перехода на 3 уровень
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceToThirdLevel"))) {
                    if (canChangeLevel) {
                        SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player)); // Удаление трупов

                        // Обновление хитбоксов стен для 3 уровня
                        for (int i = 0, j = 0; i < 13; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.treasureLevelWalls[j], Storage.treasureLevelWalls[j + 1],
                                    Storage.treasureLevelWalls[j + 2], Storage.treasureLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(240);
                        SingletonPlayer.player.setY(120);
                        SingletonPlayer.player.setMoveDirection("down");
                        level = "ThirdLevel";
                    }
                }

                // Проверка перехода на 4 уровень
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceToForthLevel"))) {
                    if (canChangeLevel) {
                        SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player)); // Удаление трупов

                        // Обновление хитбоксов стен для 4 уровня
                        for (int i = 0, j = 0; i < 13; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.bossLevelWalls[j], Storage.bossLevelWalls[j + 1],
                                    Storage.bossLevelWalls[j + 2], Storage.bossLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(180);
                        SingletonPlayer.player.setY(120);
                        SingletonPlayer.player.setMoveDirection("down");
                        level = "ForthLevel";
                        canChangeLevel = false;
                    }
                }
                break;
            }
            case "ThirdLevel": {
                glBindTexture(GL_TEXTURE_2D, textureMap.get("level2"));
                createQuadTexture(0, 0, 640, 360);

                // Отрисовка всех объектов
                for (Object object : thirdLevelObjectList) {
                    if (!object.isDrawAble()) continue;
                    if (object instanceof Coin) {
                        Coin coin = (Coin) object;
                        if (!coin.isAnimationTaskStarted())
                            coin.getTimer().schedule(coin.getAnimationTask(), 0, 120);
                        coin.setTexture("coin_0" + coin.getAnimationTime());
                        glBindTexture(GL_TEXTURE_2D, textureMap.get(coin.getTexture()));
                    }
                    else if (object instanceof Container) {
                        Container container = (Container) object;
                        glBindTexture(GL_TEXTURE_2D, textureMap.get(container.getTexture() + container.getState()));
                    }
                    else glBindTexture(GL_TEXTURE_2D, textureMap.get(object.getTexture()));
                    createQuadTexture(object.getMinX(), object.getMinY(), object.getMaxX(), object.getMaxY());
                }

                // Столкновение игрока со стенами
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall1")) || AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall6")))
                    SingletonPlayer.player.stopRight();
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall3")) || AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall5")))
                    SingletonPlayer.player.stopLeft();
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall4")))
                    SingletonPlayer.player.stopUp();
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall2")))
                    SingletonPlayer.player.stopDown();

                for (int i = 0; i < thirdLevelObjectList.size(); i++) {
                    Object object = thirdLevelObjectList.get(i);

                    // Столкновение игрока с !noclip объектами
                    if (AABB.AABBvsAABB2(SingletonPlayer.player.getCollisionBox(), object.getCollisionBox()) && !object.isNoclip())
                        SingletonPlayer.player.stop(CollisionMessage.getMessage());

                    // Столкновение объектов со стенами
                    if (object.isNoclip() && AABB.AABBvsAABB(object.getCollisionBox(), aabbMap.get("wall3")))
                        object.stopLeft();
                    if (object.isNoclip() && AABB.AABBvsAABB(object.getCollisionBox(), aabbMap.get("wall1")))
                        object.stopRight();
                    if (object.isNoclip() && AABB.AABBvsAABB(object.getCollisionBox(), aabbMap.get("wall0")))
                        object.stopUp();
                    if (object.isNoclip() && AABB.AABBvsAABB(object.getCollisionBox(), aabbMap.get("wall2")))
                        object.stopDown();

                    // Столкновение объектов с объектами
                    for (int j = i + 1; j < thirdLevelObjectList.size(); j++) {
                        Object object2 = thirdLevelObjectList.get(j);
                        if (AABB.AABBvsAABB(object.getCollisionBox(), object2.getCollisionBox())) {
                            if (object.isNoclip() && object2.isNoclip()) {
                                object.moveLeft();
                                object2.moveRight();
                            }
                            else if (object.isNoclip() && !object2.isNoclip()) object.moveRight();
                            else if (!object.isNoclip() && object2.isNoclip()) object2.moveRight();
                        }
                    }

                    // Подбор монет и зелий игроком
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), object.getCollisionBox())) {
                        if (object instanceof Coin) {
                            object.getTimer().cancel();
                            object.getTimer().purge();
                            thirdLevelObjectList.remove(object);
                            SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() + 10);
                            coinSound.play(soundMap.get("pickedCoin"));
                            break;
                        } else if (object instanceof Potion) {
                            object.getTimer().cancel();
                            object.getTimer().purge();
                            thirdLevelObjectList.remove(object);
                            if (SingletonPlayer.player.getHealth() > 90) SingletonPlayer.player.setHealth(100);
                            else SingletonPlayer.player.setHealth(SingletonPlayer.player.getHealth() + 10);
                            potionSound.play(soundMap.get("pickedPotion"));
                            break;
                        }
                    }
                }

                // Подбор всех возможных предметов с клавишей Е
                if (key_E_Pressed) {
                    for (int i = 0; i < thirdLevelObjectList.size(); i++) {
                        if (thirdLevelObjectList.get(i) instanceof Armor) {
                            Armor changingArmor = (Armor) thirdLevelObjectList.get(i);
                            if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), changingArmor.getCollisionBox())) {
                                Armor playerArmor = SingletonPlayer.player.getArmorType(changingArmor);
                                changingArmor.setIsLying(false);
                                SingletonPlayer.player.setArmor(changingArmor);
                                armorChange.play(soundMap.get("changingArmor"));
                                changingArmor = playerArmor;
                                if (changingArmor.getTexture().equals("nothing")) thirdLevelObjectList.remove(i);
                                else {
                                    changingArmor.setMinX(SingletonPlayer.player.getCollisionBox().getMin().x - 20);
                                    changingArmor.setMinY(SingletonPlayer.player.getCollisionBox().getMin().y - 30);
                                    changingArmor.setMaxX(SingletonPlayer.player.getCollisionBox().getMin().x + 44);
                                    changingArmor.setMaxY(SingletonPlayer.player.getCollisionBox().getMin().y + 34);
                                    changingArmor.setIsLying(true);
                                    changingArmor.correctCollisionBox();
                                    thirdLevelObjectList.set(i, changingArmor);
                                }
                                break;
                            }
                        } else if (thirdLevelObjectList.get(i) instanceof Weapon) {
                            Weapon changingWeapon = (Weapon) thirdLevelObjectList.get(i);
                            if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), changingWeapon.getCollisionBox())) {
                                Weapon playerWeapon = SingletonPlayer.player.getWeapon();
                                changingWeapon.setIsLying(false);
                                changingWeapon.resize();
                                SingletonPlayer.player.setWeapon(changingWeapon);
                                armorChange.play(soundMap.get("changingArmor"));
                                changingWeapon = playerWeapon;
                                if (changingWeapon.getTexture().equals("nothing")) thirdLevelObjectList.remove(i);
                                else {
                                    changingWeapon.setMinX(SingletonPlayer.player.getCollisionBox().getMin().x - 64);
                                    changingWeapon.setMinY(SingletonPlayer.player.getCollisionBox().getMin().y - 64);
                                    changingWeapon.setMaxX(SingletonPlayer.player.getCollisionBox().getMin().x + 128);
                                    changingWeapon.setMaxY(SingletonPlayer.player.getCollisionBox().getMin().y + 128);
                                    changingWeapon.setIsLying(true);
                                    changingWeapon.correctCollisionBox();
                                    thirdLevelObjectList.set(i, changingWeapon);
                                }
                                break;
                            }
                        } else if (thirdLevelObjectList.get(i) instanceof Container) {
                            Container change = (Container) thirdLevelObjectList.get(i);
                            if (AABB.toInteract(SingletonPlayer.player.getCollisionBox(), change.getCollisionBox())) {
                                if (change.getState().equals("Closed")) {
                                    if (change.getIsNeedKey() && SingletonPlayer.player.getKeys() > 0) {
                                        SingletonPlayer.player.setKeys(SingletonPlayer.player.getKeys() - 1);
                                        containerSound.play(soundMap.get("openChest"));
                                    } else containerSound.play(soundMap.get("openBoxBarrel"));
                                    change.setState("Opened");
                                    if (change.loot.size() != 0) {
                                        thirdLevelObjectList.addAll(change.loot);
                                        change.loot.clear();
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                key_E_Pressed = false;

                // Проверка перехода на второй уровень
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceFromThirdToSecondLevel"))) {
                    level = "SecondLevel";
                    // Обновление хитбоксов стен для второго уровня
                    for (int i = 0, j = 0; i < 13; i++, j += 4) {
                        aabbMap.get("wall" + i).update(Storage.dungeonBranchingLevelWalls[j], Storage.dungeonBranchingLevelWalls[j + 1],
                                Storage.dungeonBranchingLevelWalls[j + 2], Storage.dungeonBranchingLevelWalls[j + 3]);
                    }
                    SingletonPlayer.player.setX(180);
                    SingletonPlayer.player.setY(120);
                    SingletonPlayer.player.setMoveDirection("down");
                }
                break;
            }
            case "ForthLevel": {
                glBindTexture(GL_TEXTURE_2D, textureMap.get("level3"));
                createQuadTexture(0, 0, 640, 360);

                // Отрисовка объектов
                for (Object object : forthLevelObjectList) {
                    if (!object.isDrawAble()) continue;
                    glBindTexture(GL_TEXTURE_2D, textureMap.get(object.getTexture()));
                    createQuadTexture(object.getMinX(), object.getMinY(), object.getMaxX(), object.getMaxY());
                }

                if (!fourthLevelMobSpawning) {
                    fourthLevelMobSpawning = true;
                    mobTimer.schedule(mobSpawnTask, 3000, 10000);
                }

                // Все операции с мобами
                for (int i = 1; i < SingletonMobs.mobList.size(); i++) {
                    if (SingletonMobs.mobList.get(i) instanceof Imp) {
                        Imp imp = (Imp) SingletonMobs.mobList.get(i);
                        if (!imp.isDead()) {
                            imp.update();
                            // HealthBar
                            int tempHealth = imp.getHealth() % 10 == 0 ? imp.getHealth() : imp.getHealth() - (imp.getHealth() % 10) + 10;
                            if (tempHealth > 0) {
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("enemyHp" + tempHealth));
                                createQuadTexture(197, 0, 442, 30);
                            }

                            if (imp.isAttack()) glBindTexture(GL_TEXTURE_2D, textureMap.get("imp_" + imp.getMoveDirection() + "_attack_0" + imp.getHitAnimationTime()));
                            else glBindTexture(GL_TEXTURE_2D, textureMap.get("imp_" + imp.getMoveDirection() + "_move_0" + imp.getAnimationTime()));
                        }
                        else {
                            glBindTexture(GL_TEXTURE_2D, textureMap.get("imp_death_0" + imp.getDeathAnimationTime()));
                            for (Object object : forthLevelObjectList) {
                                if (object.getTexture().equals("gate2") ||
                                        object.getTexture().equals("gate3")) {
                                    object.setIsLying(false);
                                    object.setNoclip(true);
                                }
                            }
                        }
                        createQuadTexture(imp.getX(), imp.getY(), imp.getX() + 64, imp.getY() + 64);
                    }
                }

                // Столкновение мобов со стенами
                for (Mob mob : SingletonMobs.mobList) {
                    if (!mob.isDead()) {
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall8")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall6")))
                            mob.stopRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall7")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall5")))
                            mob.stopLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall9")))
                            mob.stopUp();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.stopDown();

                        for (Object object : forthLevelObjectList) {
                            // Столкновение мобов с !noclip объектами
                            if (AABB.AABBvsAABB2(mob.getCollisionBox(), object.getCollisionBox()) && !object.isNoclip())
                                mob.stop(CollisionMessage.getMessage());
                        }
                    }
                }

                // Проверка возможности перехода на другой уровень
                if (fourthLevelMobSpawningStopped) {
                    for (Mob mob : SingletonMobs.mobList) {
                        if (mob.isDead() && !(mob instanceof Player)) deathsCounter++;
                    }
                    if (deathsCounter == SingletonMobs.mobList.size() - 1) canChangeLevel = true;
                    else canChangeLevel = false;
                    deathsCounter = 0;
                }

                // Проверка перехода на второй уровень
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceFromForthToSecondLevel"))) {
                    if (canChangeLevel) {
                        SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player)); // Удаление трупов

                        // Обновление хитбоксов стен для второго уровня
                        for (int i = 0, j = 0; i < 13; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.dungeonBranchingLevelWalls[j], Storage.dungeonBranchingLevelWalls[j + 1],
                                    Storage.dungeonBranchingLevelWalls[j + 2], Storage.dungeonBranchingLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(240);
                        SingletonPlayer.player.setY(120);
                        SingletonPlayer.player.setMoveDirection("down");
                        level = "SecondLevel";
                    }
                }
                // Проверка перехода в город
                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceFromFourthToTownLevel"))) {
                    if (canChangeLevel) {
                        SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player)); // Удаление трупов

                        // Обновление хитбоксов стен для города
                        for (int i = 0, j = 0; i < 7; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.townLevelWalls[j], Storage.townLevelWalls[j + 1],
                                    Storage.townLevelWalls[j + 2], Storage.townLevelWalls[j + 3]);
                        }

                        // Обновление всех объектов
                        firstLevelObjectList.clear();
                        secondLevelObjectList.clear();
                        thirdLevelObjectList.clear();
                        forthLevelObjectList.clear();
                        addAllObjects();
                        shopObjectList.clear();
                        shop = new Shop();
                        firstLevelMobSpawning = secondLevelMobSpawning = fourthLevelMobSpawning = false;
                        firstLevelMobSpawningStopped = secondLevelMobSpawningStopped = fourthLevelMobSpawningStopped = false;
                        glTranslated(-689, 0, 0);
                        backgroundMusic.stop(soundMap.get("dungeonAmbient1"));
                        backgroundMusic.play(soundMap.get("townTheme"));
                        backgroundMusic.changeVolume(0.02f);
                        SingletonPlayer.player.setForPlacingCamera(689);
                        SingletonPlayer.player.setX(979);
                        SingletonPlayer.player.setY(192);
                        SingletonPlayer.player.setSpeed(1);
                        SingletonPlayer.player.setMoveDirection("left");
                        level = "Town";
                        canChangeLevel = false;
                    }
                }
                break;
            }
        }
    }

    public static void mainMenuToTown() {
        currentLevel = new TownLevel();
        currentLevel.init();

        SoundManager.musicSource.stop(Storage.soundMap.get("mainMenuTheme"));
        SoundManager.musicSource.play(Storage.soundMap.get("townTheme"));
        SoundManager.musicSource.changeVolume(0.02f);
    }

    public static void tavernToTown() {
        currentLevel = new TownLevel();
        currentLevel.init();

        // Обновление хитбоксов стен для города
        for (int i = 0; i < 13; i++)
            Storage.aabbMap.get("wall" + i).update(0, 0, 0, 0);

        SoundManager.environmentSoundSource.play(Storage.soundMap.get("doorOpen"));
        SingletonPlayer.player.getStepSound().stop(SingletonPlayer.player.getPlayerSounds().get("stepWood"));
        SoundManager.environmentSoundSource.play(Storage.soundMap.get("tavernTheme"));
        SoundManager.environmentSoundSource.play(Storage.soundMap.get("townTheme"));
        glTranslated(-SingletonPlayer.player.getForPlacingCamera(), 0, 0);
        SingletonPlayer.player.setX(293);
        SingletonPlayer.player.setY(192);
        SingletonPlayer.player.setMoveDirection("right");
    }

    public static void townToDungeon() {
        currentLevel = new DungeonVestibuleLevel();
        currentLevel.init();

        SingletonPlayer.player.setX(SingletonPlayer.player.getX() - 1);
        SingletonPlayer.player.setDialogBubble(false);

        if (SingletonPlayer.player.isDialogBubbleChoice()) {
            SoundManager.musicSource.changeVolume(0.1f);
            SoundManager.musicSource.stop(Storage.soundMap.get("townTheme"));
            SoundManager.musicSource.play(Storage.soundMap.get("dungeonAmbient1"));
            glTranslated(SingletonPlayer.player.getForPlacingCamera(), 0, 0);

            // Установление хитбоксов стен первого уровня
            for (int i = 0, j = 0; i < 7; i++, j += 4) {
                Storage.aabbMap.get("wall" + i).update(
                        Storage.dungeonVestibuleLevelWalls[j],
                        Storage.dungeonVestibuleLevelWalls[j + 1],
                        Storage.dungeonVestibuleLevelWalls[j + 2],
                        Storage.dungeonVestibuleLevelWalls[j + 3]
                );
            }

            SingletonPlayer.player.setX(199);
            SingletonPlayer.player.setY(273);
            SingletonPlayer.player.setSpeed(2);
            SingletonPlayer.player.setMoveDirection("up");
        }
    }

    public static boolean attackAllowed() {
        return
                currentLevel instanceof DungeonVestibuleLevel ||
                currentLevel instanceof DungeonBranchingLevel ||
                currentLevel instanceof TreasureLevel ||
                currentLevel instanceof BossLevel;
    }
}
