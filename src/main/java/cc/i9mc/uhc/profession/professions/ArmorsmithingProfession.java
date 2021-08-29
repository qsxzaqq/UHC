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

public class ArmorsmithingProfession extends Profession {
    public ArmorsmithingProfession() {
        super("Armorsmithing", "防具锻造", new ItemStack(Material.DIAMOND_CHESTPLATE));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();

        ingredients.put('I', new MaterialData(Material.STICK));
        ingredients.put('L', new MaterialData(Material.LEATHER));
        crafts.add(new Craft("Armorsmithing", "经济皮革", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 125, new String[]{"ILI", "ILI", "ILI"}, ingredients, new ItemBuilderUtil().setType(Material.LEATHER).setAmount(8).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('P', new MaterialData(Material.PAPER));
        ingredients.put('I', new MaterialData(Material.STICK));
        crafts.add(new Craft("Armorsmithing", "保护书", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 2500, new String[]{"   ", " PP", " PI"}, ingredients, new ItemBuilderUtil().setType(Material.ENCHANTED_BOOK).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('M', new MaterialData(Material.MAGMA_CREAM));
        ingredients.put('D', new MaterialData(Material.DIAMOND_CHESTPLATE));
        ingredients.put('O', new MaterialData(Material.OBSIDIAN));
        ingredients.put('V', new MaterialData(Material.ANVIL));
        crafts.add(new Craft("Armorsmithing", "巨龙之甲", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 25000, new String[]{" M ", " D ", "OVO"}, ingredients, new ItemBuilderUtil().setType(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('P', new MaterialData(Material.PAPER));
        ingredients.put('W', new MaterialData(Material.ARROW));
        crafts.add(new Craft("Armorsmithing", "阿尔忒弥之书", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 2500, new String[]{"   ", " PP", " PW"}, ingredients, new ItemBuilderUtil().setType(Material.ENCHANTED_BOOK).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Tenactity-I", "韧性-I", new String[]{"§7当你击杀一名玩家时，获得§c1秒§7的抗性提升1效果"}, 250));
        perks.add(new Perk(-1, "Tenactity-VI", "韧性-VI", new String[]{"§7韧性额外增加§c1秒§7."}, 20000));

        perks.add(new Perk(0, "Tenactity-A-II", "韧性A-II", new String[]{"§7韧性额外增加§c1秒§7."}, 500));
        perks.add(new Perk(0, "Tenactity-A-III", "韧性A-III", new String[]{"§7韧性额外增加§c1秒§7."}, 750));
        perks.add(new Perk(0, "Tenactity-A-IV", "韧性A-IV", new String[]{"§7韧性额外增加§c1秒§7."}, 10000));
        perks.add(new Perk(0, "Tenactity-A-V", "韧性A-V", new String[]{"§7韧性额外增加§c1秒§7."}, 15000));

        perks.add(new Perk(1, "Tenactity-B-II", "韧性B-II", new String[]{"§7韧性额外增加§c1秒§7."}, 500));
        perks.add(new Perk(1, "Tenactity-B-III", "韧性B-III", new String[]{"§7韧性额外增加§c1秒§7."}, 750));
        perks.add(new Perk(1, "Tenactity-B-IV", "韧性B-IV", new String[]{"§7韧性额外增加§c1秒§7."}, 10000));
        perks.add(new Perk(1, "Tenactity-B-V", "韧性B-V", new String[]{"§7韧性额外增加§c1秒§7."}, 15000));

        setCrafts(crafts);
        setPerks(perks);
    }
}
