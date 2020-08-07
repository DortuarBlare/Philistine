public class Storage {
    static final int[] firstLevelWalls = {
            111, 128, 495, 140, // wall0
            496, 143, 498, 232, // wall1
            499, 232, 639, 236, // wall2
            111, 339, 639, 341, // wall3
            103, 139, 111, 332 // wall4
    };
    static final int[] secondLevelWalls = {
            0, 182, 94, 188, // wall0
            92, 140, 96, 184, // wall1
            98, 134, 526, 140, // wall2
            529, 143, 534, 334, // wall3
            97, 335, 528, 340, // wall4
            92, 290, 96, 334, // wall5
            0, 287, 92, 291 // wall6
    };
    static final String[] textureString = {
            "0hp", "10hp", "20hp", "30hp", "40hp", "50hp", "60hp", "70hp", "80hp", "90hp", "100hp",
            "player_stand_left", "player_stand_right", "player_stand_up", "player_stand_down",
            "player_walk_left_01", "player_walk_left_02", "player_walk_left_03", "player_walk_left_04", "player_walk_left_05", "player_walk_left_06", "player_walk_left_07", "player_walk_left_08", "player_walk_left_09",
            "player_walk_right_01", "player_walk_right_02", "player_walk_right_03", "player_walk_right_04", "player_walk_right_05", "player_walk_right_06", "player_walk_right_07", "player_walk_right_08", "player_walk_right_09",
            "player_walk_up_01", "player_walk_up_02", "player_walk_up_03", "player_walk_up_04", "player_walk_up_05", "player_walk_up_06", "player_walk_up_07", "player_walk_up_08", "player_walk_up_09",
            "player_walk_down_01", "player_walk_down_02", "player_walk_down_03", "player_walk_down_04", "player_walk_down_05", "player_walk_down_06", "player_walk_down_07", "player_walk_down_08",
            "slime_left_01", "slime_left_02", "slime_left_03",
            "slime_right_01", "slime_right_02", "slime_right_03",
            "level0", "level1", "level2", "level3", "box",
            "torch0", "torch1", "torch2", "torch3",
            "enemyHp0", "enemyHp1", "enemyHp2", "enemyHp3", "enemyHp4", "enemyHp5",

            "player_slash_right_01", "player_slash_right_02", "player_slash_right_03", "player_slash_right_04", "player_slash_right_05", "player_slash_right_06",
            "player_slash_up_01", "player_slash_up_02", "player_slash_up_03", "player_slash_up_04", "player_slash_up_05", "player_slash_up_06",
            "player_slash_left_01", "player_slash_left_02", "player_slash_left_03", "player_slash_left_04", "player_slash_left_05", "player_slash_left_06",
            "player_slash_down_01", "player_slash_down_02", "player_slash_down_03", "player_slash_down_04", "player_slash_down_05", "player_slash_down_06",

            "weapon_rapier_right_01", "weapon_rapier_right_02", "weapon_rapier_right_03", "weapon_rapier_right_04", "weapon_rapier_right_05", "weapon_rapier_right_06",
            "weapon_rapier_up_01", "weapon_rapier_up_02", "weapon_rapier_up_03", "weapon_rapier_up_04", "weapon_rapier_up_05", "weapon_rapier_up_06",
            "weapon_rapier_down_01", "weapon_rapier_down_02", "weapon_rapier_down_03", "weapon_rapier_down_04", "weapon_rapier_down_05", "weapon_rapier_down_06",
            "weapon_rapier_left_01", "weapon_rapier_left_02", "weapon_rapier_left_03", "weapon_rapier_left_04", "weapon_rapier_left_05", "weapon_rapier_left_06",

            "HEAD_hair_left_move_01", "HEAD_hair_left_move_02", "HEAD_hair_left_move_03", "HEAD_hair_left_move_04", "HEAD_hair_left_move_05", "HEAD_hair_left_move_06", "HEAD_hair_left_move_07", "HEAD_hair_left_move_08", "HEAD_hair_left_move_09",
            "HEAD_hair_right_move_01", "HEAD_hair_right_move_02", "HEAD_hair_right_move_03", "HEAD_hair_right_move_04", "HEAD_hair_right_move_05", "HEAD_hair_right_move_06", "HEAD_hair_right_move_07", "HEAD_hair_right_move_08", "HEAD_hair_right_move_09",
            "HEAD_hair_up_move_01", "HEAD_hair_up_move_02", "HEAD_hair_up_move_03", "HEAD_hair_up_move_04", "HEAD_hair_up_move_05", "HEAD_hair_up_move_06", "HEAD_hair_up_move_07", "HEAD_hair_up_move_08", "HEAD_hair_up_move_09",
            "HEAD_hair_down_move_01", "HEAD_hair_down_move_02", "HEAD_hair_down_move_03", "HEAD_hair_down_move_04", "HEAD_hair_down_move_05", "HEAD_hair_down_move_06", "HEAD_hair_down_move_07", "HEAD_hair_down_move_08", "HEAD_hair_down_move_09",

            "HEAD_hair_left_slash_01", "HEAD_hair_left_slash_02", "HEAD_hair_left_slash_03", "HEAD_hair_left_slash_04", "HEAD_hair_left_slash_05", "HEAD_hair_left_slash_06",
            "HEAD_hair_right_slash_01", "HEAD_hair_right_slash_02", "HEAD_hair_right_slash_03", "HEAD_hair_right_slash_04", "HEAD_hair_right_slash_05", "HEAD_hair_right_slash_06",
            "HEAD_hair_up_slash_01", "HEAD_hair_up_slash_02", "HEAD_hair_up_slash_03", "HEAD_hair_up_slash_04", "HEAD_hair_up_slash_05", "HEAD_hair_up_slash_06",
            "HEAD_hair_down_slash_01", "HEAD_hair_down_slash_02", "HEAD_hair_down_slash_03", "HEAD_hair_down_slash_04", "HEAD_hair_down_slash_05", "HEAD_hair_down_slash_06",

            "TORSO_shirt_white_left_move_01", "TORSO_shirt_white_left_move_02", "TORSO_shirt_white_left_move_03", "TORSO_shirt_white_left_move_04", "TORSO_shirt_white_left_move_05", "TORSO_shirt_white_left_move_06", "TORSO_shirt_white_left_move_07", "TORSO_shirt_white_left_move_08", "TORSO_shirt_white_left_move_09",
            "TORSO_shirt_white_right_move_01", "TORSO_shirt_white_right_move_02", "TORSO_shirt_white_right_move_03", "TORSO_shirt_white_right_move_04", "TORSO_shirt_white_right_move_05", "TORSO_shirt_white_right_move_06", "TORSO_shirt_white_right_move_07", "TORSO_shirt_white_right_move_08", "TORSO_shirt_white_right_move_09",
            "TORSO_shirt_white_up_move_01", "TORSO_shirt_white_up_move_02", "TORSO_shirt_white_up_move_03", "TORSO_shirt_white_up_move_04", "TORSO_shirt_white_up_move_05", "TORSO_shirt_white_up_move_06", "TORSO_shirt_white_up_move_07", "TORSO_shirt_white_up_move_08", "TORSO_shirt_white_up_move_09",
            "TORSO_shirt_white_down_move_01", "TORSO_shirt_white_down_move_02", "TORSO_shirt_white_down_move_03", "TORSO_shirt_white_down_move_04", "TORSO_shirt_white_down_move_05", "TORSO_shirt_white_down_move_06", "TORSO_shirt_white_down_move_07", "TORSO_shirt_white_down_move_08", "TORSO_shirt_white_down_move_09",

            "TORSO_shirt_white_left_slash_01", "TORSO_shirt_white_left_slash_02", "TORSO_shirt_white_left_slash_03", "TORSO_shirt_white_left_slash_04", "TORSO_shirt_white_left_slash_05", "TORSO_shirt_white_left_slash_06",
            "TORSO_shirt_white_right_slash_01", "TORSO_shirt_white_right_slash_02", "TORSO_shirt_white_right_slash_03", "TORSO_shirt_white_right_slash_04", "TORSO_shirt_white_right_slash_05", "TORSO_shirt_white_right_slash_06",
            "TORSO_shirt_white_up_slash_01", "TORSO_shirt_white_up_slash_02", "TORSO_shirt_white_up_slash_03", "TORSO_shirt_white_up_slash_04", "TORSO_shirt_white_up_slash_05", "TORSO_shirt_white_up_slash_06",
            "TORSO_shirt_white_down_slash_01", "TORSO_shirt_white_down_slash_02", "TORSO_shirt_white_down_slash_03", "TORSO_shirt_white_down_slash_04", "TORSO_shirt_white_down_slash_05", "TORSO_shirt_white_down_slash_06",

            "LEGS_pants_greenish_left_move_01", "LEGS_pants_greenish_left_move_02", "LEGS_pants_greenish_left_move_03", "LEGS_pants_greenish_left_move_04", "LEGS_pants_greenish_left_move_05", "LEGS_pants_greenish_left_move_06", "LEGS_pants_greenish_left_move_07", "LEGS_pants_greenish_left_move_08", "LEGS_pants_greenish_left_move_09",
            "LEGS_pants_greenish_right_move_01", "LEGS_pants_greenish_right_move_02", "LEGS_pants_greenish_right_move_03", "LEGS_pants_greenish_right_move_04", "LEGS_pants_greenish_right_move_05", "LEGS_pants_greenish_right_move_06", "LEGS_pants_greenish_right_move_07", "LEGS_pants_greenish_right_move_08", "LEGS_pants_greenish_right_move_09",
            "LEGS_pants_greenish_up_move_01", "LEGS_pants_greenish_up_move_02", "LEGS_pants_greenish_up_move_03", "LEGS_pants_greenish_up_move_04", "LEGS_pants_greenish_up_move_05", "LEGS_pants_greenish_up_move_06", "LEGS_pants_greenish_up_move_07", "LEGS_pants_greenish_up_move_08", "LEGS_pants_greenish_up_move_09",
            "LEGS_pants_greenish_down_move_01", "LEGS_pants_greenish_down_move_02", "LEGS_pants_greenish_down_move_03", "LEGS_pants_greenish_down_move_04", "LEGS_pants_greenish_down_move_05", "LEGS_pants_greenish_down_move_06", "LEGS_pants_greenish_down_move_07", "LEGS_pants_greenish_down_move_08", "LEGS_pants_greenish_down_move_09",

            "LEGS_pants_greenish_left_slash_01", "LEGS_pants_greenish_left_slash_02", "LEGS_pants_greenish_left_slash_03", "LEGS_pants_greenish_left_slash_04", "LEGS_pants_greenish_left_slash_05", "LEGS_pants_greenish_left_slash_06",
            "LEGS_pants_greenish_right_slash_01", "LEGS_pants_greenish_right_slash_02", "LEGS_pants_greenish_right_slash_03", "LEGS_pants_greenish_right_slash_04", "LEGS_pants_greenish_right_slash_05", "LEGS_pants_greenish_right_slash_06",
            "LEGS_pants_greenish_up_slash_01", "LEGS_pants_greenish_up_slash_02", "LEGS_pants_greenish_up_slash_03", "LEGS_pants_greenish_up_slash_04", "LEGS_pants_greenish_up_slash_05", "LEGS_pants_greenish_up_slash_06",
            "LEGS_pants_greenish_down_slash_01", "LEGS_pants_greenish_down_slash_02", "LEGS_pants_greenish_down_slash_03", "LEGS_pants_greenish_down_slash_04", "LEGS_pants_greenish_down_slash_05", "LEGS_pants_greenish_down_slash_06",

            "FEET_shoes_brown_left_move_01", "FEET_shoes_brown_left_move_02", "FEET_shoes_brown_left_move_03", "FEET_shoes_brown_left_move_04", "FEET_shoes_brown_left_move_05", "FEET_shoes_brown_left_move_06", "FEET_shoes_brown_left_move_07", "FEET_shoes_brown_left_move_08", "FEET_shoes_brown_left_move_09",
            "FEET_shoes_brown_right_move_01", "FEET_shoes_brown_right_move_02", "FEET_shoes_brown_right_move_03", "FEET_shoes_brown_right_move_04", "FEET_shoes_brown_right_move_05", "FEET_shoes_brown_right_move_06", "FEET_shoes_brown_right_move_07", "FEET_shoes_brown_right_move_08", "FEET_shoes_brown_right_move_09",
            "FEET_shoes_brown_up_move_01", "FEET_shoes_brown_up_move_02", "FEET_shoes_brown_up_move_03", "FEET_shoes_brown_up_move_04", "FEET_shoes_brown_up_move_05", "FEET_shoes_brown_up_move_06", "FEET_shoes_brown_up_move_07", "FEET_shoes_brown_up_move_08", "FEET_shoes_brown_up_move_09",
            "FEET_shoes_brown_down_move_01", "FEET_shoes_brown_down_move_02", "FEET_shoes_brown_down_move_03", "FEET_shoes_brown_down_move_04", "FEET_shoes_brown_down_move_05", "FEET_shoes_brown_down_move_06", "FEET_shoes_brown_down_move_07", "FEET_shoes_brown_down_move_08", "FEET_shoes_brown_down_move_09",

            "FEET_shoes_brown_left_slash_01", "FEET_shoes_brown_left_slash_02", "FEET_shoes_brown_left_slash_03", "FEET_shoes_brown_left_slash_04", "FEET_shoes_brown_left_slash_05", "FEET_shoes_brown_left_slash_06",
            "FEET_shoes_brown_right_slash_01", "FEET_shoes_brown_right_slash_02", "FEET_shoes_brown_right_slash_03", "FEET_shoes_brown_right_slash_04", "FEET_shoes_brown_right_slash_05", "FEET_shoes_brown_right_slash_06",
            "FEET_shoes_brown_up_slash_01", "FEET_shoes_brown_up_slash_02", "FEET_shoes_brown_up_slash_03", "FEET_shoes_brown_up_slash_04", "FEET_shoes_brown_up_slash_05", "FEET_shoes_brown_up_slash_06",
            "FEET_shoes_brown_down_slash_01", "FEET_shoes_brown_down_slash_02", "FEET_shoes_brown_down_slash_03", "FEET_shoes_brown_down_slash_04", "FEET_shoes_brown_down_slash_05", "FEET_shoes_brown_down_slash_06",
    };
    static final String[] aabbString = {
            "wall0", "wall1", "wall2", "wall3", "wall4", "wall5", "wall6",
            "entranceToFirstLevel", "entranceToSecondLevel", "entranceToThirdLevel", "entranceToFourthLevel", "pants_greenish"
    };
}
