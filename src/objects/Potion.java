package objects;

import content.Storage;
import managers.SoundManager;
import physics.AABB;
import singletons.SingletonPlayer;

import java.util.ArrayList;

public class Potion extends Object {
    public Potion(String texture, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
    }

    @Override
    public void interact(ArrayList<Object> objects) {
        if (AABB.AABBvsAABB(SingletonPlayer.player.getCollisionBox(), getCollisionBox())) {
            objects.remove(this);

            if (SingletonPlayer.player.getHealth() > 90)
                SingletonPlayer.player.setHealth(100);
            else
                SingletonPlayer.player.setHealth(SingletonPlayer.player.getHealth() + 10);

            SoundManager.environmentSoundSource.play(Storage.soundMap.get("pickedPotion"));
        }
    }
}
