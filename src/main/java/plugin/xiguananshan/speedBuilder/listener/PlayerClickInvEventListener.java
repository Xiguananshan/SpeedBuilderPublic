package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.structure.Structure;
import plugin.xiguananshan.speedBuilder.SpeedBuilder;
import plugin.xiguananshan.speedBuilder.ToolClass;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PlayerClickInvEventListener implements Listener {
    @EventHandler
    public void run(InventoryClickEvent e){
        if(e.getCurrentItem() != null){
            if(e.getCurrentItem().getType().isAir()){return;}
            PersistentDataContainer pdc = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
            if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"), PersistentDataType.BOOLEAN)){
                if(e.getCurrentItem().getType() == Material.BARRIER){
                    if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN)))
                        e.setCancelled(true);
                }
            }
            if(ToolClass.creating_player != null){
                if(e.getWhoClicked().getName().equals(ToolClass.creating_player.getName())){
                    if(e.getCurrentItem().getType() == Material.ARROW){


                        if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"), PersistentDataType.BOOLEAN)){
                            if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN))){
                                ToolClass.ground_page--;
                                e.getWhoClicked().openInventory(ToolClass.GetGroundInv(ToolClass.ground_page));
                                e.setCancelled(true);
                            }
                        }

                    } else if (e.getCurrentItem().getType() == Material.PAPER) {

                        if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"target"), PersistentDataType.BOOLEAN)){
                            if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "target"), PersistentDataType.BOOLEAN))){
                                ToolClass.ground_page++;
                                e.getWhoClicked().openInventory(ToolClass.GetGroundInv(ToolClass.ground_page));
                                e.setCancelled(true);
                            }

                        }

                    }else{

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
            if(ToolClass.exercise_player.containsKey((Player) e.getWhoClicked())){
                if(pdc.has(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class) ,"problem_target"), PersistentDataType.BOOLEAN)){
                    if(Boolean.TRUE.equals(pdc.get(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class), "problem_target"), PersistentDataType.BOOLEAN))){
                        if(ToolClass.exercise_player_page.containsKey((Player) e.getWhoClicked())){
                            if(e.getCurrentItem().getType() == Material.PAPER){
                                Integer p = ToolClass.exercise_player_page.get((Player) e.getWhoClicked());
                                p++;
                                e.getWhoClicked().openInventory(ToolClass.GetProblemInv(p));
                                ToolClass.exercise_player_page.replace((Player) e.getWhoClicked(),p);
                            }else if(e.getCurrentItem().getType() == Material.ARROW){
                                Integer p = ToolClass.exercise_player_page.get((Player) e.getWhoClicked());
                                p--;
                                e.getWhoClicked().openInventory(ToolClass.GetProblemInv(p));
                                ToolClass.exercise_player_page.replace((Player) e.getWhoClicked(),p);
                            }

                            e.setCancelled(true);
                        }
                    }

                }
                if(pdc.has(new NamespacedKey(ToolClass.PLUGIN,"problem"),PersistentDataType.STRING)){
                    String s = pdc.get(new NamespacedKey(ToolClass.PLUGIN,"problem"),PersistentDataType.STRING);
                    assert  s!= null;
                    if(ToolClass.exercise_player.containsKey((Player) e.getWhoClicked())){
                        int k = ToolClass.exercise_player.get((Player) e.getWhoClicked());
                        File structure_dir =  new File(Bukkit.getWorldContainer(),"plugins/buildings/" + s + ".nbt");
                        File ground_dir = new File(Bukkit.getWorldContainer(),"plugins/grounds/" + s + ".nbt");
                        try{
                            for(int x = 4+30*k;x<=10+30*k;x++){
                                for(int y = 151;y<=157;y++){
                                    for(int z = 4;z<=10;z++){
                                        Bukkit.getWorld("world").getBlockAt(x,y,z).setBlockData(Bukkit.createBlockData(Material.AIR),false);
                                    }
                                }
                            }
                            Structure ground = null;
                            if(ground_dir.exists()){
                                ground = Bukkit.getStructureManager().loadStructure(ground_dir);
                            }else{
                                for(int x = 4+30*k;x<=10+30*k;x++){
                                    for(int z = 4;z<=10;z++){
                                        Bukkit.getWorld("world").getBlockAt(x,150,z).setBlockData(Bukkit.createBlockData(Material.WHITE_CONCRETE),false);
                                    }
                                }
                            }
                            Structure structure = Bukkit.getStructureManager().loadStructure(structure_dir);
                            if(ground != null) ground.place(new Location(Bukkit.getWorld("world"),4+30*k,150,4),false, StructureRotation.NONE, Mirror.NONE,0,1,new Random());
                            structure.place(new Location(Bukkit.getWorld("world"),4+30*k,151,4),false, StructureRotation.NONE, Mirror.NONE,0,1,new Random());

                            e.getWhoClicked().sendMessage(ChatColor.YELLOW + "结构已加载，输入指令/exercise开始练习，再次输入/choose选择题目");
                            e.setCancelled(true);
                            e.getWhoClicked().closeInventory();
                            ToolClass.exercise_structure.put((Player) e.getWhoClicked(),structure);
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }

                    }
                }
            }
        }
    }
}
