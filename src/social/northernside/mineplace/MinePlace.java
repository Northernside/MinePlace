package social.northernside.mineplace;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import social.northernside.mineplace.listeners.*;

public class MinePlace extends JavaPlugin {
    private static MinePlace instance;

    @Override
    public void onEnable() {
        MinePlace.instance = this;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new WeatherChangeListener(), this);
        pluginManager.registerEvents(new PlayerDropItemListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static MinePlace getInstance() {
        return instance;
    }
}
