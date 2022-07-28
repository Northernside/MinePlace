package de.northernsi.mineplace.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SubtitleUtils {
    public static void setSubtitle(Player receiver, UUID subtitlePlayer, String value) {
        JsonArray array = new JsonArray();
        JsonObject subtitle = new JsonObject();
        subtitle.addProperty("uuid", subtitlePlayer.toString());
        subtitle.addProperty("size", 1.2d);

        if (value != null)
            subtitle.addProperty("value", value);

        array.add(subtitle);

        LabyModProtocol.sendLabyModMessage(receiver, "account_subtitle", array);
    }
}
