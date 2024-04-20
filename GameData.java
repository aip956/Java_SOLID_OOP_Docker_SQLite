// GameData.java
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameData implements Serializable {
    // debugging saving issue
    private static final Logger logger = LoggerFactory.getLogger(GameData.class);

    private int gameID;
    private String playerName;
    private int roundsToSolve;
    private boolean solved;
    private Timestamp timestamp;
    private String secretCode;
    private List<String> guesses;

    // Constructor
    public GameData() {
        this.guesses = new ArrayList<>(); // Ensure list is never null
    }

    public GameData(int gameID, String playerName, int roundsToSolve, boolean solved, Timestamp timestamp, 
    String secretCode, List<String> guesses) {
        this.gameID = gameID;
        this.playerName = playerName;
        this.roundsToSolve = roundsToSolve;
        this.solved = solved;
        this.timestamp = timestamp;
        this.secretCode = secretCode;
        this.guesses = (guesses == null) ? new ArrayList<>() : guesses; // Safely handle null input list
    }
    // Getters, setters
    public int getGameID() {
        return gameID;
    }

    public void setGameID (int gameID) {
        this.gameID = gameID;
        // logger.debug("48GameIDD: {}", gameID);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        // logger.debug("57playerName: {}", playerName);
    }

    public int getRoundsToSolve() {
        return roundsToSolve;
    }

    public void setRoundsToSolve(int roundsToSolve) {
        this.roundsToSolve = roundsToSolve;
        // logger.debug("66roundsToSolve: {}", roundsToSolve);
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
        // logger.debug("75solved: {}", solved);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        // logger.debug("84timestamp: {}", timestamp);
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
        // logger.debug("93secretCode: {}", secretCode);
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<String> guesses) {
        this.guesses = guesses;
        // logger.debug("102guesses: {}", guesses);
    }
}
