package es.codeurjc.ais.tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats {
    private List<PlayerStats> statsList = new ArrayList<>();

    public PlayerStats getPlayerStats(Player player) {
        return statsList.get(player.getId());
    }

    public void updatePlayerStats(Player player, PlayerStats playerStats) {
        if (statsList.contains(playerStats))
            statsList.remove(player.getId());
        statsList.add(player.getId(), playerStats);
    }
}
