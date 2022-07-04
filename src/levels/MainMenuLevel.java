package levels;

import content.Storage;
import content.Texture;
import managers.SoundManager;
import physics.AABB;
import singletons.SingletonPlayer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glTranslated;

public class MainMenuLevel implements Level {
    private int guard_i = 1, guard_g = 0;
    private boolean forMainMenu = true;

    @Override
    public void init() {}

    @Override
    public void update() {
        if (SingletonPlayer.player.getX() == 1186)
            forMainMenu = false;
        else if (SingletonPlayer.player.getX() == 290)
            forMainMenu = true;

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

        Texture.draw(
                Storage.textureMap.get("Philistine"),
                new AABB(
                        SingletonPlayer.player.getX() - 68,
                        SingletonPlayer.player.getY() - 156,
                        SingletonPlayer.player.getX() + 64 + 68,
                        SingletonPlayer.player.getY() - 100
                )
        );
        Texture.draw(
                Storage.textureMap.get("Press_enter"),
                new AABB(
                        SingletonPlayer.player.getX() - 18,
                        SingletonPlayer.player.getY() - 100,
                        SingletonPlayer.player.getX() + 82,
                        SingletonPlayer.player.getY() - 72
                )
        );
    }

    @Override
    public void collide() {}
}
