package cc.i9mc.uhc.game;

import cc.i9mc.gameutils.utils.ActionBarUtil;
import cc.i9mc.gameutils.utils.TitleUtil;
import cc.i9mc.rejoin.events.RejoinPlayerJoinEvent;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.event.UHCGameStartEvent;
import cc.i9mc.uhc.listeners.Frozen;
import cc.i9mc.uhc.scoreboards.LobbyBoard;
import cc.i9mc.uhc.task.TeleportationTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameLobbyCountdown extends BukkitRunnable {
    @Getter
    private int countdown = 360;
    private Game game;
    private boolean s = false;


    GameLobbyCountdown(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (game.isForceStart()) {
            cancel();
        }

        if (!game.hasEnoughPlayers()) {
            s = false;
            game.broadcastMessage("§c人数不足，取消倒计时！");
            countdown = 360;
            game.setGameState(GameState.WAITING);
            game.setGameLobbyCountdown(null);
            LobbyBoard.updateBoard();
            cancel();
        }

        if (countdown == 60 || countdown == 30 || countdown <= 5 && countdown > 0) {
            game.getPlayers().forEach(player -> {
                player.sendMessage("§e游戏将在§c" + countdown + "§e秒后开始！");
                TitleUtil.sendTitle(player, 1, 20, 1, "§c§l" + countdown, "§e§l准备战斗吧！");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 10F);
            });
        }

        if (!s && game.getPlayers().size() == game.getMaxPlayers()) {
            s = true;
            countdown = 10;
            game.broadcastMessage("§e游戏人数已满,10秒后开始游戏！");
        }

        if (countdown <= 0) {
            cancel();

            game.setGameState(GameState.RUNNING);
            countdown = 0;

            game.moveFreePlayersToTeam();

            game.getPlayers().forEach(player -> {
                Bukkit.getPluginManager().callEvent(new RejoinPlayerJoinEvent(player));
                game.getAllowReJoin().add(player.getName());
                UHC.getInstance().getGame().freeze(player);
            });

            Bukkit.getPluginManager().callEvent(new UHCGameStartEvent(game));

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player1 : game.getGamePlayers()) {
                        if (Frozen.isFrozen(player1)) {
                            ActionBarUtil.sendBar(player1, "§a§l正在传送中...");
                        }
                    }
                }
            }.runTaskTimerAsynchronously(UHC.getInstance(), 0, 20);

            new TeleportationTask();
        }
        countdown--;
        LobbyBoard.updateBoard();

        game.getPlayers().forEach(player -> {
            player.setLevel(countdown);
            if (countdown == 360) {
                player.setExp(1.0F);
            } else {
                player.setExp(1.0F - ((1.0F / 120) * (360 - countdown)));
            }
        });
    }
}
