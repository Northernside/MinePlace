package social.northernside.mineplace.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import social.northernside.mineplace.commands.BypassCooldownCommand;
import social.northernside.mineplace.utils.InventoryPages;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class PlayerInteractListener implements Listener {
    public static HashMap<UUID, Long> cooldownMap = new HashMap<>();
    public static HashMap<Location, UUID> lastPlaced = new HashMap<>();

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item != null) {
            Block clickedBlock = event.getClickedBlock();
            if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                if (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET) {
                    event.setUseItemInHand(Event.Result.DENY);
                    return;
                }
                if (item.getType() != Material.ARROW) {
                    Long time = System.currentTimeMillis();
                    Long nextPlace = (cooldownMap.get(player.getUniqueId()) == null) ? 0L : cooldownMap.get(player.getUniqueId());
                    if (nextPlace + 5 * 1000 < time || BypassCooldownCommand.bypassingUsers.contains(player.getUniqueId())) {
                        if (!cooldownMap.containsKey(player.getUniqueId())) {
                            if (event.getClickedBlock().getLocation().getY() == 0 && event.getItem().getType().equals(Material.WOOL)) {
                                clickedBlock.setType(Material.WOOL);

                                BlockState blockState = clickedBlock.getState();
                                blockState.setData(new Wool(((Wool) item.getData()).getColor()));
                                blockState.update();

                                lastPlaced.put(clickedBlock.getLocation(), player.getUniqueId());
                                if (!BypassCooldownCommand.bypassingUsers.contains(player.getUniqueId())) {
                                    cooldownMap.put(player.getUniqueId(), time);
                                    BossBar bossBar = Bukkit.createBossBar("§cPlease wait 5 seconds.", BarColor.BLUE, BarStyle.SEGMENTED_10);
                                    bossBar.addPlayer(player);
                                    bossBar.setProgress(1);
                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        final StringBuilder builder = new StringBuilder();
                                        double cooldown = 5;
                                        int point = 0;

                                        @Override
                                        public void run() {
                                            point++;
                                            cooldown -= 0.5D;
                                            bossBar.setProgress((cooldown / 10) * 2);

                                            if (cooldown % 1 == 0)
                                                bossBar.setTitle("§cPlease wait " + (int) cooldown + " second" + ((int) cooldown != 1 ? "s." : "."));

                                            if (cooldown == 0D) {
                                                bossBar.removePlayer(player);
                                                cooldownMap.remove(player.getUniqueId(), time);
                                                timer.cancel();
                                            }
                                        }
                                    }, 500, 500);
                                }
                            }
                        }
                    }
                }
            }

            String itemDP = item.getItemMeta().getDisplayName();
            if (itemDP != null) {
                Inventory pInv = player.getInventory();
                if (itemDP.equals("§8» §7Next")) {
                    InventoryPages.changePage(1, pInv);
                } else if (itemDP.equals("§8»§r §7Next")) {
                    InventoryPages.changePage(2, pInv);
                } else if (itemDP.equals("§8« §7Back")) {
                    InventoryPages.changePage(0, pInv);
                } else if (itemDP.equals("§8«§r §7Back")) {
                    InventoryPages.changePage(1, pInv);
                } else if (itemDP.equals("§cCancel")) {
                    InventoryPages.changePage(0, pInv);
                } else if (itemDP.equals("§7Censor")) {
                    InventoryPages.changePage(42069, pInv);
                } else if (itemDP.equals("§7Last placed by")) {
                    if (lastPlaced.get(clickedBlock.getLocation()) != null) {
                        player.sendMessage("§e" + clickedBlock.getLocation().getX() + ", " + clickedBlock.getLocation().getZ() + " §awas placed by §e" + Bukkit.getOfflinePlayer(lastPlaced.get(clickedBlock.getLocation())).getName() + " (" + lastPlaced.get(clickedBlock.getLocation()) + ")");
                    } else {
                        player.sendMessage("§cThis block isn't cached :(");
                    }
                } else if (itemDP.equals("§7Position")) {
                    // Too lazy for now
//                    if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
//
//                    } else if (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
//
//                    }
                } else if (itemDP.equals("§aCensor")) {
                    // Too lazy for now
                }
            }
        }
    }
}
