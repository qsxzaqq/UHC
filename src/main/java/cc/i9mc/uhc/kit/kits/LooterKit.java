package cc.i9mc.uhc.kit.kits;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LooterKit extends Kit {
    public LooterKit() {
        super("Looter", "掠夺者", new ItemStack(Material.BONE), (player, level) -> {
            switch (level) {
                case 0:
                    player.getInventory().addItem(new ItemStack(Material.BONE));
                    player.getInventory().addItem(new ItemStack(Material.SLIME_BALL));
                    break;
                case 1:
                    player.getInventory().addItem(new ItemStack(Material.BONE, 2));
                    player.getInventory().addItem(new ItemStack(Material.SLIME_BALL, 2));
                    player.getInventory().addItem(new ItemStack(Material.SULPHUR));
                    break;
                case 2:
                    player.getInventory().addItem(new ItemStack(Material.BONE, 3));
                    player.getInventory().addItem(new ItemStack(Material.SLIME_BALL, 2));
                    player.getInventory().addItem(new ItemStack(Material.SULPHUR));
                    player.getInventory().addItem(new ItemStack(Material.FERMENTED_SPIDER_EYE));
                    break;
                case 3:
                    player.getInventory().addItem(new ItemStack(Material.BONE, 3));
                    player.getInventory().addItem(new ItemStack(Material.SLIME_BALL, 3));
                    player.getInventory().addItem(new ItemStack(Material.SULPHUR, 2));
                    player.getInventory().addItem(new ItemStack(Material.FERMENTED_SPIDER_EYE, 2));
                    player.getInventory().addItem(new ItemBuilderUtil().setType(Material.STONE_SWORD).addEnchant(Enchantment.LOOT_BONUS_MOBS, 1).getItem());
                    break;
                default:
                    break;
            }
        });
    }
}
