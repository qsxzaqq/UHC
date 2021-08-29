package cc.i9mc.uhc.profession.professions;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.profession.Craft;
import cc.i9mc.uhc.profession.Perk;
import cc.i9mc.uhc.profession.Profession;
import cc.i9mc.uhc.utils.Util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BloodCraftProfession extends Profession {
    public BloodCraftProfession() {
        super("BloodCraft", "血魔法", new ItemStack(Material.SKULL_ITEM));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('I', new MaterialData(Material.GOLD_INGOT));
        ingredients.put('S', new MaterialData(Material.SKULL_ITEM, (byte) 3));
        crafts.add(new Craft("BloodCraft", "金头", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 250, new String[]{"III", "ISI", "III"}, ingredients, new ItemBuilderUtil().setItemStack(Util.createGodSkull().clone()).setDisplayName("§6金头").addLore("§a*效果给予队伍内全部玩家").getItem()));

        ingredients = new HashMap<>();
        ingredients.put('S', new MaterialData(Material.SKULL_ITEM, (byte) 3));
        ingredients.put('C', new MaterialData(Material.CHEST));
        crafts.add(new Craft("BloodCraft", "潘多拉之盒", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 5000, new String[]{"CCC", "CSC", "CCC"}, ingredients, new ItemBuilderUtil().setType(Material.CHEST).setDisplayName("§a潘多拉之盒").getItem()));

        ingredients = new HashMap<>();
        ingredients.put('K', new MaterialData(Material.BLAZE_ROD));
        ingredients.put('B', new MaterialData(Material.BOW));
        ingredients.put('L', new MaterialData(Material.LAVA_BUCKET));
        ingredients.put('S', new MaterialData(Material.SKULL_ITEM, (byte) 3));
        crafts.add(new Craft("BloodCraft", "丘比特之弓", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 50000, new String[]{" K ", "SBS", " L "}, ingredients, new ItemBuilderUtil().setType(Material.BOW).setDisplayName("§c丘比特之弓").addEnchant(Enchantment.ARROW_DAMAGE, 2).addEnchant(Enchantment.ARROW_FIRE, 1).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('P', new MaterialData(Material.SPECKLED_MELON));
        ingredients.put('T', new MaterialData(Material.GLASS_BOTTLE));
        ingredients.put('S', new MaterialData(Material.SKULL_ITEM, (byte) 3));
        crafts.add(new Craft("BloodCraft", "永生之酒", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 5000, new String[]{"   ", "SPS", " T "}, ingredients, new ItemBuilderUtil().setType(Material.POTION).setDisplayName("§a永生之酒").setPotionData(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 2)).setPotionData(new PotionEffect(PotionEffectType.HEAL, 20, 4)).getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Celerity-I", "敏捷-I", new String[]{"§7在你使用一个玩家头后使你获得§c6秒§7的速度Ⅱ效果"}, 500));
        perks.add(new Perk(-1, "Celerity-VI", "敏捷-VI", new String[]{"§7使用头时额外获得§c1秒§7速度."}, 40000));

        perks.add(new Perk(0, "Celerity-A-II", "敏捷A-II", new String[]{"§7使用头时额外获得§c1秒§7速度."}, 1000));
        perks.add(new Perk(0, "Celerity-A-III", "敏捷A-III", new String[]{"§7使用头时额外获得§c1秒§7速度."}, 2500));
        perks.add(new Perk(0, "Celerity-A-IV", "敏捷A-IV", new String[]{"§7使用头时额外获得§c1秒§7速度."}, 20000));
        perks.add(new Perk(0, "Celerity-A-V", "敏捷A-V", new String[]{"§7使用头时额外获得§c1秒§7速度."}, 30000));

        perks.add(new Perk(1, "Celerity-B-II", "敏捷B-II", new String[]{"§7使用头时额外获得§c1秒§7速度."}, 1000));
        perks.add(new Perk(1, "Celerity-B-III", "敏捷B-III", new String[]{"§7使用头时额外获得§c1秒§7速度."}, 2500));
        perks.add(new Perk(1, "Celerity-B-IV", "敏捷B-IV", new String[]{"§7使用头时额外获得§c1秒§7速度."}, 20000));
        perks.add(new Perk(1, "Celerity-B-V", "敏捷B-V", new String[]{"§7使用头时额外获得§c1秒§7速度."}, 30000));

        setCrafts(crafts);
        setPerks(perks);
    }
}
