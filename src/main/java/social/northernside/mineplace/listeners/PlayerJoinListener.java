package social.northernside.mineplace.listeners;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import social.northernside.mineplace.MinePlace;
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
        object.addProperty("url", "https://raw.githubusercontent.com/Northernside/MinePlace/main/.github/raw/header.png");
        LabyModProtocol.sendLabyModMessage(player, "server_banner", object);

        if (ConfigHandler.getInstance().existsTeam(ConfigHandler.getInstance().getTeamByUUID(player.getUniqueId()))) {
            for (Player gop : Bukkit.getOnlinePlayers()) {
                if (ConfigHandler.getInstance().getTeamByUUID(player.getUniqueId()) != null) {
                    SubtitleUtils.setSubtitle(gop, player.getUniqueId(), "§e#" + ConfigHandler.getInstance().getTeamByUUID(player.getUniqueId()));
                }

                if (ConfigHandler.getInstance().getTeamByUUID(gop.getUniqueId()) != null) {
                    SubtitleUtils.setSubtitle(player, gop.getUniqueId(), "§e#" + ConfigHandler.getInstance().getTeamByUUID(gop.getUniqueId()));
                }
            }
        } else if (ConfigHandler.getInstance().getTeamByUUID(player.getUniqueId()) != null) {
            ConfigHandler.getInstance().deleteUserFile(player.getUniqueId());
        }

        new BukkitRunnable() {
            public void run() {
                if (!MinePlace.getInstance().usersWithLM.contains(player.getUniqueId())) {
                    player.sendMessage("§7Hey, we've detected that you're not using LabyMod.\nMight wanna try it? Download it via https://labymod.net/download\n\nWe've made great use of the LabyMod API which allows us to give all LabyMod users a better experience while playing on MinePlace.\nFor example: Our team system is only useful when our players are using LabyMod because your team name will be visible right below your ingame name.");
                }
            }
        }.runTaskLater(MinePlace.getInstance(), 80);
    }
}
