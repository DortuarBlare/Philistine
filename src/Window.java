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

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long window;
    private ArrayList<Mob> mobList;
    private ArrayList<Object> objectList;
    private HashMap<String, Integer> textureMap;
    private HashMap<String, Integer> soundMap;
    private HashMap<String, AABB> aabbMap;
    private String level = "MainMenu";
    private boolean forScale = true;
    boolean key_E_Pressed = false;
    boolean forMainMenu = true;
    boolean forMainTheme = true;
    int forPlacingCamera = 0;
    Source backgroundMusic, mobHurtSound, armorChange, coinSound;
    Player player;

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

        // Инициализация всех коллекций
        mobList = new ArrayList<>();
        textureMap = new HashMap<String, Integer>();
        soundMap = new HashMap<String, Integer>();
        aabbMap = new HashMap<String, AABB>();
        objectList = new ArrayList<Object>();

        AudioMaster.init();
        AudioMaster.setListenerData();
        backgroundMusic = new Source(1);
        mobHurtSound = new Source(0);
        armorChange = new Source(0);
        coinSound = new Source(0);

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

        // Добавление всех объектов и мобов
        objectList.add(new Container("chestClosed", false, true,250, 200, 282, 232, new AABB(250, 200, 282, 232)));
        objectList.add(new Armor("shirt_white", "torso", 1, true, true, 300, 100, 364, 164));
        objectList.add(new Armor("pants_greenish", "legs", 1, true, true, 428, 100, 492, 164));
        objectList.add(new Armor("shoes_brown", "feet", 1, true, true, 364, 100, 428, 164));
        objectList.add(new Weapon("rapier", "slash", 10, true, true, 150, 150, 342, 342, new AABB(231, 231, 259, 259)));

        mobList.add(player = new Player(290, 192, 1, 100, 0, 10));
        mobList.add(new Slime(350, 300, 1, 50, 0, 5));
        mobList.add(new Slime(330, 300, 1, 50, 0, 5));
        mobList.add(new Slime(310, 300, 1, 50, 0, 5));
        mobList.add(new Slime(290, 300, 1, 50, 0, 5));
        mobList.add(new Slime(270, 300, 1, 50, 0, 5));
        mobList.add(new Slime(250, 300, 1, 50, 0, 5));
        mobList.add(new Slime(230, 300, 1, 50, 0, 5));
        mobList.add(new Slime(210, 300, 1, 50, 0, 5));
        mobList.add(new Slime(190, 300, 1, 50, 0, 5));

        // Клашива ESC на выход(закрытие приложения)
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                AudioMaster.destroy();
                glfwSetWindowShouldClose(window, true);
                for (Mob mob : mobList) {
                    if (!mob.isDead()) {
                        mob.getTimer().cancel();
                        mob.getTimer().purge();
                    }
                }
                for (Object object : objectList) {
                    if (object instanceof Coin) {
                        object.getTimer().cancel();
                        object.getTimer().purge();
                    }
                }
            }
            if (level.equals("MainMenu") && key == GLFW_KEY_ENTER && action == GLFW_PRESS) {
                level = "FirstLevel";
                glTranslated(forPlacingCamera, 0, 0);
                player.setX(150);
                player.setY(150);
                player.setSpeed(2);
            }
            if (key == GLFW_KEY_LEFT && action == GLFW_PRESS && !level.equals("MainMenu")) player.setAttackLeft(true);
            if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS && !level.equals("MainMenu")) player.setAttackRight(true);
            if (key == GLFW_KEY_UP && action == GLFW_PRESS && !level.equals("MainMenu")) player.setAttackUp(true);
            if (key == GLFW_KEY_DOWN && action == GLFW_PRESS && !level.equals("MainMenu")) player.setAttackDown(true);
            if (key == GLFW_KEY_E && action == GLFW_PRESS && !level.equals("MainMenu")) key_E_Pressed = true;
        });
    }

    private void loop() {
        int torch_i = 1, torch_g = 0;

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
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("Press_enter"));
                    createQuadTexture(player.getX() - 18, player.getY() - 100, player.getX() + 82, player.getY() - 72);
                    if (player.getX() == 1186) forMainMenu = false;
                    else if (player.getX() == 290) forMainMenu = true;
                    if (forMainMenu) {
                        glTranslated(-1, 0, 0);
                        forPlacingCamera++;
                        player.updateForMainMenu("right");
                    }
                    else {
                        glTranslated(1, 0, 0);
                        forPlacingCamera--;
                        player.updateForMainMenu("left");
                    }
                    break;
                }
                case "FirstLevel": {
                    if (!forMainTheme) {
                        backgroundMusic.stop(soundMap.get("mainMenuTheme"));
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

                    // Отрисовка всех объектов
                    for (Object object : objectList) {
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

                    // Подбор всех возможных предметов
                    if (key_E_Pressed) {
                        for (int i = 0; i < objectList.size(); i++) {
                            if (objectList.get(i) instanceof Armor) {
                                Armor changingArmor = (Armor) objectList.get(i);
                                if (AABB.AABBvsAABB(player.getCollisionBox(), changingArmor.getCollisionBox())) {
                                    Armor playerArmor = player.getArmorType(changingArmor);
                                    changingArmor.setIsLying(false);
                                    player.setArmor(changingArmor);
                                    armorChange.play(soundMap.get("changingArmor"));
                                    changingArmor = playerArmor;
                                    changingArmor.setMinX(player.getCollisionBox().getMin().x - 20);
                                    changingArmor.setMinY(player.getCollisionBox().getMin().y - 30);
                                    changingArmor.setMaxX(player.getCollisionBox().getMin().x + 44);
                                    changingArmor.setMaxY(player.getCollisionBox().getMin().y + 34);
                                    changingArmor.setIsLying(true);
                                    changingArmor.correctCollisionBox();
                                    objectList.set(i, changingArmor);
                                    break;
                                }
                            }
                            else if (objectList.get(i) instanceof Weapon) {
                                Weapon changingWeapon = (Weapon) objectList.get(i);
                                if (AABB.AABBvsAABB(player.getCollisionBox(), changingWeapon.getCollisionBox())) {
                                    Weapon playerWeapon = player.getWeapon();
                                    changingWeapon.setIsLying(false);
                                    changingWeapon.resize();
                                    player.setWeapon(changingWeapon);
                                    armorChange.play(soundMap.get("changingArmor"));
                                    changingWeapon = playerWeapon;
                                    changingWeapon.setMinX(player.getCollisionBox().getMin().x - 64);
                                    changingWeapon.setMinY(player.getCollisionBox().getMin().y - 64);
                                    changingWeapon.setMaxX(player.getCollisionBox().getMin().x + 128);
                                    changingWeapon.setMaxY(player.getCollisionBox().getMin().y + 128);
                                    changingWeapon.setIsLying(true);
                                    changingWeapon.getCollisionBox().update(changingWeapon.getMinX() + 81, changingWeapon.getMinY() + 81,
                                            changingWeapon.getMinX() + 109, changingWeapon.getMinY() + 109);
                                    objectList.set(i, changingWeapon);
                                    break;
                                }
                            }
                            else if (objectList.get(i) instanceof Container) {
                                Container change = (Container) objectList.get(i);
                                if (AABB.AABBvsAABB(player.getCollisionBox(), change.getCollisionBox())) {
                                    change.setTexture("chestOpened");
                                    for (int h = 0; h < change.loot.size(); h++) {
                                        objectList.add(change.loot.get(h));
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
                    for (int i = 1; i < mobList.size(); i++) {
                        if (!mobList.get(i).isDead()) {
                            if (mobList.get(i) instanceof Slime) {
                                Slime slime = (Slime) mobList.get(i);
                                if (!slime.isAnimationTaskStarted()) slime.getTimer().schedule(slime.getAnimationTask(), 0, 120);
                                glBindTexture(GL_TEXTURE_2D, textureMap.get("slime_" + slime.getMoveDirection() + "_0" + slime.getAnimationTime()));
                                createQuadTexture(slime.getX(), slime.getY(), slime.getX() + 18, slime.getY() + 12);
                                slime.getHitbox().update(slime.getX() + 3, slime.getY() + 2, slime.getX() + 14, slime.getY() + 10);
                                slime.getCollisionBox().update(slime.getX() + 1, slime.getY() + 1, slime.getX() + 16, slime.getY() + 11);

                                // Преследование игрока слаймом
                                if (!AABB.AABBvsAABB(player.getHitbox(), slime.getHitbox()) && (int)(Math.random() * 6) == 5) {
                                    if (slime.getHitbox().getMin().x > player.getHitbox().getMin().x) slime.moveLeft();
                                    else slime.moveRight();
                                    if (slime.getHitbox().getMin().y > player.getHitbox().getMin().y) slime.moveUp();
                                    else slime.moveDown();
                                }
                                // Игрок получает урона от слизня
                                if (AABB.AABBvsAABB(player.getHitbox(), slime.getHitbox()) && !player.isImmortal()) {
                                    if (player.getX() > slime.getX() && player.getY() > slime.getY()) player.setKnockbackDirection("right");
                                    else if (player.getX() < slime.getX()) player.setKnockbackDirection("left");
                                    else if (player.getY() > slime.getY()) player.setKnockbackDirection("down");
                                    else if (player.getY() < slime.getY()) player.setKnockbackDirection("up");
                                    player.takeDamage(slime.getDamage());
                                    player.setImmortal(true);
                                    player.getTimer().schedule(player.getKnockbackTask(), 0, 10);
                                }
                                // Слизень получает урон от игрока
                                if (AABB.AABBvsAABB(player.getAttackBox(), slime.getHitbox()) && !slime.isImmortal()) {
                                    if (player.isAttackLeft()) slime.setKnockbackDirection("left");
                                    else if (player.isAttackRight()) slime.setKnockbackDirection("right");
                                    else if (player.isAttackUp()) slime.setKnockbackDirection("up");
                                    else if (player.isAttackDown()) slime.setKnockbackDirection("down");
                                    slime.setHealth(slime.getHealth() - player.getDamage());
                                    slime.setImmortal(true);
                                    slime.getTimer().schedule(slime.getKnockbackTask(), 0, 10);
                                    mobHurtSound.play(soundMap.get("slimeHurt"));
                                }
                                if (player.getTime() >= 15) {
                                    player.stopTimer();
                                    player.setImmortal(false);
                                }
                                if (slime.getKnockbackTime() >= 25) {
                                    slime.stopTimer();
                                    slime.setImmortal(false);
                                }

                                // Отрисовка хелсбара
                                if (slime.getHealth() <= 0) {
                                    slime.setDead(true);
                                    slime.getTimer().cancel();
                                    slime.getTimer().purge();
                                    mobList.remove(slime);
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
                    for (int i1 = 0; i1 < mobList.size(); i1++) {
                        Mob mob = mobList.get(i1);

                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall1")))
                            mob.stopRight();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall4")))
                            mob.stopLeft();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall0")) || AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall2")))
                            mob.stopUp();
                        if (AABB.AABBvsAABB(mob.getCollisionBox(), aabbMap.get("wall3")))
                            mob.stopDown();

                        // Столкновение мобов с мобами
                        for (int j1 = i1 + 1; j1 < mobList.size(); j1++) {
                            Mob mob2 = mobList.get(j1);
                            if (!(mob instanceof Player)) {
                                if (AABB.AABBvsAABB2(mob.getCollisionBox(), mob2.getCollisionBox()))
                                    mob.stop(CollisionMessage.getMessage());
                            }
                        }

                        for (int i = 0; i < objectList.size(); i++) {
                            Object object = objectList.get(i);
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
                                for (int j = i + 1; j < objectList.size(); j++) {
                                    Object object2 = objectList.get(j);
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
                                    objectList.remove(object);
                                    player.setMoney(player.getMoney() + 10);
                                    coinSound.play(soundMap.get("pickedCoin"));
                                    break;
                                }
                            }
                            if (object.isNoclip()) continue;
                            if (AABB.AABBvsAABB2(mob.getCollisionBox(), object.getCollisionBox()))
                                mob.stop(CollisionMessage.getMessage());
                        }
                    }

                    // Проверка перехода на второй уровень
                    if (AABB.AABBvsAABB(player.getCollisionBox(), aabbMap.get("entranceToSecondLevel"))) {
                        level = "SecondLevel";
                        // Обновление хитбоксов стен для второго уровня
                        for (int i = 0, j = 0; i < 7; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.secondLevelWalls[j], Storage.secondLevelWalls[j + 1],
                                    Storage.secondLevelWalls[j + 2], Storage.secondLevelWalls[j + 3]);
                        }
                        player.setX(2 - 14);
                        player.setY(225);
                    }
                    break;
                }
                case "SecondLevel": {
//                    Skeleton skeleton = (Skeleton) mobList.get(2);
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("level1")); // Фон второго уровня
                    createQuadTexture(0, 0, 640, 360);

                    // Все операции со скелетоном
                    /*if (!skeleton.isDead()) {
                        if (forSkeletonAnimation) {
                            skeleton.getTimer().schedule(skeleton.getAnimationTask(), 0, 120);
                            forSkeletonAnimation = false;
                        }
                        glBindTexture(GL_TEXTURE_2D, textureMap.get("skeleton_" + skeleton.getMoveDirection() + "_move_0" + skeleton.getAnimationTime()));
                        skeleton.getHitbox().update(skeleton.getX() + 18, skeleton.getY() + 15, skeleton.getX() + 45, skeleton.getY() + 61);
                        skeleton.getCollisionBox().update(skeleton.getX() + 16, skeleton.getY() + 44, skeleton.getX() + 47, skeleton.getY() + 62);
                        createQuadTexture(skeleton.getX(), skeleton.getY(), skeleton.getX() + 64, skeleton.getY() + 64);

                        // Преследование игрока скелетоном
                        if (!AABB.AABBvsAABB(player.getHitbox(), skeleton.getHitbox())) {
                            if (player.getHitbox().getMin().y < skeleton.getHitbox().getMin().y &&
                                    player.getHitbox().getMin().x < skeleton.getHitbox().getMin().x) skeleton.moveUpLeft();
                            else if (player.getHitbox().getMin().y < skeleton.getHitbox().getMin().y &&
                                    player.getHitbox().getMin().x > skeleton.getHitbox().getMin().x) skeleton.moveUpRight();
                            else if (player.getHitbox().getMin().y > skeleton.getHitbox().getMin().y &&
                                    player.getHitbox().getMin().x < skeleton.getHitbox().getMin().x) skeleton.moveDownLeft();
                            else if (player.getHitbox().getMin().y > skeleton.getHitbox().getMin().y &&
                                    player.getHitbox().getMin().x > skeleton.getHitbox().getMin().x) skeleton.moveDownRight();
                            else if (player.getHitbox().getMin().x < skeleton.getHitbox().getMin().x) skeleton.moveLeft();
                            else if (player.getHitbox().getMin().x > skeleton.getHitbox().getMin().x) skeleton.moveRight();
                            else if (player.getHitbox().getMin().y < skeleton.getHitbox().getMin().y) skeleton.moveUp();
                            else if (player.getHitbox().getMin().y > skeleton.getHitbox().getMin().y) skeleton.moveDown();
                        }

                        // Получение урона от скелетона
                        if (AABB.AABBvsAABB(player.getHitbox(), skeleton.getHitbox()) && !player.isDead() && !player.isImmortal()) {
                            if (player.getX() > skeleton.getX()) player.setKnockbackDirection("Right");
                            else if (player.getX() < skeleton.getX()) player.setKnockbackDirection("Left");
                            else if (player.getY() > skeleton.getY()) player.setKnockbackDirection("Down");
                            else if (player.getY() < skeleton.getY()) player.setKnockbackDirection("Up");
                            player.takeDamage(skeleton.getDamage());
                            player.setImmortal(true);
                            player.getTimer().schedule(player.getTimerTaskPlayer(), 0, 10);
                        }
                        // Скелетон получает урон от игрока
                        if (AABB.AABBvsAABB(player.getAttackBox(), skeleton.getHitbox()) && !skeleton.isImmortal()) {
                            if (player.getX() > skeleton.getX()) skeleton.setDirection("Left");
                            else if (player.getX() < skeleton.getX()) skeleton.setDirection("Right");
                            else if (player.getY() > skeleton.getY()) skeleton.setDirection("Up");
                            else if (player.getY() < skeleton.getY()) skeleton.setDirection("Down");
                            skeleton.setHealth(skeleton.getHealth() - player.getDamage());
                            skeleton.setImmortal(true);
                            skeleton.getTimer().schedule(skeleton.getKnockbackTimerTask(), 0, 10);
                        }
                        player.getAttackBox().update(0, 0, 0, 0);
                        if (player.getTime() >= 15) {
                            player.stopTimerPlayer();
                            player.setImmortal(false);
                        }
                        if (skeleton.getKnockbackTime() >= 25) {
                            skeleton.stopKnockbackTimer();
                            skeleton.setImmortal(false);
                            forSkeletonAnimation = true;
                        }

                        if (skeleton.getHealth() <= 0) skeleton.setDead(true);
                    }
                    else skeleton.getHitbox().update(0,0,0,0);*/

                    // Проверка всех мобов на столкновение со стенами
                    for (Mob mob : mobList) {
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
                    if (AABB.AABBvsAABB(player.getCollisionBox(), aabbMap.get("entranceToFirstLevel"))) {
                        level = "FirstLevel";
                        // Обновление хитбоксов стен для первого уровня
                        for(int i = 0, j = 0; i < 5; i++, j+=4) {
                            aabbMap.get("wall" + i).update(Storage.firstLevelWalls[j], Storage.firstLevelWalls[j + 1],
                                    Storage.firstLevelWalls[j + 2], Storage.firstLevelWalls[j + 3]);
                        }
                        player.setX(638 - 64 + 15);
                        player.setY(281);
                    }
                    break;
                }
            }

            // Метод для обработки игрока и отрисовка интерфейса
            if (!level.equals("MainMenu")) {
                player.update(window);

                // Полоска здоровья
                int tempHealth = player.getHealth() % 10 == 0 ? player.getHealth() : player.getHealth() - (player.getHealth() % 10) + 10;
                if (tempHealth >= 0) glBindTexture(GL_TEXTURE_2D, textureMap.get(tempHealth + "hp"));
                if (player.getHealth() <= 0) {
                    glBindTexture(GL_TEXTURE_2D, textureMap.get("0hp"));
                    player.setDead(true);
                }
                createQuadTexture(0, 0, 103, 18);

                // Щит с броней
                if (player.getArmor() < 30) glBindTexture(GL_TEXTURE_2D, textureMap.get("armor" + player.getArmor() / 5));
                else glBindTexture(GL_TEXTURE_2D, textureMap.get("armor5"));
                createQuadTexture(0, 19, 34, 53);

                // Количество монет
                int tempCoin = player.getMoney();
                int tempX0 = 633, tempX1 = 640, tempY0 = 0, tempY1 = 10;
                for (int i = 0; i < getCountsOfDigits(player.getMoney()); i++) {
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
            }

            // Отрисовка экипировки и анимации
            glBindTexture(GL_TEXTURE_2D, textureMap.get(player.getBodyAnimation()));
            createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            if (!player.getFeetTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(player.getFeetAnimation()));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getLegsTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(player.getLegsAnimation()));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getTorsoTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(player.getTorsoAnimation()));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getShouldersTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(player.getShouldersAnimation()));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getBeltTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(player.getBeltAnimation()));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getHeadTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(player.getHeadAnimation()));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if (!player.getHandsTexture().equals("nothing")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(player.getHandsAnimation()));
                createQuadTexture(player.getX(), player.getY(), player.getX() + 64, player.getY() + 64);
            }
            if ((player.isAttackLeft() || player.isAttackRight() || player.isAttackUp() ||
                    player.isAttackDown()) && !player.isDead() && !level.equals("MainMenu")) {
                glBindTexture(GL_TEXTURE_2D, textureMap.get(player.getWeaponAnimation()));
                createQuadTexture(player.getX() - player.getWeapon().getMinX(), player.getY() - player.getWeapon().getMinY(),
                        player.getX() + player.getWeapon().getMaxX(), player.getY() + player.getWeapon().getMaxY());
            }

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
        return(number == 0) ? 1 : (int) Math.ceil(Math.log10(Math.abs(number) + 0.5));
    }
}