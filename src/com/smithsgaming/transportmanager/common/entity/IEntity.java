package com.smithsgaming.transportmanager.common.entity;

import com.smithsgaming.transportmanager.common.world.*;
import com.smithsgaming.transportmanager.util.common.*;
import com.smithsgaming.transportmanager.util.data.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public interface IEntity extends IInstancedWorldObject {

    /**
     * Called when the Entity collides with an object in the world.
     *
     * @param you   An instance of this IEntity that is colliding with other
     * @param other An instance of either a different IEntity or the implementing class that a instance of the
     *              implementing class is colliding with.
     *
     * @return Whether this method handled the interaction between the Entities, or if processing should continue
     * (Default DONOTCARE)
     */
    default MethodHandlingResult onCollisionWith (EntityInstance you, EntityInstance other) {
        return MethodHandlingResult.DONOTCARE;
    }

    /**
     * Called when an EntityInstance is updated.
     *
     * @param instance The instance is that is updated.
     */
    void onInstanceUpdate (EntityInstance instance);

    /**
     * Method to get a default set of a Data for every new EntityInstance.
     *
     * @return A IDataStruct representing the default version of this entity.
     */
    IDataStruct getDefaultData ();

    /**
     * Method called when an EntityInstance of this type is written to Disk.
     *
     * @param instance The instance that is written to disk.
     *
     * @return A IDataStruct representing the EntityInstance fully on disk.
     */
    IDataStruct onWriteInstance (EntityInstance instance);

    /**
     * Method called when an EntityInstance of this type is read from disk.
     *
     * @param instance The instance that is being rebuild from data on the disk.
     * @param struct   The data should be used to rebuild the Instance.
     */
    void onReadInstance (EntityInstance instance, IDataStruct struct);
}
