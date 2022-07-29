package de.northernsi.mineplace.listeners;

import de.northernsi.mineplace.commands.BypassCooldownCommand;
import de.northernsi.mineplace.utils.CensorArea;
import de.northernsi.mineplace.utils.InventoryPages;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class PlayerInteractListener implements Listener {
    public static HashMap<UUID, Long> cooldownMap = new HashMap<>();
    public static HashMap<Location, UUID> lastPlaced = new HashMap<>();

    private static HashMap<UUID, CensorArea> censorLocs = new HashMap<>();

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item == null)
            return;

        Block clickedBlock = event.getClickedBlock();
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
            if (clickedBlock.getLocation().getY() == 0 && event.getItem().getType().equals(Material.WOOL)) {
                Long time = System.currentTimeMillis();
                Long nextPlace = (cooldownMap.get(player.getUniqueId()) == null) ? 0L : cooldownMap.get(player.getUniqueId());
                if (nextPlace + 5 * 1000 >= time && !BypassCooldownCommand.bypassingUsers.contains(player.getUniqueId()))
                    return;

                if (cooldownMap.containsKey(player.getUniqueId()))
                    return;

                Block block = Bukkit.getWorld("world").getBlockAt(clickedBlock.getLocation());
                block.setType(Material.WOOL);
                BlockState blockState = block.getState();
                blockState.setData(new Wool(((Wool) item.getData()).getColor()));
                blockState.update();
                BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent(clickedBlock, blockState, block, item, player,
                        true);
                Bukkit.getPluginManager().callEvent(blockPlaceEvent);

                lastPlaced.put(clickedBlock.getLocation(), player.getUniqueId());
                if (BypassCooldownCommand.bypassingUsers.contains(player.getUniqueId()))
                    return;

                cooldownMap.put(player.getUniqueId(), time);
                BossBar bossBar = Bukkit.createBossBar("§cPlease wait 5 seconds.", BarColor.BLUE, BarStyle.SEGMENTED_10);
                bossBar.addPlayer(player);
                bossBar.setProgress(1);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
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
                return;
            }
        }

        String itemDP = item.getItemMeta().getDisplayName();
        if (itemDP == null)
            return;

        Inventory pInv = player.getInventory();
        switch (itemDP) {
            case "§8» §7Next":
                InventoryPages.changePage(1, pInv);
                break;
            case "§8»§r §7Next":
                InventoryPages.changePage(2, pInv);
                break;
            case "§8« §7Back":
                InventoryPages.changePage(0, pInv);
                break;
            case "§8«§r §7Back":
                InventoryPages.changePage(1, pInv);
                break;
            case "§cCancel":
                InventoryPages.changePage(0, pInv);
                break;
            case "§7Censor":
                InventoryPages.changePage(42069, pInv);
                break;
            case "§7Last placed by":
                if (clickedBlock.getLocation() == null || lastPlaced.get(clickedBlock.getLocation()) == null) {
                    player.sendMessage("§cThis block isn't cached :(");
                    return;
                }

                player.sendMessage("§e" + clickedBlock.getLocation().getBlockX() + ", " + clickedBlock.getLocation()
                        .getBlockZ() + " §awas placed by §e" + Bukkit.getOfflinePlayer(lastPlaced.get(clickedBlock
                        .getLocation())).getName() + " (" + lastPlaced.get(clickedBlock.getLocation()) + ")");
                break;
            case "§7Position":
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    if (!censorLocs.containsKey(player.getUniqueId()))
                        censorLocs.put(player.getUniqueId(), new CensorArea());
                    censorLocs.get(player.getUniqueId()).setLoc1(clickedBlock.getLocation());
                    player.sendMessage("§eFirst §aposition set!");
                    return;
                } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (!censorLocs.containsKey(player.getUniqueId()))
                        censorLocs.put(player.getUniqueId(), new CensorArea());
                    censorLocs.get(player.getUniqueId()).setLoc2(clickedBlock.getLocation());
                    player.sendMessage("§eSecond §aposition set!");
                    return;
                }

                break;
            case "§aCensor":
                if (!censorLocs.containsKey(player.getUniqueId())) {
                    player.sendMessage("§cYou must select both blocks first.");
                }

                if (!censorLocs.get(player.getUniqueId()).isReady()) {
                    player.sendMessage("§cYou must select both blocks first.");
                }

                Vector max = Vector.getMaximum(censorLocs.get(player.getUniqueId()).getLoc1().toVector(),
                        censorLocs.get(player.getUniqueId()).getLoc2().toVector());
                Vector min = Vector.getMinimum(censorLocs.get(player.getUniqueId()).getLoc1().toVector(),
                        censorLocs.get(player.getUniqueId()).getLoc2().toVector());
                HashMap<OfflinePlayer, Integer> playerBlocksCount = new HashMap<>();
                for (int i = min.getBlockX(); i <= max.getBlockX(); i++) {
                    for (int k = min.getBlockZ(); k <= max.getBlockZ(); k++) {
                        Block block = censorLocs.get(player.getUniqueId()).getWorld().getBlockAt(i, 0, k);
                        block.setType(Material.WOOL);
                        OfflinePlayer blockOwner = Bukkit.getOfflinePlayer(lastPlaced.get(block.getLocation()) == null
                                ? UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff") : lastPlaced.get(block
                                .getLocation()));
                        playerBlocksCount.put(blockOwner, (playerBlocksCount.get(blockOwner) == null) ? 1
                                : playerBlocksCount.get(blockOwner) + 1);
                        lastPlaced.remove(block.getLocation());
                    }
                }

                double maxZ, maxX;
                maxZ = (Math.max(censorLocs.get(player.getUniqueId()).getLoc1().getZ(), censorLocs.get(
                        player.getUniqueId()).getLoc2().getZ()));
                maxX = (Math.max(censorLocs.get(player.getUniqueId()).getLoc1().getX(), censorLocs.get(
                        player.getUniqueId()).getLoc2().getX()));
                int blockAmount = (int) (maxZ + 1 - min.getBlockZ()) * (int) (maxX + 1 - min.getBlockX());

                player.sendMessage("§c§lCensored! §6Here are some statics about the censored area:");

                for (OfflinePlayer p : playerBlocksCount.keySet()) {
                    player.sendMessage("§e" + (p.getName() != null ? p.getName() : "Unknown users") + " §aha" +
                            (p.getName() != null ? "s" : "ve") + " placed §e" + playerBlocksCount.get(p) + " (" +
                            Math.round(((double) playerBlocksCount.get(p) / (double) blockAmount) * 100) + "%) §ablocks.");
                }

                censorLocs.remove(player.getUniqueId());
                // TODO: Log to Discord
                break;
        }
    }
}