package social.northernside.mineplace.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import social.northernside.mineplace.types.Rank;

public class ScoreboardProvider {
    private static ScoreboardProvider instance;
    private Scoreboard scoreboard;

    public ScoreboardProvider() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewTeam("01Admin");
        scoreboard.registerNewTeam("02Mod");
        scoreboard.registerNewTeam("03VIP");
        scoreboard.registerNewTeam("04Guest");

        scoreboard.getTeam("01Admin").setPrefix("§4Admin §8» §4");
        scoreboard.getTeam("02Mod").setPrefix("§cMod §8» §c");
        scoreboard.getTeam("03VIP").setPrefix("§5VIP §8» §5");
        scoreboard.getTeam("04Guest").setPrefix("§7");
    }

    public void setScoreboard() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            setTeams(all);
        }
    }

    @SuppressWarnings("deprecation")
    private void setTeams(Player player) {
        String team = "";

        if (RankProvider.getInstance().getRank(player) == Rank.ADMIN) {
            team = "01Admin";
        } else if (RankProvider.getInstance().getRank(player) == Rank.MOD) {
            team ="02Mod";
        } else if (RankProvider.getInstance().getRank(player) == Rank.VIP) {
            team = "03VIP";
        } else {
            team = "04Guest";
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