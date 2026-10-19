package plugin.xiguananshan.speedBuilder.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class SetGroundCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if(sender instanceof Player p){
            if(ToolClass.creating_player == p){
                ToolClass.ground_page = 1;
                p.openInventory(ToolClass.GetGroundInv(1));
            }else{
                p.sendMessage(ChatColor.RED + "你没在出题");
            }
        }
        return false;
    }
}
