package de.northernsi.mineplace.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

public class PotionSplashListener implements Listener {
    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        event.setCancelled(true);
    }
}
