package cc.i9mc.uhc.listeners;

import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.GameState;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockListener implements Listener {
    private Game game;
    private List<Location> blocks;

    public BlockListener(UHC uhc) {
        this.game = uhc.getGame();
        this.blocks = new ArrayList<>();

        uhc.getServer().getPluginManager().registerEvents(this, uhc);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (game.getGameState() == GameState.WAITING) {
            event.setCancelled(true);
            return;
        }

        if (game.getEventManager().currentEvent().getPriority() == 2 || game.getEventManager().currentEvent().getPriority() == 3) {
            blocks.add(event.getBlock().getLocation());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        if (game.getGameState() == GameState.WAITING) {
            event.setCancelled(true);
        }

        if (game.getEventManager().currentEvent().getPriority() == 2 || game.getEventManager().currentEvent().getPriority() == 3) {
            if (!blocks.contains(event.getBlock().getLocation())) {
                event.setCancelled(true);
            }
        }
    }
}
