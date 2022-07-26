package social.northernside.mineplace.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import social.northernside.mineplace.listeners.PlayerInteractListener;

import java.util.ArrayList;
import java.util.UUID;

public class BypassCooldownCommand implements CommandExecutor {
    public static ArrayList<UUID> bypassingUsers = new ArrayList<UUID>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.isOp()) {
                if (bypassingUsers.contains(player.getUniqueId())) {
                    bypassingUsers.remove(player.getUniqueId());
                    player.sendMessage("§aYou are no longer bypassing the cooldown.");
                } else {
                    bypassingUsers.add(player.getUniqueId());
                    PlayerInteractListener.cooldownMap.remove(player.getUniqueId());
                    player.sendMessage("§aYou are now bypassing the cooldown.");
                }
            } else {
                player.sendMessage("§cYou are not allowed to use this command.");
            }

            return true;
        }

        return false;
    }
}
