package cc.i9mc.uhc.utils;

import cc.i9mc.nick.Nick;
import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.common.Interval;
import cc.i9mc.uhc.game.Game;
import cc.i9mc.uhc.game.Team;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.nametagedit.plugin.NametagEdit;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.*;

public class Util {
    public static List<Chunk> loadChunks(Location loc, Integer radius, Boolean load) {
        List<Chunk> chunks = new ArrayList<>();

        int cx = loc.getBlockX() - radius;
        int cz = loc.getBlockZ() - radius;

        while (cx <= loc.getBlockX() + radius) {
            while (cz <= loc.getBlockZ() + radius) {
                Location l = new Location(loc.getWorld(), cx, 0, cz);
                if (load && !l.getChunk().isLoaded())
                    l.getWorld().loadChunk(l.getChunk().getX(), l.getChunk().getZ(), true);
                chunks.add(l.getChunk());
                cz += 16;
            }
            cz = loc.getBlockZ() - radius;
            cx += 16;
        }

        return chunks;
    }

    public static void playSoundToAll(String nameSound) {
        for (Player p : UHC.getInstance().getGame().getPlayers())
            playSound(p, nameSound);
    }

    public static void playSound(Player p, String nameSound) {
        try {
            Field f = Sound.class.getDeclaredField(nameSound);
            f.setAccessible(true);
            Sound sound = (Sound) f.get(null);
            p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static Location stringToLocation(String gs) {
        return new Location(Bukkit.getWorld(gs.split(",")[0]), Double.parseDouble(gs.split(",")[1]), Double.parseDouble(gs.split(",")[2]), Double.parseDouble(gs.split(",")[3]), Float.parseFloat(gs.split(",")[4]), Float.parseFloat(gs.split(",")[5]));
    }

    public static Location stringToLocation(String world, String gs) {
        return new Location(Bukkit.getWorld(world), Double.parseDouble(gs.split(",")[0]), Double.parseDouble(gs.split(",")[1]), Double.parseDouble(gs.split(",")[2]), Float.parseFloat(gs.split(",")[3]), Float.parseFloat(gs.split(",")[4]));
    }

    public static String locationToString(Location loc) {
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    }


    public static Location getRandomLocation(World w, int size, Boolean up, List<Material> mat) {
        for (int i = 0; i <= 100; i++) {
            int x = new Interval<Integer>(-size, size).getAsRandomInt();
            int z = new Interval<Integer>(-size, size).getAsRandomInt();

            Location loc = new Location(w, x, 0, z);
            loc = w.getHighestBlockAt(loc).getLocation();

            if (loc != null && loc.getY() <= 100 && !mat.contains(loc.getBlock().getRelative(BlockFace.DOWN).getType()))
                if (up)
                    return loc.add(0, 40, 0);
                else
                    return loc;
        }

        if (mat.size() > 0) {
            mat.remove(0);
            return getRandomLocation(w, size, up, mat);
        } else {
            return null;
        }
    }

    public static void teleportListPlayer(List<Player> list, Location center, Integer radius, TeleportRunnable runnable) {
        Integer i = 0;
        Set<Vector> spreadSpawns = getCircle(5d, list.size(), false, -1D);
        for (Player p : list) {
            Vector v = (Vector) spreadSpawns.toArray()[list.indexOf(p)];
            Location loc = new Location(center.getWorld(), center.getBlockX() + v.getX(), center.getBlockY() + v.getY(), center.getBlockZ() + v.getZ());
            teleportPlayer(p, loc, runnable);
            i++;
        }
    }

    public static void teleportPlayer(Player p, Location loc, TeleportRunnable runnable) {
        Bukkit.getScheduler().runTask(UHC.getInstance(), () -> {
            p.teleport(loc);
            if (runnable != null)
                runnable.run(p);
        });
    }

    public static Set<Vector> getCircle(Double radius, Integer amount, Boolean full, Double space) {
        Set<Vector> list = new HashSet<>();

        Double increment = (2 * Math.PI) / amount;

        for (Double angle = 0d; angle <= 2 * Math.PI; angle += increment) {
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            list.add(new Vector((int) x, 0, (int) z));
        }
        return list;
    }

    public static void setPlayerTeamTab() {
        Game game = UHC.getInstance().getGame();

        for (Team team : game.getTeams()) {
            team.getPlayers().forEach(player -> {
                NametagEdit.getApi().reloadNametag(player);
                NametagEdit.getApi().setNametag(player, "§7[" + team.getId() + "] §e", Nick.get().getCache().getOrDefault(player.getName(), ""));
            });
        }
    }

    public static String getDirection(Player me, Player team) {
        return getDirection(me, team.getLocation());
    }

    public static String getDirection(Player me, Location to) {
        Vector vMe = me.getEyeLocation().getDirection().setY(0);
        Vector vTeam = to.toVector().subtract(me.getLocation().toVector()).normalize().setY(0);

        double angle = Math.toDegrees(vMe.angle(vTeam));
        double crossProduct = vTeam.crossProduct(vMe).getY();

        while (angle < 0)
            angle += 360;

        if (angle <= 45)
            return "⬆";
        else if (angle > 45 && angle <= 75)
            if (crossProduct < 0)
                return "⬉";
            else
                return "⬈";
        else if (angle > 75 && angle <= 105)
            if (crossProduct > 0)
                return "➡";
            else
                return "⬅";
        else if (angle > 105 && angle <= 135)
            if (crossProduct < 0)
                return "⬋";
            else
                return "⬊";
        else if (angle > 135)
            return "⬇";

        return null;
    }

    public static ItemStack createGodSkull() {
        ItemStack head = new MaterialData(Material.valueOf("SKULL_ITEM"), (byte) 3).toItemStack(1);
        ItemMeta itemMeta = head.getItemMeta();
        head.setItemMeta(itemMeta);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwner("Notch");
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1NjY0MjA4NTk1NDYsInByb2ZpbGVJZCI6IjU3MGIwNWJhMjZmMzRhOGViZmRiODBlY2JjZDdlNjIwIiwicHJvZmlsZU5hbWUiOiJMb3JkU29ubnkiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzU2NmE4NzQ2MDE3M2FkZjA2N2NiMzU2YWUyMDBkMDMwNTA0Mzc4YzU1MmUzNDI4YjQ3NzRjNGMxMWE1OTljMjQifX19"));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static void setAI(LivingEntity entity, boolean hasAi) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) tag = new NBTTagCompound();
        nmsEntity.c(tag);
        tag.setInt("NoAI", hasAi ? 0 : 1);
        nmsEntity.f(tag);
    }

    public static interface EventTimerRunnable {
        public void run(int seconds, int currentEvent);
    }

    public static interface TeleportRunnable {
        public void run(Player p);
    }

    public static interface ActionMessage {
        public void run();
    }

    public static interface Callback {
        public void run();
    }
}
