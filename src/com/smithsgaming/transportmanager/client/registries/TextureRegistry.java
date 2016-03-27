package com.smithsgaming.transportmanager.client.registries;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.util.*;

import java.nio.*;
import java.util.*;
import java.util.stream.*;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class TextureRegistry {
    public static final TextureRegistry instance = new TextureRegistry();

    public HashMap<Integer, Texture> bufferedTextures = new HashMap<>();
    public HashMap<Integer, Texture> stitchedTextures = new HashMap<>();

    private TextureRegistry() {
    }

    public Texture loadTexture(String fileName) {
        Texture texture = ResourceUtil.loadStitchablePNGTexture(fileName);

        return this.loadTexture(texture);
    }

    public Texture loadTexture(Texture toLoad) {
        if (!toLoad.isRequiringTextureStitching())
            OpenGLUtil.loadTextureIntoGPU(toLoad);

        bufferedTextures.put(toLoad.getOpenGLTextureId(), toLoad);

        return toLoad;
    }

    public Texture getTextureForOpenGLID(int id) {
        return bufferedTextures.get(id);
    }

    public Texture initializeTextureStitching(int textureStitchingId) {
        ArrayList<Texture> texturesToCombine = bufferedTextures.values().stream().filter(texture -> texture.getTextureStitchId() == textureStitchingId && texture.isRequiringTextureStitching() && !texture.isStitched()).collect(Collectors.toCollection(ArrayList::new));

        TextureStitcher stitcher = new TextureStitcher(Display.getMaxTextureSize(), Display.getMaxTextureSize(), true);
        stitcher.addSprites(texturesToCombine);

        Texture stitchedTexture = new Texture("Stitched-" + textureStitchingId, ByteBuffer.allocateDirect(
                4 * stitcher.getCurrentStitchedWidth() * stitcher.getCurrentStitchedHeight()), stitcher.getCurrentStitchedWidth(), stitcher.getCurrentStitchedHeight(), 0, 0, false, false, textureStitchingId);

        OpenGLUtil.loadTextureIntoGPU(stitchedTexture);

        texturesToCombine = (ArrayList<Texture>) stitcher.getStitchSlots();

        for (Texture texture : texturesToCombine) {
            OpenGLUtil.loadSubTextureRegionIntoGPU(stitchedTexture, texture);
            texture.setBoundTextureUnit(stitchedTexture.getBoundTextureUnit());
            texture.setOpenGLTextureId(stitchedTexture.getOpenGLTextureId());
        }

        return null;
    }

    public void unLoad() {
        bufferedTextures.values().forEach(OpenGLUtil::destroyTexture);

        bufferedTextures.clear();
    }

    public static class Texture {
        private String textureName;

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


        public Texture(String textureName, ByteBuffer data, int width, int height) {
            this(textureName, data, width, height, 0, 0, false, true, 0);
        }

        public Texture(String textureName, ByteBuffer data, int width, int height, float u, float v, boolean isStitched, boolean requiringTextureStitching, int textureStitchId) {
            this.textureName = textureName;
            this.isStitched = isStitched;
            this.requiringTextureStitching = requiringTextureStitching;
            this.textureStitchId = textureStitchId;
            this.height = height;
            this.width = width;
            this.data = data;
            this.u = u;
            this.v = v;
        }

        public String getTextureName() {
            return textureName;
        }

        public ByteBuffer getData() {
            return data;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getOpenGLTextureId() {
            return openGLTextureId;
        }

        public void setOpenGLTextureId(int openGLTextureId) {
            this.openGLTextureId = openGLTextureId;
        }

        public int getBoundTextureUnit() {
            return boundTextureUnit;
        }

        public void setBoundTextureUnit(int boundTextureUnit) {
            this.boundTextureUnit = boundTextureUnit;
        }

        public float getV() {
            return v;
        }

        public void setV(float v) {
            this.v = v;
        }

        public float getU() {
            return u;
        }

        public void setU(float u) {
            this.u = u;
        }

        public int getTextureStitchId() {
            return textureStitchId;
        }

        public void setTextureStitchId(int textureStitchId) {
            this.textureStitchId = textureStitchId;
        }

        public boolean isStitched() {
            return isStitched;
        }

        public void setStitched(boolean stitched) {
            isStitched = stitched;
        }

        public boolean isRequiringTextureStitching() {
            return requiringTextureStitching;
        }

        public void setRequiringTextureStitching(boolean requiringTextureStitching) {
            this.requiringTextureStitching = requiringTextureStitching;
        }
    }


    public static class Textures {
        public static void init() {
            SkyBox.skyBoxOcean = ResourceUtil.loadStitchablePNGTexture("/textures/deepWater_0.png");
            SkyBox.skyBoxOcean.setRequiringTextureStitching(false);

            TextureRegistry.instance.loadTexture(SkyBox.skyBoxOcean);

            Tiles.deepWater = TextureRegistry.instance.loadTexture("/textures/tiles/world/deepWater_0.png");
            Tiles.grass = TextureRegistry.instance.loadTexture("/textures/tiles/world/grass.png");
            Tiles.dryGrass = TextureRegistry.instance.loadTexture("/textures/tiles/world/dryGrass.png");
            Tiles.beach = TextureRegistry.instance.loadTexture("/textures/tiles/world/beach.png");
            Tiles.desert = TextureRegistry.instance.loadTexture("/textures/tiles/world/desert.png");
            Tiles.river = TextureRegistry.instance.loadTexture("/textures/tiles/world/shallowWater_0.png");
            Tiles.snow = TextureRegistry.instance.loadTexture("/textures/tiles/world/snow.png");
            Tiles.stoneOverground = TextureRegistry.instance.loadTexture("/textures/tiles/world/stoneOverground.png");
            Tiles.stoneUnderground = TextureRegistry.instance.loadTexture("/textures/tiles/world/stoneUnderground.png");
            Tiles.ice = TextureRegistry.instance.loadTexture("/textures/tiles/world/ice_0.png");
        }

        public static class SkyBox {
            public static Texture skyBoxOcean;
        }

        public static class Tiles {
            public static Texture deepWater;
            public static Texture grass;
            public static Texture dryGrass;
            public static Texture beach;
            public static Texture desert;
            public static Texture river;
            public static Texture snow;
            public static Texture stoneOverground;
            public static Texture stoneUnderground;
            public static Texture ice;
        }
    }
}
