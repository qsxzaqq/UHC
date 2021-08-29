package cc.i9mc.uhc;

import cc.i9mc.gameutils.BukkitGameUtils;
import cc.i9mc.uhc.commands.AdminCommand;
import cc.i9mc.uhc.commands.NBTShowCommand;
import cc.i9mc.uhc.commands.NextEventCommand;
import cc.i9mc.uhc.commands.StartCommand;
import cc.i9mc.uhc.databse.Cacher;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.kit.KitManager;
import cc.i9mc.uhc.listeners.*;
import cc.i9mc.uhc.profession.ProfessionManager;
import cc.i9mc.uhc.scoreboards.GameBoard;
import cc.i9mc.uhc.utils.BiomeReplacerUtil;
import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class UHC extends JavaPlugin {
    public static HashMap<Integer, Integer> playerLevel = new HashMap<>();
    @Getter
    private static UHC instance;
    @Getter
    private Game game;
    @Getter
    private Cacher cacher;
    @Getter
    private Economy econ = null;
    @Getter
    private Chat chat = null;
    @Getter
    private BiomeReplacerUtil biomeReplacerUtil;

    @Override
    public void onEnable() {
        instance = this;
        long time = System.currentTimeMillis();

        if (getConfig().getBoolean("isUP")) {
            WorldCreator cr = new WorldCreator("lobby");
            cr.environment(World.Environment.NORMAL);
            World mapWorld = Bukkit.createWorld(cr);
            mapWorld.setAutoSave(false);
            mapWorld.setGameRuleValue("doMobSpawning", "false");
            mapWorld.setGameRuleValue("doFireTick", "false");
            BukkitGameUtils.getInstance().getConnectionPoolHandler().registerDatabase("uhcstats");

            biomeReplacerUtil = new BiomeReplacerUtil();
            game = new Game(this, 8, 111, new Location(Bukkit.getWorld("lobby"), 53.5, 80.0, 50.5, -1.4999137f, -0.90004534f));
            cacher = new Cacher();
            KitManager.init();
            ProfessionManager.init(game);

            setupEconomy();
            setupChat();

            new ChatListener(this);
            new ReSpawnListener(this);
            new PlayerListener(this);
            new DamageListener(this);
            new BlockListener(this);
            new ServerListener(this);
            new ChunkListener(this);
            new Frozen(this);
            Bukkit.getPluginManager().registerEvents(new GameBoard(game), this);

            Bukkit.getPluginCommand("start").setExecutor(new StartCommand());
            Bukkit.getPluginCommand("nextevent").setExecutor(new NextEventCommand());
            Bukkit.getPluginCommand("ns").setExecutor(new NBTShowCommand());
            loadLevel();

            Bukkit.getConsoleSender().sendMessage("[UHC] 加载完成耗时 " + (System.currentTimeMillis() - time) + " ms");
        }

        Bukkit.getPluginCommand("game").setExecutor(new AdminCommand());
    }

    private void loadLevel() {
        playerLevel.put(1, 0);
        playerLevel.put(2, 10);
        playerLevel.put(3, 25);
        playerLevel.put(4, 45);
        playerLevel.put(5, 100);
        playerLevel.put(6, 220);
        playerLevel.put(7, 450);
        playerLevel.put(8, 800);
        playerLevel.put(9, 900);
        playerLevel.put(10, 1050);
        playerLevel.put(11, 1800);
        playerLevel.put(12, 2600);
        playerLevel.put(13, 3450);
        playerLevel.put(14, 4200);
        playerLevel.put(15, 5450);
        playerLevel.put(16, 6150);
        playerLevel.put(17, 6850);
        playerLevel.put(18, 7550);
        playerLevel.put(19, 8250);
        playerLevel.put(20, 8900);
        playerLevel.put(21, 10000);
        playerLevel.put(22, 11250);
        playerLevel.put(23, 12500);
        playerLevel.put(24, 13750);
        playerLevel.put(25, 15000);
        playerLevel.put(26, 16250);
        playerLevel.put(27, 17500);
        playerLevel.put(28, 18750);
        playerLevel.put(29, 20000);
        playerLevel.put(30, 22000);
        playerLevel.put(31, 24000);
        playerLevel.put(32, 26000);
        playerLevel.put(33, 28000);
        playerLevel.put(34, 30000);
        playerLevel.put(35, 32000);
        playerLevel.put(36, 34000);
        playerLevel.put(37, 36000);
        playerLevel.put(38, 38000);
        playerLevel.put(39, 40000);
        playerLevel.put(40, 45000);
        playerLevel.put(41, 50000);
        playerLevel.put(42, 55000);
        playerLevel.put(43, 60000);
        playerLevel.put(44, 65000);
        playerLevel.put(45, 70000);
        playerLevel.put(46, 75000);
        playerLevel.put(47, 80000);
        playerLevel.put(48, 85000);
        playerLevel.put(49, 90000);
        playerLevel.put(50, 100000);
    }

    public int getLevel(int score) {
        int level = 0;
        for (Map.Entry<Integer, Integer> entry : playerLevel.entrySet()) {
            if (score > entry.getValue()) {
                level = entry.getKey();
            } else break;
        }
        return level;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
}
