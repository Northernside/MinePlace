package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.types.Rank;
import de.northernsi.mineplace.utils.InventoryPages;
import de.northernsi.mineplace.utils.RankProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModToolCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (RankProvider.getInstance().getRank(player) != Rank.MOD && RankProvider.getInstance().getRank(player)
                    != Rank.ADMIN) {
                commandSender.sendMessage("§cYou don't have the permission to execute this command!");
                return true;
            }

            InventoryPages.changePage(666, player.getInventory());
            player.getInventory().setHeldItemSlot(4);
        }

        return false;
    }
}
