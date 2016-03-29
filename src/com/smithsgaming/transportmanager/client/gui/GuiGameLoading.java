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
        float horizontalHalf = GuiScale.HD.getHorizontalResolution() / 2f;
        float verticalHalf = GuiScale.HD.getVerticalResolution() / 2f;

        registerComponent(new ComponentFlatArea(this, new GuiPlane(-horizontalHalf, horizontalHalf, horizontalHalf, -horizontalHalf, verticalHalf, verticalHalf, -verticalHalf, -verticalHalf), (Color) Color.WHITE));
        registerComponent(new ComponentImage(this, "/textures/gui/background/logo.png", 0, 0, 354, 123, true, true));
        registerComponent(new ComponentText(this, TextureRegistry.Fonts.TimesNewRoman, "Loading...", 0f, -200f, true));
    }
}
