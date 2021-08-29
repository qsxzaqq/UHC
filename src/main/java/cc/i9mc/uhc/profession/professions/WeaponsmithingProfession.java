package cc.i9mc.uhc.profession.professions;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.profession.Craft;
import cc.i9mc.uhc.profession.Perk;
import cc.i9mc.uhc.profession.Profession;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeaponsmithingProfession extends Profession {
    public WeaponsmithingProfession() {
        super("Weaponsmithing", "武器锻造", new ItemStack(Material.DIAMOND_SWORD));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('B', new MaterialData(Material.BONE));
        ingredients.put('I', new MaterialData(Material.IRON_SWORD));
        ingredients.put('R', new MaterialData(Material.ROTTEN_FLESH));
        crafts.add(new Craft("Weaponsmithing", "斩首之剑", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 125, new String[]{" B ", " I ", " R "}, ingredients, new ItemBuilderUtil().setType(Material.IRON_SWORD).setDisplayName("§a斩首之剑").addEnchant(Enchantment.LOOT_BONUS_MOBS, 1).addEnchant(Enchantment.DAMAGE_ARTHROPODS, 2).addEnchant(Enchantment.DAMAGE_UNDEAD, 2).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('F', new MaterialData(Material.FLINT));
        ingredients.put('P', new MaterialData(Material.PAPER));
        ingredients.put('I', new MaterialData(Material.IRON_SWORD));
        crafts.add(new Craft("Weaponsmithing", "锋利书", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 2500, new String[]{"F  ", " PP", " PI"}, ingredients, new ItemBuilderUtil().setType(Material.ENCHANTED_BOOK).addEnchant(Enchantment.DAMAGE_ALL, 1).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('L', new MaterialData(Material.BLAZE_POWDER));
        ingredients.put('D', new MaterialData(Material.DIAMOND_SWORD));
        ingredients.put('O', new MaterialData(Material.OBSIDIAN));
        ItemStack itemStack = new ItemBuilderUtil().setType(Material.DIAMOND_SWORD).setDisplayName("§a巨龙之剑").getItem();

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound damage = new NBTTagCompound();
        damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage.set("Name", new NBTTagString("generic.attackDamage"));
        damage.set("Amount", new NBTTagInt(8));
        damage.set("Operation", new NBTTagInt(0));
        damage.set("UUIDLeast", new NBTTagInt(894654));
        damage.set("UUIDMost", new NBTTagInt(2872));
        modifiers.add(damage);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        itemStack = CraftItemStack.asBukkitCopy(nmsStack);
        //Attack + 1
        crafts.add(new Craft("Weaponsmithing", "巨龙之剑", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 25000, new String[]{" L ", " D ", "OLO"}, ingredients, itemStack));

        ingredients = new HashMap<>();
        ingredients.put('B', new MaterialData(Material.BONE));
        ingredients.put('F', new MaterialData(Material.FLINT));
        ingredients.put('P', new MaterialData(Material.PAPER));
        crafts.add(new Craft("Weaponsmithing", "力量书", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 2500, new String[]{"F  ", " PP", " PB"}, ingredients, new ItemBuilderUtil().setType(Material.ENCHANTED_BOOK).addEnchant(Enchantment.ARROW_DAMAGE, 1).getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "Berserk-I", "狂怒-I", new String[]{"§7当你击杀一名玩家时，获得§c0.5秒§7的力量1效果"}, 250));
        perks.add(new Perk(-1, "Berserk-VI", "狂怒-VI", new String[]{"§7狂怒额外增加§c0.5秒§7."}, 20000));

        perks.add(new Perk(0, "Berserk-A-II", "狂怒A-II", new String[]{"§7狂怒额外增加§c0.5秒§7."}, 500));
        perks.add(new Perk(0, "Berserk-A-III", "狂怒A-III", new String[]{"§7狂怒额外增加§c0.5秒§7."}, 750));
        perks.add(new Perk(0, "Berserk-A-IV", "狂怒A-IV", new String[]{"§7狂怒额外增加§c0.5秒§7."}, 10000));
        perks.add(new Perk(0, "Berserk-A-V", "狂怒A-V", new String[]{"§7狂怒额外增加§c0.5秒§7."}, 15000));

        perks.add(new Perk(1, "Berserk-B-II", "狂怒B-II", new String[]{"§7狂怒额外增加§c0.5秒§7."}, 500));
        perks.add(new Perk(1, "Berserk-B-III", "狂怒B-III", new String[]{"§7狂怒额外增加§c0.5秒§7."}, 750));
        perks.add(new Perk(1, "Berserk-B-IV", "狂怒B-IV", new String[]{"§7狂怒额外增加§c0.5秒§7."}, 10000));
        perks.add(new Perk(1, "Berserk-B-V", "狂怒B-V", new String[]{"§7狂怒额外增加§c0.5秒§7."}, 15000));

        setCrafts(crafts);
        setPerks(perks);
    }
}
