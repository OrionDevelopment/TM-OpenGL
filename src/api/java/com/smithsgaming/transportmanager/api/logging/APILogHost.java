/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.api.logging;

import org.apache.logging.log4j.Logger;

/**
 * Class that holds the Log for the API.
 */
public class APILogHost {

    public static Logger apiLog;

    public static Logger getLog() {
        return apiLog;
    }

    public static void setLog(Logger apiLog) {
        APILogHost.apiLog = apiLog;
    }
}
