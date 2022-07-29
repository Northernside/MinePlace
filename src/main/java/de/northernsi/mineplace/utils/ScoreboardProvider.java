// Credits: Zeichenfolge
package de.northernsi.mineplace.utils;

import de.northernsi.mineplace.types.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardProvider {
    private static ScoreboardProvider instance;
    private Scoreboard scoreboard;

    public ScoreboardProvider() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewTeam("01Admin");
        scoreboard.registerNewTeam("02Dev");
        scoreboard.registerNewTeam("03Mod");
        scoreboard.registerNewTeam("04VIP");
        scoreboard.registerNewTeam("05Guest");

        scoreboard.getTeam("01Admin").setPrefix("§cAdmin §8⇾ §7");
        scoreboard.getTeam("02Dev").setPrefix("§bDev §8⇾ §7");
        scoreboard.getTeam("03Mod").setPrefix("§6Mod §8⇾ §7");
        scoreboard.getTeam("04VIP").setPrefix("§dVIP §8⇾ §7");
        scoreboard.getTeam("05Guest").setPrefix("§7");
    }

    public static ScoreboardProvider getInstance() {
        if (ScoreboardProvider.instance == null) {
            ScoreboardProvider.instance = new ScoreboardProvider();
        }

        return ScoreboardProvider.instance;
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
        } else if (RankProvider.getInstance().getRank(player) == Rank.MOD) {
            team = "03Mod";
        } else if (RankProvider.getInstance().getRank(player) == Rank.VIP) {
            team = "04VIP";
        } else {
            team = "05Guest";
        }

        scoreboard.getTeam(team).addPlayer(player);
        player.setScoreboard(scoreboard);
    }
}