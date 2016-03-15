

package com.smithsgaming.transportmanager.util;

import org.lwjgl.util.vector.*;

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

        float yMax = zNear * (float)Math.tan(0.5f * fovy);
        float yMin = -yMax;
        float xMin = yMin * aspect;
        float xMax = yMax * aspect;

        return CreatePerspectiveOffCenter(xMin, xMax, yMin, yMax, zNear, zFar);
    }

    public static Matrix4f CreatePerspectiveOffCenter(float left, float right, float bottom, float top, float zNear, float zFar)
    {
        if (zNear <= 0)
            throw new IllegalArgumentException("zNear");
        if (zFar <= 0)
            throw new IllegalArgumentException("zFar");
        if (zNear >= zFar)
            throw new IllegalArgumentException("zNear");

        float x = (2.0f * zNear) / (right - left);
        float y = (2.0f * zNear) / (top - bottom);
        float a = (right + left) / (right - left);
        float b = (top + bottom) / (top - bottom);
        float c = -(zFar + zNear) / (zFar - zNear);
        float d = -(2.0f * zFar * zNear) / (zFar - zNear);

        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();

        matrix4f.m00 = x;
        matrix4f.m11 = y;
        matrix4f.m20 = a;
        matrix4f.m21 = b;
        matrix4f.m22 = c;
        matrix4f.m23 = -1;
        matrix4f.m32 = d;
        matrix4f.m33 = 0;

        return matrix4f;
    }

}
