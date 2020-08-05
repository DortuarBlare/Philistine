import math.AABB;
import mobs.Mob;
import mobs.Player;
import mobs.Slime;
import org.lwjgl.glfw.*;
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
    private String level = "FirstLevel";
    private boolean forScale = true;
    boolean isAttackR = false, isAttackL = false, isAttackU = false, isAttackD = false;
    boolean isCheck = false;
    boolean bfe = true;
    Player player;
    String player_animation, pants, weapon;
    private final int[] firstLevelWalls = {
            111, 128, 495, 140, // wall0
            496, 143, 498, 232, // wall1
            499, 232, 639, 236, // wall2
            111, 339, 639, 341, // wall3
            103, 139, 111, 332 // wall4
    };
    private final int[] secondLevelWalls = {
            0, 182, 94, 188, // wall0
            92, 140, 96, 184, // wall1
            98, 134, 526, 140, // wall2
            529, 143, 534, 334, // wall3
            97, 335, 528, 340, // wall4
            92, 290, 96, 334, // wall5
            0, 287, 92, 291 // wall6
    };
    private final String[] textureString = {
            "0hp", "10hp", "20hp", "30hp", "40hp", "50hp", "60hp", "70hp", "80hp", "90hp", "100hp",
            "player_stand_left", "player_stand_right", "player_stand_up", "player_stand_down",
            "player_walk_left_01", "player_walk_left_02", "player_walk_left_03", "player_walk_left_04", "player_walk_left_05", "player_walk_left_06", "player_walk_left_07", "player_walk_left_08", "player_walk_left_09",
            "player_walk_right_01", "player_walk_right_02", "player_walk_right_03", "player_walk_right_04", "player_walk_right_05", "player_walk_right_06", "player_walk_right_07", "player_walk_right_08", "player_walk_right_09",
            "player_walk_up_01", "player_walk_up_02", "player_walk_up_03", "player_walk_up_04", "player_walk_up_05", "player_walk_up_06", "player_walk_up_07", "player_walk_up_08", "player_walk_up_09",
            "player_walk_down_01", "player_walk_down_02", "player_walk_down_03", "player_walk_down_04", "player_walk_down_05", "player_walk_down_06", "player_walk_down_07", "player_walk_down_08",
            "slimeLeft", "slimeLeft2", "slimeLeft3",
            "slimeRight", "slimeRight2", "slimeRight3",
            "level0", "level1", "level2", "level3", "box", "playerAttack", "sword",
            "torch0", "torch1", "torch2", "torch3",
            "enemyHp0", "enemyHp1", "enemyHp2", "enemyHp3", "enemyHp4", "enemyHp5",

            "player_slash_right_01", "player_slash_right_02", "player_slash_right_03", "player_slash_right_04", "player_slash_right_05", "player_slash_right_06",
            "player_slash_up_01", "player_slash_up_02", "player_slash_up_03", "player_slash_up_04", "player_slash_up_05", "player_slash_up_06",
            "player_slash_left_01", "player_slash_left_02", "player_slash_left_03", "player_slash_left_04", "player_slash_left_05", "player_slash_left_06",
            "player_slash_down_01", "player_slash_down_02", "player_slash_down_03", "player_slash_down_04", "player_slash_down_05", "player_slash_down_06",

            "weapon_rapier_right_01", "weapon_rapier_right_02", "weapon_rapier_right_03", "weapon_rapier_right_04", "weapon_rapier_right_05", "weapon_rapier_right_06",
            "weapon_rapier_up_01", "weapon_rapier_up_02", "weapon_rapier_up_03", "weapon_rapier_up_04", "weapon_rapier_up_05", "weapon_rapier_up_06",
            "weapon_rapier_down_01", "weapon_rapier_down_02", "weapon_rapier_down_03", "weapon_rapier_down_04", "weapon_rapier_down_05", "weapon_rapier_down_06",
            "weapon_rapier_left_01", "weapon_rapier_left_02", "weapon_rapier_left_03", "weapon_rapier_left_04", "weapon_rapier_left_05", "weapon_rapier_left_06",

            "enemyHp0", "enemyHp1", "enemyHp2", "enemyHp3", "enemyHp4", "enemyHp5",
            "player_slash_right_01", "player_slash_right_02", "player_slash_right_03", "player_slash_right_04", "player_slash_right_05", "player_slash_right_06",

            "LEGS_pants_greenish_left_move_01", "LEGS_pants_greenish_left_move_02", "LEGS_pants_greenish_left_move_03", "LEGS_pants_greenish_left_move_04", "LEGS_pants_greenish_left_move_05", "LEGS_pants_greenish_left_move_06", "LEGS_pants_greenish_left_move_07", "LEGS_pants_greenish_left_move_08", "LEGS_pants_greenish_left_move_09",
            "LEGS_pants_greenish_right_move_01", "LEGS_pants_greenish_right_move_02", "LEGS_pants_greenish_right_move_03", "LEGS_pants_greenish_right_move_04", "LEGS_pants_greenish_right_move_05", "LEGS_pants_greenish_right_move_06", "LEGS_pants_greenish_right_move_07", "LEGS_pants_greenish_right_move_08", "LEGS_pants_greenish_right_move_09",
            "LEGS_pants_greenish_up_move_01", "LEGS_pants_greenish_up_move_02", "LEGS_pants_greenish_up_move_03", "LEGS_pants_greenish_up_move_04", "LEGS_pants_greenish_up_move_05", "LEGS_pants_greenish_up_move_06", "LEGS_pants_greenish_up_move_07", "LEGS_pants_greenish_up_move_08", "LEGS_pants_greenish_up_move_09",
            "LEGS_pants_greenish_down_move_01", "LEGS_pants_greenish_down_move_02", "LEGS_pants_greenish_down_move_03", "LEGS_pants_greenish_down_move_04", "LEGS_pants_greenish_down_move_05", "LEGS_pants_greenish_down_move_06", "LEGS_pants_greenish_down_move_07", "LEGS_pants_greenish_down_move_08", "LEGS_pants_greenish_down_move_09",

            "LEGS_pants_greenish_left_slash_01", "LEGS_pants_greenish_left_slash_02", "LEGS_pants_greenish_left_slash_03", "LEGS_pants_greenish_left_slash_04", "LEGS_pants_greenish_left_slash_05", "LEGS_pants_greenish_left_slash_06",
            "LEGS_pants_greenish_right_slash_01", "LEGS_pants_greenish_right_slash_02", "LEGS_pants_greenish_right_slash_03", "LEGS_pants_greenish_right_slash_04", "LEGS_pants_greenish_right_slash_05", "LEGS_pants_greenish_right_slash_06",
            "LEGS_pants_greenish_up_slash_01", "LEGS_pants_greenish_up_slash_02", "LEGS_pants_greenish_up_slash_03", "LEGS_pants_greenish_up_slash_04", "LEGS_pants_greenish_up_slash_05", "LEGS_pants_greenish_up_slash_06",
            "LEGS_pants_greenish_down_slash_01", "LEGS_pants_greenish_down_slash_02", "LEGS_pants_greenish_down_slash_03", "LEGS_pants_greenish_down_slash_04", "LEGS_pants_greenish_down_slash_05", "LEGS_pants_greenish_down_slash_06",
    };
    private final String[] aabbString = {
            "wall0", "wall1", "wall2", "wall3", "wall4", "wall5", "wall6",
            "entranceToFirstLevel", "entranceToSecondLevel", "entranceToThirdLevel", "entranceToFourthLevel", "pants_greenish"
    };

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
        mobList.add(new Player(150, 250, 2, 100, 0, 1));
        mobList.add(new Slime(300, 300, 1, 5, 0, 10));

        // Единичная загрузка всех текстур
        for (int i = 0, id = 0; i < textureString.length; i++) {
            if (i < 11) id = Texture.loadTexture("healthbar/" + textureString[i]);
            else id = Texture.loadTexture(textureString[i]);
            textureMap.put(textureString[i], id);
        }
        // Единичная загрузка всех хитбоксов
        for(int i = 0; i < aabbString.length; i++)
            aabbMap.put(aabbString[i], new AABB());
        // Единичное установление хитбоксов стен первого уровня и переходов м/у уровнями
        for(int i = 0, j = 0; i < 5; i++, j+=4)
            aabbMap.get("wall" + i).update(firstLevelWalls[j], firstLevelWalls[j + 1], firstLevelWalls[j + 2], firstLevelWalls[j + 3]);
        aabbMap.get("entranceToFirstLevel").update(0, 190, 2, 286);
        aabbMap.get("entranceToSecondLevel").update(638, 238, 640, 335);
        aabbMap.get("pants_greenish").update(300, 100, 364, 164);

        // Клашива ESC на выход(закрытие приложения)
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
                Player player = (Player) mobList.get(0);
                player.getTimerPlayer().cancel();
                player.getTimerPlayer().purge();
                Slime slime = (Slime) mobList.get(1);
                slime.getTimerSlime().cancel();
                slime.getTimerSlime().purge();
            }
            if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS){
                isAttackR = true;
            }
            if (key == GLFW_KEY_LEFT && action == GLFW_PRESS){
                isAttackL = true;
            }
            if (key == GLFW_KEY_UP && action == GLFW_PRESS){
                isAttackU = true;
            }
            if (key == GLFW_KEY_DOWN && action == GLFW_PRESS){
                isAttackD = true;
            }
            if (key == GLFW_KEY_E && action == GLFW_PRESS){
                isCheck = true;
            }
        });
    }

    private void loop() {
        player = (Player) mobList.get(0);
        int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;
        int g = 0, g2 = 0, g3 = 0;
        int j1 = 0, j2 = 0, j3 = 0, j4 = 0;

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            // Изменение скейла изображения
            try (MemoryStack stack = stackPush()) {
                IntBuffer pWidth = stack.mallocInt(1);
                IntBuffer pHeight = stack.mallocInt(1);
                glfwGetWindowSize(window, pWidth, pHeight);
                reshape(pWidth.get(0), pHeight.get(0));
            }

            // Переключение уровня
            switch (level) {
                case "FirstLevel": {
                    Slime slime = (Slime) mobList.get(1);
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("level0")); // Фон первого уровня
                    createQuadTexture(0, 0, 640, 360);

                    //штаны
                    if (bfe){
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("LEGS_pants_greenish_down_move_01"));
                        createQuadTexture(300, 100, 364, 164);
                    }
                    if (isCheck && AABB.AABBvsAABB(player.getCollisionBox(), aabbMap.get("pants_greenish"))){
                        player.setLegs("pants_greenish");
                        bfe = false;
                    }

                    // Все операции со слизнем
                    if (!slime.getDead()) {
                        switch (i5) { // Анимация слайма
                            case 0:
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("slimeLeft"));
                                break;
                            case 1:
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("slimeLeft2"));
                                break;
                            case 2:
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("slimeLeft3"));
                                break;
                        }
                        if (g2 == 8) {
                            if (i5 == 0) { i5++; }
                            else { i5--; }
                            g2 = 0;
                        }
                        g2++;
                        // Преследование игрока слаймом, обновление хитбокса и перерисовка текстуры
                        if (!AABB.AABBvsAABB(player.getHitbox(), slime.getHitbox()) && (int)(Math.random() * 6) == 5) {
                            if(slime.getX() > player.getX()) slime.moveLeft();
                            if(slime.getX() < player.getX()) slime.moveRight();
                            if(slime.getY() > player.getY()) slime.moveUp();
                            if(slime.getY() < player.getY()) slime.moveDown();
                        }
                        slime.getHitbox().update(slime.getX(), slime.getY(), slime.getX() + 16, slime.getY() + 10);
                        slime.getCollisionBox().update(slime.getX(), slime.getY(), slime.getX() + 16, slime.getY() + 10);
                        createQuadTexture(slime.getX(), slime.getY(), slime.getX() + 16, slime.getY() + 10);

                        // Отрисовка хелсбара
                        if (slime.getHealth() <= 0) slime.setDead(true);
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("enemyHp" + slime.getHealth()));
                        createQuadTexture(slime.getX(), slime.getY() - 2, slime.getX() + 16, slime.getY());
                    }
                    else slime.getHitbox().update(0,0,0,0);

                    // Получение урона от слизня
                    if (AABB.AABBvsAABB(player.getHitbox(), slime.getHitbox()) && !player.getDead() && !player.getImmortal()) {
                        if (player.getX() > slime.getX()) player.setKnockbackDirection("Right");
                        else if (player.getX() < slime.getX()) player.setKnockbackDirection("Left");
                        else if (player.getY() > slime.getY()) player.setKnockbackDirection("Down");
                        else if (player.getY() < slime.getY()) player.setKnockbackDirection("Up");
                        player.setHealth(player.getHealth() - slime.getDamage());
                        player.setImmortal(true);
                        player.getTimerPlayer().schedule(player.getTimerTaskPlayer(), 0, 10);
                    }
                    if (AABB.AABBvsAABB(player.getAttackBox(), slime.getHitbox()) && !slime.getImmortal()) {
                        slime.setHealth(slime.getHealth() - player.getDamage());
                        slime.setImmortal(true);
                        slime.getTimerSlime().schedule(slime.getTimerTaskSlime(), 0, 10);
                    }
                    player.getAttackBox().update(0, 0, 0, 0);
                    if (player.getTime() >= 50) {
                        player.stopTimerPlayer();
                        player.setImmortal(false);
                    }
                    if (slime.getTime() >= 50) {
                        slime.stopTimerSlime();
                        slime.setImmortal(false);
                    }

                    // Проверка всех мобов на столкновение со стенами
                    for (Mob mob : mobList) {
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")))
                            mob.moveLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.moveRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")))
                            mob.moveDown();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")))
                            mob.moveUp();
                    }

                    // Переход на второй уровень
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("box"));
                    createQuadTexture(638, 238, 640, 335);

                    // Проверка перехода на второй уровень
                    if (AABB.AABBvsAABB(player.getCollisionBox(), aabbMap.get("entranceToSecondLevel"))) {
                        level = "SecondLevel";
                        // Обновление хитбоксов стен для второго уровня
                        for(int i = 0, j = 0; i < 7; i++, j+=4) {
                            aabbMap.get("wall" + i).update(secondLevelWalls[j], secondLevelWalls[j + 1], secondLevelWalls[j + 2], secondLevelWalls[j + 3]);
                        }
                        player.setX(31);
                        player.setY(239);
                    }
                    break;
                }
                case "SecondLevel": {
                    // Фон второго уровня
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("level1"));
                    createQuadTexture(0, 0, 640, 360);

                    // Проверка всех мобов на столкновение со стенами
                    for (Mob mob : mobList) {
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")))
                            mob.moveLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall5")))
                            mob.moveRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")))
                            mob.moveDown();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall6")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.moveUp();
                    }

                    // Переход на первый уровень
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("box"));
                    createQuadTexture(0, 190, 2, 286);

                    // Проверка перехода на первый уровень
                    if(AABB.AABBvsAABB(player.getCollisionBox(), aabbMap.get("entranceToFirstLevel"))) {
                        level = "FirstLevel";
                        // Обновление хитбоксов стен для первого уровня
                        for(int i = 0, j = 0; i < 5; i++, j+=4) {
                            aabbMap.get("wall" + i).update(firstLevelWalls[j], firstLevelWalls[j + 1], firstLevelWalls[j + 2], firstLevelWalls[j + 3]);
                        }
                        player.setX(579);
                        player.setY(291);
                    }
                    break;
                }
            }

            //Движение игрока и обновление хитбокса
            player_animation = "player_stand_" + player.getDirection();
            pants = "LEGS_" + player.getLegs() + "_" + player.getDirection() + "_move_01";
            if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
                switch (i2) {
                    case 0:
                        player_animation = "player_walk_left_01";
                        pants = "LEGS_" + player.getLegs() + "_left_move_01";
                        break;
                    case 1:
                        player_animation = "player_walk_left_02";
                        pants = "LEGS_" + player.getLegs() + "_left_move_02";
                        break;
                    case 2:
                        player_animation = "player_walk_left_03";
                        pants = "LEGS_" + player.getLegs() + "_left_move_03";
                        break;
                    case 3:
                        player_animation = "player_walk_left_04";
                        pants = "LEGS_" + player.getLegs() + "_left_move_04";
                        break;
                    case 4:
                        player_animation = "player_walk_left_05";
                        pants = "LEGS_" + player.getLegs() + "_left_move_05";
                        break;
                    case 5:
                        player_animation = "player_walk_left_06";
                        pants = "LEGS_" + player.getLegs() + "_left_move_06";
                        break;
                    case 6:
                        player_animation = "player_walk_left_07";
                        pants = "LEGS_" + player.getLegs() + "_left_move_07";
                        break;
                    case 7:
                        player_animation = "player_walk_left_08";
                        pants = "LEGS_" + player.getLegs() + "_left_move_08";
                        break;
                    case 8:
                        i2 = 0;
                        player_animation = "player_walk_left_09";
                        pants = "LEGS_" + player.getLegs() + "_left_move_09";
                        break;
                }
                if (g == 8) {
                    i2++;
                    g = 0;
                }
                g++;
                player.moveLeft();
                player.setDirection("left");
            }
            if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
                switch (i1) {
                    case 0:
                        player_animation = "player_walk_right_01";
                        pants = "LEGS_" + player.getLegs() + "_right_move_01";
                        break;
                    case 1:
                        player_animation = "player_walk_right_02";
                        pants = "LEGS_" + player.getLegs() + "_right_move_02";
                        break;
                    case 2:
                        player_animation = "player_walk_right_03";
                        pants = "LEGS_" + player.getLegs() + "_right_move_03";
                        break;
                    case 3:
                        player_animation = "player_walk_right_04";
                        pants = "LEGS_" + player.getLegs() + "_right_move_04";
                        break;
                    case 4:
                        player_animation = "player_walk_right_05";
                        pants = "LEGS_" + player.getLegs() + "_right_move_05";
                        break;
                    case 5:
                        player_animation = "player_walk_right_06";
                        pants = "LEGS_" + player.getLegs() + "_right_move_06";
                        break;
                    case 6:
                        player_animation = "player_walk_right_07";
                        pants = "LEGS_" + player.getLegs() + "_right_move_07";
                        break;
                    case 7:
                        player_animation = "player_walk_right_08";
                        pants = "LEGS_" + player.getLegs() + "_right_move_08";
                        break;
                    case 8:
                        i1 = 0;
                        player_animation = "player_walk_right_09";
                        pants = "LEGS_" + player.getLegs() + "_right_move_09";
                        break;
                }
                if (g == 8) {
                    i1++;
                    g = 0;
                }
                g++;
                player.moveRight();
                player.setDirection("right");
            }
            if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
                switch (i3) {
                    case 0:
                        player_animation = "player_walk_up_02";
                        pants = "LEGS_" + player.getLegs() + "_up_move_02";
                        break;
                    case 1:
                        player_animation = "player_walk_up_03";
                        pants = "LEGS_" + player.getLegs() + "_up_move_03";
                        break;
                    case 2:
                        player_animation = "player_walk_up_04";
                        pants = "LEGS_" + player.getLegs() + "_up_move_04";
                        break;
                    case 3:
                        player_animation = "player_walk_up_05";
                        pants = "LEGS_" + player.getLegs() + "_up_move_05";
                        break;
                    case 4:
                        player_animation = "player_walk_up_06";
                        pants = "LEGS_" + player.getLegs() + "_up_move_06";
                        break;
                    case 5:
                        player_animation = "player_walk_up_07";
                        pants = "LEGS_" + player.getLegs() + "_up_move_07";
                        break;
                    case 6:
                        player_animation = "player_walk_up_08";
                        pants = "LEGS_" + player.getLegs() + "_up_move_08";
                        break;
                    case 7:
                        i3 = 0;
                        player_animation = "player_walk_up_09";
                        pants = "LEGS_" + player.getLegs() + "_up_move_09";
                        break;
                }
                if (g == 8) {
                    i3++;
                    g = 0;
                }
                g++;
                player.moveUp();
                player.setDirection("up");
            }
            if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
                switch (i4) {
                    case 0:
                        player_animation = "player_walk_down_01";
                        pants = "LEGS_" + player.getLegs() + "_down_move_02";
                        break;
                    case 1:
                        player_animation = "player_walk_down_02";
                        pants = "LEGS_" + player.getLegs() + "_down_move_03";
                        break;
                    case 2:
                        player_animation = "player_walk_down_03";
                        pants = "LEGS_" + player.getLegs() + "_down_move_04";
                        break;
                    case 3:
                        player_animation = "player_walk_down_04";
                        pants = "LEGS_" + player.getLegs() + "_down_move_05";
                        break;
                    case 4:
                        player_animation = "player_walk_down_05";
                        pants = "LEGS_" + player.getLegs() + "_down_move_06";
                        break;
                    case 5:
                        player_animation = "player_walk_down_06";
                        pants = "LEGS_" + player.getLegs() + "_down_move_07";
                        break;
                    case 6:
                        player_animation = "player_walk_down_07";
                        pants = "LEGS_" + player.getLegs() + "_down_move_08";
                        break;
                    case 7:
                        i4 = 0;
                        player_animation = "player_walk_down_08";
                        pants = "LEGS_" + player.getLegs() + "_down_move_09";
                        break;
                }
                if (g == 8) {
                    i4++;
                    g = 0;
                }
                g++;
                player.moveDown();
                player.setDirection("down");
            }
            if (isAttackR){
                switch (j1){
                    case 0:
                        player_animation = "player_slash_right_01";
                        pants = "LEGS_" + player.getLegs() + "_right_slash_01";
                        weapon = "weapon_rapier_right_01";
                        break;
                    case 1:
                        player_animation = "player_slash_right_02";
                        pants = "LEGS_" + player.getLegs() + "_right_slash_02";
                        weapon = "weapon_rapier_right_02";
                        break;
                    case 2:
                        player_animation = "player_slash_right_03";
                        pants = "LEGS_" + player.getLegs() + "_right_slash_03";
                        weapon = "weapon_rapier_right_03";
                        break;
                    case 3:
                        player_animation = "player_slash_right_04";
                        pants = "LEGS_" + player.getLegs() + "_right_slash_04";
                        weapon = "weapon_rapier_right_04";
                        break;
                    case 4:
                        player_animation = "player_slash_right_05";
                        pants = "LEGS_" + player.getLegs() + "_right_slash_05";
                        weapon = "weapon_rapier_right_05";
                        player.getAttackBox().update(player.getX() + 20, player.getY() + 15, player.getX() + 128, player.getY() + 128);
                        break;
                    case 5:
                        player_animation = "player_slash_right_06";
                        pants = "LEGS_" + player.getLegs() + "_right_slash_06";
                        weapon = "weapon_rapier_right_06";
                        j1 = 0;
                        isAttackR = false;
                        break;
                }
                if (g3 == 4){
                    g3 = 0;
                    j1++;
                }
                g3++;
            } else if (isAttackD){
                switch (j2){
                    case 0:
                        player_animation = "player_slash_down_01";
                        pants = "LEGS_" + player.getLegs() + "_down_slash_01";
                        weapon = "weapon_rapier_down_01";
                        break;
                    case 1:
                        player_animation = "player_slash_down_02";
                        pants = "LEGS_" + player.getLegs() + "_down_slash_02";
                        weapon = "weapon_rapier_down_02";
                        break;
                    case 2:
                        player_animation = "player_slash_down_03";
                        pants = "LEGS_" + player.getLegs() + "_down_slash_03";
                        weapon = "weapon_rapier_down_03";
                        break;
                    case 3:
                        player_animation = "player_slash_down_04";
                        pants = "LEGS_" + player.getLegs() + "_down_slash_04";
                        weapon = "weapon_rapier_down_04";
                        break;
                    case 4:
                        player_animation = "player_slash_down_05";
                        pants = "LEGS_" + player.getLegs() + "_down_slash_05";
                        weapon = "weapon_rapier_down_05";
                        player.getAttackBox().update(player.getX() + 20, player.getY() + 15, player.getX() + 128, player.getY() + 128);
                        break;
                    case 5:
                        player_animation = "player_slash_down_06";
                        pants = "LEGS_" + player.getLegs() + "_down_slash_06";
                        weapon = "weapon_rapier_down_06";
                        j2 = 0;
                        isAttackD = false;
                        break;
                }
                if (g3 == 4){
                    g3 = 0;
                    j2++;
                }
                g3++;
            } else if (isAttackL){
                switch (j3){
                    case 0:
                        player_animation = "player_slash_left_01";
                        pants = "LEGS_" + player.getLegs() + "_left_slash_01";
                        weapon = "weapon_rapier_left_01";
                        break;
                    case 1:
                        player_animation = "player_slash_left_02";
                        pants = "LEGS_" + player.getLegs() + "_left_slash_02";
                        weapon = "weapon_rapier_left_02";
                        break;
                    case 2:
                        player_animation = "player_slash_left_03";
                        pants = "LEGS_" + player.getLegs() + "_left_slash_03";
                        weapon = "weapon_rapier_left_03";
                        break;
                    case 3:
                        player_animation = "player_slash_left_04";
                        pants = "LEGS_" + player.getLegs() + "_left_slash_04";
                        weapon = "weapon_rapier_left_04";
                        break;
                    case 4:
                        player_animation = "player_slash_left_05";
                        pants = "LEGS_" + player.getLegs() + "_left_slash_05";
                        weapon = "weapon_rapier_left_05";
                        player.getAttackBox().update(player.getX() + 20, player.getY() + 15, player.getX() + 128, player.getY() + 128);
                        break;
                    case 5:
                        player_animation = "player_slash_left_06";
                        pants = "LEGS_" + player.getLegs() + "_left_slash_06";
                        weapon = "weapon_rapier_left_06";
                        j3 = 0;
                        isAttackL = false;
                        break;
                }
                if (g3 == 4){
                    g3 = 0;
                    j3++;
                }
                g3++;
            } else if (isAttackU){
                switch (j4){
                    case 0:
                        player_animation = "player_slash_up_01";
                        pants = "LEGS_" + player.getLegs() + "_up_slash_01";
                        weapon = "weapon_rapier_up_01";
                        break;
                    case 1:
                        player_animation = "player_slash_up_02";
                        pants = "LEGS_" + player.getLegs() + "_up_slash_02";
                        weapon = "weapon_rapier_up_02";
                        break;
                    case 2:
                        player_animation = "player_slash_up_03";
                        pants = "LEGS_" + player.getLegs() + "_up_slash_03";
                        weapon = "weapon_rapier_up_03";
                        break;
                    case 3:
                        player_animation = "player_slash_up_04";
                        pants = "LEGS_" + player.getLegs() + "_up_slash_04";
                        weapon = "weapon_rapier_up_04";
                        break;
                    case 4:
                        player_animation = "player_slash_up_05";
                        pants = "LEGS_" + player.getLegs() + "_up_slash_05";
                        weapon = "weapon_rapier_up_05";
                        player.getAttackBox().update(player.getX() + 20, player.getY() + 15, player.getX() + 128, player.getY() + 128);
                        break;
                    case 5:
                        player_animation = "player_slash_up_06";
                        pants = "LEGS_" + player.getLegs() + "_up_slash_06";
                        weapon = "weapon_rapier_up_06";
                        j4 = 0;
                        isAttackU = false;
                        break;
                }
                if (g3 == 4){
                    g3 = 0;
                    j4++;
                }
                g3++;
            }
            // отрисовка экипировки
            glBindTexture(GL_TEXTURE_2D, textureMap.get(player_animation));
            createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            if (!player.getLegs().equals("nothing")){
                glBindTexture(GL_TEXTURE_2D, textureMap.get(pants));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (isAttackR || isAttackU || isAttackL || isAttackD){
                glBindTexture(GL_TEXTURE_2D, textureMap.get(weapon));
                createQuadTexture(player.getX() - 64, player.getY() - 64, player.getX() + 128, player.getY() + 128);
            }

            player.getHitbox().update(player.getX() + 15, player.getY() + 14, player.getX() + 15 + 30, player.getY() + 14 + 48);
            player.getCollisionBox().update(player.getX() + 15, player.getY() + 14 + 32, player.getX() + 15 + 30, player.getY() + 14 + 32 + 16);

            // Полоска здоровья
            int tempHealth = player.getHealth() % 10 == 0 ? player.getHealth() : player.getHealth() + 10 - (player.getHealth() % 10);
            glBindTexture(GL_TEXTURE_2D, textureMap.get(tempHealth + "hp"));
            if (player.getHealth() == 0) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get("0hp"));
                player.setDead(true);
            }
            createQuadTexture(0, 0, 103, 18);

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
        if(forScale) {
            glScaled(3, 3, 1);
            forScale = false;
        }
    }
}