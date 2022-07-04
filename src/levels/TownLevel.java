package levels;

import content.Storage;
import content.Texture;
import managers.LevelManager;
import managers.UserInputManager;
import physics.AABB;
import singletons.SingletonPlayer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glTranslated;

public class TownLevel implements Level {
    private int guard_i = 1, guard_g = 0;

    @Override
    public void init() {
        // Обновление хитбоксов стен для города
        for (int i = 0; i < 13; i++)
            Storage.aabbMap.get("wall" + i).update(0, 0, 0, 0);
    }

    @Override
    public void update() {
        collide();

        // Переход в данж
        if (SingletonPlayer.player.getX() + 32 == 1519)
            SingletonPlayer.player.setDialogBubble(true);

        if (UserInputManager.pressedKeyE) {
            // Вхождение в таверну
            if (SingletonPlayer.player.getX() + 32 > 305 && SingletonPlayer.player.getX() + 32 < 341)
                LevelManager.townToTavern();

            // Вхождение в кузницу
            else if (SingletonPlayer.player.getX() + 32 > 620 && SingletonPlayer.player.getX() + 32 < 651)
                LevelManager.townToForge();
        }
    }

    @Override
    public void draw() {
        // Фон
        Texture.draw(Storage.textureMap.get("MainMenu"), new AABB(0, 0, 1536, 360));

        // Анимация охранника
        if (guard_i == 11)
            guard_i = 1;

        Texture.draw(Storage.textureMap.get("guard_stand_0" + guard_i), new AABB(30, 190, 62, 224));

        if (guard_g++ == 10) {
            guard_i++;
            guard_g = 0;
        }

        // Надпись о блокировке пути
        if (SingletonPlayer.player.getX() <= 50)
            Texture.draw(Storage.textureMap.get("you_Shall_Not_Pass"), new AABB(34, 136, 94, 195));

        // Диалоговое окно
        if (SingletonPlayer.player.isDialogBubble()) {
            if (SingletonPlayer.player.isDialogBubbleChoice())
                Texture.draw(
                        Storage.textureMap.get("enterTheDungeon_Yes"),
                        new AABB(
                                SingletonPlayer.player.getX() - 20,
                                SingletonPlayer.player.getY() - 55,
                                SingletonPlayer.player.getX() + 40,
                                SingletonPlayer.player.getY()
                        )
                );
            else
                Texture.draw(
                        Storage.textureMap.get("enterTheDungeon_No"),
                        new AABB(
                                SingletonPlayer.player.getX() - 20,
                                SingletonPlayer.player.getY() - 55,
                                SingletonPlayer.player.getX() + 40,
                                SingletonPlayer.player.getY()
                        )
                );
        }
    }

    @Override
    public void collide() {
        // Левая граница города
        if (SingletonPlayer.player.getX() <= 48)
            SingletonPlayer.player.stopLeft();
    }
}
