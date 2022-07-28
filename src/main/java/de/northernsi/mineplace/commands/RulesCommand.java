package de.northernsi.mineplace.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RulesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            player.sendMessage("§aOur building and general behavior rules are accessible here: §ehttps://mineplace.space/rules");
            return true;
        }

        return false;
    }
}
