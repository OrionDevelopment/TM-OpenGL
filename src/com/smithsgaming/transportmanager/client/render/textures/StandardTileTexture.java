package com.smithsgaming.transportmanager.client.render.textures;

import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;

/**
 * @Author Marc (Created on: 31.03.2016)
 */
public class StandardTileTexture extends Texture {

    private StandardTileTexture (Texture source, String tileIdentity) {
        super(tileIdentity, source.getData(), source.getPixelWidth(), source.getPixelHeight());

        setRequiringTextureStitching(true);
    }

    public static Texture loadTexture (String tileIdentity, String texturePath) {
        return TextureRegistry.instance.loadTexture(new StandardTileTexture(ResourceUtil.loadPNGTexture(texturePath), tileIdentity));
    }
}
