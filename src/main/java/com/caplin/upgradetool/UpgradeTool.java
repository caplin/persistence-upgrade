package com.caplin.upgradetool;

import com.beust.jcommander.JCommander;
import com.caplin.upgradetool.converters.*;
import com.caplin.upgradetool.generator.ConverterVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UpgradeTool {

    private final List<Converter> converters = new ArrayList<>();
    private FilenameDecoder filenameDecoder = new FilenameDecoder();


    public UpgradeTool(ConverterVisitor visitor, List<Converter> converters) throws InstantiationException, IllegalAccessException {
        for (Converter converter : converters) {
            converter.accept(visitor);
            this.converters.add(converter);
        }
    }

    public void convert(String key, byte[] value) {
        String decodedKey = filenameDecoder.decode(key);
        Optional<Converter> applicableConverter = converters.stream().filter(converter -> converter.canConvert(decodedKey)).findFirst();
        applicableConverter.orElse(new NullConverter()).convert(decodedKey, value);
    }

    public void convertFromFolder(Path persistenceDirectory) throws IOException {
        List<Path> filesList = Files.walk(persistenceDirectory).collect(Collectors.toList());
        for (Path file : filesList) {
            if (!Files.isDirectory(file) ) {
                convert(file.getFileName().toString(), Files.readAllBytes(file));
            } else {
                System.err.println("Not converting <" + file + "> as it's a directory");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        CliArgs arguments = new CliArgs();
        JCommander jCommander = new JCommander(arguments, args);

        if (!arguments.help) {

            ConverterVisitor visitor = (ConverterVisitor) Class.forName(arguments.generatorClass).newInstance();

            List<Converter> converters = arguments.converterClasses.stream().map(className -> {
                try {
                    return (Converter)Class.forName(className).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                    return null; // ... java ...
                }
            }).collect(Collectors.toList());

            UpgradeTool upgradeTool = new UpgradeTool(visitor, converters);

            if (arguments.fromFileDb) {
                upgradeTool.convertFromFolder(Paths.get(arguments.fileDbFolder));
            } else {
                Class.forName(arguments.jdbcDriver);

                upgradeTool.convertFromDatabase(arguments.jdbcURL, arguments.username, arguments.password, arguments.tableName, arguments.columns);
            }
        } else {
            jCommander.usage();
        }
    }

    private void convertFromDatabase(String jdbcURL, String username, String password, String tableName, List<String> columns) throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);


        while(resultSet.next()) {
            String key = resultSet.getString(columns.get(0));
            byte[] value = resultSet.getBytes(columns.get(1));

            if (key == null || value == null) {
                System.err.println("Unable to retrieve data for columns " + columns.toString() + " from ResultSet " + resultSet);
            } else {
                convert(key, value);
            }
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}
