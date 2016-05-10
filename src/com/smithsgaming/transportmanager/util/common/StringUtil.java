package com.smithsgaming.transportmanager.util.common;

/**
 * Created by marcf on 5/10/2016.
 */
public class StringUtil {

    public static String fillCentered(String content, String filler, int length) {
        if (content.length() >= length)
            return content;

        String result = content;
        int additionCount = (length - content.length()) / 2;
        for (int i = 0; i < additionCount; i++) {
            result = filler + result + filler;
        }

        return result;
    }
}
