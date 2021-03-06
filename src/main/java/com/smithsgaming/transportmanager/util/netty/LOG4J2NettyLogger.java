package com.smithsgaming.transportmanager.util.netty;

import io.netty.util.internal.logging.AbstractInternalLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * Created by marcf on 11/16/2016.
 */
public class LOG4J2NettyLogger extends AbstractInternalLogger {

    /**
     * Following the pattern discussed in pages 162 through 168 of "The complete
     * log4j manual".
     */
    static final String FQCN = LOG4J2NettyLogger.class.getName();
    final transient Logger  logger;
    // Does the log4j version in use recognize the TRACE level?
    // The trace level was introduced in log4j 1.2.12.
    final           boolean traceCapable;

    LOG4J2NettyLogger(Logger logger) {
        super(logger.getName());
        this.logger = logger;
        traceCapable = isTraceCapable();
    }

    private boolean isTraceCapable() {
        try {
            logger.isTraceEnabled();
            return true;
        } catch (NoSuchMethodError ignored) {
            return false;
        }
    }

    /**
     * Is this logger instance enabled for the TRACE level?
     *
     * @return True if this Logger is enabled for level TRACE, false otherwise.
     */
    @Override
    public boolean isTraceEnabled() {
        if (traceCapable) {
            return logger.isTraceEnabled();
        } else {
            return logger.isDebugEnabled();
        }
    }

    /**
     * Log a message object at level TRACE.
     *
     * @param msg
     *          - the message object to be logged
     */
    @Override
    public void trace(String msg) {
        logger.log(traceCapable ? Level.TRACE : Level.DEBUG, msg);
    }

    /**
     * Log a message at level TRACE according to the specified format and
     * argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for level TRACE.
     * </p>
     *
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    @Override
    public void trace(String format, Object arg) {
        if (isTraceEnabled()) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, arg);
            logger.log(traceCapable ? Level.TRACE : Level.DEBUG, ft
                    .getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level TRACE according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the TRACE level.
     * </p>
     *
     * @param format
     *          the format string
     * @param argA
     *          the first argument
     * @param argB
     *          the second argument
     */
    @Override
    public void trace(String format, Object argA, Object argB) {
        if (isTraceEnabled()) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, argA, argB);
            logger.log(traceCapable ? Level.TRACE : Level.DEBUG, ft
                    .getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level TRACE according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the TRACE level.
     * </p>
     *
     * @param format
     *          the format string
     * @param arguments
     *          an array of arguments
     */
    @Override
    public void trace(String format, Object... arguments) {
        if (isTraceEnabled()) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.arrayFormat(format, arguments);
            logger.log(traceCapable ? Level.TRACE : Level.DEBUG, ft
                    .getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at level TRACE with an accompanying message.
     *
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    @Override
    public void trace(String msg, Throwable t) {
        logger.log(traceCapable ? Level.TRACE : Level.DEBUG, msg, t);
    }

    /**
     * Is this logger instance enabled for the DEBUG level?
     *
     * @return True if this Logger is enabled for level DEBUG, false otherwise.
     */
    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * Log a message object at level DEBUG.
     *
     * @param msg
     *          - the message object to be logged
     */
    @Override
    public void debug(String msg) {
        logger.log(Level.DEBUG, msg);
    }

    /**
     * Log a message at level DEBUG according to the specified format and
     * argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for level DEBUG.
     * </p>
     *
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    @Override
    public void debug(String format, Object arg) {
        if (logger.isDebugEnabled()) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, arg);
            logger.log(Level.DEBUG, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level DEBUG according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the DEBUG level.
     * </p>
     *
     * @param format
     *          the format string
     * @param argA
     *          the first argument
     * @param argB
     *          the second argument
     */
    @Override
    public void debug(String format, Object argA, Object argB) {
        if (logger.isDebugEnabled()) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, argA, argB);
            logger.log(Level.DEBUG, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level DEBUG according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the DEBUG level.
     * </p>
     *
     * @param format
     *          the format string
     * @param arguments an array of arguments
     */
    @Override
    public void debug(String format, Object... arguments) {
        if (logger.isDebugEnabled()) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.arrayFormat(format, arguments);
            logger.log(Level.DEBUG, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at level DEBUG with an accompanying message.
     *
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    @Override
    public void debug(String msg, Throwable t) {
        logger.log(Level.DEBUG, msg, t);
    }

    /**
     * Is this logger instance enabled for the INFO level?
     *
     * @return True if this Logger is enabled for the INFO level, false otherwise.
     */
    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    /**
     * Log a message object at the INFO level.
     *
     * @param msg
     *          - the message object to be logged
     */
    @Override
    public void info(String msg) {
        logger.log(Level.INFO, msg);
    }

    /**
     * Log a message at level INFO according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the INFO level.
     * </p>
     *
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    @Override
    public void info(String format, Object arg) {
        if (logger.isInfoEnabled()) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, arg);
            logger.log(Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at the INFO level according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the INFO level.
     * </p>
     *
     * @param format
     *          the format string
     * @param argA
     *          the first argument
     * @param argB
     *          the second argument
     */
    @Override
    public void info(String format, Object argA, Object argB) {
        if (logger.isInfoEnabled()) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, argA, argB);
            logger.log(Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level INFO according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the INFO level.
     * </p>
     *
     * @param format
     *          the format string
     * @param argArray
     *          an array of arguments
     */
    @Override
    public void info(String format, Object... argArray) {
        if (logger.isInfoEnabled()) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.arrayFormat(format, argArray);
            logger.log(Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at the INFO level with an accompanying
     * message.
     *
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    @Override
    public void info(String msg, Throwable t) {
        logger.log(Level.INFO, msg, t);
    }

    /**
     * Is this logger instance enabled for the WARN level?
     *
     * @return True if this Logger is enabled for the WARN level, false otherwise.
     */
    @Override
    public boolean isWarnEnabled() {
        return logger.isEnabled(Level.WARN);
    }

    /**
     * Log a message object at the WARN level.
     *
     * @param msg
     *          - the message object to be logged
     */
    @Override
    public void warn(String msg) {
        logger.log(Level.WARN, msg);
    }

    /**
     * Log a message at the WARN level according to the specified format and
     * argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the WARN level.
     * </p>
     *
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    @Override
    public void warn(String format, Object arg) {
        if (logger.isEnabled(Level.WARN)) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, arg);
            logger.log(Level.WARN, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at the WARN level according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the WARN level.
     * </p>
     *
     * @param format
     *          the format string
     * @param argA
     *          the first argument
     * @param argB
     *          the second argument
     */
    @Override
    public void warn(String format, Object argA, Object argB) {
        if (logger.isEnabled(Level.WARN)) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, argA, argB);
            logger.log(Level.WARN, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level WARN according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the WARN level.
     * </p>
     *
     * @param format
     *          the format string
     * @param argArray
     *          an array of arguments
     */
    @Override
    public void warn(String format, Object... argArray) {
        if (logger.isEnabled(Level.WARN)) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.arrayFormat(format, argArray);
            logger.log(Level.WARN, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at the WARN level with an accompanying
     * message.
     *
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    @Override
    public void warn(String msg, Throwable t) {
        logger.log(Level.WARN, msg, t);
    }

    /**
     * Is this logger instance enabled for level ERROR?
     *
     * @return True if this Logger is enabled for level ERROR, false otherwise.
     */
    @Override
    public boolean isErrorEnabled() {
        return logger.isEnabled(Level.ERROR);
    }

    /**
     * Log a message object at the ERROR level.
     *
     * @param msg
     *          - the message object to be logged
     */
    @Override
    public void error(String msg) {
        logger.log(Level.ERROR, msg);
    }

    /**
     * Log a message at the ERROR level according to the specified format and
     * argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the ERROR level.
     * </p>
     *
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    @Override
    public void error(String format, Object arg) {
        if (logger.isEnabled(Level.ERROR)) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, arg);
            logger.log(Level.ERROR, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at the ERROR level according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the ERROR level.
     * </p>
     *
     * @param format
     *          the format string
     * @param argA
     *          the first argument
     * @param argB
     *          the second argument
     */
    @Override
    public void error(String format, Object argA, Object argB) {
        if (logger.isEnabled(Level.ERROR)) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.format(format, argA, argB);
            logger.log(Level.ERROR, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level ERROR according to the specified format and
     * arguments.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the ERROR level.
     * </p>
     *
     * @param format
     *          the format string
     * @param argArray
     *          an array of arguments
     */
    @Override
    public void error(String format, Object... argArray) {
        if (logger.isEnabled(Level.ERROR)) {
            LOG4J2NettyFormattingTuple ft = LOG4J2NettyMessageFormatter.arrayFormat(format, argArray);
            logger.log(Level.ERROR, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at the ERROR level with an accompanying
     * message.
     *
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    @Override
    public void error(String msg, Throwable t) {
        logger.log(Level.ERROR, msg, t);
    }
}
