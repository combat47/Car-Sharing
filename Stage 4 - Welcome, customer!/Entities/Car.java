package carsharing.Entities;


public class Car {
    private final int ID;
    private String name;
    private final int companyID;

    public Car(int ID, String name, int companyID) {
        this.ID = ID;
        this.name = name;
        this.companyID = companyID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyID() {
        return companyID;
    }

}