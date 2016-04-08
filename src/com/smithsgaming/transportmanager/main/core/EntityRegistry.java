package com.smithsgaming.transportmanager.main.core;

import com.smithsgaming.transportmanager.main.entity.Entity;
import com.smithsgaming.transportmanager.util.exception.EntityRegistrationException;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tim on 07/04/2016.
 */
public class EntityRegistry {

    private static EntityRegistry instance = new EntityRegistry();

    private HashMap<String, Class<? extends Entity>> entityClassMap = new HashMap<>();

    public static boolean containsEntity(Class<? extends Entity> entity) {
        return instance.entityClassMap.containsValue(entity);
    }

    public static void registerEntity(String id, Class<? extends Entity> entity) {
        if (instance.entityClassMap.containsKey(id) || instance.entityClassMap.containsValue(entity)) {
            throw new EntityRegistrationException("Registry already contains the specified Key or Entity");
        }
        boolean hasDefaultConstructor = false;
        for (Constructor<?> constructor : entity.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                hasDefaultConstructor = true;
                break;
            }
        }
        if (!hasDefaultConstructor) {
            throw new EntityRegistrationException("Entity class must have a default (0 arg) constructor for saving and loading");
        }
        try {
            instance.entityClassMap.put(id, entity);
        } catch (Exception e) {
            throw new EntityRegistrationException("Could not register the specified Entity to the ID: " + id + "\n" + e.getMessage());
        }
    }

    public static Class<? extends Entity> getEntityClass(String id) {
        if (instance.entityClassMap.containsKey(id)) {
            return instance.entityClassMap.get(id);
        } else {
            return null;
        }
    }

    public static Entity getEntity(String id) {
        if (instance.entityClassMap.containsKey(id)) {
            try {
                return instance.entityClassMap.get(id).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getEntityID(Class<? extends Entity> entity) {
        if (instance.entityClassMap.containsValue(entity)) {
            for (Map.Entry<String, Class<? extends Entity>> e : instance.entityClassMap.entrySet()) {
                if (e.getValue() == entity) {
                    return e.getKey();
                }
            }
        }
        return "entity.null";
    }

}
