package social.northernside.mineplace.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import social.northernside.mineplace.MinePlace;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    private static ConfigHandler instance;
    private String pluginDirPath = MinePlace.getInstance().getDataFolder().getPath();

    public static ConfigHandler getInstance() {
        if (ConfigHandler.instance == null) {
            ConfigHandler.instance = new ConfigHandler();
        }
        return ConfigHandler.instance;
    }

    public void createTeam(String teamName, String ownerUUID) {
        File file = new File(pluginDirPath, "teams/" + teamName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            cfg.createSection("members");
            ConfigurationSection membersSection = cfg.getConfigurationSection("members");
            membersSection.set(ownerUUID, "owner");

            try {
                cfg.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isTeamMember(String teamName, String pUUID) {
        File file = new File(pluginDirPath, "teams/" + teamName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.getConfigurationSection("members").contains(pUUID);
    }

    public void addTeamMember(String teamName, String pUUID) {
        File file = new File(pluginDirPath, "teams/" + teamName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            ConfigurationSection membersSection = cfg.getConfigurationSection("members");
            membersSection.set(pUUID, "member");

            try {
                cfg.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeTeamMember(String teamName, String pUUID) {
        File file = new File(pluginDirPath, "teams/" + teamName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            ConfigurationSection membersSection = cfg.getConfigurationSection("members");
            membersSection.set(pUUID, null);

            try {
                cfg.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getTeamByUUID(String pUUID) {
        File file = new File(pluginDirPath, "users/" + pUUID + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return (String) cfg.getConfigurationSection("team").get("name");
    }
}