package de.northernsi.mineplace.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import de.northernsi.mineplace.types.Rank;
import de.northernsi.mineplace.utils.RankProvider;

import java.util.HashMap;
import java.util.UUID;

public class AsyncPlayerChatListener implements Listener {

    public static HashMap<UUID, Long> cooldownMap = new HashMap<UUID, Long>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage().replace("%", "%%");
        Player player = event.getPlayer();
        if (RankProvider.getInstance().getRank(event.getPlayer()) == Rank.ADMIN) {
            event.setFormat("§cAdmin §7" + player.getName() + " §8⇾ §f" + message.replace('&', '§'));
        } else if (RankProvider.getInstance().getRank(event.getPlayer()) == Rank.DEV) {
            event.setFormat("§bDev §7" + player.getName() + " §8⇾ §f" + message.replace('&', '§'));
        } else if (RankProvider.getInstance().getRank(event.getPlayer()) == Rank.MOD) {
            event.setFormat("§6Mod §7" + player.getName() + " §8⇾ §f" + message.replace('&', '§'));
        } else if (RankProvider.getInstance().getRank(event.getPlayer()) == Rank.VIP) {
            Long time = System.currentTimeMillis();
            Long nextMSG = (cooldownMap.get(player.getUniqueId()) == null) ? 0L : cooldownMap.get(player.getUniqueId());

            if (nextMSG + 0.8 * 1000 > time) {
                player.sendMessage("§cDo not spam.");
                event.setCancelled(true);
            } else {
                event.setFormat("§dVIP §7" + player.getName() + " §8⇾ §f" + message.replace('&', '§'));
                cooldownMap.put(player.getUniqueId(), time);
            }
        } else {
            Long time = System.currentTimeMillis();
            Long nextMSG = (cooldownMap.get(player.getUniqueId()) == null) ? 0L : cooldownMap.get(player.getUniqueId());

            if (nextMSG + 0.8 * 1000 > time) {
                player.sendMessage("§cDo not spam.");
                event.setCancelled(true);
            } else {
                event.setFormat("§7" + player.getName() + " §8⇾ §f" + message);
                cooldownMap.put(player.getUniqueId(), time);
            }
        }
    }
}