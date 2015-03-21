package tiriantrains;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class Database {
    
    public static final String databaseName = "tirian_trains";
    public static final String username = "root";
    public static final String password = "root";
    
    public static final String driver = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/" + databaseName;
    public static Connection connection;
    
    public static ResultSet executeQuery(String query) throws SQLException {
        if (connection == null) return null;
        return connection.createStatement().executeQuery(query);
    }
    
    public static Object[][] selectQuery(String query) throws SQLException{
        
        ResultSet rs = executeQuery(query);
        ResultSetMetaData meta = rs.getMetaData();
        ArrayList<Object[]> rows = new ArrayList<>();
        
        int columns = meta.getColumnCount();
        
        while (rs.next()) {
            Object[] row = new Object[columns];
            for (int j = 0; j < columns; ++j)
                row[j] = rs.getObject(j + 1);
            rows.add(row);
        }
        
        Object[][] data = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); ++i)
            data[i] = rows.get(i);
        
        return data;
        
    }
    
    // initialize database connection
    static {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Successfully connected to database!");
        }
        catch (ClassNotFoundException|SQLException ex) {
            System.err.println("An error occured: " + ex);
            JOptionPane.showMessageDialog(null, "Could not connect to SQL database!\n" +
                    "URL: " + URL + "\n" + 
                    "Username: " + username + "\n\n" +
                    Arrays.toString(ex.getStackTrace())
                    , "An error occured", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // test
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Object[][] data = selectQuery("SELECT CAST(CONCAT(t.year, '-', t.month, '-', t.day) AS DATE) FROM trip_schedule t;");
        System.out.println(Arrays.deepToString(data));
    }
    
}
