package com.caplin.upgradetool.generator;

import java.util.List;

public class SysOutInsertGenerator implements ConverterVisitor {

    @Override
    public void handleData(String table, List<String> columns, List<String> values) {

        StringBuilder insertBuilder = new StringBuilder("INSERT INTO ")
                .append(table).append("(");

        for (int i = 0; i < columns.size(); i++) {
            if (i > 0 ) insertBuilder.append(", ");
            insertBuilder.append(columns.get(i));
        }

        insertBuilder.append(") VALUES (");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0 ) insertBuilder.append(", ");
            insertBuilder.append("'").append(values.get(i)).append("'");
        }
        insertBuilder.append(");");


        System.out.println(insertBuilder.toString());
    }
}
