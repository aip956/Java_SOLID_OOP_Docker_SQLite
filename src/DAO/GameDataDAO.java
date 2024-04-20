// GameDataDAO.java
package DAO;
import Models.GameData;
import java.sql.SQLException;
import java.util.List;


public interface GameDataDAO {
    // Data Access Object (DAO) interface; allows games to be pulled by playerName and solved
    void saveGameData(GameData gameData) throws SQLException; // Save a game's data to db
    List<GameData> getGamesByPlayer(String playerName) throws SQLException; // Retrieves games by a specific player
    List<GameData> getGamesBySolved(boolean solved) throws SQLException; // Retrieves games based on solved or not
}

