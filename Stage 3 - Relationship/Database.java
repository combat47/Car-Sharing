package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static String dbName="carsharing";
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/" + dbName;
    public static Statement stmt;
    public static Connection connection;
    public static void createConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            stmt = connection.createStatement();
            connection.setAutoCommit(true);
//            resetDatabase();
            createDatabase();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void closeConnection() throws SQLException {
        stmt.close();
        connection.close();
    }

    private static void createDatabase() {
        createCompanyTable();
        createCarTable();
        createCustomerTable();
    }

    private static void createCompanyTable() {
        try {
            String createCompanyTable = """
                                      CREATE TABLE IF NOT EXISTS COMPANY (
                                      ID INT AUTO_INCREMENT PRIMARY KEY,
                                      NAME VARCHAR(255) NOT NULL UNIQUE
                                      );
                                      """;
            Database.stmt.execute(createCompanyTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createCarTable() {
        try {
            String createCarTable = """
                                      CREATE TABLE IF NOT EXISTS CAR (
                                      ID INT AUTO_INCREMENT PRIMARY KEY,
                                      NAME VARCHAR(255) NOT NULL UNIQUE,
                                      COMPANY_ID INT NOT NULL,
                                      CONSTRAINT fk_companyID FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)
                                      );
                                      """;

            Database.stmt.execute(createCarTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createCustomerTable() {
        try {
            String createCustomerTable = """
                                         CREATE TABLE IF NOT EXISTS CUSTOMER (
                                         ID INT AUTO_INCREMENT PRIMARY KEY,
                                         NAME VARCHAR(255) NOT NULL UNIQUE,
                                         RENTED_CAR_ID INT,
                                         CONSTRAINT fk_rentedCarID FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)
                                         );
                                         """;
            Database.stmt.execute(createCustomerTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void resetDatabase() {
        String drop = "DROP TABLE IF EXISTS COMPANY";
        String drop2 = "DROP TABLE IF EXISTS CAR";
        String drop3 = "DROP TABLE IF EXISTS CUSTOMER";
        try {
            stmt.executeUpdate(drop3);
            stmt.executeUpdate(drop2);
            stmt.executeUpdate(drop);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}