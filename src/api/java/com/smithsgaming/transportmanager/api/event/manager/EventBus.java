/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.api.event.manager;

import com.smithsgaming.transportmanager.api.event.Event;
import com.smithsgaming.transportmanager.api.logging.APILogHost;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * A skeleton implementation of an EventBus.
 */
public class EventBus {

    /**
     * List holding the processed static classes.
     * Prevents double registration of static methods as EventHandlers.
     */
    private List<Class> processedStaticClasses = new ArrayList<>();

    /**
     * Multithreaded map for storing the EventHandlers.
     */
    private Map<Class<? extends Event>, SortedMap<EventHandler.HandlerPriority, List<Consumer<Event>>>> eventHandlers = new ConcurrentHashMap<>();

    /**
     * Method used to register a new handler for events.
     *
     * @param handlerInstance The event handler that should be registered.
     */
    @SuppressWarnings("unchecked")
    public <T> void registerEventHandlerInstance(T handlerInstance) {
        registerEventHandlerInternal((Class<T>) handlerInstance.getClass(), handlerInstance);
    }

    /**
     * Method used to register a new handler for events.
     * This method is used to register the static methods of the given class.
     *
     * @param handlerClass The class that should be registered.
     */
    @SuppressWarnings("unchecked")
    public void registerEventHandlerStatic(Class handlerClass) {
        registerEventHandlerInternal(handlerClass, null);
    }

    /**
     * Method used internally to register an EventHandler.
     *
     * @param handlerClass    The class that the EventHandler data is extracted
     *                        from.
     * @param handlerInstance The instance of the class that is being
     *                        registered. Pass null for static initialization.
     * @param <T>             The type of the EventHandler.
     */
    @SuppressWarnings("unchecked")
    private <T> void registerEventHandlerInternal(Class<T> handlerClass, T handlerInstance) {
        //Skip if the object class is scanned.
        if (handlerClass.equals(Object.class)) {
            return;
        }

        registerEventHandlerInternal(handlerClass.getSuperclass(), handlerInstance);

        APILogHost.getLog().trace("Registering: " + handlerClass.getName() + " as event handler.");

        boolean isStatic = handlerInstance == null;

        //Class already processed for static methods. Skipping.
        if (isStatic && processedStaticClasses.contains(handlerClass)) {
            return;
        }

        for (Method method : handlerClass.getMethods()) {
            //No eventhandler. Skip.
            if (!method.isAnnotationPresent(EventHandler.class)) {
                continue;
            }

            //More then one parameter. Skip.
            if (method.getParameterCount() != 1) {
                continue;
            }

            //Parameter is not an event, Skip.
            if (!Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                continue;
            }

            EventHandler eventHandlerAnnotation = method.getAnnotation(EventHandler.class);

            Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameters()[0].getType();

            if ((isStatic && Modifier.isStatic(method.getModifiers())) || !isStatic && !Modifier.isStatic(method.getModifiers())) {
                setupMapForEventAndPriority(eventClass, eventHandlerAnnotation.priority());

                APILogHost.getLog().trace("Adding: " + method.getName() + " as EventHandler to: " + handlerClass.getName());

                eventHandlers.get(eventClass).get(eventHandlerAnnotation.priority()).add(event -> {
                    try {
                        method.invoke(handlerInstance, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        APILogHost.getLog().warn("An Eventhandler errored while trying to invoke handler method.", e);
                    } catch (Exception e) {
                        APILogHost.getLog().warn("Caught an exception while trying to process an Eventhandler.", e);
                    }
                });
            }
        }

        if (isStatic) {
            processedStaticClasses.add(handlerClass);
        }
    }

    /**
     * Method used to setup the EventHandler map for a specific {@link Event}.
     * and {@link EventHandler.HandlerPriority}
     *
     * @param eventClassToSetup The event to set the handler map up for.
     * @param priority          The priority to set the handler map up for.
     */
    private void setupMapForEventAndPriority(Class<? extends Event> eventClassToSetup, EventHandler.HandlerPriority priority) {
        if (!eventHandlers.containsKey(eventClassToSetup)) {
            eventHandlers.put(eventClassToSetup, new TreeMap<>());
        }

        if (!eventHandlers.get(eventClassToSetup).containsKey(priority)) {
            eventHandlers.get(eventClassToSetup).put(priority, new ArrayList<>());
        }
    }

    /**
     * Method to trigger a given {@link Event}.
     *
     * @param event The event to trigger.
     */
    public void triggerEvent(Event event) {
        for (Class<? extends Event> eventClass : eventHandlers.keySet()) {
            if (!event.getClass().isAssignableFrom(eventClass)) {
                continue;
            }

            SortedMap<EventHandler.HandlerPriority, List<Consumer<Event>>> handlers = eventHandlers.get(eventClass);
            for (EventHandler.HandlerPriority priority : handlers.keySet()) {
                for (Consumer<Event> handlerWrapper : handlers.get(priority)) {
                    handlerWrapper.accept(event);
                }
            }
        }
    }
}
