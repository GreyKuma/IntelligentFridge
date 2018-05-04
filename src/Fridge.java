import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Fridge {
    public static void main(String[] args){
        final String user = "***REMOVED***";
        final String password = "***REMOVED***";
        final String database = "***REMOVED***";
        System.out.println("Connecting...");
        try(Connection connection = DriverManager.getConnection(database, user, password)) {
            System.out.println("Successfully Connected!");
            FridgeDatabaseConnection FDBC = new FridgeDatabaseConnection(connection);
            FDBC.printItems();
            FDBC.setItem("Konfi", FDBC.getItem("Konfi").getNumber()+3);
            FDBC.setItem("Brot", 3);
            FDBC.printItems();
//            TestStuff.checkInstallation(connection);
//            TestStuff.insertTestItems(connection);
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }
}
