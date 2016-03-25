package com.smithsgaming.transportmanager.util.event;

import java.util.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public interface IEventController
{
    Queue<TMEvent> getEventQueu ();

    default void registerEvent (TMEvent event)
    {
        synchronized (getEventQueu())
        {
            getEventQueu().add(event);
        }
    }
}
