import math.AABB;
import mobs.Player;
import mobs.Slime;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window implements Runnable {
    boolean running = false;
    private long window;
//    int[] idTextures;
    int idBox, idPlayerStand, idLevel0, idLevel1;
    int idPlayerLeft, idPlayerLeft2, idPlayerLeft3;
    int idPlayerRight, idPlayerRight2, idPlayerRight3;
    int idPlayerUp, idPlayerUp2, idPlayerUp3;
    int idPlayerDown, idPlayerDown2, idPlayerDown3;
    int idSlime, idSlime2;
    Player player = new Player(250, 250, 2, 100, 0, 1);
    Slime slime = new Slime(300, 300, 2, 5, 0, 10);
    AABB wall0, wall1, wall2, wall3, wall4, entranceToFirstLevel, entranceToSecondLevel;
    int[] idHealthbar;
    String level = "FirstLevel";

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

        window = glfwCreateWindow(640, 480, "Philistine", NULL, NULL);
        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> { // Клашива ESC на выход(закрытие приложения)
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
                running = false;
            }
        });

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

        idHealthbar = new int[6];
        wall0 = new AABB();
        wall1 = new AABB();
        wall2 = new AABB();
        wall3 = new AABB();
        wall4 = new AABB();
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
        idHealthbar[1] = Texture.loadTexture("healthbar/20hp");
        idHealthbar[2] = Texture.loadTexture("healthbar/40hp");
        idHealthbar[3] = Texture.loadTexture("healthbar/60hp");
        idHealthbar[4] = Texture.loadTexture("healthbar/80hp");
        idHealthbar[5] = Texture.loadTexture("healthbar/100hp");
    }

    private void loop() {
        int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;
        int g = 0, g2 = 0;

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            switch (level) {
                case "FirstLevel": {
                    //Пытаюсь стену сделать
                    glBindTexture(GL_TEXTURE_2D, idBox);
                    glBegin(GL_QUADS);
                    glTexCoord2d(0, 0);
                    glVertex2f(60, 60);
                    glTexCoord2d(1, 0);
                    glVertex2f(455, 60);
                    glTexCoord2d(1, 1);
                    glVertex2f(455,185);
                    glTexCoord2d(0, 1);
                    glVertex2f(60, 185);
                    glEnd();
                    wall0.update(60, 60, 455, 130);
                    glBegin(GL_QUADS);
                    glTexCoord2d(0, 0);
                    glVertex2f(0, 189);
                    glTexCoord2d(1, 0);
                    glVertex2f(65, 189);
                    glTexCoord2d(1, 1);
                    glVertex2f(65,456);
                    glTexCoord2d(0, 1);
                    glVertex2f(0, 456);
                    glEnd();
                    wall1.update(0, 189, 65, 456);
                    glBegin(GL_QUADS);
                    glTexCoord2d(0, 0);
                    glVertex2f(60, 453);
                    glTexCoord2d(1, 0);
                    glVertex2f(640, 453);
                    glTexCoord2d(1, 1);
                    glVertex2f(640,480);
                    glTexCoord2d(0, 1);
                    glVertex2f(60, 480);
                    glEnd();
                    wall2.update(60, 451, 640, 480);
                    glBegin(GL_QUADS);
                    glTexCoord2d(0, 0);
                    glVertex2f(449, 312);
                    glTexCoord2d(1, 0);
                    glVertex2f(640, 312);
                    glTexCoord2d(1, 1);
                    glVertex2f(640,316);
                    glTexCoord2d(0, 1);
                    glVertex2f(449, 316);
                    glEnd();
                    wall3.update(451, 312, 640, 261);
                    glBegin(GL_QUADS);
                    glTexCoord2d(0, 0);
                    glVertex2f(449, 188);
                    glTexCoord2d(1, 0);
                    glVertex2f(451, 188);
                    glTexCoord2d(1, 1);
                    glVertex2f(451,315);
                    glTexCoord2d(0, 1);
                    glVertex2f(449, 315);
                    glEnd();
                    wall4.update(449, 188, 451, 259);
                    //Заканчиваю пытаться
                    glBindTexture(GL_TEXTURE_2D, idLevel0); // Фон первого уровня
                    glBegin(GL_QUADS);
                    glTexCoord2d(0, 0);
                    glVertex2f(0, 0);
                    glTexCoord2d(1, 0);
                    glVertex2f(640, 0);
                    glTexCoord2d(1, 1);
                    glVertex2f(640, 480);
                    glTexCoord2d(0, 1);
                    glVertex2f(0, 480);
                    glEnd();

                    glBindTexture(GL_TEXTURE_2D, idSlime);  // Текстура слайма
                    switch (i5){
                        case 0:
                            glBindTexture(GL_TEXTURE_2D, idSlime);
                            break;
                        case 1:
                            glBindTexture(GL_TEXTURE_2D, idSlime2);
                            break;
                    }   // Анимация слайма
                    if (g2 == 8) {
                        if (i5 == 0) { i5++; }
                        else { i5--; }
                        g2 = 0;
                    }
                    g2++;
//                    xSlime += Math.random() * 2;
//                    ySlime += Math.random() * 2;

                    glBegin(GL_QUADS);  // Отрисовка квадрата, на который натягивается текстура
                    glTexCoord2d(0, 0);
                    glVertex2f(slime.getX(), slime.getY());
                    glTexCoord2d(1, 0);
                    glVertex2f(slime.getX() + 30, slime.getY());
                    glTexCoord2d(1, 1);
                    glVertex2f(slime.getX() + 30, slime.getY() + 30);
                    glTexCoord2d(0, 1);
                    glVertex2f(slime.getX(), slime.getY() + 30);
                    glEnd();

                    glBindTexture(GL_TEXTURE_2D, idBox); // Переход на второй уровень
                    glBegin(GL_QUADS);
                    glTexCoord2d(0, 0);
                    glVertex2f(610, 315);
                    glTexCoord2d(1, 0);
                    glVertex2f(640, 315);
                    glTexCoord2d(1, 1);
                    glVertex2f(640,445);
                    glTexCoord2d(0, 1);
                    glVertex2f(610, 445);
                    glEnd();
                    entranceToSecondLevel.update(610, 315, 640, 445);
                    break;
                }
                case "SecondLevel": {
                    glBindTexture(GL_TEXTURE_2D, idLevel1); // Фон второго уровня
                    glBegin(GL_QUADS);
                    glTexCoord2d(0, 0);
                    glVertex2f(0, 0);
                    glTexCoord2d(1, 0);
                    glVertex2f(640, 0);
                    glTexCoord2d(1, 1);
                    glVertex2f(640, 480);
                    glTexCoord2d(0, 1);
                    glVertex2f(0, 480);
                    glEnd();

                    glBindTexture(GL_TEXTURE_2D, idBox);
                    glBegin(GL_QUADS);
                    glTexCoord2d(0, 0);
                    glVertex2f(0, 250);
                    glTexCoord2d(1, 0);
                    glVertex2f(30, 250);
                    glTexCoord2d(1, 1);
                    glVertex2f(30,380);
                    glTexCoord2d(0, 1);
                    glVertex2f(0, 380);
                    glEnd();
                    entranceToFirstLevel.update(0, 250, 30, 380);
                    break;
                }
            }

            if(player.getHealth() == 100) {
                glBindTexture(GL_TEXTURE_2D, idHealthbar[5]);
            }
            else if(player.getHealth() < 100) {
                if(player.getHealth() < 100 && player.getHealth() > 80) glBindTexture(GL_TEXTURE_2D, idHealthbar[5]);
                if(player.getHealth() <= 80 && player.getHealth() > 60) glBindTexture(GL_TEXTURE_2D, idHealthbar[4]);
                if(player.getHealth() <= 60 && player.getHealth() > 40) glBindTexture(GL_TEXTURE_2D, idHealthbar[3]);
                if(player.getHealth() <= 40 && player.getHealth() > 20) glBindTexture(GL_TEXTURE_2D, idHealthbar[2]);
                if(player.getHealth() <= 20 && player.getHealth() > 0) glBindTexture(GL_TEXTURE_2D, idHealthbar[1]);
                if(player.getHealth() == 0) {
                    glBindTexture(GL_TEXTURE_2D, idHealthbar[0]);
                    player.setDead(true);
                }

            }
            glBegin(GL_QUADS);
            glTexCoord2d(0, 0);
            glVertex2f(0, 0);
            glTexCoord2d(1, 0);
            glVertex2f(200, 0);
            glTexCoord2d(1, 1);
            glVertex2f(200, 32);
            glTexCoord2d(0, 1);
            glVertex2f(0, 32);
            glEnd();

            player.getHitbox().update(player.getX(), player.getY(), player.getX() + 42, player.getY() + 64);
            if (AABB.AABBvsAABB(player.getHitbox(), slime.getHitbox()) && level == "FirstLevel" && !player.getDead()) {
                player.setHealth(player.getHealth() - slime.getDamage());
                player.setX(player.getX() - 100);
            }
            if(AABB.AABBvsAABB(player.getHitbox(), entranceToSecondLevel) && level == "FirstLevel") {
                level = "SecondLevel";
                player.setX(35);
                player.setY(280);
            }
            if(AABB.AABBvsAABB(player.getHitbox(), entranceToFirstLevel) && level == "SecondLevel") {
                level = "FirstLevel";
                player.setX(565);
                player.setY(340);
            }

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
                if (g == 8){
                    i1++;
                    g = 0;
                }
                g++;
                if (!AABB.AABBvsAABB(player.getHitbox(), wall4))  player.setX(player.getX() + 2);
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
                if (g == 8){
                    i2++;
                    g = 0;
                }
                g++;
                if (!AABB.AABBvsAABB(player.getHitbox(), wall1))  player.setX(player.getX() - 2);
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
                if (g == 8) {
                    i3++;
                    g = 0;
                }
                g++;

                if (!AABB.AABBvsAABB(player.getHitbox(), wall0) && !AABB.AABBvsAABB(player.getHitbox(), wall3)) player.setY(player.getY() - 2);
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
                if (g == 8){
                    i4++;
                    g = 0;
                }
                g++;
                if (!AABB.AABBvsAABB(player.getHitbox(), wall2))  player.setY(player.getY() + 2);
            }
            glBegin(GL_QUADS);
            glTexCoord2d(0, 0);
            glVertex2f(player.getX(), player.getY());
            glTexCoord2d(1, 0);
            glVertex2f(player.getX() + 42, player.getY());
            glTexCoord2d(1, 1);
            glVertex2f(player.getX() + 42, player.getY() + 64);
            glTexCoord2d(0, 1);
            glVertex2f(player.getX(), player.getY() + 64);
            glEnd();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}