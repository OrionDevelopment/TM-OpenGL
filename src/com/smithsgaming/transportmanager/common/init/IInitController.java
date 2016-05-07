package com.smithsgaming.transportmanager.common.init;

import com.smithsgaming.transportmanager.common.IGame;
import com.smithsgaming.transportmanager.util.common.MethodHandlingResult;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public interface IInitController {

    default MethodHandlingResult onInitStarting(IGame game) {
        return MethodHandlingResult.DONOTCARE;
    }

    default MethodHandlingResult onPreInit(IGame game) {
        return MethodHandlingResult.DONOTCARE;
    }

    default MethodHandlingResult onInit(IGame game) {
        return MethodHandlingResult.DONOTCARE;
    }

    default MethodHandlingResult onPostInit(IGame game) {
        return MethodHandlingResult.DONOTCARE;
    }

    default MethodHandlingResult onInitComplete(IGame game) {
        return MethodHandlingResult.DONOTCARE;
    }
}