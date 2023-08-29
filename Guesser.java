import java.util.Scanner;

public class Guesser extends Player {
    // variables
    private Scanner scanner;
    private String guessedCode;
    
    // constructor
    public Guesser(Scanner scanner) {
        this.scanner = scanner;

    }

    public String makeGuess() {
        guessedCode = scanner.nextLine();
        return guessedCode;
    }
}
