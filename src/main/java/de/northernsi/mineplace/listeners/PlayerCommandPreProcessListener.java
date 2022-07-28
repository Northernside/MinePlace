package de.northernsi.mineplace.listeners;

import de.northernsi.mineplace.MinePlace;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerCommandPreProcessListener implements Listener {
    @EventHandler
    public void onPlayerCommandPreProcess(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().replaceAll("\\s.*", "");
        switch (command) {
            case "/plugins":
            case "/pl":
            case "/version":
            case "/ver":
            case "/v":
            case "/about":
            case "/bukkit":
            case "/spigot":
            case "/paper":
                event.setCancelled(true);
                sendVersionCMD(event.getPlayer());
                break;
            case "/me":
            case "/minecraft:me":
                event.setCancelled(true);
                event.getPlayer().sendMessage("§cThis command is not available on§r §eMine§6Place§c.");
                break;
            case "/help":
            case "/h":
            case "/?":
            case "/commands":
            case "/mineplace":
            case "/mp":
            case "/server":
                event.setCancelled(true);
                sendHelpCMD(event.getPlayer());
                break;
        }
    }

    private void sendVersionCMD(Player player) {
        TextComponent srcMessage = new TextComponent();
        srcMessage.setText("§7The source code is completely available on");

        TextComponent srcLink = new TextComponent();
        srcLink.setText("§egithub.com/Northernside/MinePlace");
        srcLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Northernside/MinePlace"));
        srcLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to visit the GitHub repository.").create()));
        srcMessage.addExtra(srcLink);

        TextComponent versionMessage = new TextComponent();
        versionMessage.setText("§7This server is running on§r §eMine§6Place§r §av" + MinePlace.getPlugin(MinePlace.class).getDescription().getVersion() + "§7.\n");
        versionMessage.addExtra(srcMessage);

        player.spigot().sendMessage(versionMessage);
    }

    private void sendHelpCMD(Player player) {
        player.sendMessage("§8[§eMine§6Place§8]§r §6Information about MinePlace");
        player.sendMessage("§e/ping§r §b<user>§r §7Shows your or another users' network latency");
        player.sendMessage("§e/team§r §b<arguments>§r §7Manage build teams");

        TextComponent moreInfo = new TextComponent();
        moreInfo.setText("§7Further information on our§r §9Discord§7:§r ");

        TextComponent discordLink = new TextComponent();
        discordLink.setText("§ediscord.gg/4ttvN7m2SY");
        discordLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/4ttvN7m2SY"));
        discordLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to visit the Discord server.").create()));
        moreInfo.addExtra(discordLink);

        TextComponent websiteInfo = new TextComponent();
        websiteInfo.setText("§7Visit our§r §bwebsite§7:§r ");

        TextComponent websiteLink = new TextComponent();
        websiteLink.setText("§emineplace.space");
        websiteLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mineplace.space"));
        websiteLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to visit the website.").create()));
        websiteInfo.addExtra(websiteLink);

        player.spigot().sendMessage(moreInfo);
        player.spigot().sendMessage(websiteInfo);

    }
}