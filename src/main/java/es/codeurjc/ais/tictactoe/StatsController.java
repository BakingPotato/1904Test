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

        List<String> names = new ArrayList<>();
        List<PlayerStats> playerStats = new ArrayList<>();

        for (Player p: players) {
            names.add(p.getName());
            playerStats.add(stats.getPlayerStats(p));
        }

        model.addAttribute("names", names);
        model.addAttribute("stats", playerStats);

        return "stats_template";
    }
}
