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
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class SQLiteGameDataDAO implements GameDataDAO {
    private Connection connection;

    public SQLiteGameDataDAO(String dbPath) throws SQLException {
        this.connection = DatabaseConnectionManager.getConnection("SQLite", dbPath);
    }

    @Override
    public void saveGameData(GameData gameData) throws SQLException {
        String guessesString = String.join(",", gameData.getGuesses()); // Log this to check correctness
        // System.out.println("25Saving to DB, guesses: " + guessesString);

        String sql = "INSERT INTO game_data (" +
            "player_name, " +
            "rounds_to_solve, " +
            "solved, " +
            "timestamp, " +
            "secret_code, " +
            "guesses) " + 
            "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, gameData.getPlayerName());
            statement.setInt(2, gameData.getRoundsToSolve());
            statement.setBoolean(3, gameData.isSolved());
            statement.setString(4, gameData.getFormattedDate());
            statement.setString(5, gameData.getSecretCode());
            statement.setString(6, guessesString);
            statement.executeUpdate(); // Execute SQL query
        }
    }
    
    @Override
    public List<GameData> getGamesByPlayer(String playerName) throws SQLException {
        List<GameData> games = new ArrayList<>();
        String sql = "SELECT * FROM game_data WHERE player_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int gameID = resultSet.getInt("game_id");
                int roundsToSolve = resultSet.getInt("rounds_to_solve");
                boolean solved = resultSet.getBoolean("solved");
                String formattedDate = resultSet.getString("timestamp");
                String secretCode = resultSet.getString("secret_code");
                String guessesString = resultSet.getString("guesses");
                List<String> guesses = new ArrayList<>();
                if (guessesString != null) {
                    String[] guessArray = guessesString.split(",");
                    Collections.addAll(guesses, guessArray);
                }
                GameData gameData = new GameData(gameID, playerName, roundsToSolve, solved, formattedDate, secretCode, guesses);
                games.add(gameData);
            }
        }
        return games;
    }
    
    @Override
    public List<GameData> getGamesBySolved(boolean solved) throws SQLException {
        List<GameData> games = new ArrayList<>();
        String sql = "SELECT * FROM game_data WHERE solved = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, solved);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int gameID = resultSet.getInt("game_id");
                String playerName = resultSet.getString("player_name");
                int roundsToSolve = resultSet.getInt("rounds_to_solve");
                String formattedDate = resultSet.getString("timestamp");
                String secretCode = resultSet.getString("secret_code");
                String guessesString = resultSet.getString("guesses");
                List<String> guesses = new ArrayList<>();
                if (guessesString != null) {
                    String[] guessArray = guessesString.split(",");
                    Collections.addAll(guesses, guessArray);
                }
                GameData gameData = new GameData(gameID, playerName, roundsToSolve, solved, formattedDate, secretCode, guesses);
                games.add(gameData);
            }
        }
        return games;
    }
}
