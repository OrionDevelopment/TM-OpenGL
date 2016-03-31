package com.smithsgaming.transportmanager.client.render.textures;

import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;

/**
 * @Author Marc (Created on: 31.03.2016)
 */
public class StandardTileTexture extends TextureRegistry.Texture {

    private StandardTileTexture (TextureRegistry.Texture source, String tileIdentity) {
        super(tileIdentity, source.getData(), source.getWidth(), source.getHeight());
    }

    public static StandardTileTexture loadTexture (String tileIdentity, String texturePath) {
        return new StandardTileTexture(ResourceUtil.loadPNGTexture(texturePath), tileIdentity);
    }
}
