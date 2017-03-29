
package com.smithsgaming.transportmanager.client.render.core;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class TexturedVertex {

    private VertexInformation information = VertexInformation.DEFAULT;
    
    // Vertex data
    private float[] xyzw = new float[]{0f, 0f, 0f, 1f};
    private float[] rgba = new float[]{1f, 1f, 1f, 1f};
    private float[] st = new float[]{0f, 0f};

    // Setters
    public TexturedVertex setXYZ (float x, float y, float z) {
        this.setXYZW(x, y, z, 1f);

        return this;
    }

    public TexturedVertex setXYZW (float x, float y, float z, float w) {
        this.xyzw = new float[]{x, y, z, w};

        return this;
    }

    public TexturedVertex setRGB(float r, float g, float b)
    {
        this.setRGBA(r, g, b, 1f);

        return this;
    }

    public TexturedVertex setRGBA (float r, float g, float b, float a) {
        this.rgba = new float[]{r, g, b, a};

        return this;
    }

    public TexturedVertex setST(float s, float t)
    {
        this.st = new float[] {s, t};

        return this;
    }

    // Getters
    public float[] getElements () {
        float[] out = new float[getInformation().getElementCount()];
        int i = 0;

        // Insert XYZW elements
        out[i++] = this.xyzw[0];
        out[i++] = this.xyzw[1];
        out[i++] = this.xyzw[2];
        out[i++] = this.xyzw[3];
        // Insert RGBA elements
        out[i++] = this.rgba[0];
        out[i++] = this.rgba[1];
        out[i++] = this.rgba[2];
        out[i++] = this.rgba[3];
        // Insert ST elements
        out[i++] = this.st[0];
        out[i++] = this.st[1];

        return out;
    }

    public VertexInformation getInformation()
    {
        return information;
    }

    public TexturedVertex setInformation(VertexInformation information)
    {
        this.information = information;
        return this;
    }

    public float[] getXYZW () {
        return new float[]{this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3]};
    }

    public float[] getXYZ () {
        return new float[]{this.xyzw[0], this.xyzw[1], this.xyzw[2]};
    }

    public float[] getRGBA () {
        return new float[]{this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3]};
    }

    public float[] getRGB () {
        return new float[]{this.rgba[0], this.rgba[1], this.rgba[2]};
    }

    public float[] getST () {
        return new float[]{this.st[0], this.st[1]};
    }


}
