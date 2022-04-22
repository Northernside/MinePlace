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
        scoreboard.registerNewTeam("02Dev");
        scoreboard.registerNewTeam("03SrMod");
        scoreboard.registerNewTeam("04Mod");
        scoreboard.registerNewTeam("05VIP");
        scoreboard.registerNewTeam("06Guest");

        scoreboard.getTeam("01Admin").setPrefix("§4Admin §8» §c");
        scoreboard.getTeam("02Dev").setPrefix("§9Dev §8» §c");
        scoreboard.getTeam("03SrMod").setPrefix("§cSrMod §8» §c");
        scoreboard.getTeam("04Mod").setPrefix("§cMod §8» §c");
        scoreboard.getTeam("05VIP").setPrefix("§5VIP §8» §5");
        scoreboard.getTeam("06Guest").setPrefix("§7");
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
        } else if (RankProvider.getInstance().getRank(player) == Rank.DEV) {
            team = "02Dev";
        } else if (RankProvider.getInstance().getRank(player) == Rank.SRMOD) {
            team = "03SrMod";
        } else if (RankProvider.getInstance().getRank(player) == Rank.MOD) {
            team = "04Mod";
        } else if (RankProvider.getInstance().getRank(player) == Rank.VIP) {
            team = "05VIP";
        } else {
            team = "06Guest";
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