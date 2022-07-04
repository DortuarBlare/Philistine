import content.AudioMaster;
import content.Storage;
import content.Texture;
import gui.HUD;
import levels.MainMenuLevel;
import levels.TownLevel;
import managers.LevelManager;
import managers.SoundManager;
import managers.UserInputManager;
import physics.AABB;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long window;
    private UserInputManager userInputManager;
    private LevelManager levelManager;
    private SoundManager soundManager;
    SingletonMobs singletonMobs;
    SingletonPlayer singletonPlayer;

    /*
    private void stopMobSpawn() {
        time = 0;
        mobTimer.cancel();
        mobTimer.purge();
        mobTimer = new Timer();
        mobSpawnTask = new TimerTask() {
            @Override
            public void run() {
                time++;
                if (level.equals("FirstLevel")) {
                    SingletonMobs.mobList.add(new Slime(470, 288, 1, 50, 0, 5));
                    if (time == 5) {
                        firstLevelMobSpawningStopped = true;
                        stopMobSpawn();
                    }
                }
                if (level.equals("SecondLevel")) {
                    SingletonMobs.mobList.add(new Spider(477, 284, 1, 60, 0, 10));
                    if (time == 3) {
                        secondLevelMobSpawningStopped = true;
                        stopMobSpawn();
                    }
                }
                else if (level.equals("ForthLevel")) {
                    SingletonMobs.mobList.add(new Imp(477, 284, 1, 100, 0, 50));
                    if (time == 1) {
                        fourthLevelMobSpawningStopped = true;
                        stopMobSpawn();
                    }
                }
            }
        };
    }*/

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Работа с экраном
        window = glfwCreateWindow(2560, 1080, "Philistine", glfwGetPrimaryMonitor(), NULL);

        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

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

        // Работа с OpenGL
        GL.createCapabilities(); // создает instance для OpenGL в текущем потоке
        glMatrixMode(GL_PROJECTION); // Выставление камеры
        glLoadIdentity(); // По видимости ненужная строка(что-то с единичной матрицей)
        glOrtho(0, 640, 360, 0, 1, -1); // Камера на место окна
        glMatrixMode(GL_MODELVIEW); // Установка матрицы в состояние ModelView
        glEnable(GL_TEXTURE_2D); // Включить 2D текстуры
        glEnable(GL_BLEND); // Включить смешивание цветов
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // Добавляет прозрачность
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // Инициализация звуковых источников
        AudioMaster.init();
        AudioMaster.setListenerData();

        // Инициализация менеджеров
        userInputManager = new UserInputManager();
        userInputManager.init(window);
        levelManager = new LevelManager();
        soundManager = new SoundManager();

        // Инициализация всех коллекций
        singletonPlayer = SingletonPlayer.getInstance();
        singletonMobs = SingletonMobs.getInstance();
        SingletonMobs.mobList.add(SingletonPlayer.player);
    }

    private synchronized void loop() {
        SoundManager.musicSource.play(Storage.soundMap.get("mainMenuTheme"));

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (Storage.loaded) {
                // Обновление и отрисовка уровней
                LevelManager.update();

                // Отрисовка интерфейса
                if (!(LevelManager.currentLevel instanceof MainMenuLevel) && !(LevelManager.currentLevel instanceof TownLevel)) {
                    HUD.update();
                    HUD.draw();
                }

                // Обновление и отрисовка игрока
                SingletonPlayer.player.update(window);
                SingletonPlayer.player.draw();

                // Отрисовка второстепенного меню
                if (!(LevelManager.currentLevel instanceof MainMenuLevel) && SingletonPlayer.player.isScrollMenu()) {
                    if (LevelManager.currentLevel instanceof TownLevel)
                        Texture.draw(
                                Storage.textureMap.get("scrollMenu_" + SingletonPlayer.player.getMenuChoice()),
                                new AABB(
                                        SingletonPlayer.player.getForPlacingCamera(),
                                        0,
                                        SingletonPlayer.player.getForPlacingCamera() + 640,
                                        360
                                )
                        );
                    else
                        Texture.draw(
                                Storage.textureMap.get("scrollMenu_" + SingletonPlayer.player.getMenuChoice()),
                                new AABB(0, 0, 640, 360)
                        );
                }

                // Сброс единичных нажатий клавиш
                UserInputManager.update();
            }

            glfwPollEvents();
            glfwSwapBuffers(window);
        }
    }
}