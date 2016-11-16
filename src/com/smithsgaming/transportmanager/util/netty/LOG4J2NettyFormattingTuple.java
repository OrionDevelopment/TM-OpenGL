package com.smithsgaming.transportmanager.util.netty;

/**
 * Created by marcf on 11/16/2016.
 */
public class LOG4J2NettyFormattingTuple {

    static final LOG4J2NettyFormattingTuple NULL = new LOG4J2NettyFormattingTuple(null);

    private final String message;
    private final Throwable throwable;
    private final Object[] argArray;

    LOG4J2NettyFormattingTuple(String message) {
        this(message, null, null);
    }

    LOG4J2NettyFormattingTuple(String message, Object[] argArray, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
        if (throwable == null) {
            this.argArray = argArray;
        } else {
            this.argArray = trimmedCopy(argArray);
        }
    }

    public static Object[] trimmedCopy(Object[] argArray) {
        if (argArray == null || argArray.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }
        final int trimemdLen = argArray.length - 1;
        Object[] trimmed = new Object[trimemdLen];
        System.arraycopy(argArray, 0, trimmed, 0, trimemdLen);
        return trimmed;
    }

    public String getMessage() {
        return message;
    }

    public Object[] getArgArray() {
        return argArray;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
