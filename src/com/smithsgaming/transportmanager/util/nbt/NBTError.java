package com.smithsgaming.transportmanager.util.nbt;

/**
 * Created by Tim on 27/03/2016.
 */
public class NBTError extends RuntimeException {

    public NBTError () {
        super();
    }

    public NBTError (String s) {
        super(s);
    }

    public NBTError (String message, Throwable cause) {
        super(message, cause);
    }

    public NBTError (Throwable cause) {
        super(cause);
    }

}
