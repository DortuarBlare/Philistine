import org.lwjgl.openal.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.openal.AL10.*;

public class AudioMaster {
    private static HashMap<String, Integer> idMap = new HashMap<String, Integer>();

    public static void init(){
        long device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        long context = ALC10.alcCreateContext(device, (IntBuffer) null);
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }

    public static void setListenerData(){
        AL10.alListener3f(AL_POSITION, 0, 0, 0);
        AL10.alListener3f(AL_VELOCITY, 0, 0, 0);
    }

    public static int loadSound(String fileName){
        if (idMap.containsKey(fileName)) return idMap.get(fileName);

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer channelBuffer = stack.mallocInt(1);
            IntBuffer sampleRateBuffer = stack.mallocInt(1);

            ShortBuffer rawAudioBuffer = STBVorbis.stb_vorbis_decode_filename("res/" + fileName + ".ogg", channelBuffer, sampleRateBuffer);
            if (rawAudioBuffer == null) System.out.println("Can't load file " + fileName);
            int channels = channelBuffer.get();
            int sampleRate = sampleRateBuffer.get();
            int id = AL11.alGenBuffers();

            int format = -1;
            if (channels == 1) format = AL_FORMAT_MONO16;
            else if (channels == 2) format = AL_FORMAT_STEREO16;

            alBufferData(id, format, rawAudioBuffer, sampleRate);
            idMap.put(fileName, id);
            return id;
        }
    }

}
