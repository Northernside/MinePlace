package de.northernsi.mineplace.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class LabyModAPI {
    private static HashMap<Player, Long> starttimes = new HashMap<>();

    public static void setRPC(Player player) {
        starttimes.put(player, Long.valueOf(System.currentTimeMillis()));
        String gamemodeName = "r/Place in Minecraft";

        JsonObject obj = new JsonObject();
        obj.addProperty("hasGame", "true");
        obj.addProperty("game_mode", gamemodeName);
        obj.addProperty("game_startTime", starttimes.get(player));
        obj.addProperty("game_endTime", Integer.valueOf(0));
        LabyModProtocol.sendLabyModMessage(player, "discord_rpc", obj);
    }

    public static void sendGamemode(Player player, String gamemodeName) {
        JsonObject object = new JsonObject();
        object.addProperty("show_gamemode", Boolean.valueOf(true));
        object.addProperty("gamemode_name", gamemodeName);
        LabyModProtocol.sendLabyModMessage(player, "server_gamemode", object);
    }

    public static void sendBanner(Player player) {
        JsonObject object = new JsonObject();
        object.addProperty("url", "https://raw.githubusercontent.com/Northernside/MinePlace/main/.github/raw/header.png");
        LabyModProtocol.sendLabyModMessage(player, "server_banner", object);
    }

    public static void sendSubtitle(Player player, Player receiver, String subtitleText) {
        JsonArray array = new JsonArray();
        JsonObject subtitle = new JsonObject();
        subtitle.addProperty("uuid", player.getUniqueId().toString());
        subtitle.addProperty("size", 1.2d);

        if (subtitleText != null)
            subtitle.addProperty("value", subtitleText);

        array.add(subtitle);

        LabyModProtocol.sendLabyModMessage(receiver, "account_subtitle", array);
    }
}
