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
//    private ArrayList<Mob> mobList;
    SingletonMobs singletonMobs;
    EnemyThread enemyThread;
    SingletonPlayer singletonPlayer;
    private ArrayList<Object> firstLevelObjectList;
    private ArrayList<Object> secondLevelObjectList;
    private HashMap<String, Integer> textureMap;
    private HashMap<String, Integer> soundMap;
    private HashMap<String, AABB> aabbMap;
    Source backgroundMusic, mobHurtSound, armorChange, coinSound, doorSound;
    Coin coinGUI;
    Blacksmith blacksmith;
    private String level = "MainMenu";
    private boolean forScale = true;
    boolean key_E_Pressed = false;
    boolean forMainMenu = true;
    boolean forMainTheme = true;
    private int time = 0;
    private boolean mobSpawnStarted = false;
    private Timer mobTimer = new Timer();
    private TimerTask mobSpawnTask = new TimerTask() {
        @Override
        public void run() {
            if (level.equals("FirstLevel")) SingletonMobs.mobList.add(new Slime(350, 300, 1, 50, 0, 5));
            else if (level.equals("SecondLevel")) SingletonMobs.mobList.add(new Spider(477, 284, 1, 60, 0, 10));
            time++;
            if (time == 2) stopMobSpawn();
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
                if (level.equals("FirstLevel")) SingletonMobs.mobList.add(new Slime(350, 300, 1, 50, 0, 5));
                else if (level.equals("SecondLevel")) SingletonMobs.mobList.add(new Spider(477, 284, 1, 60, 0, 10));
                time++;
                if (time == 2) stopMobSpawn();
            }
        };
    }

    public void run() throws InterruptedException {
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

        // Инициализация всех коллекций
        singletonMobs = SingletonMobs.getInstance();
        textureMap = new HashMap<String, Integer>();
        soundMap = new HashMap<String, Integer>();
        aabbMap = new HashMap<String, AABB>();
        firstLevelObjectList = new ArrayList<Object>();
        secondLevelObjectList = new ArrayList<Object>();

        AudioMaster.init();
        AudioMaster.setListenerData();
        backgroundMusic = new Source(1);
        mobHurtSound = new Source(0);
        armorChange = new Source(0);
        coinSound = new Source(0);
        doorSound = new Source(0);
        coinGUI = new Coin("coin_01", true, false, 0, 0, 0, 0, new AABB());
        coinGUI.getTimer().schedule(coinGUI.getAnimationTask(), 0, 120);

        // Единичная загрузка всех текстур
        for (int i = 0, id = 0; i < Storage.textureString.length; i++)
            textureMap.put(Storage.textureString[i], id = Texture.loadTexture("textures/" + Storage.textureString[i]));

        // Единичная загрузка всех звуков
        for (int i = 0, id = 0; i < Storage.soundString.length; i++)
            soundMap.put(Storage.soundString[i], id = AudioMaster.loadSound("sounds/" + Storage.soundString[i]));

        // Единичная загрузка всех хитбоксов
        for(int i = 0; i < Storage.aabbString.length; i++)
            aabbMap.put(Storage.aabbString[i], new AABB());

        // Единичное установление хитбоксов стен первого уровня и переходов м/у уровнями
        for(int i = 0, j = 0; i < 5; i++, j+=4)
            aabbMap.get("wall" + i).update(Storage.firstLevelWalls[j], Storage.firstLevelWalls[j + 1], Storage.firstLevelWalls[j + 2], Storage.firstLevelWalls[j + 3]);
        aabbMap.get("entranceToFirstLevel").update(0, 190, 2, 286);
        aabbMap.get("entranceToSecondLevel").update(638, 238, 640, 335);
        aabbMap.get("entranceToThirdLevel").update(197, 134, 237, 145);
        aabbMap.get("entranceToForthLevel").update(247, 134, 283, 144);
        aabbMap.get("entranceFromThirdToSecondLevel").update(249, 134, 288, 146);
        aabbMap.get("entranceFromForthToSecondLevel").update(199, 132, 236, 141);
        aabbMap.get("entranceFromTavernToTown").update(224,316,255,318);
        aabbMap.get("entranceFromForgeToTown").update(384,316,415,318);

        // Добавление всех объектов и мобов
        firstLevelObjectList.add(new Container("chestClosed", false, true,250, 200, 282, 232, new AABB(250, 200, 282, 232)));
        firstLevelObjectList.add(new Weapon("rapier", "slash", 10, true, true, 150, 150, 342, 342, new AABB(231, 231, 259, 259)));
        firstLevelObjectList.add(new Armor("chain_helmet", "head", 4, true, true, 300, 150, 364, 214));
        singletonPlayer = SingletonPlayer.getInstance();
        SingletonMobs.mobList.add(SingletonPlayer.player);
        blacksmith = new Blacksmith(176, 126, 1);
        enemyThread = new EnemyThread("Enemy");

        // Клашива ESC на выход(закрытие приложения)
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS && !level.equals("MainMenu")) {
                SingletonPlayer.player.setScrollMenu(!SingletonPlayer.player.isScrollMenu());
            }
            if (key == GLFW_KEY_ENTER && action == GLFW_PRESS && level.equals("MainMenu")) {
                level = "Town";
                backgroundMusic.stop(soundMap.get("mainMenuTheme"));
            }
            else if (key == GLFW_KEY_ENTER && action == GLFW_PRESS && level.equals("Town") && SingletonPlayer.player.isChoiceBubble()) {
                SingletonPlayer.player.setX(SingletonPlayer.player.getX() - 1);
                SingletonPlayer.player.setChoiceBubble(false);
                if (SingletonPlayer.player.isYes()) {
                    level = "FirstLevel";
                    glTranslated(SingletonPlayer.player.getForPlacingCamera(), 0, 0);
                    SingletonPlayer.player.setX(150);
                    SingletonPlayer.player.setY(150);
                    SingletonPlayer.player.setSpeed(2);
                }
            }
            else if (key == GLFW_KEY_ENTER && action == GLFW_PRESS && SingletonPlayer.player.isScrollMenu()) {
                switch (SingletonPlayer.player.getMenuChoice()) {
                    case "Resume": {
                        SingletonPlayer.player.setScrollMenu(false);
                        break;
                    }
                    case "Options": {
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
            // Атака
            if (key == GLFW_KEY_LEFT && action == GLFW_PRESS && !level.equals("MainMenu") && !level.equals("Town") &&
                    !level.equals("tavern") && !level.equals("forge")) {
                if (!SingletonPlayer.player.isAttackRight() && !SingletonPlayer.player.isAttackUp() && !SingletonPlayer.player.isAttackDown()) {
                    SingletonPlayer.player.setAttackLeft(true);
                    SingletonPlayer.player.setMoveDirection("left");
                }
            }

            // Выбор в диалоговом окне
            else if (key == GLFW_KEY_LEFT && action == GLFW_PRESS && level.equals("Town")) {
                if (SingletonPlayer.player.isChoiceBubble()) SingletonPlayer.player.setYes(!SingletonPlayer.player.isYes());
            }

            // Атака
            if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS && !level.equals("MainMenu") && !level.equals("Town") &&
                    !level.equals("tavern") && !level.equals("forge")) {
                if (!SingletonPlayer.player.isAttackLeft() && !SingletonPlayer.player.isAttackUp() &&
                        !SingletonPlayer.player.isAttackDown()) {
                    SingletonPlayer.player.setAttackRight(true);
                    SingletonPlayer.player.setMoveDirection("right");
                }
            }

            // Выбор в диалоговом окне
            else if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS && level.equals("Town")) {
                if (SingletonPlayer.player.isChoiceBubble()) SingletonPlayer.player.setYes(!SingletonPlayer.player.isYes());
            }

            if (key == GLFW_KEY_UP && action == GLFW_PRESS && SingletonPlayer.player.isScrollMenu()) {
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
                }
            }
            // Атака
            else if (key == GLFW_KEY_UP && action == GLFW_PRESS && !level.equals("MainMenu") && !level.equals("Town") &&
                    !level.equals("tavern") && !level.equals("forge")) {
                if (!SingletonPlayer.player.isAttackLeft() && !SingletonPlayer.player.isAttackRight() &&
                        !SingletonPlayer.player.isAttackDown()) {
                    SingletonPlayer.player.setAttackUp(true);
                    SingletonPlayer.player.setMoveDirection("up");
                }
            }


            if (key == GLFW_KEY_DOWN && action == GLFW_PRESS && SingletonPlayer.player.isScrollMenu()) {
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
                }
            }
            // Атака
            else if (key == GLFW_KEY_DOWN && action == GLFW_PRESS && !level.equals("MainMenu") && !level.equals("Town") &&
                    !level.equals("tavern") && !level.equals("forge")) {
                if (!SingletonPlayer.player.isAttackLeft() && !SingletonPlayer.player.isAttackRight() &&
                        !SingletonPlayer.player.isAttackUp()) {
                    SingletonPlayer.player.setAttackDown(true);
                    SingletonPlayer.player.setMoveDirection("down");
                }
            }

            if (key == GLFW_KEY_E && action == GLFW_PRESS && !level.equals("MainMenu")) key_E_Pressed = true;
        });
    }

    private void loop() throws InterruptedException {
        int torch_i = 1, torch_g = 0, guard_i = 1, guard_g = 0, forgeFurnace_i = 1, forgeFurnace_g = 0;

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Переключение уровня
            switch (level) {
                case "MainMenu": {
                    if (forMainTheme) {
                        backgroundMusic.play(soundMap.get("mainMenuTheme"));
                        forMainTheme = false;
                    }
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

                    glBindTexture(GL_TEXTURE_2D, textureMap.get("Press_enter"));
                    createQuadTexture(SingletonPlayer.player.getX() - 18, SingletonPlayer.player.getY() - 100, SingletonPlayer.player.getX() + 82, SingletonPlayer.player.getY() - 72);
                    if (SingletonPlayer.player.getX() == 1186) forMainMenu = false;
                    else if (SingletonPlayer.player.getX() == 290) forMainMenu = true;
                    if (forMainMenu) {
                        glTranslated(-1, 0, 0);
                        SingletonPlayer.player.setForPlacingCamera(SingletonPlayer.player.getForPlacingCamera() + 1);
                        SingletonPlayer.player.updateForMainMenu("right");
                    }
                    else {
                        glTranslated(1, 0, 0);
                        SingletonPlayer.player.setForPlacingCamera(SingletonPlayer.player.getForPlacingCamera() - 1);
                        SingletonPlayer.player.updateForMainMenu("left");
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
                        SingletonPlayer.player.setChoiceBubble(true);
                        if (SingletonPlayer.player.isYes()) glBindTexture(GL_TEXTURE_2D, textureMap.get("enterTheDungeon_Yes"));
                        else if (!SingletonPlayer.player.isYes()) glBindTexture(GL_TEXTURE_2D, textureMap.get("enterTheDungeon_No"));
                        createQuadTexture(SingletonPlayer.player.getX() - 20, SingletonPlayer.player.getY() - 55, SingletonPlayer.player.getX() + 40, SingletonPlayer.player.getY());
                    }

                    if (key_E_Pressed) {
                        // Вхождение в таверну
                        if (SingletonPlayer.player.getX() + 32 > 305 && SingletonPlayer.player.getX() + 32 < 341) {
                            level = "tavern";
                            glTranslated(SingletonPlayer.player.getForPlacingCamera(), 0, 0);
                            SingletonPlayer.player.setForPlacingCamera(3);

                            // Обновление хитбоксов стен для tavern
                            for (int i = 0, j = 0; i < 7; i++, j+=4) {
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
                            for (int i = 0, j = 0; i < 7; i++, j+=4) {
                                aabbMap.get("wall" + i).update(Storage.forgeLevelWalls[j], Storage.forgeLevelWalls[j + 1],
                                        Storage.forgeLevelWalls[j + 2], Storage.forgeLevelWalls[j + 3]);
                            }
                            doorSound.play(soundMap.get("doorOpen"));
                            SingletonPlayer.player.setX(368);
                            SingletonPlayer.player.setY(254);
                            SingletonPlayer.player.setMoveDirection("up");
                        }
                    }
                    key_E_Pressed = false;

                    SingletonPlayer.player.updateForTown(window);
                    break;
                }
                case "tavern": {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("tavern")); // Фон таверны
                    createQuadTexture(0, 0, 640, 360);

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
                        for (int i = 0, j = 0; i < 7; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.townLevelWalls[j], Storage.townLevelWalls[j + 1],
                                    Storage.townLevelWalls[j + 2], Storage.townLevelWalls[j + 3]);
                        }
                        doorSound.play(soundMap.get("doorOpen"));
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

                    if (forgeFurnace_i == 4) forgeFurnace_i = 1;
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("furnace_burn_0" + forgeFurnace_i)); // Анимация печки

                    createQuadTexture(223, 142, 259, 173);
                    if (forgeFurnace_g == 12) {
                        forgeFurnace_i++;
                        forgeFurnace_g = 0;
                    }
                    forgeFurnace_g++;

                    glBindTexture(GL_TEXTURE_2D, textureMap.get("blacksmith_" + blacksmith.getMoveDirection() + "_move_0" + blacksmith.getAnimationTime())); // Кузнец
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

                    // Проверка выхода в город
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceFromForgeToTown"))) {
                        level = "Town";
                        // Обновление хитбоксов стен для города
                        for (int i = 0, j = 0; i < 7; i++, j += 4) {
                            aabbMap.get("wall" + i).update(Storage.townLevelWalls[j], Storage.townLevelWalls[j + 1],
                                    Storage.townLevelWalls[j + 2], Storage.townLevelWalls[j + 3]);
                        }
                        doorSound.play(soundMap.get("doorOpen"));
                        glTranslated(-SingletonPlayer.player.getForPlacingCamera(), 0, 0);
                        SingletonPlayer.player.setX(605);
                        SingletonPlayer.player.setY(192);
                        SingletonPlayer.player.setMoveDirection("right");
                    }
                    break;
                }
                case "FirstLevel": {
                    if (!forMainTheme) {
                        backgroundMusic.changeVolume(0.1f);
                        backgroundMusic.play(soundMap.get("dungeonAmbient1"));
                        forMainTheme = true;
                    }
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("level0")); // Фон первого уровня
                    createQuadTexture(0, 0, 640, 360);
                    if (torch_i == 10) torch_i = 1;
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("torch_0" + torch_i));
                    createQuadTexture(190, 63, 221, 126);
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("torch_0" + torch_i));
                    createQuadTexture(384, 63, 415, 126);
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("torch_0" + torch_i));
                    createQuadTexture(556, 158, 587, 221);
                    if (torch_g == 8) {
                        torch_i++;
                        torch_g = 0;
                    }
                    torch_g++;

                    if (!mobSpawnStarted) {
                        mobSpawnStarted = true;
                        mobTimer.schedule(mobSpawnTask, 0, 5000);
                    }

                    // Отрисовка всех объектов
                    for (Object object : firstLevelObjectList) {
                        if (!object.isLying()) continue;
                        if (object instanceof Coin) {
                            Coin coin = (Coin) object;
                            if (!coin.isAnimationTaskStarted())
                                coin.getTimer().schedule(coin.getAnimationTask(), 0, 120);
                            coin.setTexture("coin_0" + coin.getAnimationTime());
                            glBindTexture(GL_TEXTURE_2D, textureMap.get(coin.getTexture()));
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
                            }
                            else if (firstLevelObjectList.get(i) instanceof Weapon) {
                                Weapon changingWeapon = (Weapon) firstLevelObjectList.get(i);
                                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), changingWeapon.getCollisionBox())) {
                                    Weapon playerWeapon = SingletonPlayer.player.getWeapon();
                                    changingWeapon.setIsLying(false);
                                    changingWeapon.resize();
                                    SingletonPlayer.player.setWeapon(changingWeapon);
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
                            }
                            else if (firstLevelObjectList.get(i) instanceof Container && SingletonPlayer.player.getKeys() > 0) {
                                Container change = (Container) firstLevelObjectList.get(i);
                                if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), change.getCollisionBox())) {
                                    change.setTexture("chestOpened");
                                    SingletonPlayer.player.setKeys(SingletonPlayer.player.getKeys() - 1);
                                    for (int h = 0; h < change.loot.size(); h++) {
                                        firstLevelObjectList.add(change.loot.get(h));
                                        change.loot.remove(h);
                                        h--;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    key_E_Pressed = false;

                    // Все операции с мобами
                    for (int i = 1; i < SingletonMobs.mobList.size(); i++) {
                        if (!SingletonMobs.mobList.get(i).isDead()) {
                            if (SingletonMobs.mobList.get(i) instanceof Slime) {
                                Slime slime = (Slime) SingletonMobs.mobList.get(i);
                                if (!slime.isAnimationTaskStarted()) slime.getTimer().schedule(slime.getAnimationTask(), 0, 300);
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("slime_" + slime.getMoveDirection() + "_0" + slime.getAnimationTime()));
                                createQuadTexture(slime.getX(), slime.getY(), slime.getX() + 18, slime.getY() + 12);
                                slime.getHitbox().update(slime.getX() + 3, slime.getY() + 2, slime.getX() + 14, slime.getY() + 10);
                                slime.getCollisionBox().update(slime.getX() + 1, slime.getY() + 1, slime.getX() + 16, slime.getY() + 11);

                                // Преследование игрока слаймом
//                                if (!AABB.AABBvsAABB(SingletonPlayer.player.getHitbox(), slime.getHitbox())) slime.follow(SingletonPlayer.player);
                                // Игрок получает урона от слизня
                                if (AABB.AABBvsAABB(SingletonPlayer.player.getHitbox(), slime.getHitbox()) && !SingletonPlayer.player.isDead() && !SingletonPlayer.player.isImmortal()) {
                                    if (SingletonPlayer.player.getX() > slime.getX() && SingletonPlayer.player.getY() > slime.getY()) SingletonPlayer.player.setKnockbackDirection("right");
                                    else if (SingletonPlayer.player.getX() < slime.getX()) SingletonPlayer.player.setKnockbackDirection("left");
                                    else if (SingletonPlayer.player.getY() > slime.getY()) SingletonPlayer.player.setKnockbackDirection("down");
                                    else if (SingletonPlayer.player.getY() < slime.getY()) SingletonPlayer.player.setKnockbackDirection("up");
                                    SingletonPlayer.player.takeDamage(slime.getDamage());
                                    if (!SingletonPlayer.player.isKnockbackTaskStarted()) SingletonPlayer.player.getTimer().schedule(SingletonPlayer.player.getKnockbackTask(), 0, 10);
                                }
                                // Слизень получает урон от игрока
                                if (AABB.AABBvsAABB(SingletonPlayer.player.getAttackBox(), slime.getHitbox()) && !slime.isImmortal()) {
                                    if (SingletonPlayer.player.isAttackLeft()) slime.setKnockbackDirection("left");
                                    else if (SingletonPlayer.player.isAttackRight()) slime.setKnockbackDirection("right");
                                    else if (SingletonPlayer.player.isAttackUp()) slime.setKnockbackDirection("up");
                                    else if (SingletonPlayer.player.isAttackDown()) slime.setKnockbackDirection("down");
                                    slime.setHealth(slime.getHealth() - SingletonPlayer.player.getDamage());
                                    if (!slime.isKnockbackTaskStarted()) slime.getTimer().schedule(slime.getKnockbackTask(), 0, 10);
                                    mobHurtSound.play(soundMap.get("slimeHurt"));
                                }

                                // Отрисовка хелсбара
                                if (slime.getHealth() <= 0) {
                                    slime.setDead(true);
//                                    slime.getTimer().cancel();
//                                    slime.getTimer().purge();
                                    SingletonMobs.mobList.remove(slime);
                                }
                                else {
                                    int tempHealth = slime.getHealth() % 10 == 0 ? slime.getHealth() * 2 : (slime.getHealth() * 2) - ((slime.getHealth() * 2) % 10) + 10;
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("enemyHp" + tempHealth));
                                    createQuadTexture(slime.getX(), slime.getY() - 2, slime.getX() + 16, slime.getY());
                                }
                            }
                        }
                    }

                    // Проверка всех мобов на столкновение со стенами, объектами и другими мобами. Объекты с объектами
                    for (int i1 = 0; i1 < SingletonMobs.mobList.size(); i1++) {
                        Mob mob = SingletonMobs.mobList.get(i1);

                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")))
                            mob.stopRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.stopLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")))
                            mob.stopUp();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")))
                            mob.stopDown();

                        // Столкновение мобов с мобами
                        for (int j1 = i1 + 1; j1 < SingletonMobs.mobList.size(); j1++) {
                            Mob mob2 = SingletonMobs.mobList.get(j1);
                            if (!(mob instanceof Player)) {
                                if (AABB.AABBvsAABB2(mob.getCollisionBox(), mob2.getCollisionBox()))
                                    mob.stop(CollisionMessage.getMessage());
                            }
                        }

                        for (int i = 0; i < firstLevelObjectList.size(); i++) {
                            Object object = firstLevelObjectList.get(i);
                            if (!(object instanceof Furniture) && !(object instanceof Container)) {
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
                                for (int j = i + 1; j < firstLevelObjectList.size(); j++) {
                                    Object object2 = firstLevelObjectList.get(j);
                                    if (!(object2 instanceof Furniture) && !(object2 instanceof Container)) {
                                        if (AABB.AABBvsAABB(object.getCollisionBox(), object2.getCollisionBox()))
                                            object.moveLeft();
                                    }
                                }
                            }

                            if (AABB.AABBvsAABB(mob.getCollisionBox(), object.getCollisionBox())) {
                                if ((mob instanceof Player) && (object instanceof Coin)) {
                                    object.getTimer().cancel();
                                    object.getTimer().purge();
                                    firstLevelObjectList.remove(object);
                                    SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() + 10);
                                    coinSound.play(soundMap.get("pickedCoin"));
                                    break;
                                }
                            }

                            if (AABB.AABBvsAABB(mob.getCollisionBox(), object.getCollisionBox())) {
                                if ((mob instanceof Player) && (object instanceof Potion)) {
                                    object.getTimer().cancel();
                                    object.getTimer().purge();
                                    firstLevelObjectList.remove(object);
                                    if (SingletonPlayer.player.getHealth() > 90)
                                        SingletonPlayer.player.setHealth(100);
                                    else
                                        SingletonPlayer.player.setHealth(SingletonPlayer.player.getHealth() + 10);
                                    break;
                                }
                            }

                            if (object.isNoclip()) continue;
                            if (AABB.AABBvsAABB2(mob.getCollisionBox(), object.getCollisionBox()))
                                mob.stop(CollisionMessage.getMessage());
                        }
                    }

                    // Проверка перехода на второй уровень
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceToSecondLevel"))) {
                        level = "SecondLevel";
                        mobSpawnStarted = false;
                        // Обновление хитбоксов стен для второго уровня
                        for (int i = 0, j = 0; i < 7; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.secondLevelWalls[j], Storage.secondLevelWalls[j + 1],
                                    Storage.secondLevelWalls[j + 2], Storage.secondLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(2 - 14);
                        SingletonPlayer.player.setY(225);
                    }
                    break;
                }
                case "SecondLevel": {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("level1")); // Фон второго уровня
                    createQuadTexture(0, 0, 640, 360);

                    if (!mobSpawnStarted) {
                        mobSpawnStarted = true;
                        mobTimer.schedule(mobSpawnTask, 3000, 10000);
                    }

                    // Все операции с мобами
                    for (int i = 1; i < SingletonMobs.mobList.size(); i++) {
                        if (!SingletonMobs.mobList.get(i).isDead()) {
                            if (SingletonMobs.mobList.get(i) instanceof Spider) {
                                Spider spider = (Spider) SingletonMobs.mobList.get(i);
                                if (!spider.isAnimationTaskStarted()) spider.getTimer().schedule(spider.getAnimationTask(), 0, 150);
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("spider_" + spider.getMoveDirection() + "_move_0" + spider.getAnimationTime()));
                                createQuadTexture(spider.getX(), spider.getY(), spider.getX() + 64, spider.getY() + 64);
                                spider.getHitbox().update(spider.getX() + 10, spider.getY() + 15, spider.getX() + 51, spider.getY() + 49);
                                spider.getCollisionBox().update(spider.getX() + 10, spider.getY() + 15, spider.getX() + 51, spider.getY() + 49);

                                // Преследование игрока пауком
//                                if (!AABB.AABBvsAABB(SingletonPlayer.player.getHitbox(), spider.getHitbox())) spider.follow(SingletonPlayer.player);
                                // Игрок получает урона от паука
                                if (AABB.AABBvsAABB(SingletonPlayer.player.getHitbox(), spider.getHitbox()) && !SingletonPlayer.player.isDead() && !SingletonPlayer.player.isImmortal()) {
                                    if (SingletonPlayer.player.getX() > spider.getX() && SingletonPlayer.player.getY() > spider.getY()) SingletonPlayer.player.setKnockbackDirection("right");
                                    else if (SingletonPlayer.player.getX() < spider.getX()) SingletonPlayer.player.setKnockbackDirection("left");
                                    else if (SingletonPlayer.player.getY() > spider.getY()) SingletonPlayer.player.setKnockbackDirection("down");
                                    else if (SingletonPlayer.player.getY() < spider.getY()) SingletonPlayer.player.setKnockbackDirection("up");
                                    SingletonPlayer.player.takeDamage(spider.getDamage());
                                    SingletonPlayer.player.getTimer().schedule(SingletonPlayer.player.getKnockbackTask(), 0, 10);
                                }
                                // Паук получает урон от игрока
                                if (AABB.AABBvsAABB(SingletonPlayer.player.getAttackBox(), spider.getHitbox()) && !spider.isImmortal()) {
                                    if (SingletonPlayer.player.isAttackLeft()) spider.setKnockbackDirection("left");
                                    else if (SingletonPlayer.player.isAttackRight()) spider.setKnockbackDirection("right");
                                    else if (SingletonPlayer.player.isAttackUp()) spider.setKnockbackDirection("up");
                                    else if (SingletonPlayer.player.isAttackDown()) spider.setKnockbackDirection("down");
                                    spider.setHealth(spider.getHealth() - SingletonPlayer.player.getDamage());
                                    if (!spider.isKnockbackTaskStarted()) spider.getTimer().schedule(spider.getKnockbackTask(), 0, 10);
                                }

                                // Отрисовка хелсбара
                                if (spider.getHealth() <= 0) {
                                    spider.setDead(true);
                                    /*spider.getTimer().cancel();
                                    spider.getTimer().purge();*/
                                    SingletonMobs.mobList.remove(spider);
                                }
                                /*else {
                                    int tempHealth = spider.getHealth() % 10 == 0 ? spider.getHealth() * 2 : (spider.getHealth() * 2) - ((spider.getHealth() * 2) % 10) + 10;
                                    glBindTexture(GL_TEXTURE_2D, textureMap.get("enemyHp" + tempHealth));
                                    createQuadTexture(spider.getX(), spider.getY(), spider.getX() + 64, spider.getY() + 2);
                                }*/
                            }
                        }
                    }

                    // Проверка всех мобов на столкновение со стенами
                    for (Mob mob : SingletonMobs.mobList) {
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
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceToFirstLevel"))) {
                        level = "FirstLevel";
                        // Обновление хитбоксов стен для первого уровня
                        for(int i = 0, j = 0; i < 5; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.firstLevelWalls[j], Storage.firstLevelWalls[j + 1],
                                    Storage.firstLevelWalls[j + 2], Storage.firstLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(638 - 64 + 15);
                        SingletonPlayer.player.setY(281);
                    }

                    // Проверка перехода на 3 уровень
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceToThirdLevel"))) {
                        level = "ThirdLevel";
                        // Обновление хитбоксов стен для 3 уровня
                        for (int i = 0, j = 0; i < 7; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.thirdLevelWalls[j], Storage.thirdLevelWalls[j + 1],
                                    Storage.thirdLevelWalls[j + 2], Storage.thirdLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(240);
                        SingletonPlayer.player.setY(120);
                        SingletonPlayer.player.setMoveDirection("down");
                    }

                    // Проверка перехода на 4 уровень
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceToForthLevel"))) {
                        level = "ForthLevel";
                        // Обновление хитбоксов стен для 4 уровня
                        for (int i = 0, j = 0; i < 7; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.fourthLevelWalls[j], Storage.fourthLevelWalls[j + 1],
                                    Storage.fourthLevelWalls[j + 2], Storage.fourthLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(180);
                        SingletonPlayer.player.setY(120);
                        SingletonPlayer.player.setMoveDirection("down");
                    }
                    break;
                }
                case "ThirdLevel":{
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("level2"));
                    createQuadTexture(0, 0, 640, 360);

                    for (Mob mob : SingletonMobs.mobList) {
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")))
                            mob.stopRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.stopLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall5")))
                            mob.stopUp();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall6")))
                            mob.stopDown();
                    }

                    // Проверка перехода на второй уровень
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceFromThirdToSecondLevel"))) {
                        level = "SecondLevel";
                        // Обновление хитбоксов стен для второго уровня
                        for (int i = 0, j = 0; i < 7; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.secondLevelWalls[j], Storage.secondLevelWalls[j + 1],
                                    Storage.secondLevelWalls[j + 2], Storage.secondLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(180);
                        SingletonPlayer.player.setY(120);
                        SingletonPlayer.player.setMoveDirection("down");
                    }
                    break;
                }
                case "ForthLevel":{
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("level3"));
                    createQuadTexture(0, 0, 640, 360);

                    for (Mob mob : SingletonMobs.mobList) {
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")))
                            mob.stopRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.stopLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall5")))
                            mob.stopUp();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall6")))
                            mob.stopDown();
                    }

                    // Проверка перехода на второй уровень
                    if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), aabbMap.get("entranceFromForthToSecondLevel"))) {
                        level = "SecondLevel";
                        // Обновление хитбоксов стен для второго уровня
                        for (int i = 0, j = 0; i < 7; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.secondLevelWalls[j], Storage.secondLevelWalls[j + 1],
                                    Storage.secondLevelWalls[j + 2], Storage.secondLevelWalls[j + 3]);
                        }
                        SingletonPlayer.player.setX(240);
                        SingletonPlayer.player.setY(120);
                        SingletonPlayer.player.setMoveDirection("down");
                    }
                    break;
                }
            }

            // Метод для обработки игрока и отрисовка интерфейса
            if (!level.equals("MainMenu") && !level.equals("Town")) {
                SingletonPlayer.player.update(window);

                // Полоска здоровья
                int tempHealth = SingletonPlayer.player.getHealth() % 10 == 0 ? SingletonPlayer.player.getHealth() : SingletonPlayer.player.getHealth() - (SingletonPlayer.player.getHealth() % 10) + 10;
                if (tempHealth >= 0) glBindTexture(GL_TEXTURE_2D, textureMap.get(tempHealth + "hp"));
                if (SingletonPlayer.player.getHealth() <= 0) {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("0hp"));
                    SingletonPlayer.player.setDead(true);
                }
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
                createQuadTexture(0, 55, 16, 61);
                int tempKeys = SingletonPlayer.player.getKeys();
                tempX0 = 16 + (getCountsOfDigits(SingletonPlayer.player.getKeys()) * 7) - 7;
                tempX1 = 16 + (getCountsOfDigits(SingletonPlayer.player.getKeys()) * 7);
                tempY0 = 55;
                tempY1 = 65;
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
            if (!SingletonPlayer.player.getShouldersTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getShouldersAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getBeltTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getBeltAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getHeadTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getHeadAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if (!SingletonPlayer.player.getHandsTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getHandsAnimation()));
                createQuadTexture(SingletonPlayer.player.getX(), SingletonPlayer.player.getY(), SingletonPlayer.player.getX() + 64, SingletonPlayer.player.getY() + 64);
            }
            if ((SingletonPlayer.player.isAttackLeft() || SingletonPlayer.player.isAttackRight() || SingletonPlayer.player.isAttackUp() ||
                    SingletonPlayer.player.isAttackDown()) && !SingletonPlayer.player.isDead() && !level.equals("MainMenu")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(SingletonPlayer.player.getWeaponAnimation()));
                createQuadTexture(SingletonPlayer.player.getX() - SingletonPlayer.player.getWeapon().getMinX(), SingletonPlayer.player.getY() - SingletonPlayer.player.getWeapon().getMinY(),
                        SingletonPlayer.player.getX() + SingletonPlayer.player.getWeapon().getMaxX(), SingletonPlayer.player.getY() + SingletonPlayer.player.getWeapon().getMaxY());
            }

            if (!level.equals("MainMenu") && SingletonPlayer.player.isScrollMenu()) {
                if (level.equals("Town")) {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("scrollMenu_" + SingletonPlayer.player.getMenuChoice())); // Фон второстепенного меню
                    createQuadTexture(SingletonPlayer.player.getForPlacingCamera(), 0, SingletonPlayer.player.getForPlacingCamera() + 640, 360);
                }
                else {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("scrollMenu_" + SingletonPlayer.player.getMenuChoice())); // Фон второстепенного меню
                    createQuadTexture(0, 0, 640, 360);
                }
            }

            if (SingletonPlayer.player.isScrollMenu()) enemyThread.setThreadWaiting(true);
            if (!SingletonPlayer.player.isScrollMenu() && enemyThread.getState() == Thread.State.WAITING) enemyThread.resumeThread();
            glfwPollEvents();
            glfwSwapBuffers(window);
        }
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

    public static int getCountsOfDigits(long number) {
        return (number == 0) ? 1 : (int) Math.ceil(Math.log10(Math.abs(number) + 0.5));
    }
}