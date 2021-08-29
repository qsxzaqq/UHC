package cc.i9mc.uhc.kit.kits;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EcologistKit extends Kit {
    public EcologistKit() {
        super("Ecologist", "生态学家", new ItemStack(Material.LOG), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().addItem(new ItemStack(Material.VINE, 12));
                    player.getInventory().addItem(new ItemStack(Material.WATER_LILY, 8));
                    break;
                case 1:
                    player.getInventory().addItem(new ItemStack(Material.VINE, 15));
                    player.getInventory().addItem(new ItemStack(Material.WATER_LILY, 16));
                    player.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, 4));
                    break;
                case 2:
                    player.getInventory().addItem(new ItemStack(Material.VINE, 18));
                    player.getInventory().addItem(new ItemStack(Material.WATER_LILY, 32));
                    player.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, 8));
                    break;
                case 3:
                    player.getInventory().addItem(new ItemStack(Material.VINE, 21));
                    player.getInventory().addItem(new ItemStack(Material.WATER_LILY, 64));
                    player.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, 12));
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_PICKAXE).addEnchant(Enchantment.DIG_SPEED, 3).addEnchant(Enchantment.DURABILITY, 1).getItem());
                    break;
                default:
                    break;
            }
        });
    }
}
