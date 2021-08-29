package cc.i9mc.uhc.game;

import cc.i9mc.gameutils.utils.ActionBarUtil;
import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.gameutils.utils.LoggerUtil;
import cc.i9mc.gameutils.utils.TitleUtil;
import cc.i9mc.gameutils.utils.board.Board;
import cc.i9mc.gameutils.utils.board.BoardManager;
import cc.i9mc.nick.Nick;
import cc.i9mc.pluginchannel.BukkitChannel;
import cc.i9mc.pluginchannel.bukkit.PBukkitChannelTask;
import cc.i9mc.rejoin.events.RejoinGameDeathEvent;
import cc.i9mc.rejoin.events.RejoinPlayerJoinEvent;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.common.PlayerZombieData;
import cc.i9mc.uhc.common.Swap;
import cc.i9mc.uhc.databse.Database;
import cc.i9mc.uhc.databse.PlayerData;
import cc.i9mc.uhc.event.UHCGameStartEvent;
import cc.i9mc.uhc.game.event.EventManager;
import cc.i9mc.uhc.listeners.ChunkListener;
import cc.i9mc.uhc.listeners.Frozen;
import cc.i9mc.uhc.loader.WorldFillTask;
import cc.i9mc.uhc.scoreboards.GameBoard;
import cc.i9mc.uhc.scoreboards.LobbyBoard;
import cc.i9mc.uhc.task.TeleportationTask;
import cc.i9mc.uhc.utils.Util;
import com.nametagedit.plugin.NametagEdit;
import lombok.Data;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

@Data
public class Game {
    private UHC main;
    private GameState gameState;
    private boolean forceStart;

    private int minPlayers;
    private int maxPlayers;
    private int teamPlayers;

    private WorldBorder border = null;
    private WorldBorder dmBorder = null;

    private Location waitingLocation;

    private GameLobbyCountdown gameLobbyCountdown = null;
    private List<Team> teams;
    private List<String> spectators;

    private HashMap<Player, List<Player>> Party;
    private List<PlayerZombieData> playerZombies;
    private HashMap<Player, Player> playerDamages;
    private List<String> allowReJoin;

    private boolean start = false;

    private List<Location> spawns;
    private List<Location> dmList;

    private EventManager eventManager;
    private WorldFillTask fillTask;
    private BoardManager boardManager;

    public Game(UHC main, int minPlayers, int maxPlayers, Location waitingLocation) {
        this.main = main;
        this.gameState = GameState.LOAD;
        this.forceStart = false;

        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.teamPlayers = 3;

        this.waitingLocation = waitingLocation;

        this.teams = new ArrayList<>();
        this.spectators = new ArrayList<>();

        this.playerZombies = new ArrayList<>();
        this.playerDamages = new HashMap<>();
        this.allowReJoin = new ArrayList<>();

        for (int i = 0; i < 37; i++) {
            teams.add(new Team(i));
        }

        this.Party = new HashMap<>();
        this.spawns = new ArrayList<>();
        this.dmList = new ArrayList<>();

        List<Swap> swaps = new ArrayList<>();
        swaps.add(new Swap("DEEP_OCEAN", "PLAINS"));
        swaps.add(new Swap("OCEAN", "EXTREME_HILLS"));
        swaps.add(new Swap("BEACHES", "MUSHROOM_ISLAND"));
        swaps.add(new Swap("DESERT", "PLAINS"));
        swaps.add(new Swap("DESERT_HILLS", "FOREST"));

        for (Swap swap : swaps) {
            swap.execute();
        }

        prepareWorld();

        for (String d : UHC.getInstance().getConfig().getStringList("dm")) {
            dmList.add(Util.stringToLocation(d));
        }

        boardManager = new BoardManager();
        eventManager = new EventManager(this);

        dmBorder = Bukkit.getWorld("dmWorld").getWorldBorder();
        dmBorder.setCenter(0, 999);
        dmBorder.setSize(202);

        border = Bukkit.getWorld("uhcWorld").getWorldBorder();
        border.setCenter(0, 0);
        border.setSize(2800);

        /*for (int i = 0; i < 37; i++) {
            loadSpawn();
        }*/

        startLoadChunks();
    }

    private void prepareWorld() {
        WorldCreator cr = new WorldCreator("dmWorld");
        cr.environment(World.Environment.NORMAL);
        World mapWorld = Bukkit.createWorld(cr);

        mapWorld.setAutoSave(false);
        mapWorld.setGameRuleValue("doMobSpawning", "false");
        mapWorld.setGameRuleValue("doFireTick", "false");
        mapWorld.setGameRuleValue("naturalRegeneration", "false");
        mapWorld.setGameRuleValue("doDaylightCycle", "false");

        deleteWorld(new File(Bukkit.getWorldContainer(), "uhcWorld"));
        cr = new WorldCreator("uhcWorld");
        cr.environment(World.Environment.NORMAL);
        mapWorld = Bukkit.createWorld(cr);

        mapWorld.setAutoSave(false);
        mapWorld.setGameRuleValue("naturalRegeneration", "false");
        mapWorld.setGameRuleValue("doDaylightCycle", "false");

        Bukkit.getServer().setSpawnRadius(0);
    }

    private Boolean deleteWorld(File f) {
        if (f.exists() && f.isDirectory()) {
            File[] listFiles;
            for (int length = (listFiles = f.listFiles()).length, i = 0; i < length; i++) {
                File f2 = listFiles[i];
                if (!deleteWorld(f2)) {
                    return false;
                }
            }
        }
        return f.delete();
    }


    public void loadSpawn() {
        World w = Bukkit.getWorld("uhcWorld");
        List<Material> mat = new ArrayList<>(Arrays.asList(Material.LEAVES, Material.LEAVES_2, Material.WATER, Material.LAVA, Material.LEAVES, Material.AIR, Material.WATER_LILY, Material.STATIONARY_WATER, Material.STATIONARY_LAVA));
        Location tp = Util.getRandomLocation(w, 1000, true, mat);
        ChunkListener.keepChunk.addAll(Util.loadChunks(tp, 50, false));
        spawns.add(tp);
    }

    private void startLoadChunks() {
        Util.Callback callback = () -> {
            setGameState(GameState.WAITING);

            for (int i = 0; i < 37; i++) {
                loadSpawn();
            }
        };

        this.fillTask = new WorldFillTask(Bukkit.getServer(), "uhcWorld", 1500, 2000, callback);
        if (fillTask.valid()) {
            int task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(UHC.getInstance(), fillTask, 1, 1);
            fillTask.setTaskID(task);
            LoggerUtil.info("World map generation task started.");
        } else
            LoggerUtil.error("The world map generation task failed to start.");
    }

    public void addPlayer(Player player) {
        if (gameState == GameState.WAITING) {
            getPlayers().forEach((player1 -> {
                player1.hidePlayer(player);
                player1.showPlayer(player);

                player.hidePlayer(player);
                player.showPlayer(player);
            }));
        }

        if (gameState == GameState.RUNNING) {
            boardManager.getBoardMap().put(player.getUniqueId(), new Board(player, "SB", Collections.singletonList("Test")));
            GameBoard.show(player);

            getSpectators().forEach(player1 -> {
                if (Bukkit.getPlayerExact(player1) != null) {
                    player.hidePlayer(Bukkit.getPlayerExact(player1));
                }
            });

            Team team = getTeam(player);
            PlayerZombieData playerZombieData = null;
            for (PlayerZombieData playerZombieData1 : getPlayerZombies()) {
                if (playerZombieData1.getPlayer().getName().equals(player.getName())) {
                    playerZombieData = playerZombieData1;
                }
            }

            if (team != null && allowReJoin.contains(player.getName()) && playerZombieData != null) {
                Zombie zombie = playerZombieData.getZombie();
                player.teleport(zombie);
                player.setMaxHealth(40);
                player.setHealth(playerZombieData.getZombie().getHealth());
                if (player.getEquipment() != null) {
                    if (player.getEquipment().getHelmet() != null && player.getEquipment().getHelmet().getType() != Material.AIR) {
                        player.getEquipment().setHelmet(zombie.getEquipment().getHelmet());
                    }
                    if (player.getEquipment().getChestplate() != null && player.getEquipment().getChestplate().getType() != Material.AIR) {
                        player.getEquipment().setChestplate(zombie.getEquipment().getChestplate());
                    }
                    if (player.getEquipment().getLeggings() != null && player.getEquipment().getLeggings().getType() != Material.AIR) {
                        player.getEquipment().setLeggings(zombie.getEquipment().getLeggings());
                    }
                    if (player.getEquipment().getBoots() != null && player.getEquipment().getBoots().getType() != Material.AIR) {
                        player.getEquipment().setBoots(zombie.getEquipment().getBoots());
                    }
                }

                zombie.remove();
                getPlayerZombies().remove(playerZombieData);

                Util.setPlayerTeamTab();
                broadcastMessage("§7" + Nick.get().getCache().getOrDefault(player.getName(), player.getName()) + "§a重连上线");
                return;
            }

            Bukkit.getPluginManager().callEvent(new RejoinGameDeathEvent(player));
            allowReJoin.remove(player.getName());

            getGamePlayers().forEach((player1 -> player1.hidePlayer(player)));

            player.teleport(getGamePlayers().get(0));
            if (!spectators.contains(player.getName())) {
                spectators.add(player.getName());
            }

            toSpectator(player);
            return;
        }

        player.spigot().respawn();
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setExp(0.0F);
        player.setLevel(0);
        player.setSneaking(false);
        player.setSprinting(false);
        player.setFoodLevel(20);
        player.setSaturation(5.0f);
        player.setExhaustion(0.0f);
        player.setMaxHealth(40.0D);
        player.setHealth(40.0f);
        player.setFireTicks(0);

        PlayerInventory inv = player.getInventory();
        inv.setArmorContents(new ItemStack[4]);
        inv.setContents(new ItemStack[]{});
        player.getEnderChest().clear();
        player.getActivePotionEffects().forEach((potionEffect -> player.removePotionEffect(potionEffect.getType())));

        Bukkit.getScheduler().runTaskAsynchronously(UHC.getInstance(), () -> PBukkitChannelTask.createTask()
                .channel(BukkitChannel.getInst().getBukkitChannel())
                .sender(player)
                .command("BungeeParty", "data", player.getName())
                .result((result) -> {
                    List<String> results = Arrays.asList(result);

                    if (Bukkit.getPlayerExact(results.get(1)) == null) {
                        return;
                    }

                    if (getParty().containsKey(Bukkit.getPlayerExact(results.get(1)))) {
                        return;
                    }

                    String name = results.get(0);

                    LinkedList<Player> data = new LinkedList<>();

                    for (int i = 1; i < results.size(); i++) {
                        if (Bukkit.getPlayerExact(results.get(i)) == null) {
                            return;
                        }

                        data.add(Bukkit.getPlayerExact(results.get(i)));
                    }

                    if (gameState == GameState.RUNNING) {
                        return;
                    }

                    if (data.size() == 1) {
                        return;
                    }

                    getParty().put(Bukkit.getPlayerExact(result[1]), data);
                    broadcastMessage("§a队长§e" + result[1] + "§a带着他的队伍§e" + name + "§a加入了");

                    Bukkit.getScheduler().runTask(UHC.getInstance(), () -> {
                        for (Player player1 : data) {
                            if (getTeam(player1) != null) {
                                getTeam(player1).removePlayer(player1);
                            }
                        }

                        for (Team team : teams) {
                            if (data.size() <= (getTeamPlayers() - team.getPlayers().size())) {
                                for (Player player1 : data) {
                                    team.addPlayer(player1);
                                }
                                return;
                            }
                        }
                    });
                }).run());

        broadcastMessage("§7" + ChatColor.translateAlternateColorCodes('&', main.getChat().getPlayerPrefix(player)).replace("[VIP]", "") + Nick.get().getCache().getOrDefault(player.getName(), player.getName()) + "§e加入游戏!");
        NametagEdit.getApi().setPrefix(player, main.getChat().getPlayerPrefix(player).replace("[VIP]", ""));
        if (Nick.get().getCache().get(player.getName()) != null) {
            NametagEdit.getApi().setSuffix(player, "" + Nick.get().getCache().get(player.getName()) + "");
        }

//        UHC.getInstance().getCacher().add(player.getName(), Database.getPlayerData(player));
        player.teleport(getWaitingLocation());

        boardManager.getBoardMap().put(player.getUniqueId(), new Board(player, "SB", Collections.singletonList("Test")));
        LobbyBoard.show(player);
        LobbyBoard.updateBoard();


        player.getInventory().addItem(new ItemBuilderUtil().setType(Material.ENCHANTED_BOOK).setDisplayName("§a合成书§7(右键查看)").getItem());
        player.getInventory().setItem(4, new ItemBuilderUtil().setType(Material.BOOK).setDisplayName("§a职业选择§7(右键选择)").getItem());
        player.getInventory().setItem(8, new ItemBuilderUtil().setType(Material.SLIME_BALL).setDisplayName("§c离开游戏§7(右键离开)").getItem());

        if (isStartable()) {
            if (gameState == GameState.WAITING && getGameLobbyCountdown() == null) {
                GameLobbyCountdown lobbyCountdown = new GameLobbyCountdown(this);
                lobbyCountdown.runTaskTimer(UHC.getInstance(), 20L, 20L);
                setGameLobbyCountdown(lobbyCountdown);
            }
        }
    }

    public void removePlayers(Player player) {
        if (gameState == GameState.WAITING) {
            broadcastMessage("§7" + Nick.get().getCache().getOrDefault(player.getName(), player.getName()) + "§e离开游戏");
            return;
        }

        if (UHC.getInstance().getCacher().contains(player.getName())) {
            PlayerData playerData = UHC.getInstance().getCacher().get(player.getName());
            Database.savePlayerData(playerData);
        }


        if (isSpectator(player)) {
            return;
        }

        Zombie zombie = player.getWorld().spawn(player.getLocation(), Zombie.class);
        zombie.setBaby(false);
        zombie.setCustomName(Nick.get().getCache().getOrDefault(player.getName(), player.getName()));
        zombie.getEquipment().setArmorContents(player.getEquipment().getArmorContents());
        if (zombie.getEquipment().getHelmet().getType() == Material.AIR) {
            zombie.getEquipment().setHelmet(new ItemBuilderUtil().setOwner(player.getName()).getItem());
        }
        zombie.setMaxHealth(40);
        zombie.setHealth(player.getHealth());
        zombie.setMetadata("Game", new FixedMetadataValue(UHC.getInstance(), ""));
        zombie.setMetadata("player", new FixedMetadataValue(UHC.getInstance(), player.getName()));
        Util.setAI(zombie, false);
        playerZombies.add(new PlayerZombieData(Bukkit.getOfflinePlayer(player.getUniqueId()), player.getInventory(), player.getEquipment(), player.getTotalExperience(), System.currentTimeMillis(), zombie));

        /*EntityZombie entityZombie = ((CraftZombie) zombie).getHandle();
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);

            Iterator iterator = ((List) bField.get(entityZombie.goalSelector)).iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                Field aField = o.getClass().getDeclaredField("a");
                aField.setAccessible(true);
                Object o1 = aField.get(o);

                if (o1 instanceof PathfinderGoalMoveThroughVillage) {
                    iterator.remove();
                    continue;
                }

                if (o1 instanceof PathfinderGoalMeleeAttack) {
                    iterator.remove();
                    continue;
                }

                if (o1 instanceof PathfinderGoalRandomLookaround) {
                    iterator.remove();
                    continue;
                }

                if (o1 instanceof PathfinderGoalLookAtPlayer) {
                    iterator.remove();
                    continue;
                }

                if (o1 instanceof PathfinderGoalRandomStroll) {
                    iterator.remove();
                    continue;
                }

                if (o1 instanceof PathfinderGoalFloat) {
                    iterator.remove();
                }
            }

            playerZombies.add(new PlayerZombieData(Bukkit.getOfflinePlayer(player.getUniqueId()), player.getInventory(), player.getEquipment(), player.getTotalExperience(), System.currentTimeMillis(), zombie));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }*/
    }

    public ArrayList<OfflinePlayer> getGameOfflinePlayers() {
        ArrayList<OfflinePlayer> players = new ArrayList<>();
        teams.forEach(team -> players.addAll(team.getOfflinePlayers()));
        return players;
    }

    public ArrayList<Player> getGamePlayers() {
        ArrayList<Player> players = new ArrayList<>();
        teams.forEach(team -> players.addAll(team.getPlayers()));
        return players;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    boolean hasEnoughPlayers() {
        return getPlayers().size() >= minPlayers;
    }

    public Team getLowestTeam() {
        Team lowest = null;
        for (Team team : teams) {
            if (lowest == null) {
                lowest = team;
                continue;
            }

            if (team.getPlayers().size() < lowest.getPlayers().size()) {
                lowest = team;
            }
        }

        return lowest;
    }


    public void moveFreePlayersToTeam() {
        for (Player player : getPlayers()) {
            if (getTeam(player) != null) {
                continue;
            }

            Team lowest = getLowestTeam();
            lowest.addPlayer(player);
        }
    }

    public Player findTargetPlayer(Player player) {
        Player foundPlayer = null;
        double distance = Double.MAX_VALUE;

        Team team = getTeam(player);

        ArrayList<Player> possibleTargets = new ArrayList<>(getGamePlayers());
        possibleTargets.removeAll(team.getPlayers());


        for (Player player1 : possibleTargets) {
            if (player.getWorld() != player1.getWorld()) {
                continue;
            }

            double dist = player.getLocation().distance(player1.getLocation());
            if (dist < distance) {
                foundPlayer = player1;
                distance = dist;
            }
        }

        return foundPlayer;
    }

    public Player findTargetPlayerByTeam(Player player) {
        Player foundPlayer = null;
        double distance = Double.MAX_VALUE;

        Team team = getTeam(player);
        List<Player> players = new ArrayList<>(team.getPlayers());
        players.remove(player);

        for (Player player1 : players) {
            if (player.getWorld() != player1.getWorld()) {
                continue;
            }

            double dist = player.getLocation().distance(player1.getLocation());
            if (dist < distance) {
                foundPlayer = player1;
                distance = dist;
            }
        }

        return foundPlayer;
    }

    public Team getTeam(Player p) {
        for (Team tea : teams) {
            if (tea.isInTeam(p)) {
                return tea;
            }
        }
        return null;
    }

    public Team getTeam(String name) {
        for (Team tea : teams) {
            if (tea.isInTeam(name)) {
                return tea;
            }
        }
        return null;
    }

    public void toSpectator(Player player) {
        player.setMaxHealth(40);
        player.setHealth(40);
        player.setGameMode(GameMode.ADVENTURE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        player.setAllowFlight(true);
        player.setFlying(true);
        player.spigot().setCollidesWithEntities(false);

        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§a§l传送器§7(右键打开)");
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(0, itemStack);

        itemStack = new ItemStack(Material.REDSTONE_COMPARATOR);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§c§l旁观者设置§7(右键打开)");
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(4, itemStack);

        itemStack = new ItemStack(Material.PAPER);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§b§l快速加入§7(右键加入)");
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(7, itemStack);

        itemStack = new ItemStack(Material.SLIME_BALL);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§c离开游戏§7(右键离开)");
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(8, itemStack);
    }

    public void broadcastTeamTitle(Team team, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subTitle) {
        team.getPlayers().forEach(player -> TitleUtil.sendTitle(player, fadeIn, stay, fadeOut, title, subTitle));
    }

    public void broadcastTitle(Integer fadeIn, Integer stay, Integer fadeOut, String title, String subTitle) {
        getPlayers().forEach(player -> TitleUtil.sendTitle(player, fadeIn, stay, fadeOut, title, subTitle));
    }

    public void broadcastActionBarr(String message) {
        getPlayers().forEach(player -> ActionBarUtil.sendBar(player, message));
    }


    public void broadcastTeamMessage(Team team, String... texts) {
        team.getPlayers().forEach(player -> Arrays.asList(texts).forEach(player::sendMessage));
    }

    public void broadcastMessage(String... texts) {
        getPlayers().forEach(player -> Arrays.asList(texts).forEach(player::sendMessage));
    }

    public void broadcastSpectatorMessage(String... texts) {
        getSpectators().forEach(name -> Arrays.asList(texts).forEach(message -> {
            Player player = Bukkit.getPlayerExact(name);
            if (player != null) {
                player.sendMessage(texts);
            }
        }));
    }

    public void broadcastSound(Sound sound, float v, float v1) {
        getPlayers().forEach(player -> player.playSound(player.getLocation(), sound, v, v1));
    }

    public Player getPlayerDamager(Player p) {
        return this.playerDamages.get(p);
    }

    public void setPlayerDamager(Player p, Player damager) {
        this.playerDamages.remove(p);
        this.playerDamages.put(p, damager);
    }

    public boolean isStartable() {
        return (this.hasEnoughPlayers() && this.hasEnoughTeams());
    }

    public boolean hasEnoughTeams() {
        int teamsWithPlayers = 0;
        for (Team team : teams) {
            if (team.getPlayers().size() > 0) {
                teamsWithPlayers++;
            }
        }

        List<Player> freePlayers = getPlayers();
        freePlayers.removeAll(getGamePlayers());

        return (teamsWithPlayers > 1 || (teamsWithPlayers == 1 && freePlayers.size() >= 1) || (teamsWithPlayers == 0 && freePlayers.size() >= 2));
    }

    public boolean isSpectator(Player player) {
        return (gameState == GameState.RUNNING && getSpectators().contains(player.getName()));
    }

    public List<Team> getAliveTeams() {
        List<Team> teams = new ArrayList<>();

        this.teams.forEach(team -> {
            List<OfflinePlayer> players = new ArrayList<>(team.getOfflinePlayers());
            players.removeIf(offlinePlayer -> spectators.contains(offlinePlayer.getName()));
            for (PlayerZombieData playerZombieData : playerZombies) players.removeIf(s -> Math.abs(System.currentTimeMillis() - playerZombieData.getTime()) > 300000 && playerZombieData.getPlayer().getName().equals(s.getName()));
            players.removeIf(offlinePlayer -> !offlinePlayer.isOnline());

            if (!players.isEmpty()) {
                teams.add(team);
            }
        });
        return teams;
    }

    public int getAlivePlayers() {
        int i = 0;
        for (Player player : getGamePlayers()) {
            if (!isSpectator(player)) {
                i++;
            }
        }

        return i;
    }

    public String getFormattedTime(int time) {
        String minStr = "";
        String secStr = "";
        int min = (int) Math.floor((double) (time / 60));
        int sec = time % 60;
        minStr = min < 10 ? "0" + min : String.valueOf(min);
        secStr = sec < 10 ? "0" + sec : String.valueOf(sec);
        return minStr + ":" + secStr;
    }


    public void freeze(Player player) {
        if (Frozen.isFrozen(player)) {
            return;
        }

        Frozen.frozen.add(player.getName());
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
        player.setFlySpeed(0);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.teleport(player.getLocation().add(0, 0.01, 0));
    }

    public void unfreezeAll() {
        for (Player p : getPlayers())
            unfreeze(p);
    }

    public void unfreeze(Player player) {
        if (!Frozen.isFrozen(player)) {
            return;
        }

        Bukkit.getScheduler().runTask(UHC.getInstance(), () -> {
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.BLINDNESS);

            player.setFlySpeed(0.1f);
            player.setFlying(false);
            player.setAllowFlight(false);
        });
        Frozen.frozen.remove(player.getName());
    }

    public void start() {
        setGameState(GameState.RUNNING);

        moveFreePlayersToTeam();

        getPlayers().forEach(player -> {
            Bukkit.getPluginManager().callEvent(new RejoinPlayerJoinEvent(player));
            getAllowReJoin().add(player.getName());
            UHC.getInstance().getGame().freeze(player);
        });

        Bukkit.getPluginManager().callEvent(new UHCGameStartEvent(this));
        eventManager.getRunnables().get("计分板").run(0, 0);

        new BukkitRunnable() {
            boolean color = false;
            @Override
            public void run() {
                if(Frozen.frozen.isEmpty()){
                    cancel();
                }

                for (Player player1 : getGamePlayers()) {
                    if (Frozen.isFrozen(player1)) {
                        ActionBarUtil.sendBar(player1, color ? "§a" : "§e" + "§l正在传送中...");
                        color = !color;
                    }
                }
            }
        }.runTaskTimerAsynchronously(UHC.getInstance(), 0, 20);

        new TeleportationTask();
    }

    public boolean isOver() {
        return getAliveTeams().isEmpty() || getAliveTeams().size() <= 1;
    }

    public Team getWinner() {
        for (Team team : this.teams) {
            List<String> players = new ArrayList<>(team.getPlayerNames());
            players.removeAll(spectators);
            if (!players.isEmpty()) {
                return team;
            }
        }

        return null;
    }
}
