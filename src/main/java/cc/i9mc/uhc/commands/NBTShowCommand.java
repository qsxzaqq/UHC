package cc.i9mc.uhc.commands;

import cc.i9mc.gameutils.utils.nms.nbt.NBTUtils;
import com.google.gson.Gson;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NBTShowCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println(new Gson().toJson(NBTUtils.getItemStackNBT(((Player) sender).getItemInHand())));

        return false;
    }
}
