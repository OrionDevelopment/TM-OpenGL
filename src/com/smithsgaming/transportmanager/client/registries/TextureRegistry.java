package com.smithsgaming.transportmanager.client.registries;

import com.smithsgaming.transportmanager.util.*;

import java.nio.*;
import java.util.*;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class TextureRegistry {
    public static final TextureRegistry instance = new TextureRegistry();

    public HashMap<Integer, Texture> bufferedTextures = new HashMap<>();

    private TextureRegistry () {
    }

    public Texture loadTexture (String fileName) {
        Texture texture = ResourceUtil.loadPNGTexture(fileName);
        OpenGLUtil.loadTextureIntoGPU(texture);

        bufferedTextures.put(texture.getOpenGLTextureId(), texture);

        return texture;
    }

    public Texture getTextureForOpenGLID (int id) {
        return bufferedTextures.get(id);
    }

    public void unLoad () {
        bufferedTextures.values().forEach(OpenGLUtil::destroyTexture);

        bufferedTextures.clear();
    }

    public static class Texture {
        private ByteBuffer data;
        private int width;
        private int height;

        private int openGLTextureId;
        private int boundTextureUnit;

        public Texture (ByteBuffer data, int width, int height) {
            this.data = data;
            this.width = width;
            this.height = height;
        }

        public ByteBuffer getData () {
            return data;
        }

        public int getWidth () {
            return width;
        }

        public int getHeight () {
            return height;
        }

        public int getOpenGLTextureId () {
            return openGLTextureId;
        }

        public void setOpenGLTextureId (int openGLTextureId) {
            this.openGLTextureId = openGLTextureId;
        }

        public int getBoundTextureUnit () {
            return boundTextureUnit;
        }

        public void setBoundTextureUnit (int boundTextureUnit) {
            this.boundTextureUnit = boundTextureUnit;
        }
    }

    public static class Textures {
        public static Texture deepWater;

        public static void init () {
            deepWater = TextureRegistry.instance.loadTexture("/textures/deepWater_0.png");
        }
    }
}
