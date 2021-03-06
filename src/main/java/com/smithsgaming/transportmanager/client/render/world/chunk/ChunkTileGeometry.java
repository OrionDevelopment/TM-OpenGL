/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.render.world.chunk;

import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.registries.ShaderRegistry;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.client.render.IRenderer;
import com.smithsgaming.transportmanager.client.render.core.TexturedVertex;
import com.smithsgaming.transportmanager.client.render.core.TileTexturedVertex;
import com.smithsgaming.transportmanager.client.render.core.TileVertexInformation;
import com.smithsgaming.transportmanager.client.render.core.VertexInformation;
import com.smithsgaming.transportmanager.client.render.core.geometry.Geometry;
import com.smithsgaming.transportmanager.client.render.core.textures.Texture;
import com.smithsgaming.transportmanager.client.world.chunk.ChunkClient;
import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import com.smithsgaming.transportmanager.util.OpenGLUtil;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneF;
import com.smithsgaming.transportmanager.util.world.TileDirection;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;

/**
 *  ------ Class not Documented ------
 */
public class ChunkTileGeometry extends Geometry implements IRenderer {

    public static int VertexCount = 0;
    private ChunkClient chunkClient;
    private int[] verticesIndecis;
    private int resetIndex;

    public ChunkTileGeometry (ChunkClient chunkClient) {
        super(GeometryRegistry.GeometryType.QUAD, new TexturedVertex[0], VertexInformation.DEFAULT);

        this.chunkClient = chunkClient;

        buildGeometry();
    }

    public void buildGeometry () {
        ArrayList<TexturedVertex> texturedVertices = new ArrayList<>();

        for (int x = 0; x < Chunk.chunkSize; x++) {
            for (int z = 0; z < Chunk.chunkSize; z++) {
                if (chunkClient.getTileAtPos(x, z) != null) {
                    Tile tile = chunkClient.getTileAtPos(x,z);
                    Vector2i worldPos = new Vector2i(chunkClient.getChunkX() * Chunk.chunkSize + x, chunkClient.getChunkZ() * Chunk.chunkSize + z);
                    Texture tileTexture = TextureRegistry.instance.getTextureForName(tile.getIdentity());

                    if (tileTexture != null && tileTexture.getOpenGLTextureId() == TextureRegistry.Textures.Stitched.Tiles.getOpenGLTextureId()) {
                        Vector2f topLeftQuadCorner = new Vector2f(chunkClient.getChunkX() * Chunk.chunkSize + x - (chunkClient.getWorld().getCoreData().getWorldWidth() / 2),
                                                                   (chunkClient.getWorld().getCoreData().getWorldHeight() / 2) - (chunkClient.getChunkZ() * Chunk.chunkSize + z));
                        Vector2f lowerRightQuadCorner = new Vector2f(topLeftQuadCorner.x + 1, topLeftQuadCorner.y - 1);

                        GuiPlaneF geometryPlane = new GuiPlaneF(topLeftQuadCorner, lowerRightQuadCorner);
                        GuiPlaneF texturePlane = tileTexture.getArea().getDeltaedVariant();

                        final TileTexturedVertex topLeft = (TileTexturedVertex) new TileTexturedVertex().setRGB(1f, 1f, 1f).setST(Math.abs(texturePlane.getTopLeftCoordinate().x), Math.abs(texturePlane.getTopLeftCoordinate().y)).setXYZ(geometryPlane.getTopLeftCoordinate().x, 0, geometryPlane.getTopLeftCoordinate().y);
                        final TileTexturedVertex topRight = (TileTexturedVertex) new TileTexturedVertex().setRGB(1f, 1f, 1f).setST(Math.abs(texturePlane.getTopRightCoordinate().x), Math.abs(texturePlane.getTopRightCoordinate().y)).setXYZ(geometryPlane.getTopRightCoordinate().x, 0, geometryPlane.getTopRightCoordinate().y);
                        final TileTexturedVertex bottomRight = (TileTexturedVertex) new TileTexturedVertex().setRGB(1f, 1f, 1f).setST(Math.abs(texturePlane.getLowerRightCoordinate().x), Math.abs(texturePlane.getLowerRightCoordinate().y)).setXYZ(geometryPlane.getLowerRightCoordinate().x, 0, geometryPlane.getLowerRightCoordinate().y);
                        final TileTexturedVertex bottomLeft = (TileTexturedVertex) new TileTexturedVertex().setRGB(1f, 1f, 1f).setST(Math.abs(texturePlane.getLowerLeftCoordinate().x), Math.abs(texturePlane.getLowerLeftCoordinate().y)).setXYZ(geometryPlane.getLowerLeftCoordinate().x, 0, geometryPlane.getLowerLeftCoordinate().y);

                        for(TileDirection direction : TileDirection.values()) {
                            Vector2i offsetPos =  direction.offset(worldPos);
                            if (offsetPos.x < 0 || offsetPos.x > chunkClient.getWorld().getCoreData().getWorldWidth() || offsetPos.y < 0 || offsetPos.y > chunkClient.getWorld().getCoreData().getWorldHeight()) continue;

                            Tile offsetTile = chunkClient.getWorld().getTileAtPos(offsetPos.x, offsetPos.y);

                            if (offsetTile == null || offsetTile.equals(tile)) continue;

                            if (offsetTile.createsBorders()) {
                                GuiPlaneF overlayTexturePlane = TextureRegistry.instance.getBorderTextureForTileAndDirection(offsetTile, direction).getArea();

                                topLeft.setOst(direction, overlayTexturePlane.getTopLeftCoordinate().x, overlayTexturePlane.getTopLeftCoordinate().y);
                                topRight.setOst(direction, overlayTexturePlane.getTopRightCoordinate().x, overlayTexturePlane.getTopRightCoordinate().y);
                                bottomRight.setOst(direction, overlayTexturePlane.getLowerRightCoordinate().x, overlayTexturePlane.getLowerRightCoordinate().y);
                                bottomLeft.setOst(direction, overlayTexturePlane.getLowerLeftCoordinate().x, overlayTexturePlane.getLowerRightCoordinate().y);
                            }
                        }

                        Geometry positionGeometry = new GeometryRegistry.QuadGeometry(new TexturedVertex[]{topLeft, bottomLeft, topRight, bottomRight});

                        Collections.addAll(texturedVertices, positionGeometry.getVertices());
                    }
                }
            }
        }

        VertexCount += texturedVertices.size();

        resetIndex = texturedVertices.size();
        verticesIndecis = new int[texturedVertices.size() + texturedVertices.size() / 4];
        for (int i = 0; i < verticesIndecis.length; i++) {
            if (i % 5 == 4)
                verticesIndecis[i] = resetIndex;
            else
                verticesIndecis[i] = ( i - ( i / 5 ) );
        }

        this.vertices = texturedVertices.toArray(new TexturedVertex[0]);
    }

    @Override
    public VertexInformation getInformation()
    {
        return TileVertexInformation.INSTANCE;
    }

    @Override
    public int getVertexCount()
    {
        return verticesIndecis.length;
    }

    @Override
    public boolean requiresResetting()
    {
        return true;
    }

    @Override
    public int getResetIndex()
    {
        return resetIndex;
    }

    @Override
    public IntBuffer getIndicesBuffer () {
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(verticesIndecis.length);
        indicesBuffer.put(verticesIndecis);
        indicesBuffer.flip();

        return indicesBuffer;
    }

    @Override
    public void render () {
        OpenGLUtil.drawGeometryWithShader(Camera.Player, this, ShaderRegistry.Shaders.textured);
    }
}
