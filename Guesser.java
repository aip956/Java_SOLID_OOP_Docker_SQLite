import java.util.Scanner;

public class Guesser extends Player {
    // variables
    private Scanner scanner;

    // constructor
    public Guesser(Scanner scanner, String name) {
        super(name); // constructor in Player has name
        this.scanner = scanner;

    }

    public String makeGuess() {
      
        System.out.println("Will you find the secret code?");
        System.out.println("---");
        return scanner.nextLine();
    }


}
