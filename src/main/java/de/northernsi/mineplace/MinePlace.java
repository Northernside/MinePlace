package de.northernsi.mineplace;

import de.northernsi.mineplace.commands.*;
import de.northernsi.mineplace.listeners.*;
import de.northernsi.mineplace.utils.Broadcasts;
import de.northernsi.mineplace.utils.ConfigHandler;
import de.northernsi.mineplace.utils.MOTDUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public class MinePlace extends JavaPlugin {
    private static MinePlace instance;
    public ArrayList<UUID> usersWithLM = new ArrayList<UUID>();

    public static MinePlace getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        MinePlace.instance = this;
        System.out.println("> MinePlace has been enabled.");

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
        pm.registerEvents(new PlayerBukkitEmptyListener(), this);
        pm.registerEvents(new PotionSplashListener(), this);
        pm.registerEvents(new ProjectileLaunchListener(), this);

        getCommand("ping").setExecutor(new PingCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("modtool").setExecutor(new ModToolCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("bypasscooldown").setExecutor(new BypassCooldownCommand());
        getCommand("rules").setExecutor(new RulesCommand());

        ConfigHandler.getInstance().createConfig();
        MOTDUtils.sendPlayerList();
        MOTDUtils.sendSupportedVersions();
        Broadcasts.runBroadcasts();
    }

    @Override
    public void onDisable() {
        System.out.println("> MinePlace has been disabled.");
    }
}