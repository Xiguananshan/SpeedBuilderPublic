package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import plugin.xiguananshan.speedBuilder.SpeedBuilder;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class PlayerQuitPlayingEventListener implements Listener {
    @EventHandler
    public void run(PlayerInteractEvent e){
        if((e.getAction() == Action.RIGHT_CLICK_AIR) && (e.getHand() != EquipmentSlot.OFF_HAND)){
            if(e.getItem()!=null){
                if(e.getItem().getType() == Material.STICK){
                    PersistentDataContainer pdc = e.getItem().getItemMeta().getPersistentDataContainer();
                    if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"), PersistentDataType.BOOLEAN)){
                        if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN))){
                            if(ToolClass.wait_player.contains(e.getPlayer())){
                                ToolClass.wait_player.remove(e.getPlayer());
                                e.getPlayer().getInventory().clear();
                                e.getPlayer().sendMessage(ChatColor.GREEN + "你退出了游玩队列");
                                e.getPlayer().getInventory().setItem(0,ToolClass.create);
                                e.getPlayer().getInventory().setItem(1,ToolClass.play);
                                e.getPlayer().getInventory().setItem(2,ToolClass.exercise);
                            }else{
                                e.getPlayer().sendMessage(ChatColor.RED + "你没在游玩队列当中");
                            }
                        }

                    }

                }

            }
        }

    }
}
