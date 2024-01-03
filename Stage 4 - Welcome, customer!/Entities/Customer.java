package carsharing.Entities;


public class Customer {
    private final int ID;
    private final String name;
    private final int rentedCarID;


    public Customer (String name) {
        this(0, name,0);
    }
    public Customer(int ID, String name, int rentedCarID) {
        this.ID = ID;
        this.name = name;
        this.rentedCarID = rentedCarID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return ID + ". " + name;
    }

    public int getRentedCarID() {
        return rentedCarID;
    }
}