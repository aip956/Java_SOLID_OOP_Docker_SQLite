/* Data Access Object; Handles all database interactions related to GameData,
    abstracting away the specifics of data persistence.
*/
import java.sql.DriverManager;
import java.sql.SQLException;

public interface GameDataDAO {
    void saveGameData(GameData gameData) throws SQLException;
    List<GameData> getGamesByPlayer(String playerName) throws SQLException;
    
}
public class SQLiteGameDataDAO implements GameDataDAO {
    private Connection connection;

    public SQLiteGameDataDAO(String dbPath) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    @Override
    public void saveGameData(GameData gameData) throws SQLException {
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
            statement.setTimestamp(4, gameData.getTimestamp());
            statement.setString(5, gameData.getSecretCode());
            // Convert the list of guesses to a single string
            String guessesString = (gameData.getGuesses() != null) ? String.join(",", gameData.getGuesses()) : "";
            statement.setString(6, guessesString);
            statement.executeUpdate(); // Execute SQL query
        }
    }
}