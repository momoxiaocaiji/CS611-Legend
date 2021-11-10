import java.util.List;

public abstract class RPGame {
    protected PlayMap playMap;

    /**
     * Get list of players
     * @return a list of players
     */
    protected abstract List<? extends MAHGamePlayer> getPlayers();

    /**
     * Removes the player from the game
     * @param p player to be removed
     */
    protected abstract void removePlayer(MAHGamePlayer p);
}
