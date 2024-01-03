package carsharing;

import carsharing.DAO.CarImpl;
import carsharing.DAO.CompanyImpl;
import carsharing.DAO.CustomerImpl;
import carsharing.Entities.Car;
import carsharing.Entities.Company;
import carsharing.Entities.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Scanner in = new Scanner(System.in);
    private static final CompanyImpl companies = new CompanyImpl();
    private static final CarImpl cars = new CarImpl();
    private static final CustomerImpl customers = new CustomerImpl();

    public static void mainMenu() {
        System.out.println("""
                         1. Log in as a manager
                         2. Log in as a customer
                         3. Create a customer
                         0. Exit
                         """);
        int userChoice = Integer.parseInt(in.next());

        switch (userChoice) {
            case 0 -> exitProgram();
            case 1 -> displayManagerMenu();
            case 2 -> logInAsCustomer();
            case 3 -> createCustomer();
        }
        mainMenu();
    }
    private static void exitProgram() {
        try {
            Database.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
    private static void displayManagerMenu() {
        System.out.println("""
                1. Company list
                2. Create a company
                0. Back
                """);
        int userChoice = Integer.parseInt(in.next());

        switch (userChoice) {
            case 0 -> mainMenu();
            case 1 -> companiesMenu();
            case 2 -> createCompany();
        }
        displayManagerMenu();
    }

    private static void companiesMenu() {
        if (companies.count() > 0) {
            System.out.println("Choose the company: ");
            companies.printCompanies();
            System.out.println("0. Back");
            int companyID = Integer.parseInt(in.next());
            if (companyID > 0) {
                System.out.println("'" + companies.getCompanyName(companyID) + "' company");
                displayCompanyMenu(companyID);
            }
            else {
                displayManagerMenu();
            }
        }
        else
            System.out.println("The company list is empty!");
    }

    private static void logInAsCustomer() {
        if (customers.count() == 0) {
            System.out.println("The customer list is empty!");
            return;
        }
        System.out.println("Choose a customer: ");
        customers.printCustomers();
        System.out.println("0. Back");
        int custID = Integer.parseInt(in.next());
        if (custID > 0)
            displayCustomerMenu(custID);
        else
            mainMenu();
    }
    private static void createCustomer() {
        System.out.println("Enter the customer name: ");
        in.nextLine();
        String customerName = in.nextLine();
        customers.create(new Customer(customerName)); // IMPLEMENT CREATE
        System.out.println("The customer was added!");
    }


    private static void createCompany() {
        System.out.println("Enter the company name:");
        in.nextLine();
        String companyName = in.nextLine();
        companies.create(new Company(companyName));
        System.out.println("The company was created!");
    }

    private static void displayCompanyMenu(int companyID) {
        System.out.println("""
                1. Car list
                2. Create a car
                0. Back
                """);
        int userChoice = Integer.parseInt(in.next());
        switch (userChoice) {
            case 0 -> displayManagerMenu();
            case 1 -> cars.printCars(companyID);
            case 2 -> createCar(companyID);
        }
        displayCompanyMenu(companyID);
    }

    private static void createCar(int companyID) {
        System.out.println("Enter the car name:");
        in.nextLine();
        String carName = in.nextLine();
        cars.create(new Car(0, carName, companyID));
        System.out.println("The car was added!");
    }


    private static void displayCustomerMenu(int customerID) {
        System.out.println("""
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back
                """);
        int userChoice = Integer.parseInt(in.next());
        switch (userChoice) {
            case 0 -> mainMenu();
            case 1 -> rentCar(customerID);
            case 2 -> returnRentedCar(customerID);
            case 3 -> printRentedCarDetail(customerID);
        }
        displayCustomerMenu(customerID);
    }

    private static void rentCar(int customerID) {
        if (companies.count() == 0) {
            System.out.println("The company list is empty!");
            displayCustomerMenu(customerID);
        }
        if (customers.getCarID(customerID) != 0) {
            System.out.println("You've already rented a car!");
            displayCustomerMenu(customerID);
        }
        System.out.println("Choose a company: ");
        companies.printCompanies();
        System.out.println("0. Back");
        int companyID = Integer.parseInt(in.next());
        if (companyID == 0)
            mainMenu();
        List<Car> availableCars = cars.getAvailableCars(companyID);
        if (availableCars.size() == 0) {
            System.out.println("No available cars in the " + companies.getCompanyName(companyID) + " company");
            rentCar(customerID);
        }
        System.out.println("Choose a car: ");
        for (int i = 1; i <= availableCars.size(); i++) {
            System.out.println(i+ ". " + availableCars.get(i-1).getName());
        }
        System.out.println("Back");
        int userChoice = Integer.parseInt(in.next());
        if (userChoice == 0)
            displayCustomerMenu(customerID);
        customers.rentCar(customerID, availableCars.get(userChoice-1).getID());
        System.out.println("You rented " + "'" + availableCars.get(userChoice-1).getName() + "'");
        displayCustomerMenu(customerID);
    }

    private static void returnRentedCar(int customerID) {
        customers.returnCar(customerID);
    }

    private static void printRentedCarDetail(int customerID) {
        int carID = customers.getCarID(customerID);
        if (carID == 0)
            System.out.println("You didn't rent a car!");
        else {
            Car rentedCar = cars.getCar(carID);
            System.out.println("Your rented car:");
            System.out.println(rentedCar.getName());
            System.out.println("Company:");
            System.out.println(companies.getCompanyName(rentedCar.getCompanyID()));
        }
    }

}