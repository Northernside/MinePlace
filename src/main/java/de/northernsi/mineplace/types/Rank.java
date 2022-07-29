// Created by Torben R.
package de.northernsi.mineplace.types;

import org.bukkit.entity.Player;

import java.util.List;

public enum Rank {
    ADMIN(1),
    DEV(2),
    MOD(3),
    VIP(4),
    GUEST(5);

    private final int id;

    Rank(int id) {
        this.id = id;
    }

    public static Rank byId(int id) {
        switch (id) {
            case 1:
                return ADMIN;
            case 2:
                return DEV;
            case 3:
                return MOD;
            case 4:
                return VIP;
            case 5:
                return GUEST;
        }
        return GUEST;
    }

    public static List<String> getRanks() {
        List<String> ranks = new java.util.ArrayList<>();
        for (Rank rank : values()) {
            ranks.add(rank.name());
        }

        return ranks;
    }

    public String buildPrefix(Player player) {
        switch (this) {
            case ADMIN:
                return "§cAdmin §7" + player.getName() + " §8⇾ §f";
            case DEV:
                return "§bDev §7" + player.getName() + " §8⇾ §f";
            case MOD:
                return "§6Mod §7" + player.getName() + " §8⇾ §f";
            case VIP:
                return "§dVIP §7" + player.getName() + " §8⇾ §f";
            case GUEST:
                return "§7" + player.getName() + " §8⇾ §f";
        }
        return "§7" + player.getName() + " §8⇾ §f";
    }

    public int getId() {
        return id;
    }
}
