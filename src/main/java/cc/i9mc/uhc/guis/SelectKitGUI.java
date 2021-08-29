package cc.i9mc.uhc.guis;

import cc.i9mc.gameutils.gui.CustonGUI;
import cc.i9mc.gameutils.gui.GUIAction;
import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.databse.Database;
import cc.i9mc.uhc.databse.PlayerData;
import cc.i9mc.uhc.kit.Kit;
import cc.i9mc.uhc.kit.KitManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class SelectKitGUI extends CustonGUI {
    public SelectKitGUI(Player player) {
        super(player, "§8职业套装选择", 36);

        setItem(31, new ItemBuilderUtil().setType(Material.BARRIER).setDisplayName("§c关闭").getItem(), new GUIAction(0, () -> {
        }, true));
        PlayerData playerData = UHC.getInstance().getCacher().get(player.getName());

        int[] next = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};

        int s = 0;
        for (Kit kit : KitManager.getList()) {
            if (playerData.getKit() != null && kit.getName().equals(playerData.getKit())) {
                setItem(next[s], new ItemBuilderUtil().setItemStack(kit.getIcon().clone()).addEnchant(Enchantment.DAMAGE_ALL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setDisplayName("§a" + kit.getDisplayName()).setLores("§7点击选择" + kit.getDisplayName() + "职业！", " ", "§a已装备!").getItem(), new GUIAction(0, () -> {
                    player.sendMessage("§c你已经装备了这个职业套装.");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 30.0F, 1.0F);
                }, true));
            } else {
                setItem(next[s], new ItemBuilderUtil().setItemStack(kit.getIcon().clone()).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setDisplayName("§a" + kit.getDisplayName()).setLores("§7点击选择" + kit.getDisplayName() + "职业！", " ", "§e点击选择!").getItem(), new GUIAction(0, () -> {
                    playerData.setKit(kit.getName());
                    Database.savePlayerData(playerData);
                    player.sendMessage("§a你成功装备了 " + kit.getDisplayName() + "!");
                    player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1f, 1f);
                }, true));
            }
            s++;
        }
    }
}
