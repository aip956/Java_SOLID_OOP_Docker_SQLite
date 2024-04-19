import java.sql.SQLException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMastermind {
    // private static final Logger logger = LoggerFactory.getLogger(MyMastermind.class);
    public static void main(String[] args) {
        // db path to save
        String dbPath = "MM_Reach.db";
        GameDataDAO gameDataDAO = null; // Declaring outside try block
        try {
            gameDataDAO = new SQLiteGameDataDAO(dbPath);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return;
        }
        Guesser guesser = new Guesser();
        SecretKeeper secretKeeper = new SecretKeeper();
        GameData gameData = new GameData();
        Game game = new Game(guesser, secretKeeper, gameData, gameDataDAO);
        // Start game
        game.startGame();
    }
}

