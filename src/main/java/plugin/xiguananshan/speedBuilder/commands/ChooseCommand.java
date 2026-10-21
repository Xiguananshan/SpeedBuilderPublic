package plugin.xiguananshan.speedBuilder.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import plugin.xiguananshan.speedBuilder.ToolClass;


public class ChooseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if(sender instanceof Player p){
            if(ToolClass.exercise_player.containsKey(p)){
                if(ToolClass.exercise_timer.containsKey(p)){
                    p.sendMessage(ChatColor.RED + "你已在练习状态");
                    return false;
                }
                ToolClass.exercise_player_page.put(p,1);
                p.openInventory(ToolClass.GetProblemInv(1));
            }else{
                p.sendMessage(ChatColor.RED + "你没在练习状态");
            }
        }
        return false;
    }
}
