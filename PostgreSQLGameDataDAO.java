// PostgreSQLGameDataDAO.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLGameDataDAO implements GameDataDAO {
    private Connection connection;

    // Constructor to initialize the connection
    public PostgreSQLGameDataDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveGameData(GameData gameData) throws SQLException {
        String sql = "INSERT INTO game_data (player_name, rounds_to_solve, solved, timestamp, secret_code, guesses) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, gameData.getPlayerName());
            statement.setInt(2, gameData.getRoundsToSolve());
            statement.setBoolean(3, gameData.isSolved());
            statement.setTimestamp(4, gameData.getTimestamp());
            statement.setString(5, gameData.getSecretCode());
            statement.setString(6, String.join(",", gameData.getGuesses()));
            statement.executeUpdate();
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
                GameData gameData = extractGameData(resultSet);
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
                GameData gameData = extractGameData(resultSet);
                games.add(gameData);
            }
        }
        return games;
    }

    // Helper method to extract GameData from ResultSet
    private GameData extractGameData(ResultSet resultSet) throws SQLException {
        int gameID = resultSet.getInt("game_id");
        String playerName = resultSet.getString("player_name");
        int roundsToSolve = resultSet.getInt("rounds_to_solve");
        boolean solved = resultSet.getBoolean("solved");
        Timestamp timestamp = resultSet.getTimestamp("timestamp");
        String secretCode = resultSet.getString("secret_code");
        String guessesString = resultSet.getString("guesses");
        String[] guessesArray = guessesString.split(",");
        List<String> guesses = List.of(guessesArray);
        return new GameData(gameID, playerName, roundsToSolve, solved, timestamp, secretCode, guesses);
    }
}
