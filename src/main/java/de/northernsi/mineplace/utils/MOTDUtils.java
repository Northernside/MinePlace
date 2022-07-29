package de.northernsi.mineplace.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import de.northernsi.mineplace.MinePlace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MOTDUtils {
    public static void sendPlayerList() {
        final List<WrappedGameProfile> names = new ArrayList<>();
        names.add(new WrappedGameProfile("1", " "));
        names.add(new WrappedGameProfile("2", "        §e§lMine§6§lPlace §8» §7r/place in Minecraft "));
        names.add(new WrappedGameProfile("3", "                 §8» §emine§6place§7.space §8«       "));
        names.add(new WrappedGameProfile("4", " "));
        names.add(new WrappedGameProfile("5", "               §9discord.gg/4ttvN7m2SY               "));
        names.add(new WrappedGameProfile("6", " "));
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(MinePlace.getInstance(), ListenerPriority.NORMAL,
                Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.getPacket().getServerPings().read(0).setPlayers(names);
            }
        });
    }

    public static void sendSupportedVersions() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(MinePlace.getInstance(), PacketType.Status.Server.OUT_SERVER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing ping = event.getPacket().getServerPings().read(0);
                ping.setVersionName("§c1.8 - 1.19");
            }
        });
    }
}
