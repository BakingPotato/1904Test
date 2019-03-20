package es.codeurjc.ais.tictactoe;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StatisticsService {
    private Map<Player, PlayerStats> statsMap = new HashMap<>();

    public PlayerStats getPlayerStats(Player player) {
        return statsMap.get(player);
    }

    public Set<Player> getPlayers(){
        return statsMap.keySet();
    }

    public void addWin(Player player){
        statsMap.get(player).addWin();
    }

    public void addLoss(Player player){
        statsMap.get(player).addLoss();
    }

    public void addDraw(Player player){
        statsMap.get(player).addDraw();
    }

    public void addPlayerStats(Player player, PlayerStats playerStats) {
        statsMap.put(player, playerStats);
    }
}
