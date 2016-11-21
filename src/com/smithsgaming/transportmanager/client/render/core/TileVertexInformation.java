package com.smithsgaming.transportmanager.client.render.core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * Created by marcf on 11/18/2016.
 */
public class TileVertexInformation extends VertexInformation {

    public static final TileVertexInformation INSTANCE = new TileVertexInformation();

    private TileVertexInformation() {
        super(4, 4, 4, 18);
    }

    @Override
    public void upLoadVertexAttributesForShader(int programId) {
        // Position information will be attribute 0
        GL20.glBindAttribLocation(programId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(programId, 1, "in_Color");
        // Textute information will be attribute 2
        GL20.glBindAttribLocation(programId, 2, "in_mainTextureCoord");
        //Overlay textures attribute count in 3 to 10
        GL20.glBindAttribLocation(programId, 3, "in_overlayTopLeftTextureCoord");
        GL20.glBindAttribLocation(programId, 4, "in_overlayTopTextureCoord");
        GL20.glBindAttribLocation(programId, 5, "in_overlayTopRightTextureCoord");
        GL20.glBindAttribLocation(programId, 6, "in_overlayRightTextureCoord");
        GL20.glBindAttribLocation(programId, 7, "in_overlayBottomRightTextureCoord");
        GL20.glBindAttribLocation(programId, 8, "in_overlayBottomTextureCoord");
        GL20.glBindAttribLocation(programId, 9, "in_overlayBottomLeftTextureCoord");
        GL20.glBindAttribLocation(programId, 10, "in_overlayLeftTextureCoord");
    }

    @Override
    public void upLoadVertexAttributesForGeometry() {
        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, getPositionElementCount(), GL11.GL_FLOAT,
                false, getStride(), getPositionByteOffset());
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, getColorElementCount(), GL11.GL_FLOAT,
                false, getStride(), getColorByteOffset());
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT,
                false, getStride(), getTextureByteOffset());
        
        //Uploading the overlay textures
        for (int i = 3; i < 11 ; i++){
            GL20.glVertexAttribPointer(i, 2, GL11.GL_FLOAT,
                    false, getStride(), getTextureByteOffset() + ((i-2) * (2 * getElementBytes())));
        }
    }

    @Override
    public void enableVertexAttributesForRender() {
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);
        GL20.glEnableVertexAttribArray(4);
        GL20.glEnableVertexAttribArray(5);
        GL20.glEnableVertexAttribArray(6);
        GL20.glEnableVertexAttribArray(7);
        GL20.glEnableVertexAttribArray(8);
        GL20.glEnableVertexAttribArray(9);
        GL20.glEnableVertexAttribArray(10);
        
    }

    @Override
    public void disableVertexAttributesForRender() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL20.glDisableVertexAttribArray(4);
        GL20.glDisableVertexAttribArray(5);
        GL20.glDisableVertexAttribArray(6);
        GL20.glDisableVertexAttribArray(7);
        GL20.glDisableVertexAttribArray(8);
        GL20.glDisableVertexAttribArray(9);
        GL20.glDisableVertexAttribArray(10);
    }
}
