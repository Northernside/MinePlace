package social.northernside.mineplace.listeners;

import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
        if (event.getPlayer().isOp()) {
            event.setFormat("§4Administrator §8» §c" + player.getName() + " §8» §7" + message.replace('&', '§'));
        } else {
            Long time = System.currentTimeMillis();
            Long nextMSG = (cooldownMap.get(player.getUniqueId()) == null) ? 0L : cooldownMap.get(player.getUniqueId());

            if(nextMSG + 0.8 * 1000 > time) {
                player.sendMessage("§cDo not spam.");
                event.setCancelled(true);
            } else {
                event.setFormat("§7" + player.getName() + " §8» §7" + message);
                cooldownMap.put(player.getUniqueId(), time);
            }
        }
    }
}