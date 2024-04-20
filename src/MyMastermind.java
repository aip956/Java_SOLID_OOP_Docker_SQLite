// MyMastermind.java

import Models.Guesser;
import Models.SecretKeeper;
import Models.GameData;
import Models.Game;
import View.GameUI;
import DAO.GameDataDAO;
import DAO.SQLiteGameDataDAO;
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
        dbPath = (dbPath == null || dbPath.isEmpty()) ? "src/MM_Reach.db" : dbPath;
        System.out.println("Attempting to connect to the database...");
        GameDataDAO gameDataDAO; // Declaring outside try block

        try {
            gameDataDAO = new SQLiteGameDataDAO(dbPath);
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

