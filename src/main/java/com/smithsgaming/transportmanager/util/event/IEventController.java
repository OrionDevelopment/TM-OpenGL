package com.smithsgaming.transportmanager.util.event;

import java.util.Queue;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public interface IEventController {
    default void registerEvent(TMEvent event) {
        synchronized (getEventQueue()) {
            getEventQueue().add(event);
        }
    }

    Queue<TMEvent> getEventQueue();
}
