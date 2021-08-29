package cc.i9mc.uhc.databse;

import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class PlayerData {
    private String name;
    private String kit;
    private int kills;
    private int deaths;
    private int wins;
    private int loses;
    private int games;

    private int oldKills;
    private int oldWins;
    private String compassPlayer;

    public PlayerData(String name, String kit, int oldKills, int oldWins) {
        this.name = name;
        this.kit = kit;
        this.oldKills = oldKills;
        this.oldWins = oldWins;
    }
}