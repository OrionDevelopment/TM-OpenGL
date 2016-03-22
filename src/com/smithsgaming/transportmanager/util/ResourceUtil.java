
package com.smithsgaming.transportmanager.util;

import com.smithsgaming.transportmanager.client.registries.*;
import de.matthiasmann.twl.utils.*;

import java.io.*;
import java.nio.*;
import java.util.*;

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

    public static TextureRegistry.Texture loadPNGTexture (String fileName) {
        ByteBuffer buf = null;
        int width = 0;
        int height = 0;

        try {
            // Open the PNG file as an InputStream
            InputStream in = ResourceUtil.class.getResourceAsStream(fileName);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Get the width and height of the texture
            width = decoder.getWidth();
            height = decoder.getHeight();


            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new TextureRegistry.Texture(fileName, buf, width, height);
    }
}
