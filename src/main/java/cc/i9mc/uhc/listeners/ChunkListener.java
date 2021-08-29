package cc.i9mc.uhc.listeners;

import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.game.Game;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.HashSet;
import java.util.Set;

public class ChunkListener implements Listener {
    public static Set<Chunk> keepChunk = new HashSet<>();
    private Game game;

    public ChunkListener(UHC uhc) {
        this.game = uhc.getGame();

        uhc.getServer().getPluginManager().registerEvents(this, uhc);
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        if (keepChunk.contains(event.getChunk())) {
            event.setCancelled(true);
        }
    }
}
