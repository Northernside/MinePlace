package de.northernsi.mineplace;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import de.northernsi.mineplace.commands.*;
import de.northernsi.mineplace.listeners.*;
import de.northernsi.mineplace.utils.ConfigHandler;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MinePlace extends JavaPlugin {
    private static MinePlace instance;
    public ArrayList<UUID> usersWithLM = new ArrayList<UUID>();

    @Override
    public void onEnable() {
        System.out.println("> MinePlace has been enabled.");
        MinePlace.instance = this;

        getServer().getMessenger().registerIncomingPluginChannel(this, "labymod3:main", new LabyModListener());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new WeatherChangeListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new AsyncPlayerChatListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new EntitySpawnListener(), this);
        pm.registerEvents(new FoodLevelChangeListener(), this);
        pm.registerEvents(new PlayerCommandPreProcessListener(), this);

        getCommand("ping").setExecutor(new PingCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("modtool").setExecutor(new ModToolCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("bypasscooldown").setExecutor(new BypassCooldownCommand());
        getCommand("rules").setExecutor(new RulesCommand());

        final List<WrappedGameProfile> names = new ArrayList<>();
        names.add(new WrappedGameProfile("1", " "));
        names.add(new WrappedGameProfile("2", "        §e§lMine§6§lPlace §8» §7r/place in Minecraft "));
        names.add(new WrappedGameProfile("3", "                 §8» §emine§6place§7.space §8«       "));
        names.add(new WrappedGameProfile("4", " "));
        names.add(new WrappedGameProfile("5", "               §9discord.gg/4ttvN7m2SY               "));
        names.add(new WrappedGameProfile("6", " "));
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL,
                Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.getPacket().getServerPings().read(0).setPlayers(names);
            }
        });

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Status.Server.OUT_SERVER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing ping = event.getPacket().getServerPings().read(0);
                ping.setVersionName("§c1.8 - 1.19");
            }
        });

        ConfigHandler.getInstance().createConfig();
    }

    @Override
    public void onDisable() {
        System.out.println("> MinePlace has been disabled.");
    }

    public static MinePlace getInstance() {
        return instance;
    }
}