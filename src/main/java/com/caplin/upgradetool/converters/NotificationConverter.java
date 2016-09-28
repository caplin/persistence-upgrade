package com.caplin.upgradetool.converters;

import com.caplin.upgradetool.generator.ConverterVisitor;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationConverter implements Converter {
    Pattern keyPattern = Pattern.compile("NOTIFICATION;user=([^;]+);id=([^$]+)$");
    private ConverterVisitor visitor;

    @Override
    public void convert(String key, byte[] value) {
        Matcher matcher = keyPattern.matcher(key);
        if (matcher.matches()) {
            List<String> columns = Arrays.asList("NOTIFICATION_USER", "NOTIFICATION_ID", "NOTIFICATION_DATA");
            List<String> values = Arrays.asList(matcher.group(1), matcher.group(2), new String(value));

            visitor.handleData("TF_NOTIFICATION_PERSISTENCE", columns, values);
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
