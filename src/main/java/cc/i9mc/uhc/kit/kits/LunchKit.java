package cc.i9mc.uhc.kit.kits;

import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LunchKit extends Kit {
    public LunchKit() {
        super("Lunch", "午餐盒", new ItemStack(Material.APPLE), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 3));
                    player.getInventory().addItem(new ItemStack(Material.APPLE));
                    break;
                case 1:
                    player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 5));
                    player.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, 4));
                    player.getInventory().addItem(new ItemStack(Material.APPLE, 2));
                    break;
                case 2:
                    player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 7));
                    player.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, 8));
                    player.getInventory().addItem(new ItemStack(Material.APPLE, 3));
                    break;
                case 3:
                    player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 9));
                    player.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, 12));
                    player.getInventory().addItem(new ItemStack(Material.MELON, 2));
                    player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 2));
                    player.getInventory().addItem(new ItemStack(Material.APPLE, 3));
                    break;
                default:
                    break;
            }
        });
    }
}
