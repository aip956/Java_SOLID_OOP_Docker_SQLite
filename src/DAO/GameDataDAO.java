// GameDataDAO.java
package DAO;
import Models.GameData;
import java.sql.SQLException;
import java.util.List;


public interface GameDataDAO {
    // Data Access Object (DAO) interface; allows games to be pulled by playerName and solved
    void saveGameData(GameData gameData) throws SQLException; // Save a game's data to db
    /*  Placehold for getGamesByPlayer
        Placehold for getGamesBySolved
        or any other retrieval of games from the database
    */
}

