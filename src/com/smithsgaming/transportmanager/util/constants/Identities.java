package com.smithsgaming.transportmanager.util.constants;

import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public enum Identities implements IValueContainingEnum {
    PLAYER("Player-"),

    ///Blocks
    OCEAN("tile.nonbuildable.Ocean"),
    GRASS("tile.buildable.Grass"),
    DRY_GRASS("tile.buildable.DryGrass"),
    BEACH("tile.buildable.Beach"),
    DESERT("tile.buildable.Desert"),
    RIVER("tile.bridgable.River"),
    LAKE("tile.nonbuildable.Lake"),
    SNOW("tile.buildable.Snow"),
    STONE_OVERGROUND("tile.buildable.StoneOverground"),
    STONE_UNDERGROUND("tile.buildable.StoneUnderground"),
    ICE("tile.buildable.Ice"),
    ICE_BUSH_BROWN("tile.buildable.IceBushBrown"),
    ICE_BUSH_YELLOW("tile.buildable.IceBushYellow"),
    SCORCHED("tile.nonbuildable.Scorched"),

    //Buildings
    BUILDING("tile.nonbuildable.Building");

    private final String value;

    Identities (String value) {
        this.value = value;
    }

    @Override
    public String getValue () {
        return value;
    }
}
