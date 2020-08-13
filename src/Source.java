import org.lwjgl.openal.AL10;

public class Source {
    private int sourceId;

    public Source(int loop) {
        sourceId = AL10.alGenSources();
        AL10.alSourcef(sourceId, AL10.AL_GAIN, 0.01f);
        AL10.alSourcef(sourceId, AL10.AL_PITCH, 1);
        AL10.alSource3f(sourceId, AL10.AL_POSITION, 0, 0, 0);
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop);
    }

    public void play(int buffer) {
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(sourceId);
    }

    public void stop(int buffer) {
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
        AL10.alSourceStop(sourceId);
    }

    public void changeVolume(float volume) {
        AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
    }

    public void delete() {
        AL10.alDeleteSources(sourceId);
    }
}
