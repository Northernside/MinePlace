package social.northernside.mineplace.listeners;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import social.northernside.mineplace.utils.*;

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

        JsonObject object = new JsonObject();
        object.addProperty("url", "https://raw.githubusercontent.com/Northernside/MinePlace/main/labymod-header.png");
        LabyModProtocol.sendLabyModMessage(player, "server_banner", object);

        for (Player gop : Bukkit.getOnlinePlayers()) {
            if (ConfigHandler.getInstance().getTeamByUUID(player.getUniqueId()) != null) {
                SubtitleUtils.setSubtitle(gop, player.getUniqueId(), "#" + ConfigHandler.getInstance().getTeamByUUID(player.getUniqueId()));
            }

            if (ConfigHandler.getInstance().getTeamByUUID(gop.getUniqueId()) != null) {
                SubtitleUtils.setSubtitle(player, gop.getUniqueId(), "#" + ConfigHandler.getInstance().getTeamByUUID(gop.getUniqueId()));
            }
        }
    }
}