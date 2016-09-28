package com.caplin.upgradetool.generator;

import java.util.List;

public interface ConverterVisitor {
    void handleData(String table, List<String> columns, List<String> values);
}
