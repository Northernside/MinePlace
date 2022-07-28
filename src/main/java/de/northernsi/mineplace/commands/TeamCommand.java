package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.utils.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.northernsi.mineplace.MinePlace;

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
                    if (teamName != null) {
                        if (ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID).equals("owner")) {
                            ConfigHandler.getInstance().deleteTeam(teamName, pUUID);
                            player.kickPlayer("§aSuccessfully deleted your team.\n\n§ePlease rejoin.");
                        }
                    } else {
                        player.sendMessage("§cYou're not in a team!");
                    }

                    return false;
                }

                if (args[0].equalsIgnoreCase("leave")) {
                    if (teamName != null) {
                        String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                        if (!tMRole.equals("owner")) {
                            ConfigHandler.getInstance().removeTeamMember(teamName, pUUID);
                            player.kickPlayer("§aYou left team §e#" + teamName + "§a.\n\n§ePlease rejoin.");
                        } else {
                            player.sendMessage("§cYou can't leave your own team! Delete it instead via §e/team delete§c.");
                        }
                    } else {
                        player.sendMessage("§cYou're not in a team!");
                    }

                    return false;
                }

                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("invite")) {
                        if (teamName != null) {
                            Player targetPlayer = Bukkit.getPlayer(args[1]);
                            if (targetPlayer != null) {
                                if (targetPlayer != player) {
                                    targetPlayer.sendMessage("§e" + player.getName() + " §ainvited you to join team §e#" + teamName + "§a.");
                                    player.sendMessage("§aSuccessfully invited §e" + targetPlayer.getName() + " §ato join your team.");
                                } else {
                                    player.sendMessage("§cYou can't execute this command on yourself!");
                                }
                            } else {
                                player.sendMessage("§cThat player isn't online at the moment!");
                            }
                        } else {
                            player.sendMessage("§cYou're not in a team!");
                        }

                        return false;
                    }

                    OfflinePlayer tOPlayer = Bukkit.getPlayer(args[1]);
                    if (args[0].equalsIgnoreCase("ban")) {
                        if (teamName != null) {
                            String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                            if (tMRole.equals("mod") || tMRole.equals("owner")) {
                                if (tOPlayer != null) {
                                    if (tOPlayer != player) {
                                        ConfigHandler.getInstance().banTeamMember(teamName, tOPlayer.getUniqueId());
                                        player.sendMessage("§aSuccessfully banned user §e" + tOPlayer.getName() + "§a.");
                                    } else {
                                        player.sendMessage("§cYou can't execute that command on yourself!");
                                    }
                                } else {
                                    player.sendMessage("§cThat player doesn't exist!");
                                }
                            }
                        } else {
                            player.sendMessage("§cYou're not in a team!");
                        }

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("unban")) {
                        if (teamName != null) {
                            String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                            if (tMRole.equals("mod") || tMRole.equals("owner")) {
                                if (tOPlayer != null) {
                                    if (tOPlayer != player) {
                                        ConfigHandler.getInstance().unbanTeamMember(teamName, tOPlayer.getUniqueId());
                                        player.sendMessage("§aSuccessfully unbanned user §e" + tOPlayer.getName() + "§a.");
                                    } else {
                                        player.sendMessage("§cYou can't execute that command on yourself!");
                                    }
                                } else {
                                    player.sendMessage("§cThat player doesn't exist!");
                                }
                            }
                        } else {
                            player.sendMessage("§cYou're not in a team!");
                        }

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("promote")) {
                        if (teamName != null) {
                            String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                            if (tMRole.equals("owner")) {
                                if (tOPlayer != null) {
                                    if (tOPlayer != player) {
                                        ConfigHandler.getInstance().promoteTeamMember(teamName, tOPlayer.getUniqueId());
                                        player.sendMessage("§aSuccessfully promoted user §e" + tOPlayer.getName() + " §ato §eModerator§a.");
                                    } else {
                                        player.sendMessage("§cYou can't execute that command on yourself!");
                                    }
                                } else {
                                    player.sendMessage("§cThat player doesn't exist!");
                                }
                            }
                        } else {
                            player.sendMessage("§cYou're not in a team!");
                        }

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("demote")) {
                        if (teamName != null) {
                            String tMRole = ConfigHandler.getInstance().getTeamMemberRole(teamName, pUUID);
                            if (tMRole.equals("owner")) {
                                if (tOPlayer != null) {
                                    if (tOPlayer != player) {
                                        ConfigHandler.getInstance().demoteTeamMember(teamName, tOPlayer.getUniqueId());
                                        player.sendMessage("§aSuccessfully demoted user §e" + tOPlayer.getName() + "§a.");
                                    } else {
                                        player.sendMessage("§cYou can't execute that command on yourself!");
                                    }
                                } else {
                                    player.sendMessage("§cThat player doesn't exist!");
                                }
                            }
                        } else {
                            player.sendMessage("§cYou're not in a team!");
                        }

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("create")) {
                        if (teamName == null) {
                            if (args[1].length() <= 16) {
                                if (!ConfigHandler.getInstance().existsTeam(args[1])) {
                                    ConfigHandler.getInstance().createTeam(args[1], pUUID);
                                    player.sendMessage("§aSuccessfully created team §e#" + args[1] + "§a.");
                                    player.kickPlayer("§aSuccessfully created team §e#" + args[1] + "§a.\n\n§ePlease rejoin.");
                                } else {
                                    player.sendMessage("§cTeam name §e#" + args[1] + " §cis unavailable!");
                                }
                            } else {
                                player.sendMessage("§cTeam name can't be longer than 16 characters!");
                            }
                        } else {
                            player.sendMessage("§cYou're already in a team!");
                        }

                        return false;
                    }

                    if (args[0].equalsIgnoreCase("join")) {
                        if (teamName == null) {
                            if (ConfigHandler.getInstance().existsTeam(args[1].replace("#", ""))) {
                                String tMRole = ConfigHandler.getInstance().getTeamMemberRole(args[1].replace("#", ""), pUUID);
                                if (tMRole == null) {
                                    ConfigHandler.getInstance().addTeamMember(args[1].replace("#", ""), pUUID);
                                    player.kickPlayer("§aSuccessfully joined team §e#" + args[1].replace("#", "") + "§a.\n\n§ePlease rejoin.");
                                } else {
                                    player.sendMessage("§cYou're currently banned from team §e#" + args[1].replace("#", "") + "§c!" + tMRole);
                                }
                            } else {
                                player.sendMessage("§cTeam §e#" + args[1].replace("#", "") + " §cdoesn't exist!");
                            }
                        } else {
                            player.sendMessage("§cYou're already in a team!");
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
