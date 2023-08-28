public class Player {
    private String guessedCode;

    public Player(String name) {

    }
    
    public String getName() {
        return name;
    }

    public void makeGuess(String guess) {
        guessedCode = guess;
    }



    // public boolean determineWinStatus(String secretCode) {
    //     // Implement win status logic specific to Guesser
    //     // Check if guessedCode matches secretCode
    //     return guessedCode.equals(secretCode);
    // }
}
