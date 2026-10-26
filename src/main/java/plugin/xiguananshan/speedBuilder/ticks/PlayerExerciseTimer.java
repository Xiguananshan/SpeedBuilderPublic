package plugin.xiguananshan.speedBuilder.ticks;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.structure.Palette;
import org.bukkit.structure.Structure;
import plugin.xiguananshan.speedBuilder.StairsVisualComparator;
import plugin.xiguananshan.speedBuilder.ToolClass;

import java.util.ArrayList;
import java.util.List;

public class PlayerExerciseTimer extends BukkitRunnable {
    public Player p;
    public int ticks;
    public List<Integer> scores;
    private Structure structure;
    public PlayerExerciseTimer(Player p){
        this.p = p;
        ticks = 0;
        scores = new ArrayList<>();
        structure = ToolClass.exercise_structure.get(p);
    }

    @Override
    public void run() {
        if(ToolClass.exercise_player.containsKey(p)){
            Integer k = ToolClass.exercise_player.get(p);
            ticks++;
            List<BlockState> states = new ArrayList<>();
            for(Palette palette : structure.getPalettes()){
                states.addAll(palette.getBlocks());
            }
            int total_block = 0;
            for(BlockState state : states){
                if(state.getType() != Material.AIR){
                    total_block++;
                }
            }
            int correct_block = 0;
            for(BlockState state : states){
                int wx = 4 + 30*k + state.getX();
                int wy = 151 + state.getY();
                int wz = 4 + state.getZ();
                Block worldBlock = Bukkit.getWorld("world").getBlockAt(wx,wy,wz);
                if(state.getType() == worldBlock.getType()){
                    if(state.getBlockData() instanceof Stairs){
                        if(StairsVisualComparator.Compare(state.getBlockData(),worldBlock.getBlockData())){
                            correct_block++;
                        }
                    }else{
                        if(worldBlock.getBlockData().matches(state.getBlockData())){
                            if(worldBlock.getType() != Material.AIR){
                                correct_block++;
                            }
                        }
                    }
                }

            }
            int score = (correct_block*100)/total_block;
            if(score == 100){
                p.setGameMode(GameMode.ADVENTURE);
                p.sendTitle(ChatColor.GREEN + "你已完成建筑","",10,40,10);
                p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP,1,1);
                List<Integer> e = end();
                for(int i = 0;i<Math.min(e.size()-1,6);i++){
                    p.sendMessage(ChatColor.GREEN + "秒数：" + ChatColor.YELLOW + (i+1)*10 + ChatColor.GREEN + "，分数：" + ChatColor.YELLOW + e.get(i));
                }
                p.getInventory().clear();
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(ChatColor.GREEN + "最终秒数：" + ChatColor.YELLOW +  ticks/20.0f + "，最终分数：" + ChatColor.YELLOW + e.getLast());
            }
            if(ticks%200 == 0){
                scores.add(score);
            }
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.GREEN + "已用时：" + ChatColor.YELLOW + ticks/20.0f));
        }else{
            cancel();
        }
    }


    public List<Integer> end(){
        Integer k = ToolClass.exercise_player.get(p);
        if(k==null){
            return new ArrayList<>();
        }
        int total_block = 0;
        List<BlockState> states = new ArrayList<>();
        for(Palette palette : structure.getPalettes()){
            states.addAll(palette.getBlocks());
        }
        for(BlockState state : states){
            if(state.getType() != Material.AIR){
                total_block++;
            }
        }
        int correct_block = 0;
        for(BlockState state : states){
            int wx = 4 + 30*k + state.getX();
            int wy = 151 + state.getY();
            int wz = 4 + state.getZ();
            Block worldBlock = Bukkit.getWorld("world").getBlockAt(wx,wy,wz);
            if(state.getType() == worldBlock.getType()){
                if(state.getBlockData() instanceof Stairs){
                    if(StairsVisualComparator.Compare(state.getBlockData(),worldBlock.getBlockData())){
                        correct_block++;
                    }
                }else{
                    if(worldBlock.getBlockData().matches(state.getBlockData())){
                        if(worldBlock.getType() != Material.AIR){
                            correct_block++;
                        }
                    }
                }
            }

        }
        int score = (correct_block*100)/total_block;
        scores.add(score);

        cancel();
        ToolClass.exercise_timer.remove(p);
        return scores;

    }
}
