package carsharing;

import carsharing.DAO.CompanyImpl;
import carsharing.Entities.Company;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    private static final Scanner in = new Scanner(System.in);
    private static final CompanyImpl companies = new CompanyImpl();

    public static void mainMenu() {
        System.out.println("""
                         1. Log in as a manager
                         0. Exit
                         """);
        int userChoice = Integer.parseInt(in.next());

        switch (userChoice) {
            case 0 -> exitProgram();
            case 1 -> displayManagerMenu();
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
            System.out.println();

            displayManagerMenu();

        }
        else
            System.out.println("The company list is empty!");
    }


    private static void createCompany() {
        System.out.println("Enter the company name:");
        in.nextLine();
        String companyName = in.nextLine();
        companies.create(new Company(companyName));
        System.out.println("The company was created!");
    }


}