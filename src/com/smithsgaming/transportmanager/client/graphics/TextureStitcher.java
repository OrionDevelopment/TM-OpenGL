package com.smithsgaming.transportmanager.client.graphics;

import com.google.common.collect.*;
import com.smithsgaming.transportmanager.client.render.textures.*;
import com.smithsgaming.transportmanager.util.*;

import java.util.*;

/**
 * Algorithm finds a position for all registered Textures. Works similar to Minecrafts TextureStitching algorithm, yet
 * has some modifications as our textures work a bit differently. Has no limits to textures set programmatically. The
 * GPU has to be queried how big a texture can be.
 *
 * @Author Marc (Created on: 20.03.2016)
 */
public class TextureStitcher {
    private final Set<Holder> textureHolders = new HashSet<>();
    private final List<TextureStitcher.Slot> stitchSlots = new ArrayList<>();
    private final int maxStitchedWidth;
    private final int maxStitchedHeight;
    private final boolean stitchedForcedPower2;
    private int currentStitchedWidth;
    private int currentStitchedHeight;

    public TextureStitcher (int maxTextureWidth, int maxTextureHeight, boolean shouldForcePower2) {
        this.maxStitchedWidth = maxTextureWidth;
        this.maxStitchedHeight = maxTextureHeight;
        this.stitchedForcedPower2 = shouldForcePower2;
    }

    public int getCurrentStitchedWidth () {
        return this.currentStitchedWidth;
    }

    public int getCurrentStitchedHeight () {
        return this.currentStitchedHeight;
    }

    public void addSprite (Texture texture) {
        TextureStitcher.Holder holder = new TextureStitcher.Holder(texture);
        this.textureHolders.add(holder);
    }

    public void addSprites (ArrayList<Texture> textures) {
        textures.forEach(this::addSprite);
    }

    public void doStitch () {
        TextureStitcher.Holder[] holders = this.textureHolders.toArray(new TextureStitcher.Holder[this.textureHolders.size()]);
        Arrays.sort(holders);

        for (TextureStitcher.Holder holder : holders) {
            if (!this.allocateSlot(holder)) {
                throw new IllegalStateException(holder.getAtlasSprite().getTextureName() + " Cannot be stitched. Aborting!");
            }
        }

        if (this.stitchedForcedPower2) {
            this.currentStitchedWidth = MathUtil.roundUpToPowerOfTwo(this.currentStitchedWidth);
            this.currentStitchedHeight = MathUtil.roundUpToPowerOfTwo(this.currentStitchedHeight);
        }
    }

    public List<Texture> getStitchSlots () {
        List<TextureStitcher.Slot> stitchedSlots = Lists.newArrayList();

        for (TextureStitcher.Slot slot : this.stitchSlots) {
            slot.getAllStitchSlots(stitchedSlots);
        }

        List<Texture> stitchedTextures = Lists.newArrayList();

        for (TextureStitcher.Slot slot : stitchedSlots) {
            TextureStitcher.Holder holder = slot.getStitchHolder();
            Texture texture = holder.getAtlasSprite();

            texture.setU(slot.getOriginX() / ( (float) getCurrentStitchedWidth() ));
            texture.setV(slot.getOriginY() / ( (float) getCurrentStitchedHeight() ));
            texture.setWidth(texture.getPixelWidth() / ( (float) getCurrentStitchedWidth() ));
            texture.setHeight(texture.getPixelHeight() / ( (float) getCurrentStitchedHeight() ));
            texture.setStitched(true);

            stitchedTextures.add(texture);
        }

        return stitchedTextures;
    }

    /**
     * Attempts to find space for specified tile
     */
    private boolean allocateSlot (TextureStitcher.Holder textureHolder) {
        for (int i = 0; i < this.stitchSlots.size(); ++i) {
            if (( this.stitchSlots.get(i) ).addSlot(textureHolder)) {
                return true;
            }
        }

        return this.expandAndAllocateSlot(textureHolder);
    }

    /**
     * Expand stitched texture in order to make space for specified tile
     */
    private boolean expandAndAllocateSlot (TextureStitcher.Holder textureHolder) {
        int minWidthHeight = Math.min(textureHolder.getWidth(), textureHolder.getHeight());
        boolean isEmpty = this.currentStitchedWidth == 0 && this.currentStitchedHeight == 0;
        boolean changeTracker;

        if (this.stitchedForcedPower2) {
            int currentPow2Width = MathUtil.roundUpToPowerOfTwo(this.currentStitchedWidth);
            int currentPow2Height = MathUtil.roundUpToPowerOfTwo(this.currentStitchedHeight);
            int nextPow2Width = MathUtil.roundUpToPowerOfTwo(this.currentStitchedWidth + minWidthHeight);
            int nextPow2Height = MathUtil.roundUpToPowerOfTwo(this.currentStitchedHeight + minWidthHeight);
            boolean widthOverflow = nextPow2Width <= this.maxStitchedWidth;
            boolean heightOverflow = nextPow2Height <= this.maxStitchedHeight;

            if (!widthOverflow && !heightOverflow) {
                return false;
            }

            boolean widthChanged = currentPow2Width != nextPow2Width;
            boolean heightChanged = currentPow2Height != nextPow2Height;

            if (widthChanged ^ heightChanged) {
                changeTracker = !widthChanged;
            } else {
                changeTracker = widthOverflow && currentPow2Width <= currentPow2Height;
            }
        } else {
            boolean nextWidthOverflow = this.currentStitchedWidth + minWidthHeight <= this.maxStitchedWidth;
            boolean nextHeightOverflow = this.currentStitchedHeight + minWidthHeight <= this.maxStitchedHeight;

            if (!nextWidthOverflow && !nextHeightOverflow) {
                return false;
            }

            changeTracker = nextWidthOverflow && ( isEmpty || this.currentStitchedWidth <= this.currentStitchedHeight );
        }

        int maxWidthHeight = Math.max(textureHolder.getWidth(), textureHolder.getHeight());

        if (MathUtil.roundUpToPowerOfTwo(( changeTracker ? this.currentStitchedHeight : this.currentStitchedWidth ) + maxWidthHeight) > ( changeTracker ? this.maxStitchedHeight : this.maxStitchedWidth )) {
            return false;
        } else {
            TextureStitcher.Slot stitchedSlot;

            if (changeTracker) {

                if (this.currentStitchedHeight == 0) {
                    this.currentStitchedHeight = textureHolder.getHeight();
                }

                stitchedSlot = new TextureStitcher.Slot(this.currentStitchedWidth, 0, textureHolder.getWidth(), this.currentStitchedHeight);
                this.currentStitchedWidth += textureHolder.getWidth();
            } else {
                stitchedSlot = new TextureStitcher.Slot(0, this.currentStitchedHeight, this.currentStitchedWidth, textureHolder.getHeight());
                this.currentStitchedHeight += textureHolder.getHeight();
            }

            stitchedSlot.addSlot(textureHolder);
            this.stitchSlots.add(stitchedSlot);
            return true;
        }
    }

    public static class Holder implements Comparable<TextureStitcher.Holder> {
        private final Texture texture;
        private final int width;
        private final int height;

        public Holder (Texture texture) {
            this.texture = texture;
            this.width = texture.getPixelWidth();
            this.height = texture.getPixelHeight();
        }

        public Texture getAtlasSprite () {
            return this.texture;
        }

        public int getWidth () {
            return width;
        }

        public int getHeight () {
            return height;
        }

        public int compareTo (TextureStitcher.Holder other) {
            int i;

            if (this.getHeight() == other.getHeight()) {
                if (this.getWidth() == other.getWidth()) {
                    if (this.texture.getTextureName() == null) {
                        return other.texture.getTextureName() == null ? 0 : -1;
                    }

                    return this.texture.getTextureName().compareTo(other.texture.getTextureName());
                }

                i = this.getWidth() < other.getWidth() ? 1 : -1;
            } else {
                i = this.getHeight() < other.getHeight() ? 1 : -1;
            }

            return i;
        }
    }

    public static class Slot {
        private final int originX;
        private final int originY;
        private final int width;
        private final int height;
        private List<TextureStitcher.Slot> subSlots;
        private TextureStitcher.Holder holder;

        public Slot (int originX, int originY, int widthIn, int heightIn) {
            this.originX = originX;
            this.originY = originY;
            this.width = widthIn;
            this.height = heightIn;
        }

        public TextureStitcher.Holder getStitchHolder () {
            return this.holder;
        }

        public int getOriginX () {
            return this.originX;
        }

        public int getOriginY () {
            return this.originY;
        }

        public boolean addSlot (TextureStitcher.Holder holder) {
            if (this.holder != null) {
                return false;
            } else {
                int holderWidth = holder.getWidth();
                int holderHeight = holder.getHeight();

                if (holderWidth <= this.width && holderHeight <= this.height) {
                    if (holderWidth == this.width && holderHeight == this.height) {
                        this.holder = holder;
                        return true;
                    } else {
                        if (this.subSlots == null) {
                            this.subSlots = Lists.newArrayListWithCapacity(1);
                            this.subSlots.add(new TextureStitcher.Slot(this.originX, this.originY, holderWidth, holderHeight));
                            int restWidth = this.width - holderWidth;
                            int restHeight = this.height - holderHeight;

                            if (restHeight > 0 && restWidth > 0) {
                                int maxWidth = Math.max(this.height, restWidth);
                                int maxHeight = Math.max(this.width, restHeight);

                                if (maxWidth >= maxHeight) {
                                    this.subSlots.add(new TextureStitcher.Slot(this.originX, this.originY + holderHeight, holderWidth, restHeight));
                                    this.subSlots.add(new TextureStitcher.Slot(this.originX + holderWidth, this.originY, restWidth, this.height));
                                } else {
                                    this.subSlots.add(new TextureStitcher.Slot(this.originX + holderWidth, this.originY, restWidth, holderHeight));
                                    this.subSlots.add(new TextureStitcher.Slot(this.originX, this.originY + holderHeight, this.width, restHeight));
                                }
                            } else if (restWidth == 0) {
                                this.subSlots.add(new TextureStitcher.Slot(this.originX, this.originY + holderHeight, holderWidth, restHeight));
                            } else if (restHeight == 0) {
                                this.subSlots.add(new TextureStitcher.Slot(this.originX + holderWidth, this.originY, restWidth, holderHeight));
                            }
                        }

                        for (TextureStitcher.Slot slot : this.subSlots) {
                            if (slot.addSlot(holder)) {
                                return true;
                            }
                        }

                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        /**
         * Gets the slot and all its subslots
         */
        public void getAllStitchSlots (List<TextureStitcher.Slot> slotList) {
            if (this.holder != null) {
                slotList.add(this);
            } else if (this.subSlots != null) {
                for (TextureStitcher.Slot stitcher$slot : this.subSlots) {
                    stitcher$slot.getAllStitchSlots(slotList);
                }
            }
        }
    }
}