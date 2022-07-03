package managers;

import content.AudioMaster;
import levels.MainMenuLevel;
import levels.TownLevel;
import mobs.Mob;
import singletons.SingletonMobs;
import singletons.SingletonPlayer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class UserInputManager {
    public static boolean pressedKeyE = false;

    public void init(long mainWindow) {
        // Обработка единичного нажатий клавиш
        glfwSetKeyCallback(mainWindow, (window, key, scancode, action, mods) -> {
            // Нажатие Escape
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS && !(LevelManager.currentLevel instanceof MainMenuLevel)) {
                switch (SingletonPlayer.player.getMenuChoice()) {
//                    case "Resume":
//                    case "Options":
                    case "Exit": {
                        SingletonPlayer.player.setScrollMenu(!SingletonPlayer.player.isScrollMenu());
                        break;
                    }
//                    case "Options_Sounds":
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
            if (key == GLFW_KEY_E && action == GLFW_PRESS && !(LevelManager.currentLevel instanceof MainMenuLevel))
                pressedKeyE = true;

            // Нажатие Enter
            if (key == GLFW_KEY_ENTER && action == GLFW_PRESS) {
                // Выход из главного меню
                if (LevelManager.currentLevel instanceof MainMenuLevel)
                    LevelManager.mainMenuToTown();
                // Выбор в меню паузы
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
                            System.exit(0);
                            break;
                        }
                    }
                }
                // Выбор в диалоговом окне(Войти в данж?)
                else if (LevelManager.currentLevel instanceof TownLevel && SingletonPlayer.player.isDialogBubble())
                    LevelManager.townToDungeon();
            }

            // Нажатие стрелки влево
            if (key == GLFW_KEY_LEFT && action == GLFW_PRESS) {
                // Атака
                if (!SingletonPlayer.player.isAttack() && !SingletonPlayer.player.isKnockBackTaskStarted() &&
                        LevelManager.attackAllowed()) {
                    SingletonPlayer.player.setAttackLeft(true);
                    SingletonPlayer.player.setMoveDirection("left");
                }
                // Выбор в диалоговом окне(Войти в данж?)
                else if (LevelManager.currentLevel instanceof TownLevel && SingletonPlayer.player.isDialogBubble())
                    SingletonPlayer.player.setDialogBubbleChoice(!SingletonPlayer.player.isDialogBubbleChoice());
            }

            // Нажатие стрелки вправо
            if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS) {
                // Атака
                if (!SingletonPlayer.player.isAttack() && !SingletonPlayer.player.isKnockBackTaskStarted() &&
                        LevelManager.attackAllowed()) {
                    SingletonPlayer.player.setAttackRight(true);
                    SingletonPlayer.player.setMoveDirection("right");
                }
                // Выбор в диалоговом окне(Войти в данж?)
                else if (LevelManager.currentLevel instanceof TownLevel && SingletonPlayer.player.isDialogBubble())
                    SingletonPlayer.player.setDialogBubbleChoice(!SingletonPlayer.player.isDialogBubbleChoice());
            }

            // Нажатие стрелки вверх
            if (key == GLFW_KEY_UP && action == GLFW_PRESS) {
                // Атака
                if (!SingletonPlayer.player.isAttack() && !SingletonPlayer.player.isKnockBackTaskStarted() &&
                        LevelManager.attackAllowed() &&
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
                        LevelManager.attackAllowed() &&
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

    public void update() {

        pressedKeyE = false;
    }
}
