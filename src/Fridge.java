import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Fridge {
    public static void main(String[] args){
        Properties properties = readConfigs.getProperties("config\\fridge.xml");
        final String user = properties.getProperty("DBuser");
        final String password = properties.getProperty("DBpassword");
        final String database = properties.getProperty("DBurl");
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
