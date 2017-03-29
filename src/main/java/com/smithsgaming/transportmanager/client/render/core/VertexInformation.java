package com.smithsgaming.transportmanager.client.render.core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * Created by marcf on 11/18/2016.
 */
public class VertexInformation {

    public static final VertexInformation DEFAULT = new VertexInformation();

    // The amount of bytes an element has
    private int elementBytes = 4;
    // Elements per parameter
    private int positionElementCount = 4;
    private int colorElementCount = 4;
    private int textureElementCount = 2;
    // Bytes per parameter
    private int positionBytesCount = positionElementCount * elementBytes;
    private int colorByteCount = colorElementCount * elementBytes;
    private int textureByteCount = textureElementCount * elementBytes;
    // Byte offsets per parameter
    private int positionByteOffset = 0;
    private int colorByteOffset = positionByteOffset + positionBytesCount;
    private int textureByteOffset = colorByteOffset + colorByteCount;
    // The amount of elements that a vertex has
    private int elementCount = positionElementCount +
            colorElementCount + textureElementCount;
    // The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
    private int stride = positionBytesCount + colorByteCount +
            textureByteCount;

    VertexInformation() {
    }

    public VertexInformation(int elementBytes, int positionElementCount, int colorElementCount, int textureElementCount) {
        this.elementBytes = elementBytes;
        this.positionElementCount = positionElementCount;
        this.colorElementCount = colorElementCount;
        this.textureElementCount = textureElementCount;

        //Recalculate values
        this.positionBytesCount = positionElementCount * elementBytes;
        this.colorByteCount = colorElementCount * elementBytes;
        this.textureByteCount = textureElementCount * elementBytes;
        // Byte offsets per parameter
        this.positionByteOffset = 0;
        this.colorByteOffset = positionByteOffset + positionBytesCount;
        this.textureByteOffset = colorByteOffset + colorByteCount;
        // The amount of elements that a vertex has
        this.elementCount = positionElementCount +
                colorElementCount + textureElementCount;
        // The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
        this.stride = positionBytesCount + colorByteCount +
                textureByteCount;
    }

    public int getElementBytes() {
        return elementBytes;
    }

    public VertexInformation setElementBytes(int elementBytes) {
        this.elementBytes = elementBytes;
        return this;
    }

    public int getPositionBytesCount()
    {
        return positionBytesCount;
    }

    public VertexInformation setPositionBytesCount(int positionBytesCount) {
        this.positionBytesCount = positionBytesCount;
        return this;
    }

    public int getColorByteCount()
    {
        return colorByteCount;
    }

    public VertexInformation setColorByteCount(int colorByteCount) {
        this.colorByteCount = colorByteCount;
        return this;
    }

    public int getTextureByteCount()
    {
        return textureByteCount;
    }

    public VertexInformation setTextureByteCount(int textureByteCount) {
        this.textureByteCount = textureByteCount;
        return this;
    }

    public int getElementCount()
    {
        return elementCount;
    }

    public VertexInformation setElementCount(int elementCount) {
        this.elementCount = elementCount;
        return this;
    }

    public void upLoadVertexAttributesForShader(int programId) {
        // Position information will be attribute 0
        GL20.glBindAttribLocation(programId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(programId, 1, "in_Color");
        // Textute information will be attribute 2
        GL20.glBindAttribLocation(programId, 2, "in_TextureCoord");
    }

    public void upLoadVertexAttributesForGeometry() {
        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, getPositionElementCount(), GL11.GL_FLOAT,
                false, getStride(), getPositionByteOffset());
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, getColorElementCount(), GL11.GL_FLOAT,
                false, getStride(), getColorByteOffset());
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, getTextureElementCount(), GL11.GL_FLOAT,
                false, getStride(), getTextureByteOffset());
    }

    public int getPositionElementCount()
    {
        return positionElementCount;
    }

    public int getStride()
    {
        return stride;
    }

    public int getPositionByteOffset()
    {
        return positionByteOffset;
    }

    public int getColorElementCount()
    {
        return colorElementCount;
    }

    public int getColorByteOffset()
    {
        return colorByteOffset;
    }

    public int getTextureElementCount()
    {
        return textureElementCount;
    }

    public int getTextureByteOffset()
    {
        return textureByteOffset;
    }

    public VertexInformation setTextureByteOffset(int textureByteOffset)
    {
        this.textureByteOffset = textureByteOffset;
        return this;
    }

    public VertexInformation setTextureElementCount(int textureElementCount)
    {
        this.textureElementCount = textureElementCount;
        return this;
    }

    public VertexInformation setColorByteOffset(int colorByteOffset)
    {
        this.colorByteOffset = colorByteOffset;
        return this;
    }

    public VertexInformation setColorElementCount(int colorElementCount)
    {
        this.colorElementCount = colorElementCount;
        return this;
    }

    public VertexInformation setPositionByteOffset(int positionByteOffset)
    {
        this.positionByteOffset = positionByteOffset;
        return this;
    }

    public VertexInformation setStride(int stride)
    {
        this.stride = stride;
        return this;
    }

    public VertexInformation setPositionElementCount(int positionElementCount)
    {
        this.positionElementCount = positionElementCount;
        return this;
    }

    public void unLoadVertexAttributesForGeometry() {
        this.disableVertexAttributesForRender();
    }

    public void disableVertexAttributesForRender() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
    }

    public void enableVertexAttributesForRender()
    {
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
    }
}
