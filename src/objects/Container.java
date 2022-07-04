package objects;

import content.Storage;
import content.Texture;
import managers.SoundManager;
import managers.UserInputManager;
import physics.AABB;
import singletons.SingletonPlayer;

import java.util.ArrayList;

public class Container extends Object {
    public ArrayList<Object> loot;
    private String state;
    private boolean isNeedKey = false;

    public Container(String texture, boolean isNoclip, boolean isLying, int minX, int minY, int maxX, int maxY, AABB collisionBox) {
        super(texture, isNoclip, isLying, minX, minY, maxX, maxY, collisionBox);
        state = "Closed";
        loot = new ArrayList<>();
    }

    @Override
    public void interact(ArrayList<Object> objects) {
        if (UserInputManager.pressedKeyE && AABB.toInteract(SingletonPlayer.player.getCollisionBox(), getCollisionBox())) {
            if (state.equals("Closed")) {
                if (isNeedKey && SingletonPlayer.player.getKeys() > 0) {
                    SingletonPlayer.player.setKeys(SingletonPlayer.player.getKeys() - 1);
                    SoundManager.environmentSoundSource.play(Storage.soundMap.get("openChest"));
                }
                else
                    SoundManager.environmentSoundSource.play(Storage.soundMap.get("openBoxBarrel"));

                state = "Opened";

                if (loot.size() != 0) {
                    objects.addAll(loot);
                    loot.clear();
                }
            }
        }
    }

    @Override
    public void draw() {
        Texture.draw(Storage.textureMap.get(getTexture() + state), new AABB(getMinX(), getMinY(), getMaxX(), getMaxY()));
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean getIsNeedKey(){
        return isNeedKey;
    }

    public void setIsNeedKey(boolean value){
        this.isNeedKey = value;
    }
}