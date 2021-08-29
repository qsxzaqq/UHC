package cc.i9mc.uhc.game.event;

import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.utils.Util;
import org.bukkit.potion.PotionEffectType;

public class StartEvent extends GameEvent {
    public StartEvent() {
        super("开局保护阶段", 600, 0);
    }

    public void excute(Game game) {
        game.getGamePlayers().forEach(player -> player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE));
        game.getBorder().setSize(0, 5000);
        Util.playSoundToAll("WOLF_GROWL");
    }
}
