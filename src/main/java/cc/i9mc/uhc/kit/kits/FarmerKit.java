package cc.i9mc.uhc.kit.kits;

import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FarmerKit extends Kit {
    public FarmerKit() {
        super("Farmer", "农夫", new ItemStack(Material.SEEDS), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().addItem(new ItemStack(Material.STONE_HOE));
                    player.getInventory().addItem(new ItemStack(Material.MELON_SEEDS));
                    player.getInventory().addItem(new ItemStack(Material.INK_SACK, (short) 15));
                    break;
                case 1:
                    player.getInventory().addItem(new ItemStack(Material.GOLD_HOE));
                    player.getInventory().addItem(new ItemStack(Material.MELON));
                    player.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, 2));
                    player.getInventory().addItem(new ItemStack(Material.INK_SACK, 2, (short) 15));
                    break;
                case 2:
                    player.getInventory().addItem(new ItemStack(Material.IRON_HOE));
                    player.getInventory().addItem(new ItemStack(Material.MELON, 2));
                    player.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, 2));
                    player.getInventory().addItem(new ItemStack(Material.INK_SACK, 3, (short) 15));
                    break;
                case 3:
                    player.getInventory().addItem(new ItemStack(Material.IRON_HOE));
                    player.getInventory().addItem(new ItemStack(Material.MELON, 3));
                    player.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, 3));
                    player.getInventory().addItem(new ItemStack(Material.INK_SACK, 4, (short) 15));
                    break;
                default:
                    break;
            }
        });
    }
}
