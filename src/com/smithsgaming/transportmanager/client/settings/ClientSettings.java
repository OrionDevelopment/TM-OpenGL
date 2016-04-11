package com.smithsgaming.transportmanager.client.settings;

import com.smithsgaming.transportmanager.client.graphics.*;

import java.io.*;

/**
 * @Author Marc (Created on: 30.03.2016)
 */
public class ClientSettings {

    private GuiScale currentScale;
    private GuiAspectRatio currentRatio;
    private boolean shouldWriteTextureStichtedImagesToDisk = true;

    private ClientSettings (GuiScale currentScale, GuiAspectRatio currentRatio, boolean shouldWriteTextureStichtedImagesToDisk) {
        this.currentScale = currentScale;
        this.currentRatio = currentRatio;
        this.shouldWriteTextureStichtedImagesToDisk = shouldWriteTextureStichtedImagesToDisk;
    }

    /**
     * Method to load the ClientSettings from the default file.
     * <p>
     * TODO: Implement saving and reading from disk.
     *
     * @return A instance of this class that holds the Settings of the Client.
     */
    public static ClientSettings loadSettings () {
        return new ClientSettings(GuiScale.FWVGA, GuiAspectRatio.HD, true);
    }

    /**
     * Method to load the ClientSettings from a file.
     * <p>
     * TODO: Implement saving and reading from disk.
     *
     * @return A instance of this class that holds the Settings of the Client.
     */
    public static ClientSettings loadSettings (File settingsFile) {
        return ClientSettings.loadSettings();
    }

    public void setToDefault () {
        this.currentScale = GuiScale.FWVGA;
        this.currentRatio = GuiAspectRatio.HD;
        this.shouldWriteTextureStichtedImagesToDisk = true;
    }

    public GuiScale getCurrentScale () {
        return currentScale;
    }

    public void setCurrentScale (GuiScale currentScale) {
        this.currentScale = currentScale;
    }

    public GuiAspectRatio getCurrentRatio () {
        return currentRatio;
    }

    public void setCurrentRatio (GuiAspectRatio currentRatio) {
        this.currentRatio = currentRatio;
    }

    public boolean isShouldWriteTextureStichtedImagesToDisk () {
        return shouldWriteTextureStichtedImagesToDisk;
    }

    public void setShouldWriteTextureStichtedImagesToDisk (boolean shouldWriteTextureStichtedImagesToDisk) {
        this.shouldWriteTextureStichtedImagesToDisk = shouldWriteTextureStichtedImagesToDisk;
    }
}
