package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.MinePlace;
import de.northernsi.mineplace.utils.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID pUUID = player.getUniqueId();
            String teamName = ConfigHandler.getInstance().getTeamByUUID(pUUID);
            if (args.length >= 1) {
                if (args.length == 1 && args[0].equalsIgnoreCase("delete")) {
                    if (teamName != null) {
                        player.sendMessage("§4Danger! §cType §e/team delete confirm §cif you really want to delete your team (§e#" + teamName + "§c).");
                    } else {
                        player.sendMessage("§cYou're not in a team!");
                    }

                    return false;
                }

                if (args[0].equalsIgnoreCase("delete") && args[1].equalsIgnoreCase("confirm")) {
                    if (teamName == null) {
                        player.sendMessage("§cYou're not in a team!");
                        return false;
                    }

                    if (!ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID).equals("owner")) {
                        player.sendMessage("§cYou're not the owner of this team!");
                        return false;
                    }

                    for (Player gop : Bukkit.getOnlinePlayers()) {
                        if (ConfigHandler.getInstance().getTeamByUUID(gop.getUniqueId()) != null
                                && ConfigHandler.getInstance().getTeamByUUID(gop.getUniqueId()).equals(teamName)
                                && !gop.getUniqueId().equals(pUUID)) {
                            gop.sendMessage("§cYour team has been deleted by the owner.");
                            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                                SubtitleUtils.setSubtitle(allPlayers, gop.getUniqueId(), null);
                            }
                        }

                        SubtitleUtils.setSubtitle(gop, player.getUniqueId(), null);
                    }

                    ConfigHandler.getInstance().deleteTeam(teamName, pUUID);
                    player.sendMessage("§cYour team has been deleted.");

                    return false;
                }

                if (args[0].equalsIgnoreCase("leave")) {
                    if (teamName == null) {
                        player.sendMessage("§cYou're not in a team!");
                        return false;
                    }

                    String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                    if (tMRole.equals("owner")) {
                        player.sendMessage("§cYou can't leave your own team! Delete it instead via §e/team delete§c.");
                        return false;
                    }

                    ConfigHandler.getInstance().removeTeamMember(teamName, pUUID);
                    for (Player gop : Bukkit.getOnlinePlayers()) {
                        if (ConfigHandler.getInstance().getTeamByUUID(gop.getUniqueId()) != null
                                && ConfigHandler.getInstance().getTeamByUUID(gop.getUniqueId()).equals(teamName)
                                && !gop.getUniqueId().equals(pUUID)) {
                            gop.sendMessage("§e" + player.getName() + " §cleft the team.");
                        }

                        SubtitleUtils.setSubtitle(gop, player.getUniqueId(), null);
                    }

                    return false;
                }

                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("invite")) {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: §e/team invite <username>");
                            return false;
                        }

                        if (teamName == null) {
                            player.sendMessage("§cYou're not in a team!");
                            return false;
                        }

                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        if (targetPlayer == null) {
                            player.sendMessage("§cThat player isn't online at the moment!");
                            return false;
                        }

                        if (targetPlayer == player) {
                            player.sendMessage("§cYou can't execute this command on yourself!");
                            return false;
                        }

                        if (ConfigHandler.getInstance().getTeamByUUID(targetPlayer.getUniqueId()) != null) {
                            player.sendMessage("§cThat player is already in a team!");
                            return false;
                        }

                        targetPlayer.sendMessage("§e" + player.getName() + " §ainvited you to join team §e#" + teamName + "§a.");
                        player.sendMessage("§aSuccessfully invited §e" + targetPlayer.getName() + " §ato join your team.");

                        return false;
                    }

                    OfflinePlayer tOPlayer = Bukkit.getPlayer(args[1]);
                    if (args[0].equalsIgnoreCase("ban")) {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: §e/team ban <username>");
                            return false;
                        }

                        if (teamName == null) {
                            player.sendMessage("§cYou're not in a team!");
                            return false;
                        }

                        String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                        if (!tMRole.equals("mod") && !tMRole.equals("owner")) {
                            player.sendMessage("§cYou don't have the permission to execute this command!");
                            return false;
                        }

                        if (tOPlayer == null) {
                            player.sendMessage("§cThat player isn't online at the moment!");
                            return false;
                        }

                        if (tOPlayer == player) {
                            player.sendMessage("§cYou can't execute that command on yourself!");
                            return false;
                        }

                        ConfigHandler.getInstance().banTeamMember(teamName, tOPlayer.getUniqueId());
                        player.sendMessage("§aSuccessfully banned user §e" + tOPlayer.getName() + "§a.");
                        for (Player gop : Bukkit.getOnlinePlayers()) {
                            SubtitleUtils.setSubtitle(gop, player.getUniqueId(), null);
                        }

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("unban")) {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: §e/team unban <username>");
                            return false;
                        }

                        if (teamName == null) {
                            player.sendMessage("§cYou're not in a team!");
                            return false;
                        }

                        String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                        if (!tMRole.equals("mod") && !tMRole.equals("owner")) {
                            player.sendMessage("§cYou don't have the permission to execute this command!");
                            return false;
                        }

                        if (tOPlayer == null) {
                            player.sendMessage("§cThat player isn't online at the moment!");
                            return false;
                        }

                        if (tOPlayer == player) {
                            player.sendMessage("§cYou can't execute that command on yourself!");
                            return false;
                        }

                        ConfigHandler.getInstance().unbanTeamMember(teamName, tOPlayer.getUniqueId());
                        player.sendMessage("§aSuccessfully unbanned user §e" + tOPlayer.getName() + "§a.");

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("promote")) {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: §e/team promote <username>");
                            return false;
                        }

                        if (teamName == null) {
                            player.sendMessage("§cYou're not in a team!");
                            return false;
                        }

                        String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                        if (!tMRole.equals("owner")) {
                            player.sendMessage("§cYou don't have the permission to execute this command!");
                            return false;
                        }

                        if (tOPlayer == null) {
                            player.sendMessage("§cThat player isn't online at the moment!");
                            return false;
                        }

                        if (tOPlayer == player) {
                            player.sendMessage("§cYou can't execute that command on yourself!");
                            return false;
                        }

                        ConfigHandler.getInstance().promoteTeamMember(teamName, tOPlayer.getUniqueId());
                        player.sendMessage("§aSuccessfully promoted user §e" + tOPlayer.getName() + " §ato §eModerator§a.");

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("demote")) {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: §e/team demote <username>");
                            return false;
                        }

                        if (teamName == null) {
                            player.sendMessage("§cYou're not in a team!");
                            return true;
                        }

                        String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                        if (!tMRole.equals("owner")) {
                            player.sendMessage("§cYou don't have the permission to execute this command!");
                            return false;
                        }

                        if (tOPlayer == null) {
                            player.sendMessage("§cThat player isn't online at the moment!");
                            return false;
                        }

                        if (tOPlayer == player) {
                            player.sendMessage("§cYou can't execute that command on yourself!");
                            return false;
                        }

                        ConfigHandler.getInstance().demoteTeamMember(teamName, tOPlayer.getUniqueId());
                        player.sendMessage("§aSuccessfully demoted user §e" + tOPlayer.getName() + "§a.");

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("create")) {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: §e/team create <name>");
                            return false;
                        }

                        if (teamName != null) {
                            player.sendMessage("§cYou're already in a team!");
                            return false;
                        }

                        if (args[1].length() >= 16) {
                            player.sendMessage("§cTeam name can't be longer than 16 characters!");
                            return false;
                        }

                        if (ConfigHandler.getInstance().existsTeam(args[1])) {
                            player.sendMessage("§cTeam name §e#" + args[1] + " §cis unavailable!");
                            return false;
                        }

                        teamName = args[1].replace("&", "");
                        ConfigHandler.getInstance().createTeam(teamName, pUUID);
                        player.sendMessage("§aSuccessfully created team §e#" + teamName + "§a.");
                        for (Player gop : Bukkit.getOnlinePlayers()) {
                            SubtitleUtils.setSubtitle(gop, player.getUniqueId(), teamName);
                        }

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("join")) {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: §e/team join <name>");
                            return false;
                        }

                        if (teamName != null) {
                            player.sendMessage("§cYou're already in a team!");
                            return false;
                        }

                        if (!ConfigHandler.getInstance().existsTeam(args[1].replace("#", ""))) {
                            player.sendMessage("§cTeam §e#" + args[1].replace("#", "") + " §cdoesn't exist!");
                            return false;
                        }

                        String tMRole = ConfigHandler.getInstance().getTeamMemberRole(args[1].replace("#", ""), pUUID);
                        if (tMRole != null) {
                            player.sendMessage("§cYou're currently banned from team §e#" + args[1].replace("#", "") + "§c!" + tMRole);
                            return false;
                        }

                        ConfigHandler.getInstance().addTeamMember(args[1].replace("#", ""), pUUID);
                        player.sendMessage("§aSuccessfully joined team §e#" + args[1].replace("#", "") + "§a.");
                        for (Player gop : Bukkit.getOnlinePlayers()) {
                            SubtitleUtils.setSubtitle(gop, player.getUniqueId(), "§e#" + teamName);
                        }

                        return false;
                    }
                }
            }

            player.sendMessage("§e/team create <name>");
            player.sendMessage("§e/team delete");
            player.sendMessage("§e/team invite <username>");
            player.sendMessage("§e/team join <name>");
            player.sendMessage("§e/team leave");
            player.sendMessage("§e/team ban <username>");
            player.sendMessage("§e/team unban <username>");
            player.sendMessage("§e/team promote <username>");
            player.sendMessage("§e/team demote <username>");
            if (!MinePlace.getInstance().usersWithLM.contains(player.getUniqueId())) {
                player.sendMessage("§7Please use LabyMod (https://labymod.net/download) to make our team system fully functional for your client.");
            }

            return true;
        }

        return false;
    }
}
