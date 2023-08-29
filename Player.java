public class Player {
    private String guessedCode;



    public void makeGuess(String guess) {
        guessedCode = guess;
    }

    public String getGuessedCode() {
        return guessedCode;
    }

}
   // public boolean determineWinStatus(String secretCode) {
    //     // Implement win status logic specific to Guesser
    //     // Check if guessedCode matches secretCode
    //     return guessedCode.equals(secretCode);
    // }