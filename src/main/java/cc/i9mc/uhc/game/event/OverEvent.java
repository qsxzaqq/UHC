package cc.i9mc.uhc.game.event;

import cc.i9mc.uhc.event.UHCGameEndEvent;
import cc.i9mc.uhc.event.UHCGameOverEvent;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.GameOverRunnable;
import org.bukkit.Bukkit;

public class OverEvent extends GameEvent {
    public OverEvent() {
        super("游戏结束", 900, 3);
    }

    public void excute(Game game) {
        game.getEventManager().setCurrentEvent(4);
        Bukkit.getPluginManager().callEvent(new UHCGameOverEvent(game.getWinner()));
        new GameOverRunnable(game);
    }
}
