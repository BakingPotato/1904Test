package es.codeurjc.ais.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private List<PlayerStats> statsList = new ArrayList<>();

    public PlayerStats getPlayerStats(Player player) {
        return statsList.get(player.getId());
    }

    public void updatePlayerStats(Player player, PlayerStats playerStats) {
        if (statsList.get(player.getId()) != null)
            statsList.remove(player.getId());
        statsList.add(player.getId(), playerStats);
    }

    public void addPlayerStats(Player player, PlayerStats playerStats) {
        statsList.add(player.getId(), playerStats);
    }
}
