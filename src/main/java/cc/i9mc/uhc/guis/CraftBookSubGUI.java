package cc.i9mc.uhc.guis;

import cc.i9mc.gameutils.gui.CustonGUI;
import cc.i9mc.gameutils.gui.GUIAction;
import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.profession.Craft;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CraftBookSubGUI extends CustonGUI {
    public CraftBookSubGUI(Player player, Craft craft, int page) {
        super(player, "§8合成书 " + craft.getDisplayName(), 54);

        setItem(49, new ItemBuilderUtil().setType(Material.ARROW).setDisplayName("§c返回").getItem(), new GUIAction(0, () -> new CraftBookGUI(player, page).open(), false));
        setItem(40, craft.getResult(), new GUIAction(0, () -> {
        }, false));
        int[] next = new int[]{3, 4, 5, 12, 13, 14, 21, 22, 23};

        int j = 0;
        for (int i = 0; i < craft.getShape().length; i++) {
            char[] is = craft.getShape()[i].toCharArray();
            for (char s : is) {
                if (j > next.length) {
                    continue;
                }

                if (s != ' ') {
                    setItem(next[j], new ItemBuilderUtil().setType(craft.getIngredients().get(s).getItemType()).setDurability(craft.getIngredients().get(s).getData()).getItem(), new GUIAction(0, () -> {
                    }, false));
                }

                j++;
            }
        }
    }
}
