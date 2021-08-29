package cc.i9mc.uhc.kit.kits;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantingSetKit extends Kit {
    public EnchantingSetKit() {
        super("EnchantingSet", "附魔套装", new ItemStack(Material.BOOK), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().addItem(new ItemStack(Material.BOOK));
                    player.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 7));
                    break;
                case 1:
                    player.getInventory().addItem(new ItemStack(Material.BOOK, 2));
                    player.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 10));
                    player.getInventory().addItem(new ItemStack(Material.LAPIS_ORE, 12));
                    break;
                case 2:
                    player.getInventory().addItem(new ItemStack(Material.BOOK, 3));
                    player.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 13));
                    player.getInventory().addItem(new ItemStack(Material.LAPIS_ORE, 12));
                    break;
                case 3:
                    player.getInventory().addItem(new ItemStack(Material.BOOK, 4));
                    player.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 15));
                    player.getInventory().addItem(new ItemStack(Material.LAPIS_ORE, 18));
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_PICKAXE).addEnchant(Enchantment.DIG_SPEED, 3).addEnchant(Enchantment.DURABILITY, 1).getItem());
                    break;
                default:
                    break;
            }
        });
    }
}
