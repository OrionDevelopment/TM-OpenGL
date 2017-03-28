package com.smithsgaming.transportmanager.client.render.core.textures;

import com.flowpowered.noise.Noise;
import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.util.ResourceUtil;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneF;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

/**
 * Created by Tim on 01/04/2016.
 */
public class Texture {
    private static boolean[][]noiseMap = new boolean[0][0];

    protected ByteBuffer data;
    protected int[] dataArray;
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

    private boolean requiredRepeating;

    private float u;
    private float v;

    private int originX;
    private int originY;

    private int internalFormat = GL11.GL_RGBA;
    private int format = GL11.GL_RGBA;

    public Texture(String textureName, ByteBuffer data, int pixelWidth, int pixelHeight, boolean requiredRepeating)
    {
        this(textureName, data, pixelWidth, pixelHeight, requiredRepeating, 0, 0, false, true, 0);
    }

    public Texture(
                    String textureName,
                    ByteBuffer data,
                    int pixelWidth,
                    int pixelHeight,
                    boolean requiredRepeating,
                    float u,
                    float v,
                    boolean isStitched,
                    boolean requiringTextureStitching,
                    int textureStitchId)
    {
        this.textureName = textureName;
        this.isStitched = isStitched;
        this.requiringTextureStitching = requiringTextureStitching;
        this.textureStitchId = textureStitchId;
        this.pixelHeight = pixelHeight;
        this.pixelWidth = pixelWidth;
        this.data = data;
        this.dataArray = ResourceUtil.generatePixelsFromByteBuffer(data);
        this.u = u;
        this.v = v;
        this.requiredRepeating = requiredRepeating;

        if (noiseMap.length  < getPixelWidth() || noiseMap[0].length < getPixelHeight()) {
            generateNoiseMap(pixelWidth, pixelHeight);
        }
    }

    public int getPixelWidth() {
        return pixelWidth;
    }

    public int getPixelHeight() {
        return pixelHeight;
    }

    private static void generateNoiseMap(int xsize, int ysize)
    {
        noiseMap = new boolean[xsize][ysize];

        for (int x = 0; x < xsize; x++)
        {
            for (int y = 0; y < ysize; y++)
            {
                noiseMap[x][y] = getNormalizedNoise(x, y) < 0.1F;
            }
        }
    }

    private static float getNormalizedNoise(int x, int y)
    {
        return getNoise(x, y);
    }

    private static float getNoise(int x, int y)
    {
        return (float) Noise.valueNoise3D(x, y, 0, (int) TransportManagerClient.getStartTime());
    }

    public ByteBuffer getData()
    {
        return data;
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

    public int getOriginX () {
        return originX;
    }

    public void setOriginX (int originX) {
        this.originX = originX;
    }

    public int getOriginY () {
        return originY;
    }

    public void setOriginY (int originY) {
        this.originY = originY;
    }

    public GuiPlaneF getArea()
    {
        return new GuiPlaneF(new Vector2f(this.getU(), this.getV()), new Vector2f(this.getU() + this.getWidth(), this.getV() + this.getHeight()));
        //return new GuiPlaneF(new Vector2f(this.getU(), this.getV()), new Vector2f(this.getU() + 1, this.getV() - 1));
    }

    public float getU()
    {
        return u;
    }

    public float getV()
    {
        return v;
    }

    public void setV(float v)
    {
        this.v = v;
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

    public void setU(float u)
    {
        this.u = u;
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

    public boolean isRequiredRepeating()
    {
        return requiredRepeating;
    }

    public void setRequiredRepeating(boolean requiredRepeating)
    {
        this.requiredRepeating = requiredRepeating;
    }

    /**
     * Method used to get a clip of this Texture. The Texture should return a new instance (preferably of
     * this own Type) that is clipped to the given Area and is interpolated with the noiseMap.
     *
     * @param minX     The minimal XCoord of the clipped area.
     * @param minY     The minimal YCoord of the clipped area.
     * @param maxX     The maximal XCoord of the clipped area.
     * @param maxY     The maximal YCoord of the clipped area.
     * @return A clipped variant of this Sprite (either it self when chain is true or new instance).
     */
    public Texture clip(int minX, int minY, int maxX, int maxY, String textureAppendix) {
        int[] pixelData = new int[getPixelWidth() * getPixelHeight()];

        for (int x = 0; x < getPixelWidth(); x++) {
            for (int y = 0; y < getPixelHeight(); y++) {
                int pixel = 0;

                if (x >= minX && y >= minY && x <= maxX && y <= maxY && noiseMap[x][y])
                {
                    pixel = getDataArray()[x * getPixelWidth() + y];
                }

                pixelData[x * getPixelHeight() + y] = pixel;
            }
        }

        return new Texture(getTextureName() + textureAppendix, ResourceUtil.generateBufferFromPixels(pixelData), getPixelWidth(), getPixelHeight(), requiredRepeating);
    }

    public int[] getDataArray() { return dataArray; }

    public String getTextureName()
    {
        return textureName;
    }

    public void setGlTextureOptions()
    {
        if (requiredRepeating)
        {
            setGLTextureRepeat();
        }
        else
        {
            setGlTextureClamp();
        }
    }

    private void setGLTextureRepeat()
    {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
    }

    private void setGlTextureClamp()
    {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
    }
}
