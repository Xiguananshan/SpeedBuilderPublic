package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import plugin.xiguananshan.speedBuilder.SpeedBuilder;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class PlayCreateEvent implements Listener {
    @EventHandler
    public void run(PlayerInteractEvent e){
        if((e.getAction() == Action.RIGHT_CLICK_AIR) && (e.getHand() != EquipmentSlot.OFF_HAND)){
            if(e.getItem()!=null){
                if(e.getItem().getType() == Material.SLIME_BALL){
                    PersistentDataContainer pdc = e.getItem().getItemMeta().getPersistentDataContainer();
                    if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"),PersistentDataType.BOOLEAN)){
                        if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN))){

                            if(ToolClass.creating_player == null){
                                for(int x = 0;x<=6;x++){
                                    for(int y = 100;y<=106;y++){
                                        for(int z= -6;z<=0;z++){
                                            e.getPlayer().getWorld().getBlockAt(x,y,z).setBlockData(Bukkit.createBlockData(Material.AIR),false);
                                        }
                                    }
                                }
                                ToolClass.creating_player = e.getPlayer();
                                e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 0,100,0));
                                e.getPlayer().sendMessage(ChatColor.YELLOW + "你现在正在出题，所有建筑的范围理应小于7*7*7");
                                e.getPlayer().sendMessage(ChatColor.YELLOW + "输入/end指令可以保存题目，输入/back指令可取消出题");
                                e.getPlayer().setGameMode(GameMode.CREATIVE);
                                e.getPlayer().getInventory().clear();
                            }else{
                                e.getPlayer().sendMessage(ChatColor.RED + "已经有人正在出题，请稍等");
                            }
                        }

                    }

                }

            }
        }

    }
}
