package cc.i9mc.uhc.kit.kits;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class StoneGearKit extends Kit {
    public StoneGearKit() {
        super("StoneGear", "石制工具套装", new ItemStack(Material.STONE_PICKAXE), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
                    player.getInventory().addItem(new ItemStack(Material.STONE_SPADE));
                    player.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE));
                    player.getInventory().addItem(new ItemStack(Material.STONE_AXE));
                    break;
                case 1:
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_SWORD).addEnchant(Enchantment.DIG_SPEED, 1).getItem());
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_SPADE).addEnchant(Enchantment.DIG_SPEED, 1).getItem());
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_PICKAXE).addEnchant(Enchantment.DIG_SPEED, 1).getItem());
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_AXE).addEnchant(Enchantment.DIG_SPEED, 1).getItem());
                    break;
                case 2:
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_SWORD).addEnchant(Enchantment.DIG_SPEED, 2).getItem());
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_SPADE).addEnchant(Enchantment.DIG_SPEED, 2).getItem());
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_PICKAXE).addEnchant(Enchantment.DIG_SPEED, 2).getItem());
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_AXE).addEnchant(Enchantment.DIG_SPEED, 2).getItem());
                    break;
                case 3:
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_SWORD).addEnchant(Enchantment.DIG_SPEED, 3).addEnchant(Enchantment.DURABILITY, 1).getItem());
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_SPADE).addEnchant(Enchantment.DIG_SPEED, 3).addEnchant(Enchantment.DURABILITY, 1).getItem());
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_PICKAXE).addEnchant(Enchantment.DIG_SPEED, 3).addEnchant(Enchantment.DURABILITY, 1).getItem());
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_AXE).addEnchant(Enchantment.DIG_SPEED, 3).addEnchant(Enchantment.DURABILITY, 1).getItem());
                    break;
                default:
                    break;
            }
        });
    }
}
