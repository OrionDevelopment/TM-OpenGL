package com.smithsgaming.transportmanager.client.registries;

import com.smithsgaming.transportmanager.client.*;
import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.graphics.Display;
import com.smithsgaming.transportmanager.client.render.textures.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.opengl.*;

import javax.imageio.*;
import javax.imageio.stream.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.*;
import java.util.*;
import java.util.stream.*;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class TextureRegistry {
    public static final TextureRegistry instance = new TextureRegistry();

    public HashMap<String, Texture> namedBufferedTextured = new HashMap<>();
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
        namedBufferedTextured.put(toLoad.getTextureName(), toLoad);

        return toLoad;
    }

    public Texture getTextureForName(String name) {
        return namedBufferedTextured.get(name);
    }

    public Texture getTextureForOpenGLID(int id) {
        return bufferedTextures.get(id);
    }

    public Texture initializeTextureStitching(int textureStitchingId) {
        ArrayList<Texture> texturesToCombine = namedBufferedTextured.values().stream().filter(texture -> texture.getTextureStitchId() == textureStitchingId && texture.isRequiringTextureStitching() && !texture.isStitched()).collect(Collectors.toCollection(ArrayList::new));

        TextureStitcher stitcher = new TextureStitcher(Display.getMaxTextureSize(), Display.getMaxTextureSize(), true);
        stitcher.addSprites(texturesToCombine);

        stitcher.doStitch();

        texturesToCombine = (ArrayList<Texture>) stitcher.getStitchSlots();

        Texture stitchedTexture = new Texture("Stitched-" + textureStitchingId, null, stitcher.getCurrentStitchedWidth(), stitcher.getCurrentStitchedHeight(), 0, 0, false, false, textureStitchingId);

        OpenGLUtil.loadTextureIntoGPU(stitchedTexture);

        for (Texture texture : texturesToCombine) {
            OpenGLUtil.loadSubTextureRegionIntoGPU(stitchedTexture, texture);
            texture.setBoundTextureUnit(stitchedTexture.getBoundTextureUnit());
            texture.setOpenGLTextureId(stitchedTexture.getOpenGLTextureId());
        }


        if (TransportManagerClient.instance.getSettings().isShouldWriteTextureStichtedImagesToDisk()) {
            ByteBuffer pixelData = ByteBuffer.allocateDirect(
                    4 * stitcher.getCurrentStitchedWidth() * stitcher.getCurrentStitchedHeight());

            GL13.glActiveTexture(stitchedTexture.getBoundTextureUnit());
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, stitchedTexture.getOpenGLTextureId());
            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, stitchedTexture.getFormat(), GL11.GL_UNSIGNED_BYTE, pixelData);

            try {
                File imageFile = new File("resource-test/textures/stitched/" + stitchedTexture.getTextureName() + ".png");

                if (!imageFile.exists()) {
                    imageFile.getParentFile().mkdirs();
                }

                FileImageOutputStream writer = new FileImageOutputStream(imageFile);

                BufferedImage image = new BufferedImage(stitchedTexture.getPixelWidth(), stitchedTexture.getPixelHeight(), BufferedImage.TYPE_4BYTE_ABGR);

                for (int x = 0; x < stitcher.getCurrentStitchedWidth(); x++) {
                    for (int y = 0; y < stitcher.getCurrentStitchedHeight(); y++) {
                        int i = ( x + ( stitcher.getCurrentStitchedWidth() * y ) ) * 4;
                        int r = pixelData.get(i) & 0xFF;
                        int g = pixelData.get(i + 1) & 0xFF;
                        int b = pixelData.get(i + 2) & 0xFF;
                        int a = pixelData.get(i + 3) & 0xFF;
                        image.setRGB(x, stitcher.getCurrentStitchedHeight() - ( y + 1 ), ( a << 24 ) | ( r << 16 ) | ( g << 8 ) | b);
                    }
                }

                ImageIO.write(image, "PNG", writer);
                writer.close();
            } catch (Exception ex) {
                System.out.println("Failed to write stitched image to disk:");
                ex.printStackTrace();
            }
        }

        bufferedTextures.put(stitchedTexture.getOpenGLTextureId(), stitchedTexture);
        namedBufferedTextured.put(stitchedTexture.getTextureName(), stitchedTexture);

        return stitchedTexture;
    }

    public void unLoad() {
        bufferedTextures.values().forEach(OpenGLUtil::destroyTexture);

        bufferedTextures.clear();
        namedBufferedTextured.clear();
    }

    public static class Textures {
        public static void init() {
            TransportManagerClient.clientLogger.info("Loading Textures.");
            SkyBox.skyBoxOcean = ResourceUtil.loadPNGTexture("/textures/tiles/world/deepWater_0.png");

            TextureRegistry.instance.loadTexture(SkyBox.skyBoxOcean);

            Tiles.deepWater = StandardTileTexture.loadTexture(TileNames.OCEAN, "/textures/tiles/world/deepWater_0.png");
            Tiles.grass = StandardTileTexture.loadTexture(TileNames.GRASS, "/textures/tiles/world/grass.png");
            Tiles.dryGrass = StandardTileTexture.loadTexture(TileNames.DRY_GRASS, "/textures/tiles/world/dryGrass.png");
            Tiles.beach = StandardTileTexture.loadTexture(TileNames.BEACH, "/textures/tiles/world/beach.png");
            Tiles.desert = StandardTileTexture.loadTexture(TileNames.DESERT, "/textures/tiles/world/desert.png");
            Tiles.river = StandardTileTexture.loadTexture(TileNames.RIVER, "/textures/tiles/world/shallowWater_0.png");
            Tiles.lake = StandardTileTexture.loadTexture(TileNames.LAKE, "/textures/tiles/world/shallowWater_0.png");
            Tiles.snow = StandardTileTexture.loadTexture(TileNames.SNOW, "/textures/tiles/world/snow.png");
            Tiles.stoneOverground = StandardTileTexture.loadTexture(TileNames.STONE_OVERGROUND, "/textures/tiles/world/stoneOverground.png");
            Tiles.stoneUnderground = StandardTileTexture.loadTexture(TileNames.STONE_UNDERGROUND, "/textures/tiles/world/stoneUnderground.png");
            Tiles.ice = StandardTileTexture.loadTexture(TileNames.ICE, "/textures/tiles/world/ice_0.png");
            Tiles.ice_bush_brown = StandardTileTexture.loadTexture(TileNames.ICE_BUSH_BROWN, "/textures/tiles/world/ice_1.png");
            Tiles.scorched = StandardTileTexture.loadTexture(TileNames.SCORCHED, "/textures/tiles/world/scorchedStone.png");

            TransportManagerClient.clientLogger.debug("Starting Texturestitching.");
            TextureRegistry.instance.initializeTextureStitching(0);
            TransportManagerClient.clientLogger.debug("Finished Texturestitching.");
            TransportManagerClient.clientLogger.info("Finished loading Textures.");

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
            public static Texture ice_bush_brown;
            public static Texture scorched;
            public static Texture lake;
        }
    }

    public static class Fonts {
        public static TrueTypeFont Courier;
        public static TrueTypeFont TimesNewRoman;

        public static void init() {
            TransportManagerClient.clientLogger.info("Loading Fonts.");

            Courier = new TrueTypeFont(new Font("Courier", java.awt.Font.PLAIN, 25), false);
            TimesNewRoman = new TrueTypeFont(new Font("Times New Roman", java.awt.Font.PLAIN, 25), true);

            TransportManagerClient.clientLogger.info("Finished loading Fonts.");
        }

        public static void unLoad() {
            if (Courier == null)
                return;

            Courier.destroy();
            TimesNewRoman.destroy();

            Courier = null;
            TimesNewRoman = null;
        }
    }
}
