package com.smithsgaming.transportmanager.util.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Tim on 29/03/2016.
 */
public abstract class CompletionNotifierThread extends Thread {

    private CopyOnWriteArrayList<IThreadCompletionListener> completionListeners = new CopyOnWriteArrayList<>();

    public void registerNewCompletionListener (IThreadCompletionListener listener) {
        completionListeners.add(listener);
    }

    public void removeCompletionListener (IThreadCompletionListener listener) {
        completionListeners.remove(listener);
    }

    @Override
    public final void run () {
        try {
            doRun();
        } catch (Exception exception) {
            throw exception;
        } finally {
            onThreadCompletion();
        }
    }

    public abstract void doRun ();

    private void onThreadCompletion()
    {
        synchronized (completionListeners)
        {
            for (IThreadCompletionListener listener : completionListeners)
            {
                listener.onThreadCompleted(this);
            }
        }
    }
}
