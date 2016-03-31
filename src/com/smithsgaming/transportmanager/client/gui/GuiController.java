package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.render.*;

import java.util.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class GuiController implements IRenderer {

    Stack<GuiAbstract> openedGuiStack = new Stack<>();

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        if (openedGuiStack.size() == 0)
            return;

        GuiAbstract guiOpen = openedGuiStack.peek();
        guiOpen.render();
    }

    public void openGui (GuiAbstract gui) {
        openedGuiStack.push(gui);

        gui.loadGui();
        gui.loadGeometry();
        gui.loadTextures();
    }

    public void closeGui () {
        if (openedGuiStack.size() == 0)
            throw new IllegalStateException("No gui open!");

        GuiAbstract guiToBeClosed = openedGuiStack.pop();
        guiToBeClosed.unLoadGeometry();
        guiToBeClosed.unLoadTextures();
    }

    public boolean isGuiOpen () {
        return openedGuiStack.size() != 0;
    }
}
