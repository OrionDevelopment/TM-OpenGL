package com.smithsgaming.transportmanager.main.world.structure;

/**
 * Created by Tim on 28/03/2016.
 */
public enum BuildingProperties {
    OFFICE(BuildingType.OFFICE, 3, 4, 10),
    OFFICE_1(BuildingType.OFFICE, 3, 4, 10),
    OFFICE_2(BuildingType.OFFICE, 3, 4, 10),
    HOUSE(BuildingType.RESIDENTIAL, 2, 3, 4);

    private final BuildingType type;
    private final int width, height, connectionIndex;

    BuildingProperties(BuildingType type, int width, int height, int connectionIndex) {
        this.type = type;
        this.width = width;
        this.height = height;
        this.connectionIndex = connectionIndex;
    }

    public BuildingType getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getConnectionIndex() {
        return connectionIndex;
    }

    public enum BuildingType {
        OFFICE, RESIDENTIAL
    }
}
