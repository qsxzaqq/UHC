package cc.i9mc.uhc.commands;

import cc.i9mc.uhc.UHC;
import cc.i9mc.uhc.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminCommand implements CommandExecutor {
    private List<Location> dmList;

    public AdminCommand() {
        dmList = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("此命令仅限玩家");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.sendMessage("/game toWorld <地图名字> ----- 前往世界");
            player.sendMessage("/game loadWorld <地图名字> ----- 加载世界");
            player.sendMessage(" ");
            player.sendMessage("/game addDM ----- 添加新的死亡竞赛点");
            player.sendMessage("/game clearDM ----- 清除死亡竞赛点");
            player.sendMessage(" ");
            player.sendMessage("/game info ----- 查看设置的参数");
            player.sendMessage("/game save ----- 保存设置的参数");
            return true;
        }

        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("addDM")) {
                dmList.add(player.getLocation());
                player.sendMessage("添加成功！");
                return true;
            }

            if (strings[0].equalsIgnoreCase("clearDM")) {
                dmList.clear();
                player.sendMessage("清理成功！");
                return true;
            }

            if (strings[0].equalsIgnoreCase("info")) {
                player.sendMessage("死亡竞赛点: " + dmList.size());
                return true;
            }

            if (strings[0].equalsIgnoreCase("save")) {
                List<String> data = new ArrayList<>();

                for (Location loc : dmList) {
                    data.add(Util.locationToString(loc));
                }

                UHC.getInstance().getConfig().set("dm", data);
                UHC.getInstance().saveConfig();
                player.sendMessage("保存成功！");
                return true;
            }
        }

        if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("toWorld")) {
                player.teleport(Bukkit.getWorld(strings[1]).getSpawnLocation());
            }

            if (strings[0].equalsIgnoreCase("loadWorld")) {
                WorldCreator cr = new WorldCreator(strings[1]);
                cr.environment(World.Environment.NORMAL);
                World mapWorld = Bukkit.createWorld(cr);

                mapWorld.setAutoSave(false);
                mapWorld.setGameRuleValue("doMobSpawning", "false");
                mapWorld.setGameRuleValue("doFireTick", "false");
            }
        }

        return false;
    }
}