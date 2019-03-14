package es.codeurjc.ais.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private List<PlayerStats> statsList = new ArrayList<>();

    public PlayerStats getPlayerStats(Player player) {
        return statsList.get(player.getId());
    }

    public void addWin(Player player){
        statsList.get(player.getId()).addWin();
    }

    public void addLoss(Player player){
        statsList.get(player.getId()).addLoss();
    }

    public void addDraw(Player player){
        statsList.get(player.getId()).addDraw();
    }

    public void addPlayerStats(Player player, PlayerStats playerStats) {
        statsList.add(player.getId(), playerStats);
    }
}
