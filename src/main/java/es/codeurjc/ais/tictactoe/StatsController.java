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

        StatisticsService stats = ttth.getStats();
        Set<Player> players = stats.getPlayers();

        for (Player p: players) {
            p.setStats(stats.getPlayerStats(p));
        }

        model.addAttribute("players", players);

        return "stats_template";
    }
}
