package cc.i9mc.uhc.task;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.Team;
import cc.i9mc.uhc.kit.KitManager;
import cc.i9mc.uhc.listeners.ChunkListener;
import cc.i9mc.uhc.utils.Util;
import com.google.common.collect.Lists;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TeleportationTask extends BukkitRunnable {
    private ArrayList<Player> players;

    private Util.TeleportRunnable runnable = p -> {

        p.getInventory().clear();
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setGameMode(GameMode.SURVIVAL);
        p.setLevel(0);
        p.setFoodLevel(20);
        p.setSaturation(5.0f);
        p.setExhaustion(0.0f);
        p.getInventory().clear();

        p.setMetadata("nojumpdamage", new FixedMetadataValue(UHC.getInstance(), "nojumpdamage"));
        p.updateInventory();
        p.closeInventory();

        KitManager.give(p);
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2147483647, 0), true);
        p.getInventory().addItem(new ItemBuilderUtil().setType(Material.ENCHANTED_BOOK).setDisplayName("§a合成书§7(右键查看)").setLores("§7右键查看合成配合").getItem());
        p.getInventory().addItem(new ItemBuilderUtil().setType(Material.COMPASS).setDisplayName("§a队友追踪器§7(右键点击)").setLores("§7右键点击切换").getItem());

        players.remove(p);
    };

    public TeleportationTask() {
        this.players = Lists.newArrayList(UHC.getInstance().getGame().getGamePlayers());
        int TP_SECOND = 10;
        int count = (TP_SECOND / 37) + 1;
        this.runTaskTimerAsynchronously(UHC.getInstance(), 0, 20 / count);
    }

    public void run() {
        Game game = UHC.getInstance().getGame();

        if (!players.isEmpty()) {
            Player player = players.get(0);

            if (player == null || !player.isOnline()) {
                players.remove(0);
                return;
            }

            Location spawn = game.getSpawns().remove(0);
            System.out.println(spawn);
            Team team = game.getTeam(player);

            if (team == null) // Should not happen...
                return;

            Util.teleportListPlayer(team.getPlayers(), spawn, 5, runnable);
        }
        if (players.isEmpty()) {
            game.unfreezeAll();
            game.getEventManager().start();

            Util.setPlayerTeamTab();

            ChunkListener.keepChunk.clear();
            Util.playSoundToAll("EXPLODE");

            this.cancel();
        }
    }
}
