package cc.i9mc.uhc.profession;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class Craft {
    private String name;
    private String displayName;
    private String[] lore;
    private int money;
    private String[] shape;
    private HashMap<Character, MaterialData> ingredients;
    private ItemStack result;
}
