package content;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import physics.AABB;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Texture {
    private static HashMap<String, Integer> idMap = new HashMap<>();

    public static int loadTexture(String resourceName) {
        if (idMap.containsKey(resourceName)) return idMap.get(resourceName);

        int width, height;
        ByteBuffer buffer;

        try (MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            File file = new File("res/" + resourceName + ".png");
            String filePath = file.getAbsolutePath();
            buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
            if (buffer == null) throw new Exception("Can't load file " + resourceName + " " + STBImage.stbi_failure_reason());
            width = w.get();
            height = h.get();

            int id = GL11.glGenTextures();
            idMap.put(resourceName, id);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            STBImage.stbi_image_free(buffer);
            return id;
        }
        catch (Exception e) { e.printStackTrace(); }

        return 0;
    }

    public static void draw(int target, int texture, AABB position) {
        glBindTexture(target, texture);
        glBegin(GL_QUADS);
        glTexCoord2d(0, 0);
        glVertex2f(position.getMin().x, position.getMin().y);
        glTexCoord2d(1, 0);
        glVertex2f(position.getMax().x, position.getMin().y);
        glTexCoord2d(1, 1);
        glVertex2f(position.getMax().x, position.getMax().y);
        glTexCoord2d(0, 1);
        glVertex2f(position.getMin().x, position.getMax().y);
        glEnd();
    }

    public static void draw(int texture, AABB position) {
        draw(GL_TEXTURE_2D, texture, position);
    }
}