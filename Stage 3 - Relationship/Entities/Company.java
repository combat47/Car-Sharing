package carsharing.Entities;

public class Company {
    private final int ID;
    private String name;

    public Company (String name) {
        this(0, name);
    }

    public Company(int ID, String name) {
        this.ID = ID;
        this.name = name;
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

    @Override
    public String toString() {
        return ID + ". " + name;
    }
}