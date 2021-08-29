package cc.i9mc.uhc.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UHCPlayerKilledEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player = null;
    private Player killer = null;

    public UHCPlayerKilledEvent(Player player, Player killer) {
        this.player = player;
        this.killer = killer;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Player getKiller() {
        return this.killer;
    }
}
