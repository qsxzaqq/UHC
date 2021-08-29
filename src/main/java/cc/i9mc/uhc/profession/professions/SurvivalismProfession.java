package cc.i9mc.uhc.profession.professions;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.profession.Craft;
import cc.i9mc.uhc.profession.Perk;
import cc.i9mc.uhc.profession.Profession;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SurvivalismProfession extends Profession {
    public SurvivalismProfession() {
        super("Survivalism", "生存技巧", new ItemStack(Material.FEATHER));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('B', new MaterialData(Material.RAW_BEEF));
        ingredients.put('C', new MaterialData(Material.COAL));
        crafts.add(new Craft("Survivalism", "美味佳肴", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 62, new String[]{"BBB", "BCB", "BBB"}, ingredients, new ItemBuilderUtil().setType(Material.COOKED_BEEF).setAmount(10).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('S', new MaterialData(Material.SLIME_BALL));
        ingredients.put('N', new MaterialData(Material.SNOW));
        ingredients.put('G', new MaterialData(Material.GLASS_BOTTLE));
        crafts.add(new Craft("Survivalism", "坚韧药水", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1250, new String[]{" S ", " N ", " G "}, ingredients, new ItemBuilderUtil().setType(Material.POTION).setPotionData(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2400, 1)).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('E', new MaterialData(Material.FEATHER));
        ingredients.put('R', new MaterialData(Material.ENDER_PEARL));
        ingredients.put('M', new MaterialData(Material.DIAMOND_BOOTS));
        ingredients.put('W', new MaterialData(Material.WATER_BUCKET));
        crafts.add(new Craft("Survivalism", "七国战靴", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 12500, new String[]{"ERE", "EME", "EWE"}, ingredients, new ItemBuilderUtil().setType(Material.DIAMOND_BOOTS).setDisplayName("§a七国战靴").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.PROTECTION_FALL, 3).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('L', new MaterialData(Material.WATER_LILY));
        ingredients.put('T', new MaterialData(Material.CACTUS));
        ingredients.put('H', new MaterialData(Material.LEATHER_CHESTPLATE));
        crafts.add(new Craft("Survivalism", "刃甲", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1250, new String[]{" L ", " T ", " H "}, ingredients, new ItemBuilderUtil().setType(Material.LEATHER_CHESTPLATE).setDisplayName("§a刃甲").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).addEnchant(Enchantment.DURABILITY, 10).addEnchant(Enchantment.THORNS, 1).setColor(DyeColor.GREEN.getColor()).getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Magnetism-I", "专家-I", new String[]{"§7减少§c4%§7的自然环境伤害"}, 125));
        perks.add(new Perk(-1, "Magnetism-VI", "专家-VI", new String[]{"§7减少§c4%§7的自然环境伤害"}, 10000));

        perks.add(new Perk(0, "Magnetism-A-II", "专家A-II", new String[]{"§7减少§c4%§7的自然环境伤害"}, 250));
        perks.add(new Perk(0, "Magnetism-A-III", "专家A-III", new String[]{"§7减少§c4%§7的自然环境伤害"}, 625));
        perks.add(new Perk(0, "Magnetism-A-IV", "专家A-IV", new String[]{"§7减少§c4%§7的自然环境伤害"}, 5000));
        perks.add(new Perk(0, "Magnetism-A-V", "专家A-V", new String[]{"§7减少§c4%§7的自然环境伤害"}, 7500));

        perks.add(new Perk(1, "Magnetism-B-II", "专家B-II", new String[]{"§7减少§c4%§7的自然环境伤害"}, 250));
        perks.add(new Perk(1, "Magnetism-B-III", "专家B-III", new String[]{"§7减少§c4%§7的自然环境伤害"}, 625));
        perks.add(new Perk(1, "Magnetism-B-IV", "专家B-IV", new String[]{"§7减少§c4%§7的自然环境伤害"}, 5000));
        perks.add(new Perk(1, "Magnetism-B-V", "专家B-V", new String[]{"§7减少§c4%§7的自然环境伤害"}, 7500));

        setCrafts(crafts);
        setPerks(perks);
    }
}
