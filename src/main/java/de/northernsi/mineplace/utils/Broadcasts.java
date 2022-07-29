package de.northernsi.mineplace.utils;

import de.northernsi.mineplace.objects.Broadcast;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Broadcasts {
    public static void runBroadcasts() {
        TextComponent mapMessage = new TextComponent();
        mapMessage.setText("§e§lMine§6§lPlace §9» §7View our live world map at ");

        TextComponent mapLink = new TextComponent();
        mapLink.setText("§emap.mineplace.space");
        mapLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://map.mineplace.space"));
        mapLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to visit the live world map.").create()));
        mapMessage.addExtra(mapLink);

        Broadcast liveMap = new Broadcast(mapMessage, (long) (10 * 60 * 20) /* 10 minutes */);
        liveMap.register();
    }
}
