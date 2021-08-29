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

public class CookingProfession extends Profession {
    public CookingProfession() {
        super("Cooking", "烹饪学术", new ItemStack(Material.GOLDEN_APPLE));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('P', new MaterialData(Material.APPLE));
        ingredients.put('D', new MaterialData(Material.INK_SACK, (byte) 15));
        crafts.add(new Craft("Cooking", "智慧果", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 125, new String[]{"   ", " D ", " P "}, ingredients, new ItemBuilderUtil().setType(Material.APPLE).setAmount(2).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('P', new MaterialData(Material.APPLE));
        ingredients.put('D', new MaterialData(Material.INK_SACK, (byte) 15));
        ingredients.put('S', new MaterialData(Material.SEEDS));
        crafts.add(new Craft("Cooking", "治愈果实", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 2500, new String[]{"DSD", "SPS", "DSD"}, ingredients, new ItemBuilderUtil().setType(Material.MELON).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('I', new MaterialData(Material.GOLD_INGOT));
        ingredients.put('P', new MaterialData(Material.APPLE));
        crafts.add(new Craft("Cooking", "简易金苹果", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 25000, new String[]{" I ", "IPI", " I "}, ingredients, new ItemBuilderUtil().setType(Material.GOLDEN_APPLE).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('I', new MaterialData(Material.GOLD_INGOT));
        ingredients.put('B', new MaterialData(Material.REDSTONE_BLOCK));
        ingredients.put('R', new MaterialData(Material.RECORD_12));
        ingredients.put('G', new MaterialData(Material.GLASS_BOTTLE));
        crafts.add(new Craft("Cooking", "圣水", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 2500, new String[]{"IBI", " R ", " G "}, ingredients, new ItemBuilderUtil().setType(Material.POTION).setPotionData(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 2)).getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Vitamins-I", "维生素-I", new String[]{"§7开局获得4颗心的伤害吸收效果。持续§c60秒§7."}, 250));
        perks.add(new Perk(-1, "Vitamins-VI", "维生素-VI", new String[]{"§7霸体额外增加§c60秒§7."}, 20000));

        perks.add(new Perk(0, "Vitamins-A-II", "维生素A-II", new String[]{"§7霸体额外增加§c60秒§7."}, 500));
        perks.add(new Perk(0, "Vitamins-A-III", "维生素A-III", new String[]{"§7霸体额外增加§c60秒§7."}, 750));
        perks.add(new Perk(0, "Vitamins-A-IV", "维生素A-IV", new String[]{"§7霸体额外增加§c60秒§7."}, 10000));
        perks.add(new Perk(0, "Vitamins-A-V", "维生素A-V", new String[]{"§7霸体额外增加§c60秒§7."}, 15000));

        perks.add(new Perk(1, "Vitamins-B-II", "维生素B-II", new String[]{"§7霸体额外增加§c60秒§7."}, 500));
        perks.add(new Perk(1, "Vitamins-B-III", "维生素B-III", new String[]{"§7霸体额外增加§c60秒§7."}, 750));
        perks.add(new Perk(1, "Vitamins-B-IV", "维生素B-IV", new String[]{"§7霸体额外增加§c60秒§7."}, 10000));
        perks.add(new Perk(1, "Vitamins-B-V", "维生素B-V", new String[]{"§7霸体额外增加§c60秒§7."}, 15000));

        setCrafts(crafts);
        setPerks(perks);
    }
}
