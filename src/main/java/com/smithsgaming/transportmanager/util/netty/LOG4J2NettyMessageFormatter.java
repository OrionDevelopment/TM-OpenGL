/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.util.netty;

//Extracted this class from the NETTY Package to make it work with Log4j2

// contributors: lizongbo: proposed special treatment of array parameter values
// Joern Huxhorn: pointed out double[] omission, suggested deep array copy

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Formats messages according to very simple substitution rules. Substitutions
 * can be made 1, 2 or more arguments.
 *
 *
 * For example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;)
 * </pre>
 *
 * will return the string "Hi there.".
 *
 * The {} pair is called the <em>formatting anchor</em>. It serves to designate
 * the location where arguments need to be substituted within the message
 * pattern.
 *
 * In case your message contains the '{' or the '}' character, you do not have
 * to do anything special unless the '}' character immediately follows '{'. For
 * example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Set {1,2,3} is not equal to {}.&quot;, &quot;1,2&quot;);
 * </pre>
 *
 * will return the string "Set {1,2,3} is not equal to 1,2.".
 *
 *
 * If for whatever reason you need to place the string "{}" in the message
 * without its <em>formatting anchor</em> meaning, then you need to escape the
 * '{' character with '\', that is the backslash character. Only the '{'
 * character should be escaped. There is no need to escape the '}' character.
 * For example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Set \\{} is not equal to {}.&quot;, &quot;1,2&quot;);
 * </pre>
 *
 * will return the string "Set {} is not equal to 1,2.".
 *
 *
 * The escaping behavior just described can be overridden by escaping the escape
 * character '\'. Calling
 *
 * <pre>
 * MessageFormatter.format(&quot;File name is C:\\\\{}.&quot;, &quot;file.zip&quot;);
 * </pre>
 *
 * will return the string "File name is C:\file.zip".
 *
 *
 * The formatting conventions are different than those of {@link MessageFormat}
 * which ships with the Java platform. This is justified by the fact that
 * SLF4J's implementation is 10 times faster than that of {@link MessageFormat}.
 * This local performance difference is both measurable and significant in the
 * larger context of the complete logging processing chain.
 *
 *
 * See also {@link #format(String, Object)},
 * {@link #format(String, Object, Object)} and
 * {@link #arrayFormat(String, Object[])} methods for more details.
 */
public final class LOG4J2NettyMessageFormatter {
    static final char DELIM_START = '{';
    static final char DELIM_STOP = '}';
    static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';

    private LOG4J2NettyMessageFormatter()
    {
    }

    /**
     * Performs single argument substitution for the 'messagePattern' passed as
     * parameter.
     *
     * For example,
     *
     * <pre>
     * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;);
     * </pre>
     *
     * will return the string "Hi there.".
     *
     *
     * @param messagePattern The message pattern which will be parsed and formatted
     * @param arg            The argument to be substituted in place of the formatting anchor
     * @return The formatted message
     */
    static LOG4J2NettyFormattingTuple format(String messagePattern, Object arg) {
        return arrayFormat(messagePattern, new Object[]{arg});
    }

    /**
     * Performs a two argument substitution for the 'messagePattern' passed as
     * parameter.
     *
     * For example,
     *
     * <pre>
     * MessageFormatter.format(&quot;Hi {}. My name is {}.&quot;, &quot;Alice&quot;, &quot;Bob&quot;);
     * </pre>
     *
     * will return the string "Hi Alice. My name is Bob.".
     *
     * @param messagePattern The message pattern which will be parsed and formatted
     * @param argA           The argument to be substituted in place of the first formatting
     *                       anchor
     * @param argB           The argument to be substituted in place of the second formatting
     *                       anchor
     * @return The formatted message
     */
    static LOG4J2NettyFormattingTuple format(final String messagePattern,
                                  Object argA, Object argB) {
        return arrayFormat(messagePattern, new Object[]{argA, argB});
    }

    static Throwable getThrowableCandidate(Object[] argArray) {
        if (argArray == null || argArray.length == 0) {
            return null;
        }

        final Object lastEntry = argArray[argArray.length - 1];
        if (lastEntry instanceof Throwable) {
            return (Throwable) lastEntry;
        }
        return null;
    }

    /**
     * Same principle as the {@link #format(String, Object)} and
     * {@link #format(String, Object, Object)} methods except that any number of
     * arguments can be passed in an array.
     *
     * @param messagePattern The message pattern which will be parsed and formatted
     * @param argArray       An array of arguments to be substituted in place of formatting
     *                       anchors
     * @return The formatted message
     */
    static LOG4J2NettyFormattingTuple arrayFormat(final String messagePattern,
                                       final Object[] argArray) {

        Throwable throwableCandidate = getThrowableCandidate(argArray);

        if (messagePattern == null) {
            return new LOG4J2NettyFormattingTuple(null, argArray, throwableCandidate);
        }

        if (argArray == null) {
            return new LOG4J2NettyFormattingTuple(messagePattern);
        }

        int i = 0;
        int j;
        StringBuffer sbuf = new StringBuffer(messagePattern.length() + 50);

        int L;
        for (L = 0; L < argArray.length; L++) {

            j = messagePattern.indexOf(DELIM_STR, i);

            if (j == -1) {
                // no more variables
                if (i == 0) { // this is a simple string
                    return new LOG4J2NettyFormattingTuple(messagePattern, argArray,
                            throwableCandidate);
                } else { // add the tail string which contains no variables and return
                    // the result.
                    sbuf.append(messagePattern.substring(i, messagePattern.length()));
                    return new LOG4J2NettyFormattingTuple(sbuf.toString(), argArray,
                            throwableCandidate);
                }
            } else {
                if (isEscapedDelimeter(messagePattern, j)) {
                    if (!isDoubleEscaped(messagePattern, j)) {
                        L--; // DELIM_START was escaped, thus should not be incremented
                        sbuf.append(messagePattern.substring(i, j - 1));
                        sbuf.append(DELIM_START);
                        i = j + 1;
                    } else {
                        // The escape character preceding the delimiter start is
                        // itself escaped: "abc x:\\{}"
                        // we have to consume one backward slash
                        sbuf.append(messagePattern.substring(i, j - 1));
                        deeplyAppendParameter(sbuf, argArray[L], new HashMap<Object[], Void>());
                        i = j + 2;
                    }
                } else {
                    // normal case
                    sbuf.append(messagePattern.substring(i, j));
                    deeplyAppendParameter(sbuf, argArray[L], new HashMap<Object[], Void>());
                    i = j + 2;
                }
            }
        }
        // append the characters following the last {} pair.
        sbuf.append(messagePattern.substring(i, messagePattern.length()));
        if (L < argArray.length - 1) {
            return new LOG4J2NettyFormattingTuple(sbuf.toString(), argArray, throwableCandidate);
        } else {
            return new LOG4J2NettyFormattingTuple(sbuf.toString(), argArray, null);
        }
    }

    static boolean isEscapedDelimeter(String messagePattern,
                                      int delimeterStartIndex) {

        if (delimeterStartIndex == 0) {
            return false;
        }
        return messagePattern.charAt(delimeterStartIndex - 1) == ESCAPE_CHAR;
    }

    static boolean isDoubleEscaped(String messagePattern,
                                   int delimeterStartIndex) {
        return delimeterStartIndex >= 2 && messagePattern.charAt(delimeterStartIndex - 2) == ESCAPE_CHAR;
    }

    // special treatment of array values was suggested by 'lizongbo'
    private static void deeplyAppendParameter(StringBuffer sbuf, Object o,
                                              Map<Object[], Void> seenMap) {
        if (o == null) {
            sbuf.append("null");
            return;
        }
        if (!o.getClass().isArray()) {
            safeObjectAppend(sbuf, o);
        } else {
            // check for primitive array types because they
            // unfortunately cannot be cast to Object[]
            if (o instanceof boolean[]) {
                booleanArrayAppend(sbuf, (boolean[]) o);
            } else if (o instanceof byte[]) {
                byteArrayAppend(sbuf, (byte[]) o);
            } else if (o instanceof char[]) {
                charArrayAppend(sbuf, (char[]) o);
            } else if (o instanceof short[]) {
                shortArrayAppend(sbuf, (short[]) o);
            } else if (o instanceof int[]) {
                intArrayAppend(sbuf, (int[]) o);
            } else if (o instanceof long[]) {
                longArrayAppend(sbuf, (long[]) o);
            } else if (o instanceof float[]) {
                floatArrayAppend(sbuf, (float[]) o);
            } else if (o instanceof double[]) {
                doubleArrayAppend(sbuf, (double[]) o);
            } else {
                objectArrayAppend(sbuf, (Object[]) o, seenMap);
            }
        }
    }

    private static void safeObjectAppend(StringBuffer sbuf, Object o) {
        try {
            String oAsString = o.toString();
            sbuf.append(oAsString);
        } catch (Throwable t) {
            System.err
                    .println("SLF4J: Failed toString() invocation on an object of type ["
                            + o.getClass().getName() + ']');
            t.printStackTrace();
            sbuf.append("[FAILED toString()]");
        }
    }

    private static void objectArrayAppend(StringBuffer sbuf, Object[] a,
                                          Map<Object[], Void> seenMap) {
        sbuf.append('[');
        if (!seenMap.containsKey(a)) {
            seenMap.put(a, null);
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                deeplyAppendParameter(sbuf, a[i], seenMap);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }
            // allow repeats in siblings
            seenMap.remove(a);
        } else {
            sbuf.append("...");
        }
        sbuf.append(']');
    }

    private static void booleanArrayAppend(StringBuffer sbuf, boolean[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }
        sbuf.append(']');
    }

    private static void byteArrayAppend(StringBuffer sbuf, byte[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }
        sbuf.append(']');
    }

    private static void charArrayAppend(StringBuffer sbuf, char[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }
        sbuf.append(']');
    }

    private static void shortArrayAppend(StringBuffer sbuf, short[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }
        sbuf.append(']');
    }

    private static void intArrayAppend(StringBuffer sbuf, int[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }
        sbuf.append(']');
    }

    private static void longArrayAppend(StringBuffer sbuf, long[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }
        sbuf.append(']');
    }

    private static void floatArrayAppend(StringBuffer sbuf, float[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }
        sbuf.append(']');
    }

    private static void doubleArrayAppend(StringBuffer sbuf, double[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }
        sbuf.append(']');
    }
}
