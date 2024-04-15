import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class GameData implements Serializable {
    private int gameID;
    private String playerName;
    private int roundsToSolve;
    private boolean solved;
    private Timestamp timestamp;
    private String secretCode;
    private List<String> guesses;

    // Constructor
    public GameData() {}

    public GameData(int gameID, String playerName, int roundsToSolve, boolean solved, Timestamp timestamp, 
    String secretCode, List<String> guesses) {
        this.gameID = gameID;
        this.playerName = playerName;
        this.roundsToSolve = roundsToSolve;
        this.solved = solved;
        this.timestamp = timestamp;
        this.secretCode = secretCode;
        this.guesses = guesses;
    }
    // Getters, setters
    public int getGameID() {
        return gameID;
    }

    public void setGameID (int gameID) {
        this.gameID = gameID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getRoundsToSolve() {
        return roundsToSolve;
    }

    public void setRoundsToSolve(int roundsToSolve) {
        this.roundsToSolve = roundsToSolve;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<String> guesses) {
        this.guesses = guesses;
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
                statement.setTimestamp(4, gameData.getTimestamp());
                statement.setString(5, gameData.getSecretCode());
                // Convert the list of guesses to a single string
                statement.setString(6, String.join(",", gameData.getGuesses()));
                statement.executeUpdate(); // Execute SQL query
            }
        }
    }
}