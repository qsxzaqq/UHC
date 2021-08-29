package cc.i9mc.uhc.kit;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
public abstract class Kit {
    private String name;
    private String displayName;
    private ItemStack icon;
    private ItemRunnable itemRunnable;

    public Kit(String name, String displayName, ItemStack icon, ItemRunnable itemRunnable) {
        this.name = name;
        this.displayName = displayName;
        this.icon = icon;
        this.itemRunnable = itemRunnable;
    }

    public ItemRunnable getRunnable() {
        return itemRunnable;
    }

    public interface ItemRunnable {
        void run(Player player, int level);
    }
}
