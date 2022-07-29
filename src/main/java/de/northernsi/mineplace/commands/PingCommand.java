// Credits: Zeichenfolge
package de.northernsi.mineplace.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length == 0) {
                player.sendMessage("§aYour ping: §e" + ((CraftPlayer) player).getHandle().ping + "ms");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§cPlayer not found");
                return true;
            }

            CraftPlayer craftTarget = (CraftPlayer) target;
            player.sendMessage("§e" + target.getName() + "§a" + (!target.getName().endsWith("s".toLowerCase()) ? "'s" : "'") + " ping is §e" + craftTarget.getHandle().ping + "ms");
            return true;
        }

        return false;
    }
}