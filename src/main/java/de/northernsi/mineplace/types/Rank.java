// Created by Torben R.
package de.northernsi.mineplace.types;

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

    public int getId() {
        return id;
    }
}
