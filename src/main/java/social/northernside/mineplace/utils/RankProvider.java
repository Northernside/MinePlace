// Created by Torben R.
package social.northernside.mineplace.utils;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import social.northernside.mineplace.MinePlace;
import social.northernside.mineplace.types.Rank;

import java.io.File;
import java.io.IOException;

public class RankProvider {

    private static RankProvider instance;
    private final File file = new File(MinePlace.getInstance().getDataFolder().getPath(), "ranks.yml");
    private final YamlConfiguration yamlConfiguration;

    public static RankProvider getInstance() {
        if (instance == null) {
            return new RankProvider();
        }
        return instance;
    }

    public RankProvider() {
        instance = this;
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public Rank getRank(Player player) {
        return Rank.byId(this.yamlConfiguration.getInt(player.getUniqueId().toString()));
    }

    @SneakyThrows(IOException.class)
    public void setRank(Player player, Rank rank) {
        this.yamlConfiguration.set(player.getUniqueId().toString(), rank.getId());
        yamlConfiguration.save(this.file);
    }

    @SneakyThrows(IOException.class)
    public void setRank(String name, Rank rank) {
        this.yamlConfiguration.set(Bukkit.getOfflinePlayer(name).getUniqueId().toString(), rank.getId());
        yamlConfiguration.save(this.file);
    }
}
