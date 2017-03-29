package com.smithsgaming.transportmanager.main.world.biome;

import java.awt.*;

/**
 * Created by Tim on 29/03/2016.
 */
public enum Biome {

    OCEAN("Ocean", 0x44447a), LAKE("Lake", 0x336699), BEACH("Beach", 0xa09077), SNOW("Snow", 0xffffff),
    TUNDRA("Tundra", 0xbbbbaa), BARE("Barren Land", 0x888888), SCORCHED("Scorched", 0x555555), TAIGA("Taiga", 0x99aa77),
    TEMPERATE_DESERT("Temperate Desert", 0xc9d29b),
    TEMPERATE_RAIN_FOREST("Temperate Rain Forest", 0x448855), TEMPERATE_DECIDUOUS_FOREST("Temperate Deciduous Forest", 0x679459),
    GRASSLAND("Grassland", 0x88aa55), SUBTROPICAL_DESERT("Subtropical Dessert", 0xd2b98b), SHRUBLAND("Shrubland", 0x889977),
    ICE("Ice", 0x99ffff), MARSH("Marsh", 0x2f6666), TROPICAL_RAIN_FOREST("Tropical Rain Forest", 0x337755),
    TROPICAL_SEASONAL_FOREST("Tropical Seasonal Forest", 0x559944), COAST("Coast", 0x33335a),
    LAKESHORE("Lakeshore", 0x225588), RIVER("River", 0x225588);

    private Color generationColor;
    private String name;

    Biome(String name, int color)
    {
        this(name, new Color(color));
    }

    Biome (String name, Color generationColor) {
        this.name = name;
        this.generationColor = generationColor;
    }

    public Color getGenerationColor () {
        return generationColor;
    }

    public void setGenerationColor (Color generationColor) {
        this.generationColor = generationColor;
    }

    public String getName () {
        return name;
    }

}
