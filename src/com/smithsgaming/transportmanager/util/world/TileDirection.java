package com.smithsgaming.transportmanager.util.world;

import com.smithsgaming.transportmanager.util.math.Vector2i;

import java.util.HashMap;

/**
 * Created by marcf on 11/18/2016.
 */
public enum TileDirection {

    TOPLEFT(new Vector2i(-1, -1)),
    TOP(new Vector2i(0, -1)),
    TOPRIGHT(new Vector2i(1, -1)),
    RIGHT(new Vector2i(1, 0)),
    BOTTOMRIGHT(new Vector2i(1,1)),
    BOTTOM(new Vector2i(0,1)),
    BOTTOMLEFT(new Vector2i(-1, 1)),
    LEFT(new Vector2i(-1, 0));


    TileDirection(Vector2i offset) {
        this.offset = offset;
    }

    Vector2i offset;

    private static HashMap<Vector2i, TileDirection> opositeResolver = null;


    public Vector2i getOffset() {
        return new Vector2i(offset);
    }

    public TileDirection getOposite() {
        if (opositeResolver == null) {
            opositeResolver = new HashMap<>();
            for (TileDirection direction : values()) {
                opositeResolver.put(direction.getOffset().multiply(-1), direction);
            }
        }

        return opositeResolver.get(getOffset());
    }

    public Vector2i offset(Vector2i source) {
        return new Vector2i(source).add(getOffset());
    }
}
