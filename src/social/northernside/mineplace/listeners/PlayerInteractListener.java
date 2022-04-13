package social.northernside.mineplace.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import social.northernside.mineplace.utils.InventoryPages;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInteractListener implements Listener {
    private HashMap<UUID, Long> cooldowns = new HashMap<UUID, Long>();

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item != null) {
            if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                if (item.getType() != Material.ARROW) {
                    Long time = System.currentTimeMillis();
                    Long lastPlace = (cooldowns.get(player.getUniqueId()) == null) ? 0L : cooldowns.get(player.getUniqueId());

                    if (lastPlace + 5 * 1000 > time) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§cWait another " + (5 - (time / 1000 - lastPlace / 1000)) + " seconds!"));
                    } else {
                        cooldowns.remove(player.getUniqueId());
                        if (event.getClickedBlock().getLocation().getY() == 0 && event.getItem().getType().equals(Material.WOOL)) {
                            Block clickedBlock = event.getClickedBlock();
                            clickedBlock.setType(Material.WOOL);

                            BlockState blockState = clickedBlock.getState();
                            blockState.setData(new Wool(((Wool) item.getData()).getColor()));
                            blockState.update();

                            cooldowns.put(player.getUniqueId(), time);
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
                }
            }
        }
    }
}
