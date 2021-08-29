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

public class HunterProfession extends Profession {
    public HunterProfession() {
        super("Hunter", "猎手", new ItemStack(Material.BOW));
        List<Craft> crafts = new ArrayList<>();
        HashMap<Character, MaterialData> ingredients = new HashMap<>();
        ingredients.put('F', new MaterialData(Material.FLINT));
        ingredients.put('S', new MaterialData(Material.STICK));
        ingredients.put('E', new MaterialData(Material.FEATHER));
        crafts.add(new Craft("Hunter", "经济弓箭", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 250, new String[]{"FFF", "SSS", "EEE"}, ingredients, new ItemBuilderUtil().setType(Material.ARROW).setAmount(20).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('L', new MaterialData(Material.LEATHER));
        ingredients.put('T', new MaterialData(Material.STRING));
        ingredients.put('I', new MaterialData(Material.IRON_INGOT));
        crafts.add(new Craft("Hunter", "马鞍", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 5000, new String[]{"LLL", "TLT", "I I"}, ingredients, new ItemBuilderUtil().setType(Material.SADDLE).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('C', new MaterialData(Material.COCOA));
        ingredients.put('U', new MaterialData(Material.SUGAR));
        ingredients.put('G', new MaterialData(Material.GLASS_BOTTLE));
        crafts.add(new Craft("Hunter", "速度药水", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 5000, new String[]{" C ", " U ", " G "}, ingredients, new ItemBuilderUtil().setType(Material.POTION).setDisplayName("§a高速药水").setPotionData(new PotionEffect(PotionEffectType.SPEED, 240, 3)).getItem()));

        ingredients = new HashMap<>();
        ingredients.put('L', new MaterialData(Material.LEATHER));
        ingredients.put('B', new MaterialData(Material.BONE));
        ingredients.put('X', new MaterialData(Material.EXP_BOTTLE));
        crafts.add(new Craft("Hunter", "芬里厄巨狼", new String[]{"§7你可以在游戏中", "§7合成菜单学习", "§7更多有关消息"}, 50000, new String[]{"LLL", "BXB", "LLL"}, ingredients, new ItemBuilderUtil().setType(Material.MONSTER_EGG).setDisplayName("§a芬里厄巨狼").getItem()));

        List<Perk> perks = new ArrayList<>();
        perks.add(new Perk(-1, "TombRobber-I", "盗墓者-I", new String[]{"§7敌人在被你或你队友击杀时掉落§c1个§7金粒."}, 500));
        perks.add(new Perk(-1, "TombRobber-VI", "盗墓者-VI", new String[]{"§7击杀额外获得§c1个§7金粒."}, 40000));

        perks.add(new Perk(0, "TombRobber-A-II", "盗墓者A-II", new String[]{"§7击杀额外获得§c1个§7金粒."}, 1000));
        perks.add(new Perk(0, "TombRobber-A-III", "盗墓者A-III", new String[]{"§7击杀额外获得§c1个§7金粒."}, 2500));
        perks.add(new Perk(0, "TombRobber-A-IV", "盗墓者A-IV", new String[]{"§7击杀额外获得§c1个§7金粒."}, 20000));
        perks.add(new Perk(0, "TombRobber-A-V", "盗墓者A-V", new String[]{"§7击杀额外获得§c1个§7金粒."}, 30000));

        perks.add(new Perk(1, "TombRobber-B-II", "盗墓者B-II", new String[]{"§7击杀额外获得§c1个§7金粒."}, 1000));
        perks.add(new Perk(1, "TombRobber-B-III", "盗墓者B-III", new String[]{"§7击杀额外获得§c1个§7金粒."}, 2500));
        perks.add(new Perk(1, "TombRobber-B-IV", "盗墓者B-IV", new String[]{"§7击杀额外获得§c1个§7金粒."}, 20000));
        perks.add(new Perk(1, "TombRobber-B-V", "盗墓者B-V", new String[]{"§7击杀额外获得§c1个§7金粒."}, 30000));

        setCrafts(crafts);
        setPerks(perks);
    }
}
