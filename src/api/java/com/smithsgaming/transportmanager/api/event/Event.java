/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.api.event;

/**
 * Class describing an Event that can be triggered on both sides.
 */
public abstract class Event {

    /**
     * Method used to trigger this event.
     */
    public abstract void trigger();
}
