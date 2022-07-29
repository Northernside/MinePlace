package de.northernsi.mineplace.objects;

import de.northernsi.mineplace.MinePlace;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

public class Broadcast {
    public TextComponent message;
    public Long interval;

    public Broadcast(TextComponent message, Long interval) {
        this.message = message;
        this.interval = interval;
    }

    public void register() {
        Bukkit.getScheduler().runTaskTimer(MinePlace.getInstance(), () -> Bukkit.spigot().broadcast(message), 0, interval);
    }
}
