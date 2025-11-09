package com.alu.engine.rendering;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.io.IOException;

import static com.alu.engine.utils.ResourceUtil.getResourceAsStream;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    private int textureId;

    void init() {
        textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);


        try (final var inputStream = getResourceAsStream("stone.png")) {
            var bufferedImage = ImageIO.read(inputStream);

            var width = bufferedImage.getWidth();
            var height = bufferedImage.getHeight();

            var pixels = new int[width * height];
            bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);

            // Flip RGB order of the integers
            for (int i = 0; i < pixels.length; i++) {
                int alpha = pixels[i] >> 24 & 0xFF;
                int red = pixels[i] >> 16 & 0xFF;
                int green = pixels[i] >> 8 & 0xFF;
                int blue = pixels[i] & 0xFF;

                // ARGB to ABGR
                pixels[i] = alpha << 24 | blue << 16 | green << 8 | red;
            }

            final var byteBuffer = BufferUtils.createByteBuffer(width * height * 4);
            byteBuffer.asIntBuffer().put(pixels);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
            glGenerateMipmap(GL_TEXTURE_2D);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
