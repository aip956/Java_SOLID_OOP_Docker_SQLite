import java.util.Scanner;
import java.sql.Timestamp;
import java.sql.SQLException;


public class Game {
    private Guesser guesser;
    private SecretKeeper secretKeeper;
    private GameData gameData;
    private GameData.GameDataDAO gameDataDAO;


    // class constructor
    public Game (Guesser guesser, SecretKeeper secretKeeper, GameData gameData, GameDataDAO gameDataDAO) {
        this.guesser = guesser;
        this.secretKeeper = secretKeeper;
        this.gameData = gameData; 
        this.gameDataDAO = gameDataDAO;      
    }

    public void startGame() {
        // Prompts to start game
        if (maxAttempts == attemptsLeft) {
            System.out.println("Will you find the secret code?");
            System.out.println("---");
            System.out.println("Round " + (maxAttempts - attemptsLeft));
        }
        // Play game; Guesser makes guess
        // Game determines if guess is valid and wins, tracks Round, determins if loses
        while (secretKeeper.hasAttemptsLeft()) {
            String guess = guesser.makeGuess();
            // System.out.println("GameLine28");
            if (secretKeeper.isValidGuess(guess)) {
                secretKeeper.evaluateGuess(guess);
                String feedback = secretKeeper.provideFeedback(guess);
                System.out.println(feedback);
                gameData.addGuess(guess);
                if (guess.equals(secretKeeper.getSecretCode())) {
                    System.out.println("Congrats! You did it! I'm the Game.java");
                    gameData.setSolved(true);
                    break;
                }
            } else {
                System.out.println("Wrong Input!");
            }
        } 

        // Womp womp womp . . . you lose
        if (!gameData.isSolved()) {
            System.out.println("Sorry, too many tries. The code was: " + secretCode);
            gameData.setSolved(false);
        }
        
        scanner.close();
        finalizeGameData();
        // saveGameDataToDatabase();
    }
}



    private void finalizeGameData() {
        gameData.setPlayerName(guesser.getName());
        gameData.setGuesses(secretKeeper.getGuesses());
        // gameData.setGuesses(guesser.getGuesses());
        // gameData.setRoundsToSolve(maxAttempts - attemptsLeft);
        gameData.setRoundsToSolve(secretKeeper.getMaxAttempts() - secretKeeper.getAttemptsLeft());
        gameData.setTimestamp(new Timestamp(System.currentTimeMillis()));
        // gameData.setSecretCode(secretCode);
        saveGameDataToDatabase();
        
    }
    // Save data to database
    public void saveGameDataToDatabase() {
        try {
            gameDataDAO.saveGameData(gameData);
            System.out.println("Game data saved");
        } catch (SQLException e) {
            System.err.println("Error occured saving game data: " + e.getMessage());
        }
    }


