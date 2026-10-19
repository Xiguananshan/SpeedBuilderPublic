package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import plugin.xiguananshan.speedBuilder.SpeedBuilder;
import plugin.xiguananshan.speedBuilder.ToolClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlayerChatEventListener implements Listener {
    @EventHandler
    public void run(AsyncPlayerChatEvent e){
        if(e.getPlayer() == ToolClass.creating_player){
            if(ToolClass.create_structure != null){
                e.setCancelled(true);
                Bukkit.getScheduler().runTask(SpeedBuilder.getPlugin(SpeedBuilder.class),()->{
                    Path dir = Paths.get("plugins/buildings/" + e.getMessage() + ".nbt");
                    File file = dir.toFile();
                    if(!file.exists()){
                        try {
                            Bukkit.getStructureManager().saveStructure(file,ToolClass.create_structure);
                        } catch (IOException ex) {
                            e.getPlayer().sendMessage(ChatColor.RED + "保存失败，请重试");
                            return;
                        }
                        e.getPlayer().sendMessage(ChatColor.GREEN + "保存成功");
                        Player p = e.getPlayer();
                        p.teleport(new Location(p.getWorld(),0,-60,0));
                        p.getInventory().clear();
                        p.getInventory().setItem(0, ToolClass.create);
                        p.getInventory().setItem(1, ToolClass.play);
                        ToolClass.create_structure = null;
                        ToolClass.creating_player = null;
                        p.setGameMode(GameMode.ADVENTURE);
                    }else{
                        e.getPlayer().sendMessage(ChatColor.RED + "目标建筑已存在");
                    }
                });
            }
        }
    }
}
