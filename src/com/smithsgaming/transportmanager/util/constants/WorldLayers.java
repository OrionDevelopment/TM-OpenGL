package com.smithsgaming.transportmanager.util.constants;

import com.smithsgaming.transportmanager.common.world.*;
import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public enum WorldLayers implements IValueContainingEnum {
    WORLD("OverWorld"),
    METRO("MetroWorld");

    private String value;
    private IWorldLayer layerData;

    WorldLayers (String value) {
        this.value = value;
    }

    @Override
    public String getValue () {
        return value;
    }

    public IWorldLayer getLayerData () {
        return layerData;
    }

    public void setLayerData (IWorldLayer layerData) {
        this.layerData = layerData;
    }
}
