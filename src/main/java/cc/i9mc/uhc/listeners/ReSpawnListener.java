package cc.i9mc.uhc.listeners;

import cc.i9mc.gameutils.utils.TitleUtil;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.GameState;
import cc.i9mc.uhc.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ReSpawnListener implements Listener {
    private Game game;

    public ReSpawnListener(UHC uhc) {
        this.game = uhc.getGame();

        uhc.getServer().getPluginManager().registerEvents(this, uhc);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Team team = game.getTeam(player);

        if (game.getGameState() != GameState.RUNNING) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
            event.setRespawnLocation(game.getGamePlayers().get(0).getLocation());

            TitleUtil.sendTitle(player, 0, 20, 0, "§c你凉了！", "§7继续加油");

            game.getPlayers().forEach((player1 -> player1.hidePlayer(player)));
            player.hidePlayer(player);
            game.toSpectator(player);

            if (team.getPlayers().isEmpty()) {
                UHC.getInstance().getCacher().get(player.getName()).setLoses(UHC.getInstance().getCacher().get(player.getName()).getLoses() + 1);
                UHC.getInstance().getCacher().get(player.getName()).setGames(UHC.getInstance().getCacher().get(player.getName()).getGames() + 1);
            }

            game.getSpectators().add(player.getName());
        }, 5L);
    }
}
