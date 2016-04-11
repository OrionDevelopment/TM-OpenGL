package com.smithsgaming.transportmanager.util.event;

import com.smithsgaming.transportmanager.util.*;

import java.io.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public abstract class TMEvent implements Serializable {

    public abstract void processEvent (Side side);
}
