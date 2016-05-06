package com.smithsgaming.transportmanager.common.init;

import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public interface IInitController {

    default MethodHandlingResult onInitStarting () {
        return MethodHandlingResult.DONOTCARE;
    }

    default MethodHandlingResult onPreInit () {
        return MethodHandlingResult.DONOTCARE;
    }

    default MethodHandlingResult onInit () {
        return MethodHandlingResult.DONOTCARE;
    }

    default MethodHandlingResult onPostInit () {
        return MethodHandlingResult.DONOTCARE;
    }

    default MethodHandlingResult onInitComplete () {
        return MethodHandlingResult.DONOTCARE;
    }
}