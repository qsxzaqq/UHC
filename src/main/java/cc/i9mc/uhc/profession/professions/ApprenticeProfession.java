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

public class ApprenticeProfession extends Profession {
    public ApprenticeProfession() {
        super("Apprentice", "学徒", new ItemStack(Material.IRON_HELMET));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('I', new MaterialData(Material.IRON_INGOT));
        ingredients.put('R', new MaterialData(Material.REDSTONE_TORCH_ON));
        crafts.add(new Craft("ApprenticeHelmet", "学徒之盔", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 50, new String[]{"III", "IRI", "   "}, ingredients, new ItemBuilderUtil().setType(Material.IRON_HELMET).setDisplayName("§a学徒之盔").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchant(Enchantment.PROTECTION_FIRE, 1).addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('B', new MaterialData(Material.REDSTONE_BLOCK));
        ingredients.put('S', new MaterialData(Material.IRON_SWORD));
        crafts.add(new Craft("ApprenticeHelmet", "学徒之剑", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1250, new String[]{" B ", " S ", " B "}, ingredients, new ItemBuilderUtil().setType(Material.IRON_SWORD).setDisplayName("§a学徒之剑").setLores(" §b➟ 在保护阶段结束后获得锋利I", " §b➟ 在保护阶段结束后5分钟获得锋利II", " §b➟ 在保护阶段结束后15分钟获得锋利III", " §b➟ 在死斗获得锋利IV", " ", "§7不能在铁砧和附魔台使用").getItem()));

        ingredients = new HashMap<>();
        ingredients.put('D', new MaterialData(Material.REDSTONE));
        ingredients.put('R', new MaterialData(Material.REDSTONE_TORCH_ON));
        ingredients.put('C', new MaterialData(Material.COMPASS));
        crafts.add(new Craft("ApprenticeHelmet", "大师罗盘", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 12500, new String[]{"DRD", "DCD", "DDD"}, ingredients, new ItemBuilderUtil().setType(Material.COMPASS).setDisplayName("§c大师罗盘").setLores("§7由古代吸血鬼打造的神秘强力", "§7罗盘,其指针会指向地面上离", "§7你最近的玩家").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('R', new MaterialData(Material.REDSTONE_TORCH_ON));
        ingredients.put('L', new MaterialData(Material.STRING));
        crafts.add(new Craft("ApprenticeHelmet", "学徒之弓", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 1250, new String[]{" RL", "R L", " RL"}, ingredients, new ItemBuilderUtil().setType(Material.BOW).setDisplayName("§a学徒之弓").setLores(" §b➟ 在保护阶段结束后获得力量I", " §b➟ 在保护阶段结束后5分钟获得力量II", " §b➟ 在保护阶段结束后15分钟获得力量III", " §b➟ 在死斗获得力量IV", " ", "§7不能在铁砧和附魔台使用").getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "HappyMeal-I", "愉悦盛宴-I", new String[]{"§7在游戏开始时,获得§c1§7分钟", "§7的饱和效果"}, 50));
        perks.add(new Perk(-1, "HappyMeal-VI", "愉悦盛宴-VI", new String[]{"§7在游戏开始时,额外获得§c1§7分钟", "§7的饱和效果"}, 112));

        perks.add(new Perk(0, "HappyMeal-A-II", "愉悦盛宴A-II", new String[]{"§7在游戏开始时,额外获得§c1§7分钟", "§7的饱和效果"}, 100));
        perks.add(new Perk(0, "HappyMeal-A-III", "愉悦盛宴A-III", new String[]{"§7在游戏开始时,额外获得§c1§7分钟", "§7的饱和效果"}, 312));
        perks.add(new Perk(0, "HappyMeal-A-IV", "愉悦盛宴A-IV", new String[]{"§7在游戏开始时,额外获得§c1§7分钟", "§7的饱和效果"}, 5000));
        perks.add(new Perk(0, "HappyMeal-A-V", "愉悦盛宴A-V", new String[]{"§7在游戏开始时,额外获得§c1§7分钟", "§7的饱和效果"}, 7500));

        perks.add(new Perk(1, "HappyMeal-B-II", "愉悦盛宴B-II", new String[]{"§7在游戏开始时,额外获得§c1§7分钟", "§7的饱和效果"}, 100));
        perks.add(new Perk(1, "HappyMeal-B-III", "愉悦盛宴B-III", new String[]{"§7在游戏开始时,额外获得§c1§7分钟", "§7的饱和效果"}, 312));
        perks.add(new Perk(1, "HappyMeal-B-IV", "愉悦盛宴B-IV", new String[]{"§7在游戏开始时,额外获得§c1§7分钟", "§7的饱和效果"}, 5000));
        perks.add(new Perk(1, "HappyMeal-B-V", "愉悦盛宴B-V", new String[]{"§7在游戏开始时,额外获得§c1§7分钟", "§7的饱和效果"}, 7500));

        setCrafts(crafts);
        setPerks(perks);
    }
}
