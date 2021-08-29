package cc.i9mc.uhc.listeners;

import cc.i9mc.dansync.DanSyncBukkit;
import cc.i9mc.nick.Nick;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.databse.PlayerData;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.GameState;
import cc.i9mc.uhc.game.Team;
import me.zhanshi123.globalprefix.GlobalPrefix;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private Game game;

    public ChatListener(UHC uhc) {
        this.game = uhc.getGame();

        uhc.getServer().getPluginManager().registerEvents(this, uhc);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = event.getMessage();
        PlayerData playerData = UHC.getInstance().getCacher().get(player.getName());
        int level = (playerData.getOldKills() * 2) + (playerData.getOldWins() * 15);
        String globalPrefix = ChatColor.translateAlternateColorCodes('&', UHC.getInstance().getChat().getPlayerPrefix(player));
        String gamePrefix = GlobalPrefix.getInstance().getCacher().get(player.getName()).getPrefix();
        cc.i9mc.dansync.database.PlayerData danData = DanSyncBukkit.getInstance().getCacher().contains(player.getName(), new cc.i9mc.dansync.database.PlayerData());
        String dan = danData == null ? "" : DanSyncBukkit.getLevel(Integer.parseInt(danData.getData().getOrDefault("level", String.valueOf(0))));

        if (game.getGameState() == GameState.RUNNING) {
            if (game.isSpectator(player)) {
                game.broadcastSpectatorMessage("§7[旁观者]" + gamePrefix + "§f" + Nick.get().getCache().getOrDefault(event.getPlayer().getName(), event.getPlayer().getName()) + "§7: " + message);
                return;
            }

            Team team = game.getTeam(player);
            boolean all = event.getMessage().startsWith("!");
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(all ? "§6[全局]" : "§9[团队]");
            stringBuilder.append("§6[").append(UHC.getInstance().getLevel(level)).append("✫]");
            stringBuilder.append(gamePrefix);
            stringBuilder.append("§7[").append(dan).append("§7]");
            stringBuilder.append(globalPrefix);
            stringBuilder.append(Nick.get().getCache().getOrDefault(player.getName(), player.getName()));
            stringBuilder.append("§7: ").append(all ? message.substring(1) : message);


            if (all) {
                game.broadcastMessage(stringBuilder.toString());
            } else {
                game.broadcastTeamMessage(team, stringBuilder.toString());
            }
            return;
        }

        game.broadcastMessage("§6[" + UHC.getInstance().getLevel(level) + "✫]" + gamePrefix + "§7[" + dan + "§7]" + globalPrefix + "§7" + Nick.get().getCache().getOrDefault(player.getName(), player.getName()) + ": " + message);
    }
}
