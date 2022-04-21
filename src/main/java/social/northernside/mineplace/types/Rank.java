// Created by Torben R.
package social.northernside.mineplace.types;

public enum Rank {

    ADMIN(1),
    MOD(2),
    VIP(3),
    GUEST(4);

    private final int id;

    Rank(int id) {
        this.id = id;
    }

    public static Rank byId(int id) {
        switch (id) {
            case 1:
                return ADMIN;
            case 2:
                return MOD;
            case 3:
                return VIP;
            case 4:
                return GUEST;
        }
        return GUEST;
    }

    public int getId() {
        return id;
    }
}
