// Created by Torben R.
package social.northernside.mineplace.types;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

@Getter
@Setter
public class Area {

    private final Plugin plugin;
    public Location location1 = null;
    public Location location2 = null;

    public Area(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setBlocks(Material material) {
        int xMin = Math.min(location1.getBlockX(), location2.getBlockX());
        int yMin = Math.min(location1.getBlockY(), location2.getBlockY());
        int zMin = Math.min(location1.getBlockZ(), location2.getBlockZ());
        int xMax = Math.max(location1.getBlockX(), location2.getBlockX());
        int yMax = Math.max(location1.getBlockY(), location2.getBlockY());
        int zMax = Math.max(location1.getBlockZ(), location2.getBlockZ());
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            double sizeX = Math.abs(xMax - xMin) + 1;
            double sizeY = Math.abs(yMax - yMin) + 1;
            double sizeZ = Math.abs(zMax - zMin) + 1;
            int x = 0;
            int y = 0;
            int z = 0;

            @Override
            public void run() {
                if (x <= sizeX && y <= sizeY && z <= sizeZ) {
                    location1.getWorld().getBlockAt((int) (location1.getX() + this.x), (int) (location1.getY() + this.y), (int) (location1.getZ() + this.z)).setType(material);
                    if (++x >= this.sizeX) {
                        this.x = 0;
                        if (++this.y >= this.sizeY) {
                            this.y = 0;
                            ++this.z;
                        }
                    }
                }
            }
        }, 5, 5);
    }
}