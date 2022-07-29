package de.northernsi.mineplace.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

public class InventoryPages {
    public static void changePage(int page, Inventory pInv) {
        ItemStack cancelBtn = new ItemStack(Material.BARRIER, 1);
        ItemMeta cBMeta = cancelBtn.getItemMeta();
        cBMeta.setDisplayName("§cCancel");
        cancelBtn.setItemMeta(cBMeta);
        pInv.clear();

        switch (page) {
            case 0:
                ItemStack nArrowP0 = new ItemStack(Material.ARROW, 1);
                ItemMeta nAMetaP0 = nArrowP0.getItemMeta();
                nAMetaP0.setDisplayName("§8» §7Next");
                nArrowP0.setItemMeta(nAMetaP0);

                pInv.setItem(0, new Wool(DyeColor.WHITE).toItemStack(1));
                pInv.setItem(1, new Wool(DyeColor.SILVER).toItemStack(1));
                pInv.setItem(2, new Wool(DyeColor.GRAY).toItemStack(1));
                pInv.setItem(3, new Wool(DyeColor.BLACK).toItemStack(1));
                pInv.setItem(4, new Wool(DyeColor.RED).toItemStack(1));
                pInv.setItem(5, new Wool(DyeColor.BROWN).toItemStack(1));
                pInv.setItem(6, new Wool(DyeColor.ORANGE).toItemStack(1));
                pInv.setItem(7, new Wool(DyeColor.YELLOW).toItemStack(1));
                pInv.setItem(8, nArrowP0);
                break;
            case 1:
                ItemStack bArrowP1 = new ItemStack(Material.ARROW, 1);
                ItemMeta bAMetaP1 = bArrowP1.getItemMeta();
                bAMetaP1.setDisplayName("§8« §7Back");
                bArrowP1.setItemMeta(bAMetaP1);

                ItemStack nArrowP1 = new ItemStack(Material.ARROW, 1);
                ItemMeta nAMetaP1 = nArrowP1.getItemMeta();
                nAMetaP1.setDisplayName("§8»§r §7Next");
                nArrowP1.setItemMeta(nAMetaP1);

                pInv.setItem(0, bArrowP1);
                pInv.setItem(1, new Wool(DyeColor.LIME).toItemStack(1));
                pInv.setItem(2, new Wool(DyeColor.GREEN).toItemStack(1));
                pInv.setItem(3, new Wool(DyeColor.LIGHT_BLUE).toItemStack(1));
                pInv.setItem(4, new Wool(DyeColor.CYAN).toItemStack(1));
                pInv.setItem(5, new Wool(DyeColor.BLUE).toItemStack(1));
                pInv.setItem(6, new Wool(DyeColor.PURPLE).toItemStack(1));
                pInv.setItem(7, new Wool(DyeColor.MAGENTA).toItemStack(1));
                pInv.setItem(8, nArrowP1);
                break;
            case 2:
                ItemStack bArrowP2 = new ItemStack(Material.ARROW, 1);
                ItemMeta bAMetaP2 = bArrowP2.getItemMeta();
                bAMetaP2.setDisplayName("§8«§r §7Back");
                bArrowP2.setItemMeta(bAMetaP2);

                pInv.setItem(0, bArrowP2);
                pInv.setItem(1, new Wool(DyeColor.PINK).toItemStack(1));
                break;
            case 666:
                ItemStack censorTool = new ItemStack(Material.COAL_BLOCK, 1);
                ItemMeta cTMeta = censorTool.getItemMeta();
                cTMeta.setDisplayName("§7Censor");
                censorTool.setItemMeta(cTMeta);

                ItemStack lPBTool = new ItemStack(Material.NAME_TAG, 1);
                ItemMeta lPBTMeta = lPBTool.getItemMeta();
                lPBTMeta.setDisplayName("§7Last placed by");
                lPBTool.setItemMeta(lPBTMeta);

                pInv.setItem(0, cancelBtn);
                pInv.setItem(3, censorTool);
                pInv.setItem(5, lPBTool);
                break;
            case 42069:
                ItemStack posItem = new ItemStack(Material.BLAZE_ROD, 1);
                ItemMeta pIMeta = posItem.getItemMeta();
                pIMeta.setDisplayName("§7Position");
                posItem.setItemMeta(pIMeta);

                ItemStack censorBtn = new ItemStack(Material.LAVA_BUCKET, 1);
                ItemMeta ceBMeta = censorBtn.getItemMeta();
                ceBMeta.setDisplayName("§aCensor");
                censorBtn.setItemMeta(ceBMeta);

                pInv.setItem(0, cancelBtn);
                pInv.setItem(4, posItem);
                pInv.setItem(8, censorBtn);
                break;
        }
    }
}
