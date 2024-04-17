import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Guesser extends Player {
    // variables
    private Scanner scanner;
    private List<String> guesses = new ArrayList<>();

    // private String guessedCode;
    
    // constructor
    public Guesser(Scanner scanner) {
        this.scanner = scanner;
    }
    // takes in the input guess
    public String makeGuess() {
        String guessedCode = scanner.nextLine();
        guesses.add(guessedCode);
        return guessedCode;
    }

    public List<String> getGuesses() {
        return new ArrayList<>(guesses); // return a copy for encapsulation
    }
}
