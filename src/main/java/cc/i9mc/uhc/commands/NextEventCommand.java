package cc.i9mc.uhc.commands;

import cc.i9mc.uhc.UHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NextEventCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            UHC.getInstance().getGame().getEventManager().setCurrentEvent(Integer.parseInt(strings[0]));

            if (strings.length == 2) {
                UHC.getInstance().getGame().getEventManager().currentEvent().excute(UHC.getInstance().getGame());
            }
        }
        return false;
    }
}
