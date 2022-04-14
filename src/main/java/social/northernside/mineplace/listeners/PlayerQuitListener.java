package social.northernside.mineplace.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID pUUID = event.getPlayer().getUniqueId();
        event.setQuitMessage(null);

        if (PlayerInteractListener.cooldownMap.containsKey(pUUID))
            PlayerInteractListener.cooldownMap.remove(pUUID);
        if (AsyncPlayerChatListener.cooldownMap.containsKey(pUUID))
            AsyncPlayerChatListener.cooldownMap.remove(pUUID);
    }
}
