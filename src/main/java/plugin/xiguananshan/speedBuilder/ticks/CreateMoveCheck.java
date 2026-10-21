package plugin.xiguananshan.speedBuilder.ticks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import plugin.xiguananshan.speedBuilder.ToolClass;

import java.util.Map;

public class CreateMoveCheck implements Runnable {

    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            if(ToolClass.destroy_tick.containsKey(p)){
                if(ToolClass.destroy_tick.get(p)>=0){
                    ToolClass.destroy_tick.replace(p,ToolClass.destroy_tick.get(p)-1);
                }else{
                    ToolClass.destroy_tick.remove(p);
                }

            }
            if(ToolClass.exercise_player.containsKey(p)){
                Integer k = ToolClass.exercise_player.get(p);
                double x = p.getLocation().getX(),z = p.getLocation().getZ();
                if(!(30*k<=x&&x<=30*k+15&&0<=z&&z<=15)){
                    p.teleport(new Location(Bukkit.getWorld("world"),30*k+3,151,3));
                    p.sendMessage(ChatColor.RED + "你不能离开练习区域");
                }
            }

        }
        if(ToolClass.creating_player != null){
            double x,z;
            x = ToolClass.creating_player.getLocation().getX();
            z = ToolClass.creating_player.getLocation().getZ();
            if(!(-4<=x&&x<=10&&-10<=z&&z<=4)){
                ToolClass.creating_player.teleport(new Location(ToolClass.creating_player.getWorld(),0,100,0));
                ToolClass.creating_player.sendMessage(ChatColor.RED + "你不能离开出题区域");
            }
        }
        if(ToolClass.playing){
            for(int i = 0;i<ToolClass.playing_player.size();i++){
                double x,z;
                x = ToolClass.playing_player.get(i).getLocation().getX();
                z = ToolClass.playing_player.get(i).getLocation().getZ();
                if(ToolClass.playing_player.get(i).getGameMode() == GameMode.SPECTATOR){
                    continue;
                }
                //ToolClass.playing_player.get(i).sendMessage(30*i-15+" " + 30*i + " ");
                if(!(30*i<=x&&x<=30*i+15&&0<=z&&z<=15)){

                    ToolClass.playing_player.get(i).teleport(new Location(ToolClass.playing_player.get(i).getWorld(),30*i+3,201,3));
                    ToolClass.playing_player.get(i).sendMessage(ChatColor.RED + "你不能离开创作区域");
                }
            }
        }

    }
}
