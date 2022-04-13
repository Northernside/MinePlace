// Credits: Zeichenfolge
package social.northernside.mineplace.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TabUtils {
    public static void setTab(Player player, String header, String footer) {
        if (header == null) header = "";
        if (footer == null) footer = "";
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        IChatBaseComponent Title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header.replace('&', '§') + "\"}");
        IChatBaseComponent Footer = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer.replace('&', '§') + "\"}");

        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(Title);

        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, Footer);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.sendPacket(headerPacket);
        }
    }
}
