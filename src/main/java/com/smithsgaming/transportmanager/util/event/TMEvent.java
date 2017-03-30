/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.util.event;

import com.smithsgaming.transportmanager.util.network.Side;

import java.io.Serializable;

/**
 *  ------ Class not Documented ------
 */
public abstract class TMEvent implements Serializable {

    public abstract void processEvent (Side side);
}
