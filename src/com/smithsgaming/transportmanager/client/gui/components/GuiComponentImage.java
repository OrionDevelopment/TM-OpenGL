package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.vector.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class GuiComponentImage extends GuiComponentAbstract {

    float xCoord, yCoord, width, heigth;
    boolean centerX, centerY;
    private String resourcePath;
    private TextureRegistry.Texture textureToRender;
    private GeometryRegistry.Geometry geometryToRender;

    public GuiComponentImage (GuiComponentAbstract parent, String resourcePath, float xCoord, float yCoord, float width, float height, boolean centerX, boolean centerY) {
        super(parent);
        this.resourcePath = resourcePath;

        if (!centerX)
            xCoord += ( width / 2f );

        if (!centerY)
            yCoord += ( height / 2f );

        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.width = width;
        this.heigth = height;

        this.centerX = centerX;
        this.centerY = centerY;

        this.geometryToRender = GeometryRegistry.QuadGeometry.constructFromPlaneForTexture(new GuiPlane(-1, 1, 1, -1, 1, 1, -1, -1), new GuiPlane(0, 1, 1, 0, 1, 1, 0, 0));
    }

    @Override
    public void loadTextures () {
        textureToRender = ResourceUtil.loadPNGTexture(resourcePath);
        OpenGLUtil.loadTextureIntoGPU(textureToRender);
    }

    @Override
    public void loadGeometry () {
        OpenGLUtil.loadGeometryIntoGPU(geometryToRender);
    }

    @Override
    public void unLoadTextures () {
        OpenGLUtil.destroyTexture(textureToRender);
    }

    @Override
    public void unLoadGeometry () {
        OpenGLUtil.deleteGeometry(geometryToRender);
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        Camera.Gui.pushMatrix();
        Camera.Gui.translateModel(new Vector3f(xCoord, yCoord, 0f));
        Camera.Gui.scaleModel(new Vector3f(1f / ( 2f / width ), 1f / ( 2f / heigth ), 1f));
        Camera.Gui.pushMatrix();


        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Gui, geometryToRender, textureToRender, ShaderRegistry.Shaders.guiTextured);
        glDisable(GL_BLEND);

        Camera.Gui.popMatrix();
        Camera.Gui.popMatrix();
    }
}
