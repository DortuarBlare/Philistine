package levels;

import content.Storage;
import content.Texture;
import managers.LevelManager;
import managers.SoundManager;
import managers.UserInputManager;
import mobs.Waiter;
import physics.AABB;
import singletons.SingletonPlayer;

public class TavernLevel implements Level {
    private Waiter waiter;

    @Override
    public void init() {
        waiter = new Waiter(168, 136, 1);
    }

    @Override
    public void update() {
        collide();
        waiter.simulateBehavior();

        if (waiter.isWaitingTaskStarted()) {
            // Восстановить здоровье
            if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("toBuyBeer"))) {
                if (UserInputManager.pressedKeyE && SingletonPlayer.player.readyToHeal()) {
                    SingletonPlayer.player.setHealth(100);
                    SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() - 10);
                    SoundManager.environmentSoundSource.play(Storage.soundMap.get("drinkBeer"));
                }
            }
        }

        // Покупка ключей
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("toBuyKey"))) {
            if (UserInputManager.pressedKeyE && SingletonPlayer.player.getMoney() >= 20) {
                SingletonPlayer.player.setMoney(SingletonPlayer.player.getMoney() - 20);
                SingletonPlayer.player.setKeys(SingletonPlayer.player.getKeys() + 1);
                SoundManager.environmentSoundSource.play(Storage.soundMap.get("boughtKey"));
            }
        }

        // Проверка выхода в город
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("entranceFromTavernToTown")))
            LevelManager.tavernToTown();
    }

    @Override
    public void draw() {
        Texture.draw(Storage.textureMap.get("tavern"), new AABB(0, 0, 640, 360));
        waiter.draw(); // Оффициантка
        // Торговец ключами
        Texture.draw(
                Storage.textureMap.get("gentleman_brown"),
                new AABB(350, 190, 350 + 64, 190 + 64)
        );

        // Восполнение здоровья
        if (waiter.isWaitingTaskStarted()) {
            if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("toBuyBeer"))) {
                Texture.draw(
                        Storage.textureMap.get("emotion_coin"),
                        new AABB(
                                SingletonPlayer.player.getX() + 32, SingletonPlayer.player.getY(),
                                SingletonPlayer.player.getX() + 52, SingletonPlayer.player.getY() + 20
                        )
                );
                Texture.draw(
                        Storage.textureMap.get("emotion_health"),
                        new AABB(
                                waiter.getX() + 31,
                                waiter.getY(),
                                waiter.getX() + 51,
                                waiter.getY() + 20
                        )
                );
            }
        }

        // Покупка ключей
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("toBuyKey"))) {
            Texture.draw(
                    Storage.textureMap.get("emotion_key"),
                    new AABB(381, 182, 381 + 20, 182 + 20)
            );
            Texture.draw(
                    Storage.textureMap.get("emotion_coin"),
                    new AABB(
                            SingletonPlayer.player.getX() + 32,
                            SingletonPlayer.player.getY(),
                            SingletonPlayer.player.getX() + 52,
                            SingletonPlayer.player.getY() + 20
                    )
            );
        }
    }

    @Override
    public void collide() {
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall3")) ||
                AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall5")))
            SingletonPlayer.player.stopRight();
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall1")) ||
                AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall6")))
            SingletonPlayer.player.stopLeft();
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall0")) ||
                AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall4")))
            SingletonPlayer.player.stopDown();
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), Storage.aabbMap.get("wall2")))
            SingletonPlayer.player.stopUp();
    }
}
