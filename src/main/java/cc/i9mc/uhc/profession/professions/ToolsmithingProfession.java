package cc.i9mc.uhc.profession.professions;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.profession.Craft;
import cc.i9mc.uhc.profession.Perk;
import cc.i9mc.uhc.profession.Profession;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToolsmithingProfession extends Profession {
    public ToolsmithingProfession() {
        super("Toolsmithing", "工具打造", new ItemStack(Material.DIAMOND_PICKAXE));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('C', new MaterialData(Material.COBBLESTONE));
        ingredients.put('B', new MaterialData(Material.COAL_BLOCK));
        crafts.add(new Craft("Armorsmithing", "锻炉", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 125, new String[]{"CCC", "CBC", "CCC"}, ingredients, new ItemBuilderUtil().setType(Material.FURNACE).setDisplayName("§a锻炉").addEnchant(Enchantment.FIRE_ASPECT, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('I', new MaterialData(Material.IRON_ORE));
        ingredients.put('O', new MaterialData(Material.COAL));
        ingredients.put('S', new MaterialData(Material.STICK));
        crafts.add(new Craft("Armorsmithing", "效率铁镐", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 2500, new String[]{"III", "OSO", " S "}, ingredients, new ItemBuilderUtil().setType(Material.IRON_PICKAXE).addEnchant(Enchantment.DIG_SPEED, 1).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('E', new MaterialData(Material.EXP_BOTTLE));
        ingredients.put('P', new MaterialData(Material.IRON_PICKAXE));
        ingredients.put('T', new MaterialData(Material.ENCHANTMENT_TABLE));
        ingredients.put('X', new MaterialData(Material.IRON_AXE));
        ingredients.put('H', new MaterialData(Material.BOOKSHELF));
        crafts.add(new Craft("Armorsmithing", "强化书", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 25000, new String[]{"EEE", "PTX", "HHH"}, ingredients, new ItemBuilderUtil().setType(Material.ENCHANTED_BOOK).setDisplayName("§a强化书").getItem()));

        ingredients = new HashMap<>();
        ingredients.put('I', new MaterialData(Material.IRON_INGOT));
        ingredients.put('V', new MaterialData(Material.FLINT));
        ingredients.put('S', new MaterialData(Material.STICK));
        crafts.add(new Craft("Armorsmithing", "伐木人之斧", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 2500, new String[]{"IIV", "IS ", " S "}, ingredients, new ItemBuilderUtil().setType(Material.IRON_AXE).setDisplayName("§a伐木人之斧").addEnchant(Enchantment.DIG_SPEED, 1).getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Tenactity-I", "好运来-I", new String[]{"§7挖掘铁矿金矿时，有§c5%§7的几率获得双倍掉落。"}, 250));
        perks.add(new Perk(-1, "Tenactity-VI", "好运来-VI", new String[]{"§7好运来额外增加§c1%§7掉落几率."}, 20000));

        perks.add(new Perk(0, "Tenactity-A-II", "好运来A-II", new String[]{"§7好运来额外增加§c1%§7掉落几率."}, 500));
        perks.add(new Perk(0, "Tenactity-A-III", "好运来A-III", new String[]{"§7好运来额外增加§c1%§7掉落几率."}, 750));
        perks.add(new Perk(0, "Tenactity-A-IV", "好运来A-IV", new String[]{"§7好运来额外增加§c1%§7掉落几率."}, 10000));
        perks.add(new Perk(0, "Tenactity-A-V", "好运来A-V", new String[]{"§7好运来额外增加§c1%§7掉落几率."}, 15000));

        perks.add(new Perk(1, "Tenactity-B-II", "好运来B-II", new String[]{"§7好运来额外增加§c1%§7掉落几率."}, 500));
        perks.add(new Perk(1, "Tenactity-B-III", "好运来B-III", new String[]{"§7好运来额外增加§c1%§7掉落几率."}, 750));
        perks.add(new Perk(1, "Tenactity-B-IV", "好运来B-IV", new String[]{"§7好运来额外增加§c1%§7掉落几率."}, 10000));
        perks.add(new Perk(1, "Tenactity-B-V", "好运来B-V", new String[]{"§7好运来额外增加§c1%§7掉落几率."}, 15000));

        setCrafts(crafts);
        setPerks(perks);
    }
}
