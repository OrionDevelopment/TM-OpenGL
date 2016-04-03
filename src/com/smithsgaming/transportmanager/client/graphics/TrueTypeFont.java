package com.smithsgaming.transportmanager.client.graphics;

import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.registries.ShaderRegistry;
import com.smithsgaming.transportmanager.client.render.textures.Texture;
import com.smithsgaming.transportmanager.util.OpenGLUtil;
import com.smithsgaming.transportmanager.util.TexturedVertex;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;
import javafx.util.Pair;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public class TrueTypeFont {
    public final static int ALIGN_LEFT = 0, ALIGN_RIGHT = 1, ALIGN_CENTER = 2;

    private HashMap<Character, Pair<CharGeometry, CharTexture>> characterPairHashMap = new HashMap<>();

    private boolean antiAlias;
    private int fontSize = 0;
    private int fontHeight = 0;
    private Texture fontTextureMap;
    private int textureWidth = 512;
    private int textureHeight = 512;
    private Font font;
    private FontMetrics fontMetrics;

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

            fontTextureMap = new Texture(fontMetrics.getFont().getName() + "-Map", byteBuffer, width, height);
            fontTextureMap.setInternalFormat(GL11.GL_RGBA8);

            OpenGLUtil.loadTextureIntoGPU(fontTextureMap);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
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
            g.setColor(new Color(1, 1, 1, 1));
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

                CharTexture charTexture = CharTexture.getForChar(fontMetrics, ch, ( (float) positionX ) / textureWidth, ( (float) positionY ) / textureHeight);
                CharGeometry charGeometry = CharGeometry.getForChar(charTexture, textureWidth, textureHeight);
                OpenGLUtil.loadGeometryIntoGPU(charGeometry);

                positionX += fontImage.getWidth();
                characterPairHashMap.put(ch, new Pair<>(charGeometry, charTexture));
            }

            loadImage(imgTemp);

            for (Map.Entry<Character, Pair<CharGeometry, CharTexture>> charToGeoTexEntry : characterPairHashMap.entrySet()) {
                charToGeoTexEntry.getValue().getValue().setBoundTextureUnit(fontTextureMap.getBoundTextureUnit());
                charToGeoTexEntry.getValue().getValue().setOpenGLTextureId(fontTextureMap.getOpenGLTextureId());
                charToGeoTexEntry.getValue().getValue().setInternalFormat(fontTextureMap.getInternalFormat());
            }

            File texture = new File("Font-" + font.getName() + ".png");
            ImageIO.write(imgTemp, "png", texture);
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
        GuiPlaneI area = getOccupiedAreaForText(text);

        float startY = y;

        if (format == ALIGN_CENTER) {
            startY -= (area.getHeight() / 2);
        }

        String lines[] = text.split("\\r?\\n");

        for (String line : lines) {
            switch (format) {
                case ALIGN_RIGHT:
                    drawStringLine(camera, x - getOccupiedAreaForLine(line).getWidth(), startY, line);
                    break;
                case ALIGN_CENTER:
                    drawStringLine(camera, x - getOccupiedAreaForLine(line).getWidth() / 2f, startY, line);
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

    public GuiPlaneI getOccupiedAreaForText(String text) {
        String lines[] = text.split("\\r?\\n");
        int width = 0, height = 0;

        for (String line : lines) {
            GuiPlaneI lineArea = getOccupiedAreaForLine(line);

            height += lineArea.getHeight();

            if (width < lineArea.getWidth())
                width = lineArea.getWidth();
        }

        return new GuiPlaneI(new Vector2i(0, 0), new Vector2i(width, -height));
    }

    private GuiPlaneI getOccupiedAreaForLine(String line) {
        int width = 0, height = 0;

        for (Character c : line.toCharArray()) {
            width += characterPairHashMap.get(c).getValue().getWidth();

            if (height < characterPairHashMap.get(c).getValue().getHeight())
                height = characterPairHashMap.get(c).getValue().getHeight();
        }

        return new GuiPlaneI(new Vector2i(0, 0), new Vector2i(width, -height));
    }

    public void destroy () {
        OpenGLUtil.destroyTexture(fontTextureMap);
        characterPairHashMap.clear();
    }

    private static class CharGeometry extends GeometryRegistry.Geometry {
        private CharGeometry (TexturedVertex[] charVertexes) {
            super(GeometryRegistry.GeometryType.QUAD, charVertexes);
        }

        public static CharGeometry getForChar (Texture charTexture, float mapHeight, float mapWidth) {
            int charWidth = charTexture.getWidth();
            int charHeight = charTexture.getHeight();

            TexturedVertex topLeft = new TexturedVertex().setST(charTexture.getU(), charTexture.getV()).setXYZ(0, 0, 0);
            TexturedVertex topRight = new TexturedVertex().setST(charTexture.getU() + ( charTexture.getWidth() / mapWidth ), charTexture.getV()).setXYZ(charWidth, 0, 0);
            TexturedVertex bottomRight = new TexturedVertex().setST(charTexture.getU() + ( charTexture.getWidth() / mapWidth ), charTexture.getV() + ( charTexture.getHeight() / mapHeight )).setXYZ(charWidth, charHeight, 0);
            TexturedVertex bottomLeft = new TexturedVertex().setST(charTexture.getU(), charTexture.getV() + ( charTexture.getHeight() / mapHeight )).setXYZ(0, charHeight, 0);

            return new CharGeometry(new TexturedVertex[]{topLeft, bottomLeft, topRight, bottomRight});
        }
    }

    private static class CharTexture extends Texture {
        private CharTexture (String textureName, ByteBuffer data, int width, int height, float u, float v, boolean isStitched, boolean requiringTextureStitching, int textureStitchId) {
            super(textureName, data, width, height, u, v, isStitched, requiringTextureStitching, textureStitchId);
        }

        public static CharTexture getForChar (FontMetrics fontMetrics, char c, float stitchX, float stitchY) {
            return new CharTexture(fontMetrics.getFont().getName() + "-Char:" + c, null, fontMetrics.charWidth(c) + 8, fontMetrics.getHeight() + 3, stitchX, stitchY, false, false, -1);
        }
    }
}