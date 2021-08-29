package cc.i9mc.uhc.scoreboards;

import cc.i9mc.gameutils.utils.board.Board;
import cc.i9mc.nick.Nick;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.event.UHCGameStartEvent;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.Team;
import cc.i9mc.uhc.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.SimpleDateFormat;
import java.util.*;

public class GameBoard implements Listener {
    private static Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
    private static Objective hp;
    private static Objective o;

    private static Game game;

    public GameBoard(Game game) {
        GameBoard.game = game;
    }

    public static Scoreboard getBoard() {
        return sb;
    }

    public static void show(Player p) {
        p.setScoreboard(sb);
    }

    public static void updateBoard() {
        for (Map.Entry<UUID, Board> boardEntry : game.getBoardManager().getBoardMap().entrySet()) {
            UUID uuid = boardEntry.getKey();
            Board board = boardEntry.getValue();
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                continue;
            }
            hp.getScore(player.getName()).setScore((int) player.getHealth());
            o.getScore(player.getName()).setScore((int) player.getHealth());

            List<String> list = new ArrayList<>();
            list.add("§7" + new SimpleDateFormat("MM", Locale.CHINESE).format(Calendar.getInstance().getTime()) + "/" + new SimpleDateFormat("dd", Locale.CHINESE).format(Calendar.getInstance().getTime()) + "/" + new SimpleDateFormat("yy", Locale.CHINESE).format(Calendar.getInstance().getTime()) + " §8M" + String.valueOf(Bukkit.getPort()).substring(String.valueOf(Bukkit.getPort()).length() - 2) + " " + UHC.getInstance().getDescription().getVersion());
            list.add(" ");
            list.add(game.getEventManager().formattedNextEvent());
            list.add("§a" + game.getFormattedTime(game.getEventManager().getLeftTime()));
            list.add("  ");
            list.add("§f存活队伍:§a" + game.getAliveTeams().size());
            list.add("§f存活玩家:§a" + game.getAlivePlayers());
            list.add("   ");
            list.add("§f队友:");
            //list.add("§f§l⬉⬆⬈⬅➡⬋⬇⬊");
            Team team = game.getTeam(player);
            if (team != null && team.getOfflinePlayers().size() > 1) {
                for (OfflinePlayer player1 : team.getOfflinePlayers()) {
                    if (player1.getName().equals(player.getName())) {
                        continue;
                    }

                    if (player1.isOnline()) {
                        list.add("§f§l" + Util.getDirection(player, player1.getPlayer()) + " §a" + Nick.get().getCache().getOrDefault(player1.getName(), player1.getName()));
                    } else {
                        list.add("§7" + Nick.get().getCache().getOrDefault(player1.getName(), player1.getName()));
                    }
                }
            } else {
                list.add("§a无");
            }
            list.add("    ");
            if (UHC.getInstance().getCacher().contains(player.getName())) {
                list.add("§f击杀数:§a" + UHC.getInstance().getCacher().get(player.getName()).getKills());
            } else {
                list.add("§f击杀数:§a0");
            }

            list.add("    ");
            if (game.getEventManager().currentEvent().getPriority() >= 2) {
                list.add("§a-" + Math.floor(game.getDmBorder().getSize() / 2) + ", +" + Math.floor(game.getDmBorder().getSize() / 2));
            } else {
                list.add("§a-" + Math.floor(game.getBorder().getSize() / 2) + ", +" + Math.floor(game.getBorder().getSize() / 2));
            }


            board.send("§e§l游戏世界 极限生存", list);
        }
    }

    @EventHandler
    public void onStart(UHCGameStartEvent e) {
        if (hp == null) {
            hp = sb.registerNewObjective("NAME_HEALTH", "health");
            hp.setDisplaySlot(DisplaySlot.BELOW_NAME);
            hp.setDisplayName(ChatColor.RED + "❤");
        }
        if (o == null) {
            o = sb.registerNewObjective("health", "dummy");
            o.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        }

        Util.setPlayerTeamTab();

        for (Player player : Bukkit.getOnlinePlayers()) {
            show(player);
        }

        game.getEventManager().registerRunnable("计分板", (s, c) -> updateBoard());
    }
}
