import java.sql.SQLException;

public interface GameDataDAO {
    // Data Access Object (DAO) interface; allows games to be pulled by playerName and solved
    void saveGameData(GameData gameData) throws SQLException; // Save a game's data to db
    List<GameData> getGamesByPlayer(String playerName) throws SQLException; // Retrieves games by a specific player
    List<GameData> getGamesBySolved(boolean solved) throws SQLException; // Retrieves games based on solved or not
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
    

