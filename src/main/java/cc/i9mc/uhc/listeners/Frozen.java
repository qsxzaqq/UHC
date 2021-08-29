package cc.i9mc.uhc.listeners;

import cc.i9mc.uhc.UHC;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Frozen implements Listener {
    public static List<String> frozen = new ArrayList<>();
    public static HashMap<String, Integer> fireticks = new HashMap<>();
    public static HashMap<String, Collection<PotionEffect>> effects = new HashMap<>();

    public Frozen(UHC uhc) {
        uhc.getServer().getPluginManager().registerEvents(this, uhc);
    }

    public static Boolean isFrozen(Player p) {
        return frozen.contains(p.getName());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onMove(final PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (isFrozen(p)) {
            if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                Location loc = p.getLocation();
                loc.setX(e.getFrom().getBlockX());
                loc.setZ(e.getFrom().getBlockZ());
                loc.add(0.5, 0, 0.5);
                e.getPlayer().teleport(loc);
            }
        }
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent e) {
        if (isFrozen(e.getPlayer().getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().setVelocity(new Vector().zero());
        }
    }
}