import java.sql.SQLException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import GameData.GameDataDAO;

public class MyMastermind {
    // private static final Logger logger = LoggerFactory.getLogger(MyMastermind.class);
    public static void main(String[] args) {
        // db path to save
        String dbPath = "MM_Reach.db";
        GameDataDAO gameDataDAO = new SQLiteGameDataDAO(dbPath);
        Guesser guesser = new Guesser();
        SecretKeeper secretKeeper = new SecretKeeper()
        GameData gameData = new GameData();
        Game game = new Game(new Guesser(), new SecretKeeper(), new GameData(), gameDataDAO);
        // Start game
        game.startGame();
    }
}

        // // Save to the database
        // try {
        //     // Create data-related objects:
        //     GameData gameData = new GameData();
        //     GameData.SQLiteGameDataDAO dao = new GameData.SQLiteGameDataDAO(dbPath);
        //     logger.debug("19GameData and SQLiteGameDataDAO objects created");
        //     // Create game instance and necessary components:
        //     Guesser guesser = new Guesser(scanner);
            
        //     logger.info("23Game instance and components intitialized");

            
        //     logger.info("27Game started");


        // } catch (SQLException e) {
        //     logger.error("37Error occured saving game data: " + e.getMessage());
        // } finally {
        //     // Close scanner
        //     scanner.close();
        //     logger.debug("41Scanner closed");
        // }

