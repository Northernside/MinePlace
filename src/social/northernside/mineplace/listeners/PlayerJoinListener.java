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
import social.northernside.mineplace.utils.ScoreboardProvider;
import social.northernside.mineplace.utils.TabUtils;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 1, 0.5));
        player.setGameMode(GameMode.CREATIVE);
        player.setFlying(true);

        InventoryPages.changePage(0, player.getInventory());
        LabyModRPC.setRPC(player);
        TabUtils.setTab(player, "&e&lMine&6&lPlace", " &7Made with &c❤ &7by Northernside ");
        ScoreboardProvider.getInstance().setScoreboard();
    }
}