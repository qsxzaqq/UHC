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

public class EnchantingProfession extends Profession {
    public EnchantingProfession() {
        super("Enchanting", "附魔学术", new ItemStack(Material.ENCHANTMENT_TABLE));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('R', new MaterialData(Material.REDSTONE_BLOCK));
        ingredients.put('B', new MaterialData(Material.GLASS_BOTTLE));
        crafts.add(new Craft("Enchanting", "启蒙套装", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 95, new String[]{" R ", "RBR", " R "}, ingredients, new ItemBuilderUtil().setType(Material.EXP_BOTTLE).setAmount(8).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('I', new MaterialData(Material.IRON_INGOT));
        ingredients.put('C', new MaterialData(Material.IRON_BLOCK));
        crafts.add(new Craft("Enchanting", "简易铁砧", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1875, new String[]{"III", " C ", "III"}, ingredients, new ItemBuilderUtil().setType(Material.ANVIL).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('S', new MaterialData(Material.BOOKSHELF));
        ingredients.put('D', new MaterialData(Material.DIAMOND));
        ingredients.put('O', new MaterialData(Material.OBSIDIAN));
        ingredients.put('E', new MaterialData(Material.EXP_BOTTLE));
        crafts.add(new Craft("Enchanting", "简易附魔台", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1875, new String[]{" S ", "ODO", "OEO"}, ingredients, new ItemBuilderUtil().setType(Material.ENCHANTMENT_TABLE).getItem()));


        ingredients = new HashMap<>();
        ingredients.put('Y', new MaterialData(Material.EYE_OF_ENDER));
        ingredients.put('P', new MaterialData(Material.PAPER));
        ingredients.put('F', new MaterialData(Material.FIREBALL));
        crafts.add(new Craft("Enchanting", "月神之书", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 18750, new String[]{"Y  ", " PP", " PF"}, ingredients, new ItemBuilderUtil().setType(Material.ENCHANTED_BOOK).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.DAMAGE_ALL, 2).addEnchant(Enchantment.ARROW_DAMAGE, 2).addEnchant(Enchantment.ARROW_KNOCKBACK, 1).addEnchant(Enchantment.FIRE_ASPECT, 1).getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Magnetism-I", "经验吸收-I", new String[]{"§7挖掘矿石或击杀生物时，使你额外获得§c5%§7的经验"}, 187));
        perks.add(new Perk(-1, "Magnetism-VI", "经验吸收-VI", new String[]{"§7经验获得额外增加§c5%§7."}, 15000));

        perks.add(new Perk(0, "Magnetism-A-II", "经验吸收A-II", new String[]{"§7经验获得额zhan外增加§c5%§7."}, 375));
        perks.add(new Perk(0, "Magnetism-A-III", "经验吸收A-III", new String[]{"§7经验获得额外增加§c5%§7."}, 937));
        perks.add(new Perk(0, "Magnetism-A-IV", "经验吸收A-IV", new String[]{"§7经验获得额外增加§c5%§7."}, 7500));
        perks.add(new Perk(0, "Magnetism-A-V", "经验吸收A-V", new String[]{"§7经验获得额外增加§c5%§7."}, 12500));

        perks.add(new Perk(1, "Magnetism-B-II", "经验吸收B-II", new String[]{"§7经验获得额外增加§c5%§7."}, 375));
        perks.add(new Perk(1, "Magnetism-B-III", "经验吸收B-III", new String[]{"§7经验获得额外增加§c5%§7."}, 937));
        perks.add(new Perk(1, "Magnetism-B-IV", "经验吸收B-IV", new String[]{"§7经验获得额外增加§c5%§7."}, 7500));
        perks.add(new Perk(1, "Magnetism-B-V", "经验吸收B-V", new String[]{"§7经验获得额外增加§c5%§7."}, 12500));

        setCrafts(crafts);
        setPerks(perks);
    }
}
