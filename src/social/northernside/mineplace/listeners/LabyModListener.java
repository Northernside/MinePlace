package social.northernside.mineplace.listeners;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import social.northernside.mineplace.utils.LabyModProtocol;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class LabyModListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("labymod3:main")) {
            return;
        }

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        ByteBuf buf = Unpooled.wrappedBuffer(message);
        String key = LabyModProtocol.readString(buf, Short.MAX_VALUE);
        String json = LabyModProtocol.readString(buf, Short.MAX_VALUE);

        if (key.equals("input_prompt")) {
            try {
                Object pJSON = new JSONParser().parse(json);
                JSONObject jObj = (JSONObject) pJSON;
                String value = (String) jObj.get("value");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
