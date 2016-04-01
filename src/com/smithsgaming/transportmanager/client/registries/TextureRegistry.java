package com.smithsgaming.transportmanager.client.registries;

import com.smithsgaming.transportmanager.client.graphics.Display;
import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.render.textures.Texture;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.opengl.*;

import java.awt.*;
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

    private TextureRegistry () {
    }

    public Texture loadTexture (String fileName) {
        Texture texture = ResourceUtil.loadStitchablePNGTexture(fileName);

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

    public Texture initializeTextureStitching (int textureStitchingId) {
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

    public void unLoad () {
        bufferedTextures.values().forEach(OpenGLUtil::destroyTexture);

        bufferedTextures.clear();
    }

    public static class Textures {
        public static void init () {
            SkyBox.skyBoxOcean = ResourceUtil.loadStitchablePNGTexture("/textures/tiles/world/deepWater_0.png");
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

    public static class Fonts {
        public static TrueTypeFont Courier;
        public static TrueTypeFont TimesNewRoman;

        public static void init () {
            Courier = new TrueTypeFont(new Font("Courier", java.awt.Font.PLAIN, 25), false);
            TimesNewRoman = new TrueTypeFont(new Font("Times New Roman", java.awt.Font.PLAIN, 25), true);
        }

        public static void unLoad () {
            Courier.destroy();
            TimesNewRoman.destroy();
        }
    }
}
