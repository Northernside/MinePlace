package de.northernsi.mineplace.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        event.setCancelled(true);
    }
}
