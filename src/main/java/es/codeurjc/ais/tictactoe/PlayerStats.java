package es.codeurjc.ais.tictactoe;

public class PlayerStats {
    private String name;
    private int wins;
    private int losses;
    private int draws;

    public PlayerStats() {
    }

    public PlayerStats(String name, int wins, int losses, int draws) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        this.wins++;
    }

    public int getLosses() {
        return losses;
    }

    public void addLoss() {
        this.losses++;
    }

    public int getDraws() {
        return draws;
    }

    public void addDraw() {
        this.draws++;
    }

    @Override
    public String toString(){
        return "Stats for" + name + " -> Wins: " + wins + ", Losses: " + losses + ", Draws: " + draws;
    }
}
