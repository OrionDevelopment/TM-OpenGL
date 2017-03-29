package com.smithsgaming.transportmanager.util.netty;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.apache.logging.log4j.LogManager;

/**
 * Created by marcf on 11/16/2016.
 */
public class LOG4J2NettyFactory extends InternalLoggerFactory {
    @Override
    protected InternalLogger newInstance(String s) {
        return new LOG4J2NettyLogger(LogManager.getLogger(s));
    }
}
