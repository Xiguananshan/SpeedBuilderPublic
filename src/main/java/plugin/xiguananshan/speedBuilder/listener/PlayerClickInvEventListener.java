package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import plugin.xiguananshan.speedBuilder.SpeedBuilder;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class PlayerClickInvEventListener implements Listener {
    @EventHandler
    public void run(InventoryClickEvent e){
        if(e.getCurrentItem() != null){
            if(e.getCurrentItem().getType().isAir()){return;}
            if(ToolClass.creating_player != null){
                if(e.getWhoClicked().getName().equals(ToolClass.creating_player.getName())){
                    if(e.getCurrentItem().getType() == Material.ARROW){

                        PersistentDataContainer pdc = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
                        if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"), PersistentDataType.BOOLEAN)){
                            if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN))){
                                ToolClass.ground_page--;
                                e.getWhoClicked().openInventory(ToolClass.GetGroundInv(ToolClass.ground_page));
                                e.setCancelled(true);
                            }
                        }

                    } else if (e.getCurrentItem().getType() == Material.PAPER) {
                        PersistentDataContainer pdc = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
                        if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"), PersistentDataType.BOOLEAN)){
                            if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN))){
                                ToolClass.ground_page++;
                                e.getWhoClicked().openInventory(ToolClass.GetGroundInv(ToolClass.ground_page));
                                e.setCancelled(true);
                            }

                        }

                    }else{
                        PersistentDataContainer pdc = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
                        if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"), PersistentDataType.BOOLEAN)){
                            if(e.getCurrentItem().getType() == Material.BARRIER){
                                if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN)))
                                    e.setCancelled(true);
                            }
                        }
                        if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"ground"), PersistentDataType.BOOLEAN)){
                            if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "ground"), PersistentDataType.BOOLEAN))){
                                for(int x = 0;x<=6;x++){
                                    for(int z = -6;z<=0;z++){
                                        Bukkit.getWorld("world").setBlockData(x,99,z,Bukkit.createBlockData(e.getCurrentItem().getType()));
                                    }
                                }
                                e.getWhoClicked().closeInventory();
                                e.setCancelled(true);
                            }
                        }

                    }
                }
            }
        }
    }
}
