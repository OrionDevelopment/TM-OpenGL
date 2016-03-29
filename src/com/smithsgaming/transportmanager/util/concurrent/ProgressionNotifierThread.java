package com.smithsgaming.transportmanager.util.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Tim on 29/03/2016.
 */
public abstract class ProgressionNotifierThread extends CompletionNotifierThread {

    private CopyOnWriteArrayList<IThreadProgressionListener> progressionListeners = new CopyOnWriteArrayList<>();

    public void registerNewProgressionListener(IThreadProgressionListener listener) {
        progressionListeners.add(listener);
    }

    public void removeProgressionListener(IThreadProgressionListener listener) {
        progressionListeners.remove(listener);
    }

    public void onThreadProgressionChanged(float progression, int type, String progressionString) {
        synchronized (progressionListeners) {
            for (IThreadProgressionListener listener : progressionListeners) {
                listener.onThreadProgressionChanged(this, type, progression, progressionString);
            }
        }
    }
}
