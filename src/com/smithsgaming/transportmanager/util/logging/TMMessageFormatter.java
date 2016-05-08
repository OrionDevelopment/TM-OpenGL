package com.smithsgaming.transportmanager.util.logging;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.*;

import java.nio.charset.*;
import java.time.*;
import java.util.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
@Plugin(name = "TMMessageFormatter", category = "Core", elementType = "layout", printObject = true)
public class TMMessageFormatter extends AbstractStringLayout {
    public TMMessageFormatter (Charset charset) {
        super(charset);
    }

    @PluginFactory
    public static TMMessageFormatter createLayout (@PluginAttribute("locationInfo") boolean locationInfo,
                                                   @PluginAttribute("properties") boolean properties,
                                                   @PluginAttribute("complete") boolean complete,
                                                   @PluginAttribute(value = "charset", defaultString = "UTF-8") Charset charset) {
        return new TMMessageFormatter(charset);
    }


    @Override
    public String toSerializable (LogEvent logEvent) {
        StringBuilder builder = getStringBuilder();
        Date logDate = Date.from(Instant.now());

        builder.append(logEvent.getLevel().toString());
        builder.append(" : ");

        builder.append(logDate.toString());
        builder.append(" - ");
        builder.append(logEvent.getThreadName());
        builder.append(" - ");

        if (logEvent.getMarker() != null) {
            builder.append(getMarkerDisplayString(logEvent.getMarker()));
            builder.append(" - ");
        }

        try {
            builder.append(logEvent.getMessage().getFormattedMessage() + ( logEvent.getThrown() != null ? " : " + logEvent.getThrown().getMessage() : "" ) + "\n");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        if (logEvent.getThrown() != null) {
            builder.append(logEvent.getThrownProxy().getExtendedStackTraceAsString());
        }

        return builder.toString();
    }

    private String getMarkerDisplayString (Marker marker) {
        String formatted = "";

        if (marker.hasParents()) {
            for (Marker parent : marker.getParents())
                formatted += getMarkerDisplayString(parent);
        }

        formatted += "[" + marker.getName() + "]";

        return formatted;
    }
}
