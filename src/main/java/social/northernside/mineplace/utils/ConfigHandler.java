package social.northernside.mineplace.utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import social.northernside.mineplace.MinePlace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ConfigHandler {
    private static ConfigHandler instance;
    private String pluginDirPath = MinePlace.getInstance().getDataFolder().getPath();

    public static ConfigHandler getInstance() {
        if (ConfigHandler.instance == null) {
            ConfigHandler.instance = new ConfigHandler();
        }
        
        return ConfigHandler.instance;
    }

    public void createTeam(String teamName, UUID ownerUUID) {
        setTeamMemberRole(teamName, ownerUUID, "owner");
        addTeamToUser(teamName, ownerUUID);
    }

    public void deleteTeam(String teamName, UUID ownerUUID) {
        File file = new File(pluginDirPath, "teams/" + teamName + ".yml");
        file.delete();
        deleteUserFile(ownerUUID);
    }

    public String getTeamMemberRole(String teamName, UUID pUUID) {
        File file = new File(pluginDirPath, "teams/" + teamName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        return (String) cfg.getConfigurationSection("members").get(String.valueOf(pUUID));
    }

    public void addTeamMember(String teamName, UUID pUUID) {
        setTeamMemberRole(teamName, pUUID, "member");
        addTeamToUser(teamName, pUUID);
    }

    private void addTeamToUser(String teamName, UUID pUUID) {
        File file = new File(pluginDirPath, "users/" + pUUID + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if (cfg.getConfigurationSection("team") == null) {
            cfg.createSection("team");
        }

        ConfigurationSection teamSection = cfg.getConfigurationSection("team");
        teamSection.set("name", teamName);

        try {
            cfg.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteUserFile(UUID pUUID) {
        File file = new File(pluginDirPath, "users/" + pUUID + ".yml");
        if (file.exists()) {
            file.delete();
        }
    }

    public boolean existsTeam(String teamName) {
        File file = new File(pluginDirPath, "teams/" + teamName + ".yml");

        if (file.exists()) {
            return true;
        }

        return false;
    }

    public void removeTeamMember(String teamName, UUID pUUID) {
        File file = new File(pluginDirPath, "teams/" + teamName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection membersSection = cfg.getConfigurationSection("members");
        membersSection.set(String.valueOf(pUUID), null);

        try {
            cfg.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        deleteUserFile(pUUID);
    }

    public String getTeamByUUID(UUID pUUID) {
        File file = new File(pluginDirPath, "users/" + pUUID + ".yml");

        if (file.exists()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return (String) cfg.getConfigurationSection("team").get("name");
        }

        return null;
    }

    public void banTeamMember(String teamName, UUID pUUID) {
        setTeamMemberRole(teamName, pUUID, "banned");
        deleteUserFile(pUUID);
    }

    private void setTeamMemberRole(String teamName, UUID pUUID, String role) {
        File file = new File(pluginDirPath, "teams/" + teamName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if (cfg.getConfigurationSection("members") == null) {
            cfg.createSection("members");
        }

        ConfigurationSection membersSection = cfg.getConfigurationSection("members");
        membersSection.set(String.valueOf(pUUID), role);

        try {
            cfg.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void promoteTeamMember(String teamName, UUID pUUID) {
        setTeamMemberRole(teamName, pUUID, "mod");
    }

    public void demoteTeamMember(String teamName, UUID pUUID) {
        setTeamMemberRole(teamName, pUUID, "member");
    }

    public void unbanTeamMember(String teamName, UUID pUUID) {
        removeTeamMember(teamName, pUUID);
    }
}
