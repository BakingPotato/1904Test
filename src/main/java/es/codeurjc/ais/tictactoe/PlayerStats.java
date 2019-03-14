package es.codeurjc.ais.tictactoe;

public class PlayerStats {
    private int wins;
    private int losses;
    private int draws;

    public PlayerStats() {
    }

    public PlayerStats(int wins, int losses, int draws) {
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public int getWins() {
        return wins;
    }

    public void addWin(int wins) {
        this.wins++;
    }

    public int getLosses() {
        return losses;
    }

    public void addLoss(int losses) {
        this.losses++;
    }

    public int getDraws() {
        return draws;
    }

    public void addDraw(int draws) {
        this.draws++;
    }
}
