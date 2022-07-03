package managers;

import content.AudioSource;

public class SoundManager {
    public static AudioSource musicSource;
    public static AudioSource environmentSoundSource;

    static {
        musicSource = new AudioSource(1);
        environmentSoundSource = new AudioSource(0);
    }
}
