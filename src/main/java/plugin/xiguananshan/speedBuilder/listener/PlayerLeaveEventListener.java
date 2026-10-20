package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class PlayerLeaveEventListener  implements Listener {
    @EventHandler
    public void run(PlayerQuitEvent e){
        if(e.getPlayer() == ToolClass.creating_player){
            ToolClass.creating_player = null;
            ToolClass.create_structure = null;
        }
        ToolClass.wait_player.remove(e.getPlayer());
        if(ToolClass.playing_player.contains(e.getPlayer())){
            int inx = ToolClass.playing_player.indexOf(e.getPlayer());
            if(!ToolClass.died_player.contains(e.getPlayer())){
                for(int x = 30*inx;x<=30*inx+14;x++){
                    for(int y = 200;y<=207;y++) {
                        for (int z = 0; z <= 14; z++) {
                            Bukkit.getWorld("world").setBlockData(x, y, z, Bukkit.createBlockData(Material.AIR));
                        }
                    }
                }
            }
            ToolClass.died_player.add(e.getPlayer());
        }
        if(ToolClass.exercise_player.containsKey(e.getPlayer())){
            int k = ToolClass.exercise_player.get(e.getPlayer());
            for(int x = 30*k;x<=30*k+14;x++){
                for(int y = 150;y<=157;y++){
                    for(int z = 0;z<=14;z++){
                        Bukkit.getWorld("world").getBlockAt(x,y,z).setBlockData(Bukkit.createBlockData(Material.AIR),false);
                    }
                }
            }
            ToolClass.exercise_player.remove(e.getPlayer());
        }

    }
}
