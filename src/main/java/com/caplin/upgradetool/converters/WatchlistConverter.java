package com.caplin.upgradetool.converters;

import com.caplin.upgradetool.generator.ConverterVisitor;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WatchlistConverter implements Converter {
    Pattern keyPattern = Pattern.compile("WATCHLIST(V2|);user=([^;]+);id=([^$]+)");
    private ConverterVisitor visitor;

    @Override
    public void convert(String key, byte[] value) {
        Matcher matcher = keyPattern.matcher(key);
        if (matcher.matches()) {
            List<String> columns = Arrays.asList("WATCHLIST_USER", "WATCHLIST_ID", "WATCHLIST_DATA");
            List<String> values = Arrays.asList(matcher.group(2), matcher.group(3), new String(value));

            visitor.handleData("TF_WATCHLIST_PERSISTENCE", columns, values);
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
