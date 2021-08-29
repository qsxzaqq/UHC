package cc.i9mc.uhc.game.event;

import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.common.PlayerZombieData;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PVPStartEvent extends GameEvent {
    public PVPStartEvent() {
        super("死亡竞赛倒计时", 3000, 1);
    }

    public void excute(Game game) {
        int ii = 0;
        for (Team team : game.getAliveTeams()) {
            for (OfflinePlayer offlinePlayer : team.getOfflinePlayers()) {
                if (!offlinePlayer.isOnline()) {
                    for (PlayerZombieData playerZombieData : game.getPlayerZombies()) {
                        if (playerZombieData.getPlayer().getName().equals(offlinePlayer.getName()) && !playerZombieData.getZombie().isDead()) {
                            int finalIi = ii;
                            Bukkit.getScheduler().runTask(UHC.getInstance(), () -> playerZombieData.getZombie().teleport(game.getDmList().get(finalIi)));
                        }
                    }
                    continue;
                }

                int finalIi1 = ii;
                Bukkit.getScheduler().runTask(UHC.getInstance(), () -> offlinePlayer.getPlayer().teleport(game.getDmList().get(finalIi1)));
            }
            ii++;
        }
    }
}
