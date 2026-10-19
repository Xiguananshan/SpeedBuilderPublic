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

public class PlayerPlayEventListener implements Listener {
        @EventHandler
        public void run(PlayerInteractEvent e){
            if((e.getAction() == Action.RIGHT_CLICK_AIR) && (e.getHand() != EquipmentSlot.OFF_HAND)){
                if(e.getItem()!=null){
                    if(e.getItem().getType() == Material.MAGMA_CREAM){
                        PersistentDataContainer pdc = e.getItem().getItemMeta().getPersistentDataContainer();
                        if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"), PersistentDataType.BOOLEAN)){
                            if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN))){
                                if(ToolClass.playing){
                                    e.getPlayer().sendMessage(ChatColor.RED + "游戏已经开始，请稍后");
                                    return;
                                }
                                if(!ToolClass.wait_player.contains(e.getPlayer())){
                                    ToolClass.wait_player.add(e.getPlayer());
                                    e.getPlayer().getInventory().clear();
                                    e.getPlayer().sendMessage(ChatColor.GREEN + "你进入了游玩队列");
                                    e.getPlayer().getInventory().setItem(0,ToolClass.cancel);
                                    if(ToolClass.wait_player.size() == 2){
                                        ToolClass.start_time = 30;
                                    }
                                }else{
                                    e.getPlayer().sendMessage(ChatColor.RED + "你已经在游玩队列当中");
                                }
                            }

                        }

                    }

                }
            }

        }

}
