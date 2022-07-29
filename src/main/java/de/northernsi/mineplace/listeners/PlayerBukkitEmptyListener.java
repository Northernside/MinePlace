package de.northernsi.mineplace.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class PlayerBukkitEmptyListener implements Listener {
    @EventHandler
    public void onPlayerBukkitEmpty(PlayerBucketEmptyEvent event) {
        event.setCancelled(true);
    }
}
