// MyMastermind.java
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMastermind {
    // private static final Logger logger = LoggerFactory.getLogger(MyMastermind.class);
    public static void main(String[] args) {
        // db path to save
        String dbPath = System.getenv("DB_FILE");
        if (dbPath == null || dbPath.isEmpty()) {
            dbPath = "MM_Reach.db"; // default path when running locally
            System.out.println("Database local path: " + dbPath);
        } else {
            System.out.println("Database path from environment: " + dbPath);
        }
        System.out.println("Attempting to connect to the database...");
        GameDataDAO gameDataDAO = null; // Declaring outside try block

        // Connection connection = null; // Enable this for postgress
        // PostgreSQLGameDataDAO gameDataDAO = null;

        try {
            gameDataDAO = new SQLiteGameDataDAO(dbPath);

            // Instantiate the PostgreSQLGameDataDAO with necessary parameters
            // Enable these for postgress
            // connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MM_Reach", "postgres", "local");
            // gameDataDAO = new PostgreSQLGameDataDAO(connection);

            System.out.println("DB connect success");
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

