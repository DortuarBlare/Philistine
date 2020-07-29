import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window{
    private long window;
    private int x = 50, y = 50, x1 = 100, y1 = 100;
    int idBox, idPlayerStand, idPlayerRight, idPlayerLeft, idPlayerUp, idPlayerDown, idBackground;

    public void run(){
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

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(640, 480, "Window", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) ->{
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                glfwSetWindowShouldClose(window, true);
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
        glBlendFunc(GL_SRC_ALPHA,GL_ONE);   // Добавляет прозрачность
        glEnable(GL_BLEND);
        idBox = Texture.loadTexture("box");
        idBackground = Texture.loadTexture("background");
        idPlayerStand = Texture.loadTexture("player_stand");
        idPlayerRight = Texture.loadTexture("player_right");
        idPlayerLeft = Texture.loadTexture("player_left");
        idPlayerUp = Texture.loadTexture("player_up");
        idPlayerDown = Texture.loadTexture("player_down");
    }

    private void loop(){

        while (!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

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

            glBindTexture(GL_TEXTURE_2D, idPlayerStand);
            if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS){
                glBindTexture(GL_TEXTURE_2D, idPlayerRight);
                x1+=1;
            }
            if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS){
                glBindTexture(GL_TEXTURE_2D, idPlayerLeft);
                x1-=1;
            }
            if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS){
                glBindTexture(GL_TEXTURE_2D, idPlayerUp);
                y1-=1;
            }
            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS){
                glBindTexture(GL_TEXTURE_2D, idPlayerDown);
                y1+=1;
            }

            glBegin(GL_QUADS);  // Отрисовка квадрата, на который натягивается текстура
            glTexCoord2d(0, 0);
            glVertex2f(x1, y1);
            glTexCoord2d(1, 0);
            glVertex2f(x1 + 50, y1);
            glTexCoord2d(1, 1);
            glVertex2f(x1 + 50, y1 + 50);
            glTexCoord2d(0, 1);
            glVertex2f(x1, y1 + 50);
            glEnd();

            glBindTexture(GL_TEXTURE_2D, idBox);
            glBegin(GL_QUADS);
            glTexCoord2d(0, 0);
            glVertex2f(x, y);
            glTexCoord2d(1, 0);
            glVertex2f(x + 50, y);
            glTexCoord2d(1, 1);
            glVertex2f(x + 50, y + 50);
            glTexCoord2d(0, 1);
            glVertex2f(x, y + 50);
            glEnd();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

}
