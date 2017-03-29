package com.smithsgaming.transportmanager.client.gui.input;

import com.smithsgaming.transportmanager.util.network.ActionProcessingResult;

/**
 * Created by marcf on 4/3/2016.
 */
public interface IMouseInputComponent {
    boolean componentCanHandleMouseInput();

    ActionProcessingResult handleKeyAction(int key, int action);
}
