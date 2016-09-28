package com.caplin.upgradetool.converters;

import com.caplin.upgradetool.generator.ConverterVisitor;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class HighLowConverter implements Converter {
    Pattern keyPattern = Pattern.compile("(.+)/(LowPrice|LowTime|OpenPrice|HighPrice|HighTime)");
    private ConverterVisitor visitor;

    @Override
    public void convert(String key, byte[] value) {
        List<String> columns = Arrays.asList("JTM_KEY", "JTM_VALUE");
        List<String> values = Arrays.asList(key, new String(value));

        visitor.handleData("TF_LEGACY_PERSISTENCE", columns, values);
    }

    @Override
    public boolean canConvert(String key) {
        return keyPattern.matcher(key).matches();
    }

    @Override
    public void accept(ConverterVisitor visitor) {
        this.visitor = visitor;
    }
}
