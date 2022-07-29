package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.types.Rank;
import de.northernsi.mineplace.utils.RankProvider;
import de.northernsi.mineplace.utils.ScoreboardProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player && RankProvider.getInstance().getRank((Player) commandSender) != Rank.ADMIN
                && !commandSender.isOp()) {
            commandSender.sendMessage("§cYou don't have the permission to execute this command!");
            return true;
        }

        if (args.length < 2) {
            commandSender.sendMessage("§cUsage: /rank <username> <rank>");
            return true;
        }

        args[1] = args[1].toLowerCase();
        switch (args[1]) {
            case "admin":
            case "dev":
            case "mod":
            case "vip":
            case "guest":
                RankProvider.getInstance().setRank(args[0], Rank.valueOf(args[1].toUpperCase()));
                ScoreboardProvider.getInstance().setScoreboard();
                commandSender.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1]);
                break;
            default:
                commandSender.sendMessage("§cUnknown rank");
                break;
        }

        ScoreboardProvider.getInstance().setScoreboard();
        return false;
    }
}