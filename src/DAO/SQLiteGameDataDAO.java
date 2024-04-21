/* SQLiteGameDataDAO.java
Data Access Object; Handles all database interactions related to GameData,
    abstracting away the specifics of data persistence.
*/
package DAO;
import Models.GameData;
import DBConnectionManager.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
// import java.sql.ResultSet;
import java.sql.SQLException;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Collections;

public class SQLiteGameDataDAO implements GameDataDAO {
    private final String dbPath;
    private Connection connection;

    public SQLiteGameDataDAO(String dbPath) throws SQLException {
        this.dbPath = dbPath; // Savd db as a class member
        this.connection = DatabaseConnectionManager.getConnection("SQLite", this.dbPath);
    }

    @Override
    public void saveGameData(GameData gameData) throws SQLException {
        String guessesString = String.join(",", gameData.getGuesses()); // Log this to check correctness

        String sql = "INSERT INTO game_data (" +
            "player_name, " +
            "rounds_to_solve, " +
            "solved, " +
            "timestamp, " +
            "secret_code, " +
            "guesses) " + 
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getConnection("SQLite", dbPath);
            PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, gameData.getPlayerName());
                statement.setInt(2, gameData.getRoundsToSolve());
                statement.setBoolean(3, gameData.isSolved());
                statement.setString(4, gameData.getFormattedDate());
                statement.setString(5, gameData.getSecretCode());
                statement.setString(6, guessesString);
                statement.executeUpdate(); // Execute SQL query
        } catch (SQLException e) {
            System.err.println("Error occured while saving game data: " + e.getMessage());
            throw e;
        }
    }
}
