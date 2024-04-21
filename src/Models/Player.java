// Player.java
package Models;

public abstract class Player {
    protected String playerName;
    // protected String guessedCode;

    public Player(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }
}
