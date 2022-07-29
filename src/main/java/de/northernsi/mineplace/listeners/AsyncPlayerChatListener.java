package de.northernsi.mineplace.listeners;

import de.northernsi.mineplace.objects.MPPlayer;
import de.northernsi.mineplace.types.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class AsyncPlayerChatListener implements Listener {
    public static HashMap<UUID, Long> cooldownMap = new HashMap<UUID, Long>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage().replace("%", "%%");
        MPPlayer mpPlayer = new MPPlayer(event.getPlayer());
        if (mpPlayer.getRank() == Rank.GUEST || mpPlayer.getRank() == Rank.VIP) {
            Long time = System.currentTimeMillis();
            Long nextMSG = (cooldownMap.get(mpPlayer.getUniqueId()) == null) ? 0L : cooldownMap.get(mpPlayer.getUniqueId());
            if (nextMSG + 0.8 * 1000 > time) {
                mpPlayer.sendMessage("§cDo not spam.");
                event.setCancelled(true);
            } else {
                cooldownMap.put(mpPlayer.getUniqueId(), time);
            }
        }

        event.setFormat(mpPlayer.buildPrefix() + message.replace('&', '§'));
    }
}