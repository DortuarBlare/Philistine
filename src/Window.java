import math.AABB;
import mobs.Mob;
import mobs.Player;
import mobs.Slime;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

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
    String level = "FirstLevel";
    boolean forScale = true;
    Player player;
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
            "player_walk_left_01", "player_walk_left_02", "player_walk_left_03", "player_walk_left_04", "player_walk_left_05", "player_walk_left_06", "player_walk_left_07", "player_walk_left_08", "player_walk_left_09",
            "player_walk_right_01", "player_walk_right_02", "player_walk_right_03", "player_walk_right_04", "player_walk_right_05", "player_walk_right_06", "player_walk_right_07", "player_walk_right_08", "player_walk_right_09",
            "player_walk_up_01", "player_walk_up_02", "player_walk_up_03", "player_walk_up_04", "player_walk_up_05", "player_walk_up_06", "player_walk_up_07", "player_walk_up_08", "player_walk_up_09",
            "player_walk_down_01", "player_walk_down_02", "player_walk_down_03", "player_walk_down_04", "player_walk_down_05", "player_walk_down_06", "player_walk_down_07", "player_walk_down_08",
            "slimeLeft", "slimeLeft2", "slimeLeft3",
            "slimeRight", "slimeRight2", "slimeRight3",
            "level0", "level1", "level2", "level3", "box", "player_stand", "playerAttack", "sword",
            "torch0", "torch1", "torch2", "torch3",
            "enemyHp0", "enemyHp1", "enemyHp2", "enemyHp3", "enemyHp4", "enemyHp5", "pants",
            "player_slash_right_01", "player_slash_right_02", "player_slash_right_03", "player_slash_right_04", "player_slash_right_05", "player_slash_right_06"
    };
    private final String[] aabbString = {
            "wall0", "wall1", "wall2", "wall3", "wall4", "wall5", "wall6",
            "entranceToFirstLevel", "entranceToSecondLevel", "entranceToThirdLevel", "entranceToFourthLevel"
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
            if(i < 11) id = Texture.loadTexture("healthbar/" + textureString[i]);
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

        // Клашива ESC на выход(закрытие приложения)
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
                Player player = (Player) mobList.get(0);
                player.getTimerPlayer().cancel();
                player.getTimerPlayer().purge();
                Slime slime = (Slime) mobList.get(1);
                slime.getTimerSlime().cancel();
                slime.getTimerSlime().purge();
            }
            if (key == GLFW_KEY_Z && action == GLFW_PRESS){
                player = (Player) mobList.get(0);
                glBindTexture(GL_TEXTURE_2D, textureMap.get("sword"));
                createQuadTexture(player.getX() + 20, player.getY() + 5, player.getX() + 20 + 67, player.getY() + 5 + 25);
                player.getAttackBox().update(player.getX() + 20, player.getY() + 15, player.getX() + 20 + 67, player.getY() + 15 + 25);
                glBindTexture(GL_TEXTURE_2D, textureMap.get("player_slash_right_01"));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
                glfwSwapBuffers(window);
                glBindTexture(GL_TEXTURE_2D, textureMap.get("player_slash_right_02"));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
                    glfwSwapBuffers(window);
                glBindTexture(GL_TEXTURE_2D, textureMap.get("player_slash_right_03"));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
                    glfwSwapBuffers(window);
                glBindTexture(GL_TEXTURE_2D, textureMap.get("player_slash_right_04"));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
                    glfwSwapBuffers(window);
                glBindTexture(GL_TEXTURE_2D, textureMap.get("player_slash_right_05"));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
                    glfwSwapBuffers(window);
                glBindTexture(GL_TEXTURE_2D, textureMap.get("player_slash_right_06"));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
                    glfwSwapBuffers(window);
            }
        });
    }

    private void loop() {
        player = (Player) mobList.get(0);
        int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;
        int g = 0, g2 = 0;

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
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("torch0"));
                    createQuadTexture(186, 83, 194, 107);

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
                        if (player.getX() > slime.getX()) player.setDirection("Right");
                        else if (player.getX() < slime.getX()) player.setDirection("Left");
                        else if (player.getY() > slime.getY()) player.setDirection("Down");
                        else if (player.getY() < slime.getY()) player.setDirection("Up");
                        player.setHealth(player.getHealth() - slime.getDamage());
                        player.setImmortal(true);
                        player.getTimerPlayer().schedule(player.getTimerTaskPlayer(), 0, 10);
                    }
                    if (AABB.AABBvsAABB(player.getAttackBox(), slime.getHitbox()) && !slime.getImmortal()) {
                        slime.setHealth(slime.getHealth() - player.getDamage());
                        slime.setImmortal(true);
                        slime.getTimerSlime().schedule(slime.getTimerTaskSlime(), 0, 10);
                    }
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
            glBindTexture(GL_TEXTURE_2D, textureMap.get("player_stand"));
            if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
                switch (i2) {
                    case 0:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_left_01"));
                        break;
                    case 1:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_left_02"));
                        break;
                    case 2:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_left_03"));
                        break;
                    case 3:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_left_04"));
                        break;
                    case 4:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_left_05"));
                        break;
                    case 5:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_left_06"));
                        break;
                    case 6:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_left_07"));
                        break;
                    case 7:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_left_08"));
                        break;
                    case 8:
                        i2 = 0;
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_left_09"));
                        break;
                }
                if (g == 8) {
                    i2++;
                    g = 0;
                }
                g++;
                player.moveLeft();
            }
            if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
                switch (i1) {
                    case 0:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_right_01"));
                        break;
                    case 1:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_right_02"));
                        break;
                    case 2:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_right_03"));
                        break;
                    case 3:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_right_04"));
                        break;
                    case 4:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_right_05"));
                        break;
                    case 5:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_right_06"));
                        break;
                    case 6:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_right_07"));
                        break;
                    case 7:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_right_08"));
                        break;
                    case 8:
                        i1 = 0;
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_right_09"));
                        break;
                }
                if (g == 8) {
                    i1++;
                    g = 0;
                }
                g++;
                player.moveRight();
            }
            if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
                switch (i3) {
                    case 0:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_up_01"));
                        break;
                    case 1:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_up_02"));
                        break;
                    case 2:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_up_03"));
                        break;
                    case 3:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_up_04"));
                        break;
                    case 4:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_up_05"));
                        break;
                    case 5:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_up_06"));
                        break;
                    case 6:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_up_07"));
                        break;
                    case 7:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_up_08"));
                        break;
                    case 8:
                        i3 = 0;
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_up_09"));
                        break;
                }
                if (g == 8) {
                    i3++;
                    g = 0;
                }
                g++;
                player.moveUp();
            }
            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
                switch (i4) {
                    case 0:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_down_01"));
                        break;
                    case 1:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_down_02"));
                        break;
                    case 2:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_down_03"));
                        break;
                    case 3:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_down_04"));
                        break;
                    case 4:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_down_05"));
                        break;
                    case 5:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_down_06"));
                        break;
                    case 6:
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_down_07"));
                        break;
                    case 7:
                        i4 = 0;
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("player_walk_down_08"));
                        break;
                }
                if (g == 8) {
                    i4++;
                    g = 0;
                }
                g++;
                player.moveDown();
            }

            createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            player.getHitbox().update(player.getX() + 15, player.getY() + 14, player.getX() + 15 + 30, player.getY() + 14 + 48);
            player.getCollisionBox().update(player.getX() + 15, player.getY() + 14 + 32, player.getX() + 15 + 30, player.getY() + 14 + 32 + 16);

            //Полоска здоровья
            if(player.getHealth() == 100) { glBindTexture(GL_TEXTURE_2D, textureMap.get("100hp")); }
            else {
                if(player.getHealth() < 100 && player.getHealth() > 90) glBindTexture(GL_TEXTURE_2D, textureMap.get("100hp"));
                if(player.getHealth() <= 90 && player.getHealth() > 80) glBindTexture(GL_TEXTURE_2D, textureMap.get("90hp"));
                if(player.getHealth() <= 80 && player.getHealth() > 70) glBindTexture(GL_TEXTURE_2D, textureMap.get("80hp"));
                if(player.getHealth() <= 70 && player.getHealth() > 60) glBindTexture(GL_TEXTURE_2D, textureMap.get("70hp"));
                if(player.getHealth() <= 60 && player.getHealth() > 50) glBindTexture(GL_TEXTURE_2D, textureMap.get("60hp"));
                if(player.getHealth() <= 50 && player.getHealth() > 40) glBindTexture(GL_TEXTURE_2D, textureMap.get("50hp"));
                if(player.getHealth() <= 40 && player.getHealth() > 30) glBindTexture(GL_TEXTURE_2D, textureMap.get("40hp"));
                if(player.getHealth() <= 30 && player.getHealth() > 20) glBindTexture(GL_TEXTURE_2D, textureMap.get("30hp"));
                if(player.getHealth() <= 20 && player.getHealth() > 10) glBindTexture(GL_TEXTURE_2D, textureMap.get("20hp"));
                if(player.getHealth() <= 10 && player.getHealth() > 0) glBindTexture(GL_TEXTURE_2D, textureMap.get("10hp"));
                if(player.getHealth() == 0) {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("0hp"));
                    player.setDead(true);
                }
            }
            createQuadTexture(5, 5, 105, 21);


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