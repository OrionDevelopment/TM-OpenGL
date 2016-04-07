package com.smithsgaming.transportmanager.main.entity;

import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.util.math.Vector2d;
import com.smithsgaming.transportmanager.util.nbt.NBTTagCompound;

import java.util.UUID;

/**
 * Created by Tim on 07/04/2016.
 */
public abstract class Entity extends AbstractEntity {

    protected int motionX, motionY;
    public final UUID uuid = UUID.randomUUID();

    public Entity() {
    }

    protected Entity(World world, int xPos, int yPos) {
        this.world = world;
        this.xPos = xPos;
        this.yPos = yPos;
        motionX = 0;
        motionY = 0;
    }

    /**
     * @return The position of the entity
     */
    public Vector2d getPosition() {
        return new Vector2d(xPos, yPos);
    }

    /**
     * @return The motion of the entity
     */
    public Vector2d getMotion() {
        return new Vector2d(motionX, motionY);
    }

    /**
     * Sets the motion of the entity
     *
     * @param motion The motion
     */
    public void setMotion(Vector2d motion) {
        setMotion((int) motion.x, (int) motion.y);
    }

    /**
     * Sets the motion of the entity
     *
     * @param xMotion Motion in the X direction
     * @param yMotion Motion in the Y direction
     */
    public void setMotion(int xMotion, int yMotion) {
        motionX = -xMotion;
        motionY = -yMotion;
    }

    /**
     * Called when the entity gets added to the world
     *
     * @param world The world instance the entity was added to
     */
    public void onAddedToWorld(World world) {
    }

    /**
     * Called every update cycle
     */
    @Override
    public void update() {
        xPos += motionX;
        yPos += motionY;
    }

    @Override
    public void writeToDisk(NBTTagCompound tag) {
        super.writeToDisk(tag);
        tag.writeInt("motionX", motionX);
        tag.writeInt("motionY", motionY);
    }

    @Override
    public void loadFromDisk(NBTTagCompound tag) {
        super.loadFromDisk(tag);
        motionX = tag.getInt("motionX");
        motionY = tag.getInt("motionY");
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entity))
            return false;
        Entity other = (Entity) obj;
        return (uuid.equals(other.uuid));
    }
}
