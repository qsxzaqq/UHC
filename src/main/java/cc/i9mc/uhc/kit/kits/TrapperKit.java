package cc.i9mc.uhc.kit.kits;

import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TrapperKit extends Kit {
    public TrapperKit() {
        super("Trapper", "陷阱师", new ItemStack(Material.PISTON_BASE), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().addItem(new ItemStack(Material.PISTON_BASE, 2));
                    player.getInventory().addItem(new ItemStack(Material.PISTON_STICKY_BASE, 2));
                    player.getInventory().addItem(new ItemStack(Material.REDSTONE, 10));
                    player.getInventory().addItem(new ItemStack(Material.LOG, 4));
                    break;
                case 1:
                    player.getInventory().addItem(new ItemStack(Material.PISTON_BASE, 4));
                    player.getInventory().addItem(new ItemStack(Material.PISTON_STICKY_BASE, 4));
                    player.getInventory().addItem(new ItemStack(Material.REDSTONE, 15));
                    player.getInventory().addItem(new ItemStack(Material.LOG, 8));
                    break;
                case 2:
                    player.getInventory().addItem(new ItemStack(Material.PISTON_BASE, 6));
                    player.getInventory().addItem(new ItemStack(Material.PISTON_STICKY_BASE, 6));
                    player.getInventory().addItem(new ItemStack(Material.REDSTONE, 20));
                    player.getInventory().addItem(new ItemStack(Material.LOG, 12));
                    break;
                case 3:
                    player.getInventory().addItem(new ItemStack(Material.PISTON_BASE, 8));
                    player.getInventory().addItem(new ItemStack(Material.PISTON_STICKY_BASE, 8));
                    player.getInventory().addItem(new ItemStack(Material.REDSTONE, 25));
                    player.getInventory().addItem(new ItemStack(Material.LOG, 16));
                    break;
                default:
                    break;
            }
        });
    }
}
