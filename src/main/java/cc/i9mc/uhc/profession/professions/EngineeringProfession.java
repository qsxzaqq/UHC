package cc.i9mc.uhc.profession.professions;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.profession.Craft;
import cc.i9mc.uhc.profession.Perk;
import cc.i9mc.uhc.profession.Profession;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EngineeringProfession extends Profession {
    public EngineeringProfession() {
        super("Engineering", "工程学术", new ItemStack(Material.ANVIL));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('I', new MaterialData(Material.IRON_ORE));
        ingredients.put('C', new MaterialData(Material.COAL));
        crafts.add(new Craft("Engineering", "冶铁", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 62, new String[]{"III", "ICI", "III"}, ingredients, new ItemBuilderUtil().setType(Material.IRON_INGOT).setAmount(10).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('W', new MaterialData(Material.WATER_BUCKET));
        ingredients.put('L', new MaterialData(Material.LAVA_BUCKET));
        crafts.add(new Craft("Engineering", "黑曜石", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1250, new String[]{"   ", " W ", " L "}, ingredients, new ItemBuilderUtil().setType(Material.OBSIDIAN).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('D', new MaterialData(Material.DIAMOND));
        ingredients.put('N', new MaterialData(Material.IRON_INGOT));
        ingredients.put('R', new MaterialData(Material.REDSTONE_BLOCK));
        crafts.add(new Craft("Engineering", "冰斗湖头盔", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1250, new String[]{"DND", "DRD", "   "}, ingredients, new ItemBuilderUtil().setType(Material.DIAMOND_HELMET).setDisplayName("§a冰斗湖头盔").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchant(Enchantment.PROTECTION_FIRE, 1).addEnchant(Enchantment.WATER_WORKER, 3).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('I', new MaterialData(Material.IRON_ORE));
        ingredients.put('G', new MaterialData(Material.GOLD_ORE));
        ingredients.put('Z', new MaterialData(Material.LAPIS_BLOCK));
        ingredients.put('K', new MaterialData(Material.STICK));
        crafts.add(new Craft("Engineering", "哲人之镐", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 12500, new String[]{"IGI", "ZKZ", " K "}, ingredients, new ItemBuilderUtil().setType(Material.DIAMOND_PICKAXE).setDisplayName("§a哲人之镐").addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 2).setDurability(1559).getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Magnetism-I", "经验吸收-I", new String[]{"§7挖掘矿石或击杀生物时，使你额外获得§c5%§7的经验"}, 125));
        perks.add(new Perk(-1, "Magnetism-VI", "经验吸收-VI", new String[]{"§7经验获得额外增加§c5%§7."}, 10000));

        perks.add(new Perk(0, "Magnetism-A-II", "经验吸收A-II", new String[]{"§7经验获得额zhan外增加§c5%§7."}, 250));
        perks.add(new Perk(0, "Magnetism-A-III", "经验吸收A-III", new String[]{"§7经验获得额外增加§c5%§7."}, 625));
        perks.add(new Perk(0, "Magnetism-A-IV", "经验吸收A-IV", new String[]{"§7经验获得额外增加§c5%§7."}, 5000));
        perks.add(new Perk(0, "Magnetism-A-V", "经验吸收A-V", new String[]{"§7经验获得额外增加§c5%§7."}, 7500));

        perks.add(new Perk(1, "Magnetism-B-II", "经验吸收B-II", new String[]{"§7经验获得额外增加§c5%§7."}, 250));
        perks.add(new Perk(1, "Magnetism-B-III", "经验吸收B-III", new String[]{"§7经验获得额外增加§c5%§7."}, 625));
        perks.add(new Perk(1, "Magnetism-B-IV", "经验吸收B-IV", new String[]{"§7经验获得额外增加§c5%§7."}, 5000));
        perks.add(new Perk(1, "Magnetism-B-V", "经验吸收B-V", new String[]{"§7经验获得额外增加§c5%§7."}, 7500));

        setCrafts(crafts);
        setPerks(perks);
    }
}
