package com.smithsgaming.transportmanager.client.render.textures;

import com.smithsgaming.transportmanager.util.math.graphical.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.*;

import java.nio.*;

/**
 * Created by Tim on 01/04/2016.
 */
public class Texture {
    protected ByteBuffer data;
    private String textureName;

    private float width = 1f;
    private float height = 1f;

    private int pixelWidth;
    private int pixelHeight;

    private int openGLTextureId;
    private int boundTextureUnit;

    private boolean requiringTextureStitching;
    private boolean isStitched;
    private int textureStitchId;

    private float u;
    private float v;

    private int internalFormat = GL11.GL_RGBA;
    private int format = GL11.GL_RGBA;

    public Texture(String textureName, ByteBuffer data, int pixelWidth, int pixelHeight) {
        this(textureName, data, pixelWidth, pixelHeight, 0, 0, false, true, 0);
    }

    public Texture(String textureName, ByteBuffer data, int pixelWidth, int pixelHeight, float u, float v, boolean isStitched, boolean requiringTextureStitching, int textureStitchId) {
        this.textureName = textureName;
        this.isStitched = isStitched;
        this.requiringTextureStitching = requiringTextureStitching;
        this.textureStitchId = textureStitchId;
        this.pixelHeight = pixelHeight;
        this.pixelWidth = pixelWidth;
        this.data = data;
        this.u = u;
        this.v = v;
    }

    public String getTextureName() {
        return textureName;
    }

    public ByteBuffer getData() {
        return data;
    }

    public int getPixelWidth() {
        return pixelWidth;
    }

    public int getPixelHeight() {
        return pixelHeight;
    }

    public int getOpenGLTextureId() {
        return openGLTextureId;
    }

    public void setOpenGLTextureId(int openGLTextureId) {
        this.openGLTextureId = openGLTextureId;
    }

    public int getBoundTextureUnit() {
        return boundTextureUnit;
    }

    public void setBoundTextureUnit(int boundTextureUnit) {
        this.boundTextureUnit = boundTextureUnit;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public float getU() {
        return u;
    }

    public void setU(float u) {
        this.u = u;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public GuiPlaneF getArea() {
        return new GuiPlaneF(new Vector2f(this.getU(), this.getV()), new Vector2f(this.getU() + this.getWidth(), this.getV() - this.getHeight()));
        //return new GuiPlaneF(new Vector2f(this.getU(), this.getV()), new Vector2f(this.getU() + 1, this.getV() - 1));
    }

    public int getTextureStitchId() {
        return textureStitchId;
    }

    public void setTextureStitchId(int textureStitchId) {
        this.textureStitchId = textureStitchId;
    }

    public boolean isStitched() {
        return isStitched;
    }

    public void setStitched(boolean stitched) {
        isStitched = stitched;
    }

    public boolean isRequiringTextureStitching() {
        return requiringTextureStitching;
    }

    public void setRequiringTextureStitching(boolean requiringTextureStitching) {
        this.requiringTextureStitching = requiringTextureStitching;
    }

    public int getInternalFormat() {
        return internalFormat;
    }

    public void setInternalFormat(int internalFormat) {
        this.internalFormat = internalFormat;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }
}
