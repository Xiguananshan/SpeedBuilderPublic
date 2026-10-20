package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class PlayerClickBlockEventListener implements Listener{
    @EventHandler
    public void run(PlayerInteractEvent e){
        if(e.getAction() == Action.LEFT_CLICK_BLOCK){
            if(e.getPlayer() == ToolClass.creating_player){
                Block block = e.getClickedBlock();
                if(block != null){
                    if(block.getX() >= 0 && block.getX() <= 6 && block.getY() == 99 && block.getZ() >= -6 && block.getZ() <= 0){
                        ItemStack stack = e.getItem();
                        if(stack != null){
                            Material material = stack.getType();
                            if(!material.isAir()){
                                if(material.isBlock() && material.isItem()){
                                    BlockData blockData = Bukkit.createBlockData(material);

                                    if(blockData.isOccluding()){
                                        if(!(blockData.createBlockState() instanceof TileState)){
                                            block.setBlockData(blockData,false);
                                            e.setCancelled(true);
                                            e.getPlayer().sendMessage(ChatColor.YELLOW + "地板方块已更换");
                                        }
                                    }else{
                                        e.getPlayer().sendMessage(ChatColor.RED + "不允许该方块作为地板");
                                        e.setCancelled(true);
                                    }
                                }else{
                                    e.getPlayer().sendMessage(ChatColor.RED + "物品当地板的都来了");
                                    e.setCancelled(true);
                                }
                            }else{
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }

    }
}
