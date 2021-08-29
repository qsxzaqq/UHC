package cc.i9mc.uhc.profession;

import cc.i9mc.gameutils.utils.ActionBarUtil;
import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.nick.Nick;
import cc.i9mc.uhc.common.GameRecipe;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.event.EventManager;
import cc.i9mc.uhc.profession.professions.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfessionManager {
    @Getter
    private static final List<Profession> professions = new ArrayList<>();
    @Getter
    private static final List<Craft> crafts = new ArrayList<>();

    public static void init(Game game) {
        registerProfession(new ApprenticeProfession());
        registerProfession(new WeaponsmithingProfession());
        registerProfession(new ArmorsmithingProfession());
        registerProfession(new AlchemyProfession());
        registerProfession(new SurvivalismProfession());
        registerProfession(new EngineeringProfession());
        registerProfession(new EnchantingProfession());
        registerProfession(new CookingProfession());
        registerProfession(new BloodCraftProfession());
        registerProfession(new HunterProfession());
        registerProfession(new ToolsmithingProfession());
        registerProfession(new InventionProfession());

        professions.forEach(profession -> crafts.addAll(profession.getCrafts()));

        for (Profession profession : professions) {
            for (Craft craft : profession.getCrafts()) {
                GameRecipe gameRecipe = new GameRecipe(new ItemBuilderUtil().setItemStack(craft.getResult().clone()).addLore("§§§").getItem());
                gameRecipe.shape(craft.getShape());
                for (Map.Entry<Character, MaterialData> entry : craft.getIngredients().entrySet()) {
                    gameRecipe.setIngredient(entry.getKey(), entry.getValue());
                }
                gameRecipe.setProfessionName(profession.getName());
                gameRecipe.setCraftName(craft.getDisplayName());

                Bukkit.addRecipe(gameRecipe);
            }
        }

        EventManager eventManager = game.getEventManager();
        eventManager.registerRunnable("学徒专业附魔", (seconds, currentEvent) -> {
            int level = 0;
            if (currentEvent == 1) {
                if (seconds >= 0 && seconds < 500) {
                    level = 1;
                } else if (seconds >= 500 && seconds < 900) {
                    level = 2;
                } else if (seconds >= 900) {
                    level = 3;
                }
            } else if (currentEvent == 2) {
                level = 4;
            }

            for (OfflinePlayer offlinePlayer : game.getGameOfflinePlayers()) {
                if (!offlinePlayer.isOnline()) {
                    continue;
                }

                Player player = offlinePlayer.getPlayer();

                boolean b = false;
                for (ItemStack itemStack : player.getInventory()) {
                    if (itemStack != null && itemStack.getType() == Material.IRON_SWORD && itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName().equals("§a学徒之剑")) {
                        if (itemStack.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) {
                            if (level > 0 && itemStack.getEnchantments().get(Enchantment.DAMAGE_ALL) != level) {
                                itemStack.removeEnchantment(Enchantment.DAMAGE_ALL);
                                itemStack.addEnchantment(Enchantment.DAMAGE_ALL, level);
                                b = true;
                            }
                        } else {
                            if (level > 0) {
                                itemStack.addEnchantment(Enchantment.DAMAGE_ALL, level);
                                b = true;
                            }
                        }
                    } else if (itemStack != null && itemStack.getType() == Material.BOW && itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName().equals("§a学徒之弓")) {
                        if (itemStack.getEnchantments().containsKey(Enchantment.ARROW_DAMAGE)) {
                            if (level > 0 && itemStack.getEnchantments().get(Enchantment.ARROW_DAMAGE) != level) {
                                itemStack.removeEnchantment(Enchantment.ARROW_DAMAGE);
                                itemStack.addEnchantment(Enchantment.ARROW_DAMAGE, level);
                                b = true;
                            }
                        } else {
                            if (level > 0) {
                                itemStack.addEnchantment(Enchantment.ARROW_DAMAGE, level);
                                b = true;
                            }
                        }
                    }
                }

                if (b) {
                    player.updateInventory();
                }
            }
        });

        eventManager.registerRunnable("大师罗盘", (seconds, currentEvent) -> {
            for (OfflinePlayer player : game.getGameOfflinePlayers()) {
                if (!player.isOnline()) {
                    continue;
                }
                ItemStack itemStack = player.getPlayer().getItemInHand();

                if (itemStack != null && itemStack.getType() == Material.COMPASS && itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName().equals("§c大师罗盘")) {
                    Player target = game.findTargetPlayer(player.getPlayer());

                    if (target == null) {
                        continue;
                    }
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    numberFormat.setMaximumFractionDigits(2);
                    String result = numberFormat.format((float) target.getHealth() / (float) target.getMaxHealth() * 100);
                    ActionBarUtil.sendBar(player.getPlayer(), "§f目标:§a§l" + Nick.get().getCache().getOrDefault(target.getName(), target.getName()) + "  §f生命值:§a§l" + result + "%  §f距离:§a§l" + ((int) player.getPlayer().getLocation().distance(target.getLocation())) + "米");
                    player.getPlayer().setCompassTarget(target.getLocation());
                }
            }
        });
    }

    public static void registerProfession(Profession profession) {
        professions.add(profession);
    }

    public static Profession getProfession(String name) {
        for (Profession profession : professions) {
            if (profession.getName().equals(name)) {
                return profession;
            }
        }

        return null;
    }
}
