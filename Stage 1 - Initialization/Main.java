package carsharing;

public class Main {

    public static void main(String[] args) {
        if(args.length > 1 && args[0].equals("-databaseFileName")){
            DatabaseUtil.setDatabaseFileName(args[1]);
            DatabaseUtil.setUpTables();
        }
    }
}