package com.smithsgaming.transportmanager.util.logging;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * Created by marcf on 5/10/2016.
 */
public class Markers {
    public static final Marker CORE = MarkerManager.getMarker("CORE");
    public static final Marker CLIENT = MarkerManager.getMarker("CLIENT");
    public static final Marker SERVER = MarkerManager.getMarker("SERVER");

    public static final Marker INIT = MarkerManager.getMarker("INIT", CORE);
    public static final Marker TILEREGISTRY = MarkerManager.getMarker("TILEREGISTRY", CORE);
    public static final Marker TILEENTITYREGISTRY = MarkerManager.getMarker("TILEENTITYREGISTRY", CORE);
}
