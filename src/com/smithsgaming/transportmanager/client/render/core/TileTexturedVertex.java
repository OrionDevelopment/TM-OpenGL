package com.smithsgaming.transportmanager.client.render.core;

import com.smithsgaming.transportmanager.util.world.TileDirection;

/**
 * Created by marcf on 11/18/2016.
 */
public class TileTexturedVertex extends TexturedVertex {

    public TileTexturedVertex() {
        setInformation(TileVertexInformation.INSTANCE);
    }

    private float[] ost1 = new float[]{0f, 0f};
    private float[] ost2= new float[]{0f, 0f};
    private float[] ost3 =new float[]{0f, 0f};
    private float[] ost4 = new float[]{0f, 0f};
    private float[] ost5 = new float[]{0f, 0f};
    private float[] ost6 = new float[]{0f, 0f};
    private float[] ost7 = new float[]{0f, 0f};
    private float[] ost8 = new float[]{0f, 0f};

    public float[] getOst1() {
        return ost1;
    }

    public void setOst1(float[] ost1) {
        this.ost1 = ost1;
    }

    public float[] getOst2() {
        return ost2;
    }

    public void setOst2(float[] ost2) {
        this.ost2 = ost2;
    }

    public float[] getOst3() {
        return ost3;
    }

    public void setOst3(float[] ost3) {
        this.ost3 = ost3;
    }

    public float[] getOst4() {
        return ost4;
    }

    public void setOst4(float[] ost4) {
        this.ost4 = ost4;
    }

    public float[] getOst5() {
        return ost5;
    }

    public void setOst5(float[] ost5) {
        this.ost5 = ost5;
    }

    public float[] getOst6() {
        return ost6;
    }

    public void setOst6(float[] ost6) {
        this.ost6 = ost6;
    }

    public float[] getOst7() {
        return ost7;
    }

    public void setOst7(float[] ost7) {
        this.ost7 = ost7;
    }

    public float[] getOst8() {
        return ost8;
    }

    public void setOst8(float[] ost8) {
        this.ost8 = ost8;
    }

    public void setOst(TileDirection direction, float... ost) {
        switch (direction) {
            case TOPLEFT:
                setOst1(ost);
                break;
            case TOP:
                setOst2(ost);                
                break;
            case TOPRIGHT:
                setOst3(ost);
                break;
            case RIGHT:
                setOst4(ost);
                break;
            case BOTTOMRIGHT:
                setOst5(ost);
                break;
            case BOTTOM:
                setOst6(ost);
                break;
            case BOTTOMLEFT:
                setOst7(ost);
                break;
            case LEFT:
                setOst8(ost);
                break;
        }
    }
    
    @Override
    public float[] getElements() {
        float[] additionalData = super.getElements();
        int i = 10;

        additionalData[i++] = ost1[0];
        additionalData[i++] = ost1[1];
        additionalData[i++] = ost2[0];
        additionalData[i++] = ost2[1];
        additionalData[i++] = ost3[0];
        additionalData[i++] = ost3[1];
        additionalData[i++] = ost4[0];
        additionalData[i++] = ost4[1];
        additionalData[i++] = ost5[0];
        additionalData[i++] = ost5[1];
        additionalData[i++] = ost6[0];
        additionalData[i++] = ost6[1];
        additionalData[i++] = ost7[0];
        additionalData[i++] = ost7[1];
        additionalData[i++] = ost8[0];
        additionalData[i++] = ost8[1];

        return additionalData;
    }
}
