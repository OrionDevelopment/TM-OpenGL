

package com.smithsgaming.transportmanager.util;


import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.graphics.GuiAspectRatio;
import org.joml.Matrix4f;

/**
 * Created by marcf on 3/11/2016.
 */
public class MathUtil
{

    public static float coTangent(float angle)
    {
        return (float) (1f / Math.tan(angle));
    }

    public static float toRadiant(float degree)
    {
        return (float) ((degree * Math.PI) / 180f);
    }

    public static Matrix4f CreatePerspectiveFieldOfView(float fovy, float aspect, float zNear, float zFar)
    {
        if (fovy <= 0 || fovy > Math.PI)
            throw new IllegalArgumentException("fovy");
        if (aspect <= 0)
            throw new IllegalArgumentException("aspect");
        if (zNear <= 0)
            throw new IllegalArgumentException("zNear");
        if (zFar <= 0)
            throw new IllegalArgumentException("zFar");
        if (zNear >= zFar)
            throw new IllegalArgumentException("zNear");


        return (new Matrix4f().perspective(fovy, aspect, zNear, zFar));
    }


    public static Matrix4f CreateOrthogonalFieldOfView(float zNear, float zFar)
    {
        if (zNear < 0)
            throw new IllegalArgumentException("zNear");
        if (zFar <= 0)
            throw new IllegalArgumentException("zFar");
        if (zNear >= zFar)
            throw new IllegalArgumentException("zNear");


        return (new Matrix4f().ortho(TransportManagerClient.getDisplay().getResolutionHorizontal() / 2f, TransportManagerClient.getDisplay().getResolutionHorizontal() / 2f, TransportManagerClient.getDisplay().getResolutionVertical() / 2f, TransportManagerClient.getDisplay().getResolutionVertical() / 2f, zNear, zFar));
    }

    /**
     * Returns the input value rounded up to the next highest power of two.
     */
    public static int roundUpToPowerOfTwo (int value) {
        int i = value - 1;
        i = i | i >> 1;
        i = i | i >> 2;
        i = i | i >> 4;
        i = i | i >> 8;
        i = i | i >> 16;
        return i + 1;
    }
}
