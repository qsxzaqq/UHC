package cc.i9mc.uhc.guis;

import cc.i9mc.gameutils.gui.CustonGUI;
import cc.i9mc.gameutils.gui.GUIAction;
import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.nick.Nick;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.Team;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SpectatorCompassGUI extends CustonGUI {

    public SpectatorCompassGUI(Player player, Game game) {
        super(player, "§8选择一个玩家来传送", game.getGamePlayers().size() <= 27 ? 27 : 54);

        Team team = game.getTeam(player);
        if (team == null) {
            return;
        }

        int i = 0;
        for (Player players : team.getPlayers()) {
            if (game.isSpectator(players)) {
                continue;
            }

            if (player.getName().equals(players.getName())) {
                continue;
            }

            setItem(i, new ItemBuilderUtil().setOwner(players.getName()).setDisplayName("§7[" + team.getId() + "] §e" + Nick.get().getCache().getOrDefault(players.getName(), players.getName())).setLores(" ", "§f血量: §8" + (int) players.getHealth(), "§f饥饿: §8" + players.getFoodLevel(), "§f等级: §8" + players.getLevel(), "§f距离: §8" + (players.getWorld().equals(player.getWorld()) ? (int) players.getLocation().distance(player.getLocation()) : "NA")).getItem(), new GUIAction(0, () -> {
                if (player.getGameMode() != GameMode.SPECTATOR) {
                    if (game.isSpectator(player)) {
                        if (player.getSpectatorTarget() != null) {
                            if ((player.getSpectatorTarget() instanceof Player)) {
                                player.setSpectatorTarget(null);
                            }
                        }
                    }
                }

                player.teleport(players);
            }, true));
            i++;
        }
    }
}
