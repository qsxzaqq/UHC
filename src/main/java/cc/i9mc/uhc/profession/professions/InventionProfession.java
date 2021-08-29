package cc.i9mc.uhc.profession.professions;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.profession.Craft;
import cc.i9mc.uhc.profession.Perk;
import cc.i9mc.uhc.profession.Profession;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventionProfession extends Profession {
    public InventionProfession() {
        super("Invention", "发明", new ItemStack(Material.REDSTONE_COMPARATOR));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('G', new MaterialData(Material.GOLD_ORE));
        ingredients.put('C', new MaterialData(Material.COAL));
        crafts.add(new Craft("Invention", "冶金", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 250, new String[]{"GGG", "GCG", "GGG"}, ingredients, new ItemBuilderUtil().setType(Material.GOLD_INGOT).setAmount(10).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('S', new MaterialData(Material.SAPLING));
        ingredients.put('E', new MaterialData(Material.SEEDS));
        ingredients.put('U', new MaterialData(Material.SUGAR));
        crafts.add(new Craft("Invention", "蔗糖提取术", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 5000, new String[]{"   ", " S ", "EUE"}, ingredients, new ItemBuilderUtil().setType(Material.SUGAR_CANE).setAmount(4).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('1', new MaterialData(Material.DIAMOND_HELMET));
        ingredients.put('2', new MaterialData(Material.DIAMOND_CHESTPLATE));
        ingredients.put('3', new MaterialData(Material.DIAMOND_LEGGINGS));
        ingredients.put('4', new MaterialData(Material.DIAMOND_BOOTS));
        crafts.add(new Craft("Invention", "聚变之甲", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 50000, new String[]{"123", "44 ", "   "}, ingredients, new ItemBuilderUtil().setType(Material.DIAMOND_CHESTPLATE).setDisplayName("§a聚变之甲").getItem()));

        ingredients = new HashMap<>();
        ingredients.put('T', new MaterialData(Material.STICK));
        ingredients.put('L', new MaterialData(Material.LEATHER));
        ingredients.put('H', new MaterialData(Material.CHEST));
        crafts.add(new Craft("Invention", "背包", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 5000, new String[]{"TLT", "THT", "TLT"}, ingredients, new ItemBuilderUtil().setType(Material.CHEST).setDisplayName("§a背包").getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Convenience-I", "便捷-I", new String[]{"§c5%§7几率获取双倍沙子，燧石和黑曜石", "挖掘上述方块时§7效率等级+1级"}, 500));
        perks.add(new Perk(-1, "Convenience-VI", "便捷-VI", new String[]{"§7增加§c5%§7几率获取双倍沙子,燧石和黑曜石."}, 40000));

        perks.add(new Perk(0, "Convenience-A-II", "便捷A-II", new String[]{"§7增加§c5%§7几率获取双倍沙子,燧石和黑曜石."}, 1000));
        perks.add(new Perk(0, "Convenience-A-III", "便捷A-III", new String[]{"§7增加§c5%§7几率获取双倍沙子,燧石和黑曜石."}, 2500));
        perks.add(new Perk(0, "Convenience-A-IV", "便捷A-IV", new String[]{"§7增加§c5%§7几率获取双倍沙子,燧石和黑曜石."}, 20000));
        perks.add(new Perk(0, "Convenience-A-V", "便捷A-V", new String[]{"§7增加§c5%§7几率获取双倍沙子,燧石和黑曜石."}, 30000));

        perks.add(new Perk(1, "Convenience-B-II", "便捷B-II", new String[]{"§7增加§c5%§7几率获取双倍沙子,燧石和黑曜石."}, 1000));
        perks.add(new Perk(1, "Convenience-B-III", "便捷B-III", new String[]{"§7增加§c5%§7几率获取双倍沙子,燧石和黑曜石."}, 2500));
        perks.add(new Perk(1, "Convenience-B-IV", "便捷B-IV", new String[]{"§7增加§c5%§7几率获取双倍沙子,燧石和黑曜石."}, 20000));
        perks.add(new Perk(1, "Convenience-B-V", "便捷B-V", new String[]{"§7增加§c5%§7几率获取双倍沙子,燧石和黑曜石."}, 30000));

        setCrafts(crafts);
        setPerks(perks);
    }
}
