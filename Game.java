// Game.java
import java.util.Scanner;
import java.sql.Timestamp;
import java.sql.SQLException;


public class Game {
    private Guesser guesser;
    private SecretKeeper secretKeeper;
    private GameData gameData;
    private GameDataDAO gameDataDAO;
    private Scanner scanner;
    private int attemptsLeft;
    public static final int MAX_ATTEMPTS = 5;

    public static int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }



    // class constructor
    public Game (Guesser guesser, SecretKeeper secretKeeper, GameData gameData, GameDataDAO gameDataDAO) {
        this.guesser = guesser;
        this.secretKeeper = secretKeeper;
        this.gameData = gameData; 
        this.gameDataDAO = gameDataDAO;      
        this.scanner = new Scanner(System.in);
        this.attemptsLeft = MAX_ATTEMPTS;
    }

    public void startGame() {
        // Prompts to start game
        if (attemptsLeft == MAX_ATTEMPTS) {
            System.out.println("Will you find the secret code?");
            System.out.println("---");
        }
        // Play game; Guesser makes guess
        // Game determines if guess is valid and wins, tracks Round, determins if loses
        while (secretKeeper.hasAttemptsLeft()) {
            System.out.println("---"); 
            System.out.println("Round " + (getMaxAttempts() - secretKeeper.getAttemptsLeft()));  
            System.out.println("Rounds left: " + secretKeeper.getAttemptsLeft());     
            
            String guess = guesser.makeGuess();
            
            if (secretKeeper.isValidGuess(guess)) {
                secretKeeper.evaluateGuess(guess);
                String feedback = secretKeeper.provideFeedback(guess);
                System.out.println(feedback);
                // gameData.addGuess(guess);
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
            System.out.println("Sorry, too many tries. The code was: " + secretKeeper.getSecretCode());
            gameData.setSolved(false);
        }
        finalizeGameData();
        scanner.close();
    }

    private void finalizeGameData() {
        gameData.setPlayerName(guesser.getPlayerName());
        gameData.setGuesses(guesser.getGuesses());
        gameData.setRoundsToSolve(getMaxAttempts() - secretKeeper.getAttemptsLeft());
        gameData.setTimestamp(new Timestamp(System.currentTimeMillis()));
        gameData.setSecretCode(secretKeeper.getSecretCode());
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
}
