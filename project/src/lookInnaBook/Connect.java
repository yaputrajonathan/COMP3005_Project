package lookInnaBook;
import java.sql.*;

public class Connect {

    static Connection conn;



    public static Connection ConnectDB() {
        final String url = "jdbc:postgresql://localhost/COMP3005_Project";
        final String user = "postgres";
        final String password = "SERAphimon10!";

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PGSQL");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
