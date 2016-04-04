package com.smithsgaming.transportmanager.util.event;

import java.util.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public interface IEventController {
    Queue<TMEvent> getEventQueue();

    default void registerEvent(TMEvent event) {
        synchronized (getEventQueue()) {
            getEventQueue().add(event);
        }
    }
}
