public abstract class Player {
    protected String playerName;
    // protected String guessedCode;

    public Player(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    // public void setPlayerName(String playerName) {
    //     this.playerName = playerName;
    // }
    // public void makeGuess(String guess) {
    //     guessedCode = guess;
    // }
}
