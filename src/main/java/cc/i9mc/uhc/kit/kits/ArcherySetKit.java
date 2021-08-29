package cc.i9mc.uhc.kit.kits;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ArcherySetKit extends Kit {
    public ArcherySetKit() {
        super("ArcherySet", "弓箭手套装", new ItemStack(Material.BOW), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().addItem(new ItemStack(Material.STRING, 3));
                    player.getInventory().addItem(new ItemStack(Material.FEATHER, 3));
                    break;
                case 1:
                    player.getInventory().addItem(new ItemStack(Material.STRING, 4));
                    player.getInventory().addItem(new ItemStack(Material.FEATHER, 5));
                    break;
                case 2:
                    player.getInventory().addItem(new ItemStack(Material.STRING, 5));
                    player.getInventory().addItem(new ItemStack(Material.FEATHER, 7));
                    break;
                case 3:
                    player.getInventory().addItem(new ItemStack(Material.STRING, 6));
                    player.getInventory().addItem(new ItemStack(Material.FEATHER, 9));
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_SPADE).addEnchant(Enchantment.DIG_SPEED, 3).addEnchant(Enchantment.DURABILITY, 1).getItem());
                    break;
                default:
                    break;
            }
        });
    }
}
