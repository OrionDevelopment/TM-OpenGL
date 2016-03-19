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
    public HashMap<Integer, Texture> stitchedTextures = new HashMap<>();

    private TextureRegistry () {
    }

    public Texture loadTexture (String fileName) {
        Texture texture = ResourceUtil.loadPNGTexture(fileName);

        return this.loadTexture(texture);
    }

    public Texture loadTexture (Texture toLoad) {
        if (!toLoad.isRequiringTextureStitching())
            OpenGLUtil.loadTextureIntoGPU(toLoad);

        bufferedTextures.put(toLoad.getOpenGLTextureId(), toLoad);

        return toLoad;
    }

    public Texture getTextureForOpenGLID (int id) {
        return bufferedTextures.get(id);
    }

    public Texture initializeTextureStitching (int textureStichtingId) {
        ArrayList<Texture> texturesToCombine = new ArrayList<>();

        for (Texture texture : bufferedTextures.values()) {
            if (texture.getTextureStitchId() == textureStichtingId && texture.isRequiringTextureStitching() && !texture.isStitched()) {

            }
        }

        return null;
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

        private boolean requiringTextureStitching;
        private boolean isStitched;
        private int textureStitchId;

        private float u;
        private float v;


        public Texture (ByteBuffer data, int width, int height) {
            this(false, true, 0, data, width, height, 0, 0);
        }

        public Texture (boolean isStitched, boolean requiringTextureStitching, int textureStitchId, ByteBuffer data, int width, int height, float u, float v) {
            this.isStitched = isStitched;
            this.requiringTextureStitching = requiringTextureStitching;
            this.textureStitchId = textureStitchId;
            this.height = height;
            this.width = width;
            this.data = data;
            this.u = u;
            this.v = v;
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

        public float getV () {
            return v;
        }

        public void setV (float v) {
            this.v = v;
        }

        public float getU () {
            return u;
        }

        public void setU (float u) {
            this.u = u;
        }

        public int getTextureStitchId () {
            return textureStitchId;
        }

        public void setTextureStitchId (int textureStitchId) {
            this.textureStitchId = textureStitchId;
        }

        public boolean isStitched () {
            return isStitched;
        }

        public void setStitched (boolean stitched) {
            isStitched = stitched;
        }

        public boolean isRequiringTextureStitching () {
            return requiringTextureStitching;
        }

        public void setRequiringTextureStitching (boolean requiringTextureStitching) {
            this.requiringTextureStitching = requiringTextureStitching;
        }
    }


    public static class Textures {
        public static void init () {
            SkyBox.skyBoxOcean = ResourceUtil.loadPNGTexture("/textures/deepWater_0.png");
            SkyBox.skyBoxOcean.setRequiringTextureStitching(false);

            TextureRegistry.instance.loadTexture(SkyBox.skyBoxOcean);

            Tiles.deepWater = TextureRegistry.instance.loadTexture("/textures/deepWater_0.png");
        }

        public static class SkyBox {
            public static Texture skyBoxOcean;
        }

        public static class Tiles {
            public static Texture deepWater;
        }
    }
}
