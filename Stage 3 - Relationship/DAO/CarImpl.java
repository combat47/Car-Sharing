package carsharing.DAO;

import carsharing.Database;
import carsharing.Entities.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarImpl implements AbstractDao<Car> {

    private List<Car> cars;

    public CarImpl() {
        this.cars = new ArrayList<>();
    }


    public List<Car> getAllByCompanyID(int companyID) {
        try {
            String printCarsQuery = """
                                         SELECT *
                                         FROM CAR
                                         WHERE COMPANY_ID = (?)
                                         """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(printCarsQuery);
            prepdStmt.setInt(1, companyID);
            ResultSet rs = prepdStmt.executeQuery();
            cars.clear();
            while (rs.next())
                cars.add(new Car(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("COMPANY_ID")));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public List<Car> getAll() {
        return null;
    }

    @Override
    public void create(Car car) {
        try {
            String query = """
                           INSERT INTO CAR (NAME, COMPANY_ID)
                           VALUES (?, ?);
                           """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(query);
            prepdStmt.setString(1, car.getName());
            prepdStmt.setInt(2, car.getCompanyID());
            prepdStmt.executeUpdate();
            prepdStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Car car) {

    }

    @Override
    public void delete(int ID) {

    }

    public void printCars(int companyID) {
        cars = getAllByCompanyID(companyID);
        if (cars.size()==0) {
            System.out.println("The car list is empty!");
            return;
        }
        for (int i=1; i<=cars.size(); i++)
            System.out.println(i+". " + cars.get(i-1).getName());
        System.out.println();
    }

    public List<Car> getAvailableCars(int companyID) {
        List<Car> availableCars = new ArrayList<>();
        try {
            String query = """
                           SELECT CAR.ID, CAR.NAME, CAR.COMPANY_ID
                           FROM CAR LEFT JOIN CUSTOMER ON CAR.ID = CUSTOMER.RENTED_CAR_ID
                           WHERE COMPANY_ID = (?) AND CUSTOMER.ID IS NULL
                           """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(query);
            prepdStmt.setInt(1, companyID);
            ResultSet rs = prepdStmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                availableCars.add(new Car(id, name, companyID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableCars;
    }

    public Car getCar(int carID) {
        Car car = null;
        try {
            String query = """
                           SELECT *
                           FROM CAR
                           WHERE ID = (?)
                           """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(query);
            prepdStmt.setInt(1, carID);
            ResultSet rs = prepdStmt.executeQuery();
            rs.next();
            int ID = rs.getInt("ID");
            String name = rs.getString("NAME");
            int companyID = rs.getInt("COMPANY_ID");
            car = new Car(ID, name, companyID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }
}