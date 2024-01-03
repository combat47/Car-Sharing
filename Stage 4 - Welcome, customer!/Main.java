package carsharing;


public class Main {

    public static void main(String[] args) {
        try {
            Database.createConnection();
            Menu.mainMenu();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}