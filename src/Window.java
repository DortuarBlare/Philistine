import content.AudioMaster;
import content.Source;
import content.Storage;
import content.Texture;
import math.AABB;
import math.CollisionMessage;
import mobs.*;
import objects.*;
import objects.Object;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import java.nio.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long window;
    SingletonMobs singletonMobs;
    SingletonPlayer singletonPlayer;
    private ArrayList<Object> firstLevelObjectList;
    private ArrayList<Object> secondLevelObjectList;
    private ArrayList<Object> thirdLevelObjectList;
    private ArrayList<Object> forthLevelObjectList;
    private ArrayList<Object> shopObjectList;
    private HashMap<String, Integer> textureMap;
    private HashMap<String, Integer> soundMap;
    private HashMap<String, AABB> aabbMap;
    private Source backgroundMusic, armorChange, coinSound, potionSound, doorSound, containerSound, tavernSound;
    private Coin coinGUI;
    private Shop shop;
    private Blacksmith blacksmith;
    private Waiter waiter;
    private String level = "MainMenu";
    private boolean canChangeLevel = false;
    private int deathsCounter = 0;
    boolean key_E_Pressed = false;
    boolean forMainMenu = true;
    private boolean firstLevelMobSpawning, secondLevelMobSpawning, fourthLevelMobSpawning = false;
    private boolean firstLevelMobSpawningStopped, secondLevelMobSpawningStopped, fourthLevelMobSpawningStopped = false;
    private int time = 0;
    private Timer mobTimer = new Timer();
    private TimerTask mobSpawnTask = new TimerTask() {
        @Override
        public void run() {
            time++;
            if (time == 5) {
                firstLevelMobSpawningStopped = true;
                stopMobSpawn();
            }
            SingletonMobs.mobList.add(new Slime(470, 288, 1, 50, 0, 5));
        }
    };

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
    }

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
        backgroundMusic = new Source(1);
        armorChange = new Source(0);
        coinSound = new Source(0);
        potionSound = new Source(0);
        tavernSound = new Source(0);
        doorSound = new Source(0);
        containerSound = new Source(0);
        coinGUI = new Coin("coin_01", true, false, 0, 0, 0, 0, new AABB());
        coinGUI.getTimer().schedule(coinGUI.getAnimationTask(), 0, 120);

        // Инициализация всех коллекций
        singletonPlayer = SingletonPlayer.getInstance();
        singletonMobs = SingletonMobs.getInstance();
        textureMap = new HashMap<String, Integer>();
        soundMap = new HashMap<String, Integer>();
        aabbMap = new HashMap<String, AABB>();
        firstLevelObjectList = new ArrayList<Object>();
        secondLevelObjectList = new ArrayList<Object>();
        thirdLevelObjectList = new ArrayList<>();
        forthLevelObjectList = new ArrayList<>();
        shopObjectList = new ArrayList<>();

        // Единичная загрузка всех текстур
        for (int i = 0, id = 0; i < Storage.textureString.length; i++)
            textureMap.put(Storage.textureString[i], id = Texture.loadTexture("textures/" + Storage.textureString[i]));

        // Единичная загрузка всех звуков
        for (int i = 0, id = 0; i < Storage.soundString.length; i++)
            soundMap.put(Storage.soundString[i], id = AudioMaster.loadSound("sounds/" + Storage.soundString[i]));

        // Единичная загрузка всех хитбоксов
        for (int i = 0; i < Storage.aabbString.length; i++)
            aabbMap.put(Storage.aabbString[i], new AABB());

        // Единичная инициализация переходов м/у уровнями
        aabbMap.get("entranceToFirstLevel").update(0, 190, 2, 286);
        aabbMap.get("entranceToSecondLevel").update(626, 237, 640, 335);
        aabbMap.get("entranceToThirdLevel").update(197, 104, 237, 107);
        aabbMap.get("entranceToForthLevel").update(247, 124, 283, 127);
        aabbMap.get("entranceFromThirdToSecondLevel").update(249, 122, 288, 124);
        aabbMap.get("entranceFromForthToSecondLevel").update(199, 108, 236, 112);
        aabbMap.get("entranceFromFourthToTownLevel").update(292, 356, 339, 359);
        aabbMap.get("entranceFromTavernToTown").update(224, 316, 255, 318);
        aabbMap.get("entranceFromForgeToTown").update(384, 316, 415, 318);
        aabbMap.get("toBuyBeer").update(224, 240, 255, 255);
        aabbMap.get("toBuyKey").update(359, 244, 373, 250);

        addAllObjects();
        shop = new Shop();
        waiter = new Waiter(168, 136, 1);
        blacksmith = new Blacksmith(176, 126, 1);
        SingletonMobs.mobList.add(SingletonPlayer.player);

        // Обработка единичного нажатий клавиш
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            // Нажатие Escape
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS && !level.equals("MainMenu")) {
                switch (SingletonPlayer.player.getMenuChoice()) {
                    case "Resume":
                    case "Options":
                    case "Exit": {
                        SingletonPlayer.player.setScrollMenu(!SingletonPlayer.player.isScrollMenu());
                        break;
                    }
                    case "Options_Sounds":
                    case "Options_Controls": {
                        SingletonPlayer.player.setMenuChoice("Options");
                        break;
                    }
                    case "Controls": {
                        SingletonPlayer.player.setMenuChoice("Options_Controls");
                        break;
                    }
                }
            }

            // Нажатие Е
            if (key == GLFW_KEY_E && action == GLFW_PRESS && !level.equals("MainMenu")) key_E_Pressed = true;

            // Нажатие Enter
            if (key == GLFW_KEY_ENTER && action == GLFW_PRESS) {
                // Выход из главного меню
                if (level.equals("MainMenu")) {
                    level = "Town";
                    backgroundMusic.stop(soundMap.get("mainMenuTheme"));
                    backgroundMusic.play(soundMap.get("townTheme"));
                    backgroundMusic.changeVolume(0.02f);
                }
                // Выбор во второстепенном меню
                else if (SingletonPlayer.player.isScrollMenu()) {
                    switch (SingletonPlayer.player.getMenuChoice()) {
                        case "Resume": {
                            SingletonPlayer.player.setScrollMenu(false);
                            break;
                        }
                        case "Options": {
                            SingletonPlayer.player.setMenuChoice("Options_Controls");
                            break;
                        }
                        case "Options_Controls": {
                            SingletonPlayer.player.setMenuChoice("Controls");
                            break;
                        }
                        case "Exit": {
                            AudioMaster.destroy();
                            glfwSetWindowShouldClose(window, true);
                            for (Mob mob : SingletonMobs.mobList) {
                                if (!mob.isDead()) {
                                    mob.getTimer().cancel();
                                    mob.getTimer().purge();
                                }
                            }
                            for (Object object : firstLevelObjectList) {
                                if (object instanceof Coin) {
                                    object.getTimer().cancel();
                                    object.getTimer().purge();
                                }
                            }
                            System.exit(0);
                            break;
                        }
                    }
                }
                // Выбор в диалоговом окне(Войти в данж?)
                else if (level.equals("Town") && SingletonPlayer.player.isDialogBubble()) {
                    SingletonPlayer.player.setX(SingletonPlayer.player.getX() - 1);
                    SingletonPlayer.player.setDialogBubble(false);
                    if (SingletonPlayer.player.isDialogBubbleChoice()) {
                        backgroundMusic.changeVolume(0.1f);
                        backgroundMusic.stop(soundMap.get("townTheme"));
                        backgroundMusic.play(soundMap.get("dungeonAmbient1"));
                        glTranslated(SingletonPlayer.player.getForPlacingCamera(), 0, 0);

                        // Установление хитбоксов стен первого уровня
                        for (int i = 0, j = 0; i < 7; i++, j += 4)
                            aabbMap.get("wall" + i).update(Storage.firstLevelWalls[j], Storage.firstLevelWalls[j + 1], Storage.firstLevelWalls[j + 2], Storage.firstLevelWalls[j + 3]);
                        SingletonPlayer.player.setX(199);
                        SingletonPlayer.player.setY(273);
                        SingletonPlayer.player.setSpeed(2);
                        SingletonPlayer.player.setMoveDirection("up");
                        level = "FirstLevel";
                    }
                }
            }

            // Нажатие стрелки влево
            if (key == GLFW_KEY_LEFT && action == GLFW_PRESS) {
                // Атака
                if (!SingletonPlayer.player.isAttack() && !SingletonPlayer.player.isKnockBackTaskStarted() &&
                        !level.equals("MainMenu") && !level.equals("Town") && !level.equals("tavern") && !level.equals("forge")) {
                    SingletonPlayer.player.setAttackLeft(true);
                    SingletonPlayer.player.setMoveDirection("left");
                }
                // Выбор в диалоговом окне(Войти в данж?)
                else if (level.equals("Town") && SingletonPlayer.player.isDialogBubble())
                    SingletonPlayer.player.setDialogBubbleChoice(!SingletonPlayer.player.isDialogBubbleChoice());
            }

            // Нажатие стрелки вправо
            if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS) {
                // Атака
                if (!SingletonPlayer.player.isAttack() && !SingletonPlayer.player.isKnockBackTaskStarted() &&
                        !level.equals("MainMenu") && !level.equals("Town") && !level.equals("tavern") && !level.equals("forge")) {
                    SingletonPlayer.player.setAttackRight(true);
                    SingletonPlayer.player.setMoveDirection("right");
                }
                // Выбор в диалоговом окне(Войти в данж?)
                else if (level.equals("Town") && SingletonPlayer.player.isDialogBubble())
                    SingletonPlayer.player.setDialogBubbleChoice(!SingletonPlayer.player.isDialogBubbleChoice());
            }

            // Нажатие стрелки вверх
            if (key == GLFW_KEY_UP && action == GLFW_PRESS) {
                // Атака
                if (!SingletonPlayer.player.isAttack() && !SingletonPlayer.player.isKnockBackTaskStarted() &&
                        !level.equals("MainMenu") && !level.equals("Town") && !level.equals("tavern") && !level.equals("forge") &&
                        !SingletonPlayer.player.isScrollMenu()) {
                    SingletonPlayer.player.setAttackUp(true);
                    SingletonPlayer.player.setMoveDirection("up");
                }
                // Выбор во второстепенном меню
                else if (SingletonPlayer.player.isScrollMenu())
                    switch (SingletonPlayer.player.getMenuChoice()) {
                        case "Resume": {
                            SingletonPlayer.player.setMenuChoice("Exit");
                            break;
                        }
                        case "Options": {
                            SingletonPlayer.player.setMenuChoice("Resume");
                            break;
                        }
                        case "Exit": {
                            SingletonPlayer.player.setMenuChoice("Options");
                            break;
                        }
                        case "Options_Controls": {
                            SingletonPlayer.player.setMenuChoice("Options_Sounds");
                            break;
                        }
                        case "Options_Sounds": {
                            SingletonPlayer.player.setMenuChoice("Options_Controls");
                            break;
                        }
                    }
            }

            // Нажатие стрелки вниз
            if (key == GLFW_KEY_DOWN && action == GLFW_PRESS) {
                // Атака
                if (!SingletonPlayer.player.isAttack() && !SingletonPlayer.player.isKnockBackTaskStarted() &&
                        !level.equals("MainMenu") && !level.equals("Town") && !level.equals("tavern") && !level.equals("forge") &&
                        !SingletonPlayer.player.isScrollMenu()) {
                    SingletonPlayer.player.setAttackDown(true);
                    SingletonPlayer.player.setMoveDirection("down");
                }
                // Выбор во второстепенном меню
                else if (SingletonPlayer.player.isScrollMenu())
                    switch (SingletonPlayer.player.getMenuChoice()) {
                        case "Resume": {
                            SingletonPlayer.player.setMenuChoice("Options");
                            break;
                        }
                        case "Options": {
                            SingletonPlayer.player.setMenuChoice("Exit");
                            break;
                        }
                        case "Exit": {
                            SingletonPlayer.player.setMenuChoice("Resume");
                            break;
                        }
                        case "Options_Controls": {
                            SingletonPlayer.player.setMenuChoice("Options_Sounds");
                            break;
                        }
                        case "Options_Sounds": {
                            SingletonPlayer.player.setMenuChoice("Options_Controls");
                            break;
                        }
                    }
            }
        });
    }

    private synchronized void loop() {
        int guard_i = 1, guard_g = 0, forgeFurnace_i = 1, forgeFurnace_g = 0;
        backgroundMusic.play(soundMap.get("mainMenuTheme"));

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

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
                        else if (!SingletonPlayer.player.isDialogBubbleChoice())
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
                case "tavern": {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("tavern")); // Фон таверны
                    createQuadTexture(0, 0, 640, 360);

                    // Отрисовка оффицианта и остановка около прилавка
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("waiter_" + waiter.getMoveDirection() + "_move_0" + waiter.getAnimationTime()));
                    createQuadTexture(waiter.getX(), waiter.getY(), waiter.getX() + 64, waiter.getY() + 64);
                    waiter.simulateBehavior();
                    if (waiter.isWaitingTaskStarted()) {
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("emotion_health"));
                        createQuadTexture(waiter.getX() + 31, waiter.getY(), waiter.getX() + 51, waiter.getY() + 20);

                        // Восстановить здоровье
                        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("toBuyBeer"))) {
                            glBindTexture(GL_TEXTURE_2D, textureMap.get("emotion_coin"));
                            createQuadTexture(SingletonPlayer.player.getX() + 32, SingletonPlayer.player.getY(),
                                    SingletonPlayer.player.getX() + 52, SingletonPlayer.player.getY() + 20);
                            if (key_E_Pressed && SingletonPlayer.player.getHealth() < 100 && SingletonPlayer.player.getMoney() >= 10) {
                                SingletonPlayer.player.setHealth(100);
                                SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() - 10);
                                tavernSound.play(soundMap.get("drinkBeer"));
                            }
                        }
                    }

                    // Торговец ключами
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("gentleman_brown"));
                    createQuadTexture(350, 190, 350 + 64, 190 + 64);
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("toBuyKey"))) {
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("emotion_key"));
                        createQuadTexture(381, 182, 381 + 20, 182 + 20);
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("emotion_coin"));
                        createQuadTexture(SingletonPlayer.player.getX() + 32, SingletonPlayer.player.getY(),
                                SingletonPlayer.player.getX() + 52, SingletonPlayer.player.getY() + 20);
                        if (key_E_Pressed && SingletonPlayer.player.getMoney() >= 20) {
                            SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() - 20);
                            SingletonPlayer.player.setKeys(SingletonPlayer.player.getKeys() + 1);
                            tavernSound.play(soundMap.get("boughtKey"));
                        }
                    }
                    key_E_Pressed = false;

                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall3")) || AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall5")))
                        SingletonPlayer.player.stopRight();
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall1")) || AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall6")))
                        SingletonPlayer.player.stopLeft();
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall2")))
                        SingletonPlayer.player.stopUp();
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall4")))
                        SingletonPlayer.player.stopDown();

                    // Проверка выхода в город
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceFromTavernToTown"))) {
                        level = "Town";

                        // Обновление хитбоксов стен для города
                        for (int i = 0, j = 0; i < 13; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.townLevelWalls[j], Storage.townLevelWalls[j + 1],
                                    Storage.townLevelWalls[j + 2], Storage.townLevelWalls[j + 3]);
                        }
                        doorSound.play(soundMap.get("doorOpen"));
                        SingletonPlayer.player.getStepSound().stop(SingletonPlayer.player.getPlayerSounds().get("stepWood"));
                        backgroundMusic.stop(soundMap.get("tavernTheme"));
                        backgroundMusic.play(soundMap.get("townTheme"));
                        glTranslated(-SingletonPlayer.player.getForPlacingCamera(), 0, 0);
                        SingletonPlayer.player.setX(293);
                        SingletonPlayer.player.setY(192);
                        SingletonPlayer.player.setMoveDirection("right");
                    }
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
                                aabbMap.get("wall" + i).update(Storage.secondLevelWalls[j], Storage.secondLevelWalls[j + 1],
                                        Storage.secondLevelWalls[j + 2], Storage.secondLevelWalls[j + 3]);
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
                        if (object.getTexture().equals("gate3") && canChangeLevel && secondLevelMobSpawningStopped) {
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
                                aabbMap.get("wall" + i).update(Storage.firstLevelWalls[j], Storage.firstLevelWalls[j + 1],
                                        Storage.firstLevelWalls[j + 2], Storage.firstLevelWalls[j + 3]);
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
                                aabbMap.get("wall" + i).update(Storage.thirdLevelWalls[j], Storage.thirdLevelWalls[j + 1],
                                        Storage.thirdLevelWalls[j + 2], Storage.thirdLevelWalls[j + 3]);
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
                                aabbMap.get("wall" + i).update(Storage.fourthLevelWalls[j], Storage.fourthLevelWalls[j + 1],
                                        Storage.fourthLevelWalls[j + 2], Storage.fourthLevelWalls[j + 3]);
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
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall1")) && AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall6")))
                        SingletonPlayer.player.stopRight();
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall3")) && AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall5")))
                        SingletonPlayer.player.stopLeft();
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall0")) && AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("wall4")))
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
                            aabbMap.get("wall" + i).update(Storage.secondLevelWalls[j], Storage.secondLevelWalls[j + 1],
                                    Storage.secondLevelWalls[j + 2], Storage.secondLevelWalls[j + 3]);
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
                                aabbMap.get("wall" + i).update(Storage.secondLevelWalls[j], Storage.secondLevelWalls[j + 1],
                                        Storage.secondLevelWalls[j + 2], Storage.secondLevelWalls[j + 3]);
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

            // Отрисовка интерфейса
            if (!level.equals("MainMenu") && !level.equals("Town")) {
                // Полоска здоровья
                int tempHealth = SingletonPlayer.player.getHealth() % 10 == 0 ? SingletonPlayer.player.getHealth() : SingletonPlayer.player.getHealth() - (SingletonPlayer.player.getHealth() % 10) + 10;
                if (tempHealth >= 0) glBindTexture(GL_TEXTURE_2D, textureMap.get(tempHealth + "hp"));
                else if (SingletonPlayer.player.getHealth() <= 0)
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("0hp"));
                createQuadTexture(0, 0, 103, 18);

                // Щит с броней
                int tempArmor = SingletonPlayer.player.getArmor() % 5 == 0 ? SingletonPlayer.player.getArmor() : SingletonPlayer.player.getArmor() - (SingletonPlayer.player.getArmor() % 5);
                glBindTexture(GL_TEXTURE_2D, textureMap.get(tempArmor + "armor"));
                createQuadTexture(0, 20, 60, 40);

                // Количество монет
                glBindTexture(GL_TEXTURE_2D, textureMap.get("coin_0" + coinGUI.getAnimationTime()));
                createQuadTexture(0, 42, 11, 54);
                int tempCoin = SingletonPlayer.player.getMoney();
                int tempX0 = 13 + (getCountsOfDigits(SingletonPlayer.player.getMoney()) * 7) - 7, tempX1 = 13 + (getCountsOfDigits(SingletonPlayer.player.getMoney()) * 7), tempY0 = 44, tempY1 = 54;
                for (int i = 0; i < getCountsOfDigits(SingletonPlayer.player.getMoney()); i++) {
                    switch (tempCoin % 10) {
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
                    tempCoin = tempCoin / 10;
                    tempX0 -= 7;
                    tempX1 -= 7;
                }

                // Количество ключей
                glBindTexture(GL_TEXTURE_2D, textureMap.get("keyGold"));
                createQuadTexture(0, 55, 16, 71);
                int tempKeys = SingletonPlayer.player.getKeys();
                tempX0 = 16 + (getCountsOfDigits(SingletonPlayer.player.getKeys()) * 7) - 7;
                tempX1 = 16 + (getCountsOfDigits(SingletonPlayer.player.getKeys()) * 7);
                tempY0 = 60;
                tempY1 = 70;
                for (int i = 0; i < getCountsOfDigits(SingletonPlayer.player.getKeys()); i++) {
                    switch (tempKeys % 10) {
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
                    tempKeys = tempKeys / 10;
                    tempX0 -= 7;
                    tempX1 -= 7;
                }
            }

            // Отрисовка экипировки и анимации
            SingletonPlayer.player.update(window, level);
            glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getBodyAnimation()));
            createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            if (!SingletonPlayer.player.getFeetTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getFeetAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getLegsTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getLegsAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getTorsoTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getTorsoAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getOverTorsoTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getOverTorsoAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getShouldersTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getShouldersAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getBeltTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getBeltAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getHeadTexture().equals("nothing")) {
//                System.out.println(SingletonPlayer.player.getHeadAnimation());
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getHeadAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getHandsTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getHandsAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (SingletonPlayer.player.isAttack() && !SingletonPlayer.player.isDead() && !level.equals("MainMenu")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getWeaponAnimation()));
                createQuadTexture(SingletonPlayer.player.getX() - SingletonPlayer.player.getWeapon().getMinX(), SingletonPlayer.player.getY() - SingletonPlayer.player.getWeapon().getMinY(),
                        SingletonPlayer.player.getX() + SingletonPlayer.player.getWeapon().getMaxX(), SingletonPlayer.player.getY() + SingletonPlayer.player.getWeapon().getMaxY());
            }

            // Отрисовка второстепенного меню
            if (!level.equals("MainMenu") && SingletonPlayer.player.isScrollMenu()) {
                if (level.equals("Town")) {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("scrollMenu_" + SingletonPlayer.player.getMenuChoice())); // Фон второстепенного меню
                    createQuadTexture(SingletonPlayer.player.getForPlacingCamera(), 0, SingletonPlayer.player.getForPlacingCamera() + 640, 360);
                } else {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("scrollMenu_" + SingletonPlayer.player.getMenuChoice())); // Фон второстепенного меню
                    createQuadTexture(0, 0, 640, 360);
                }
            }

            glfwPollEvents();
            glfwSwapBuffers(window);
        }
    }

    private void addAllObjects() {
        // Добавление объектов на первый уровень
        firstLevelObjectList.add(new Furniture("barrelOpened", 118, 135));
        firstLevelObjectList.add(new Furniture("bagMedium", 137, 134));
        firstLevelObjectList.add(new Furniture("boxOpened", 308, 129));
        firstLevelObjectList.add(new Furniture("chair1", 399, 172));
        firstLevelObjectList.add(new Furniture("chair3", 431, 226));
        firstLevelObjectList.add(new Furniture("table1", 425, 162));
        firstLevelObjectList.add(new Furniture("littleBag", 426, 160));
        firstLevelObjectList.add(new Furniture("bookRed", 434, 186));
        firstLevelObjectList.add(new Furniture("bones", 113, 173));
        firstLevelObjectList.add(new Furniture("trash", 462, 173));
        firstLevelObjectList.add(new Gate("verticalGate", 628, 147));
        firstLevelObjectList.add(new Gate("verticalGate", 628, 243));

        // Добавление объектов на второй уровень
        secondLevelObjectList.add(new Furniture("gate3", 198, 54));
        secondLevelObjectList.add(new Furniture("gate3", 246, 54));

        // Добавление объектов на третий уровень
        thirdLevelObjectList.add(new Chest("chest",279, 215));
        thirdLevelObjectList.add(new Box("box", 369, 127));
        thirdLevelObjectList.add(new Box("box", 451, 153));
        thirdLevelObjectList.add(new Box("box", 414, 246));
        thirdLevelObjectList.add(new Barrel("barrel", 150, 236));
        thirdLevelObjectList.add(new Barrel("barrel", 205, 260));
        thirdLevelObjectList.add(new Barrel("barrel", 110, 278));
        thirdLevelObjectList.add(new Furniture("bagBig", 171, 131));
        thirdLevelObjectList.add(new Furniture("bagMedium", 199, 132));
        thirdLevelObjectList.add(new Furniture("bagSmall", 113, 210));
        thirdLevelObjectList.add(new Furniture("altar0", 460, 164));
        thirdLevelObjectList.add(new Furniture("altar1", 129, 170));

        // Добавление объектов на четвертый уровень
        forthLevelObjectList.add(new Furniture("gate2", 292, 336));
        forthLevelObjectList.add(new Furniture("gate3", 198, 54));
        forthLevelObjectList.add(new Furniture("trash", 118, 288));
        forthLevelObjectList.add(new Furniture("bones", 466, 163));
        forthLevelObjectList.add(new Furniture("water", 466, 163));
    }

    private void createQuadTexture(int xMin, int yMin, int xMax, int yMax) {
        glBegin(GL_QUADS);
        glTexCoord2d(0, 0);
        glVertex2f(xMin, yMin);
        glTexCoord2d(1, 0);
        glVertex2f(xMax, yMin);
        glTexCoord2d(1, 1);
        glVertex2f(xMax, yMax);
        glTexCoord2d(0, 1);
        glVertex2f(xMin, yMax);
        glEnd();
    }

    public static int getCountsOfDigits(long number) {
        return (number == 0) ? 1 : (int) Math.ceil(Math.log10(Math.abs(number) + 0.5));
    }
}