 
package gam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableExample {
    public static void main(String[] args) {
        // Database connection parameters
        String url = "jdbc:sqlite:Gam.db";

        // SQL statement to create a table
        String sql = "INSERT INTO  gam_clints ( price, NB)";

        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish database connection
            Connection conn = DriverManager.getConnection(url);

            // Create a statement object
            Statement stmt = conn.createStatement();

            // Execute the SQL statement to create the table
            stmt.execute(sql);

            System.out.println("Table created successfully");

            // Close the statement and connection
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

 
 
