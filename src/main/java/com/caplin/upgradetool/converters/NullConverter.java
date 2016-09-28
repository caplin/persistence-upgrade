package com.caplin.upgradetool.converters;

import com.caplin.upgradetool.generator.ConverterVisitor;

public class NullConverter implements Converter {
    @Override
    public void convert(String key, byte[] value) {
        System.err.println("No converter found for key " + key);
    }

    @Override
    public boolean canConvert(String key) {
        return false;
    }

    @Override
    public void accept(ConverterVisitor visitor) {

    }
}
