package com.smithsgaming.transportmanager.common.world;

import com.smithsgaming.transportmanager.common.entity.*;
import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public interface IWorldLayer {
    IWorld getWorld ();

    ITile getTileForPosition (WorldCoordinate worldCoordinate);

    ITileIdentity getTileIdentity (WorldCoordinate worldCoordinate);

    void setTileForPosition (ITile tile, WorldCoordinate worldCoordinate);

    void spawnEntityInstanceInLayer (EntityInstance instance);

    void killEntityInstanceInLayer (EntityInstance instance);

    void killEntityInstanceInLayer (WorldCoordinate pos, float radius);
}
