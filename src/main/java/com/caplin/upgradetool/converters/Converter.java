package com.caplin.upgradetool.converters;

import com.caplin.upgradetool.generator.ConverterVisitor;

public interface Converter {
    boolean canConvert(String key);
    void convert(String key, byte[] value);

    void accept(ConverterVisitor visitor);
}
