package carsharing.DAO;

import carsharing.Database;
import carsharing.Entities.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerImpl implements AbstractDao<Customer> {

    private List<Customer> customers;

    public CustomerImpl() {
        this.customers = new ArrayList<>();
    }

    @Override
    public List<Customer> getAll() {
        try {
            String printCompaniesQuery = """
                                         SELECT *
                                         FROM CUSTOMER
                                         ORDER BY ID;
                                         """;
            ResultSet rs = Database.stmt.executeQuery(printCompaniesQuery);
            customers.clear();
            while (rs.next())
                customers.add(new Customer(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("RENTED_CAR_ID")));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void create(Customer customer) {
        try {
            String query = """
                           INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID)
                           VALUES (?, NULL);
                           """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(query);
            prepdStmt.setString(1, customer.getName());
            prepdStmt.executeUpdate();
            prepdStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(int ID) {

    }

    public int count() {
        customers = getAll();
        return customers.size();
    }

    public void printCustomers() {
        for (Customer customer : customers)
            System.out.println(customer.toString());
    }
    public int getCarID(int customerID) {
        int carID = 0;
        try {
            String query = """
                           SELECT *
                           FROM CUSTOMER
                           WHERE ID = (?)
                           """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(query);
            prepdStmt.setInt(1, customerID);
            ResultSet rs = prepdStmt.executeQuery();
            rs.next();
            carID = rs.getInt("RENTED_CAR_ID");
            prepdStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return carID;
    }


    public void returnCar(int customerID) {
        if (getCarID(customerID)==0) {
            System.out.println("You didn't rent a car!");
            return;
        }
        try {
            String returnCar = """
                               UPDATE CUSTOMER
                               SET RENTED_CAR_ID = NULL
                               WHERE ID = (?)
                               """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(returnCar);
            prepdStmt.setInt(1, customerID);
            prepdStmt.executeUpdate();
            prepdStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("You've returned a rented car!");
    }

    public void rentCar(int customerID, int car_id) {
        try {
            String returnCar = """
                               UPDATE CUSTOMER
                               SET RENTED_CAR_ID = (?)
                               WHERE ID = (?)
                               """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(returnCar);
            prepdStmt.setInt(1, car_id);
            prepdStmt.setInt(2, customerID);
            prepdStmt.executeUpdate();
            prepdStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

