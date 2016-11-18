
package com.smithsgaming.transportmanager.util;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.render.core.textures.Texture;
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
        TransportManagerClient.clientLogger.debug("Loading stitchable Texture:" + fileName);

        ByteBuffer buf = loadPNGBuffer(fileName);
        int width = getPNGWidth(fileName);
        int height = getPNGHeight(fileName);

        return new Texture(fileName, buf, width, height);
    }

    public static Texture loadPNGTexture (String fileName) {
        TransportManagerClient.clientLogger.debug("Loading non stitchable Texture:" + fileName);
        ByteBuffer buf = loadPNGBuffer(fileName);
        int width = getPNGWidth(fileName);
        int height = getPNGHeight(fileName);

        return new Texture(fileName, buf, width, height, 0, 0, false, false, -1);
    }

    private static ByteBuffer loadPNGBuffer (String fileName) {
        TransportManagerClient.clientLogger.trace("Loading ByteBuffer for Texture:" + fileName);
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

    public static int[] convertByteArrayToIntArray(byte[] data) {
        if ((data.length % 4) != 0) throw new IllegalArgumentException("The given Data is no pixel data");
        int[] pixels = new int[data.length / 4];

        for (int i = 0; i < pixels.length; i++) {
            int red = data[i*4];
            int green = data[i*4 + 1];
            int blue = data[i*4 + 2];
            int alpha = data[i*4 + 3];

            pixels[i] = (red << 24) | (green << 16) | (blue << 8) | alpha;
        }

        return pixels;
    }

    public static byte[] convertIntArrayToByteArray(int[] pixels) {
        byte[] data = new byte[pixels.length * 4];

        for (int i = 0; i < pixels.length; i++) {
            data[i * 4] = (byte) (pixels[i] >> 24);
            data[i * 4 + 1] = (byte) (pixels[i] >> 16);
            data[i * 4 + 2] = (byte) (pixels[i] >> 8);
            data[i * 4 + 3] = (byte) (pixels[i]);
        }

        return data;
    }

    public static ByteBuffer generateBufferFromData(byte[] data) {
        return ByteBuffer.wrap(data);
    }

    public static ByteBuffer generateBufferFromPixels(int[] pixels) {
        return generateBufferFromData(convertIntArrayToByteArray(pixels));
    }

    public static int[] generatePixelsFromByteBuffer(ByteBuffer buffer) {
        return convertByteArrayToIntArray(buffer.array());
    }

}
