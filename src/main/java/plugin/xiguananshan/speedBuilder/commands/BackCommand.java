package plugin.xiguananshan.speedBuilder.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
                p.sendMessage(ChatColor.YELLOW + "你已取消出题");
                ToolClass.creating_player = null;
                ToolClass.create_structure = null;
                if(!p.isOp())
                    p.setGameMode(GameMode.ADVENTURE);
            }else{
                p.sendMessage(ChatColor.RED + "你没在出题");
            }
        }
        return false;
    }
}
