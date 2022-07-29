package de.northernsi.mineplace.listeners;

import de.northernsi.mineplace.objects.MPPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        new MPPlayer(event.getPlayer()).joinMinePlace();
    }
}
