package social.northernside.mineplace.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import social.northernside.mineplace.types.Rank;
import social.northernside.mineplace.utils.RankProvider;
import social.northernside.mineplace.utils.ScoreboardProvider;

public class RankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player && (commandSender.isOp() ||
                RankProvider.getInstance().getRank((Player) commandSender) == Rank.ADMIN)) {
            if (args.length == 2) {
                args[1] = args[1].toLowerCase();
                switch (args[1]) {
                    case "guest":
                        RankProvider.getInstance().setRank(args[0], Rank.GUEST);
                        ScoreboardProvider.getInstance().setScoreboard();
                        commandSender.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1]);
                        break;
                    case "vip":
                        RankProvider.getInstance().setRank(args[0], Rank.VIP);
                        commandSender.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1]);
                        break;
                    case "mod":
                        RankProvider.getInstance().setRank(args[0], Rank.MOD);
                        commandSender.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1]);
                        break;
                    case "admin":
                        RankProvider.getInstance().setRank(args[0], Rank.ADMIN);
                        commandSender.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1]);
                        break;
                }
                ScoreboardProvider.getInstance().setScoreboard();
            } else {
                if (!commandSender.isOp()) {
                    commandSender.sendMessage("§cYou are not permitted to execute this command");
                } else {
                    commandSender.sendMessage("§cUsage: /rank <Player> <Rank>");
                }
            }
        }

        return false;
    }
}
