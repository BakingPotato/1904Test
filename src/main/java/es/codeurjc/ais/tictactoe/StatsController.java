package es.codeurjc.ais.tictactoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class StatsController {

    @Autowired
    private TicTacToeHandler ttth;

    @RequestMapping("/stats")
    public String showStats(Model model){

        StatisticsService stats = ttth.getStats();
        List<Player> players = ttth.getPlayers();

        model.addAttribute("name1", players.get(0).getName());
        model.addAttribute("name2", players.get(1).getName());
        model.addAttribute("stats1", stats.getPlayerStats(players.get(0)));
        model.addAttribute("stats2", stats.getPlayerStats(players.get(1)));

        return "/stats.mustache";
    }
}
