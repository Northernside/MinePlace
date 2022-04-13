package social.northernside.mineplace.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import social.northernside.mineplace.utils.ConfigHandler;

import java.util.UUID;

public class TeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            String teamName = ConfigHandler.getInstance().getTeamByUUID(player.getUniqueId());
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (!ConfigHandler.getInstance().existsTeam(teamName)) {
                        if (!ConfigHandler.getInstance().existsTeam(args[1])) {
                            if (args[1].length() <= 16) {
                                ConfigHandler.getInstance().createTeam(args[1], player.getUniqueId());
                                player.sendMessage("§aSuccessfully created team §e#" + args[1]);
                            }
                        } else {
                            player.sendMessage("§cTeam name §e#" + args[1] + " §cis unavailable.");
                        }
                    } else {
                        player.sendMessage("§cYou already own a team. Type §e/team delete §cif you want to delete your team.");
                    }
                } else if (args[0].equalsIgnoreCase("delete") && args.length == 1) {
                    player.sendMessage("§4Danger! §cType §e/team delete confirm §cif you really want to delete your team.");
                } else if (args[0].equalsIgnoreCase("delete") && args[1].equalsIgnoreCase("confirm")) {
                    if (ConfigHandler.getInstance().existsTeam(teamName)) {
                        if (ConfigHandler.getInstance().getTeamMemberRole(teamName, player.getUniqueId()).equalsIgnoreCase("owner")) {
                            ConfigHandler.getInstance().deleteTeam(teamName);
                            player.sendMessage("§cYou deleted your team.");
                        }  else {
                            player.sendMessage("§cYou're not the team owner." + ConfigHandler.getInstance().getTeamMemberRole(teamName, player.getUniqueId()));
                        }
                    } else {
                        player.sendMessage("§cYou're not in a team.");
                    }
                } else if (args.length >= 2 && args[0].equalsIgnoreCase("invite")) {
                    if (ConfigHandler.getInstance().existsTeam(teamName)) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            if (Bukkit.getPlayer(args[1]) != player) {
                                Player targetPlayer = Bukkit.getPlayer(args[1]);
                                targetPlayer.sendMessage("§e" + player.getName() + " §ainvited you to join team §e#" + teamName + "\n§aJoin them via §e/team join #" + teamName);
                            } else {
                                player.sendMessage("§cYou can't invite yourself.");
                            }
                        } else {
                            player.sendMessage("§cThat player doesn't exist or isn't online at the moment.");
                        }
                    } else {
                        player.sendMessage("§cYou're not in a team.");
                    }
                } else if (args.length >= 2 && args[0].equalsIgnoreCase("join")) {
                    if (ConfigHandler.getInstance().existsTeam(args[1].replace("#", ""))) {
                        if (ConfigHandler.getInstance().getTeamByUUID(player.getUniqueId()) == null) {
                            ConfigHandler.getInstance().addTeamMember(args[1], player.getUniqueId());
                            player.sendMessage("§aYou've joined team §e#" + args[1]);
                        } else {
                            player.sendMessage("§cYou're already in a team. Leave your current team via §e/team leave");
                        }
                    } else {
                        player.sendMessage("§cThis team does not exist.");
                    }
                } else if (args[0].equalsIgnoreCase("leave")) {
                    if (ConfigHandler.getInstance().existsTeam(teamName)) {
                        if (!ConfigHandler.getInstance().getTeamMemberRole(teamName, player.getUniqueId()).equals("owner")) {
                            ConfigHandler.getInstance().removeTeamMember(teamName, player.getUniqueId());
                            player.sendMessage("§cYou left your team.");
                        } else {
                            player.sendMessage("§cYou can't leave your team. But you can delete it via §e/team delete");
                        }
                    } else {
                        player.sendMessage("§cYou're not in a team.");
                    }
                } else if (args[0].equalsIgnoreCase("ban")) {
                    if (ConfigHandler.getInstance().existsTeam(teamName)) {
                        String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, player.getUniqueId());
                        if (tMRole.equals("owner") || tMRole.equals("mod")) {
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

                            if (offlinePlayer.hasPlayedBefore()) {
                                UUID oPUUID = offlinePlayer.getUniqueId();
                                ConfigHandler.getInstance().banTeamMember(teamName, oPUUID);
                                player.sendMessage("§aSuccessfully banned §e" + args[1]);
                            } else {
                                player.sendMessage("§cThat player never played on MinePlace.");
                            }
                        } else {
                            player.sendMessage("§cYou're not the owner of your team.");
                        }
                    } else {
                        player.sendMessage("§cYou're not in a team.");
                    }
                } else if (args[0].equalsIgnoreCase("unban")) {
                    if (ConfigHandler.getInstance().existsTeam(teamName)) {
                        String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, player.getUniqueId());
                        if (tMRole.equals("owner") || tMRole.equals("mod")) {
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

                            if (offlinePlayer.hasPlayedBefore()) {
                                UUID oPUUID = offlinePlayer.getUniqueId();
                                ConfigHandler.getInstance().unbanTeamMember(teamName, oPUUID);
                                player.sendMessage("§aSuccessfully unbanned §e" + args[1]);
                            } else {
                                player.sendMessage("§cThat player never played on MinePlace.");
                            }
                        } else {
                            player.sendMessage("§cYou're not the owner of your team.");
                        }
                    } else {
                        player.sendMessage("§cYou're not in a team.");
                    }
                } else if (args[0].equalsIgnoreCase("promote")) {
                    if (ConfigHandler.getInstance().existsTeam(teamName)) {
                        if (ConfigHandler.getInstance().getTeamMemberRole(teamName, player.getUniqueId()).equals("owner")) {
                            Player targetPlayer = Bukkit.getPlayer(args[1]);

                            if (targetPlayer != null) {
                                if (targetPlayer != player) {
                                    if (ConfigHandler.getInstance().isTeamMember(teamName, targetPlayer.getUniqueId())) {
                                        ConfigHandler.getInstance().promoteTeamMember(teamName, targetPlayer.getUniqueId());
                                        player.sendMessage("§aSuccessfully promoted §e" + targetPlayer.getName() + " §ato moderator.");
                                    } else {
                                        player.sendMessage("§e" + targetPlayer.getName() + " §cis not a member of your team.");
                                    }
                                } else {
                                    player.sendMessage("§cYou can't promote yourself.");
                                }
                            } else {
                                player.sendMessage("§cThat doesn't exist or isn't online at the moment.");
                            }
                        } else {
                            player.sendMessage("§cYou're not the owner of your team.");
                        }
                    } else {
                        player.sendMessage("§cYou're not in a team.");
                    }
                } else if (args[0].equalsIgnoreCase("demote")) {
                    if (ConfigHandler.getInstance().existsTeam(teamName)) {
                        if (ConfigHandler.getInstance().getTeamMemberRole(teamName, player.getUniqueId()).equals("owner")) {
                            Player targetPlayer = Bukkit.getPlayer(args[1]);

                            if (targetPlayer != null) {
                                if (targetPlayer != player) {
                                    if (ConfigHandler.getInstance().isTeamMember(teamName, targetPlayer.getUniqueId())) {
                                        ConfigHandler.getInstance().demoteTeamMember(teamName, targetPlayer.getUniqueId());
                                        player.sendMessage("§aSuccessfully demoted §e" + targetPlayer.getName());
                                    } else {
                                        player.sendMessage("§e" + targetPlayer.getName() + " §cis not a member of your team.");
                                    }
                                } else {
                                    player.sendMessage("§cYou can't demote yourself.");
                                }
                            } else {
                                player.sendMessage("§cThat doesn't exist or isn't online at the moment.");
                            }
                        } else {
                            player.sendMessage("§cYou're not the owner of your team.");
                        }
                    } else {
                        player.sendMessage("§cYou're not in a team.");
                    }
                }
            } else {
                player.sendMessage("§e/team create <name>");
                player.sendMessage("§e/team delete");
                player.sendMessage("§e/team invite <username>");
                player.sendMessage("§e/team join <name>");
                player.sendMessage("§e/team leave");
                player.sendMessage("§e/team ban <username>");
                player.sendMessage("§e/team unban <username>");
                player.sendMessage("§e/team promote <username>");
                player.sendMessage("§e/team demote <username>");
            }

            return true;
        }

        return false;
    }
}