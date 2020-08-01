import math.AABB;
import mobs.Mob;
import mobs.Player;
import mobs.Slime;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long window;
//    int[] idTextures;
    int idBox, idPlayerStand, idLevel0, idLevel1;
    int idPlayerLeft, idPlayerLeft2, idPlayerLeft3;
    int idPlayerRight, idPlayerRight2, idPlayerRight3;
    int idPlayerUp, idPlayerUp2, idPlayerUp3;
    int idPlayerDown, idPlayerDown2, idPlayerDown3;
    int idSlime, idSlime2;
    ArrayList<Mob> mobList;
    AABB wall0, wall1, wall2, wall3, wall4, wall5, wall6, entranceToFirstLevel, entranceToSecondLevel;
    int[] idHealthbar;
    String level = "FirstLevel";
    boolean a = true;

    public void run() {
        System.out.println("Start");

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

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(1920, 1080, "Philistine", NULL, NULL);
        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

        // Работа с экраном
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities(); // создает instance для OpenGL в текущем потоке
        glMatrixMode(GL_PROJECTION); // Выставление камеры
        glLoadIdentity(); // По видимости ненужная строка(что-то с единичной матрицей)
        glOrtho(0, 640, 480, 0, 1, -1); // Камера на место окна
        glMatrixMode(GL_MODELVIEW); // Установка матрицы в состояние ModelView
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // Добавляет прозрачность
        glEnable(GL_BLEND);

        idHealthbar = new int[11];
        mobList = new ArrayList<>();
        mobList.add(new Player(150, 250, 2, 100, 0, 1));
        mobList.add(new Slime(300, 300, 1, 5, 0, 10));
        wall0 = new AABB();
        wall1 = new AABB();
        wall2 = new AABB();
        wall3 = new AABB();
        wall4 = new AABB();
        wall5 = new AABB();
        wall6 = new AABB();

        entranceToSecondLevel = new AABB();
        entranceToFirstLevel = new AABB();

        // Единичная загрузка всех текстур
        idBox = Texture.loadTexture("box");
        idLevel0 = Texture.loadTexture("level0");
        idLevel1 = Texture.loadTexture("level1");
        idPlayerStand = Texture.loadTexture("player_stand");
        idPlayerRight = Texture.loadTexture("player_right");
        idPlayerRight2 = Texture.loadTexture("player_right2");
        idPlayerRight3 = Texture.loadTexture("player_right3");
        idPlayerLeft = Texture.loadTexture("player_left");
        idPlayerLeft2 = Texture.loadTexture("player_left2");
        idPlayerLeft3 = Texture.loadTexture("player_left3");
        idPlayerUp = Texture.loadTexture("player_up");
        idPlayerUp2 = Texture.loadTexture("player_up2");
        idPlayerUp3 = Texture.loadTexture("player_up3");
        idPlayerDown = Texture.loadTexture("player_down");
        idPlayerDown2 = Texture.loadTexture("player_down2");
        idPlayerDown3 = Texture.loadTexture("player_down3");
        idSlime = Texture.loadTexture("slime");
        idSlime2 = Texture.loadTexture("slime2");
        idHealthbar[0] = Texture.loadTexture("healthbar/0hp");
        idHealthbar[1] = Texture.loadTexture("healthbar/10hp");
        idHealthbar[2] = Texture.loadTexture("healthbar/20hp");
        idHealthbar[3] = Texture.loadTexture("healthbar/30hp");
        idHealthbar[4] = Texture.loadTexture("healthbar/40hp");
        idHealthbar[5] = Texture.loadTexture("healthbar/50hp");
        idHealthbar[6] = Texture.loadTexture("healthbar/60hp");
        idHealthbar[7] = Texture.loadTexture("healthbar/70hp");
        idHealthbar[8] = Texture.loadTexture("healthbar/80hp");
        idHealthbar[9] = Texture.loadTexture("healthbar/90hp");
        idHealthbar[10] = Texture.loadTexture("healthbar/100hp");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> { // Клашива ESC на выход(закрытие приложения)
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
                Player player = (Player) mobList.get(0);
                player.getTimerPlayer().cancel();
                player.getTimerPlayer().purge();
            }
        });
    }

    private void loop() {
        Player player = (Player) mobList.get(0);
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

            switch (level) {
                case "FirstLevel": {
                    Slime slime = (Slime) mobList.get(1);
                    // Установление хитбоксов стен
                    wall0.update(60, 60, 455, 130);
                    wall1.update(0, 189, 65, 456);
                    wall2.update(60, 451, 640, 480);
                    wall3.update(451, 312, 640, 261);
                    wall4.update(449, 188, 451, 259);

                    glBindTexture(GL_TEXTURE_2D, idLevel0); // Фон первого уровня
                    createQuadTexture(0, 0, 640, 480);

                    glBindTexture(GL_TEXTURE_2D, idSlime);  // Текстура слайма
                    switch (i5) { // Анимация слайма
                        case 0:
                            glBindTexture(GL_TEXTURE_2D, idSlime);
                            break;
                        case 1:
                            glBindTexture(GL_TEXTURE_2D, idSlime2);
                            break;
                    }
                    if (g2 == 8) {
                        if (i5 == 0) { i5++; }
                        else { i5--; }
                        g2 = 0;
                    }
                    g2++;
                    // Преследование игрока слаймом
                    if (!AABB.AABBvsAABB(player.getHitbox(), slime.getHitbox()) && (int)(Math.random() * 6) == 5) {
                        if(slime.getX() > player.getX()) slime.setX(slime.getX() - slime.getSpeed());
                        if(slime.getX() < player.getX()) slime.setX(slime.getX() + slime.getSpeed());
                        if(slime.getY() > player.getY()) slime.setY(slime.getY() - slime.getSpeed());
                        if(slime.getY() < player.getY()) slime.setY(slime.getY() + slime.getSpeed());
                    }
                    slime.getHitbox().update(slime.getX(), slime.getY(), slime.getX() + 30, slime.getY() + 30); // Обновление хитбокса слаймв
                    createQuadTexture(slime.getX(), slime.getY(), slime.getX() + 30, slime.getY() + 30);

                    if (AABB.AABBvsAABB(player.getHitbox(), slime.getHitbox()) && !player.getDead() && !player.getImmortal()) {
                        player.setHealth(player.getHealth() - slime.getDamage());
                        player.setImmortal(true);
                        player.getTimerPlayer().schedule(player.getTimerTaskPlayer(), 0, 10);
                    }
                    if (player.getTime() >= 50) {
                        player.stopTimerPlayer();
                        player.setImmortal(false);
                    }

                    // Проверка всех мобов на столкновение со стенами
                    for (Mob mob : mobList) {
                        if (AABB.AABBvsAABB(mob.getHitbox(), wall4))
                            mob.setX(mob.getX() - mob.getSpeed());
                        if (AABB.AABBvsAABB(mob.getHitbox(), wall1))
                            mob.setX(mob.getX() + mob.getSpeed());
                        if (AABB.AABBvsAABB(mob.getHitbox(), wall0) || AABB.AABBvsAABB(mob.getHitbox(), wall3))
                            mob.setY(mob.getY() + mob.getSpeed());
                        if (AABB.AABBvsAABB(mob.getHitbox(), wall2))
                            mob.setY(mob.getY() - mob.getSpeed());
                    }

                    glBindTexture(GL_TEXTURE_2D, idBox); // Переход на второй уровень
                    createQuadTexture(610, 315, 640, 445);
                    entranceToSecondLevel.update(610, 315, 640, 445);

                    // Проверка перехода на второй уровень
                    if (AABB.AABBvsAABB(player.getHitbox(), entranceToSecondLevel)) {
                        level = "SecondLevel";
                        player.setX(35);
                        player.setY(280);
                    }
                    break;
                }
                case "SecondLevel": {
                    // Установление хитбоксов стен
                    wall0.update(0, 190, 126, 192);
                    wall1.update(126, 185, 128, 190);
                    wall2.update(128, 186, 580, 128);
                    wall3.update(577, 188, 580, 452);
                    wall4.update(128, 452, 580, 458);
                    wall5.update(126, 385, 128, 453);
                    wall6.update(0, 384, 126, 388);

                    glBindTexture(GL_TEXTURE_2D, idLevel1); // Фон второго уровня
                    createQuadTexture(0, 0, 640, 480);

                    // Проверка всех мобов на столкновение со стенами
                    for (Mob mob : mobList) {
                        if (AABB.AABBvsAABB(mob.getHitbox(), wall3))
                            mob.setX(mob.getX() - mob.getSpeed());
                        if (AABB.AABBvsAABB(mob.getHitbox(), wall1) || AABB.AABBvsAABB(mob.getHitbox(), wall5))
                            mob.setX(mob.getX() + mob.getSpeed());
                        if (AABB.AABBvsAABB(mob.getHitbox(), wall0) || AABB.AABBvsAABB(mob.getHitbox(), wall2))
                            mob.setY(mob.getY() + mob.getSpeed());
                        if (AABB.AABBvsAABB(mob.getHitbox(), wall6) || AABB.AABBvsAABB(mob.getHitbox(), wall4))
                            mob.setY(mob.getY() - mob.getSpeed());
                    }

                    glBindTexture(GL_TEXTURE_2D, idBox); // Переход на первый уровень
                    createQuadTexture(0, 250, 30, 380);
                    entranceToFirstLevel.update(0, 250, 30, 380);

                    // Проверка перехода на первый уровень
                    if(AABB.AABBvsAABB(player.getHitbox(), entranceToFirstLevel)) {
                        level = "FirstLevel";
                        player.setX(565);
                        player.setY(340);
                    }
                    break;
                }
            }

            //Движение игрока и обновление хитбокса
            glBindTexture(GL_TEXTURE_2D, idPlayerStand);
            if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
                switch (i1){
                    case 0:
                        glBindTexture(GL_TEXTURE_2D, idPlayerRight);
                        break;
                    case 1:
                        glBindTexture(GL_TEXTURE_2D, idPlayerRight3);
                        break;
                    case 2:
                        glBindTexture(GL_TEXTURE_2D, idPlayerRight2);
                        break;
                    case 3:
                        i1 = 0;
                        glBindTexture(GL_TEXTURE_2D, idPlayerRight3);
                        break;
                }
                if (g == 12){
                    i1++;
                    g = 0;
                }
                g++;
                player.setX(player.getX() + player.getSpeed());
            }
            if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
                switch (i2){
                    case 0:
                        glBindTexture(GL_TEXTURE_2D, idPlayerLeft);
                        break;
                    case 1:
                        glBindTexture(GL_TEXTURE_2D, idPlayerLeft3);
                        break;
                    case 2:
                        glBindTexture(GL_TEXTURE_2D, idPlayerLeft2);
                        break;
                    case 3:
                        i2 = 0;
                        glBindTexture(GL_TEXTURE_2D, idPlayerLeft3);
                        break;
                }
                if (g == 12) {
                    i2++;
                    g = 0;
                }
                g++;
                player.setX(player.getX() - player.getSpeed());
            }
            if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
                switch (i3){
                    case 0:
                        glBindTexture(GL_TEXTURE_2D, idPlayerUp);
                        break;
                    case 1:
                        glBindTexture(GL_TEXTURE_2D, idPlayerUp3);
                        break;
                    case 2:
                        glBindTexture(GL_TEXTURE_2D, idPlayerUp2);
                        break;
                    case 3:
                        i3 = 0;
                        glBindTexture(GL_TEXTURE_2D, idPlayerUp3);
                        break;
                }
                if (g == 12) {
                    i3++;
                    g = 0;
                }
                g++;
                player.setY(player.getY() - player.getSpeed());
            }
            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
                switch (i4){
                    case 0:
                        glBindTexture(GL_TEXTURE_2D, idPlayerDown);
                        break;
                    case 1:
                        glBindTexture(GL_TEXTURE_2D, idPlayerDown3);
                        break;
                    case 2:
                        glBindTexture(GL_TEXTURE_2D, idPlayerDown2);
                        break;
                    case 3:
                        i4 = 0;
                        glBindTexture(GL_TEXTURE_2D, idPlayerDown3);
                        break;
                }
                if (g == 12){
                    i4++;
                    g = 0;
                }
                g++;
                player.setY(player.getY() + player.getSpeed());
            }
            createQuadTexture(player.getX(), player.getY(), player.getX() + 42, player.getY() + 64);
            player.getHitbox().update(player.getX(), player.getY(), player.getX() + 42, player.getY() + 64);

            //Полоска здоровья
            if(player.getHealth() == 100) { glBindTexture(GL_TEXTURE_2D, idHealthbar[10]); }
            else {
                if(player.getHealth() < 100 && player.getHealth() > 90) glBindTexture(GL_TEXTURE_2D, idHealthbar[10]);
                if(player.getHealth() <= 90 && player.getHealth() > 80) glBindTexture(GL_TEXTURE_2D, idHealthbar[9]);
                if(player.getHealth() <= 80 && player.getHealth() > 70) glBindTexture(GL_TEXTURE_2D, idHealthbar[8]);
                if(player.getHealth() <= 70 && player.getHealth() > 60) glBindTexture(GL_TEXTURE_2D, idHealthbar[7]);
                if(player.getHealth() <= 60 && player.getHealth() > 50) glBindTexture(GL_TEXTURE_2D, idHealthbar[6]);
                if(player.getHealth() <= 50 && player.getHealth() > 40) glBindTexture(GL_TEXTURE_2D, idHealthbar[5]);
                if(player.getHealth() <= 40 && player.getHealth() > 30) glBindTexture(GL_TEXTURE_2D, idHealthbar[4]);
                if(player.getHealth() <= 30 && player.getHealth() > 20) glBindTexture(GL_TEXTURE_2D, idHealthbar[3]);
                if(player.getHealth() <= 20 && player.getHealth() > 10) glBindTexture(GL_TEXTURE_2D, idHealthbar[2]);
                if(player.getHealth() <= 10 && player.getHealth() > 0) glBindTexture(GL_TEXTURE_2D, idHealthbar[1]);
                if(player.getHealth() == 0) {
                    glBindTexture(GL_TEXTURE_2D, idHealthbar[0]);
                    player.setDead(true);
                }
            }
            createQuadTexture(5, 5, 205, 37);

            glfwSwapBuffers(window);
            glfwPollEvents();
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
        if (a) {
            glScaled(2, 2, 1);
            a = false;
        }
    }
}