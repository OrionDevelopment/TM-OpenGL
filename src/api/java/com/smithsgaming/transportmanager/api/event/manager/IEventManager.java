/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.api.event.manager;

import com.smithsgaming.transportmanager.api.event.Event;

/**
 * Class handling the event triggering and other elements of a the event
 * management system.
 *
 * @param <E> The event type for which this {@link IEventManager} handles
 */
public interface IEventManager<E extends Event> {

    /**
     * Method used to register an event Handler.
     *
     * @param handler The object that handles events, all methods of this class
     *                that are marked with the annotation {@link EventHandler}
     *                and have a single parameter of a type that extends the
     *                type {@link E}.
     */
    void registerHandler(Object handler);

    /**
     * Method used to handle a triggered {@link Event}.
     *
     * @param event       The {@link Event} that has been triggered by calling
     *                    its {@link Event#trigger()} method.
     * @param <EventType> The type of Event that was triggered. Extends {@link
     *                    E}.
     */
    <EventType extends E> void handleTriggeredEvent(EventType event);
}
