/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.api.event.manager;

import com.smithsgaming.transportmanager.api.event.Event;
import com.smithsgaming.transportmanager.api.logging.APILogHost;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EventBusTest {

    EventBus bus;

    @BeforeEach
    void setUp() {
        APILogHost.setLog(LogManager.getFormatterLogger("API"));
        bus = new EventBus();
    }

    @Test
    void registerEventHandlerInstance() {
        bus.registerEventHandlerInstance(new EventBusTestEventHandler());
        bus.registerEventHandlerInstance(new EventBusTestEventHandler());
        bus.triggerEvent(new EventBusTestEvent());
    }

    @Test
    void registerEventHandlerStatic() {
        bus.registerEventHandlerStatic(EventBusStaticEventHandler.class);
        bus.registerEventHandlerStatic(EventBusStaticEventHandler.class);
        bus.triggerEvent(new EventBusTestEvent());
    }

    @Test
    void triggerEvent() {
        bus.registerEventHandlerInstance(new EventBusTestEventHandler());
        bus.registerEventHandlerStatic(EventBusStaticEventHandler.class);
        bus.registerEventHandlerInstance(new EventBusTestPrioritizedEventHandler());
        bus.triggerEvent(new EventBusTestEvent());
        new EventBusTestEvent().trigger();
    }

    static class EventBusStaticEventHandler {

        static boolean triggered = false;

        @EventHandler
        public static void handleStatic(EventBusTestEvent event) {
            assertTrue(!triggered, "Static event handlers called multiple times.");
            triggered = true;
        }
    }

    class EventBusTestEvent extends Event {

        /**
         * Method used to trigger this event.
         */
        @Override
        public void trigger() {
            bus.triggerEvent(this);
        }
    }

    class EventBusTestEventHandler {

        @EventHandler
        public void handle(EventBusTestEvent event) {
            assertTrue(true, "Successfully triggered event.");
        }
    }

    class EventBusTestPrioritizedEventHandler {

        boolean highTriggeredFirst = false;

        @EventHandler(priority = EventHandler.HandlerPriority.LOW)
        public void lowPriority(EventBusTestEvent event) {
            assertTrue(highTriggeredFirst, "High priority EventHandler was not triggered first.");
        }

        @EventHandler(priority = EventHandler.HandlerPriority.HIGH)
        public void highPriority(EventBusTestEvent event) {
            highTriggeredFirst = true;
        }
    }


}