// Created by Torben R.
package social.northernside.mineplace.types;

public enum Rank {

    ADMIN(1),
    DEV(2),
    SRMOD(3),
    MOD(4),
    VIP(5),
    GUEST(6);

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
                return SRMOD;
            case 4:
                return MOD;
            case 5:
                return VIP;
            case 6:
                return GUEST;
        }
        return GUEST;
    }

    public int getId() {
        return id;
    }
}
