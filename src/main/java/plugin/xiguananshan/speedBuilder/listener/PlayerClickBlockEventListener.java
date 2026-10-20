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
                            if(ToolClass.grounds_list.contains(material)){
                                block.setBlockData(Bukkit.createBlockData(material));
                                e.getPlayer().sendMessage(ChatColor.YELLOW + "目标地板方块已替换");
                                e.setCancelled(true);
                            }else{
                                e.setCancelled(true);
                                e.getPlayer().sendMessage(ChatColor.RED + "无法选择该方块");
                            }
                        }
                    }
                }
            }
        }

    }
}
