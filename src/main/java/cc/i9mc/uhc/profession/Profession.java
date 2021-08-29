package cc.i9mc.uhc.profession;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public abstract class Profession {
    private String name;
    private String displayName;
    private List<String> lore;
    private ItemStack icon;
    private List<Craft> crafts;
    private List<Perk> perks;


    public Profession(String name, String displayName, ItemStack icon) {
        this.name = name;
        this.displayName = displayName;
        this.icon = icon;
    }
}
