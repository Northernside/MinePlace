package social.northernside.mineplace.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import social.northernside.mineplace.utils.InventoryPages;
import social.northernside.mineplace.utils.LabyModRPC;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 69, 0.5));
        player.setGameMode(GameMode.CREATIVE);
        player.setFlying(true);

        InventoryPages.changePage(0, player.getInventory());
        LabyModRPC.setRPC(player);
    }
}