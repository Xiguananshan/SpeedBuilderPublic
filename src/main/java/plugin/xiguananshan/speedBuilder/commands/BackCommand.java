package plugin.xiguananshan.speedBuilder.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class BackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label,@NotNull String[] args) {
        if(sender instanceof Player p){
            if(p== ToolClass.creating_player){
                p.teleport(new Location(p.getWorld(),0,-60,0));
                p.getInventory().clear();
                p.getInventory().setItem(0, ToolClass.create);
                p.getInventory().setItem(1, ToolClass.play);
                p.getInventory().setItem(2,ToolClass.exercise);
                p.sendMessage(ChatColor.YELLOW + "你已取消出题");
                ToolClass.creating_player = null;
                ToolClass.create_structure = null;
                if(!p.isOp())
                    p.setGameMode(GameMode.ADVENTURE);
            }else{
                if(ToolClass.exercise_player.containsKey(p)){
                    p.teleport(new Location(p.getWorld(),0,-60,0));
                    p.getInventory().clear();
                    p.getInventory().setItem(0, ToolClass.create);
                    p.getInventory().setItem(1, ToolClass.play);
                    p.getInventory().setItem(2,ToolClass.exercise);
                    p.sendMessage(ChatColor.YELLOW + "你已取消练习");
                    p.setGameMode(GameMode.ADVENTURE);
                    int k = ToolClass.exercise_player.get(p);
                    for(int x = 30*k;x<=30*k+14;x++){
                        for(int y = 150;y<=157;y++){
                            for(int z = 0;z<=14;z++){
                                Bukkit.getWorld("world").getBlockAt(x,y,z).setBlockData(Bukkit.createBlockData(Material.AIR),false);
                            }
                        }
                    }
                    ToolClass.exercise_player.remove(p);
                    ToolClass.exercise_player_page.remove(p);
                    ToolClass.exercise_structure.remove(p);
                    ToolClass.exercise_ground.remove(p);
                    if(ToolClass.exercise_timer.containsKey(p))
                        ToolClass.exercise_timer.get(p).end();

                }else{
                    p.sendMessage(ChatColor.RED + "你没在出题或在练习");
                }

            }
        }
        return false;
    }
}
