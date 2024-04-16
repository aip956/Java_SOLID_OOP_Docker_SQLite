import java.sql.SQLException;
import com.example.GameData.GameDataDAO;
import com.example.GameData.SQLiteGameDataDAO;

public class MyMastermind {
    public static void main(String[] args) {
        // db path to save
        String dbPath = "MM_Reach.db";
        // Instantiate a concrete subclass of Player
        Guesser player =  new Guesser();

        // create and start game
        GameData gameData = new GameData();
        Game game = new Game(player, gameData);
        game.startGame();

        // Save to the database
        try {
            // Create SQLIteGameDataDAO object
            SQLiteGameDataDAO dao = new SQLiteGameDataDAO(dbPath);
            // Save game data to the db
            dao.saveGameData(gameData);
            System.out.println("Game data saved");
        } catch (SQLException e) {
            System.err.println("Error occured saving game data: " + e.getMessage());
        }
    }
}
