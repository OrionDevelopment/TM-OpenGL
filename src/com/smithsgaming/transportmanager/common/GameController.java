package com.smithsgaming.transportmanager.common;

import com.smithsgaming.transportmanager.common.registries.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class GameController {
    public static IGame runningInstance;

    public static TileRegistry tileRegistry = new TileRegistry();
    public static TileEntityRegistry tileEntityRegistry = new TileEntityRegistry();
}
