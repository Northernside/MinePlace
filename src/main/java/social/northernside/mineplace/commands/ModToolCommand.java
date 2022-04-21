package social.northernside.mineplace.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import social.northernside.mineplace.types.Rank;
import social.northernside.mineplace.utils.InventoryPages;
import social.northernside.mineplace.utils.RankProvider;

public class ModToolCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (RankProvider.getInstance().getRank(player) == Rank.MOD || RankProvider.getInstance().getRank(player) == Rank.ADMIN || player.isOp()) {
                InventoryPages.changePage(666, player.getInventory());
            } else {
                commandSender.sendMessage("§cYou are not permitted to execute this command");
            }

            //InventoryPages.changePage(666, player.getInventory());
        }

        return false;
    }
}
