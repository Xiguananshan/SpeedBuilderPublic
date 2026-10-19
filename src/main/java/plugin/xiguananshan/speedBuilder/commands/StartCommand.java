package plugin.xiguananshan.speedBuilder.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if(sender instanceof Player p){
            if(ToolClass.wait_player.contains(p)){
                if(ToolClass.playing){
                    p.sendMessage(ChatColor.RED + "游戏已经开始");
                }else{
                    if(ToolClass.wait_player.size() > 1){
                        ToolClass.start_time = 1;
                        p.sendMessage(ChatColor.GREEN + "游戏已强开");
                    }else{
                        p.sendMessage(ChatColor.RED + "队列人数太少");
                    }
                }
            }else{
                p.sendMessage(ChatColor.RED + "你没有在游玩队列中");
            }
        }
        return false;
    }
}
