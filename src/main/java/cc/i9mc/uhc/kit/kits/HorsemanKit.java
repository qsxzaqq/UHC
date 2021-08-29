package cc.i9mc.uhc.kit.kits;

import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HorsemanKit extends Kit {
    public HorsemanKit() {
        super("Horseman", "骑手", new ItemStack(Material.SADDLE), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().addItem(new ItemStack(Material.LEATHER, 3));
                    player.getInventory().addItem(new ItemStack(Material.WHEAT, 3));
                    break;
                case 1:
                    player.getInventory().addItem(new ItemStack(Material.FEATHER, 3));
                    player.getInventory().addItem(new ItemStack(Material.WHEAT, 5));
                    player.getInventory().addItem(new ItemStack(Material.STRING, 2));
                    break;
                case 2:
                    player.getInventory().addItem(new ItemStack(Material.LEATHER, 9));
                    player.getInventory().addItem(new ItemStack(Material.WHEAT, 7));
                    player.getInventory().addItem(new ItemStack(Material.STRING, 3));
                    break;
                case 3:
                    player.getInventory().addItem(new ItemStack(Material.LEATHER, 12));
                    player.getInventory().addItem(new ItemStack(Material.HAY_BLOCK, 1));
                    player.getInventory().addItem(new ItemStack(Material.STRING, 4));
                    player.getInventory().addItem(new ItemStack(Material.GOLD_BARDING));
                    player.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, (short) 100));
                    break;
                default:
                    break;
            }
        });
    }
}
