package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.structure.Structure;
import plugin.xiguananshan.speedBuilder.SpeedBuilder;
import plugin.xiguananshan.speedBuilder.ToolClass;

import java.util.Random;

public class PlayerExerciseEventListener implements Listener {
    @EventHandler
    public void run(PlayerInteractEvent e){
        if((e.getAction() == Action.RIGHT_CLICK_AIR) && (e.getHand() != EquipmentSlot.OFF_HAND)){
            if(e.getItem()!=null){
                if(e.getItem().getType() == Material.SNOWBALL){
                    PersistentDataContainer pdc = e.getItem().getItemMeta().getPersistentDataContainer();
                    if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"), PersistentDataType.BOOLEAN)){
                        if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN))){
                            e.getPlayer().sendMessage(ChatColor.RED + "功能仍在开发");
                            e.setCancelled(true);
                            return;
                            /*
                            int k = 0;
                            while(ToolClass.exercise_player.containsValue(k)){
                                k++;
                            }
                            ToolClass.exercise_player.put(e.getPlayer(),k);
                            Structure labby = Bukkit.getStructureManager().loadStructure(NamespacedKey.minecraft("labby"));
                            assert labby != null;
                            labby.place(new Location(Bukkit.getWorld("world"),30*k,150,0),false, StructureRotation.NONE, Mirror.NONE,0,1,new Random());
                            e.getPlayer().teleport(new Location(Bukkit.getWorld("world"),30*k+3,151,3));
                            */
                        }

                    }

                }

            }
        }

    }
}
