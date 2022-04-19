package social.northernside.mineplace.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import social.northernside.mineplace.utils.InventoryPages;

public class ModToolCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        /*if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            InventoryPages.changePage(666, player.getInventory());
        }*/

        return false;
    }
}
