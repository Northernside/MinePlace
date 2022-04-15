// Created by Torben R.
package social.northernside.mineplace.types;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class Team {

    String name;
    List<TeamMember> members;
    List<UUID> bannedPlayers;

}
