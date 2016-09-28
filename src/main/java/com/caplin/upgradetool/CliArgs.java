package com.caplin.upgradetool;

import com.beust.jcommander.Parameter;
import com.caplin.upgradetool.converters.HighLowConverter;
import com.caplin.upgradetool.converters.NotificationConverter;
import com.caplin.upgradetool.converters.RecordConverter;
import com.caplin.upgradetool.converters.WatchlistConverter;
import com.caplin.upgradetool.generator.SysOutInsertGenerator;

import java.util.Arrays;
import java.util.List;

public class CliArgs {
    @Parameter(names={"--generator", "-g"}, description = "Class name of the visitor to be used to handle the upgraded data")
    public String generatorClass = SysOutInsertGenerator.class.getName();

    @Parameter(names={"--converters", "-c"}, description = "List of class names to be used for conversion of persisted data")
    public List<String> converterClasses = Arrays.asList(WatchlistConverter.class.getName(), RecordConverter.class.getName(), NotificationConverter.class.getName(), HighLowConverter.class.getName());

    @Parameter(names={"--filedb"}, description = "Whether to read data from a folder or a DB")
    public boolean fromFileDb = false;

    @Parameter(names={"--folder", "-f"}, description = "Folder to read persistence files from")
    public String fileDbFolder;

    @Parameter(names={"--driver"}, description = "JDBC driver name")
    public String jdbcDriver;

    @Parameter(names={"--url"}, description = "JDBC Url to connect to the DB")
    public String jdbcURL;

    @Parameter(names={"--user", "-u"}, description = "Username for JDBC connection")
    public String username;

    @Parameter(names={"--password", "-p"}, description = "Password for JDBC connection")
    public String password;

    @Parameter(names={"--table"}, description = "Name of the database table to read data from")
    public String tableName;

    @Parameter(names={"--columns"}, description = "The key and value column for the table specified in --table. Key column must be named first", arity = 2)
    public List<String> columns;

    @Parameter(names = "--help", help = true, description = "Print this help text")
    public boolean help;
}
