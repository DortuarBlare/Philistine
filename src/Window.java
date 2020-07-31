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
    private Thread thread;
    boolean running = false;
    boolean dead = false;
    private long window;
    private int xPlayer = 200, yPlayer = 200, xSlime = 250, ySlime = 250;
    AABB playerBox = new AABB();
    AABB slimeBox = new AABB();
    int idBox, idPlayerStand, idLevel0, idLevel1;
    int idPlayerLeft, idPlayerLeft2, idPlayerLeft3;
    int idPlayerRight, idPlayerRight2, idPlayerRight3;
    int idPlayerUp, idPlayerUp2, idPlayerUp3;
    int idPlayerDown, idPlayerDown2, idPlayerDown3;
    int idSlime, idSlime2;
    int[] idHealthbar;
    int playerHealth = 6;
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

    private void init(){
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

        idHealthbar = new int[7];

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
        idHealthbar[0] = Texture.loadTexture("You dead");
        idHealthbar[1] = Texture.loadTexture("1hp");
        idHealthbar[2] = Texture.loadTexture("2hp");
        idHealthbar[3] = Texture.loadTexture("3hp");
        idHealthbar[4] = Texture.loadTexture("4hp");
        idHealthbar[5] = Texture.loadTexture("5hp");
        idHealthbar[6] = Texture.loadTexture("6hp");
    }

    private void loop() {
        int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;
        int g = 0, g2 = 0;

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            switch (level) {
                case "FirstLevel": {
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
                    glVertex2f(xSlime, ySlime);
                    glTexCoord2d(1, 0);
                    glVertex2f(xSlime + 30, ySlime);
                    glTexCoord2d(1, 1);
                    glVertex2f(xSlime + 30, ySlime + 30);
                    glTexCoord2d(0, 1);
                    glVertex2f(xSlime, ySlime + 30);
                    glEnd();
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
                    break;
                }
            }
            if (xPlayer > 630 && (yPlayer > 280 && yPlayer < 420) && level == "FirstLevel") { // Переход с первого уровня
                xPlayer = 10;
                level = "SecondLevel";
            }
            else if (xPlayer < 5 && (yPlayer > 280 && yPlayer < 420) && level == "SecondLevel") { // Переход со второго уровня
                xPlayer = 625;
                level = "FirstLevel";
            }

            if (xPlayer < 60 && level == "FirstLevel") xPlayer += 2; // Столкновение со стеной
            if (xPlayer > 410 && yPlayer < 270 && level == "FirstLevel") xPlayer -= 2;
            if (yPlayer < 135 && level == "FirstLevel") yPlayer += 2;
            if (yPlayer > 385 && level == "FirstLevel") yPlayer -= 2;

            switch (playerHealth) { // Отрисовка хелсбара в зависимости от единиц хп
                case 0: {
                    glBindTexture(GL_TEXTURE_2D, idHealthbar[0]);
                    dead = true;
                    break;
                }
                case 1: {
                    glBindTexture(GL_TEXTURE_2D, idHealthbar[1]); // Текстура хелсбара
                    break;
                }
                case 2: {
                    glBindTexture(GL_TEXTURE_2D, idHealthbar[2]);
                    break;
                }
                case 3: {
                    glBindTexture(GL_TEXTURE_2D, idHealthbar[3]);
                    break;
                }
                case 4: {
                    glBindTexture(GL_TEXTURE_2D, idHealthbar[4]);
                    break;
                }
                case 5: {
                    glBindTexture(GL_TEXTURE_2D, idHealthbar[5]);
                    break;
                }
                case 6: {
                    glBindTexture(GL_TEXTURE_2D, idHealthbar[6]);
                    break;
                }
            }
            glBegin(GL_QUADS);
            glTexCoord2d(0, 0);
            glVertex2f(0, 0);
            glTexCoord2d(1, 0);
            glVertex2f(100, 0);
            glTexCoord2d(1, 1);
            glVertex2f(100, 20);
            glTexCoord2d(0, 1);
            glVertex2f(0, 20);
            glEnd();

            playerBox.min[0] = xPlayer;
            playerBox.min[1] = yPlayer;
            playerBox.max[0] = xPlayer + 25;
            playerBox.max[1] = yPlayer + 50;
            slimeBox.min[0] = xSlime;
            slimeBox.min[1] = ySlime;
            slimeBox.max[0] = xSlime + 14;
            slimeBox.max[1] = ySlime + 14;

            if (AABB.AABBvsAABB(playerBox, slimeBox) && !dead) {
                playerHealth--;
                xPlayer -= 100;
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

                xPlayer += 2;
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

                xPlayer -= 2;
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
                if (g == 8){
                    i3++;
                    g = 0;
                }
                g++;

                yPlayer -= 2;
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
                yPlayer += 2;
            }
            glBegin(GL_QUADS);
            glTexCoord2d(0, 0);
            glVertex2f(xPlayer, yPlayer);
            glTexCoord2d(1, 0);
            glVertex2f(xPlayer + 42, yPlayer);
            glTexCoord2d(1, 1);
            glVertex2f(xPlayer + 42, yPlayer + 64);
            glTexCoord2d(0, 1);
            glVertex2f(xPlayer, yPlayer + 64);
            glEnd();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}