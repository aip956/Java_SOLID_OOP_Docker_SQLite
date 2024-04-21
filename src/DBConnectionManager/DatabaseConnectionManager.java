// DatabaseConnectionManager.java

package DBConnectionManager;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnectionManager {
    private static Connection connection;

    public static Connection getConnection (String dbType, String dbPath) throws SQLException {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    return connection;
                }
            } catch (SQLException e) {
                System.err.println("Error checking conection status: " + e.getMessage());
                connection = null;
            }
        }

        // Establish new connection if existing one closed or null
        switch (dbType) {
            case "SQLite":
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
                } catch (ClassNotFoundException e) {
                    System.err.println("Could not initialize JDBC driver; driver not found");
                    throw new SQLException("Driver not found", e);
                }
                break;             
        }
        return connection;
    }
}

