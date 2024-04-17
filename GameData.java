import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class GameData implements Serializable {
    private int gameID;
    private String playerName;
    private int roundsToSolve;
    private boolean solved;
    private Timestamp timestamp;
    private String secretCode;
    private List<String> guesses;

    // Constructor
    public GameData() {
        this.guesses = new ArrayList<>(); // Ensure list is never null
    }

    public GameData(int gameID, String playerName, int roundsToSolve, boolean solved, Timestamp timestamp, 
    String secretCode, List<String> guesses) {
        this.gameID = gameID;
        this.playerName = playerName;
        this.roundsToSolve = roundsToSolve;
        this.solved = solved;
        this.timestamp = timestamp;
        this.secretCode = secretCode;
        this.guesses = (guesses == null) ? new ArrayList<>() : guesses; // Safely handle null input list
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

// Dat Access Object (DAO) interface; allows games to be pulled by playerName and solved
    public interface GameDataDAO {
        void saveGameData(GameData gameData) throws SQLException; // Save a game's data to db
        List<GameData> getGamesByPlayer(String playerName) throws SQLException; // Retrieves games by a specific player
        // List<GameData> getGamesSolvedWithinRounds(int rounds) throws SQLException; // Retrieves games solved within a spec. num of rounds
        List<GameData> getGamesBySolved(boolean solved) throws SQLException; // Retrieves games based on solved or not
        // List<GameData> getGamesByTime(Timestamp timestamp) throws SQLException; // Retrieves games played at a spec timestamp
        // List<GameData> getGamesBySecret(String secretCode) throws SQLException; // Retrieves games with a spec secret code
        // List<GameData> getGamesByGuesses(List<String> guesses) throws SQLException; // Retrieves games based on the list of guesses
    }

    public static class SQLiteGameDataDAO implements GameDataDAO {
        private Connection connection;
        static {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                System.err.println("Could not init JDBC driver - driver not found");
            }
        }
            
        // Constructor to init the connection
        public SQLiteGameDataDAO(String dbPath) throws SQLException {
            // Connect to the SQLite db
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
                    Timestamp timestamp = resultSet.getTimestamp("timestamp");
                    String secretCode = resultSet.getString("secret_code");
                    String guessesString = resultSet.getString("guesses");
                    List<String> guesses = new ArrayList<>();
                    if (guessesString != null) {
                        String[] guessArray = guessesString.split(",");
                        Collections.addAll(guesses, guessArray);
                    }
                    GameData gameData = new GameData(gameID, playerName, roundsToSolve, solved, timestamp, secretCode, guesses);
                    games.add(gameData);
                }
            }
            return games;
        }

        @Override
        public List<GameData> getGamesBySolved(boolean solved) throws SQLException {
            List<GameData> games = new ArrayList<>();
            // add in logic
            return games;
        }
    }
}