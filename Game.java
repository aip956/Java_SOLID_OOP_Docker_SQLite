import java.util.Scanner;
import java.sql.Timestamp;
import java.sql.SQLException;


public class Game {
    private Guesser guesser;
    private SecretKeeper secretKeeper;
    private String secretCode;
    private int maxAttempts;
    private int attemptsLeft;
    private Scanner scanner;
    private GameData gameData;
    private GameData.GameDataDAO gameDataDAO;


    // class constructor
    public Game (Guesser guesser, GameData gameData, GameData.GameDataDAO gameDataDAO) {
        this.scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();
        this.secretKeeper = new SecretKeeper(this, playerName);
        this.guesser = new Guesser(scanner);
        this.gameData = gameData == null ? new GameData() : gameData;
        this.gameDataDAO = gameDataDAO;
        this.gameData.setPlayerName(playerName);
        this.maxAttempts = secretKeeper.maxAttempts;
        this.attemptsLeft = secretKeeper.attemptsLeft;
        this.secretCode = secretKeeper.secretCode;
        
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
        do {
            String guess = guesser.makeGuess();
            // System.out.println("GameLine28");
            if (isValidGuess(guess)) {
                String feedback = secretKeeper.provideFeedback(guess);
                System.out.println(feedback);
                attemptsLeft--;

                if (guess.equals(secretCode)) {
                    System.out.println("Congrats! You did it! I'm the Game.java");
                    gameData.setSolved(true);
                    break;
                }

                if (attemptsLeft > 0) {
                    System.out.println("Round " + (maxAttempts - attemptsLeft));
                }
            
            } else {
                System.out.println("Wrong Input!");
            }
        } while (attemptsLeft > 0);

        // Womp womp womp . . . you lose
        if (attemptsLeft == 0) {
            System.out.println("Sorry, too many tries. The code was: " + secretCode);
            gameData.setSolved(false);
        }
        
        scanner.close();
        finalizeGameData();
        saveGameDataToDatabase();
    }

    private void finalizeGameData() {
        gameData.setRoundsToSolve(maxAttempts - attemptsLeft);
        gameData.setTimestamp(new Timestamp(System.currentTimeMillis()));
        gameData.setSecretCode(secretCode);
        gameData.setGuesses(guesser.getGuesses());
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
    // Method to check if guess is four nums 0 - 8
    public boolean isValidGuess(String guess) {
        try {
            int guessNum = Integer.valueOf(guess);
            return guessNum >= 0 && guessNum <= 8888 && guess.length() == 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
