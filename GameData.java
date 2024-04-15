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
    void saveGameData(GameData gameData) throws SQLException;
    List<GameData> getGamesByPlayer(String playerName) throws SQLException;
    List<GameData> getGamesSolvedWithinRounds(int rounds) throws SQLException;
    List<GameData> getSolved(boolean solved) throws SQLException;
    List<GameData> getTime(Timestamp timestamp) throws SQLException;
    List<GameData> getSecret(String secretCode) throws SQLException;
    List<GameData> getGuesses(List<String> guesses) throws SQLException;
}
