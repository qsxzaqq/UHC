package cc.i9mc.uhc.scoreboards;

import cc.i9mc.gameutils.utils.board.Board;
import cc.i9mc.nick.Nick;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.databse.PlayerData;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.GameLobbyCountdown;
import cc.i9mc.uhc.game.GameState;
import cc.i9mc.uhc.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LobbyBoard {
    private static Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
    private static Objective hp;
    private static Objective o;


    public static Scoreboard getBoard() {
        return sb;
    }

    public static void show(Player p) {
        if (hp == null) {
            hp = sb.registerNewObjective("NAME_HEALTH", "health");
            hp.setDisplaySlot(DisplaySlot.BELOW_NAME);
            hp.setDisplayName(ChatColor.GOLD + "✫");
        }
        if (o == null) {
            o = sb.registerNewObjective("health", "dummy");
            o.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        }

        p.setScoreboard(sb);
    }

    public static void updateBoard() {
        Game game = UHC.getInstance().getGame();
        for (Map.Entry<UUID, Board> boardEntry : game.getBoardManager().getBoardMap().entrySet()) {
            UUID uuid = boardEntry.getKey();
            Board board = boardEntry.getValue();
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                continue;
            }

            PlayerData playerData = UHC.getInstance().getCacher().get(player.getName());
            if (playerData == null) {
                continue;
            }
            int level = UHC.getInstance().getLevel((playerData.getOldKills() * 5) + ((playerData.getOldWins() * 20) * 3));

            hp.getScore(player.getName()).setScore(level);
            o.getScore(player.getName()).setScore(level);

            List<String> list = new ArrayList<>();

            list.add(" ");
            list.add("§f玩家: §a" + game.getPlayers().size() + "/" + game.getMaxPlayers());
            list.add("  ");
            list.add(getCountdown());
            list.add("   ");
            list.add("§f队友:");
            Team team = game.getTeam(player);
            if (team != null && team.getPlayers().size() > 1) {
                int i = 0;
                for (Player player1 : team.getPlayers()) {
                    if (player.equals(player1)) {
                        continue;
                    }

                    list.add("§a" + Nick.get().getCache().getOrDefault(player1.getName(), player1.getName()));
                    i++;
                }

                if (i == 1) {
                    list.add("§a随机  ");
                }
            } else {
                list.add("§a随机 ");
                list.add("§a随机  ");
            }
            list.add("    ");
            list.add("§f版本: §a" + UHC.getInstance().getDescription().getVersion());
            list.add("§f服务器: §aUHC-TEAM-" + String.valueOf(Bukkit.getPort()).substring(String.valueOf(Bukkit.getPort()).length() - 2));
            list.add("     ");
            list.add("§emcyc.win");


            board.send("§e§l极限生存", list);
        }
    }

    private static String getCountdown() {
        Game game = UHC.getInstance().getGame();
        GameLobbyCountdown gameLobbyCountdown = game.getGameLobbyCountdown();

        if (gameLobbyCountdown != null) {
            return gameLobbyCountdown.getCountdown() + "秒后开始";
        } else if (game.getGameState() == GameState.WAITING) {
            return "§f等待中...";
        }

        return null;
    }
}
