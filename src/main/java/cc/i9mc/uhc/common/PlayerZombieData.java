package cc.i9mc.uhc.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;

@Data
@AllArgsConstructor
public class PlayerZombieData {
    private OfflinePlayer player;
    private Inventory inventory;
    private EntityEquipment equipment;
    private int level;
    private long time;
    private Zombie zombie;
}
