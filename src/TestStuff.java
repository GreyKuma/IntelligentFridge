import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestStuff {
    public static void checkInstallation(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rset = stmt.executeQuery("SELECT CURRENT_USER");
            while (rset.next()) {
                System.out.println(rset.getString(1));
            }
            rset.close();
        }

        System.out.println("Your JDBC installation is correct.");
    }

    public static void insertTestItems(Connection connection) throws SQLException{
        try (Statement stmt = connection.createStatement()){
            System.out.println("Inhalt von items loeschen");

            stmt.execute("DELETE FROM items");

            System.out.println("Inhalt von items abfuellen");
            stmt.executeUpdate("INSERT INTO items "
                    + "VALUES('Rüebli', 1)");
            stmt.executeUpdate("INSERT INTO items "
                    + "VALUES('Wurst', 1)");
            stmt.executeUpdate("INSERT INTO items "
                    + "VALUES('Yoghurt', 1)");
            stmt.executeUpdate("INSERT INTO items "
                    + "VALUES('Kuttel', 1)");
            stmt.executeUpdate("INSERT INTO items "
                    + "VALUES('Spargel', 1)");

            System.out.println("\nCurrently in Fridge:");
            ResultSet rs = stmt.executeQuery("SELECT name, number FROM items");
            while (rs.next()) {
                String s = rs.getString("name");
                int f = rs.getInt("number");
                System.out.printf("%d\t%s%n",f,s);
            }
        }

    }
}
