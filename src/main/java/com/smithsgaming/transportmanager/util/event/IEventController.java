/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.util.event;

import java.util.Queue;

/**
 *  ------ Class not Documented ------
 */
public interface IEventController {
    default void registerEvent(TMEvent event) {
        synchronized (getEventQueue()) {
            getEventQueue().add(event);
        }
    }

    Queue<TMEvent> getEventQueue();
}
