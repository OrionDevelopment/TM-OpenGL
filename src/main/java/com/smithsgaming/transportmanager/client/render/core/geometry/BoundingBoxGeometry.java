package com.smithsgaming.transportmanager.client.render.core.geometry;

import com.smithsgaming.transportmanager.client.graphics.AABox;
import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.render.core.TexturedVertex;
import com.smithsgaming.transportmanager.client.render.core.VertexInformation;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by marcf on 3/22/2017.
 */
public class BoundingBoxGeometry extends Geometry
{

    public BoundingBoxGeometry(Builder builder)
    {
        super(GeometryRegistry.GeometryType.BOX, builder.vertices, VertexInformation.DEFAULT);
    }

    public static class Builder
    {
        //Get a random color for the bounding box.
        private final Color            color    = new Color(new Random().nextInt());
        private       TexturedVertex[] vertices = new TexturedVertex[8];

        public Builder(AABox box)
        {
            ArrayList<TexturedVertex> vertices = new ArrayList<>(8);
            for (Vector3f point : box.getPoints())
            {
                vertices.add(new TexturedVertex().setRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                               .setST(0, 0)
                               .setXYZ(point.x, point.y, point.z)
                               .setInformation(VertexInformation.DEFAULT));
            }

            vertices.toArray(this.vertices);
        }

        public BoundingBoxGeometry build()
        {
            return new BoundingBoxGeometry(this);
        }
    }
}
