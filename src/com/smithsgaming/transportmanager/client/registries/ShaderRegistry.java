package com.smithsgaming.transportmanager.client.registries;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.render.core.Shader;
import com.smithsgaming.transportmanager.client.render.core.TileVertexInformation;
import com.smithsgaming.transportmanager.client.render.core.VertexInformation;
import com.smithsgaming.transportmanager.util.*;

import java.io.*;
import java.util.*;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class ShaderRegistry {

    public static final ShaderRegistry instance = new ShaderRegistry();

    private HashMap<Integer, Shader> openGLShaderMap = new HashMap<>();

    private ShaderRegistry () {
    }

    public int registerNewShader (Shader shader) {
        OpenGLUtil.loadShaderProgramm(shader);
        openGLShaderMap.put(shader.getShaderId(), shader);

        return shader.getShaderId();
    }

    public Shader getShaderForOpenGLID (int openGLID) {
        return openGLShaderMap.get(openGLID);
    }

    public void unLoad () {
        openGLShaderMap.values().forEach(OpenGLUtil::deleteShader);

        openGLShaderMap.clear();
    }

    public static class Shaders {

        public static Shader colored;
        public static Shader textured;
        public static Shader tile;
        public static Shader guiColored;
        public static Shader guiTextured;

        static {
            try {
                colored = new Shader("vertexShaderProjection", "fragmentShaderColor", VertexInformation.DEFAULT);
                textured = new Shader("vertexShaderProjection", "fragmentShaderTextured", VertexInformation.DEFAULT);

                tile = new Shader("vertexShaderDefaultWorldTile", "fragmentShaderDefaultWorldTile", TileVertexInformation.INSTANCE);

                guiColored = new Shader("vertexShaderGui", "fragmentShaderColor", VertexInformation.DEFAULT);
                guiTextured = new Shader("vertexShaderGui", "fragmentShaderTextured", VertexInformation.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void init () {
            TransportManagerClient.clientLogger.info("Loading Shaders.");
            ShaderRegistry.instance.registerNewShader(colored);
            ShaderRegistry.instance.registerNewShader(textured);
            ShaderRegistry.instance.registerNewShader(tile);
            ShaderRegistry.instance.registerNewShader(guiColored);
            ShaderRegistry.instance.registerNewShader(guiTextured);
            TransportManagerClient.clientLogger.info("Finished loading Shaders.");
        }
    }

}
