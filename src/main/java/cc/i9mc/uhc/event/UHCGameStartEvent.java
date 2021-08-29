package cc.i9mc.uhc.event;

import cc.i9mc.uhc.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UHCGameStartEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Game game;

    public UHCGameStartEvent(Game game) {
        this.game = game;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Game getGame() {
        return this.game;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
