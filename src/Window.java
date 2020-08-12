import math.AABB;
import math.CollisionMessage;
import mobs.*;
import objects.Armor;
import objects.Furniture;
import objects.Object;
import org.lwjgl.glfw.*;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long window;
    private ArrayList<Mob> mobList;
    private HashMap<String, Integer> textureMap;
    private HashMap<String, AABB> aabbMap;
    private HashMap<Integer, Object> objectMap;
    private String level = "MainMenu";
    private boolean forScale = true;
    boolean isAttackRight = false, isAttackLeft = false, isAttackUp = false, isAttackDown = false;
    boolean isCheck = false;
    boolean forSkeletonAnimation = true;
    boolean forMainMenu = true;
    boolean forMainTheme = true;
    int mainMenuTheme;
    Source source;
    int forCamera = 0;
    Player player;
    String player_animation, weapon, head, shoulders, torso, belt, hands, legs, feet;

    public void run() {
        System.out.println("Игра запущена");

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        // Работа с экраном
        window = glfwCreateWindow(1920, 1080, "Philistine", glfwGetPrimaryMonitor(), NULL);
        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidMode.width() - pWidth.get(0)) / 2, ((vidMode.height() - pHeight.get(0)) / 2) - 10);
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities(); // создает instance для OpenGL в текущем потоке
        glMatrixMode(GL_PROJECTION); // Выставление камеры
        glLoadIdentity(); // По видимости ненужная строка(что-то с единичной матрицей)
        glOrtho(0, 640, 360, 0, 1, -1); // Камера на место окна
        glMatrixMode(GL_MODELVIEW); // Установка матрицы в состояние ModelView
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // Добавляет прозрачность
        glEnable(GL_BLEND);

        mobList = new ArrayList<>();
        textureMap = new HashMap<String, Integer>();
        aabbMap = new HashMap<String, AABB>();
        objectMap = new HashMap<Integer, Object>();
        objectMap.put(0, new Furniture(false, true,250, 200, 282, 232, new AABB(250, 200, 282, 232), "chestClosed"));
        objectMap.put(1, new Armor("torso", 1, true, true, 300, 100, 364, 164, new AABB(300, 100, 364, 164), "shirt_white"));
        objectMap.put(2, new Armor("legs", 1, true, true, 428, 100, 492, 164, new AABB(428, 100, 492, 164), "pants_greenish"));
        objectMap.put(3, new Armor("feet", 1, true, true, 364, 100, 428, 164, new AABB(364, 100, 428, 164), "shoes_brown"));
        mobList.add(new Player(290, 192, 1, 100, 0, 10));
        mobList.add(new Slime(300, 300, 1, 5, 0, 5));
        mobList.add(new Skeleton(300, 200, 1, 50, 0, 10));
        player = (Player) mobList.get(0);

        // Единичная загрузка всех текстур
        for (int i = 0, id = 0; i < Storage.textureString.length; i++) {
            if (i < 11) id = Texture.loadTexture("healthbar/" + Storage.textureString[i]);
            else id = Texture.loadTexture(Storage.textureString[i]);
            textureMap.put(Storage.textureString[i], id);
        }

        AudioMaster.init();
        AudioMaster.setListenerData();
        mainMenuTheme = AudioMaster.loadSound("MainMenu");
        source = new Source();

        // Единичная загрузка всех хитбоксов
        for(int i = 0; i < Storage.aabbString.length; i++)
            aabbMap.put(Storage.aabbString[i], new AABB());
        // Единичное установление хитбоксов стен первого уровня и переходов м/у уровнями
        for(int i = 0, j = 0; i < 5; i++, j+=4)
            aabbMap.get("wall" + i).update(Storage.firstLevelWalls[j], Storage.firstLevelWalls[j + 1], Storage.firstLevelWalls[j + 2], Storage.firstLevelWalls[j + 3]);
        aabbMap.get("entranceToFirstLevel").update(0, 190, 2, 286);
        aabbMap.get("entranceToSecondLevel").update(638, 238, 640, 335);

        // Клашива ESC на выход(закрытие приложения)
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                AudioMaster.destroy();
                source.delete();
                AL10.alDeleteBuffers(mainMenuTheme);
                glfwSetWindowShouldClose(window, true);
                Player player = (Player) mobList.get(0);
                player.getTimer().cancel();
                player.getTimer().purge();
                Slime slime = (Slime) mobList.get(1);
                slime.getTimerSlime().cancel();
                slime.getTimerSlime().purge();
                Skeleton skeleton = (Skeleton) mobList.get(2);
                skeleton.getTimer().cancel();
                skeleton.getTimer().purge();
            }
            if (level.equals("MainMenu") && key == GLFW_KEY_ENTER && action == GLFW_PRESS) {
                level = "FirstLevel";
                glTranslated(forCamera, 0, 0);
                player.setX(200);
                player.setY(150);
                player.setSpeed(2);
            }
            if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS) isAttackRight = true;
            if (key == GLFW_KEY_LEFT && action == GLFW_PRESS) isAttackLeft = true;
            if (key == GLFW_KEY_UP && action == GLFW_PRESS) isAttackUp = true;
            if (key == GLFW_KEY_DOWN && action == GLFW_PRESS) isAttackDown = true;
            if (key == GLFW_KEY_E && action == GLFW_PRESS) isCheck = true;
        });
    }

    private void loop() {
        int i1 = 2, i2 = 2, i3 = 2, i4 = 2, i5 = 0;
        int g1 = 0, g2 = 0, g3 = 0, g4 = 0;
        int j1 = 1, j2 = 1, j3 = 1, j4 = 1;
        int b = 0;

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            // Переключение уровня
            switch (level) {
                case "MainMenu": {
                    if (forMainTheme) {
                        source.play(mainMenuTheme);
                        forMainTheme = false;
                    }
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("MainMenu")); // Фон главного меню
                    createQuadTexture(0, 0, 1536, 360);
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("Press_enter"));
                    createQuadTexture(player.getX() - 13, player.getY() - 100, player.getX() + 77, player.getY() - 82);
                    if (player.getX() == 1186) forMainMenu = false;
                    else if (player.getX() == 290) forMainMenu = true;
                    if (forMainMenu) {
                        glTranslated(-1, 0, 0);
                        forCamera++;
                        if (i1 == 10) i1 = 2;
                        player_animation = "player_walk_right_0" + i1;
                        head = "HEAD_" + player.getHead() + "_right_move_0" + i1;
                        shoulders = "SHOULDERS_" + player.getShoulders() + "_right_move_0" + i1;
                        torso = "TORSO_" + player.getTorso() + "_right_move_0" + i1;
                        belt = "BELT_" + player.getBelt() + "_right_move_0" + i1;
                        hands = "HANDS_" + player.getHands() + "_right_move_0" + i1;
                        legs = "LEGS_" + player.getLegs() + "_right_move_0" + i1;
                        feet = "FEET_" + player.getFeet() + "_right_move_0" + i1;
                        if (g1 == 8) {
                            i1++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveRight();
                    }
                    else {
                        glTranslated(1, 0, 0);
                        forCamera--;
                        if (i2 == 10) i2 = 2;
                        player_animation = "player_walk_left_0" + i2;
                        head =  "HEAD_" + player.getHead() + "_left_move_0" + i2;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_left_move_0" + i2;
                        torso = "TORSO_" + player.getTorso() + "_left_move_0" + i2;
                        belt = "BELT_" + player.getBelt() + "_left_move_0" + i2;
                        hands = "HANDS_" + player.getHands() + "_left_move_0" + i2;
                        legs = "LEGS_" + player.getLegs() + "_left_move_0" + i2;
                        feet = "FEET_" + player.getFeet() + "_left_move_0" + i2;
                        if (g1 == 8) {
                            i2++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveLeft();
                    }
                    break;
                }
                case "FirstLevel": {
                    if (!forMainTheme) {
                        source.stop(mainMenuTheme);
                        forMainTheme = true;
                    }
                    Slime slime = (Slime) mobList.get(1);
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("level0")); // Фон первого уровня
                    createQuadTexture(0, 0, 640, 360);

                    // Отрисовка всех объектов
                    for (int i = 0; i < objectMap.size(); i++) {
                        Object object = objectMap.get(i);
                        if (!object.getIsLying()) continue;
                        glBindTexture(GL_TEXTURE_2D, textureMap.get(object.getTexture()));
                        createQuadTexture(object.getMinX(), object.getMinY(), object.getMaxX(), object.getMaxY());
                    }
                    if (isCheck) {
                        for (int i = 0; i < objectMap.size(); i++) {
                            Object object = objectMap.get(i);
                            if (object instanceof Armor) {
                                if (AABB.AABBvsAABB(player.getCollisionBox(), object.getCollisionBox())) {
                                    Armor armor = player.getArmorType((Armor) object);
                                    object.setIsLying(false);
                                    player.setArmor((Armor) object);
                                    object = armor;
                                    object.setMinX(player.getCollisionBox().getMin().x - 20);
                                    object.setMinY(player.getCollisionBox().getMin().y - 30);
                                    object.setMaxX(player.getCollisionBox().getMin().x + 44);
                                    object.setMaxY(player.getCollisionBox().getMin().y + 34);
                                    object.setIsLying(true);
                                    object.getCollisionBox().update(object.getMinX(), object.getMinY(), object.getMaxX(), object.getMaxY());
                                    objectMap.put(i, object);
                                }
                            }
                        }
                    }
                    isCheck = false;

                    // Все операции со слизнем
                    if (!slime.getDead()) {
                        switch (i5) { // Анимация слайма
                            case 0:
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("slime_" + slime.getMoveDirection() +"_01"));
                                break;
                            case 1:
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("slime_" + slime.getMoveDirection() +"_02"));
                                break;
                            case 2:
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("slime_" + slime.getMoveDirection() +"_03"));
                                break;
                        }
                        if (g2 == 8) {
                            if (i5 == 0) { i5++; }
                            else { i5--; }
                            g2 = 0;
                        }
                        g2++;
                        slime.getHitbox().update(slime.getX(), slime.getY(), slime.getX() + 16, slime.getY() + 10);
                        slime.getCollisionBox().update(slime.getX(), slime.getY(), slime.getX() + 16, slime.getY() + 10);
                        createQuadTexture(slime.getX(), slime.getY(), slime.getX() + 16, slime.getY() + 10);

                        // Преследование игрока слаймом, обновление хитбокса и перерисовка текстуры
                        if (!AABB.AABBvsAABB(player.getHitbox(), slime.getHitbox()) && (int)(Math.random() * 6) == 5) {
                            if (slime.getHitbox().getMin().x > player.getHitbox().getMin().x) slime.moveLeft();
                            else slime.moveRight();
                            if (slime.getHitbox().getMin().y > player.getHitbox().getMin().y) slime.moveUp();
                            else slime.moveDown();
                        }

                        // Получение урона от слизня
                        if (AABB.AABBvsAABB(player.getHitbox(), slime.getHitbox()) && !player.getDead() && !player.getImmortal()) {
                            if (player.getX() > slime.getX()) player.setKnockbackDirection("Right");
                            else if (player.getX() < slime.getX()) player.setKnockbackDirection("Left");
                            else if (player.getY() > slime.getY()) player.setKnockbackDirection("Down");
                            else if (player.getY() < slime.getY()) player.setKnockbackDirection("Up");
                            player.takeDamage(slime.getDamage());
                            System.out.println(player.getHealth());
                            player.setImmortal(true);
                            player.getTimer().schedule(player.getTimerTaskPlayer(), 0, 10);
                        }
                        // Слизень получает урон от игрока
                        if (AABB.AABBvsAABB(player.getAttackBox(), slime.getHitbox()) && !slime.getImmortal()) {
                            if (player.getX() > slime.getX()) slime.setDirection("Left");
                            else if (player.getX() < slime.getX()) slime.setDirection("Right");
                            else if (player.getY() > slime.getY()) slime.setDirection("Up");
                            else if (player.getY() < slime.getY()) slime.setDirection("Down");
                            slime.setHealth(slime.getHealth() - player.getDamage());
                            slime.setImmortal(true);
                            slime.getTimerSlime().schedule(slime.getTimerTaskSlime(), 0, 10);
                        }
                        player.getAttackBox().update(0, 0, 0, 0);
                        if (player.getTime() >= 15) {
                            player.stopTimerPlayer();
                            player.setImmortal(false);
                        }
                        if (slime.getTime() >= 25) {
                            slime.stopTimerSlime();
                            slime.setImmortal(false);
                        }

                        // Отрисовка хелсбара
                        if (slime.getHealth() <= 0) slime.setDead(true);
                        else {
                            glBindTexture(GL_TEXTURE_2D, textureMap.get("enemyHp" + slime.getHealth()));
                            createQuadTexture(slime.getX(), slime.getY() - 2, slime.getX() + 16, slime.getY());
                        }
                    }
                    else slime.getHitbox().update(0,0,0,0);

                    // Проверка всех мобов на столкновение со стенами
                    for (Mob mob : mobList) {
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")))
                            mob.stopRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.stopLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")))
                            mob.stopUp();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")))
                            mob.stopDown();
                    }
                    // Проверка всех мобов на столкновение с объектами
                    for (Mob mob : mobList) {
                        for (int i = 0; i < objectMap.size(); i++) {
                            Object object = objectMap.get(i);
                            if (object.getIsNoclip()) continue;
                            if (AABB.AABBvsAABB2(mob.getCollisionBox(), object.getCollisionBox())) mob.stop(CollisionMessage.getMessage());
                        }
                    }

                    // Проверка перехода на второй уровень
                    if (AABB.AABBvsAABB(player.getCollisionBox(), aabbMap.get("entranceToSecondLevel"))) {
                        level = "SecondLevel";
                        // Обновление хитбоксов стен для второго уровня
                        for (int i = 0, j = 0; i < 7; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.secondLevelWalls[j], Storage.secondLevelWalls[j + 1],
                                    Storage.secondLevelWalls[j + 2], Storage.secondLevelWalls[j + 3]);
                        }
                        player.setX(2 - 14);
                        player.setY(225);
                    }
                    break;
                }
                case "SecondLevel": {
                    Skeleton skeleton = (Skeleton) mobList.get(2);
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("level1")); // Фон второго уровня
                    createQuadTexture(0, 0, 640, 360);

                    // Все операции со скелетоном
                    if (!skeleton.getDead()) {
                        if (forSkeletonAnimation) {
                            skeleton.getTimer().schedule(skeleton.getAnimationTask(), 0, 120);
                            forSkeletonAnimation = false;
                        }
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("skeleton_" + skeleton.getMoveDirection() + "_move_0" + skeleton.getAnimationTime()));
                        skeleton.getHitbox().update(skeleton.getX() + 18, skeleton.getY() + 15, skeleton.getX() + 45, skeleton.getY() + 61);
                        skeleton.getCollisionBox().update(skeleton.getX() + 16, skeleton.getY() + 44, skeleton.getX() + 47, skeleton.getY() + 62);
                        createQuadTexture(skeleton.getX(), skeleton.getY(), skeleton.getX() + 64, skeleton.getY() + 64);

                        // Преследование игрока скелетоном
                        if (!AABB.AABBvsAABB(player.getHitbox(), skeleton.getHitbox())) {
                            if (player.getHitbox().getMin().y < skeleton.getHitbox().getMin().y &&
                                    player.getHitbox().getMin().x < skeleton.getHitbox().getMin().x) skeleton.moveUpLeft();
                            else if (player.getHitbox().getMin().y < skeleton.getHitbox().getMin().y &&
                                    player.getHitbox().getMin().x > skeleton.getHitbox().getMin().x) skeleton.moveUpRight();
                            else if (player.getHitbox().getMin().y > skeleton.getHitbox().getMin().y &&
                                    player.getHitbox().getMin().x < skeleton.getHitbox().getMin().x) skeleton.moveDownLeft();
                            else if (player.getHitbox().getMin().y > skeleton.getHitbox().getMin().y &&
                                    player.getHitbox().getMin().x > skeleton.getHitbox().getMin().x) skeleton.moveDownRight();
                            else if (player.getHitbox().getMin().x < skeleton.getHitbox().getMin().x) skeleton.moveLeft();
                            else if (player.getHitbox().getMin().x > skeleton.getHitbox().getMin().x) skeleton.moveRight();
                            else if (player.getHitbox().getMin().y < skeleton.getHitbox().getMin().y) skeleton.moveUp();
                            else if (player.getHitbox().getMin().y > skeleton.getHitbox().getMin().y) skeleton.moveDown();
                        }

                        // Получение урона от скелетона
                        if (AABB.AABBvsAABB(player.getHitbox(), skeleton.getHitbox()) && !player.getDead() && !player.getImmortal()) {
                            if (player.getX() > skeleton.getX()) player.setKnockbackDirection("Right");
                            else if (player.getX() < skeleton.getX()) player.setKnockbackDirection("Left");
                            else if (player.getY() > skeleton.getY()) player.setKnockbackDirection("Down");
                            else if (player.getY() < skeleton.getY()) player.setKnockbackDirection("Up");
                            player.takeDamage(skeleton.getDamage());
                            player.setImmortal(true);
                            player.getTimer().schedule(player.getTimerTaskPlayer(), 0, 10);
                        }
                        // Скелетон получает урон от игрока
                        if (AABB.AABBvsAABB(player.getAttackBox(), skeleton.getHitbox()) && !skeleton.getImmortal()) {
                            if (player.getX() > skeleton.getX()) skeleton.setDirection("Left");
                            else if (player.getX() < skeleton.getX()) skeleton.setDirection("Right");
                            else if (player.getY() > skeleton.getY()) skeleton.setDirection("Up");
                            else if (player.getY() < skeleton.getY()) skeleton.setDirection("Down");
                            skeleton.setHealth(skeleton.getHealth() - player.getDamage());
                            skeleton.setImmortal(true);
                            skeleton.getTimer().schedule(skeleton.getKnockbackTimerTask(), 0, 10);
                        }
                        player.getAttackBox().update(0, 0, 0, 0);
                        if (player.getTime() >= 15) {
                            player.stopTimerPlayer();
                            player.setImmortal(false);
                        }
                        if (skeleton.getKnockbackTime() >= 25) {
                            skeleton.stopKnockbackTimer();
                            skeleton.setImmortal(false);
                            forSkeletonAnimation = true;
                        }

                        if (skeleton.getHealth() <= 0) skeleton.setDead(true);
                    }
                    else skeleton.getHitbox().update(0,0,0,0);

                    // Проверка всех мобов на столкновение со стенами
                    for (Mob mob : mobList) {
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")))
                            mob.stopRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall5")))
                            mob.stopLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")))
                            mob.stopUp();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall6")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.stopDown();
                    }

                    // Проверка перехода на первый уровень
                    if (AABB.AABBvsAABB(player.getCollisionBox(), aabbMap.get("entranceToFirstLevel"))) {
                        level = "FirstLevel";
                        // Обновление хитбоксов стен для первого уровня
                        for(int i = 0, j = 0; i < 5; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.firstLevelWalls[j], Storage.firstLevelWalls[j + 1],
                                    Storage.firstLevelWalls[j + 2], Storage.firstLevelWalls[j + 3]);
                        }
                        player.setX(638 - 64 + 15);
                        player.setY(281);
                    }
                    break;
                }
            }

            //Движение игрока и обновление одежды
            if (!level.equals("MainMenu")) {
                if (!player.getDead()) {
                    player_animation = "player_stand_" + player.getMoveDirection();
                    head = "HEAD_" + player.getHead() + "_" + player.getMoveDirection() + "_move_01";
                    shoulders = "SHOULDERS_" + player.getShoulders() + "_" + player.getMoveDirection() + "_move_01";
                    torso = "TORSO_" + player.getTorso() + "_" + player.getMoveDirection() + "_move_01";
                    belt = "BELT_" + player.getBelt() + "_" + player.getMoveDirection() + "_move_01";
                    hands = "HANDS_" + player.getHands() + "_" + player.getMoveDirection() + "_move_01";
                    legs = "LEGS_" + player.getLegs() + "_" + player.getMoveDirection() + "_move_01";
                    feet = "FEET_" + player.getFeet() + "_" + player.getMoveDirection() + "_move_01";

                    if ( (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) ) {
                        if (i2 == 10) i2 = 2;
                        player_animation = "player_walk_left_0" + i2;
                        head =  "HEAD_" + player.getHead() + "_left_move_0" + i2;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_left_move_0" + i2;
                        torso = "TORSO_" + player.getTorso() + "_left_move_0" + i2;
                        belt = "BELT_" + player.getBelt() + "_left_move_0" + i2;
                        hands = "HANDS_" + player.getHands() + "_left_move_0" + i2;
                        legs = "LEGS_" + player.getLegs() + "_left_move_0" + i2;
                        feet = "FEET_" + player.getFeet() + "_left_move_0" + i2;
                        if (g1 == 8) {
                            i2++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveUpLeft();
                    }
                    else if ( (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) ) {
                        if (i1 == 10) i1 = 2;
                        player_animation = "player_walk_right_0" + i1;
                        head =  "HEAD_" + player.getHead() + "_right_move_0" + i1;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_right_move_0" + i1;
                        torso = "TORSO_" + player.getTorso() + "_right_move_0" + i1;
                        belt = "BELT_" + player.getBelt() + "_right_move_0" + i1;
                        hands = "HANDS_" + player.getHands() + "_right_move_0" + i1;
                        legs = "LEGS_" + player.getLegs() + "_right_move_0" + i1;
                        feet = "FEET_" + player.getFeet() + "_right_move_0" + i1;
                        if (g1 == 8) {
                            i1++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveUpRight();
                    }
                    else if ( (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) ) {
                        if (i2 == 10) i2 = 2;
                        player_animation = "player_walk_left_0" + i2;
                        head =  "HEAD_" + player.getHead() + "_left_move_0" + i2;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_left_move_0" + i2;
                        torso = "TORSO_" + player.getTorso() + "_left_move_0" + i2;
                        belt = "BELT_" + player.getBelt() + "_left_move_0" + i2;
                        hands = "HANDS_" + player.getHands() + "_left_move_0" + i2;
                        legs = "LEGS_" + player.getLegs() + "_left_move_0" + i2;
                        feet = "FEET_" + player.getFeet() + "_left_move_0" + i2;
                        if (g1 == 8) {
                            i2++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveDownLeft();
                    }
                    else if ( (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS && glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) ) {
                        if (i1 == 10) i1 = 2;
                        player_animation = "player_walk_right_0" + i1;
                        head =  "HEAD_" + player.getHead() + "_right_move_0" + i1;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_right_move_0" + i1;
                        torso = "TORSO_" + player.getTorso() + "_right_move_0" + i1;
                        belt = "BELT_" + player.getBelt() + "_right_move_0" + i1;
                        hands = "HANDS_" + player.getHands() + "_right_move_0" + i1;
                        legs = "LEGS_" + player.getLegs() + "_right_move_0" + i1;
                        feet = "FEET_" + player.getFeet() + "_right_move_0" + i1;
                        if (g1 == 8) {
                            i1++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveDownRight();
                    }
                    else if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
                        if (i2 == 10) i2 = 2;
                        player_animation = "player_walk_left_0" + i2;
                        head =  "HEAD_" + player.getHead() + "_left_move_0" + i2;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_left_move_0" + i2;
                        torso = "TORSO_" + player.getTorso() + "_left_move_0" + i2;
                        belt = "BELT_" + player.getBelt() + "_left_move_0" + i2;
                        hands = "HANDS_" + player.getHands() + "_left_move_0" + i2;
                        legs = "LEGS_" + player.getLegs() + "_left_move_0" + i2;
                        feet = "FEET_" + player.getFeet() + "_left_move_0" + i2;
                        if (g1 == 8) {
                            i2++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveLeft();
                    }
                    else if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
                        if (i1 == 10) i1 = 2;
                        player_animation = "player_walk_right_0" + i1;
                        head =  "HEAD_" + player.getHead() + "_right_move_0" + i1;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_right_move_0" + i1;
                        torso = "TORSO_" + player.getTorso() + "_right_move_0" + i1;
                        belt = "BELT_" + player.getBelt() + "_right_move_0" + i1;
                        hands = "HANDS_" + player.getHands() + "_right_move_0" + i1;
                        legs = "LEGS_" + player.getLegs() + "_right_move_0" + i1;
                        feet = "FEET_" + player.getFeet() + "_right_move_0" + i1;
                        if (g1 == 8) {
                            i1++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveRight();
                    }
                    else if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
                        if (i3 == 10) i3 = 2;
                        player_animation = "player_walk_up_0" + i3;
                        head =  "HEAD_" + player.getHead() + "_up_move_0" + i3;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_up_move_0" + i3;
                        torso = "TORSO_" + player.getTorso() + "_up_move_0" + i3;
                        belt = "BELT_" + player.getBelt() + "_up_move_0" + i3;
                        hands = "HANDS_" + player.getHands() + "_up_move_0" + i3;
                        legs = "LEGS_" + player.getLegs() + "_up_move_0" + i3;
                        feet = "FEET_" + player.getFeet() + "_up_move_0" + i3;
                        if (g1 == 8) {
                            i3++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveUp();
                    }
                    else if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
                        if (i4 == 10) i4 = 2;
                        player_animation = "player_walk_down_0" + i4;
                        head =  "HEAD_" + player.getHead() + "_down_move_0" + i4;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_down_move_0" + i4;
                        torso = "TORSO_" + player.getTorso() + "_down_move_0" + i4;
                        belt = "BELT_" + player.getBelt() + "_down_move_0" + i4;
                        hands = "HANDS_" + player.getHands() + "_down_move_0" + i4;
                        legs = "LEGS_" + player.getLegs() + "_down_move_0" + i4;
                        feet = "FEET_" + player.getFeet() + "_down_move_0" + i4;
                        if (g1 == 8) {
                            i4++;
                            g1 = 0;
                        }
                        g1++;
                        player.moveDown();
                    }
                    if (isAttackLeft) {
                        if (j3 == 7) {
                            j3 = 1;
                            isAttackLeft = false;
                        }
                        if (j3 == 5) player.getAttackBox().update(player.getX() - 50, player.getY() + 20, player.getX() - 50 + 69, player.getY() + 20 + 27);
                        player_animation = "player_" + player.getAttackType() + "_left_0" + j3;
                        weapon = "weapon_" + player.getWeapon() + "_left_" + player.getAttackType() + "_0" + j3;
                        head =  "HEAD_" + player.getHead() + "_left_" + player.getAttackType() + "_0" + j3;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_left_" + player.getAttackType() + "_0" + j3;
                        torso = "TORSO_" + player.getTorso() + "_left_" + player.getAttackType() + "_0" + j3;
                        belt = "BELT_" + player.getBelt() + "_left_" + player.getAttackType() + "_0" + j3;
                        hands = "HANDS_" + player.getHands() + "_left_" + player.getAttackType() + "_0" + j3;
                        legs = "LEGS_" + player.getLegs() + "_left_" + player.getAttackType() + "_0" + j3;
                        feet = "FEET_" + player.getFeet() + "_left_" + player.getAttackType() + "_0" + j3;
                        if (g3 == 4) {
                            j3++;
                            g3 = 0;
                        }
                        g3++;
                    }
                    else if (isAttackRight) {
                        if (j1 == 7) {
                            j1 = 1;
                            isAttackRight = false;
                        }
                        if (j1 == 5) player.getAttackBox().update(player.getX() + 55, player.getY() + 20, player.getX() + 55 + 60, player.getY() + 20 + 28);
                        player_animation = "player_" + player.getAttackType() + "_right_0" + j1;
                        weapon = "weapon_" + player.getWeapon() + "_right_" + player.getAttackType() + "_0" + j1;
                        head =  "HEAD_" + player.getHead() + "_right_" + player.getAttackType() + "_0" + j1;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_right_" + player.getAttackType() + "_0" + j1;
                        torso = "TORSO_" + player.getTorso() + "_right_" + player.getAttackType() + "_0" + j1;
                        belt = "BELT_" + player.getBelt() + "_right_" + player.getAttackType() + "_0" + j1;
                        hands = "HANDS_" + player.getHands() + "_right_" + player.getAttackType() + "_0" + j1;
                        legs = "LEGS_" + player.getLegs() + "_right_" + player.getAttackType() + "_0" + j1;
                        feet = "FEET_" + player.getFeet() + "_right_" + player.getAttackType() + "_0" + j1;
                        if (g3 == 4) {
                            g3 = 0;
                            j1++;
                        }
                        g3++;
                    }
                    else if (isAttackUp) {
                        if (j4 == 7) {
                            j4 = 1;
                            isAttackUp = false;
                        }
                        if (j4 == 5) player.getAttackBox().update(player.getX() + 18, player.getY() - 10, player.getX() + 18 + 56, player.getY() - 10 + 21);
                        player_animation = "player_" + player.getAttackType() + "_up_0" + j4;
                        weapon = "weapon_" + player.getWeapon() + "_up_" + player.getAttackType() + "_0" + j4;
                        head =  "HEAD_" + player.getHead() + "_up_" + player.getAttackType() + "_0" + j4;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_up_" + player.getAttackType() + "_0" + j4;
                        torso = "TORSO_" + player.getTorso() + "_up_" + player.getAttackType() + "_0" + j4;
                        belt = "BELT_" + player.getBelt() + "_up_" + player.getAttackType() + "_0" + j4;
                        hands = "HANDS_" + player.getHands() + "_up_" + player.getAttackType() + "_0" + j4;
                        legs = "LEGS_" + player.getLegs() + "_up_" + player.getAttackType() + "_0" + j4;
                        feet = "FEET_" + player.getFeet() + "_up_" + player.getAttackType() + "_0" + j4;
                        if (g3 == 4) {
                            g3 = 0;
                            j4++;
                        }
                        g3++;
                    }
                    else if (isAttackDown) {
                        if (j2 == 7) {
                            j2 = 1;
                            isAttackDown = false;
                        }
                        if (j2 == 5) player.getAttackBox().update(player.getX() + 10, player.getY() + 20, player.getX() + 10 + 55, player.getY() + 45 + 39);
                        player_animation = "player_" + player.getAttackType() + "_down_0" + j2;
                        weapon = "weapon_" + player.getWeapon() + "_down_" + player.getAttackType() + "_0" + j2;
                        head =  "HEAD_" + player.getHead() + "_down_" + player.getAttackType() + "_0" + j2;
                        shoulders =  "SHOULDERS_" + player.getShoulders() + "_down_" + player.getAttackType() + "_0" + j2;
                        torso = "TORSO_" + player.getTorso() + "_down_" + player.getAttackType() + "_0" + j2;
                        belt = "BELT_" + player.getBelt() + "_down_" + player.getAttackType() + "_0" + j2;
                        hands = "HANDS_" + player.getHands() + "_down_" + player.getAttackType() + "_0" + j2;
                        legs = "LEGS_" + player.getLegs() + "_down_" + player.getAttackType() + "_0" + j2;
                        feet = "FEET_" + player.getFeet() + "_down_" + player.getAttackType() + "_0" + j2;
                        if (g3 == 4) {
                            g3 = 0;
                            j2++;
                        }
                        g3++;
                    }

                    // Полоска здоровья
                    int tempHealth = player.getHealth() % 10 == 0 ? player.getHealth() : player.getHealth() - (player.getHealth() % 10) + 10;
                    if (tempHealth >= 0) glBindTexture(GL_TEXTURE_2D, textureMap.get(tempHealth + "hp"));
                    if (player.getHealth() <= 0) {
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("0hp"));
                        player.setDead(true);
                    }
                    createQuadTexture(0, 0, 103, 18);

                    player.reArmor();
                    // Броня
                    if (player.getArmor() < 30) glBindTexture(GL_TEXTURE_2D, textureMap.get("armor" + player.getArmor() / 5));
                    else glBindTexture(GL_TEXTURE_2D, textureMap.get("armor5"));

                    createQuadTexture(0, 19, 34, 53);
                }
                else {
                    switch (b) {
                        case 0:
                            player_animation = "player_hurt_01";
                            head = "HEAD_" + player.getHead() + "_hurt_01";
                            shoulders = "SHOULDERS_" + player.getShoulders() + "_hurt_01";
                            torso = "TORSO_" + player.getTorso() + "_hurt_01";
                            belt = "BELT_" + player.getBelt() + "_hurt_01";
                            hands = "HANDS_" + player.getHands() + "_hurt_01";
                            legs = "LEGS_" + player.getLegs() + "_hurt_01";
                            feet = "FEET_" + player.getFeet() + "_hurt_01";
                            break;
                        case 1:
                            player_animation = "player_hurt_02";
                            head = "HEAD_" + player.getHead() + "_hurt_02";
                            shoulders = "SHOULDERS_" + player.getShoulders() + "_hurt_02";
                            torso = "TORSO_" + player.getTorso() + "_hurt_02";
                            belt = "BELT_" + player.getBelt() + "_hurt_02";
                            hands = "HANDS_" + player.getHands() + "_hurt_02";
                            legs = "LEGS_" + player.getLegs() + "_hurt_02";
                            feet = "FEET_" + player.getFeet() + "_hurt_02";
                            break;
                        case 2:
                            player_animation = "player_hurt_03";
                            head = "HEAD_" + player.getHead() + "_hurt_03";
                            shoulders = "SHOULDERS_" + player.getShoulders() + "_hurt_03";
                            torso = "TORSO_" + player.getTorso() + "_hurt_03";
                            belt = "BELT_" + player.getBelt() + "_hurt_03";
                            hands = "HANDS_" + player.getHands() + "_hurt_03";
                            legs = "LEGS_" + player.getLegs() + "_hurt_03";
                            feet = "FEET_" + player.getFeet() + "_hurt_03";
                            break;
                        case 3:
                            player_animation = "player_hurt_04";
                            head = "HEAD_" + player.getHead() + "_hurt_04";
                            shoulders = "SHOULDERS_" + player.getShoulders() + "_hurt_04";
                            torso = "TORSO_" + player.getTorso() + "_hurt_04";
                            belt = "BELT_" + player.getBelt() + "_hurt_04";
                            hands = "HANDS_" + player.getHands() + "_hurt_04";
                            legs = "LEGS_" + player.getLegs() + "_hurt_04";
                            feet = "FEET_" + player.getFeet() + "_hurt_04";
                            break;
                        case 4:
                            player_animation = "player_hurt_05";
                            head = "HEAD_" + player.getHead() + "_hurt_05";
                            shoulders = "SHOULDERS_" + player.getShoulders() + "_hurt_05";
                            torso = "TORSO_" + player.getTorso() + "_hurt_05";
                            belt = "BELT_" + player.getBelt() + "_hurt_05";
                            hands = "HANDS_" + player.getHands() + "_hurt_05";
                            legs = "LEGS_" + player.getLegs() + "_hurt_05";
                            feet = "FEET_" + player.getFeet() + "_hurt_05";
                            break;
                        case 5:
                            player_animation = "player_hurt_06";
                            head = "HEAD_" + player.getHead() + "_hurt_06";
                            shoulders = "SHOULDERS_" + player.getShoulders() + "_hurt_06";
                            torso = "TORSO_" + player.getTorso() + "_hurt_06";
                            belt = "BELT_" + player.getBelt() + "_hurt_06";
                            hands = "HANDS_" + player.getHands() + "_hurt_06";
                            legs = "LEGS_" + player.getLegs() + "_hurt_06";
                            feet = "FEET_" + player.getFeet() + "_hurt_06";
                            break;
                    }
                    if (g4 == 8) {
                        b++;
                        g4 = 0;
                    }
                    g4++;
                }
            }

            // Отрисовка экипировки и анимации
            glBindTexture(GL_TEXTURE_2D, textureMap.get(player_animation));
            createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            if (!player.getFeet().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(feet));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getLegs().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(legs));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getTorso().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(torso));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getShoulders().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(shoulders));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getHands().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(hands));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getHead().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(head));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getBelt().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(belt));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if ( (isAttackRight || isAttackUp || isAttackLeft || isAttackDown) && !player.getDead()) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(weapon));
                createQuadTexture(player.getX() - 64, player.getY() - 64, player.getX() + 128, player.getY() + 128);
            }

            if (player.getMoveDirection().equals("left")) player.getHitbox().update(player.getX() + 23, player.getY() + 15, player.getX() + 42, player.getY() + 59);
            else if (player.getMoveDirection().equals("right")) player.getHitbox().update(player.getX() + 21, player.getY() + 15, player.getX() + 40, player.getY() + 59);
            else if (player.getMoveDirection().equals("up")) player.getHitbox().update(player.getX() + 20, player.getY() + 15, player.getX() + 43, player.getY() + 60);
            else if (player.getMoveDirection().equals("down")) player.getHitbox().update(player.getX() + 20, player.getY() + 15, player.getX() + 43, player.getY() + 61);
            player.getCollisionBox().update(player.getX() + 17, player.getY() + 46, player.getX() + 46, player.getY() + 61);

            glfwPollEvents();
            glfwSwapBuffers(window);
        }
    }

    private void createQuadTexture(int xmin, int ymin, int xmax, int ymax) {
        glBegin(GL_QUADS);
        glTexCoord2d(0, 0);
        glVertex2f(xmin, ymin);
        glTexCoord2d(1, 0);
        glVertex2f(xmax, ymin);
        glTexCoord2d(1, 1);
        glVertex2f(xmax, ymax);
        glTexCoord2d(0, 1);
        glVertex2f(xmin, ymax);
        glEnd();
    }

    void reshape(int w, int h) {
        glViewport(0, 0, w, h);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, w, h, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        if (forScale) {
            glScaled(3, 3, 1);
            forScale = false;
        }
    }
}