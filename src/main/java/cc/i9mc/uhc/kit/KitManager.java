package cc.i9mc.uhc.kit;

import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.databse.PlayerData;
import cc.i9mc.uhc.kit.kits.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitManager {
    private static final List<Kit> kits = new ArrayList<>();

    public static void init() {
        registerKit(new LeatherArmorKit());
        registerKit(new EnchantingSetKit());
        registerKit(new ArcherySetKit());
        registerKit(new StoneGearKit());
        registerKit(new LunchKit());
        registerKit(new LooterKit());
        registerKit(new EcologistKit());
        registerKit(new FarmerKit());
        registerKit(new HorsemanKit());
        registerKit(new TrapperKit());
    }

    public static void give(Player player) {
        PlayerData playerData = UHC.getInstance().getCacher().get(player.getName());
        if (playerData.getKit() == null) {
            return;
        }
        Kit kit = getKit(playerData.getKit());

        if (kit == null) {
            return;
        }

        if (player.hasPermission("uhc.kits." + playerData.getKit() + ".3")) {
            kit.getRunnable().run(player, 3);
            player.updateInventory();
            return;
        }

        if (player.hasPermission("uhc.kits." + playerData.getKit() + ".2")) {
            kit.getRunnable().run(player, 2);
            player.updateInventory();
            return;
        }

        if (player.hasPermission("uhc.kits." + playerData.getKit() + ".1")) {
            kit.getRunnable().run(player, 1);
            player.updateInventory();
            return;
        }

        kit.getRunnable().run(player, 0);
        player.updateInventory();
    }

    public static void registerKit(Kit kit) {
        kits.add(kit);
    }

    public static Kit getKit(String name) {
        for (Kit kit : kits) {
            if (kit.getName().equals(name)) {
                return kit;
            }
        }

        return null;
    }

    public static List<Kit> getList() {
        return kits;
    }
}
