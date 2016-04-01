
package com.smithsgaming.transportmanager.util;

import com.smithsgaming.transportmanager.client.render.textures.Texture;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * @Author Marc (Created on: 05.03.2016)
 */
public class ResourceUtil {

    /**
     * Method to load the contents of a File in the Resources of the GameJar into memory.
     *
     * @param filePath The path to the file in the jar.
     *
     * @return A String with the contents of the specific jar.
     *
     * @throws FileNotFoundException Exception thrown when the file does not exist.
     */
    public static String getFileContents (String filePath) throws FileNotFoundException {
        StringBuilder result = new StringBuilder("");

        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            throw e;
        }

        return result.toString();
    }

    public static Texture loadStitchablePNGTexture (String fileName) {
        ByteBuffer buf = loadPNGBuffer(fileName);
        int width = getPNGWidth(fileName);
        int height = getPNGHeight(fileName);

        return new Texture(fileName, buf, width, height);
    }

    public static Texture loadPNGTexture (String fileName) {
        ByteBuffer buf = loadPNGBuffer(fileName);
        int width = getPNGWidth(fileName);
        int height = getPNGHeight(fileName);

        return new Texture(fileName, buf, width, height, 0, 0, false, false, -1);
    }

    private static ByteBuffer loadPNGBuffer (String fileName) {
        ByteBuffer buf = null;
        
        try {
            // Open the PNG file as an InputStream
            InputStream in = ResourceUtil.class.getResourceAsStream(fileName);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf;
    }

    private static int getPNGWidth (String fileName) {
        int width = -1;

        try {
            // Open the PNG file as an InputStream
            InputStream in = ResourceUtil.class.getResourceAsStream(fileName);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            width = decoder.getWidth();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return width;
    }

    private static int getPNGHeight (String fileName) {
        int height = -1;

        try {
            // Open the PNG file as an InputStream
            InputStream in = ResourceUtil.class.getResourceAsStream(fileName);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            height = decoder.getHeight();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return height;
    }
    
}
