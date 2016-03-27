package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.gui.components.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.*;

/**
 * @Author Marc (Created on: 25.03.200.25f6)
 */
public class GuiGameLoading extends Gui {

    @Override
    protected void loadGui () {
        registerComponent(new ComponentFlatArea(this, new GuiPlane(-1, 1, 1, -1, 1, 1, -1, -1), (Color) Color.WHITE));
        registerComponent(new ComponentImage(this, "/textures/gui/background/logo.png", new GuiPlane(-0.25f, 0.25f, 0.25f, -0.25f, -0.25f, -0.25f, 0.25f, 0.25f)));
        registerComponent(new ComponentText(this, TextureRegistry.Fonts.Courier, "Loading...", -0.3f, 0f, true));
    }
}
