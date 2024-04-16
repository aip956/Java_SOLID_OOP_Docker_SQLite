import java.sql.SQLException;
import java.util.Scanner;

public class MyMastermind {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // db path to save
        String dbPath = "MM_Reach.db";

        // Create data-related objects:
        GameData gameData = new GameData();
        SQLiteGameDataDAO dao = new SQLiteGameDataDAO(dbPath);

        // Create game instance and necessary components:
        Guesser guesser = new Guesser(scanner);
        Game game = new Game(guesser, gameData, dao);

        // Start game
        game.startGame();

        // Close scanner
        scanner.close();

        // Save to the database
        try {
            // Save game data to the db
            dao.saveGameData(gameData);
            System.out.println("Game data saved");
        } catch (SQLException e) {
            System.err.println("Error occured saving game data: " + e.getMessage());
        }
    }
}
