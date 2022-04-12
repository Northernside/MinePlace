package social.northernside.mineplace.utils;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class LabyModRPC {
    private static HashMap<Player, Long> starttimes = new HashMap<>();

    public static void setRPC(Player player) {
        starttimes.put(player, Long.valueOf(System.currentTimeMillis()));
        String gamemodeName = "MinePlace";
        JsonObject obj = new JsonObject();
        obj.addProperty("hasGame", "true");
        obj.addProperty("game_mode", gamemodeName);
        obj.addProperty("game_startTime", starttimes.get(player));
        obj.addProperty("game_endTime", Integer.valueOf(0));
        LabyModProtocol.sendLabyModMessage(player, "discord_rpc", obj);

        JsonObject object = new JsonObject();
        object.addProperty("show_gamemode", Boolean.valueOf(true));
        object.addProperty("gamemode_name", gamemodeName);
        LabyModProtocol.sendLabyModMessage(player, "server_gamemode", object);
    }
}
