# Persistence upgrade tool

A tool that helps migrating data persisted with a Transformer 6.2 to a Transformer 7.0. It supports reading from either a file DB or a java DB (via jdbc) and generates insert statements for the different tables used in version 7. 

## Usage:
 - Clone the Repo
 - run `gradle jar` which produces an executable jar file
 - run the program by running `java -jar build/libs/upgradeTool-0.1.jar [options]`
 
## Examples:
The following examples showcase how to run the tool for the different modes (file DB vs java DB)
### FileDb
To run the upgrade tool on a persistence folder run 
```bash
java -jar build/libs/upgradeTool-0.1.jar --filedb --folder <path_to_persistence_folder>
```

### JavaDb
To run the upgrade tool with jdbc mode, a JDBC Driver needs to be supplied and made available on the classpath in addition to specifying the correct arguments
```bash
java -classpath <path_to_jdbc_driver> -jar build/libs/upgradeTool-0.1.jar --driver <driverName> --url <jdbc url> --user <jdbc user> --password <jdbc password> --table <table to upgrade data from> --colums <pers_key column name> <pers_val column name>
```

## Notes
- The command line option `--converters` can be used to override the default classes used in case other modules than the ones that have converters supplied for them need their data upgraded too. This option is a list of class names. The classes must mimplement the `Converter` interface
- The command line option `--generator` can be used to override the default generator. The default generator prints INSERT statements for the upgraded data to stdout. It might be desirable to directly insert data into the new database tables, in which case a new generator can be supplied. The class must implement the `ConverterVisitor` interface
- This tool is unsupported and provided as is
- You're free to modify the code as you see fit

