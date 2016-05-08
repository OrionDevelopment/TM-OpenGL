package com.smithsgaming.transportmanager.client.render.gui;

import com.smithsgaming.transportmanager.client.framework.*;
import net.smert.frameworkgl.*;
import net.smert.frameworkgl.gui.*;
import net.smert.frameworkgl.opengl.*;
import net.smert.frameworkgl.opengl.mesh.*;
import net.smert.frameworkgl.opengl.renderable.*;
import net.smert.frameworkgl.utils.*;

import java.io.*;


/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class GuiLoading implements GuiScreen {

    AbstractRenderable backgroundRenderable;
    AbstractRenderable logoRenderable;

    @Override
    public void onEnd () {

    }

    @Override
    public void onStart () {
        Mesh backgroundMesh = GL.dynamicMeshBuilder
                .setQuality(1, 1, 1)
                .setSize(1, 1, 0)
                .setLocalPosition(0.5f, 0.5f, 0)
                .build("quad")
                .createMesh(true);

        Mesh logoMesh = GL.dynamicMeshBuilder
                .setQuality(1, 1, 1)
                .setSize(356, 125, 0)
                .setTexCoordMinMaxX(0, 1)
                .setTexCoordMinMaxY(0, 1)
                .setTexCoordMinMaxZ(0, 1)
                .setLocalPosition(177, 62, 0)
                .build("quad")
                .createMesh(true);

        Segment backgroundMeshSegment = backgroundMesh.getSegment(0);
        if (backgroundMeshSegment.getMaterial() == null)
            backgroundMeshSegment.setMaterial(new SegmentMaterial());

        backgroundMeshSegment.getMaterial().setColor(Color.WHITE);

        Segment logoSegment = logoMesh.getSegment(0);
        if (logoSegment.getMaterial() == null)
            logoSegment.setMaterial(GL.meshFactory.createSegmentMaterial());

        logoSegment.getMaterial().setTexture(TextureType.DIFFUSE, "menus/Logo.png");

        backgroundRenderable = Fw.graphics.createInterleavedRenderable();
        backgroundRenderable.create(backgroundMesh);

        logoRenderable = Fw.graphics.createDynamicInterleavedRenderable();
        logoRenderable.create(logoMesh);

        // Load textures
        try {
            Fw.graphics.loadTextures(logoMesh);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void render () {
        Fw.graphics.pushMatrix();
        Fw.graphics.scale(TMGameConfiguration.instance.getCurrentWidth(), TMGameConfiguration.instance.getCurrentHeight(), 0F);
        Fw.graphics.render(backgroundRenderable);
        Fw.graphics.popMatrix();

        Fw.graphics.pushMatrix();
        Fw.graphics.translate(( TMGameConfiguration.instance.getCurrentWidth() - 356 ) / 2, ( TMGameConfiguration.instance.getCurrentHeight() - 125 ) / 2, 0f);
        Fw.graphics.render(logoRenderable);
        Fw.graphics.popMatrix();

    }

    @Override
    public void update () {

    }
}
