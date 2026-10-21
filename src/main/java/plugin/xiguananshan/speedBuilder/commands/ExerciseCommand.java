package plugin.xiguananshan.speedBuilder.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.SmallDripleaf;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.structure.Palette;
import org.jspecify.annotations.NonNull;
import plugin.xiguananshan.speedBuilder.ToolClass;
import plugin.xiguananshan.speedBuilder.ticks.PlayerExerciseTimer;

import java.util.ArrayList;
import java.util.List;

public class ExerciseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if(sender instanceof Player p){
            if(!ToolClass.exercise_timer.containsKey(p)){
                if(!ToolClass.exercise_structure.containsKey(p)){
                    p.sendMessage(ChatColor.RED + "请先选择题目");
                    return false;
                }
                p.getInventory().clear();
                p.setGameMode(GameMode.SURVIVAL);
                Integer k = ToolClass.exercise_player.get(p);
                for(int x = 0;x<=6;x++){
                    for(int y = 0;y<=6;y++){
                        for(int z = 0;z<=6;z++){
                            Bukkit.getWorld("world").getBlockAt(4+30*k+x,151+y,4+z).setBlockData(Bukkit.createBlockData(Material.AIR),false);
                        }
                    }
                }
                List<BlockState> states = new ArrayList<>();
                for(Palette palette : ToolClass.exercise_structure.get(p).getPalettes()){
                    states.addAll(palette.getBlocks());
                }
                for(BlockState state : states){
                    if(state.getBlockData() instanceof Bed b){
                        if(b.getPart() == Bed.Part.HEAD){
                            p.getInventory().addItem(new ItemStack(state.getType()));
                        }

                    }else if(state.getBlockData() instanceof Bisected b){
                        if(state.getBlockData() instanceof Stairs || state.getBlockData() instanceof TrapDoor || state.getBlockData() instanceof SmallDripleaf){
                            p.getInventory().addItem(new ItemStack(state.getType()));
                        }else if(b.getHalf() == Bisected.Half.BOTTOM){
                            p.getInventory().addItem(new ItemStack(state.getType()));
                        }
                    }else{
                        p.getInventory().addItem(new ItemStack(state.getType()));
                    }


                }

                p.sendTitle(ChatColor.GREEN + "开始复原","",10,40,10);
                PlayerExerciseTimer timer = new PlayerExerciseTimer(p);
                timer.runTaskTimer(ToolClass.PLUGIN,0,1);
                ToolClass.exercise_timer.put(p,timer);
            }else{
                p.sendMessage(ChatColor.RED + "你已经开始练习");
            }
        }
        return false;
    }
}
