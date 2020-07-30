import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window implements Runnable{
    private Thread thread;
    boolean running = false;
    private long window;
    private int xPlayer = 100, yPlayer = 100, xSlime = 50, ySlime = 50;
    int idBox, idPlayerStand, idBackground, idBackground2;
    int idPlayerLeft, idPlayerLeft2, idPlayerLeft3;
    int idPlayerRight, idPlayerRight2, idPlayerRight3;
    int idPlayerUp, idPlayerUp2, idPlayerUp3;
    int idPlayerDown, idPlayerDown2, idPlayerDown3;
    int idSlime, idSlime2;
    String level = "Village";

    public void start() {
        thread = new Thread(this, "Game");
        running = true;
        thread.start();
    }

    public void run() {
        while(running) {
            System.out.println("Start");

            init();
            loop();

            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);

            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    private void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(640, 480, "Window", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) ->{
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
                running = false;
            }
        });

        try (MemoryStack stack = stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();
        glMatrixMode(GL_PROJECTION);    // Выставление камеры
        glLoadIdentity();
        glOrtho(0, 640, 480, 0, 1, -1);    // Камера на место окна
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);   // Добавляет прозрачность
        glEnable(GL_BLEND);
        idBox = Texture.loadTexture("box");
        idBackground = Texture.loadTexture("background");
        idBackground2 = Texture.loadTexture("background2");
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
    }

    private void loop() {
        int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;
        int g = 0, g2 = 0;
        boolean mov = false;
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            switch (level) {
                case "Village": {
                    glBindTexture(GL_TEXTURE_2D, idBackground);
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

                    glBindTexture(GL_TEXTURE_2D, idSlime);
                    switch (i5){
                        case 0:
                            glBindTexture(GL_TEXTURE_2D, idSlime);
                            break;
                        case 1:
                            glBindTexture(GL_TEXTURE_2D, idSlime2);
                            break;
                    }
                    if (g2 == 8) {
                        if (i5 == 0){
                            i5++;
                        }
                        else {
                            i5--;
                        }
                        g2 = 0;
                    }
                    g2++;
                    xSlime += Math.random() * 2;
                    ySlime += Math.random() * 2;

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
                case "Castle": {
                    glBindTexture(GL_TEXTURE_2D, idBackground2);
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
            if (xPlayer > 630 && (yPlayer > 280 && yPlayer < 420)) {
                xPlayer = 10;
                level = "Castle";
            }
            else if (xPlayer < 5 && (yPlayer > 280 && yPlayer < 420)) {
                xPlayer = 625;
                level = "Village";
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
            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS){
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
            glBegin(GL_QUADS);  // Отрисовка квадрата, на который натягивается текстура
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
