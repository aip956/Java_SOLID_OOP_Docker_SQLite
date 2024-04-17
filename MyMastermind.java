import java.sql.SQLException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMastermind {
    private static final Logger logger = LoggerFactory.getLogger(MyMastermind.class);
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // db path to save
        String dbPath = "MM_Reach.db";

        // Save to the database
        try {
            // Create data-related objects:
            GameData gameData = new GameData();
            GameData.SQLiteGameDataDAO dao = new GameData.SQLiteGameDataDAO(dbPath);
            logger.debug("19GameData and SQLiteGameDataDAO objects created");
            // Create game instance and necessary components:
            Guesser guesser = new Guesser(scanner);
            Game game = new Game(guesser, gameData, dao);
            logger.info("23Game instance and components intitialized");

            // Start game
            game.startGame();
            logger.info("27Game started");

            // Logging gameData state before saving
            // logger.debug("Saving GameData: PlayerName: {}, RoundsToSolve: {}, Solved: {}, Timestamp: {}, SecretCode: {}, Guesses: {}", 
                // gameData.getPlayerName(), gameData.getRoundsToSolve(), gameData.isSolved(), gameData.getTimestamp(), gameData.getSecretCode(), gameData.getGuesses());

            // Save game data to the db
            // dao.saveGameData(gameData);
            // System.out.println("34Game data saved");
            // logger.info("35Game data saved successfully");
        } catch (SQLException e) {
            logger.error("37Error occured saving game data: " + e.getMessage());
        } finally {
            // Close scanner
            scanner.close();
            logger.debug("41Scanner closed");
        }
    }
}
