package com.smithsgaming.transportmanager.util.constants;

import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public enum Identities implements IValueContainingEnum {
    PLAYER("Player-"),

    ///Blocks
    OCEAN("Tiles.NonBuildable.Ocean"),
    LAKE("Tiles.NonBuildable.Lake"),
    SCORCHED("Tiles.NonBuildable.Scorched"),
    OCEAN("Tiles.NonBuildable.Ocean"),
    OCEAN("Tiles.NonBuildable.Ocean"),
    OCEAN("Tiles.NonBuildable.Ocean"),
    OCEAN("Tiles.NonBuildable.Ocean"),;

    private final String value;

    Identities (String value) {
        this.value = value;
    }

    @Override
    public String getValue () {
        return value;
    }
}
