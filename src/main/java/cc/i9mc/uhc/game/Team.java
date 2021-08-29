package cc.i9mc.uhc.game;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Team {
    private boolean die;
    private org.bukkit.scoreboard.Team team;
    private Location spawn = null;
    private int id;
    private String playerString;


    public Team(int id) {
        this.id = id;

        die = false;
        team = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam(String.valueOf(id));
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (String aPlayer : team.getEntries()) {
            Player player = Bukkit.getPlayerExact(aPlayer);
            if (player != null) {
                players.add(player);
            }
        }

        return players;
    }

    public List<String> getPlayerNames() {
        return new ArrayList<>(team.getEntries());
    }


    public List<OfflinePlayer> getOfflinePlayers() {
        return new ArrayList<>(team.getPlayers());
    }

    public boolean isInTeam(String name) {
        return team.hasEntry(name);
    }

    public boolean isInTeam(Player player) {
        if (player == null) {
            return false;
        }
        return team.hasEntry(player.getName());
    }

    public boolean addPlayer(Player player) {
        team.addEntry(player.getName());
        return true;
    }

    public void removePlayer(Player player) {
        if (team.hasEntry(player.getName())) {
            team.removeEntry(player.getName());
        }
    }

    public String nextPlayer(String... playerStrings) {
        List<String> players = getPlayerNames();
        players.removeAll(Arrays.asList(playerStrings));

        if (players.isEmpty()) {
            if (playerStrings.length == 1) {
                return null;
            }

            return playerStrings[1];
        }

        return players.get(0);
    }
}