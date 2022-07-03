package gui;

import content.Storage;
import content.Texture;
import math.SimpleMath;
import physics.AABB;
import objects.Coin;
import singletons.SingletonPlayer;
public class HUD {
    private static int healthToDraw = 0;
    private static int armorToDraw = 0;
    private static Coin coinToDraw;
    private static int coinAmountToDraw = 0;
    private static int keysAmountToDraw = 0;

    static {
        coinToDraw = new Coin("coin_01", true, false, 0, 0, 0, 0, new AABB());
        coinToDraw.getTimer().schedule(coinToDraw.getAnimationTask(), 0, 120);
    }

    public static void update() {
        healthToDraw =
                SingletonPlayer.player.getHealth() % 10 == 0 ?
                        SingletonPlayer.player.getHealth() :
                        SingletonPlayer.player.getHealth() - (SingletonPlayer.player.getHealth() % 10) + 10;

        armorToDraw =
                SingletonPlayer.player.getArmor() % 5 == 0 ?
                        SingletonPlayer.player.getArmor() :
                        SingletonPlayer.player.getArmor() - (SingletonPlayer.player.getArmor() % 5);

        coinAmountToDraw = SingletonPlayer.player.getMoney();
        keysAmountToDraw = SingletonPlayer.player.getKeys();
    }

    public static void draw() {
        // Полоска здоровья
        if (healthToDraw > 0)
            Texture.draw(Storage.textureMap.get(healthToDraw + "hp"), new AABB(0, 0, 103, 18));
        else
            Texture.draw(Storage.textureMap.get("0hp"), new AABB(0, 0, 103, 18));

        // Щиты с броней
        Texture.draw(Storage.textureMap.get(armorToDraw + "armor"), new AABB(0, 20, 60, 40));

        // Монета
        Texture.draw(
                Storage.textureMap.get("coin_0" + coinToDraw.getAnimationTime()),
                new AABB(0, 42, 11, 54)
        );

        // Количество монет
        int minX = 13 + (SimpleMath.getCountsOfDigits(SingletonPlayer.player.getMoney()) * 7) - 7;
        int maxX = 13 + (SimpleMath.getCountsOfDigits(SingletonPlayer.player.getMoney()) * 7);
        for (int i = 0; i < SimpleMath.getCountsOfDigits(SingletonPlayer.player.getMoney()); i++) {
            Texture.draw(Storage.textureMap.get("number_" + coinAmountToDraw % 10), new AABB(minX, 44, maxX, 54));

            coinAmountToDraw /= 10;
            minX -= 7;
            maxX -= 7;
        }

        // Золотой ключ
        Texture.draw(Storage.textureMap.get("keyGold"), new AABB(0, 55, 16, 71));

        // Количество ключей
        minX = 16 + (SimpleMath.getCountsOfDigits(SingletonPlayer.player.getKeys()) * 7) - 7;
        maxX = 16 + (SimpleMath.getCountsOfDigits(SingletonPlayer.player.getKeys()) * 7);
        for (int i = 0; i < SimpleMath.getCountsOfDigits(SingletonPlayer.player.getKeys()); i++) {
            Texture.draw(
                    Storage.textureMap.get("number_" + keysAmountToDraw % 10),
                    new AABB(minX, 60, maxX, 70)
            );

            keysAmountToDraw /= 10;
            minX -= 7;
            maxX -= 7;
        }
    }
}
