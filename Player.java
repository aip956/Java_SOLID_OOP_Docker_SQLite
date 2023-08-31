public abstract class Player {
    protected String guessedCode;

    public void makeGuess(String guess) {
        guessedCode = guess;
    }
   
// getter method for future extendability
    // public String getGuessedCode() {
    //     return guessedCode;
    // }
}
