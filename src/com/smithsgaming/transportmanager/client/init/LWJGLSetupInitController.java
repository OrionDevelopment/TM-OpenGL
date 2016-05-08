package com.smithsgaming.transportmanager.client.init;

import com.smithsgaming.transportmanager.common.*;
import com.smithsgaming.transportmanager.common.init.*;
import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class LWJGLSetupInitController implements IInitController {

    @Override
    public MethodHandlingResult onInitStarting (IGame game) {
        System.getProperties().setProperty("java.library.path", "target/natives");
        return MethodHandlingResult.HANDLED;
    }
}
