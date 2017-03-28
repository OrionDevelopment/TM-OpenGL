

package com.smithsgaming.transportmanager.util.math;

import com.hoten.delaunay.geom.Point;
import com.smithsgaming.transportmanager.client.TransportManagerClient;
import org.joml.Matrix4f;

/**
 * Created by marcf on 3/11/2016.
 */
public class MathUtil
{
    public static final double EPSILON = 1E-10;

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

    public static Matrix4f PojectionSymmetricalMultiplyViewOut(Matrix4f projection, Matrix4f view, Matrix4f out) {
        // http://www.songho.ca/opengl/gl_projectionmatrix.html
        // Project        |  View         = Result
        // d0 0  d8  d12  |  xx yx zx px  = (d0 xx + d8 xz) (d0 yx + d8 yz) (d0 zx + d8 zz) (d0 px + d8 pz) + d12
        // 0  d5 d9  d13  |  xy yy zy py  = (d5 xy + d9 xz) (d5 yy + d9 yz) (d5 zy + d9 zz) (d5 py + d9 pz) + d13
        // 0  0  d10 d14  |  xz yz zz pz  = (d10 xz)        (d10 yz)        (d10 zz)        (d10 pz) + d14
        // 0  0  d11 d15  |  0  0  0  1   = (d11 xz)        (d11 yz)        (d11 zz)        (d11 pz) + d15
        
        out.m00(projection.m00() * view.m00() + projection.m20() * view.m02());
        out.m10(projection.m00()* view.m10()+ projection.m20() * view.m12());
        out.m20(projection.m00()* view.m20() + projection.m20() * view.m22());
        out.m30(projection.m00()* view.m30()+ projection.m20() * view.m32()+ projection.m30());
        out.m01(projection.m11() * view.m01()+ projection.m21() * view.m02());
        out.m11(projection.m11() * view.m11() + projection.m21() * view.m12());
        out.m21(projection.m11() * view.m21() + projection.m21() * view.m22());
        out.m31(projection.m11() * view.m31()+ projection.m21() * view.m32()+ projection.m31());
        out.m02(projection.m22()* view.m02());
        out.m12(projection.m22()* view.m12());
        out.m22(projection.m22()* view.m22());
        out.m32(projection.m22()* view.m32()+ projection.m32());
        out.m03(projection.m23()* view.m02());
        out.m13(projection.m23()* view.m12());
        out.m23(projection.m23()* view.m22());
        out.m33(projection.m23()* view.m32()+ projection.m33());
        return out;
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

    public static Point interpolateCoordinate(Point p1, Point p2, float factor) {
        double xDelta = (p2.x - p1.x);

        double x = (p1.x + (xDelta * factor));
        double y = (p1.y + ((x - p1.x)*((p2.y - p1.y) / (p2.x - p1.x))));

        return new Point(x, y);
    }
}
