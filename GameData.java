import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class GameData {
    private int gameID;
    private String playerName;
    private int roundsToSolve;
    private boolean solved;
    private Timestamp timestamp;
    private String secretCode;
    private List<String> guesses;

}

public interface GameDataDAO {
    void saveGameData(GameData gameData) throws SQLException; // Save a game's data to db
    List<GameData> getGamesByPlayer(String playerName) throws SQLException; // Retrieves games by a specific player
    List<GameData> getGamesSolvedWithinRounds(int rounds) throws SQLException; // Retrieves games solved within a spec. num of rounds
    List<GameData> getGamesBySolved(boolean solved) throws SQLException; // Retrieves games based on solved or not
    List<GameData> getGamesByTime(Timestamp timestamp) throws SQLException; // Retrieves games played at a spec timestamp
    List<GameData> getGamesBySecret(String secretCode) throws SQLException; // Retrieves games with a spec secret code
    List<GameData> getGamesByGuesses(List<String> guesses) throws SQLException; // Retrieves games based on the list of guesses
}

public class SQLiteGameDataDAO implements GameDataDAO {
    private Connection connection;
    
    // Constructor to init the connection
    public SQLiteGameDataDAO(String dbPath) throws SQLException {
        // Connect to the SQLite db
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }
}

@Override
public void saveGameData(GameData gameData) throws SQLException {
    String sql = "INSERT INTO game_data (
        player_name, 
        rounds_to_solve,
        solved,
        timestamp,
        secret_code,
        guesses) " + 
        "VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, gameData.getPlayerName());
        statement.setInt(2, gameData.getRoundsToSolve());
        statement.setBoolean(3, gameData.isSolved());
        statement.setTimestamp(4, gameData.getTimestame());
        statement.setString(5, gameData.getSecretCode());
        // Convert the list of guesses to a single string
        statement.setString(6, String.join(",", gameData.getGuesses()));
        statement.executeUpdate(); // Execute SQL query
    }
}