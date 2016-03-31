package com.smithsgaming.transportmanager.util.concurrent;

/**
 * Created by Tim on 29/03/2016.
 */
public interface IThreadProgressionListener {

    void onThreadProgressionChanged (final Thread thread, int type, float progression, String progressionString);

}
