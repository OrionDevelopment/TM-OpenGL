package com.smithsgaming.transportmanager.client.render.core.textures;

import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.util.ResourceUtil;

/**
 * @Author Marc (Created on: 31.03.2016)
 */
public class StandardTileTexture extends Texture {

    private StandardTileTexture (Texture source, String tileIdentity) {
        super(tileIdentity, source.getData(), source.getPixelWidth(), source.getPixelHeight(), false);

        setRequiringTextureStitching(true);
    }

    public static Texture loadTexture (String tileIdentity, String texturePath) {
        return TextureRegistry.instance.loadTexture(new StandardTileTexture(ResourceUtil.loadStitchablePNGTexture(texturePath), tileIdentity));
    }

    @Override
    public Texture clip(int minX, int minY, int maxX, int maxY, String textureAppendix) {
        Texture clipped = super.clip(minX, minY, maxX, maxY, textureAppendix);

        return new StandardTileTexture(clipped, clipped.getTextureName());
    }
}
