package com.smithsgaming.transportmanager.util.data;

/**
 * @Author Marc (Created on: 25.04.2016)
 */
public class PrimaryTypeDataStruct implements IDataStruct {

    String key;
    String value;

    public PrimaryTypeDataStruct (String key, String value) {
        this.key = key;
        this.value = value;
    }

    public PrimaryTypeDataStruct (String key, Integer value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public PrimaryTypeDataStruct (String key, Float value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public PrimaryTypeDataStruct (String key, Double value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public PrimaryTypeDataStruct (String key, Long value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public PrimaryTypeDataStruct (String key, Short value) {
        this.key = key;
        this.value = String.valueOf(value);
    }


    @Override
    public String getKey () {
        return key;
    }

    @Override
    public IDataStruct getValue () {
        return this;
    }

    public String getValueAsString () {
        return value;
    }

    public Float getValueAsFloat () {
        return Float.valueOf(value);
    }

    public Double getValueAsDouble () {
        return Double.valueOf(value);
    }

    public Integer getValueAsInt () {
        return Integer.valueOf(value);
    }

    public Short getValueAsShort () {
        return Short.valueOf(value);
    }

    public Long getValueAsLong () {
        return Long.valueOf(value);
    }
}
