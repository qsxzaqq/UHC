package cc.i9mc.uhc.game.event;

import cc.i9mc.uhc.event.UHCGameEndEvent;
import cc.i9mc.uhc.game.Game;
import org.bukkit.Bukkit;

public class EndEvent extends GameEvent {
    public EndEvent() {
        super("游戏结束！", 30, 4);
    }

    public void excute(Game game) {
        Bukkit.getPluginManager().callEvent(new UHCGameEndEvent());
        Bukkit.shutdown();
    }
}
