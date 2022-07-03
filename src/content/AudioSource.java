package content;

import org.lwjgl.openal.AL10;

public class AudioSource {
    private int audioSourceId;

    public AudioSource(int loop) {
        audioSourceId = AL10.alGenSources();
        AL10.alSourcef(audioSourceId, AL10.AL_GAIN, 0.01f);
        AL10.alSourcef(audioSourceId, AL10.AL_PITCH, 1);
        AL10.alSource3f(audioSourceId, AL10.AL_POSITION, 0, 0, 0);
        AL10.alSourcei(audioSourceId, AL10.AL_LOOPING, loop);
    }

    public void play(int buffer) {
        AL10.alSourcei(audioSourceId, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(audioSourceId);
    }

    public void stop(int buffer) {
        AL10.alSourcei(audioSourceId, AL10.AL_BUFFER, buffer);
        AL10.alSourceStop(audioSourceId);
    }

    public void changeVolume(float volume) {
        AL10.alSourcef(audioSourceId, AL10.AL_GAIN, volume);
    }

    public void delete() {
        AL10.alDeleteSources(audioSourceId);
    }
}
