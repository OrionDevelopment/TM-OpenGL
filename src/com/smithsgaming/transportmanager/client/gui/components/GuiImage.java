package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.gui.GuiComponent;
import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.registries.ShaderRegistry;
import com.smithsgaming.transportmanager.client.render.textures.Texture;
import com.smithsgaming.transportmanager.util.OpenGLUtil;
import com.smithsgaming.transportmanager.util.ResourceUtil;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_BLEND;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class GuiImage extends GuiComponent {

    private GuiPlaneI area;
    private String resourcePath;
    private Texture textureToRender;
    private GeometryRegistry.Geometry geometryToRender;

    public GuiImage(GuiComponent parent, String resourcePath, GuiPlaneI area) {
        super(parent);
        this.resourcePath = resourcePath;
        this.area = area;

        this.geometryToRender = GeometryRegistry.getDefaultQuadGeometry();
    }

    @Override
    public GuiPlaneI getOccupiedArea() {
        return area;
    }

    @Override
    public void loadTextures () {
        textureToRender = ResourceUtil.loadPNGTexture(resourcePath);
        OpenGLUtil.loadTextureIntoGPU(textureToRender);
    }

    @Override
    public void loadGeometry () {
    }

    @Override
    public void unLoadTextures () {
        OpenGLUtil.destroyTexture(textureToRender);
    }

    @Override
    public void unLoadGeometry () {

    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        Camera.Gui.pushMatrix();
        Camera.Gui.translateModel(new Vector3f(area.getCenterCoord().x, area.getCenterCoord().y, 0f));
        Camera.Gui.scaleModel(new Vector3f(area.getWidth(), area.getHeight(), 1f));
        Camera.Gui.pushMatrix();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Gui, geometryToRender, textureToRender, ShaderRegistry.Shaders.guiTextured);

        GL11.glDisable(GL_BLEND);
        Camera.Gui.popMatrix();
        Camera.Gui.popMatrix();
    }
}
