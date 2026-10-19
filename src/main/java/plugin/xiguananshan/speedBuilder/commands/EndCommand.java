package plugin.xiguananshan.speedBuilder.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;
import org.jspecify.annotations.NonNull;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class EndCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if(sender instanceof Player p){
            if(p == ToolClass.creating_player){
                Location c1 = new Location(p.getWorld(),0,100,-6);
                ToolClass.create_structure = Bukkit.getStructureManager().createStructure();
                ToolClass.create_structure.fill(c1,new BlockVector(7,7,7),false);
                p.sendMessage(ChatColor.YELLOW + "方块读取完成，请在聊天栏中输入该建筑的名字");
            }else{
                p.sendMessage(ChatColor.RED + "你没在出题");
            }
        }
        return false;
    }
}
