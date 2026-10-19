package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import plugin.xiguananshan.speedBuilder.ToolClass;


public class BlockPlaceEventListener implements Listener {
    @EventHandler
    public void run(BlockPlaceEvent e){
        if(e.getPlayer() == ToolClass.creating_player){
            int x,y,z;
            x = e.getBlock().getX();
            y = e.getBlock().getY();
            z = e.getBlock().getZ();
            if(!(0<=x&&x<=6 && 100<=y&&y<=106&&-6<=z&&z<=0)){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "不可修改的方块");
                return;
            }
            if(ToolClass.create_structure != null){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "结构已保存，不能修改方块");
            }
        }
        if(ToolClass.playing_player.contains(e.getPlayer())){
            int x = e.getBlock().getX(),y = e.getBlock().getY(),z = e.getBlock().getZ();
            int index = ToolClass.playing_player.indexOf(e.getPlayer());
            if(!(4+30*index<=x&&x<=10+30*index&&201<=y&&y<=207&&4<=z&&z<=10)){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "不可修改的方块");
                return;
            }
            if(!ToolClass.is_building){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "不可修改的方块");
            }
        }
    }
}

