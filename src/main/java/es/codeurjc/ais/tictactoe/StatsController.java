package es.codeurjc.ais.tictactoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class StatsController {

    @Autowired
    private TicTacToeHandler ttth;

    @RequestMapping("/stats")
    public String showStats(Model model){
        //Obtiene los jugadores que han participado
        StatisticsService stats = ttth.getStats();
        Set<Player> players = stats.getPlayers();
        //Crea una lista y la llena con los stats de los jugadores
        List<PlayerStats> playerStats = new ArrayList<>();
        for (Player p: players) {
            playerStats.add(stats.getPlayerStats(p));
        }
        //Pasa dichos stats al html a traves del model
        model.addAttribute("stats", playerStats);
        return "stats_template";
    }
}
