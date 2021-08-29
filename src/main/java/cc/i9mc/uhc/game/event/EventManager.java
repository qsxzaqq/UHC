package cc.i9mc.uhc.game.event;

import cc.i9mc.gameutils.utils.ActionBarUtil;
import cc.i9mc.nick.Nick;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.utils.Util;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class EventManager extends TimerTask {
    private Game game;
    private Timer timer;
    private int currentEvent = 0;
    private int seconds = 0;
    @Getter
    private boolean over = false;
    @Getter
    private HashMap<String, Util.EventTimerRunnable> runnables = new HashMap<>();
    private HashMap<Integer, GameEvent> events = new HashMap<>();

    public EventManager(Game game) {
        this.game = game;
        this.registerEvent(new StartEvent());
        this.registerEvent(new PVPStartEvent());
        this.registerEvent(new DMStartEvent());
        this.registerEvent(new OverEvent());
        this.registerEvent(new EndEvent());
    }

    public void run() {
        GameEvent event = this.currentEvent();
        if (this.seconds >= event.getExcuteSeconds()) {
            this.seconds = 0;
            this.currentEvent = event.getPriority() + 1;
            event.excute(this.game);
        }

        if (game.isOver() && !over) {
            setCurrentEvent(3);
            currentEvent().excute(game);
            over = true;
        }

        //Start 队友针
        for (OfflinePlayer player : game.getGameOfflinePlayers()) {
            if (!player.isOnline()) {
                continue;
            }
            ItemStack itemStack = player.getPlayer().getItemInHand();

            if (itemStack != null && itemStack.getType() == Material.COMPASS && itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName().equals("§a队友追踪器§7(右键点击)")) {
                Player target = game.findTargetPlayerByTeam(player.getPlayer());

                if (target == null) {
                    continue;
                }
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(2);
                String result = numberFormat.format((float) target.getHealth() / (float) target.getMaxHealth() * 100);
                ActionBarUtil.sendBar(player.getPlayer(), "§f目标:§a§l" + Nick.get().getCache().getOrDefault(target.getName(), target.getName()) + "  §f生命值:§a§l" + result + "%  §f距离:§a§l" + ((int) player.getPlayer().getLocation().distance(target.getLocation())) + "米");
                player.getPlayer().setCompassTarget(target.getLocation());
            }
        }
        // END

        runnables.values().forEach(runnable -> runnable.run(seconds, currentEvent));

        ++this.seconds;
    }

    public GameEvent currentEvent() {
        return this.events.getOrDefault(this.currentEvent, this.events.get(4));
    }

    public void setCurrentEvent(int priority) {
        this.seconds = 0;
        this.currentEvent = priority;
    }

    public int getLeftTime() {
        return this.currentEvent().getExcuteSeconds() - this.seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public String formattedNextEvent() {
        GameEvent currentEvent = this.currentEvent();
        return currentEvent instanceof EndEvent ? currentEvent.getName() : currentEvent.getName();
    }

    public void registerRunnable(String name, Util.EventTimerRunnable runnable) {
        this.runnables.put(name, runnable);
    }

    private void registerEvent(GameEvent event) {
        this.events.put(event.getPriority(), event);
    }

    public void start() {
        if (this.timer == null) {
            timer = new Timer();
            timer.schedule(this, 0, 1000);
        }
    }

    public void stop() {
        if (this.timer != null) {
            timer.cancel();
            this.currentEvent = 0;
            this.seconds = 0;
        }
    }
}
