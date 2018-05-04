import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Item {
    private int number;
    private String name;
    final String GETITEM = "SELECT * FROM items WHERE name = ?";
    final String DELETEITEM = "DELETE FROM items WHERE name = ?";
    final String PUTITEM = "INSERT INTO items VALUES(?, ?)";
    final String SETITEM = "UPDATE items SET number = ? WHERE name = ?";
    final String SETITEMQUERY =
            "SELECT CASE WHEN EXISTS (" +
                "SELECT * " +
                "FROM items " +
                "WHERE name = ?" +
            ")" +
            "THEN CAST(1 AS BIT)" +
            "ELSE CAST(0 AS BIT) END";

    public void prepareGet(PreparedStatement ps) throws SQLException{
        ps.setString(1, name);
    }

    public void prepareDelete(PreparedStatement ps) throws SQLException{
        ps.setString(1, name);
    }

    public void prepareSet(PreparedStatement ps) throws SQLException{
        ps.setInt(1, number);
        ps.setString(2, name);
    }

    public void prepareQuery(PreparedStatement ps) throws SQLException{
        ps.setString(1, name);
    }

    public void prepareInsert(PreparedStatement ps) throws SQLException{
        ps.setString(1, name);
        ps.setInt(2, number);
    }

    public Item(String name, int number){
        this.number = number;
        this.name = name;
    }

    public Item(String name){
        this(name, 1);
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
