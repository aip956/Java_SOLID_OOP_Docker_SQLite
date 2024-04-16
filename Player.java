public abstract class Player {
    protected String guessedCode;
    protected String playerName;

    public void makeGuess(String guess) {
        guessedCode = guess;
    }
}
