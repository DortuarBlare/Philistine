package managers;

import content.Storage;
import levels.*;
import mobs.*;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import static org.lwjgl.opengl.GL11.*;

public class LevelManager {
    public static Level currentLevel;
    public static boolean canChangeLevel = false;

    static {
        currentLevel = new MainMenuLevel();
    }

    public static void update() {
        currentLevel.update();
        currentLevel.draw();

        for (Mob mob : SingletonMobs.mobList)  {
            mob.update();
            mob.draw();
        }
    }

    public static void mainMenuToTown() {
        currentLevel = new TownLevel();
        currentLevel.init();

        SoundManager.musicSource.stop(Storage.soundMap.get("mainMenuTheme"));
        SoundManager.musicSource.play(Storage.soundMap.get("townTheme"));
        SoundManager.musicSource.changeVolume(0.02f);
    }

    public static void townToTavern() {
        currentLevel = new TavernLevel();
        currentLevel.init();

        glTranslated(SingletonPlayer.player.getForPlacingCamera(), 0, 0);
        SingletonPlayer.player.setForPlacingCamera(3);

        // Обновление хитбоксов стен для tavern
        for (int i = 0, j = 0; i < 13; i++, j += 4) {
            Storage.aabbMap.get("wall" + i).update(Storage.tavernLevelWalls[j], Storage.tavernLevelWalls[j + 1],
                    Storage.tavernLevelWalls[j + 2], Storage.tavernLevelWalls[j + 3]);
        }

        SoundManager.environmentSoundSource.play(Storage.soundMap.get("doorOpen"));
        SoundManager.musicSource.stop(Storage.soundMap.get("mainMenuTheme"));
        SoundManager.musicSource.play(Storage.soundMap.get("tavernTheme"));

        SingletonPlayer.player.setX(210);
        SingletonPlayer.player.setY(254);
        SingletonPlayer.player.setMoveDirection("up");
    }

    public static void tavernToTown() {
        currentLevel = new TownLevel();
        currentLevel.init();

        SingletonPlayer.player.getStepSound().stop(SingletonPlayer.player.getPlayerSounds().get("stepWood"));
        SoundManager.environmentSoundSource.play(Storage.soundMap.get("doorOpen"));
        SoundManager.environmentSoundSource.play(Storage.soundMap.get("tavernTheme"));
        SoundManager.environmentSoundSource.play(Storage.soundMap.get("townTheme"));

        glTranslated(-SingletonPlayer.player.getForPlacingCamera(), 0, 0);
        SingletonPlayer.player.setX(293);
        SingletonPlayer.player.setY(192);
        SingletonPlayer.player.setMoveDirection("right");
    }

    public static void townToForge() {
        currentLevel = new ForgeLevel();
        currentLevel.init();

        glTranslated(SingletonPlayer.player.getForPlacingCamera(), 0, 0);
        SingletonPlayer.player.setForPlacingCamera(315);

        // Обновление хитбоксов стен для forge
        for (int i = 0, j = 0; i < 13; i++, j += 4) {
            Storage.aabbMap.get("wall" + i).update(Storage.forgeLevelWalls[j], Storage.forgeLevelWalls[j + 1],
                    Storage.forgeLevelWalls[j + 2], Storage.forgeLevelWalls[j + 3]);
        }

        SoundManager.environmentSoundSource.play(Storage.soundMap.get("doorOpen"));
        SoundManager.musicSource.stop(Storage.soundMap.get("townTheme"));
        SoundManager.musicSource.play(Storage.soundMap.get("forgeTheme"));

        SingletonPlayer.player.setX(368);
        SingletonPlayer.player.setY(254);
        SingletonPlayer.player.setMoveDirection("up");
    }

    public static void forgeToTown() {
        currentLevel = new TownLevel();
        currentLevel.init();

        SingletonPlayer.player.getStepSound().stop(SingletonPlayer.player.getPlayerSounds().get("stepWood"));
        SoundManager.environmentSoundSource.play(Storage.soundMap.get("doorOpen"));
        SoundManager.environmentSoundSource.play(Storage.soundMap.get("tavernTheme"));
        SoundManager.environmentSoundSource.play(Storage.soundMap.get("townTheme"));

        glTranslated(-SingletonPlayer.player.getForPlacingCamera(), 0, 0);
        SingletonPlayer.player.setX(605);
        SingletonPlayer.player.setY(192);
        SingletonPlayer.player.setMoveDirection("right");
    }

    public static void townToDungeon() {
        currentLevel = new DungeonVestibuleLevel();
        currentLevel.init();

        SingletonPlayer.player.setX(SingletonPlayer.player.getX() - 1);
        SingletonPlayer.player.setDialogBubble(false);

        if (SingletonPlayer.player.isDialogBubbleChoice()) {
            SoundManager.musicSource.changeVolume(0.1f);
            SoundManager.musicSource.stop(Storage.soundMap.get("townTheme"));
            SoundManager.musicSource.play(Storage.soundMap.get("dungeonAmbient1"));
            glTranslated(SingletonPlayer.player.getForPlacingCamera(), 0, 0);

            SingletonPlayer.player.setX(199);
            SingletonPlayer.player.setY(273);
            SingletonPlayer.player.setSpeed(2);
            SingletonPlayer.player.setMoveDirection("up");
        }
    }

    public static void dungeonVestibuleToBranchingRoom() {
        if (canChangeLevel) {
            canChangeLevel = false;

            // Переключение уровня
            currentLevel = new DungeonBranchingLevel();
            currentLevel.init();

            // Удаление трупов
            SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player));

            // Перемещение игрока
            SingletonPlayer.player.setX(-12);
            SingletonPlayer.player.setY(225);
        }
    }

    public static void dungeonBranchingRoomToVestibule() {
        if (canChangeLevel) {
            canChangeLevel = false;

            // Переключение уровня
            currentLevel = new DungeonVestibuleLevel();
            currentLevel.init();

            // Удаление трупов
            SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player));

            // Перемещение игрока
            SingletonPlayer.player.setX(580);
            SingletonPlayer.player.setY(281);
        }
    }

    public static void dungeonBranchingRoomToTreasureRoom() {
        if (canChangeLevel) {
            canChangeLevel = false;

            // Переключение уровня
            currentLevel = new TreasureLevel();
            currentLevel.init();

            // Удаление трупов
            SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player));

            // Перемещение игрока
            SingletonPlayer.player.setX(240);
            SingletonPlayer.player.setY(120);
            SingletonPlayer.player.setMoveDirection("down");
        }
    }

    public static void dungeonBranchingRoomToBossRoom () {
        if (canChangeLevel) {
            canChangeLevel = false;

            // Переключение уровня
            currentLevel = new BossLevel();
            currentLevel.init();

            // Удаление трупов
            SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player));

            // Перемещение игрока
            SingletonPlayer.player.setX(180);
            SingletonPlayer.player.setY(120);
            SingletonPlayer.player.setMoveDirection("down");
        }
    }

    public static void dungeonTreasureRoomToBranchingRoom() {
        // Переключение уровня
        currentLevel = new DungeonBranchingLevel();
        currentLevel.init();

        // Перемещение игрока
        SingletonPlayer.player.setX(180);
        SingletonPlayer.player.setY(120);
        SingletonPlayer.player.setMoveDirection("down");
    }

    public static void dungeonBossRoomToBranchingRoom() {
        if (canChangeLevel) {
            canChangeLevel = false;

            // Переключение уровня
            currentLevel = new DungeonBranchingLevel();
            currentLevel.init();

            // Удаление трупов
            SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player));

            // Перемещение игрока
            SingletonPlayer.player.setX(240);
            SingletonPlayer.player.setY(120);
            SingletonPlayer.player.setMoveDirection("down");
        }
    }

    public static void dungeonBossRoomToTown() {
        if (canChangeLevel) {
            canChangeLevel = false;

            // Переключение уровня
            currentLevel = new TownLevel();
            currentLevel.init();

            // Удаление трупов
            SingletonMobs.mobList.removeIf(mob -> !(mob instanceof Player)); // Удаление трупов


            // Обновление всех объектов
            SoundManager.musicSource.stop(Storage.soundMap.get("dungeonAmbient1"));
            SoundManager.musicSource.play(Storage.soundMap.get("townTheme"));
            SoundManager.musicSource.changeVolume(0.02f);

            // Перемещение игрока и камеры
            glTranslated(-689, 0, 0);
            SingletonPlayer.player.setForPlacingCamera(689);
            SingletonPlayer.player.setX(979);
            SingletonPlayer.player.setY(192);
            SingletonPlayer.player.setSpeed(1);
            SingletonPlayer.player.setMoveDirection("left");
        }
    }

    public static boolean attackAllowed() {
        return
                currentLevel instanceof DungeonVestibuleLevel ||
                currentLevel instanceof DungeonBranchingLevel ||
                currentLevel instanceof TreasureLevel ||
                currentLevel instanceof BossLevel;
    }
}
