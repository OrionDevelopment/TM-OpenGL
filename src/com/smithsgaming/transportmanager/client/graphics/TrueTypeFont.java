package com.smithsgaming.transportmanager.client.graphics;

import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;
import javafx.util.*;
import org.lwjgl.util.vector.*;

import java.awt.*;
import java.awt.image.*;
import java.nio.*;
import java.util.*;

/**
 * A TrueType font implementation originally for Slick, edited for Bobjob's Engine  *  * @original author James Chambers
 * (Jimmy)  * @original author Jeremy Adams (elias4444)  * @original author Kevin Glass (kevglass)  * @original author
 * Peter Korzuszek (genail)  *  * @new version edited by David Aaron Muhar (bobjob)
 */
public class TrueTypeFont {
    public final static int ALIGN_LEFT = 0, ALIGN_RIGHT = 1, ALIGN_CENTER = 2;

    private HashMap<Character, Pair<CharGeometry, CharTexture>> characterPairHashMap = new HashMap<>();

    /** Map of user defined font characters (Character <-> IntObject) */
    private Map customChars = new HashMap();

    /** Boolean flag on whether AntiAliasing is enabled or not */
    private boolean antiAlias;

    /** Font's size */
    private int fontSize = 0;

    /** Font's height */
    private int fontHeight = 0;

    /** Texture used to cache the font 0-255 characters */
    private int fontTextureID;
    private TextureRegistry.Texture fontTextureMap;

    /** Default font texture width */
    private int textureWidth = 512;

    /** Default font texture height */
    private int textureHeight = 512;

    /** A reference to Java's AWT Font that we create our font texture from */
    private Font font;
    /** The font metrics for our Java AWT font */

    private FontMetrics fontMetrics;
    private int correctL = 9, correctR = 8;

    public TrueTypeFont (Font font, boolean antiAlias, char[] additionalChars) {
        System.out.println("[Client] Loading font: " + font.getFontName());
        this.font = font;
        this.fontSize = font.getSize() + 3;
        this.antiAlias = antiAlias;

        BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
        if (antiAlias == true) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        fontMetrics = g.getFontMetrics();

        createSet(additionalChars);

        fontHeight -= 1;

        if (fontHeight <= 0)
            fontHeight = 1;
    }

    public TrueTypeFont (Font font, boolean antiAlias) {
        this(font, antiAlias, null);
    }

    public static boolean isSupported (String fontname) {
        Font font[] = getFonts();
        for (int i = font.length - 1; i >= 0; i--) {
            if (font[i].getName().equalsIgnoreCase(fontname)) return true;
        }
        return false;
    }

    public static Font[] getFonts () {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    }

    public static byte[] intToByteArray (int value) {
        return new byte[]{(byte) ( value >>> 24 ), (byte) ( value >>> 16 ), (byte) ( value >>> 8 ), (byte) value};
    }

    private void loadImage (BufferedImage bufferedImage) {
        try {
            short width = (short) bufferedImage.getWidth();
            short height = (short) bufferedImage.getHeight();
            int bpp = (byte) bufferedImage.getColorModel().getPixelSize();
            ByteBuffer byteBuffer;
            DataBuffer db = bufferedImage.getData().getDataBuffer();
            if (db instanceof DataBufferInt) {
                int intI[] = ( (DataBufferInt) ( bufferedImage.getData().getDataBuffer() ) ).getData();
                byte newI[] = new byte[intI.length * 4];
                for (int i = 0; i < intI.length; i++) {
                    byte b[] = intToByteArray(intI[i]);
                    int newIndex = i * 4;
                    newI[newIndex] = b[1];
                    newI[newIndex + 1] = b[2];
                    newI[newIndex + 2] = b[3];
                    newI[newIndex + 3] = b[0];
                }
                byteBuffer = ByteBuffer.allocateDirect(width * height * ( bpp / 8 )).order(ByteOrder.nativeOrder()).put(newI);
            } else {
                byteBuffer = ByteBuffer.allocateDirect(width * height * ( bpp / 8 )).order(ByteOrder.nativeOrder()).put(( (DataBufferByte) ( bufferedImage.getData().getDataBuffer() ) ).getData());
            }
            byteBuffer.flip();

            fontTextureMap = new TextureRegistry.Texture(fontMetrics.getFont().getName() + "-Map", byteBuffer, width, height);

            OpenGLUtil.loadTextureIntoGPU(fontTextureMap);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void setCorrection (boolean on) {
        if (on) {
            correctL = 2;
            correctR = 1;
        } else {
            correctL = 0;
        }
        correctR = 0;
    }

    private BufferedImage getFontImage (char ch) {
        int charwidth = fontMetrics.charWidth(ch) + 8;
        if (charwidth <= 0) {
            charwidth = 7;
        }
        int charheight = fontMetrics.getHeight() + 3;
        if (charheight <= 0) {
            charheight = fontSize;
        }

        // Create another image holding the character we are creating
        BufferedImage fontImage;
        fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gt = (Graphics2D) fontImage.getGraphics();
        if (antiAlias == true) {
            gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        gt.setFont(font);
        gt.setColor(Color.WHITE);
        int charx = 3;
        int chary = 1;
        gt.drawString(String.valueOf(ch), ( charx ), ( chary ) + fontMetrics.getAscent());
        return fontImage;
    }

    private void createSet (char[] customCharsArray) {
        //If there are custom chars then I expand the font texture twice
        if (customCharsArray != null && customCharsArray.length > 0) {
            textureWidth *= 2;
        }

        // In any case this should be done in other way. Texture with size 512x512
        // can maintain only 256 characters with resolution of 32x32. The texture
        // size should be calculated dynamicaly by looking at character sizes.
        try {
            BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) imgTemp.getGraphics();
            g.setColor(new Color(0, 0, 0, 1));
            g.fillRect(0, 0, textureWidth, textureHeight);
            int rowHeight = 0;
            int positionX = 0;
            int positionY = 0;
            int customCharsLength = ( customCharsArray != null ) ? customCharsArray.length : 0;
            for (int i = 0; i < 256 + customCharsLength; i++) {

                char ch = ( i < 256 ) ? (char) i : customCharsArray[i - 256];
                BufferedImage fontImage = getFontImage(ch);

                if (positionX + fontImage.getWidth() >= textureWidth) {
                    positionX = 0;
                    positionY += rowHeight;
                    rowHeight = 0;
                }

                //Update the FontHeight if need be.
                if (fontImage.getHeight() > fontHeight) {
                    fontHeight = fontImage.getHeight();
                }

                //Update the rowheight if need be.
                if (fontImage.getHeight() > rowHeight) {
                    rowHeight = fontImage.getHeight();
                }
                g.drawImage(fontImage, positionX, positionY, null);

                CharTexture charTexture = CharTexture.getForChar(fontMetrics, ch, positionX, positionY);
                CharGeometry charGeometry = CharGeometry.getForChar(fontMetrics, ch, charTexture);

                positionX += fontImage.getWidth();
                characterPairHashMap.put(ch, new Pair<>(charGeometry, charTexture));
            }

            loadImage(imgTemp);

            for (Map.Entry<Character, Pair<CharGeometry, CharTexture>> charToGeoTexEntry : characterPairHashMap.entrySet()) {
                charToGeoTexEntry.getValue().getValue().setBoundTextureUnit(fontTextureMap.getBoundTextureUnit());
                charToGeoTexEntry.getValue().getValue().setOpenGLTextureId(fontTextureMap.getOpenGLTextureId());
            }
        } catch (Exception e) {
            System.err.println("Failed to create font.");
            e.printStackTrace();
        }
    }

    public int getLineHeight () {
        return fontHeight;
    }

    public void drawString (Camera camera, float x, float y, String text) {
        drawString(camera, x, y, text, ALIGN_LEFT);
    }

    public void drawString (Camera camera, float x, float y, String text, int format) {
        float startY = y;

        String lines[] = text.split("\\r?\\n");

        for (String line : lines) {
            switch (format) {
                case ALIGN_RIGHT:
                    drawStringLine(camera, x - fontMetrics.stringWidth(line), startY, line);
                    break;
                case ALIGN_CENTER:
                    drawStringLine(camera, x - fontMetrics.stringWidth(line) / 2f, startY, line);
                    break;
                case ALIGN_LEFT:
                default:
                    drawStringLine(camera, x, startY, line);
                    break;
            }

            startY += getLineHeight();
        }
    }

    private void drawStringLine (Camera camera, float x, float y, String line) {
        camera.pushMatrix();
        camera.translateModel(new Vector3f(x, y, 0));
        camera.pushMatrix();

        float currentX = 0;

        for (char c : line.toCharArray()) {
            camera.translateModel(new Vector3f(currentX, 0, 0));

            CharGeometry charGeometry = characterPairHashMap.get(c).getKey();
            CharTexture charTexture = characterPairHashMap.get(c).getValue();

            OpenGLUtil.drawGeometryWithShaderAndTexture(camera, charGeometry, charTexture, ShaderRegistry.Shaders.guiTextured);

            currentX += charTexture.getWidth();

            camera.popMatrix();
        }

        camera.popMatrix();
        camera.popMatrix();
    }

    public void destroy () {
        OpenGLUtil.destroyTexture(fontTextureMap);
        characterPairHashMap.clear();
    }

    private static class CharGeometry extends GeometryRegistry.Geometry {
        private CharGeometry (TexturedVertex[] charVertexes) {
            super(GeometryRegistry.GeometryType.QUAD, charVertexes);
        }

        public static CharGeometry getForChar (FontMetrics fontMetrics, char c, TextureRegistry.Texture charTexture) {
            int charWidth = fontMetrics.charWidth(c) + 8;
            int charHeight = fontMetrics.getHeight() + 3;

            TexturedVertex topLeft = new TexturedVertex().setST(charTexture.getU(), charTexture.getV()).setXYZ(0, 0, 0);
            TexturedVertex topRight = new TexturedVertex().setST(charTexture.getU() + charTexture.getWidth(), charTexture.getV()).setXYZ(charWidth, 0, 0);
            TexturedVertex bottomRight = new TexturedVertex().setST(charTexture.getU() + charTexture.getWidth(), charTexture.getHeight()).setXYZ(charWidth, charHeight, 0);
            TexturedVertex bottomLeft = new TexturedVertex().setST(charTexture.getU(), charTexture.getV() + charTexture.getHeight()).setXYZ(0, charHeight, 0);

            return new CharGeometry(new TexturedVertex[]{topLeft, bottomLeft, topRight, bottomRight});
        }
    }

    private static class CharTexture extends TextureRegistry.Texture {
        private CharTexture (String textureName, ByteBuffer data, int width, int height, float u, float v, boolean isStitched, boolean requiringTextureStitching, int textureStitchId) {
            super(textureName, data, width, height, u, v, isStitched, requiringTextureStitching, textureStitchId);
        }

        public static CharTexture getForChar (FontMetrics fontMetrics, char c, float stitchX, float stitchY) {
            return new CharTexture(fontMetrics.getFont().getName() + "-Char:" + c, null, fontMetrics.charWidth(c) + 8, fontMetrics.getHeight() + 3, stitchX, stitchY, true, false, -1);
        }
    }
}