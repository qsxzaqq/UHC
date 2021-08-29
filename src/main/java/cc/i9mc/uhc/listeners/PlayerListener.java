package cc.i9mc.uhc.listeners;

import cc.i9mc.gameutils.utils.BungeeUtil;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.databse.Database;
import cc.i9mc.uhc.databse.PlayerData;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.GameState;
import cc.i9mc.uhc.game.Team;
import cc.i9mc.uhc.guis.CraftBookGUI;
import cc.i9mc.uhc.guis.SelectKitGUI;
import cc.i9mc.uhc.guis.SpectatorCompassGUI;
import cc.i9mc.uhc.profession.Craft;
import cc.i9mc.uhc.profession.Profession;
import cc.i9mc.uhc.profession.ProfessionManager;
import cc.i9mc.watchnmslreport.BukkitReport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {
    private Game game;

    public PlayerListener(UHC uhc) {
        this.game = uhc.getGame();

        uhc.getServer().getPluginManager().registerEvents(this, uhc);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (game.getGameState() == GameState.RUNNING && game.getAllowReJoin().contains(player.getName())) {
            return;
        }

        if (player.hasPermission("uhc.*") || BukkitReport.getInstance().getStaffs().containsKey(player.getName())) {
            return;
        }

        if (game.getPlayers().size() >= game.getMaxPlayers()) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, "开始了");
            return;
        }

        if (game.getGameState() == GameState.RUNNING) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, "开始了");
            return;
        }

        if (game.getGameState() == GameState.LOAD) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, "准备中");
            return;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        game.addPlayer(event.getPlayer());
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onAsyncJoin(AsyncPlayerPreLoginEvent event){
        PlayerData playerData = Database.getPlayerData(event.getName());
        UHC.getInstance().getCacher().add(event.getName(), playerData);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        game.removePlayers(event.getPlayer());
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if (game.getGameState() == GameState.WAITING) {
            event.setCancelled(true);
            return;
        }

        if (game.isSpectator((Player) event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (game.getGameState() == GameState.RUNNING) {
            if (game.isSpectator(event.getPlayer())) {
                event.setCancelled(true);
            }
        }


        Material interactingMaterial = event.getMaterial();

        if (interactingMaterial == null) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        if (game.getGameState() == GameState.WAITING) {
            if (interactingMaterial == Material.SLIME_BALL) {
                event.setCancelled(true);
                BungeeUtil.send("UHC-Lobby-1", player);
                return;
            }

            if (interactingMaterial == Material.BOOK) {
                event.setCancelled(true);
                new SelectKitGUI(player).open();
            }

            if (interactingMaterial == Material.ENCHANTED_BOOK) {
                event.setCancelled(true);
                new CraftBookGUI(player, 1).open();
            }

            return;
        }


        if (game.getGameState() == GameState.RUNNING) {
            switch (interactingMaterial) {
                case COMPASS:
                    event.setCancelled(true);
                    if (!game.isSpectator(player)) {
                        /*PlayerData playerData = UHC.getInstance().getCacher().get(player.getName());
                        if(playerData.getCompassPlayer() != null){
                            String player1 = game.getTeam(player).nextPlayer(player.getName(), playerData.getCompassPlayer());
                            if(player1 != null){
                                playerData.setCompassPlayer(player1);
                                return;
                            }
                        }

                        playerData.setCompassPlayer(game.getTeam(player).nextPlayer(player.getName()));*/
                        return;
                    }

                    new SpectatorCompassGUI(player, game).open();
                    break;
                case PAPER:
                    event.setCancelled(true);
                    Bukkit.dispatchCommand(player, "queue join uhc td");
                    break;
                case SLIME_BALL:
                    event.setCancelled(true);
                    BungeeUtil.send("UHC-Lobby-1", player);
                    break;
                case SKULL_ITEM:
                    ItemStack itemStack = event.getItem();
                    if (itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName().contains(" §c的头")) {
                        event.setCancelled(true);
                        if (player.getItemInHand().getAmount() == 1) {
                            player.getInventory().setItemInHand(null);
                        } else {
                            player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount() - 1);
                        }

                        Team team = game.getTeam(player);

                        team.getPlayers().forEach(player1 -> {
                            if (player1.equals(player)) {
                                player.sendMessage("§a你吃掉玩家的头并获得了4秒的生命恢复和6秒的速度II效果！");
                            }

                            player1.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 4 * 20, 2));
                            player1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6 * 20, 1));
                        });
                        break;
                    }

                    break;
                case ENCHANTED_BOOK:
                    event.setCancelled(true);
                    if (player.hasMetadata("CBT") && Math.abs(System.currentTimeMillis() - player.getMetadata("CBT").get(0).asLong()) < 500) {
                        return;
                    }
                    player.setMetadata("CBT", new FixedMetadataValue(UHC.getInstance(), System.currentTimeMillis()));
                    new CraftBookGUI(player, 1).open();
                    break;
                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onClick2(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) {
            return;
        }
        Material interactingMaterial = event.getCurrentItem().getType();

        if (!game.isSpectator(player)) {
            return;
        }

        if (event.getSlotType() == InventoryType.SlotType.QUICKBAR) {
            switch (interactingMaterial) {
                case COMPASS:
                    event.setCancelled(true);
                    new SpectatorCompassGUI(player, game).open();
                    return;
                case PAPER:
                    event.setCancelled(true);
                    Bukkit.dispatchCommand(player, "queue join uhc td");
                    return;
                case SLIME_BALL:
                    event.setCancelled(true);
                    BungeeUtil.send("UHC-Lobby-1", player);
                    return;
                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("/report")) {
            return;
        }

        if (e.getPlayer().hasPermission("uhc.lr") && e.getMessage().startsWith("/wnm")) {
            return;
        }

        if (!e.getPlayer().hasPermission("uhc.*")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGameDrop(PlayerDropItemEvent event) {
        if (game.getGameState() == GameState.WAITING) {
            event.setCancelled(true);
            return;
        }

        if (game.isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (game.getGameState() == GameState.WAITING) {
            event.setCancelled(true);
            return;
        }

        if (game.isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (game.isSpectator(player)) {
            return;
        }

        if (game.getEventManager().currentEvent().getPriority() == 2) {
            if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
                Location loc = player.getLocation();
                loc.setX(event.getFrom().getBlockX());
                loc.setZ(event.getFrom().getBlockZ());
                loc.add(0.5, 0, 0.5);
                event.getPlayer().teleport(loc);
            }
            ;
        }
    }

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event) {
        for (HumanEntity humanEntity : event.getViewers()) {
            if (humanEntity instanceof Player) {
                ItemStack itemStack = event.getInventory().getResult();
                ItemMeta itemMeta = itemStack.getItemMeta();
                boolean b = false;
                if (itemMeta.getLore() != null) {
                    List<String> lore = new ArrayList<>(itemMeta.getLore());
                    if (lore.get(lore.size() - 1).equals("§§§")) {
                        b = true;
                        if (lore.size() == 1) {
                            itemMeta.setLore(null);
                        } else {
                            lore.remove(lore.size() - 1);
                            itemMeta.setLore(lore);
                        }
                    }
                }
                itemStack.setItemMeta(itemMeta);

                if (b) {
                    for (Profession profession : ProfessionManager.getProfessions()) {
                        for (Craft craft : profession.getCrafts()) {
                            if (craft.getResult().equals(itemStack)) {
                                if (!humanEntity.hasPermission("uhc.profession." + profession.getName() + "." + craft.getDisplayName())) {
                                    event.getInventory().setResult(new ItemStack(Material.AIR));
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();


    }
}