package carsharing.DAO;

import carsharing.Database;
import carsharing.Entities.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyImpl implements AbstractDao<Company> {

    private List<Company> companies;

    public CompanyImpl() {
        this.companies = new ArrayList<>();
    }

    @Override
    public List<Company> getAll() {
        try {
            String printCompaniesQuery = """
                                         SELECT *
                                         FROM COMPANY
                                         ORDER BY ID;
                                         """;
            ResultSet rs = Database.stmt.executeQuery(printCompaniesQuery);
            companies.clear();
            while (rs.next())
                companies.add(new Company(rs.getInt("ID"), rs.getString("NAME")));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void create(Company company) {
        try {
            String query = """
                           INSERT INTO COMPANY (NAME)
                           VALUES (?);
                           """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(query);
            prepdStmt.setString(1, company.getName());
            prepdStmt.executeUpdate();
            prepdStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Company entity) {
    }

    @Override
    public void delete(int id) {
    }

    public void printCompanies() {
        for (Company company : companies)
            System.out.println(company.toString());
    }

    public int count () {
        companies = getAll();
        return companies.size();
    }

    public String getCompanyName(int companyID) {
        try {
            String query = """
                           SELECT *
                           FROM COMPANY 
                           WHERE ID = (?);
                           """;
            PreparedStatement prepdStmt = Database.connection.prepareStatement(query);
            prepdStmt.setInt(1, companyID);
            ResultSet rs = prepdStmt.executeQuery();
            rs.next();
            return rs.getString("NAME");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}