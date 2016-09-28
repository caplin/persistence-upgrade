package com.caplin.upgradetool.converters;

import com.caplin.upgradetool.generator.ConverterVisitor;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chsitter on 27/09/2016.
 */
public class RecordConverter implements Converter {
    Pattern keyPattern = Pattern.compile("RECORD;uid=([^;]+);id=([^$]+)$");
    private ConverterVisitor visitor;

    @Override
    public void convert(String key, byte[] value) {
        Matcher matcher = keyPattern.matcher(key);
        if (matcher.matches()) {
            List<String> columns = Arrays.asList("PERSISTENCE_USER", "PERSISTENCE_ID", "PERSISTENCE_DATA");
            List<String> values = Arrays.asList(matcher.group(1), matcher.group(2), new String(value));
            visitor.handleData("TF_RECORD_PERSISTENCE", columns, values);
        } else {
            //Cannot happen
        }
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
