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
        if (commandSender instanceof Player && commandSender.isOp()) {
            if (args.length == 2) {
                switch (args[1]) {
                    case "guest":
                        RankProvider.getInstance().setRank(args[0], Rank.GUEST);
                        ScoreboardProvider.getInstance().setScoreboard();
                        commandSender.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1].toLowerCase());
                        break;
                    case "vip":
                        RankProvider.getInstance().setRank(args[0], Rank.VIP);
                        commandSender.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1].toLowerCase());
                        break;
                    case "mod":
                        RankProvider.getInstance().setRank(args[0], Rank.MOD);
                        commandSender.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1].toLowerCase());
                        break;
                    case "admin":
                        RankProvider.getInstance().setRank(args[0], Rank.ADMIN);
                        commandSender.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1].toLowerCase());
                        break;
                }
            }
        }

        return false;
    }
}
