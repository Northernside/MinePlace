// Created by Torben R.
package de.northernsi.mineplace.types;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class TeamMember {

    UUID uuid;
    String teamname;
    TeamRole role;

    public UUID getUuid() {
        return uuid;
    }

    public TeamRole getRole() {
        return role;
    }
}
