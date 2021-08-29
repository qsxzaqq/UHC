package cc.i9mc.uhc.profession.professions;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.profession.Craft;
import cc.i9mc.uhc.profession.Perk;
import cc.i9mc.uhc.profession.Profession;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlchemyProfession extends Profession {
    public AlchemyProfession() {
        super("Alchemy", "炼药学术", new ItemStack(Material.BREWING_STAND_ITEM));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('D', new MaterialData(Material.REDSTONE));
        ingredients.put('F', new MaterialData(Material.FLINT_AND_STEEL));
        crafts.add(new Craft("Alchemy", "光之尘", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 95, new String[]{"DDD", "DFD", "DDD"}, ingredients, new ItemBuilderUtil().setType(Material.GLOWSTONE_DUST).setAmount(8).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('E', new MaterialData(Material.FERMENTED_SPIDER_EYE));
        ingredients.put('S', new MaterialData(Material.SEEDS));
        crafts.add(new Craft("Alchemy", "炼药制品", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1875, new String[]{" S ", "SES", " S "}, ingredients, new ItemBuilderUtil().setType(Material.NETHER_STALK).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('O', new MaterialData(Material.STAINED_GLASS, (byte) 1));
        ingredients.put('L', new MaterialData(Material.LAVA_BUCKET));
        ingredients.put('W', new MaterialData(Material.FIREWORK));
        crafts.add(new Craft("Alchemy", "烈焰棒", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 18750, new String[]{"OLO", "OWO", "OLO"}, ingredients, new ItemBuilderUtil().setType(Material.BLAZE_ROD).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('G', new MaterialData(Material.GLASS_BOTTLE));
        ingredients.put('M', new MaterialData(Material.MELON));
        ingredients.put('R', new MaterialData(Material.EMERALD));
        ingredients.put('N', new MaterialData(Material.GOLD_INGOT));
        crafts.add(new Craft("Alchemy", "甘露", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1875, new String[]{" R ", "NMN", " G "}, ingredients, new ItemBuilderUtil().setType(Material.POTION).setPotionData(new PotionEffect(PotionEffectType.REGENERATION, 200, 3)).getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Endurance-I", "耐力-I", new String[]{"§7使你的正面药水持续时间§c+2%"}, 187));
        perks.add(new Perk(-1, "Endurance-VI", "耐力-VI", new String[]{"§7耐力额外增加§c2%§7时间."}, 15000));

        perks.add(new Perk(0, "Endurance-A-II", "耐力A-II", new String[]{"§7耐力额外增加§c2%§7时间."}, 375));
        perks.add(new Perk(0, "Endurance-A-III", "耐力A-III", new String[]{"§7耐力额外增加§c2%§7时间."}, 937));
        perks.add(new Perk(0, "Endurance-A-IV", "耐力A-IV", new String[]{"§7耐力额外增加§c2%§7时间."}, 7500));
        perks.add(new Perk(0, "Endurance-A-V", "耐力A-V", new String[]{"§7耐力额外增加§c2%§7时间."}, 12500));

        perks.add(new Perk(1, "Endurance-B-II", "耐力B-II", new String[]{"§7耐力额外增加§c2%§7时间."}, 375));
        perks.add(new Perk(1, "Endurance-B-III", "耐力B-III", new String[]{"§7耐力额外增加§c2%§7时间."}, 937));
        perks.add(new Perk(1, "Endurance-B-IV", "耐力B-IV", new String[]{"§7耐力额外增加§c2%§7时间."}, 7500));
        perks.add(new Perk(1, "Endurance-B-V", "耐力B-V", new String[]{"§7耐力额外增加§c2%§7时间."}, 12500));

        setCrafts(crafts);
        setPerks(perks);
    }
}
