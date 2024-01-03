package carsharing.Entities;

public class Company {
    private final int ID;
    private final String name;

    public Company (String name) {
        this(0, name);
    }

    public Company(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return ID + ". " + name;
    }
}