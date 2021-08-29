package cc.i9mc.uhc.commands;

import cc.i9mc.rejoin.events.RejoinPlayerJoinEvent;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.event.UHCGameStartEvent;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.GameState;
import cc.i9mc.uhc.task.TeleportationTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            Game game = UHC.getInstance().getGame();

            game.setForceStart(true);

            game.setGameState(GameState.RUNNING);
            game.moveFreePlayersToTeam();

            game.getPlayers().forEach(player -> {
                Bukkit.getPluginManager().callEvent(new RejoinPlayerJoinEvent(player));
                game.getAllowReJoin().add(player.getName());
                UHC.getInstance().getGame().freeze(player);
            });

            Bukkit.getPluginManager().callEvent(new UHCGameStartEvent(game));

            new TeleportationTask();
        }
        return false;
    }
}
