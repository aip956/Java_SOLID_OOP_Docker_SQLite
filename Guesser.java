// Guesser.java
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Guesser extends Player {
    private Scanner scanner;
    private List<String> guesses;

    public Guesser() {
        super("");// initialize with empty name
        this.scanner = new Scanner(System.in);
        this.guesses = new ArrayList<>();
        System.out.println("Please enter your name: ");
        this.playerName = scanner.nextLine();
    }
    public String makeGuess() {
        System.out.print("Enter guess: ");
        String guess = scanner.nextLine();
        // System.out.println("Guesser21guess: " + guess);
        guesses.add(guess);
        // System.out.println("Guesser22: " + guesses); // Log the addition for debugging

        return guess;
    }
    public List<String> getGuesses() {
            return new ArrayList<>(guesses); // return a copy for encapsulation
    }
}

