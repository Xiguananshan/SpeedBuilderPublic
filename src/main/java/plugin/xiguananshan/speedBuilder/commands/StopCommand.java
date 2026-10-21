package plugin.xiguananshan.speedBuilder.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import plugin.xiguananshan.speedBuilder.ToolClass;
import plugin.xiguananshan.speedBuilder.ticks.PlayerExerciseTimer;

import java.util.List;

public class StopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if(sender instanceof Player p){
            if(ToolClass.exercise_timer.containsKey(p)){
                PlayerExerciseTimer timer = ToolClass.exercise_timer.get(p);
                List<Integer> scores = timer.end();
                for(int i = 0;i<Math.min(scores.size()-1,6);i++){
                    p.sendMessage(ChatColor.GREEN + "秒数：" + ChatColor.YELLOW + (i+1)*10 + ChatColor.GREEN + "，分数：" + ChatColor.YELLOW + scores.get(i));
                }
                p.sendMessage(ChatColor.GREEN + "最终秒数：" + ChatColor.YELLOW +  timer.ticks/20.0f + "，最终分数：" + ChatColor.YELLOW + scores.getLast());
                p.getInventory().clear();
                p.setGameMode(GameMode.ADVENTURE);
            }else{
                p.sendMessage(ChatColor.RED + "你没在练习");
            }
        }
        return false;
    }
}
