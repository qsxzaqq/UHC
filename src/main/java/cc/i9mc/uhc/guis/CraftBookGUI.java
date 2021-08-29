package cc.i9mc.uhc.guis;

import cc.i9mc.gameutils.gui.CustonGUI;
import cc.i9mc.gameutils.gui.GUIAction;
import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.profession.Craft;
import cc.i9mc.uhc.profession.Profession;
import cc.i9mc.uhc.profession.ProfessionManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CraftBookGUI extends CustonGUI {
    public CraftBookGUI(Player player, int page) {
        super(player, "§8合成书 第" + page + "页", 54);

        setItem(49, new ItemBuilderUtil().setType(Material.BARRIER).setDisplayName("§c关闭").getItem(), new GUIAction(0, () -> {
        }, true));
        int[] next = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};

        int startSize;
        if (page == 1) {
            startSize = 0;
        } else {
            startSize = (page - 1) * next.length;

            setItem(45, new ItemBuilderUtil().setType(Material.ARROW).setDisplayName("§a上一页").setLores("§e页码: " + (page - 1)).getItem(), new GUIAction(0, () -> new CraftBookGUI(player, page - 1).open(), false));
        }

        int s = 1;
        int i = 0;
        for (Profession profession : ProfessionManager.getProfessions()) {
            for (Craft craft : profession.getCrafts()) {
                if (i < startSize) {
                    i++;
                    continue;
                }

                if (s > next.length) {
                    continue;
                }

                setItem(next[(s - 1)], new ItemBuilderUtil().setItemStack(craft.getResult().clone()).setDisplayName("§a" + profession.getDisplayName() + "合成:" + craft.getDisplayName()).addLores(" ", "§7类别:", "§7最大合成次数: §b", " ", player.hasPermission("uhc.profession." + profession.getName() + "." + craft.getDisplayName()) ? "§a点击查看配方" : "§c你还没有解锁这个配方").getItem(), new GUIAction(0, () -> {
                    new CraftBookSubGUI(player, craft, page).open();
                }, false));
                s++;
            }
        }

        if ((ProfessionManager.getCrafts().size() - (s + ((page - 1) * next.length))) > 1) {
            setItem(53, new ItemBuilderUtil().setType(Material.ARROW).setDisplayName("§a下一页").setLores("§e页码: " + (page + 1)).getItem(), new GUIAction(0, () -> new CraftBookGUI(player, page + 1).open(), false));
        }
    }
}
