import java.sql.*;
import java.util.HashMap;

public class FridgeDatabaseConnection{
    Connection connection;
    public FridgeDatabaseConnection(Connection connection){
        this.connection = connection;

    }

    public Item getItem(String name) throws SQLException{
        Item tempitem = new Item(name);
        try(PreparedStatement statement = connection.prepareStatement(tempitem.GETITEM)){
            tempitem.prepareGet(statement);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                String iName = rs.getString(1);
                int iNumber = rs.getInt(2);
                return new Item(iName, iNumber);
            }else{
                return new Item("N/A", 0);
            }
        }
    }

    public void deleteItem(String name) throws SQLException{
        Item tempitem = new Item(name);
        System.out.print("Deleting " + name + "...");
        try(PreparedStatement statement = connection.prepareStatement(tempitem.DELETEITEM)){
            tempitem.prepareDelete(statement);
            statement.executeUpdate();
        }
        System.out.println("Done!");
    }

    private void putItem(String name, int number) throws SQLException{
        Item tempitem = new Item(name,number);
        try(PreparedStatement statement = connection.prepareStatement(tempitem.PUTITEM)){
            tempitem.prepareInsert(statement);
            statement.executeUpdate();
        }
    }

    public void setItem(String name, int number) throws SQLException{
        Item tempitem = new Item(name, number);
        try(PreparedStatement setStatement = connection.prepareStatement(tempitem.SETITEM);
        PreparedStatement queryStatement = connection.prepareStatement(tempitem.SETITEMQUERY)){
            tempitem.prepareQuery(queryStatement);
            ResultSet rs = queryStatement.executeQuery();
            rs.next();
            if (rs.getBoolean(1)){
                System.out.println(name + " already exists");
                tempitem.prepareSet(setStatement);
                setStatement.executeUpdate();
            }else{
                System.out.println("Making new entry for " + name);
                putItem(name, number);
            }
        }
    }

    public void printItems() throws SQLException{
        try(Statement statement = connection.createStatement()){
            System.out.println("\nCurrently in Fridge:");
            ResultSet rs = statement.executeQuery("SELECT name, number FROM items");
            while (rs.next()) {
                String s = rs.getString("name");
                int f = rs.getInt("number");
                System.out.printf("%d\t%s%n",f,s);
            }
        }
    }

    public HashMap<String, Integer> getItems() throws SQLException{
        HashMap<String, Integer> itemList = new HashMap<>();
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT name, number FROM items");
            while (rs.next()) {
                String s = rs.getString("name");
                int f = rs.getInt("number");
                itemList.put(s,f);
            }
        }
        return itemList;
    }
}
