package content;

public class Storage {
    public static final int[] firstLevelWalls = {
            108, 128, 495, 140, // wall0
            496, 143, 498, 232, // wall1
            499, 232, 639, 236, // wall2
            108, 339, 640, 359, // wall3
            0, 45, 107, 338, // wall4
            0, 0, 0, 0, // wall5
            0, 0, 0, 0 // wall6
    };

    public static final int[] secondLevelWalls = {
            0, 182, 94, 188, // wall0
            92, 140, 96, 184, // wall1
            98, 134, 526, 140, // wall2
            529, 143, 534, 334, // wall3
            97, 335, 528, 340, // wall4
            92, 290, 96, 334, // wall5
            0, 287, 92, 291 // wall6
    };

    public static final int[] thirdLevelWalls = {
            96, 135, 528, 141, // wall0
            528, 147, 534, 339, // wall1
            96, 339, 528, 342, // wall2
            90, 141, 96, 336, // wall3
            0, 0, 0, 0, // wall4
            0, 0, 0, 0, // wall5
            0, 0, 0, 0 // wall6
    };

    public static final int[] fourthLevelWalls = {
            96, 135, 528, 141, // wall0
            528, 147, 534, 339, // wall1
            96, 339, 528, 342, // wall2
            90, 141, 96, 336, // wall3
            0, 0, 0, 0, // wall4
            0, 0, 0, 0, // wall5
            0, 0, 0, 0 // wall6
    };

    public static final int[] townLevelWalls = {
            0, 0, 0, 0, // wall0
            0, 0, 0, 0, // wall1
            0, 0, 0, 0, // wall2
            0, 0, 0, 0, // wall3
            0, 0, 0, 0, // wall4
            0, 0, 0, 0, // wall5
            0, 0, 0, 0 // wall6
    };

    public static final int[] tavernLevelWalls = {
            189, 279, 223, 280, // wall0
            184, 239, 188, 278, // wall1
            189, 237, 447, 240, // wall2
            448, 242, 451, 277, // wall3
            257, 279, 448, 273, // wall4
            256, 280, 261, 318, // wall5
            222, 280, 224, 318 // wall6
    };

    public static final int[] forgeLevelWalls = {
            382, 279, 383, 317, // wall0
            342, 277, 381, 278, // wall1
            340, 239, 341, 278, // wall2
            342, 230, 451, 231, // wall3
            452, 239, 458, 276, // wall4
            418, 277, 451, 278, // wall5
            416, 279, 417, 317 // wall6
    };

    public static final String[] aabbString = {
            "wall0", "wall1", "wall2", "wall3", "wall4", "wall5", "wall6",
            "entranceToFirstLevel", "entranceToSecondLevel", "entranceToThirdLevel", "entranceToForthLevel", "entranceFromThirdToSecondLevel", "entranceFromForthToSecondLevel", "entranceFromTavernToTown", "entranceFromForgeToTown",
            "toBuyBeer"
    };

    public static final String[] soundString = {
            "mainMenuTheme", "dungeonAmbient1", "changingArmor", "pickedCoin", "doorOpen", "drinkBeer"
    };

    public static final String[] playerSoundString = {
            "stepStone", "stepWood", "swish", "selectionClick", "openMenu"
    };

    public static final String[] slimeSoundString = {
            "slimeHurt", "slimeDead"
    };

    public static final String[] textureString = {
            // Фон
            "level0", "level1", "level2", "level3", "MainMenu",

            // Интерфейс
            "0hp", "10hp", "20hp", "30hp", "40hp", "50hp", "60hp", "70hp", "80hp", "90hp", "100hp",
            "0armor", "5armor", "10armor", "15armor", "20armor", "25armor", "30armor",
            "you_Shall_Not_Pass", "enterTheDungeon_Yes", "enterTheDungeon_No",
            "Press_enter", "Philistine", "price", "price2",
            "emotion_question", "emotion_health",
            "enemyHp0", "enemyHp10", "enemyHp20", "enemyHp30", "enemyHp40", "enemyHp50", "enemyHp60", "enemyHp70", "enemyHp80", "enemyHp90", "enemyHp100",
            "coin_01", "coin_02", "coin_03", "coin_04", "coin_05", "coin_06", "coin_07", "coin_08",
            "number_0", "number_1", "number_2", "number_3", "number_4", "number_5", "number_6", "number_7", "number_8", "number_9", "keyGold",
            "scrollMenu_Resume", "scrollMenu_Options", "scrollMenu_Options_Controls", "scrollMenu_Options_Sounds", "scrollMenu_Controls", "scrollMenu_Exit",

            // Окружение
            "chestClosed", "chestOpened",
            "boxClosed", "boxOpened",
            "barrelClosed", "barrelOpened",
            "bones", "trash",
            "bookBlue", "bookGreen", "bookPink", "bookRed",
            "chair0", "chair1", "chair2", "chair3",
            "table0", "table1",
            "bagSmall", "bagMedium", "bagBig", "littleBag",
            "leverOff", "leverOn",
            "furnace_burn_01", "furnace_burn_02", "furnace_burn_03",
            "torch_01", "torch_02", "torch_03", "torch_04", "torch_05", "torch_06", "torch_07", "torch_08", "torch_09",
            "leather_hat", "robe_hood", "chain_helmet", "chain_hood", "plate_helmet",
            "leather_shoulderPads", "plate_shoulderPads",
            "shirt_white", "plate_armor", "robe_shirt_brown", "leather_armor", "chain_jacket_purple", "chain_armor",
            "leather", "rope",
            "plate_gloves", "leather_bracers",
            "pants_greenish", "plate_pants", "robe_skirt",
            "shoes_brown", "plate_shoes",
            "longsword", "rapier", "long_spear", "spear", "stick", "potionRed",
            "guard_stand_01", "guard_stand_02", "guard_stand_03", "guard_stand_04", "guard_stand_05", "guard_stand_06", "guard_stand_07", "guard_stand_08", "guard_stand_09", "guard_stand_010",

            // NPC
            "blacksmith_left_move_01", "blacksmith_left_move_02", "blacksmith_left_move_03", "blacksmith_left_move_04", "blacksmith_left_move_05", "blacksmith_left_move_06", "blacksmith_left_move_07", "blacksmith_left_move_08", "blacksmith_left_move_09",
            "blacksmith_right_move_01", "blacksmith_right_move_02", "blacksmith_right_move_03", "blacksmith_right_move_04", "blacksmith_right_move_05", "blacksmith_right_move_06", "blacksmith_right_move_07", "blacksmith_right_move_08", "blacksmith_right_move_09",
            "blacksmith_up_move_01", "blacksmith_up_move_02", "blacksmith_up_move_03", "blacksmith_up_move_04", "blacksmith_up_move_05", "blacksmith_up_move_06", "blacksmith_up_move_07", "blacksmith_up_move_08", "blacksmith_up_move_09",
            "blacksmith_down_move_01", "blacksmith_down_move_02", "blacksmith_down_move_03", "blacksmith_down_move_04", "blacksmith_down_move_05", "blacksmith_down_move_06", "blacksmith_down_move_07", "blacksmith_down_move_08", "blacksmith_down_move_09",

            "waiter_left_move_01", "waiter_left_move_02", "waiter_left_move_03", "waiter_left_move_04", "waiter_left_move_05", "waiter_left_move_06", "waiter_left_move_07", "waiter_left_move_08", "waiter_left_move_09",
            "waiter_right_move_01", "waiter_right_move_02", "waiter_right_move_03", "waiter_right_move_04", "waiter_right_move_05", "waiter_right_move_06", "waiter_right_move_07", "waiter_right_move_08", "waiter_right_move_09",
            "waiter_up_move_01", "waiter_up_move_02", "waiter_up_move_03", "waiter_up_move_04", "waiter_up_move_05", "waiter_up_move_06", "waiter_up_move_07", "waiter_up_move_08", "waiter_up_move_09",
            "waiter_down_move_01", "waiter_down_move_02", "waiter_down_move_03", "waiter_down_move_04", "waiter_down_move_05", "waiter_down_move_06", "waiter_down_move_07", "waiter_down_move_08", "waiter_down_move_09",

            "gentleman_brown",

            // Анимации игрока
            "player_stand_left", "player_stand_right", "player_stand_up", "player_stand_down",
            "player_walk_left_01", "player_walk_left_02", "player_walk_left_03", "player_walk_left_04", "player_walk_left_05", "player_walk_left_06", "player_walk_left_07", "player_walk_left_08", "player_walk_left_09",
            "player_walk_right_01", "player_walk_right_02", "player_walk_right_03", "player_walk_right_04", "player_walk_right_05", "player_walk_right_06", "player_walk_right_07", "player_walk_right_08", "player_walk_right_09",
            "player_walk_up_01", "player_walk_up_02", "player_walk_up_03", "player_walk_up_04", "player_walk_up_05", "player_walk_up_06", "player_walk_up_07", "player_walk_up_08", "player_walk_up_09",
            "player_walk_down_01", "player_walk_down_02", "player_walk_down_03", "player_walk_down_04", "player_walk_down_05", "player_walk_down_06", "player_walk_down_07", "player_walk_down_08", "player_walk_down_09",

            "player_slash_right_01", "player_slash_right_02", "player_slash_right_03", "player_slash_right_04", "player_slash_right_05", "player_slash_right_06",
            "player_slash_up_01", "player_slash_up_02", "player_slash_up_03", "player_slash_up_04", "player_slash_up_05", "player_slash_up_06",
            "player_slash_left_01", "player_slash_left_02", "player_slash_left_03", "player_slash_left_04", "player_slash_left_05", "player_slash_left_06",
            "player_slash_down_01", "player_slash_down_02", "player_slash_down_03", "player_slash_down_04", "player_slash_down_05", "player_slash_down_06",

            "player_thrust_left_01", "player_thrust_left_02", "player_thrust_left_03", "player_thrust_left_04", "player_thrust_left_05", "player_thrust_left_06", "player_thrust_left_07", "player_thrust_left_08",
            "player_thrust_right_01", "player_thrust_right_02", "player_thrust_right_03", "player_thrust_right_04", "player_thrust_right_05", "player_thrust_right_06", "player_thrust_right_07", "player_thrust_right_08",
            "player_thrust_up_01", "player_thrust_up_02", "player_thrust_up_03", "player_thrust_up_04", "player_thrust_up_05", "player_thrust_up_06", "player_thrust_up_07", "player_thrust_up_08",
            "player_thrust_down_01", "player_thrust_down_02", "player_thrust_down_03", "player_thrust_down_04", "player_thrust_down_05", "player_thrust_down_06", "player_thrust_down_07", "player_thrust_down_08",

            "player_hurt_01", "player_hurt_02", "player_hurt_03", "player_hurt_04", "player_hurt_05", "player_hurt_06",

            // Оружие
            "weapon_longsword_left_slash_01", "weapon_longsword_left_slash_02", "weapon_longsword_left_slash_03", "weapon_longsword_left_slash_04", "weapon_longsword_left_slash_05", "weapon_longsword_left_slash_06",
            "weapon_longsword_right_slash_01", "weapon_longsword_right_slash_02", "weapon_longsword_right_slash_03", "weapon_longsword_right_slash_04", "weapon_longsword_right_slash_05", "weapon_longsword_right_slash_06",
            "weapon_longsword_up_slash_01", "weapon_longsword_up_slash_02", "weapon_longsword_up_slash_03", "weapon_longsword_up_slash_04", "weapon_longsword_up_slash_05", "weapon_longsword_up_slash_06",
            "weapon_longsword_down_slash_01", "weapon_longsword_down_slash_02", "weapon_longsword_down_slash_03", "weapon_longsword_down_slash_04", "weapon_longsword_down_slash_05", "weapon_longsword_down_slash_06",

            "weapon_rapier_right_slash_01", "weapon_rapier_right_slash_02", "weapon_rapier_right_slash_03", "weapon_rapier_right_slash_04", "weapon_rapier_right_slash_05", "weapon_rapier_right_slash_06",
            "weapon_rapier_up_slash_01", "weapon_rapier_up_slash_02", "weapon_rapier_up_slash_03", "weapon_rapier_up_slash_04", "weapon_rapier_up_slash_05", "weapon_rapier_up_slash_06",
            "weapon_rapier_down_slash_01", "weapon_rapier_down_slash_02", "weapon_rapier_down_slash_03", "weapon_rapier_down_slash_04", "weapon_rapier_down_slash_05", "weapon_rapier_down_slash_06",
            "weapon_rapier_left_slash_01", "weapon_rapier_left_slash_02", "weapon_rapier_left_slash_03", "weapon_rapier_left_slash_04", "weapon_rapier_left_slash_05", "weapon_rapier_left_slash_06",

            "weapon_long_spear_left_thrust_01", "weapon_long_spear_left_thrust_02", "weapon_long_spear_left_thrust_03", "weapon_long_spear_left_thrust_04", "weapon_long_spear_left_thrust_05", "weapon_long_spear_left_thrust_06", "weapon_long_spear_left_thrust_07", "weapon_long_spear_left_thrust_08",
            "weapon_long_spear_right_thrust_01", "weapon_long_spear_right_thrust_02", "weapon_long_spear_right_thrust_03", "weapon_long_spear_right_thrust_04", "weapon_long_spear_right_thrust_05", "weapon_long_spear_right_thrust_06", "weapon_long_spear_right_thrust_07", "weapon_long_spear_right_thrust_08",
            "weapon_long_spear_up_thrust_01", "weapon_long_spear_up_thrust_02", "weapon_long_spear_up_thrust_03", "weapon_long_spear_up_thrust_04", "weapon_long_spear_up_thrust_05", "weapon_long_spear_up_thrust_06", "weapon_long_spear_up_thrust_07", "weapon_long_spear_up_thrust_08",
            "weapon_long_spear_down_thrust_01", "weapon_long_spear_down_thrust_02", "weapon_long_spear_down_thrust_03", "weapon_long_spear_down_thrust_04", "weapon_long_spear_down_thrust_05", "weapon_long_spear_down_thrust_06", "weapon_long_spear_down_thrust_07", "weapon_long_spear_down_thrust_08",

            "weapon_stick_left_thrust_01", "weapon_stick_left_thrust_02", "weapon_stick_left_thrust_03", "weapon_stick_left_thrust_04", "weapon_stick_left_thrust_05", "weapon_stick_left_thrust_06", "weapon_stick_left_thrust_07", "weapon_stick_left_thrust_08",
            "weapon_stick_right_thrust_01", "weapon_stick_right_thrust_02", "weapon_stick_right_thrust_03", "weapon_stick_right_thrust_04", "weapon_stick_right_thrust_05", "weapon_stick_right_thrust_06", "weapon_stick_right_thrust_07", "weapon_stick_right_thrust_08",
            "weapon_stick_up_thrust_01", "weapon_stick_up_thrust_02", "weapon_stick_up_thrust_03", "weapon_stick_up_thrust_04", "weapon_stick_up_thrust_05", "weapon_stick_up_thrust_06", "weapon_stick_up_thrust_07", "weapon_stick_up_thrust_08",
            "weapon_stick_down_thrust_01", "weapon_stick_down_thrust_02", "weapon_stick_down_thrust_03", "weapon_stick_down_thrust_04", "weapon_stick_down_thrust_05", "weapon_stick_down_thrust_06", "weapon_stick_down_thrust_07", "weapon_stick_down_thrust_08",

            // Одежда игрока
            "HEAD_hair_left_move_01", "HEAD_hair_left_move_02", "HEAD_hair_left_move_03", "HEAD_hair_left_move_04", "HEAD_hair_left_move_05", "HEAD_hair_left_move_06", "HEAD_hair_left_move_07", "HEAD_hair_left_move_08", "HEAD_hair_left_move_09",
            "HEAD_hair_right_move_01", "HEAD_hair_right_move_02", "HEAD_hair_right_move_03", "HEAD_hair_right_move_04", "HEAD_hair_right_move_05", "HEAD_hair_right_move_06", "HEAD_hair_right_move_07", "HEAD_hair_right_move_08", "HEAD_hair_right_move_09",
            "HEAD_hair_up_move_01", "HEAD_hair_up_move_02", "HEAD_hair_up_move_03", "HEAD_hair_up_move_04", "HEAD_hair_up_move_05", "HEAD_hair_up_move_06", "HEAD_hair_up_move_07", "HEAD_hair_up_move_08", "HEAD_hair_up_move_09",
            "HEAD_hair_down_move_01", "HEAD_hair_down_move_02", "HEAD_hair_down_move_03", "HEAD_hair_down_move_04", "HEAD_hair_down_move_05", "HEAD_hair_down_move_06", "HEAD_hair_down_move_07", "HEAD_hair_down_move_08", "HEAD_hair_down_move_09",

            "HEAD_hair_left_slash_01", "HEAD_hair_left_slash_02", "HEAD_hair_left_slash_03", "HEAD_hair_left_slash_04", "HEAD_hair_left_slash_05", "HEAD_hair_left_slash_06",
            "HEAD_hair_right_slash_01", "HEAD_hair_right_slash_02", "HEAD_hair_right_slash_03", "HEAD_hair_right_slash_04", "HEAD_hair_right_slash_05", "HEAD_hair_right_slash_06",
            "HEAD_hair_up_slash_01", "HEAD_hair_up_slash_02", "HEAD_hair_up_slash_03", "HEAD_hair_up_slash_04", "HEAD_hair_up_slash_05", "HEAD_hair_up_slash_06",
            "HEAD_hair_down_slash_01", "HEAD_hair_down_slash_02", "HEAD_hair_down_slash_03", "HEAD_hair_down_slash_04", "HEAD_hair_down_slash_05", "HEAD_hair_down_slash_06",

            "HEAD_hair_hurt_01", "HEAD_hair_hurt_02", "HEAD_hair_hurt_03", "HEAD_hair_hurt_04", "HEAD_hair_hurt_05", "HEAD_hair_hurt_06",

            "HEAD_leather_hat_left_move_01", "HEAD_leather_hat_left_move_02", "HEAD_leather_hat_left_move_03", "HEAD_leather_hat_left_move_04", "HEAD_leather_hat_left_move_05", "HEAD_leather_hat_left_move_06", "HEAD_leather_hat_left_move_07", "HEAD_leather_hat_left_move_08", "HEAD_leather_hat_left_move_09",
            "HEAD_leather_hat_right_move_01", "HEAD_leather_hat_right_move_02", "HEAD_leather_hat_right_move_03", "HEAD_leather_hat_right_move_04", "HEAD_leather_hat_right_move_05", "HEAD_leather_hat_right_move_06", "HEAD_leather_hat_right_move_07", "HEAD_leather_hat_right_move_08", "HEAD_leather_hat_right_move_09",
            "HEAD_leather_hat_up_move_01", "HEAD_leather_hat_up_move_02", "HEAD_leather_hat_up_move_03", "HEAD_leather_hat_up_move_04", "HEAD_leather_hat_up_move_05", "HEAD_leather_hat_up_move_06", "HEAD_leather_hat_up_move_07", "HEAD_leather_hat_up_move_08", "HEAD_leather_hat_up_move_09",
            "HEAD_leather_hat_down_move_01", "HEAD_leather_hat_down_move_02", "HEAD_leather_hat_down_move_03", "HEAD_leather_hat_down_move_04", "HEAD_leather_hat_down_move_05", "HEAD_leather_hat_down_move_06", "HEAD_leather_hat_down_move_07", "HEAD_leather_hat_down_move_08", "HEAD_leather_hat_down_move_09",

            "HEAD_leather_hat_left_slash_01", "HEAD_leather_hat_left_slash_02", "HEAD_leather_hat_left_slash_03", "HEAD_leather_hat_left_slash_04", "HEAD_leather_hat_left_slash_05", "HEAD_leather_hat_left_slash_06",
            "HEAD_leather_hat_right_slash_01", "HEAD_leather_hat_right_slash_02", "HEAD_leather_hat_right_slash_03", "HEAD_leather_hat_right_slash_04", "HEAD_leather_hat_right_slash_05", "HEAD_leather_hat_right_slash_06",
            "HEAD_leather_hat_up_slash_01", "HEAD_leather_hat_up_slash_02", "HEAD_leather_hat_up_slash_03", "HEAD_leather_hat_up_slash_04", "HEAD_leather_hat_up_slash_05", "HEAD_leather_hat_up_slash_06",
            "HEAD_leather_hat_down_slash_01", "HEAD_leather_hat_down_slash_02", "HEAD_leather_hat_down_slash_03", "HEAD_leather_hat_down_slash_04", "HEAD_leather_hat_down_slash_05", "HEAD_leather_hat_down_slash_06",

            "HEAD_leather_hat_left_thrust_01", "HEAD_leather_hat_left_thrust_02", "HEAD_leather_hat_left_thrust_03", "HEAD_leather_hat_left_thrust_04", "HEAD_leather_hat_left_thrust_05", "HEAD_leather_hat_left_thrust_06", "HEAD_leather_hat_left_thrust_07", "HEAD_leather_hat_left_thrust_08",
            "HEAD_leather_hat_right_thrust_01", "HEAD_leather_hat_right_thrust_02", "HEAD_leather_hat_right_thrust_03", "HEAD_leather_hat_right_thrust_04", "HEAD_leather_hat_right_thrust_05", "HEAD_leather_hat_right_thrust_06", "HEAD_leather_hat_right_thrust_07", "HEAD_leather_hat_right_thrust_08",
            "HEAD_leather_hat_up_thrust_01", "HEAD_leather_hat_up_thrust_02", "HEAD_leather_hat_up_thrust_03", "HEAD_leather_hat_up_thrust_04", "HEAD_leather_hat_up_thrust_05", "HEAD_leather_hat_up_thrust_06", "HEAD_leather_hat_up_thrust_07", "HEAD_leather_hat_up_thrust_08",
            "HEAD_leather_hat_down_thrust_01", "HEAD_leather_hat_down_thrust_02", "HEAD_leather_hat_down_thrust_03", "HEAD_leather_hat_down_thrust_04", "HEAD_leather_hat_down_thrust_05", "HEAD_leather_hat_down_thrust_06", "HEAD_leather_hat_down_thrust_07", "HEAD_leather_hat_down_thrust_08",

            "HEAD_leather_hat_hurt_01", "HEAD_leather_hat_hurt_02", "HEAD_leather_hat_hurt_03", "HEAD_leather_hat_hurt_04", "HEAD_leather_hat_hurt_05", "HEAD_leather_hat_hurt_06",

            "HEAD_robe_hood_left_move_01", "HEAD_robe_hood_left_move_02", "HEAD_robe_hood_left_move_03", "HEAD_robe_hood_left_move_04", "HEAD_robe_hood_left_move_05", "HEAD_robe_hood_left_move_06", "HEAD_robe_hood_left_move_07", "HEAD_robe_hood_left_move_08", "HEAD_robe_hood_left_move_09",
            "HEAD_robe_hood_right_move_01", "HEAD_robe_hood_right_move_02", "HEAD_robe_hood_right_move_03", "HEAD_robe_hood_right_move_04", "HEAD_robe_hood_right_move_05", "HEAD_robe_hood_right_move_06", "HEAD_robe_hood_right_move_07", "HEAD_robe_hood_right_move_08", "HEAD_robe_hood_right_move_09",
            "HEAD_robe_hood_up_move_01", "HEAD_robe_hood_up_move_02", "HEAD_robe_hood_up_move_03", "HEAD_robe_hood_up_move_04", "HEAD_robe_hood_up_move_05", "HEAD_robe_hood_up_move_06", "HEAD_robe_hood_up_move_07", "HEAD_robe_hood_up_move_08", "HEAD_robe_hood_up_move_09",
            "HEAD_robe_hood_down_move_01", "HEAD_robe_hood_down_move_02", "HEAD_robe_hood_down_move_03", "HEAD_robe_hood_down_move_04", "HEAD_robe_hood_down_move_05", "HEAD_robe_hood_down_move_06", "HEAD_robe_hood_down_move_07", "HEAD_robe_hood_down_move_08", "HEAD_robe_hood_down_move_09",

            "HEAD_robe_hood_left_slash_01", "HEAD_robe_hood_left_slash_02", "HEAD_robe_hood_left_slash_03", "HEAD_robe_hood_left_slash_04", "HEAD_robe_hood_left_slash_05", "HEAD_robe_hood_left_slash_06",
            "HEAD_robe_hood_right_slash_01", "HEAD_robe_hood_right_slash_02", "HEAD_robe_hood_right_slash_03", "HEAD_robe_hood_right_slash_04", "HEAD_robe_hood_right_slash_05", "HEAD_robe_hood_right_slash_06",
            "HEAD_robe_hood_up_slash_01", "HEAD_robe_hood_up_slash_02", "HEAD_robe_hood_up_slash_03", "HEAD_robe_hood_up_slash_04", "HEAD_robe_hood_up_slash_05", "HEAD_robe_hood_up_slash_06",
            "HEAD_robe_hood_down_slash_01", "HEAD_robe_hood_down_slash_02", "HEAD_robe_hood_down_slash_03", "HEAD_robe_hood_down_slash_04", "HEAD_robe_hood_down_slash_05", "HEAD_robe_hood_down_slash_06",

            "HEAD_robe_hood_left_thrust_01", "HEAD_robe_hood_left_thrust_02", "HEAD_robe_hood_left_thrust_03", "HEAD_robe_hood_left_thrust_04", "HEAD_robe_hood_left_thrust_05", "HEAD_robe_hood_left_thrust_06", "HEAD_robe_hood_left_thrust_07", "HEAD_robe_hood_left_thrust_08",
            "HEAD_robe_hood_right_thrust_01", "HEAD_robe_hood_right_thrust_02", "HEAD_robe_hood_right_thrust_03", "HEAD_robe_hood_right_thrust_04", "HEAD_robe_hood_right_thrust_05", "HEAD_robe_hood_right_thrust_06", "HEAD_robe_hood_right_thrust_07", "HEAD_robe_hood_right_thrust_08",
            "HEAD_robe_hood_up_thrust_01", "HEAD_robe_hood_up_thrust_02", "HEAD_robe_hood_up_thrust_03", "HEAD_robe_hood_up_thrust_04", "HEAD_robe_hood_up_thrust_05", "HEAD_robe_hood_up_thrust_06", "HEAD_robe_hood_up_thrust_07", "HEAD_robe_hood_up_thrust_08",
            "HEAD_robe_hood_down_thrust_01", "HEAD_robe_hood_down_thrust_02", "HEAD_robe_hood_down_thrust_03", "HEAD_robe_hood_down_thrust_04", "HEAD_robe_hood_down_thrust_05", "HEAD_robe_hood_down_thrust_06", "HEAD_robe_hood_down_thrust_07", "HEAD_robe_hood_down_thrust_08",

            "HEAD_robe_hood_hurt_01", "HEAD_robe_hood_hurt_02", "HEAD_robe_hood_hurt_03", "HEAD_robe_hood_hurt_04", "HEAD_robe_hood_hurt_05", "HEAD_robe_hood_hurt_06",

            "HEAD_chain_helmet_left_move_01", "HEAD_chain_helmet_left_move_02", "HEAD_chain_helmet_left_move_03", "HEAD_chain_helmet_left_move_04", "HEAD_chain_helmet_left_move_05", "HEAD_chain_helmet_left_move_06", "HEAD_chain_helmet_left_move_07", "HEAD_chain_helmet_left_move_08", "HEAD_chain_helmet_left_move_09",
            "HEAD_chain_helmet_right_move_01", "HEAD_chain_helmet_right_move_02", "HEAD_chain_helmet_right_move_03", "HEAD_chain_helmet_right_move_04", "HEAD_chain_helmet_right_move_05", "HEAD_chain_helmet_right_move_06", "HEAD_chain_helmet_right_move_07", "HEAD_chain_helmet_right_move_08", "HEAD_chain_helmet_right_move_09",
            "HEAD_chain_helmet_up_move_01", "HEAD_chain_helmet_up_move_02", "HEAD_chain_helmet_up_move_03", "HEAD_chain_helmet_up_move_04", "HEAD_chain_helmet_up_move_05", "HEAD_chain_helmet_up_move_06", "HEAD_chain_helmet_up_move_07", "HEAD_chain_helmet_up_move_08", "HEAD_chain_helmet_up_move_09",
            "HEAD_chain_helmet_down_move_01", "HEAD_chain_helmet_down_move_02", "HEAD_chain_helmet_down_move_03", "HEAD_chain_helmet_down_move_04", "HEAD_chain_helmet_down_move_05", "HEAD_chain_helmet_down_move_06", "HEAD_chain_helmet_down_move_07", "HEAD_chain_helmet_down_move_08", "HEAD_chain_helmet_down_move_09",

            "HEAD_chain_helmet_left_slash_01", "HEAD_chain_helmet_left_slash_02", "HEAD_chain_helmet_left_slash_03", "HEAD_chain_helmet_left_slash_04", "HEAD_chain_helmet_left_slash_05", "HEAD_chain_helmet_left_slash_06",
            "HEAD_chain_helmet_right_slash_01", "HEAD_chain_helmet_right_slash_02", "HEAD_chain_helmet_right_slash_03", "HEAD_chain_helmet_right_slash_04", "HEAD_chain_helmet_right_slash_05", "HEAD_chain_helmet_right_slash_06",
            "HEAD_chain_helmet_up_slash_01", "HEAD_chain_helmet_up_slash_02", "HEAD_chain_helmet_up_slash_03", "HEAD_chain_helmet_up_slash_04", "HEAD_chain_helmet_up_slash_05", "HEAD_chain_helmet_up_slash_06",
            "HEAD_chain_helmet_down_slash_01", "HEAD_chain_helmet_down_slash_02", "HEAD_chain_helmet_down_slash_03", "HEAD_chain_helmet_down_slash_04", "HEAD_chain_helmet_down_slash_05", "HEAD_chain_helmet_down_slash_06",

            "HEAD_chain_helmet_left_thrust_01", "HEAD_chain_helmet_left_thrust_02", "HEAD_chain_helmet_left_thrust_03", "HEAD_chain_helmet_left_thrust_04", "HEAD_chain_helmet_left_thrust_05", "HEAD_chain_helmet_left_thrust_06", "HEAD_chain_helmet_left_thrust_07", "HEAD_chain_helmet_left_thrust_08",
            "HEAD_chain_helmet_right_thrust_01", "HEAD_chain_helmet_right_thrust_02", "HEAD_chain_helmet_right_thrust_03", "HEAD_chain_helmet_right_thrust_04", "HEAD_chain_helmet_right_thrust_05", "HEAD_chain_helmet_right_thrust_06", "HEAD_chain_helmet_right_thrust_07", "HEAD_chain_helmet_right_thrust_08",
            "HEAD_chain_helmet_up_thrust_01", "HEAD_chain_helmet_up_thrust_02", "HEAD_chain_helmet_up_thrust_03", "HEAD_chain_helmet_up_thrust_04", "HEAD_chain_helmet_up_thrust_05", "HEAD_chain_helmet_up_thrust_06", "HEAD_chain_helmet_up_thrust_07", "HEAD_chain_helmet_up_thrust_08",
            "HEAD_chain_helmet_down_thrust_01", "HEAD_chain_helmet_down_thrust_02", "HEAD_chain_helmet_down_thrust_03", "HEAD_chain_helmet_down_thrust_04", "HEAD_chain_helmet_down_thrust_05", "HEAD_chain_helmet_down_thrust_06", "HEAD_chain_helmet_down_thrust_07", "HEAD_chain_helmet_down_thrust_08",

            "HEAD_chain_helmet_hurt_01", "HEAD_chain_helmet_hurt_02", "HEAD_chain_helmet_hurt_03", "HEAD_chain_helmet_hurt_04", "HEAD_chain_helmet_hurt_05", "HEAD_chain_helmet_hurt_06",

            "HEAD_chain_hood_left_move_01", "HEAD_chain_hood_left_move_02", "HEAD_chain_hood_left_move_03", "HEAD_chain_hood_left_move_04", "HEAD_chain_hood_left_move_05", "HEAD_chain_hood_left_move_06", "HEAD_chain_hood_left_move_07", "HEAD_chain_hood_left_move_08", "HEAD_chain_hood_left_move_09",
            "HEAD_chain_hood_right_move_01", "HEAD_chain_hood_right_move_02", "HEAD_chain_hood_right_move_03", "HEAD_chain_hood_right_move_04", "HEAD_chain_hood_right_move_05", "HEAD_chain_hood_right_move_06", "HEAD_chain_hood_right_move_07", "HEAD_chain_hood_right_move_08", "HEAD_chain_hood_right_move_09",
            "HEAD_chain_hood_up_move_01", "HEAD_chain_hood_up_move_02", "HEAD_chain_hood_up_move_03", "HEAD_chain_hood_up_move_04", "HEAD_chain_hood_up_move_05", "HEAD_chain_hood_up_move_06", "HEAD_chain_hood_up_move_07", "HEAD_chain_hood_up_move_08", "HEAD_chain_hood_up_move_09",
            "HEAD_chain_hood_down_move_01", "HEAD_chain_hood_down_move_02", "HEAD_chain_hood_down_move_03", "HEAD_chain_hood_down_move_04", "HEAD_chain_hood_down_move_05", "HEAD_chain_hood_down_move_06", "HEAD_chain_hood_down_move_07", "HEAD_chain_hood_down_move_08", "HEAD_chain_hood_down_move_09",

            "HEAD_chain_hood_left_slash_01", "HEAD_chain_hood_left_slash_02", "HEAD_chain_hood_left_slash_03", "HEAD_chain_hood_left_slash_04", "HEAD_chain_hood_left_slash_05", "HEAD_chain_hood_left_slash_06",
            "HEAD_chain_hood_right_slash_01", "HEAD_chain_hood_right_slash_02", "HEAD_chain_hood_right_slash_03", "HEAD_chain_hood_right_slash_04", "HEAD_chain_hood_right_slash_05", "HEAD_chain_hood_right_slash_06",
            "HEAD_chain_hood_up_slash_01", "HEAD_chain_hood_up_slash_02", "HEAD_chain_hood_up_slash_03", "HEAD_chain_hood_up_slash_04", "HEAD_chain_hood_up_slash_05", "HEAD_chain_hood_up_slash_06",
            "HEAD_chain_hood_down_slash_01", "HEAD_chain_hood_down_slash_02", "HEAD_chain_hood_down_slash_03", "HEAD_chain_hood_down_slash_04", "HEAD_chain_hood_down_slash_05", "HEAD_chain_hood_down_slash_06",

            "HEAD_chain_hood_left_thrust_01", "HEAD_chain_hood_left_thrust_02", "HEAD_chain_hood_left_thrust_03", "HEAD_chain_hood_left_thrust_04", "HEAD_chain_hood_left_thrust_05", "HEAD_chain_hood_left_thrust_06", "HEAD_chain_hood_left_thrust_07", "HEAD_chain_hood_left_thrust_08",
            "HEAD_chain_hood_right_thrust_01", "HEAD_chain_hood_right_thrust_02", "HEAD_chain_hood_right_thrust_03", "HEAD_chain_hood_right_thrust_04", "HEAD_chain_hood_right_thrust_05", "HEAD_chain_hood_right_thrust_06", "HEAD_chain_hood_right_thrust_07", "HEAD_chain_hood_right_thrust_08",
            "HEAD_chain_hood_up_thrust_01", "HEAD_chain_hood_up_thrust_02", "HEAD_chain_hood_up_thrust_03", "HEAD_chain_hood_up_thrust_04", "HEAD_chain_hood_up_thrust_05", "HEAD_chain_hood_up_thrust_06", "HEAD_chain_hood_up_thrust_07", "HEAD_chain_hood_up_thrust_08",
            "HEAD_chain_hood_down_thrust_01", "HEAD_chain_hood_down_thrust_02", "HEAD_chain_hood_down_thrust_03", "HEAD_chain_hood_down_thrust_04", "HEAD_chain_hood_down_thrust_05", "HEAD_chain_hood_down_thrust_06", "HEAD_chain_hood_down_thrust_07", "HEAD_chain_hood_down_thrust_08",

            "HEAD_chain_hood_hurt_01", "HEAD_chain_hood_hurt_02", "HEAD_chain_hood_hurt_03", "HEAD_chain_hood_hurt_04", "HEAD_chain_hood_hurt_05", "HEAD_chain_hood_hurt_06",

            "tavern", "forge",

            "HEAD_plate_helmet_left_move_01", "HEAD_plate_helmet_left_move_02", "HEAD_plate_helmet_left_move_03", "HEAD_plate_helmet_left_move_04", "HEAD_plate_helmet_left_move_05", "HEAD_plate_helmet_left_move_06", "HEAD_plate_helmet_left_move_07", "HEAD_plate_helmet_left_move_08", "HEAD_plate_helmet_left_move_09",
            "HEAD_plate_helmet_right_move_01", "HEAD_plate_helmet_right_move_02", "HEAD_plate_helmet_right_move_03", "HEAD_plate_helmet_right_move_04", "HEAD_plate_helmet_right_move_05", "HEAD_plate_helmet_right_move_06", "HEAD_plate_helmet_right_move_07", "HEAD_plate_helmet_right_move_08", "HEAD_plate_helmet_right_move_09",
            "HEAD_plate_helmet_up_move_01", "HEAD_plate_helmet_up_move_02", "HEAD_plate_helmet_up_move_03", "HEAD_plate_helmet_up_move_04", "HEAD_plate_helmet_up_move_05", "HEAD_plate_helmet_up_move_06", "HEAD_plate_helmet_up_move_07", "HEAD_plate_helmet_up_move_08", "HEAD_plate_helmet_up_move_09",
            "HEAD_plate_helmet_down_move_01", "HEAD_plate_helmet_down_move_02", "HEAD_plate_helmet_down_move_03", "HEAD_plate_helmet_down_move_04", "HEAD_plate_helmet_down_move_05", "HEAD_plate_helmet_down_move_06", "HEAD_plate_helmet_down_move_07", "HEAD_plate_helmet_down_move_08", "HEAD_plate_helmet_down_move_09",

            "HEAD_plate_helmet_left_slash_01", "HEAD_plate_helmet_left_slash_02", "HEAD_plate_helmet_left_slash_03", "HEAD_plate_helmet_left_slash_04", "HEAD_plate_helmet_left_slash_05", "HEAD_plate_helmet_left_slash_06",
            "HEAD_plate_helmet_right_slash_01", "HEAD_plate_helmet_right_slash_02", "HEAD_plate_helmet_right_slash_03", "HEAD_plate_helmet_right_slash_04", "HEAD_plate_helmet_right_slash_05", "HEAD_plate_helmet_right_slash_06",
            "HEAD_plate_helmet_up_slash_01", "HEAD_plate_helmet_up_slash_02", "HEAD_plate_helmet_up_slash_03", "HEAD_plate_helmet_up_slash_04", "HEAD_plate_helmet_up_slash_05", "HEAD_plate_helmet_up_slash_06",
            "HEAD_plate_helmet_down_slash_01", "HEAD_plate_helmet_down_slash_02", "HEAD_plate_helmet_down_slash_03", "HEAD_plate_helmet_down_slash_04", "HEAD_plate_helmet_down_slash_05", "HEAD_plate_helmet_down_slash_06",

            "HEAD_plate_helmet_left_thrust_01", "HEAD_plate_helmet_left_thrust_02", "HEAD_plate_helmet_left_thrust_03", "HEAD_plate_helmet_left_thrust_04", "HEAD_plate_helmet_left_thrust_05", "HEAD_plate_helmet_left_thrust_06", "HEAD_plate_helmet_left_thrust_07", "HEAD_plate_helmet_left_thrust_08",
            "HEAD_plate_helmet_right_thrust_01", "HEAD_plate_helmet_right_thrust_02", "HEAD_plate_helmet_right_thrust_03", "HEAD_plate_helmet_right_thrust_04", "HEAD_plate_helmet_right_thrust_05", "HEAD_plate_helmet_right_thrust_06", "HEAD_plate_helmet_right_thrust_07", "HEAD_plate_helmet_right_thrust_08",
            "HEAD_plate_helmet_up_thrust_01", "HEAD_plate_helmet_up_thrust_02", "HEAD_plate_helmet_up_thrust_03", "HEAD_plate_helmet_up_thrust_04", "HEAD_plate_helmet_up_thrust_05", "HEAD_plate_helmet_up_thrust_06", "HEAD_plate_helmet_up_thrust_07", "HEAD_plate_helmet_up_thrust_08",
            "HEAD_plate_helmet_down_thrust_01", "HEAD_plate_helmet_down_thrust_02", "HEAD_plate_helmet_down_thrust_03", "HEAD_plate_helmet_down_thrust_04", "HEAD_plate_helmet_down_thrust_05", "HEAD_plate_helmet_down_thrust_06", "HEAD_plate_helmet_down_thrust_07", "HEAD_plate_helmet_down_thrust_08",

            "HEAD_plate_helmet_hurt_01", "HEAD_plate_helmet_hurt_02", "HEAD_plate_helmet_hurt_03", "HEAD_plate_helmet_hurt_04", "HEAD_plate_helmet_hurt_05", "HEAD_plate_helmet_hurt_06",

            "SHOULDERS_leather_shoulderPads_left_move_01", "SHOULDERS_leather_shoulderPads_left_move_02", "SHOULDERS_leather_shoulderPads_left_move_03", "SHOULDERS_leather_shoulderPads_left_move_04", "SHOULDERS_leather_shoulderPads_left_move_05", "SHOULDERS_leather_shoulderPads_left_move_06", "SHOULDERS_leather_shoulderPads_left_move_07", "SHOULDERS_leather_shoulderPads_left_move_08", "SHOULDERS_leather_shoulderPads_left_move_09",
            "SHOULDERS_leather_shoulderPads_right_move_01", "SHOULDERS_leather_shoulderPads_right_move_02", "SHOULDERS_leather_shoulderPads_right_move_03", "SHOULDERS_leather_shoulderPads_right_move_04", "SHOULDERS_leather_shoulderPads_right_move_05", "SHOULDERS_leather_shoulderPads_right_move_06", "SHOULDERS_leather_shoulderPads_right_move_07", "SHOULDERS_leather_shoulderPads_right_move_08", "SHOULDERS_leather_shoulderPads_right_move_09",
            "SHOULDERS_leather_shoulderPads_up_move_01", "SHOULDERS_leather_shoulderPads_up_move_02", "SHOULDERS_leather_shoulderPads_up_move_03", "SHOULDERS_leather_shoulderPads_up_move_04", "SHOULDERS_leather_shoulderPads_up_move_05", "SHOULDERS_leather_shoulderPads_up_move_06", "SHOULDERS_leather_shoulderPads_up_move_07", "SHOULDERS_leather_shoulderPads_up_move_08", "SHOULDERS_leather_shoulderPads_up_move_09",
            "SHOULDERS_leather_shoulderPads_down_move_01", "SHOULDERS_leather_shoulderPads_down_move_02", "SHOULDERS_leather_shoulderPads_down_move_03", "SHOULDERS_leather_shoulderPads_down_move_04", "SHOULDERS_leather_shoulderPads_down_move_05", "SHOULDERS_leather_shoulderPads_down_move_06", "SHOULDERS_leather_shoulderPads_down_move_07", "SHOULDERS_leather_shoulderPads_down_move_08", "SHOULDERS_leather_shoulderPads_down_move_09",

            "SHOULDERS_leather_shoulderPads_left_slash_01", "SHOULDERS_leather_shoulderPads_left_slash_02", "SHOULDERS_leather_shoulderPads_left_slash_03", "SHOULDERS_leather_shoulderPads_left_slash_04", "SHOULDERS_leather_shoulderPads_left_slash_05", "SHOULDERS_leather_shoulderPads_left_slash_06",
            "SHOULDERS_leather_shoulderPads_right_slash_01", "SHOULDERS_leather_shoulderPads_right_slash_02", "SHOULDERS_leather_shoulderPads_right_slash_03", "SHOULDERS_leather_shoulderPads_right_slash_04", "SHOULDERS_leather_shoulderPads_right_slash_05", "SHOULDERS_leather_shoulderPads_right_slash_06",
            "SHOULDERS_leather_shoulderPads_up_slash_01", "SHOULDERS_leather_shoulderPads_up_slash_02", "SHOULDERS_leather_shoulderPads_up_slash_03", "SHOULDERS_leather_shoulderPads_up_slash_04", "SHOULDERS_leather_shoulderPads_up_slash_05", "SHOULDERS_leather_shoulderPads_up_slash_06",
            "SHOULDERS_leather_shoulderPads_down_slash_01", "SHOULDERS_leather_shoulderPads_down_slash_02", "SHOULDERS_leather_shoulderPads_down_slash_03", "SHOULDERS_leather_shoulderPads_down_slash_04", "SHOULDERS_leather_shoulderPads_down_slash_05", "SHOULDERS_leather_shoulderPads_down_slash_06",

            "SHOULDERS_leather_shoulderPads_left_thrust_01", "SHOULDERS_leather_shoulderPads_left_thrust_02", "SHOULDERS_leather_shoulderPads_left_thrust_03", "SHOULDERS_leather_shoulderPads_left_thrust_04", "SHOULDERS_leather_shoulderPads_left_thrust_05", "SHOULDERS_leather_shoulderPads_left_thrust_06", "SHOULDERS_leather_shoulderPads_left_thrust_07", "SHOULDERS_leather_shoulderPads_left_thrust_08",
            "SHOULDERS_leather_shoulderPads_right_thrust_01", "SHOULDERS_leather_shoulderPads_right_thrust_02", "SHOULDERS_leather_shoulderPads_right_thrust_03", "SHOULDERS_leather_shoulderPads_right_thrust_04", "SHOULDERS_leather_shoulderPads_right_thrust_05", "SHOULDERS_leather_shoulderPads_right_thrust_06", "SHOULDERS_leather_shoulderPads_right_thrust_07", "SHOULDERS_leather_shoulderPads_right_thrust_08",
            "SHOULDERS_leather_shoulderPads_up_thrust_01", "SHOULDERS_leather_shoulderPads_up_thrust_02", "SHOULDERS_leather_shoulderPads_up_thrust_03", "SHOULDERS_leather_shoulderPads_up_thrust_04", "SHOULDERS_leather_shoulderPads_up_thrust_05", "SHOULDERS_leather_shoulderPads_up_thrust_06", "SHOULDERS_leather_shoulderPads_up_thrust_07", "SHOULDERS_leather_shoulderPads_up_thrust_08",
            "SHOULDERS_leather_shoulderPads_down_thrust_01", "SHOULDERS_leather_shoulderPads_down_thrust_02", "SHOULDERS_leather_shoulderPads_down_thrust_03", "SHOULDERS_leather_shoulderPads_down_thrust_04", "SHOULDERS_leather_shoulderPads_down_thrust_05", "SHOULDERS_leather_shoulderPads_down_thrust_06", "SHOULDERS_leather_shoulderPads_down_thrust_07", "SHOULDERS_leather_shoulderPads_down_thrust_08",

            "SHOULDERS_leather_shoulderPads_hurt_01", "SHOULDERS_leather_shoulderPads_hurt_02", "SHOULDERS_leather_shoulderPads_hurt_03", "SHOULDERS_leather_shoulderPads_hurt_04", "SHOULDERS_leather_shoulderPads_hurt_05", "SHOULDERS_leather_shoulderPads_hurt_06",

            "SHOULDERS_plate_shoulderPads_left_move_01", "SHOULDERS_plate_shoulderPads_left_move_02", "SHOULDERS_plate_shoulderPads_left_move_03", "SHOULDERS_plate_shoulderPads_left_move_04", "SHOULDERS_plate_shoulderPads_left_move_05", "SHOULDERS_plate_shoulderPads_left_move_06", "SHOULDERS_plate_shoulderPads_left_move_07", "SHOULDERS_plate_shoulderPads_left_move_08", "SHOULDERS_plate_shoulderPads_left_move_09",
            "SHOULDERS_plate_shoulderPads_right_move_01", "SHOULDERS_plate_shoulderPads_right_move_02", "SHOULDERS_plate_shoulderPads_right_move_03", "SHOULDERS_plate_shoulderPads_right_move_04", "SHOULDERS_plate_shoulderPads_right_move_05", "SHOULDERS_plate_shoulderPads_right_move_06", "SHOULDERS_plate_shoulderPads_right_move_07", "SHOULDERS_plate_shoulderPads_right_move_08", "SHOULDERS_plate_shoulderPads_right_move_09",
            "SHOULDERS_plate_shoulderPads_up_move_01", "SHOULDERS_plate_shoulderPads_up_move_02", "SHOULDERS_plate_shoulderPads_up_move_03", "SHOULDERS_plate_shoulderPads_up_move_04", "SHOULDERS_plate_shoulderPads_up_move_05", "SHOULDERS_plate_shoulderPads_up_move_06", "SHOULDERS_plate_shoulderPads_up_move_07", "SHOULDERS_plate_shoulderPads_up_move_08", "SHOULDERS_plate_shoulderPads_up_move_09",
            "SHOULDERS_plate_shoulderPads_down_move_01", "SHOULDERS_plate_shoulderPads_down_move_02", "SHOULDERS_plate_shoulderPads_down_move_03", "SHOULDERS_plate_shoulderPads_down_move_04", "SHOULDERS_plate_shoulderPads_down_move_05", "SHOULDERS_plate_shoulderPads_down_move_06", "SHOULDERS_plate_shoulderPads_down_move_07", "SHOULDERS_plate_shoulderPads_down_move_08", "SHOULDERS_plate_shoulderPads_down_move_09",

            "SHOULDERS_plate_shoulderPads_left_slash_01", "SHOULDERS_plate_shoulderPads_left_slash_02", "SHOULDERS_plate_shoulderPads_left_slash_03", "SHOULDERS_plate_shoulderPads_left_slash_04", "SHOULDERS_plate_shoulderPads_left_slash_05", "SHOULDERS_plate_shoulderPads_left_slash_06",
            "SHOULDERS_plate_shoulderPads_right_slash_01", "SHOULDERS_plate_shoulderPads_right_slash_02", "SHOULDERS_plate_shoulderPads_right_slash_03", "SHOULDERS_plate_shoulderPads_right_slash_04", "SHOULDERS_plate_shoulderPads_right_slash_05", "SHOULDERS_plate_shoulderPads_right_slash_06",
            "SHOULDERS_plate_shoulderPads_up_slash_01", "SHOULDERS_plate_shoulderPads_up_slash_02", "SHOULDERS_plate_shoulderPads_up_slash_03", "SHOULDERS_plate_shoulderPads_up_slash_04", "SHOULDERS_plate_shoulderPads_up_slash_05", "SHOULDERS_plate_shoulderPads_up_slash_06",
            "SHOULDERS_plate_shoulderPads_down_slash_01", "SHOULDERS_plate_shoulderPads_down_slash_02", "SHOULDERS_plate_shoulderPads_down_slash_03", "SHOULDERS_plate_shoulderPads_down_slash_04", "SHOULDERS_plate_shoulderPads_down_slash_05", "SHOULDERS_plate_shoulderPads_down_slash_06",

            "SHOULDERS_plate_shoulderPads_left_thrust_01", "SHOULDERS_plate_shoulderPads_left_thrust_02", "SHOULDERS_plate_shoulderPads_left_thrust_03", "SHOULDERS_plate_shoulderPads_left_thrust_04", "SHOULDERS_plate_shoulderPads_left_thrust_05", "SHOULDERS_plate_shoulderPads_left_thrust_06", "SHOULDERS_plate_shoulderPads_left_thrust_07", "SHOULDERS_plate_shoulderPads_left_thrust_08",
            "SHOULDERS_plate_shoulderPads_right_thrust_01", "SHOULDERS_plate_shoulderPads_right_thrust_02", "SHOULDERS_plate_shoulderPads_right_thrust_03", "SHOULDERS_plate_shoulderPads_right_thrust_04", "SHOULDERS_plate_shoulderPads_right_thrust_05", "SHOULDERS_plate_shoulderPads_right_thrust_06", "SHOULDERS_plate_shoulderPads_right_thrust_07", "SHOULDERS_plate_shoulderPads_right_thrust_08",
            "SHOULDERS_plate_shoulderPads_up_thrust_01", "SHOULDERS_plate_shoulderPads_up_thrust_02", "SHOULDERS_plate_shoulderPads_up_thrust_03", "SHOULDERS_plate_shoulderPads_up_thrust_04", "SHOULDERS_plate_shoulderPads_up_thrust_05", "SHOULDERS_plate_shoulderPads_up_thrust_06", "SHOULDERS_plate_shoulderPads_up_thrust_07", "SHOULDERS_plate_shoulderPads_up_thrust_08",
            "SHOULDERS_plate_shoulderPads_down_thrust_01", "SHOULDERS_plate_shoulderPads_down_thrust_02", "SHOULDERS_plate_shoulderPads_down_thrust_03", "SHOULDERS_plate_shoulderPads_down_thrust_04", "SHOULDERS_plate_shoulderPads_down_thrust_05", "SHOULDERS_plate_shoulderPads_down_thrust_06", "SHOULDERS_plate_shoulderPads_down_thrust_07", "SHOULDERS_plate_shoulderPads_down_thrust_08",

            "SHOULDERS_plate_shoulderPads_hurt_01", "SHOULDERS_plate_shoulderPads_hurt_02", "SHOULDERS_plate_shoulderPads_hurt_03", "SHOULDERS_plate_shoulderPads_hurt_04", "SHOULDERS_plate_shoulderPads_hurt_05", "SHOULDERS_plate_shoulderPads_hurt_06",

            "TORSO_shirt_white_left_move_01", "TORSO_shirt_white_left_move_02", "TORSO_shirt_white_left_move_03", "TORSO_shirt_white_left_move_04", "TORSO_shirt_white_left_move_05", "TORSO_shirt_white_left_move_06", "TORSO_shirt_white_left_move_07", "TORSO_shirt_white_left_move_08", "TORSO_shirt_white_left_move_09",
            "TORSO_shirt_white_right_move_01", "TORSO_shirt_white_right_move_02", "TORSO_shirt_white_right_move_03", "TORSO_shirt_white_right_move_04", "TORSO_shirt_white_right_move_05", "TORSO_shirt_white_right_move_06", "TORSO_shirt_white_right_move_07", "TORSO_shirt_white_right_move_08", "TORSO_shirt_white_right_move_09",
            "TORSO_shirt_white_up_move_01", "TORSO_shirt_white_up_move_02", "TORSO_shirt_white_up_move_03", "TORSO_shirt_white_up_move_04", "TORSO_shirt_white_up_move_05", "TORSO_shirt_white_up_move_06", "TORSO_shirt_white_up_move_07", "TORSO_shirt_white_up_move_08", "TORSO_shirt_white_up_move_09",
            "TORSO_shirt_white_down_move_01", "TORSO_shirt_white_down_move_02", "TORSO_shirt_white_down_move_03", "TORSO_shirt_white_down_move_04", "TORSO_shirt_white_down_move_05", "TORSO_shirt_white_down_move_06", "TORSO_shirt_white_down_move_07", "TORSO_shirt_white_down_move_08", "TORSO_shirt_white_down_move_09",

            "TORSO_shirt_white_left_slash_01", "TORSO_shirt_white_left_slash_02", "TORSO_shirt_white_left_slash_03", "TORSO_shirt_white_left_slash_04", "TORSO_shirt_white_left_slash_05", "TORSO_shirt_white_left_slash_06",
            "TORSO_shirt_white_right_slash_01", "TORSO_shirt_white_right_slash_02", "TORSO_shirt_white_right_slash_03", "TORSO_shirt_white_right_slash_04", "TORSO_shirt_white_right_slash_05", "TORSO_shirt_white_right_slash_06",
            "TORSO_shirt_white_up_slash_01", "TORSO_shirt_white_up_slash_02", "TORSO_shirt_white_up_slash_03", "TORSO_shirt_white_up_slash_04", "TORSO_shirt_white_up_slash_05", "TORSO_shirt_white_up_slash_06",
            "TORSO_shirt_white_down_slash_01", "TORSO_shirt_white_down_slash_02", "TORSO_shirt_white_down_slash_03", "TORSO_shirt_white_down_slash_04", "TORSO_shirt_white_down_slash_05", "TORSO_shirt_white_down_slash_06",

            "TORSO_shirt_white_left_thrust_01", "TORSO_shirt_white_left_thrust_02", "TORSO_shirt_white_left_thrust_03", "TORSO_shirt_white_left_thrust_04", "TORSO_shirt_white_left_thrust_05", "TORSO_shirt_white_left_thrust_06", "TORSO_shirt_white_left_thrust_07", "TORSO_shirt_white_left_thrust_08",
            "TORSO_shirt_white_right_thrust_01", "TORSO_shirt_white_right_thrust_02", "TORSO_shirt_white_right_thrust_03", "TORSO_shirt_white_right_thrust_04", "TORSO_shirt_white_right_thrust_05", "TORSO_shirt_white_right_thrust_06", "TORSO_shirt_white_right_thrust_07", "TORSO_shirt_white_right_thrust_08",
            "TORSO_shirt_white_up_thrust_01", "TORSO_shirt_white_up_thrust_02", "TORSO_shirt_white_up_thrust_03", "TORSO_shirt_white_up_thrust_04", "TORSO_shirt_white_up_thrust_05", "TORSO_shirt_white_up_thrust_06", "TORSO_shirt_white_up_thrust_07", "TORSO_shirt_white_up_thrust_08",
            "TORSO_shirt_white_down_thrust_01", "TORSO_shirt_white_down_thrust_02", "TORSO_shirt_white_down_thrust_03", "TORSO_shirt_white_down_thrust_04", "TORSO_shirt_white_down_thrust_05", "TORSO_shirt_white_down_thrust_06", "TORSO_shirt_white_down_thrust_07", "TORSO_shirt_white_down_thrust_08",

            "TORSO_shirt_white_hurt_01", "TORSO_shirt_white_hurt_02", "TORSO_shirt_white_hurt_03", "TORSO_shirt_white_hurt_04", "TORSO_shirt_white_hurt_05", "TORSO_shirt_white_hurt_06",

            "TORSO_robe_shirt_brown_left_move_01", "TORSO_robe_shirt_brown_left_move_02", "TORSO_robe_shirt_brown_left_move_03", "TORSO_robe_shirt_brown_left_move_04", "TORSO_robe_shirt_brown_left_move_05", "TORSO_robe_shirt_brown_left_move_06", "TORSO_robe_shirt_brown_left_move_07", "TORSO_robe_shirt_brown_left_move_08", "TORSO_robe_shirt_brown_left_move_09",
            "TORSO_robe_shirt_brown_right_move_01", "TORSO_robe_shirt_brown_right_move_02", "TORSO_robe_shirt_brown_right_move_03", "TORSO_robe_shirt_brown_right_move_04", "TORSO_robe_shirt_brown_right_move_05", "TORSO_robe_shirt_brown_right_move_06", "TORSO_robe_shirt_brown_right_move_07", "TORSO_robe_shirt_brown_right_move_08", "TORSO_robe_shirt_brown_right_move_09",
            "TORSO_robe_shirt_brown_up_move_01", "TORSO_robe_shirt_brown_up_move_02", "TORSO_robe_shirt_brown_up_move_03", "TORSO_robe_shirt_brown_up_move_04", "TORSO_robe_shirt_brown_up_move_05", "TORSO_robe_shirt_brown_up_move_06", "TORSO_robe_shirt_brown_up_move_07", "TORSO_robe_shirt_brown_up_move_08", "TORSO_robe_shirt_brown_up_move_09",
            "TORSO_robe_shirt_brown_down_move_01", "TORSO_robe_shirt_brown_down_move_02", "TORSO_robe_shirt_brown_down_move_03", "TORSO_robe_shirt_brown_down_move_04", "TORSO_robe_shirt_brown_down_move_05", "TORSO_robe_shirt_brown_down_move_06", "TORSO_robe_shirt_brown_down_move_07", "TORSO_robe_shirt_brown_down_move_08", "TORSO_robe_shirt_brown_down_move_09",

            "TORSO_robe_shirt_brown_left_slash_01", "TORSO_robe_shirt_brown_left_slash_02", "TORSO_robe_shirt_brown_left_slash_03", "TORSO_robe_shirt_brown_left_slash_04", "TORSO_robe_shirt_brown_left_slash_05", "TORSO_robe_shirt_brown_left_slash_06",
            "TORSO_robe_shirt_brown_right_slash_01", "TORSO_robe_shirt_brown_right_slash_02", "TORSO_robe_shirt_brown_right_slash_03", "TORSO_robe_shirt_brown_right_slash_04", "TORSO_robe_shirt_brown_right_slash_05", "TORSO_robe_shirt_brown_right_slash_06",
            "TORSO_robe_shirt_brown_up_slash_01", "TORSO_robe_shirt_brown_up_slash_02", "TORSO_robe_shirt_brown_up_slash_03", "TORSO_robe_shirt_brown_up_slash_04", "TORSO_robe_shirt_brown_up_slash_05", "TORSO_robe_shirt_brown_up_slash_06",
            "TORSO_robe_shirt_brown_down_slash_01", "TORSO_robe_shirt_brown_down_slash_02", "TORSO_robe_shirt_brown_down_slash_03", "TORSO_robe_shirt_brown_down_slash_04", "TORSO_robe_shirt_brown_down_slash_05", "TORSO_robe_shirt_brown_down_slash_06",

            "TORSO_robe_shirt_brown_left_thrust_01", "TORSO_robe_shirt_brown_left_thrust_02", "TORSO_robe_shirt_brown_left_thrust_03", "TORSO_robe_shirt_brown_left_thrust_04", "TORSO_robe_shirt_brown_left_thrust_05", "TORSO_robe_shirt_brown_left_thrust_06", "TORSO_robe_shirt_brown_left_thrust_07", "TORSO_robe_shirt_brown_left_thrust_08",
            "TORSO_robe_shirt_brown_right_thrust_01", "TORSO_robe_shirt_brown_right_thrust_02", "TORSO_robe_shirt_brown_right_thrust_03", "TORSO_robe_shirt_brown_right_thrust_04", "TORSO_robe_shirt_brown_right_thrust_05", "TORSO_robe_shirt_brown_right_thrust_06", "TORSO_robe_shirt_brown_right_thrust_07", "TORSO_robe_shirt_brown_right_thrust_08",
            "TORSO_robe_shirt_brown_up_thrust_01", "TORSO_robe_shirt_brown_up_thrust_02", "TORSO_robe_shirt_brown_up_thrust_03", "TORSO_robe_shirt_brown_up_thrust_04", "TORSO_robe_shirt_brown_up_thrust_05", "TORSO_robe_shirt_brown_up_thrust_06", "TORSO_robe_shirt_brown_up_thrust_07", "TORSO_robe_shirt_brown_up_thrust_08",
            "TORSO_robe_shirt_brown_down_thrust_01", "TORSO_robe_shirt_brown_down_thrust_02", "TORSO_robe_shirt_brown_down_thrust_03", "TORSO_robe_shirt_brown_down_thrust_04", "TORSO_robe_shirt_brown_down_thrust_05", "TORSO_robe_shirt_brown_down_thrust_06", "TORSO_robe_shirt_brown_down_thrust_07", "TORSO_robe_shirt_brown_down_thrust_08",

            "TORSO_robe_shirt_brown_hurt_01", "TORSO_robe_shirt_brown_hurt_02", "TORSO_robe_shirt_brown_hurt_03", "TORSO_robe_shirt_brown_hurt_04", "TORSO_robe_shirt_brown_hurt_05", "TORSO_robe_shirt_brown_hurt_06",

            "TORSO_leather_armor_left_move_01", "TORSO_leather_armor_left_move_02", "TORSO_leather_armor_left_move_03", "TORSO_leather_armor_left_move_04", "TORSO_leather_armor_left_move_05", "TORSO_leather_armor_left_move_06", "TORSO_leather_armor_left_move_07", "TORSO_leather_armor_left_move_08", "TORSO_leather_armor_left_move_09",
            "TORSO_leather_armor_right_move_01", "TORSO_leather_armor_right_move_02", "TORSO_leather_armor_right_move_03", "TORSO_leather_armor_right_move_04", "TORSO_leather_armor_right_move_05", "TORSO_leather_armor_right_move_06", "TORSO_leather_armor_right_move_07", "TORSO_leather_armor_right_move_08", "TORSO_leather_armor_right_move_09",
            "TORSO_leather_armor_up_move_01", "TORSO_leather_armor_up_move_02", "TORSO_leather_armor_up_move_03", "TORSO_leather_armor_up_move_04", "TORSO_leather_armor_up_move_05", "TORSO_leather_armor_up_move_06", "TORSO_leather_armor_up_move_07", "TORSO_leather_armor_up_move_08", "TORSO_leather_armor_up_move_09",
            "TORSO_leather_armor_down_move_01", "TORSO_leather_armor_down_move_02", "TORSO_leather_armor_down_move_03", "TORSO_leather_armor_down_move_04", "TORSO_leather_armor_down_move_05", "TORSO_leather_armor_down_move_06", "TORSO_leather_armor_down_move_07", "TORSO_leather_armor_down_move_08", "TORSO_leather_armor_down_move_09",

            "TORSO_leather_armor_left_slash_01", "TORSO_leather_armor_left_slash_02", "TORSO_leather_armor_left_slash_03", "TORSO_leather_armor_left_slash_04", "TORSO_leather_armor_left_slash_05", "TORSO_leather_armor_left_slash_06",
            "TORSO_leather_armor_right_slash_01", "TORSO_leather_armor_right_slash_02", "TORSO_leather_armor_right_slash_03", "TORSO_leather_armor_right_slash_04", "TORSO_leather_armor_right_slash_05", "TORSO_leather_armor_right_slash_06",
            "TORSO_leather_armor_up_slash_01", "TORSO_leather_armor_up_slash_02", "TORSO_leather_armor_up_slash_03", "TORSO_leather_armor_up_slash_04", "TORSO_leather_armor_up_slash_05", "TORSO_leather_armor_up_slash_06",
            "TORSO_leather_armor_down_slash_01", "TORSO_leather_armor_down_slash_02", "TORSO_leather_armor_down_slash_03", "TORSO_leather_armor_down_slash_04", "TORSO_leather_armor_down_slash_05", "TORSO_leather_armor_down_slash_06",

            "TORSO_leather_armor_left_thrust_01", "TORSO_leather_armor_left_thrust_02", "TORSO_leather_armor_left_thrust_03", "TORSO_leather_armor_left_thrust_04", "TORSO_leather_armor_left_thrust_05", "TORSO_leather_armor_left_thrust_06", "TORSO_leather_armor_left_thrust_07", "TORSO_leather_armor_left_thrust_08",
            "TORSO_leather_armor_right_thrust_01", "TORSO_leather_armor_right_thrust_02", "TORSO_leather_armor_right_thrust_03", "TORSO_leather_armor_right_thrust_04", "TORSO_leather_armor_right_thrust_05", "TORSO_leather_armor_right_thrust_06", "TORSO_leather_armor_right_thrust_07", "TORSO_leather_armor_right_thrust_08",
            "TORSO_leather_armor_up_thrust_01", "TORSO_leather_armor_up_thrust_02", "TORSO_leather_armor_up_thrust_03", "TORSO_leather_armor_up_thrust_04", "TORSO_leather_armor_up_thrust_05", "TORSO_leather_armor_up_thrust_06", "TORSO_leather_armor_up_thrust_07", "TORSO_leather_armor_up_thrust_08",
            "TORSO_leather_armor_down_thrust_01", "TORSO_leather_armor_down_thrust_02", "TORSO_leather_armor_down_thrust_03", "TORSO_leather_armor_down_thrust_04", "TORSO_leather_armor_down_thrust_05", "TORSO_leather_armor_down_thrust_06", "TORSO_leather_armor_down_thrust_07", "TORSO_leather_armor_down_thrust_08",

            "TORSO_leather_armor_hurt_01", "TORSO_leather_armor_hurt_02", "TORSO_leather_armor_hurt_03", "TORSO_leather_armor_hurt_04", "TORSO_leather_armor_hurt_05", "TORSO_leather_armor_hurt_06",

            "TORSO_chain_jacket_purple_left_move_01", "TORSO_chain_jacket_purple_left_move_02", "TORSO_chain_jacket_purple_left_move_03", "TORSO_chain_jacket_purple_left_move_04", "TORSO_chain_jacket_purple_left_move_05", "TORSO_chain_jacket_purple_left_move_06", "TORSO_chain_jacket_purple_left_move_07", "TORSO_chain_jacket_purple_left_move_08", "TORSO_chain_jacket_purple_left_move_09",
            "TORSO_chain_jacket_purple_right_move_01", "TORSO_chain_jacket_purple_right_move_02", "TORSO_chain_jacket_purple_right_move_03", "TORSO_chain_jacket_purple_right_move_04", "TORSO_chain_jacket_purple_right_move_05", "TORSO_chain_jacket_purple_right_move_06", "TORSO_chain_jacket_purple_right_move_07", "TORSO_chain_jacket_purple_right_move_08", "TORSO_chain_jacket_purple_right_move_09",
            "TORSO_chain_jacket_purple_up_move_01", "TORSO_chain_jacket_purple_up_move_02", "TORSO_chain_jacket_purple_up_move_03", "TORSO_chain_jacket_purple_up_move_04", "TORSO_chain_jacket_purple_up_move_05", "TORSO_chain_jacket_purple_up_move_06", "TORSO_chain_jacket_purple_up_move_07", "TORSO_chain_jacket_purple_up_move_08", "TORSO_chain_jacket_purple_up_move_09",
            "TORSO_chain_jacket_purple_down_move_01", "TORSO_chain_jacket_purple_down_move_02", "TORSO_chain_jacket_purple_down_move_03", "TORSO_chain_jacket_purple_down_move_04", "TORSO_chain_jacket_purple_down_move_05", "TORSO_chain_jacket_purple_down_move_06", "TORSO_chain_jacket_purple_down_move_07", "TORSO_chain_jacket_purple_down_move_08", "TORSO_chain_jacket_purple_down_move_09",

            "TORSO_chain_jacket_purple_left_slash_01", "TORSO_chain_jacket_purple_left_slash_02", "TORSO_chain_jacket_purple_left_slash_03", "TORSO_chain_jacket_purple_left_slash_04", "TORSO_chain_jacket_purple_left_slash_05", "TORSO_chain_jacket_purple_left_slash_06",
            "TORSO_chain_jacket_purple_right_slash_01", "TORSO_chain_jacket_purple_right_slash_02", "TORSO_chain_jacket_purple_right_slash_03", "TORSO_chain_jacket_purple_right_slash_04", "TORSO_chain_jacket_purple_right_slash_05", "TORSO_chain_jacket_purple_right_slash_06",
            "TORSO_chain_jacket_purple_up_slash_01", "TORSO_chain_jacket_purple_up_slash_02", "TORSO_chain_jacket_purple_up_slash_03", "TORSO_chain_jacket_purple_up_slash_04", "TORSO_chain_jacket_purple_up_slash_05", "TORSO_chain_jacket_purple_up_slash_06",
            "TORSO_chain_jacket_purple_down_slash_01", "TORSO_chain_jacket_purple_down_slash_02", "TORSO_chain_jacket_purple_down_slash_03", "TORSO_chain_jacket_purple_down_slash_04", "TORSO_chain_jacket_purple_down_slash_05", "TORSO_chain_jacket_purple_down_slash_06",

            "TORSO_chain_jacket_purple_left_thrust_01", "TORSO_chain_jacket_purple_left_thrust_02", "TORSO_chain_jacket_purple_left_thrust_03", "TORSO_chain_jacket_purple_left_thrust_04", "TORSO_chain_jacket_purple_left_thrust_05", "TORSO_chain_jacket_purple_left_thrust_06", "TORSO_chain_jacket_purple_left_thrust_07", "TORSO_chain_jacket_purple_left_thrust_08",
            "TORSO_chain_jacket_purple_right_thrust_01", "TORSO_chain_jacket_purple_right_thrust_02", "TORSO_chain_jacket_purple_right_thrust_03", "TORSO_chain_jacket_purple_right_thrust_04", "TORSO_chain_jacket_purple_right_thrust_05", "TORSO_chain_jacket_purple_right_thrust_06", "TORSO_chain_jacket_purple_right_thrust_07", "TORSO_chain_jacket_purple_right_thrust_08",
            "TORSO_chain_jacket_purple_up_thrust_01", "TORSO_chain_jacket_purple_up_thrust_02", "TORSO_chain_jacket_purple_up_thrust_03", "TORSO_chain_jacket_purple_up_thrust_04", "TORSO_chain_jacket_purple_up_thrust_05", "TORSO_chain_jacket_purple_up_thrust_06", "TORSO_chain_jacket_purple_up_thrust_07", "TORSO_chain_jacket_purple_up_thrust_08",
            "TORSO_chain_jacket_purple_down_thrust_01", "TORSO_chain_jacket_purple_down_thrust_02", "TORSO_chain_jacket_purple_down_thrust_03", "TORSO_chain_jacket_purple_down_thrust_04", "TORSO_chain_jacket_purple_down_thrust_05", "TORSO_chain_jacket_purple_down_thrust_06", "TORSO_chain_jacket_purple_down_thrust_07", "TORSO_chain_jacket_purple_down_thrust_08",

            "TORSO_chain_jacket_purple_hurt_01", "TORSO_chain_jacket_purple_hurt_02", "TORSO_chain_jacket_purple_hurt_03", "TORSO_chain_jacket_purple_hurt_04", "TORSO_chain_jacket_purple_hurt_05", "TORSO_chain_jacket_purple_hurt_06",

            "TORSO_chain_armor_left_move_01", "TORSO_chain_armor_left_move_02", "TORSO_chain_armor_left_move_03", "TORSO_chain_armor_left_move_04", "TORSO_chain_armor_left_move_05", "TORSO_chain_armor_left_move_06", "TORSO_chain_armor_left_move_07", "TORSO_chain_armor_left_move_08", "TORSO_chain_armor_left_move_09",
            "TORSO_chain_armor_right_move_01", "TORSO_chain_armor_right_move_02", "TORSO_chain_armor_right_move_03", "TORSO_chain_armor_right_move_04", "TORSO_chain_armor_right_move_05", "TORSO_chain_armor_right_move_06", "TORSO_chain_armor_right_move_07", "TORSO_chain_armor_right_move_08", "TORSO_chain_armor_right_move_09",
            "TORSO_chain_armor_up_move_01", "TORSO_chain_armor_up_move_02", "TORSO_chain_armor_up_move_03", "TORSO_chain_armor_up_move_04", "TORSO_chain_armor_up_move_05", "TORSO_chain_armor_up_move_06", "TORSO_chain_armor_up_move_07", "TORSO_chain_armor_up_move_08", "TORSO_chain_armor_up_move_09",
            "TORSO_chain_armor_down_move_01", "TORSO_chain_armor_down_move_02", "TORSO_chain_armor_down_move_03", "TORSO_chain_armor_down_move_04", "TORSO_chain_armor_down_move_05", "TORSO_chain_armor_down_move_06", "TORSO_chain_armor_down_move_07", "TORSO_chain_armor_down_move_08", "TORSO_chain_armor_down_move_09",

            "TORSO_chain_armor_left_slash_01", "TORSO_chain_armor_left_slash_02", "TORSO_chain_armor_left_slash_03", "TORSO_chain_armor_left_slash_04", "TORSO_chain_armor_left_slash_05", "TORSO_chain_armor_left_slash_06",
            "TORSO_chain_armor_right_slash_01", "TORSO_chain_armor_right_slash_02", "TORSO_chain_armor_right_slash_03", "TORSO_chain_armor_right_slash_04", "TORSO_chain_armor_right_slash_05", "TORSO_chain_armor_right_slash_06",
            "TORSO_chain_armor_up_slash_01", "TORSO_chain_armor_up_slash_02", "TORSO_chain_armor_up_slash_03", "TORSO_chain_armor_up_slash_04", "TORSO_chain_armor_up_slash_05", "TORSO_chain_armor_up_slash_06",
            "TORSO_chain_armor_down_slash_01", "TORSO_chain_armor_down_slash_02", "TORSO_chain_armor_down_slash_03", "TORSO_chain_armor_down_slash_04", "TORSO_chain_armor_down_slash_05", "TORSO_chain_armor_down_slash_06",

            "TORSO_chain_armor_left_thrust_01", "TORSO_chain_armor_left_thrust_02", "TORSO_chain_armor_left_thrust_03", "TORSO_chain_armor_left_thrust_04", "TORSO_chain_armor_left_thrust_05", "TORSO_chain_armor_left_thrust_06", "TORSO_chain_armor_left_thrust_07", "TORSO_chain_armor_left_thrust_08",
            "TORSO_chain_armor_right_thrust_01", "TORSO_chain_armor_right_thrust_02", "TORSO_chain_armor_right_thrust_03", "TORSO_chain_armor_right_thrust_04", "TORSO_chain_armor_right_thrust_05", "TORSO_chain_armor_right_thrust_06", "TORSO_chain_armor_right_thrust_07", "TORSO_chain_armor_right_thrust_08",
            "TORSO_chain_armor_up_thrust_01", "TORSO_chain_armor_up_thrust_02", "TORSO_chain_armor_up_thrust_03", "TORSO_chain_armor_up_thrust_04", "TORSO_chain_armor_up_thrust_05", "TORSO_chain_armor_up_thrust_06", "TORSO_chain_armor_up_thrust_07", "TORSO_chain_armor_up_thrust_08",
            "TORSO_chain_armor_down_thrust_01", "TORSO_chain_armor_down_thrust_02", "TORSO_chain_armor_down_thrust_03", "TORSO_chain_armor_down_thrust_04", "TORSO_chain_armor_down_thrust_05", "TORSO_chain_armor_down_thrust_06", "TORSO_chain_armor_down_thrust_07", "TORSO_chain_armor_down_thrust_08",

            "TORSO_chain_armor_hurt_01", "TORSO_chain_armor_hurt_02", "TORSO_chain_armor_hurt_03", "TORSO_chain_armor_hurt_04", "TORSO_chain_armor_hurt_05", "TORSO_chain_armor_hurt_06",

            "TORSO_plate_armor_left_move_01", "TORSO_plate_armor_left_move_02", "TORSO_plate_armor_left_move_03", "TORSO_plate_armor_left_move_04", "TORSO_plate_armor_left_move_05", "TORSO_plate_armor_left_move_06", "TORSO_plate_armor_left_move_07", "TORSO_plate_armor_left_move_08", "TORSO_plate_armor_left_move_09",
            "TORSO_plate_armor_right_move_01", "TORSO_plate_armor_right_move_02", "TORSO_plate_armor_right_move_03", "TORSO_plate_armor_right_move_04", "TORSO_plate_armor_right_move_05", "TORSO_plate_armor_right_move_06", "TORSO_plate_armor_right_move_07", "TORSO_plate_armor_right_move_08", "TORSO_plate_armor_right_move_09",
            "TORSO_plate_armor_up_move_01", "TORSO_plate_armor_up_move_02", "TORSO_plate_armor_up_move_03", "TORSO_plate_armor_up_move_04", "TORSO_plate_armor_up_move_05", "TORSO_plate_armor_up_move_06", "TORSO_plate_armor_up_move_07", "TORSO_plate_armor_up_move_08", "TORSO_plate_armor_up_move_09",
            "TORSO_plate_armor_down_move_01", "TORSO_plate_armor_down_move_02", "TORSO_plate_armor_down_move_03", "TORSO_plate_armor_down_move_04", "TORSO_plate_armor_down_move_05", "TORSO_plate_armor_down_move_06", "TORSO_plate_armor_down_move_07", "TORSO_plate_armor_down_move_08", "TORSO_plate_armor_down_move_09",

            "TORSO_plate_armor_left_slash_01", "TORSO_plate_armor_left_slash_02", "TORSO_plate_armor_left_slash_03", "TORSO_plate_armor_left_slash_04", "TORSO_plate_armor_left_slash_05", "TORSO_plate_armor_left_slash_06",
            "TORSO_plate_armor_right_slash_01", "TORSO_plate_armor_right_slash_02", "TORSO_plate_armor_right_slash_03", "TORSO_plate_armor_right_slash_04", "TORSO_plate_armor_right_slash_05", "TORSO_plate_armor_right_slash_06",
            "TORSO_plate_armor_up_slash_01", "TORSO_plate_armor_up_slash_02", "TORSO_plate_armor_up_slash_03", "TORSO_plate_armor_up_slash_04", "TORSO_plate_armor_up_slash_05", "TORSO_plate_armor_up_slash_06",
            "TORSO_plate_armor_down_slash_01", "TORSO_plate_armor_down_slash_02", "TORSO_plate_armor_down_slash_03", "TORSO_plate_armor_down_slash_04", "TORSO_plate_armor_down_slash_05", "TORSO_plate_armor_down_slash_06",

            "TORSO_plate_armor_left_thrust_01", "TORSO_plate_armor_left_thrust_02", "TORSO_plate_armor_left_thrust_03", "TORSO_plate_armor_left_thrust_04", "TORSO_plate_armor_left_thrust_05", "TORSO_plate_armor_left_thrust_06", "TORSO_plate_armor_left_thrust_07", "TORSO_plate_armor_left_thrust_08",
            "TORSO_plate_armor_right_thrust_01", "TORSO_plate_armor_right_thrust_02", "TORSO_plate_armor_right_thrust_03", "TORSO_plate_armor_right_thrust_04", "TORSO_plate_armor_right_thrust_05", "TORSO_plate_armor_right_thrust_06", "TORSO_plate_armor_right_thrust_07", "TORSO_plate_armor_right_thrust_08",
            "TORSO_plate_armor_up_thrust_01", "TORSO_plate_armor_up_thrust_02", "TORSO_plate_armor_up_thrust_03", "TORSO_plate_armor_up_thrust_04", "TORSO_plate_armor_up_thrust_05", "TORSO_plate_armor_up_thrust_06", "TORSO_plate_armor_up_thrust_07", "TORSO_plate_armor_up_thrust_08",
            "TORSO_plate_armor_down_thrust_01", "TORSO_plate_armor_down_thrust_02", "TORSO_plate_armor_down_thrust_03", "TORSO_plate_armor_down_thrust_04", "TORSO_plate_armor_down_thrust_05", "TORSO_plate_armor_down_thrust_06", "TORSO_plate_armor_down_thrust_07", "TORSO_plate_armor_down_thrust_08",

            "TORSO_plate_armor_hurt_01", "TORSO_plate_armor_hurt_02", "TORSO_plate_armor_hurt_03", "TORSO_plate_armor_hurt_04", "TORSO_plate_armor_hurt_05", "TORSO_plate_armor_hurt_06",

            "BELT_leather_left_move_01", "BELT_leather_left_move_02", "BELT_leather_left_move_03", "BELT_leather_left_move_04", "BELT_leather_left_move_05", "BELT_leather_left_move_06", "BELT_leather_left_move_07", "BELT_leather_left_move_08", "BELT_leather_left_move_09",
            "BELT_leather_right_move_01", "BELT_leather_right_move_02", "BELT_leather_right_move_03", "BELT_leather_right_move_04", "BELT_leather_right_move_05", "BELT_leather_right_move_06", "BELT_leather_right_move_07", "BELT_leather_right_move_08", "BELT_leather_right_move_09",
            "BELT_leather_up_move_01", "BELT_leather_up_move_02", "BELT_leather_up_move_03", "BELT_leather_up_move_04", "BELT_leather_up_move_05", "BELT_leather_up_move_06", "BELT_leather_up_move_07", "BELT_leather_up_move_08", "BELT_leather_up_move_09",
            "BELT_leather_down_move_01", "BELT_leather_down_move_02", "BELT_leather_down_move_03", "BELT_leather_down_move_04", "BELT_leather_down_move_05", "BELT_leather_down_move_06", "BELT_leather_down_move_07", "BELT_leather_down_move_08", "BELT_leather_down_move_09",

            "BELT_leather_left_slash_01", "BELT_leather_left_slash_02", "BELT_leather_left_slash_03", "BELT_leather_left_slash_04", "BELT_leather_left_slash_05", "BELT_leather_left_slash_06",
            "BELT_leather_right_slash_01", "BELT_leather_right_slash_02", "BELT_leather_right_slash_03", "BELT_leather_right_slash_04", "BELT_leather_right_slash_05", "BELT_leather_right_slash_06",
            "BELT_leather_up_slash_01", "BELT_leather_up_slash_02", "BELT_leather_up_slash_03", "BELT_leather_up_slash_04", "BELT_leather_up_slash_05", "BELT_leather_up_slash_06",
            "BELT_leather_down_slash_01", "BELT_leather_down_slash_02", "BELT_leather_down_slash_03", "BELT_leather_down_slash_04", "BELT_leather_down_slash_05", "BELT_leather_down_slash_06",

            "BELT_leather_left_thrust_01", "BELT_leather_left_thrust_02", "BELT_leather_left_thrust_03", "BELT_leather_left_thrust_04", "BELT_leather_left_thrust_05", "BELT_leather_left_thrust_06", "BELT_leather_left_thrust_07", "BELT_leather_left_thrust_08",
            "BELT_leather_right_thrust_01", "BELT_leather_right_thrust_02", "BELT_leather_right_thrust_03", "BELT_leather_right_thrust_04", "BELT_leather_right_thrust_05", "BELT_leather_right_thrust_06", "BELT_leather_right_thrust_07", "BELT_leather_right_thrust_08",
            "BELT_leather_up_thrust_01", "BELT_leather_up_thrust_02", "BELT_leather_up_thrust_03", "BELT_leather_up_thrust_04", "BELT_leather_up_thrust_05", "BELT_leather_up_thrust_06", "BELT_leather_up_thrust_07", "BELT_leather_up_thrust_08",
            "BELT_leather_down_thrust_01", "BELT_leather_down_thrust_02", "BELT_leather_down_thrust_03", "BELT_leather_down_thrust_04", "BELT_leather_down_thrust_05", "BELT_leather_down_thrust_06", "BELT_leather_down_thrust_07", "BELT_leather_down_thrust_08",

            "BELT_leather_hurt_01", "BELT_leather_hurt_02", "BELT_leather_hurt_03", "BELT_leather_hurt_04", "BELT_leather_hurt_05", "BELT_leather_hurt_06",

            "BELT_rope_left_move_01", "BELT_rope_left_move_02", "BELT_rope_left_move_03", "BELT_rope_left_move_04", "BELT_rope_left_move_05", "BELT_rope_left_move_06", "BELT_rope_left_move_07", "BELT_rope_left_move_08", "BELT_rope_left_move_09",
            "BELT_rope_right_move_01", "BELT_rope_right_move_02", "BELT_rope_right_move_03", "BELT_rope_right_move_04", "BELT_rope_right_move_05", "BELT_rope_right_move_06", "BELT_rope_right_move_07", "BELT_rope_right_move_08", "BELT_rope_right_move_09",
            "BELT_rope_up_move_01", "BELT_rope_up_move_02", "BELT_rope_up_move_03", "BELT_rope_up_move_04", "BELT_rope_up_move_05", "BELT_rope_up_move_06", "BELT_rope_up_move_07", "BELT_rope_up_move_08", "BELT_rope_up_move_09",
            "BELT_rope_down_move_01", "BELT_rope_down_move_02", "BELT_rope_down_move_03", "BELT_rope_down_move_04", "BELT_rope_down_move_05", "BELT_rope_down_move_06", "BELT_rope_down_move_07", "BELT_rope_down_move_08", "BELT_rope_down_move_09",

            "BELT_rope_left_slash_01", "BELT_rope_left_slash_02", "BELT_rope_left_slash_03", "BELT_rope_left_slash_04", "BELT_rope_left_slash_05", "BELT_rope_left_slash_06",
            "BELT_rope_right_slash_01", "BELT_rope_right_slash_02", "BELT_rope_right_slash_03", "BELT_rope_right_slash_04", "BELT_rope_right_slash_05", "BELT_rope_right_slash_06",
            "BELT_rope_up_slash_01", "BELT_rope_up_slash_02", "BELT_rope_up_slash_03", "BELT_rope_up_slash_04", "BELT_rope_up_slash_05", "BELT_rope_up_slash_06",
            "BELT_rope_down_slash_01", "BELT_rope_down_slash_02", "BELT_rope_down_slash_03", "BELT_rope_down_slash_04", "BELT_rope_down_slash_05", "BELT_rope_down_slash_06",

            "BELT_rope_left_thrust_01", "BELT_rope_left_thrust_02", "BELT_rope_left_thrust_03", "BELT_rope_left_thrust_04", "BELT_rope_left_thrust_05", "BELT_rope_left_thrust_06", "BELT_rope_left_thrust_07", "BELT_rope_left_thrust_08",
            "BELT_rope_right_thrust_01", "BELT_rope_right_thrust_02", "BELT_rope_right_thrust_03", "BELT_rope_right_thrust_04", "BELT_rope_right_thrust_05", "BELT_rope_right_thrust_06", "BELT_rope_right_thrust_07", "BELT_rope_right_thrust_08",
            "BELT_rope_up_thrust_01", "BELT_rope_up_thrust_02", "BELT_rope_up_thrust_03", "BELT_rope_up_thrust_04", "BELT_rope_up_thrust_05", "BELT_rope_up_thrust_06", "BELT_rope_up_thrust_07", "BELT_rope_up_thrust_08",
            "BELT_rope_down_thrust_01", "BELT_rope_down_thrust_02", "BELT_rope_down_thrust_03", "BELT_rope_down_thrust_04", "BELT_rope_down_thrust_05", "BELT_rope_down_thrust_06", "BELT_rope_down_thrust_07", "BELT_rope_down_thrust_08",

            "BELT_rope_hurt_01", "BELT_rope_hurt_02", "BELT_rope_hurt_03", "BELT_rope_hurt_04", "BELT_rope_hurt_05", "BELT_rope_hurt_06",

            "HANDS_plate_gloves_left_move_01", "HANDS_plate_gloves_left_move_02", "HANDS_plate_gloves_left_move_03", "HANDS_plate_gloves_left_move_04", "HANDS_plate_gloves_left_move_05", "HANDS_plate_gloves_left_move_06", "HANDS_plate_gloves_left_move_07", "HANDS_plate_gloves_left_move_08", "HANDS_plate_gloves_left_move_09",
            "HANDS_plate_gloves_right_move_01", "HANDS_plate_gloves_right_move_02", "HANDS_plate_gloves_right_move_03", "HANDS_plate_gloves_right_move_04", "HANDS_plate_gloves_right_move_05", "HANDS_plate_gloves_right_move_06", "HANDS_plate_gloves_right_move_07", "HANDS_plate_gloves_right_move_08", "HANDS_plate_gloves_right_move_09",
            "HANDS_plate_gloves_up_move_01", "HANDS_plate_gloves_up_move_02", "HANDS_plate_gloves_up_move_03", "HANDS_plate_gloves_up_move_04", "HANDS_plate_gloves_up_move_05", "HANDS_plate_gloves_up_move_06", "HANDS_plate_gloves_up_move_07", "HANDS_plate_gloves_up_move_08", "HANDS_plate_gloves_up_move_09",
            "HANDS_plate_gloves_down_move_01", "HANDS_plate_gloves_down_move_02", "HANDS_plate_gloves_down_move_03", "HANDS_plate_gloves_down_move_04", "HANDS_plate_gloves_down_move_05", "HANDS_plate_gloves_down_move_06", "HANDS_plate_gloves_down_move_07", "HANDS_plate_gloves_down_move_08", "HANDS_plate_gloves_down_move_09",

            "HANDS_plate_gloves_left_slash_01", "HANDS_plate_gloves_left_slash_02", "HANDS_plate_gloves_left_slash_03", "HANDS_plate_gloves_left_slash_04", "HANDS_plate_gloves_left_slash_05", "HANDS_plate_gloves_left_slash_06",
            "HANDS_plate_gloves_right_slash_01", "HANDS_plate_gloves_right_slash_02", "HANDS_plate_gloves_right_slash_03", "HANDS_plate_gloves_right_slash_04", "HANDS_plate_gloves_right_slash_05", "HANDS_plate_gloves_right_slash_06",
            "HANDS_plate_gloves_up_slash_01", "HANDS_plate_gloves_up_slash_02", "HANDS_plate_gloves_up_slash_03", "HANDS_plate_gloves_up_slash_04", "HANDS_plate_gloves_up_slash_05", "HANDS_plate_gloves_up_slash_06",
            "HANDS_plate_gloves_down_slash_01", "HANDS_plate_gloves_down_slash_02", "HANDS_plate_gloves_down_slash_03", "HANDS_plate_gloves_down_slash_04", "HANDS_plate_gloves_down_slash_05", "HANDS_plate_gloves_down_slash_06",

            "HANDS_plate_gloves_left_thrust_01", "HANDS_plate_gloves_left_thrust_02", "HANDS_plate_gloves_left_thrust_03", "HANDS_plate_gloves_left_thrust_04", "HANDS_plate_gloves_left_thrust_05", "HANDS_plate_gloves_left_thrust_06", "HANDS_plate_gloves_left_thrust_07", "HANDS_plate_gloves_left_thrust_08",
            "HANDS_plate_gloves_right_thrust_01", "HANDS_plate_gloves_right_thrust_02", "HANDS_plate_gloves_right_thrust_03", "HANDS_plate_gloves_right_thrust_04", "HANDS_plate_gloves_right_thrust_05", "HANDS_plate_gloves_right_thrust_06", "HANDS_plate_gloves_right_thrust_07", "HANDS_plate_gloves_right_thrust_08",
            "HANDS_plate_gloves_up_thrust_01", "HANDS_plate_gloves_up_thrust_02", "HANDS_plate_gloves_up_thrust_03", "HANDS_plate_gloves_up_thrust_04", "HANDS_plate_gloves_up_thrust_05", "HANDS_plate_gloves_up_thrust_06", "HANDS_plate_gloves_up_thrust_07", "HANDS_plate_gloves_up_thrust_08",
            "HANDS_plate_gloves_down_thrust_01", "HANDS_plate_gloves_down_thrust_02", "HANDS_plate_gloves_down_thrust_03", "HANDS_plate_gloves_down_thrust_04", "HANDS_plate_gloves_down_thrust_05", "HANDS_plate_gloves_down_thrust_06", "HANDS_plate_gloves_down_thrust_07", "HANDS_plate_gloves_down_thrust_08",

            "HANDS_plate_gloves_hurt_01", "HANDS_plate_gloves_hurt_02", "HANDS_plate_gloves_hurt_03", "HANDS_plate_gloves_hurt_04", "HANDS_plate_gloves_hurt_05", "HANDS_plate_gloves_hurt_06",

            "HANDS_leather_bracers_left_move_01", "HANDS_leather_bracers_left_move_02", "HANDS_leather_bracers_left_move_03", "HANDS_leather_bracers_left_move_04", "HANDS_leather_bracers_left_move_05", "HANDS_leather_bracers_left_move_06", "HANDS_leather_bracers_left_move_07", "HANDS_leather_bracers_left_move_08", "HANDS_leather_bracers_left_move_09",
            "HANDS_leather_bracers_right_move_01", "HANDS_leather_bracers_right_move_02", "HANDS_leather_bracers_right_move_03", "HANDS_leather_bracers_right_move_04", "HANDS_leather_bracers_right_move_05", "HANDS_leather_bracers_right_move_06", "HANDS_leather_bracers_right_move_07", "HANDS_leather_bracers_right_move_08", "HANDS_leather_bracers_right_move_09",
            "HANDS_leather_bracers_up_move_01", "HANDS_leather_bracers_up_move_02", "HANDS_leather_bracers_up_move_03", "HANDS_leather_bracers_up_move_04", "HANDS_leather_bracers_up_move_05", "HANDS_leather_bracers_up_move_06", "HANDS_leather_bracers_up_move_07", "HANDS_leather_bracers_up_move_08", "HANDS_leather_bracers_up_move_09",
            "HANDS_leather_bracers_down_move_01", "HANDS_leather_bracers_down_move_02", "HANDS_leather_bracers_down_move_03", "HANDS_leather_bracers_down_move_04", "HANDS_leather_bracers_down_move_05", "HANDS_leather_bracers_down_move_06", "HANDS_leather_bracers_down_move_07", "HANDS_leather_bracers_down_move_08", "HANDS_leather_bracers_down_move_09",

            "HANDS_leather_bracers_left_slash_01", "HANDS_leather_bracers_left_slash_02", "HANDS_leather_bracers_left_slash_03", "HANDS_leather_bracers_left_slash_04", "HANDS_leather_bracers_left_slash_05", "HANDS_leather_bracers_left_slash_06",
            "HANDS_leather_bracers_right_slash_01", "HANDS_leather_bracers_right_slash_02", "HANDS_leather_bracers_right_slash_03", "HANDS_leather_bracers_right_slash_04", "HANDS_leather_bracers_right_slash_05", "HANDS_leather_bracers_right_slash_06",
            "HANDS_leather_bracers_up_slash_01", "HANDS_leather_bracers_up_slash_02", "HANDS_leather_bracers_up_slash_03", "HANDS_leather_bracers_up_slash_04", "HANDS_leather_bracers_up_slash_05", "HANDS_leather_bracers_up_slash_06",
            "HANDS_leather_bracers_down_slash_01", "HANDS_leather_bracers_down_slash_02", "HANDS_leather_bracers_down_slash_03", "HANDS_leather_bracers_down_slash_04", "HANDS_leather_bracers_down_slash_05", "HANDS_leather_bracers_down_slash_06",

            "HANDS_leather_bracers_left_thrust_01", "HANDS_leather_bracers_left_thrust_02", "HANDS_leather_bracers_left_thrust_03", "HANDS_leather_bracers_left_thrust_04", "HANDS_leather_bracers_left_thrust_05", "HANDS_leather_bracers_left_thrust_06", "HANDS_leather_bracers_left_thrust_07", "HANDS_leather_bracers_left_thrust_08",
            "HANDS_leather_bracers_right_thrust_01", "HANDS_leather_bracers_right_thrust_02", "HANDS_leather_bracers_right_thrust_03", "HANDS_leather_bracers_right_thrust_04", "HANDS_leather_bracers_right_thrust_05", "HANDS_leather_bracers_right_thrust_06", "HANDS_leather_bracers_right_thrust_07", "HANDS_leather_bracers_right_thrust_08",
            "HANDS_leather_bracers_up_thrust_01", "HANDS_leather_bracers_up_thrust_02", "HANDS_leather_bracers_up_thrust_03", "HANDS_leather_bracers_up_thrust_04", "HANDS_leather_bracers_up_thrust_05", "HANDS_leather_bracers_up_thrust_06", "HANDS_leather_bracers_up_thrust_07", "HANDS_leather_bracers_up_thrust_08",
            "HANDS_leather_bracers_down_thrust_01", "HANDS_leather_bracers_down_thrust_02", "HANDS_leather_bracers_down_thrust_03", "HANDS_leather_bracers_down_thrust_04", "HANDS_leather_bracers_down_thrust_05", "HANDS_leather_bracers_down_thrust_06", "HANDS_leather_bracers_down_thrust_07", "HANDS_leather_bracers_down_thrust_08",

            "HANDS_leather_bracers_hurt_01", "HANDS_leather_bracers_hurt_02", "HANDS_leather_bracers_hurt_03", "HANDS_leather_bracers_hurt_04", "HANDS_leather_bracers_hurt_05", "HANDS_leather_bracers_hurt_06",

            "LEGS_pants_greenish_left_move_01", "LEGS_pants_greenish_left_move_02", "LEGS_pants_greenish_left_move_03", "LEGS_pants_greenish_left_move_04", "LEGS_pants_greenish_left_move_05", "LEGS_pants_greenish_left_move_06", "LEGS_pants_greenish_left_move_07", "LEGS_pants_greenish_left_move_08", "LEGS_pants_greenish_left_move_09",
            "LEGS_pants_greenish_right_move_01", "LEGS_pants_greenish_right_move_02", "LEGS_pants_greenish_right_move_03", "LEGS_pants_greenish_right_move_04", "LEGS_pants_greenish_right_move_05", "LEGS_pants_greenish_right_move_06", "LEGS_pants_greenish_right_move_07", "LEGS_pants_greenish_right_move_08", "LEGS_pants_greenish_right_move_09",
            "LEGS_pants_greenish_up_move_01", "LEGS_pants_greenish_up_move_02", "LEGS_pants_greenish_up_move_03", "LEGS_pants_greenish_up_move_04", "LEGS_pants_greenish_up_move_05", "LEGS_pants_greenish_up_move_06", "LEGS_pants_greenish_up_move_07", "LEGS_pants_greenish_up_move_08", "LEGS_pants_greenish_up_move_09",
            "LEGS_pants_greenish_down_move_01", "LEGS_pants_greenish_down_move_02", "LEGS_pants_greenish_down_move_03", "LEGS_pants_greenish_down_move_04", "LEGS_pants_greenish_down_move_05", "LEGS_pants_greenish_down_move_06", "LEGS_pants_greenish_down_move_07", "LEGS_pants_greenish_down_move_08", "LEGS_pants_greenish_down_move_09",

            "LEGS_pants_greenish_left_slash_01", "LEGS_pants_greenish_left_slash_02", "LEGS_pants_greenish_left_slash_03", "LEGS_pants_greenish_left_slash_04", "LEGS_pants_greenish_left_slash_05", "LEGS_pants_greenish_left_slash_06",
            "LEGS_pants_greenish_right_slash_01", "LEGS_pants_greenish_right_slash_02", "LEGS_pants_greenish_right_slash_03", "LEGS_pants_greenish_right_slash_04", "LEGS_pants_greenish_right_slash_05", "LEGS_pants_greenish_right_slash_06",
            "LEGS_pants_greenish_up_slash_01", "LEGS_pants_greenish_up_slash_02", "LEGS_pants_greenish_up_slash_03", "LEGS_pants_greenish_up_slash_04", "LEGS_pants_greenish_up_slash_05", "LEGS_pants_greenish_up_slash_06",
            "LEGS_pants_greenish_down_slash_01", "LEGS_pants_greenish_down_slash_02", "LEGS_pants_greenish_down_slash_03", "LEGS_pants_greenish_down_slash_04", "LEGS_pants_greenish_down_slash_05", "LEGS_pants_greenish_down_slash_06",

            "LEGS_pants_greenish_left_thrust_01", "LEGS_pants_greenish_left_thrust_02", "LEGS_pants_greenish_left_thrust_03", "LEGS_pants_greenish_left_thrust_04", "LEGS_pants_greenish_left_thrust_05", "LEGS_pants_greenish_left_thrust_06", "LEGS_pants_greenish_left_thrust_07", "LEGS_pants_greenish_left_thrust_08",
            "LEGS_pants_greenish_right_thrust_01", "LEGS_pants_greenish_right_thrust_02", "LEGS_pants_greenish_right_thrust_03", "LEGS_pants_greenish_right_thrust_04", "LEGS_pants_greenish_right_thrust_05", "LEGS_pants_greenish_right_thrust_06", "LEGS_pants_greenish_right_thrust_07", "LEGS_pants_greenish_right_thrust_08",
            "LEGS_pants_greenish_up_thrust_01", "LEGS_pants_greenish_up_thrust_02", "LEGS_pants_greenish_up_thrust_03", "LEGS_pants_greenish_up_thrust_04", "LEGS_pants_greenish_up_thrust_05", "LEGS_pants_greenish_up_thrust_06", "LEGS_pants_greenish_up_thrust_07", "LEGS_pants_greenish_up_thrust_08",
            "LEGS_pants_greenish_down_thrust_01", "LEGS_pants_greenish_down_thrust_02", "LEGS_pants_greenish_down_thrust_03", "LEGS_pants_greenish_down_thrust_04", "LEGS_pants_greenish_down_thrust_05", "LEGS_pants_greenish_down_thrust_06", "LEGS_pants_greenish_down_thrust_07", "LEGS_pants_greenish_down_thrust_08",

            "LEGS_pants_greenish_hurt_01", "LEGS_pants_greenish_hurt_02", "LEGS_pants_greenish_hurt_03", "LEGS_pants_greenish_hurt_04", "LEGS_pants_greenish_hurt_05", "LEGS_pants_greenish_hurt_06",

            "LEGS_robe_skirt_left_move_01", "LEGS_robe_skirt_left_move_02", "LEGS_robe_skirt_left_move_03", "LEGS_robe_skirt_left_move_04", "LEGS_robe_skirt_left_move_05", "LEGS_robe_skirt_left_move_06", "LEGS_robe_skirt_left_move_07", "LEGS_robe_skirt_left_move_08", "LEGS_robe_skirt_left_move_09",
            "LEGS_robe_skirt_right_move_01", "LEGS_robe_skirt_right_move_02", "LEGS_robe_skirt_right_move_03", "LEGS_robe_skirt_right_move_04", "LEGS_robe_skirt_right_move_05", "LEGS_robe_skirt_right_move_06", "LEGS_robe_skirt_right_move_07", "LEGS_robe_skirt_right_move_08", "LEGS_robe_skirt_right_move_09",
            "LEGS_robe_skirt_up_move_01", "LEGS_robe_skirt_up_move_02", "LEGS_robe_skirt_up_move_03", "LEGS_robe_skirt_up_move_04", "LEGS_robe_skirt_up_move_05", "LEGS_robe_skirt_up_move_06", "LEGS_robe_skirt_up_move_07", "LEGS_robe_skirt_up_move_08", "LEGS_robe_skirt_up_move_09",
            "LEGS_robe_skirt_down_move_01", "LEGS_robe_skirt_down_move_02", "LEGS_robe_skirt_down_move_03", "LEGS_robe_skirt_down_move_04", "LEGS_robe_skirt_down_move_05", "LEGS_robe_skirt_down_move_06", "LEGS_robe_skirt_down_move_07", "LEGS_robe_skirt_down_move_08", "LEGS_robe_skirt_down_move_09",

            "LEGS_robe_skirt_left_slash_01", "LEGS_robe_skirt_left_slash_02", "LEGS_robe_skirt_left_slash_03", "LEGS_robe_skirt_left_slash_04", "LEGS_robe_skirt_left_slash_05", "LEGS_robe_skirt_left_slash_06",
            "LEGS_robe_skirt_right_slash_01", "LEGS_robe_skirt_right_slash_02", "LEGS_robe_skirt_right_slash_03", "LEGS_robe_skirt_right_slash_04", "LEGS_robe_skirt_right_slash_05", "LEGS_robe_skirt_right_slash_06",
            "LEGS_robe_skirt_up_slash_01", "LEGS_robe_skirt_up_slash_02", "LEGS_robe_skirt_up_slash_03", "LEGS_robe_skirt_up_slash_04", "LEGS_robe_skirt_up_slash_05", "LEGS_robe_skirt_up_slash_06",
            "LEGS_robe_skirt_down_slash_01", "LEGS_robe_skirt_down_slash_02", "LEGS_robe_skirt_down_slash_03", "LEGS_robe_skirt_down_slash_04", "LEGS_robe_skirt_down_slash_05", "LEGS_robe_skirt_down_slash_06",

            "LEGS_robe_skirt_left_thrust_01", "LEGS_robe_skirt_left_thrust_02", "LEGS_robe_skirt_left_thrust_03", "LEGS_robe_skirt_left_thrust_04", "LEGS_robe_skirt_left_thrust_05", "LEGS_robe_skirt_left_thrust_06", "LEGS_robe_skirt_left_thrust_07", "LEGS_robe_skirt_left_thrust_08",
            "LEGS_robe_skirt_right_thrust_01", "LEGS_robe_skirt_right_thrust_02", "LEGS_robe_skirt_right_thrust_03", "LEGS_robe_skirt_right_thrust_04", "LEGS_robe_skirt_right_thrust_05", "LEGS_robe_skirt_right_thrust_06", "LEGS_robe_skirt_right_thrust_07", "LEGS_robe_skirt_right_thrust_08",
            "LEGS_robe_skirt_up_thrust_01", "LEGS_robe_skirt_up_thrust_02", "LEGS_robe_skirt_up_thrust_03", "LEGS_robe_skirt_up_thrust_04", "LEGS_robe_skirt_up_thrust_05", "LEGS_robe_skirt_up_thrust_06", "LEGS_robe_skirt_up_thrust_07", "LEGS_robe_skirt_up_thrust_08",
            "LEGS_robe_skirt_down_thrust_01", "LEGS_robe_skirt_down_thrust_02", "LEGS_robe_skirt_down_thrust_03", "LEGS_robe_skirt_down_thrust_04", "LEGS_robe_skirt_down_thrust_05", "LEGS_robe_skirt_down_thrust_06", "LEGS_robe_skirt_down_thrust_07", "LEGS_robe_skirt_down_thrust_08",

            "LEGS_robe_skirt_hurt_01", "LEGS_robe_skirt_hurt_02", "LEGS_robe_skirt_hurt_03", "LEGS_robe_skirt_hurt_04", "LEGS_robe_skirt_hurt_05", "LEGS_robe_skirt_hurt_06",

            "LEGS_plate_pants_left_move_01", "LEGS_plate_pants_left_move_02", "LEGS_plate_pants_left_move_03", "LEGS_plate_pants_left_move_04", "LEGS_plate_pants_left_move_05", "LEGS_plate_pants_left_move_06", "LEGS_plate_pants_left_move_07", "LEGS_plate_pants_left_move_08", "LEGS_plate_pants_left_move_09",
            "LEGS_plate_pants_right_move_01", "LEGS_plate_pants_right_move_02", "LEGS_plate_pants_right_move_03", "LEGS_plate_pants_right_move_04", "LEGS_plate_pants_right_move_05", "LEGS_plate_pants_right_move_06", "LEGS_plate_pants_right_move_07", "LEGS_plate_pants_right_move_08", "LEGS_plate_pants_right_move_09",
            "LEGS_plate_pants_up_move_01", "LEGS_plate_pants_up_move_02", "LEGS_plate_pants_up_move_03", "LEGS_plate_pants_up_move_04", "LEGS_plate_pants_up_move_05", "LEGS_plate_pants_up_move_06", "LEGS_plate_pants_up_move_07", "LEGS_plate_pants_up_move_08", "LEGS_plate_pants_up_move_09",
            "LEGS_plate_pants_down_move_01", "LEGS_plate_pants_down_move_02", "LEGS_plate_pants_down_move_03", "LEGS_plate_pants_down_move_04", "LEGS_plate_pants_down_move_05", "LEGS_plate_pants_down_move_06", "LEGS_plate_pants_down_move_07", "LEGS_plate_pants_down_move_08", "LEGS_plate_pants_down_move_09",

            "LEGS_plate_pants_left_slash_01", "LEGS_plate_pants_left_slash_02", "LEGS_plate_pants_left_slash_03", "LEGS_plate_pants_left_slash_04", "LEGS_plate_pants_left_slash_05", "LEGS_plate_pants_left_slash_06",
            "LEGS_plate_pants_right_slash_01", "LEGS_plate_pants_right_slash_02", "LEGS_plate_pants_right_slash_03", "LEGS_plate_pants_right_slash_04", "LEGS_plate_pants_right_slash_05", "LEGS_plate_pants_right_slash_06",
            "LEGS_plate_pants_up_slash_01", "LEGS_plate_pants_up_slash_02", "LEGS_plate_pants_up_slash_03", "LEGS_plate_pants_up_slash_04", "LEGS_plate_pants_up_slash_05", "LEGS_plate_pants_up_slash_06",
            "LEGS_plate_pants_down_slash_01", "LEGS_plate_pants_down_slash_02", "LEGS_plate_pants_down_slash_03", "LEGS_plate_pants_down_slash_04", "LEGS_plate_pants_down_slash_05", "LEGS_plate_pants_down_slash_06",

            "LEGS_plate_pants_left_thrust_01", "LEGS_plate_pants_left_thrust_02", "LEGS_plate_pants_left_thrust_03", "LEGS_plate_pants_left_thrust_04", "LEGS_plate_pants_left_thrust_05", "LEGS_plate_pants_left_thrust_06", "LEGS_plate_pants_left_thrust_07", "LEGS_plate_pants_left_thrust_08",
            "LEGS_plate_pants_right_thrust_01", "LEGS_plate_pants_right_thrust_02", "LEGS_plate_pants_right_thrust_03", "LEGS_plate_pants_right_thrust_04", "LEGS_plate_pants_right_thrust_05", "LEGS_plate_pants_right_thrust_06", "LEGS_plate_pants_right_thrust_07", "LEGS_plate_pants_right_thrust_08",
            "LEGS_plate_pants_up_thrust_01", "LEGS_plate_pants_up_thrust_02", "LEGS_plate_pants_up_thrust_03", "LEGS_plate_pants_up_thrust_04", "LEGS_plate_pants_up_thrust_05", "LEGS_plate_pants_up_thrust_06", "LEGS_plate_pants_up_thrust_07", "LEGS_plate_pants_up_thrust_08",
            "LEGS_plate_pants_down_thrust_01", "LEGS_plate_pants_down_thrust_02", "LEGS_plate_pants_down_thrust_03", "LEGS_plate_pants_down_thrust_04", "LEGS_plate_pants_down_thrust_05", "LEGS_plate_pants_down_thrust_06", "LEGS_plate_pants_down_thrust_07", "LEGS_plate_pants_down_thrust_08",

            "LEGS_plate_pants_hurt_01", "LEGS_plate_pants_hurt_02", "LEGS_plate_pants_hurt_03", "LEGS_plate_pants_hurt_04", "LEGS_plate_pants_hurt_05", "LEGS_plate_pants_hurt_06",

            "FEET_shoes_brown_left_move_01", "FEET_shoes_brown_left_move_02", "FEET_shoes_brown_left_move_03", "FEET_shoes_brown_left_move_04", "FEET_shoes_brown_left_move_05", "FEET_shoes_brown_left_move_06", "FEET_shoes_brown_left_move_07", "FEET_shoes_brown_left_move_08", "FEET_shoes_brown_left_move_09",
            "FEET_shoes_brown_right_move_01", "FEET_shoes_brown_right_move_02", "FEET_shoes_brown_right_move_03", "FEET_shoes_brown_right_move_04", "FEET_shoes_brown_right_move_05", "FEET_shoes_brown_right_move_06", "FEET_shoes_brown_right_move_07", "FEET_shoes_brown_right_move_08", "FEET_shoes_brown_right_move_09",
            "FEET_shoes_brown_up_move_01", "FEET_shoes_brown_up_move_02", "FEET_shoes_brown_up_move_03", "FEET_shoes_brown_up_move_04", "FEET_shoes_brown_up_move_05", "FEET_shoes_brown_up_move_06", "FEET_shoes_brown_up_move_07", "FEET_shoes_brown_up_move_08", "FEET_shoes_brown_up_move_09",
            "FEET_shoes_brown_down_move_01", "FEET_shoes_brown_down_move_02", "FEET_shoes_brown_down_move_03", "FEET_shoes_brown_down_move_04", "FEET_shoes_brown_down_move_05", "FEET_shoes_brown_down_move_06", "FEET_shoes_brown_down_move_07", "FEET_shoes_brown_down_move_08", "FEET_shoes_brown_down_move_09",

            "FEET_shoes_brown_left_slash_01", "FEET_shoes_brown_left_slash_02", "FEET_shoes_brown_left_slash_03", "FEET_shoes_brown_left_slash_04", "FEET_shoes_brown_left_slash_05", "FEET_shoes_brown_left_slash_06",
            "FEET_shoes_brown_right_slash_01", "FEET_shoes_brown_right_slash_02", "FEET_shoes_brown_right_slash_03", "FEET_shoes_brown_right_slash_04", "FEET_shoes_brown_right_slash_05", "FEET_shoes_brown_right_slash_06",
            "FEET_shoes_brown_up_slash_01", "FEET_shoes_brown_up_slash_02", "FEET_shoes_brown_up_slash_03", "FEET_shoes_brown_up_slash_04", "FEET_shoes_brown_up_slash_05", "FEET_shoes_brown_up_slash_06",
            "FEET_shoes_brown_down_slash_01", "FEET_shoes_brown_down_slash_02", "FEET_shoes_brown_down_slash_03", "FEET_shoes_brown_down_slash_04", "FEET_shoes_brown_down_slash_05", "FEET_shoes_brown_down_slash_06",

            "FEET_shoes_brown_left_thrust_01", "FEET_shoes_brown_left_thrust_02", "FEET_shoes_brown_left_thrust_03", "FEET_shoes_brown_left_thrust_04", "FEET_shoes_brown_left_thrust_05", "FEET_shoes_brown_left_thrust_06", "FEET_shoes_brown_left_thrust_07", "FEET_shoes_brown_left_thrust_08",
            "FEET_shoes_brown_right_thrust_01", "FEET_shoes_brown_right_thrust_02", "FEET_shoes_brown_right_thrust_03", "FEET_shoes_brown_right_thrust_04", "FEET_shoes_brown_right_thrust_05", "FEET_shoes_brown_right_thrust_06", "FEET_shoes_brown_right_thrust_07", "FEET_shoes_brown_right_thrust_08",
            "FEET_shoes_brown_up_thrust_01", "FEET_shoes_brown_up_thrust_02", "FEET_shoes_brown_up_thrust_03", "FEET_shoes_brown_up_thrust_04", "FEET_shoes_brown_up_thrust_05", "FEET_shoes_brown_up_thrust_06", "FEET_shoes_brown_up_thrust_07", "FEET_shoes_brown_up_thrust_08",
            "FEET_shoes_brown_down_thrust_01", "FEET_shoes_brown_down_thrust_02", "FEET_shoes_brown_down_thrust_03", "FEET_shoes_brown_down_thrust_04", "FEET_shoes_brown_down_thrust_05", "FEET_shoes_brown_down_thrust_06", "FEET_shoes_brown_down_thrust_07", "FEET_shoes_brown_down_thrust_08",

            "FEET_shoes_brown_hurt_01", "FEET_shoes_brown_hurt_02", "FEET_shoes_brown_hurt_03", "FEET_shoes_brown_hurt_04", "FEET_shoes_brown_hurt_05", "FEET_shoes_brown_hurt_06",

            "FEET_plate_shoes_left_move_01", "FEET_plate_shoes_left_move_02", "FEET_plate_shoes_left_move_03", "FEET_plate_shoes_left_move_04", "FEET_plate_shoes_left_move_05", "FEET_plate_shoes_left_move_06", "FEET_plate_shoes_left_move_07", "FEET_plate_shoes_left_move_08", "FEET_plate_shoes_left_move_09",
            "FEET_plate_shoes_right_move_01", "FEET_plate_shoes_right_move_02", "FEET_plate_shoes_right_move_03", "FEET_plate_shoes_right_move_04", "FEET_plate_shoes_right_move_05", "FEET_plate_shoes_right_move_06", "FEET_plate_shoes_right_move_07", "FEET_plate_shoes_right_move_08", "FEET_plate_shoes_right_move_09",
            "FEET_plate_shoes_up_move_01", "FEET_plate_shoes_up_move_02", "FEET_plate_shoes_up_move_03", "FEET_plate_shoes_up_move_04", "FEET_plate_shoes_up_move_05", "FEET_plate_shoes_up_move_06", "FEET_plate_shoes_up_move_07", "FEET_plate_shoes_up_move_08", "FEET_plate_shoes_up_move_09",
            "FEET_plate_shoes_down_move_01", "FEET_plate_shoes_down_move_02", "FEET_plate_shoes_down_move_03", "FEET_plate_shoes_down_move_04", "FEET_plate_shoes_down_move_05", "FEET_plate_shoes_down_move_06", "FEET_plate_shoes_down_move_07", "FEET_plate_shoes_down_move_08", "FEET_plate_shoes_down_move_09",

            "FEET_plate_shoes_left_slash_01", "FEET_plate_shoes_left_slash_02", "FEET_plate_shoes_left_slash_03", "FEET_plate_shoes_left_slash_04", "FEET_plate_shoes_left_slash_05", "FEET_plate_shoes_left_slash_06",
            "FEET_plate_shoes_right_slash_01", "FEET_plate_shoes_right_slash_02", "FEET_plate_shoes_right_slash_03", "FEET_plate_shoes_right_slash_04", "FEET_plate_shoes_right_slash_05", "FEET_plate_shoes_right_slash_06",
            "FEET_plate_shoes_up_slash_01", "FEET_plate_shoes_up_slash_02", "FEET_plate_shoes_up_slash_03", "FEET_plate_shoes_up_slash_04", "FEET_plate_shoes_up_slash_05", "FEET_plate_shoes_up_slash_06",
            "FEET_plate_shoes_down_slash_01", "FEET_plate_shoes_down_slash_02", "FEET_plate_shoes_down_slash_03", "FEET_plate_shoes_down_slash_04", "FEET_plate_shoes_down_slash_05", "FEET_plate_shoes_down_slash_06",

            "FEET_plate_shoes_left_thrust_01", "FEET_plate_shoes_left_thrust_02", "FEET_plate_shoes_left_thrust_03", "FEET_plate_shoes_left_thrust_04", "FEET_plate_shoes_left_thrust_05", "FEET_plate_shoes_left_thrust_06", "FEET_plate_shoes_left_thrust_07", "FEET_plate_shoes_left_thrust_08",
            "FEET_plate_shoes_right_thrust_01", "FEET_plate_shoes_right_thrust_02", "FEET_plate_shoes_right_thrust_03", "FEET_plate_shoes_right_thrust_04", "FEET_plate_shoes_right_thrust_05", "FEET_plate_shoes_right_thrust_06", "FEET_plate_shoes_right_thrust_07", "FEET_plate_shoes_right_thrust_08",
            "FEET_plate_shoes_up_thrust_01", "FEET_plate_shoes_up_thrust_02", "FEET_plate_shoes_up_thrust_03", "FEET_plate_shoes_up_thrust_04", "FEET_plate_shoes_up_thrust_05", "FEET_plate_shoes_up_thrust_06", "FEET_plate_shoes_up_thrust_07", "FEET_plate_shoes_up_thrust_08",
            "FEET_plate_shoes_down_thrust_01", "FEET_plate_shoes_down_thrust_02", "FEET_plate_shoes_down_thrust_03", "FEET_plate_shoes_down_thrust_04", "FEET_plate_shoes_down_thrust_05", "FEET_plate_shoes_down_thrust_06", "FEET_plate_shoes_down_thrust_07", "FEET_plate_shoes_down_thrust_08",

            "FEET_plate_shoes_hurt_01", "FEET_plate_shoes_hurt_02", "FEET_plate_shoes_hurt_03", "FEET_plate_shoes_hurt_04", "FEET_plate_shoes_hurt_05", "FEET_plate_shoes_hurt_06",

            // Остальные мобы
            "slime_left_01", "slime_left_02", "slime_left_03",
            "slime_right_01", "slime_right_02", "slime_right_03",

            "slime_death_01", "slime_death_02",

            "skeleton_left_move_01", "skeleton_left_move_02", "skeleton_left_move_03", "skeleton_left_move_04", "skeleton_left_move_05", "skeleton_left_move_06", "skeleton_left_move_07", "skeleton_left_move_08", "skeleton_left_move_09",
            "skeleton_right_move_01", "skeleton_right_move_02", "skeleton_right_move_03", "skeleton_right_move_04", "skeleton_right_move_05", "skeleton_right_move_06", "skeleton_right_move_07", "skeleton_right_move_08", "skeleton_right_move_09",
            "skeleton_up_move_01", "skeleton_up_move_02", "skeleton_up_move_03", "skeleton_up_move_04", "skeleton_up_move_05", "skeleton_up_move_06", "skeleton_up_move_07", "skeleton_up_move_08", "skeleton_up_move_09",
            "skeleton_down_move_01", "skeleton_down_move_02", "skeleton_down_move_03", "skeleton_down_move_04", "skeleton_down_move_05", "skeleton_down_move_06", "skeleton_down_move_07", "skeleton_down_move_08", "skeleton_down_move_09",

            "spider_left_move_01", "spider_left_move_02", "spider_left_move_03", "spider_left_move_04", "spider_left_move_05", "spider_left_move_06", "spider_left_move_07",
            "spider_right_move_01", "spider_right_move_02", "spider_right_move_03", "spider_right_move_04", "spider_right_move_05", "spider_right_move_06", "spider_right_move_07",
            "spider_up_move_01", "spider_up_move_02",  "spider_up_move_03", "spider_up_move_04", "spider_up_move_05", "spider_up_move_06", "spider_up_move_07",
            "spider_down_move_01", "spider_down_move_02", "spider_down_move_03", "spider_down_move_04", "spider_down_move_05", "spider_down_move_06", "spider_down_move_07",

            "spider_left_attack_01", "spider_left_attack_02", "spider_left_attack_03",
            "spider_right_attack_01", "spider_right_attack_02", "spider_right_attack_03",
            "spider_up_attack_01", "spider_up_attack_02", "spider_up_attack_03",
            "spider_down_attack_01", "spider_down_attack_02", "spider_down_attack_03",

            "spider_death_01", "spider_death_02", "spider_death_03", "spider_death_04",

            "imp_left_move_01", "imp_left_move_02", "imp_left_move_03", "imp_left_move_04",
            "imp_right_move_01", "imp_right_move_02", "imp_right_move_03", "imp_right_move_04",
            "imp_up_move_01", "imp_up_move_02",  "imp_up_move_03", "imp_up_move_04",
            "imp_down_move_01", "imp_down_move_02", "imp_down_move_03", "imp_down_move_04",

            "imp_left_attack_01", "imp_left_attack_02", "imp_left_attack_03", "imp_left_attack_04",
            "imp_right_attack_01", "imp_right_attack_02", "imp_right_attack_03", "imp_right_attack_04",
            "imp_up_attack_01", "imp_up_attack_02", "imp_up_attack_03", "imp_up_attack_04",
            "imp_down_attack_01", "imp_down_attack_02", "imp_down_attack_03", "imp_down_attack_04",

            "imp_death_01", "imp_death_02", "imp_death_03", "imp_death_04", "imp_death_05", "imp_death_06", "imp_death_07",

            "boxClosed", "boxOpened", "barrelClosed", "barrelOpened", "bagBig", "bagMedium", "bagSmall", "altar0", "altar1"

    };
}
