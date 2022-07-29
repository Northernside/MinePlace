package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.listeners.PlayerInteractListener;
import de.northernsi.mineplace.types.Rank;
import de.northernsi.mineplace.utils.RankProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class BypassCooldownCommand implements CommandExecutor {
    public static ArrayList<UUID> bypassingUsers = new ArrayList<UUID>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (RankProvider.getInstance().getRank(player) != Rank.MOD && RankProvider.getInstance().getRank(player)
                    != Rank.ADMIN) {
                commandSender.sendMessage("§cYou don't have the permission to execute this command!");
                return true;
            }

            if (bypassingUsers.contains(player.getUniqueId())) {
                bypassingUsers.remove(player.getUniqueId());
                player.sendMessage("§aYou are no longer bypassing the cooldown.");
                return true;
            }

            bypassingUsers.add(player.getUniqueId());
            PlayerInteractListener.cooldownMap.remove(player.getUniqueId());
            player.sendMessage("§aYou are now bypassing the cooldown.");

            return true;
        }

        return false;
    }
}
