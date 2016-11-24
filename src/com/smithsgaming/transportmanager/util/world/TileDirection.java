package com.smithsgaming.transportmanager.util.world;

import com.smithsgaming.transportmanager.util.math.Vector2i;

import java.util.HashMap;

/**
 * Created by marcf on 11/18/2016.
 */
public enum TileDirection {

    TOPLEFT(new Vector2i(-1, -1)),
    TOP(new Vector2i(-1, 0)),
    TOPRIGHT(new Vector2i(-1, 1)),
    RIGHT(new Vector2i(0, 1)),
    BOTTOMRIGHT(new Vector2i(1,1)),
    BOTTOM(new Vector2i(1,0)),
    BOTTOMLEFT(new Vector2i(1, -1)),
    LEFT(new Vector2i(0, -1));


    TileDirection(Vector2i offset) {
        this.offset = offset;
    }

    Vector2i offset;

    private static HashMap<TileDirection, TileDirection> opositeResolver = new HashMap<>();

    static {
        opositeResolver.put(TOPLEFT, BOTTOMRIGHT);
        opositeResolver.put(TOP, BOTTOMRIGHT);
        opositeResolver.put(TOPRIGHT, BOTTOMLEFT);
        opositeResolver.put(RIGHT, LEFT);
        opositeResolver.put(BOTTOMRIGHT, TOPLEFT);
        opositeResolver.put(BOTTOM, TOP);
        opositeResolver.put(BOTTOMLEFT, TOPRIGHT);
        opositeResolver.put(LEFT, RIGHT);
    }

    public Vector2i getOffset() {
        return new Vector2i(offset);
    }

    public TileDirection getOposite() {
        return opositeResolver.get(this);
    }

    public Vector2i offset(Vector2i source) {
        return new Vector2i(source).add(getOffset());
    }
}
