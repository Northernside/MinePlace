// Created by Torben R.
package de.northernsi.mineplace.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.northernsi.mineplace.types.Team;
import de.northernsi.mineplace.types.TeamMember;
import de.northernsi.mineplace.types.TeamRole;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import de.northernsi.mineplace.MinePlace;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class TeamHandler {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().create();

    private final Map<String, Team> teams = new ConcurrentHashMap<>();
    private final Map<UUID, TeamMember> cache = Collections.emptyMap();
    private final Map<UUID, Team> invites = Collections.emptyMap();

    public boolean createTeam(String name, Player player) {
        if (teams.containsKey(name)) return false;
        teams.put(name, new Team(name, Collections.singletonList(new TeamMember(player.getUniqueId(), name, TeamRole.OWNER)),
                Collections.emptyList()));
        return true;
    }

    private void setRole(String target, TeamRole role) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
        Team team = teams.get(getTeamMember(offlinePlayer.getUniqueId()).getTeamname());
        TeamMember targetMember = getTeamMember(offlinePlayer.getUniqueId());
        targetMember.setRole(role);
    }

    public void deleteTeam(Player player) {
        teams.remove(getTeamMember(player.getUniqueId()).getTeamname());
    }

    public void leaveTeam(Player player) {
        teams.get(getTeamMember(player.getUniqueId()).getTeamname()).getMembers().remove(getTeamMember(player.getUniqueId()));
    }

    public void invite(Player player, String target) {
        invites.put(Bukkit.getOfflinePlayer(target).getUniqueId(), getTeam(getTeamMember(player.getUniqueId())));
    }

    public void kick(Player player, String target) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
        Team team = teams.get(getTeamMember(player.getUniqueId()).getTeamname());
        TeamMember targetMember = getTeamMember(offlinePlayer.getUniqueId());
        team.getMembers().remove(targetMember);
    }

    public void promoteToMod( String target) {
        setRole(target, TeamRole.MODERATOR);
    }

    public void demoteToMember(Player player, String target) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
        Team team = teams.get(getTeamMember(player.getUniqueId()).getTeamname());
        TeamMember targetMember = getTeamMember(offlinePlayer.getUniqueId());
        targetMember.setRole(TeamRole.MEMBER);
    }

    public void ban(Player player, String target) {
        setRole(target, TeamRole.BANNED);
    }

    private TeamMember getTeamMember(UUID uuid) {
        if (cache.containsKey(uuid)) return cache.get(uuid);
        AtomicReference<TeamMember> atomicReference = new AtomicReference<>();
        teams.values().stream().forEach(team -> {
            if (atomicReference.get() == null) {
                TeamMember member = team.getMembers().stream().filter(teamMember -> teamMember.getUuid().equals(uuid)).findFirst().get();
                cache.put(uuid, member);
                atomicReference.set(member);
                return;
            }
        });

        return atomicReference.get();
    }

    private Team getTeam(TeamMember teamMember) {
        return teams.values().stream().filter(team -> team.getMembers().contains(teamMember)).findFirst().get();
    }

    private void saveAllTeams() {
        String path = MinePlace.getInstance().getDataFolder().getPath()+"data/teams";
        teams.values().stream().forEach(this::saveTeam);
    }

    @SneakyThrows(IOException.class)
    private void saveTeam(Team team) {
        String path = MinePlace.getInstance().getDataFolder().getPath()+"data/teams";
        File file = new File(path+team.getName()+".json");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(GSON.toJson(team));
        writer.close();
    }
}
