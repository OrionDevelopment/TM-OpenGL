package com.smithsgaming.transportmanager.util.event;

import java.util.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public interface IEventController
{

    Queue<TMEvent> eventQueu = new ArrayDeque<>();

    default void registerEvent (TMEvent event)
    {
        synchronized (eventQueu)
        {
            eventQueu.add(event);
        }
    }
}
