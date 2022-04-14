package social.northernside.mineplace;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import social.northernside.mineplace.commands.TeamCommand;
import social.northernside.mineplace.commands.PingCommand;
import social.northernside.mineplace.listeners.*;

import java.util.ArrayList;
import java.util.UUID;

public class MinePlace extends JavaPlugin {
    private static MinePlace instance;
    public ArrayList<UUID> usersWithLM = new ArrayList<UUID>();

    @Override
    public void onEnable() {
        MinePlace.instance = this;

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new WeatherChangeListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new AsyncPlayerChatListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);

        getServer().getMessenger().registerIncomingPluginChannel(this, "labymod3:main", new LabyModListener());

        getCommand("ping").setExecutor(new PingCommand());
        getCommand("team").setExecutor(new TeamCommand());
    }

    @Override
    public void onDisable() {}

    public static MinePlace getInstance() {
        return instance;
    }
}
