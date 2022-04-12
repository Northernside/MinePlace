package social.northernside.mineplace.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardProvider {
    private static ScoreboardProvider instance;
    private Scoreboard scoreboard;

    public ScoreboardProvider() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewTeam("01Admin");
        scoreboard.registerNewTeam("02Default");

        scoreboard.getTeam("01Admin").setPrefix("§4Admin §8» §c");
        scoreboard.getTeam("02Default").setPrefix("§7");
    }

    public void setScoreboard() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            setTeams(all);
        }
    }

    @SuppressWarnings("deprecation")
    private void setTeams(Player player) {
        String team = "";

        if (player.isOp()) {
            team = "01Admin";
        } else {
            team = "02Default";
        }

        scoreboard.getTeam(team).addPlayer(player);
        player.setScoreboard(scoreboard);
    }

    public static ScoreboardProvider getInstance() {
        if (ScoreboardProvider.instance == null) {
            ScoreboardProvider.instance = new ScoreboardProvider();
        }

        return ScoreboardProvider.instance;
    }
}