package cc.i9mc.uhc.kit.kits;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LeatherArmorKit extends Kit {
    public LeatherArmorKit() {
        super("LeatherArmor", "皮革护甲", new ItemStack(Material.LEATHER_CHESTPLATE), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)});
                    break;
                case 1:
                    player.getInventory().setArmorContents(new ItemStack[]{
                            new ItemBuilderUtil().setType(Material.LEATHER_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).getItem(),
                            new ItemBuilderUtil().setType(Material.LEATHER_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).getItem(),
                            new ItemBuilderUtil().setType(Material.LEATHER_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).getItem(),
                            new ItemBuilderUtil().setType(Material.LEATHER_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).getItem()
                    });
                    break;
                case 2:
                    player.getInventory().setArmorContents(new ItemStack[]{
                            new ItemBuilderUtil().setType(Material.LEATHER_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).getItem(),
                            new ItemBuilderUtil().setType(Material.LEATHER_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).getItem(),
                            new ItemBuilderUtil().setType(Material.LEATHER_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).getItem(),
                            new ItemBuilderUtil().setType(Material.LEATHER_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).getItem()
                    });
                    break;
                case 3:
                    player.getInventory().setArmorContents(new ItemStack[]{
                            new ItemBuilderUtil().setType(Material.LEATHER_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).getItem(),
                            new ItemBuilderUtil().setType(Material.LEATHER_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).getItem(),
                            new ItemBuilderUtil().setType(Material.LEATHER_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).getItem(),
                            new ItemBuilderUtil().setType(Material.LEATHER_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).getItem()
                    });
                    break;
                default:
                    break;
            }
        });
    }
}
