package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.gui.components.*;
import com.smithsgaming.transportmanager.util.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class GuiGameLoading extends Gui {

    @Override
    protected void loadGui () {
        registerComponent(new ComponentImage(this, "/textures/menus/loading/background.png", new GuiPlane(-1, 1, 1, -1, -1, -1, 1, 1)));
    }
}
