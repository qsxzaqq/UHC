package cc.i9mc.uhc.listeners;

import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.GameState;
import cc.i9mc.uhc.utils.Util;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class ServerListener implements Listener {
    private final Game game;
    private final List<EntityType> entityTypes;

    public ServerListener(UHC uhc) {
        this.game = uhc.getGame();

        entityTypes = new ArrayList<>();
        entityTypes.add(EntityType.COW);
        entityTypes.add(EntityType.MUSHROOM_COW);
        entityTypes.add(EntityType.SHEEP);
        entityTypes.add(EntityType.PIG);
        entityTypes.add(EntityType.PIG);
        entityTypes.add(EntityType.HORSE);
        entityTypes.add(EntityType.CHICKEN);
        entityTypes.add(EntityType.SQUID);



        uhc.getServer().getPluginManager().registerEvents(this, uhc);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Location loc = event.getEntity().getLocation();
        if (Math.round(loc.getX()) > 1400 || Math.round(loc.getZ()) > 1400) {
            event.setCancelled(true);
        }else {
            if(entityTypes.contains(event.getEntityType())){
                Util.setAI(event.getEntity(), false);
            }else if(!event.getEntity().hasMetadata("Game")){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if (game.getGameState() == GameState.RUNNING) {
            event.setMotd("开始了");
            return;
        }

        if (game.getGameState() == GameState.LOAD) {
            event.setMotd("加载中");
            return;
        }

        if (game.getPlayers().size() >= game.getMaxPlayers()) {
            event.setMotd("开始了");
            return;
        }

        event.setMotd("lobby");
    }
}
