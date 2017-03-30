/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.api.event.manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marking methods that function as event handlers.
 * The method needs to have one parameter. The type of this parameter needs to
 * be the type of the Event that the method should handle.
 * <p>
 * Multiple methods with this Annotation might exist in a given class.
 * All methods should be treated as individual event handlers.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    /**
     * @return The priority of this EventHandler. Default NORMAL.
     */
    HandlerPriority priority() default HandlerPriority.NORMAL;

    /**
     * Enum defining the priority of an EvenHandler.
     */
    enum HandlerPriority {
        HIGHEST,
        HIGH,
        NORMAL,
        LOW,
        LOWEST
    }
}
