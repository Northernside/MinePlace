// Credits: unordentlich <3
package de.northernsi.mineplace.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class CensorArea {
    Location loc1;
    Location loc2;

    public CensorArea() {
    }

    public Location getLoc1() {
        return loc1;
    }

    public void setLoc1(Location loc1) {
        this.loc1 = loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    public void setLoc2(Location loc2) {
        this.loc2 = loc2;
    }

    public boolean isReady() {
        return loc1 != null && loc2 != null;
    }

    public World getWorld() {
        return loc1.getWorld();
    }
}
