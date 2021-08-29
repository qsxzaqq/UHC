package cc.i9mc.uhc.listeners;

import cc.i9mc.gameutils.utils.ItemBuilderUtil;
import cc.i9mc.nick.Nick;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.common.PlayerZombieData;
import cc.i9mc.uhc.event.UHCPlayerKilledEvent;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.GameState;
import cc.i9mc.uhc.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DamageListener implements Listener {
    private Game game;

    public DamageListener(UHC uhc) {
        this.game = uhc.getGame();

        uhc.getServer().getPluginManager().registerEvents(this, uhc);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Zombie) {
            if (!entity.hasMetadata("player")) {
                return;
            }

            PlayerZombieData playerZombieData = null;
            for (PlayerZombieData playerZombieData1 : game.getPlayerZombies()) {
                if (playerZombieData1.getPlayer().getName().equals(entity.getMetadata("player").get(0).asString())) {
                    playerZombieData = playerZombieData1;
                }
            }
            if (playerZombieData == null) {
                return;
            }

            event.setDroppedExp(playerZombieData.getLevel());
            event.getDrops().clear();
            List<ItemStack> contents = new ArrayList<>(Arrays.asList(playerZombieData.getInventory().getContents()));
            Iterator<ItemStack> iterator = contents.iterator();
            while (iterator.hasNext()) {
                ItemStack itemStack = iterator.next();

                if (itemStack != null && itemStack.getType() == Material.COMPASS && itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName().equals("§a队友追踪器§7(右键点击)")) {
                    iterator.remove();
                    continue;
                }

                if (itemStack != null && itemStack.getType() == Material.ENCHANTED_BOOK && itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName().equals("§a合成书§7(右键查看)")) {
                    iterator.remove();
                }
            }

            event.getDrops().addAll(contents);
            event.getDrops().addAll(Arrays.asList(playerZombieData.getEquipment().getArmorContents()));
            event.getDrops().add(new ItemBuilderUtil().setOwner(playerZombieData.getPlayer().getName()).setDisplayName("§f" + Nick.get().getCache().getOrDefault(playerZombieData.getPlayer().getName(), playerZombieData.getPlayer().getName()) + " §c的头").addLore("§a*效果给予队伍内全部玩家").getItem());
            event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
            game.getPlayerZombies().remove(playerZombieData);

            if (game.getSpectators().contains(playerZombieData.getPlayer().getName()))
                game.getSpectators().add(playerZombieData.getPlayer().getName());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (game.getGameState() == GameState.WAITING) {
            if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                player.teleport(game.getWaitingLocation());
            }

            event.setCancelled(true);
        }

        if(game.getGameState() == GameState.RUNNING){
            if (game.getEventManager().isOver()) {
                event.setCancelled(true);
                return;
            }

            if (game.getEventManager().currentEvent().getPriority() == 0 && game.getEventManager().getSeconds() < 6) {
                event.setCancelled(true);
                return;
            }

            if (game.isSpectator((Player) event.getEntity())) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);

        Player player = event.getEntity();
        Team team = game.getTeam(player);

        if (game.getGameState() == GameState.WAITING) {
            event.setKeepInventory(true);
            event.getDrops().clear();
            event.getEntity().getInventory().clear();
            event.setDroppedExp(0);
            return;
        }

        if (game.isSpectator(player)) {
            return;
        }

        Player killer = player.getKiller();
        if (killer == null) {
            killer = game.getPlayerDamager(player);
        }
        Team killerTeam = game.getTeam(killer);

        if (killer == null || killerTeam == null) {
            game.broadcastMessage(Nick.get().getCache().getOrDefault(player.getName(), player.getName()) + "§e不知道咋死了");
        } else {
            boolean isEmpty = false;
            for (ItemStack item : killer.getInventory().getContents()) {
                if (item == null) {
                    isEmpty = true;
                    break;
                }
            }
            if (isEmpty) {
                killer.getInventory().addItem(new ItemBuilderUtil().setOwner(player.getName()).setDisplayName("§f" + Nick.get().getCache().getOrDefault(player.getName(), player.getName()) + " §c的头").addLore("§a*效果给予队伍内全部玩家").getItem());
            } else {
                killer.getWorld().dropItem(killer.getEyeLocation(), new ItemBuilderUtil().setOwner(player.getName()).setDisplayName("§f" + Nick.get().getCache().getOrDefault(player.getName(), player.getName()) + " §c的头").addLore("§a*效果给予队伍内全部玩家").getItem());
            }


            game.broadcastMessage(Nick.get().getCache().getOrDefault(player.getName(), player.getName()) + "§e被" + Nick.get().getCache().getOrDefault(killer.getName(), killer.getName()) + "§e狠狠滴推倒");

            UHC.getInstance().getCacher().get(killer.getName()).setKills(UHC.getInstance().getCacher().get(killer.getName()).getKills() + 1);
            UHC.getInstance().getCacher().get(player.getName()).setDeaths(UHC.getInstance().getCacher().get(player.getName()).getDeaths() + 1);
            Bukkit.getPluginManager().callEvent(new UHCPlayerKilledEvent(player, killer));
        }

        Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> player.spigot().respawn(), 5L);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damaged = event.getDamager();

        if (damaged instanceof Player) {
            if (game.isSpectator((Player) damaged)) {
                event.setCancelled(true);
                return;
            }
        }

        if (damaged instanceof Player || damaged instanceof Projectile) {
            if (game.getGameState() == GameState.RUNNING) {
                if (damaged instanceof Player) {
                    if (entity instanceof Player) {
                        if (game.getEventManager().currentEvent().getPriority() == 0) {
                            event.setCancelled(true);
                            return;
                        }

                        if (game.getTeam((Player) damaged) == game.getTeam((Player) entity)) {
                            event.setCancelled(true);
                        } else {
                            game.setPlayerDamager((Player) entity, (Player) damaged);
                        }
                        return;
                    } else if (entity instanceof Zombie) {
                        if (!entity.hasMetadata("player")) {
                            return;
                        }

                        if (game.getTeam(entity.getMetadata("player").get(0).asString()) == game.getTeam((Player) damaged)) {
                            event.setCancelled(true);
                            return;
                        }

                        if (game.getEventManager().currentEvent().getPriority() == 0) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                    return;
                }

                Projectile projectile = (Projectile) damaged;
                if (projectile.getShooter() instanceof Player) {
                    if (game.getTeam((Player) projectile.getShooter()) == game.getTeam((Player) entity)) {
                        event.setCancelled(true);
                    } else {
                        if (game.getEventManager().currentEvent().getPriority() == 0) {
                            event.setCancelled(true);
                            return;
                        }

                        game.setPlayerDamager((Player) entity, (Player) projectile.getShooter());
                    }
                }
            }
        }
    }
}
