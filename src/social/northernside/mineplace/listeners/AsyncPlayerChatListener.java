package social.northernside.mineplace.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage().replace("%", "%%");
        if (event.getPlayer().isOp()) {
            event.setFormat("§4Administrator §8» §c" + event.getPlayer().getName() + " §8» §7" + message.replace('&', '§'));
        } else {
            event.setFormat("§7" + event.getPlayer().getName() + " §8» §7" + message);
        }
    }
}