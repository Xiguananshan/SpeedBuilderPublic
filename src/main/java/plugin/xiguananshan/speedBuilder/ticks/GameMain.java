package plugin.xiguananshan.speedBuilder.ticks;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.SmallDripleaf;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.structure.Palette;

import org.bukkit.structure.Structure;
import plugin.xiguananshan.speedBuilder.SpeedBuilder;
import plugin.xiguananshan.speedBuilder.StairsVisualComparator;
import plugin.xiguananshan.speedBuilder.ToolClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameMain implements Runnable{
    public int ticks = 0;

    @Override
    public void run() {
        if(ToolClass.playing){
            ticks++;
            int sav_count = ToolClass.playing_player.size() - ToolClass.died_player.size();
            if(sav_count <= 1){
                String name = "";
                for(Player p : ToolClass.playing_player){
                    if(!ToolClass.died_player.contains(p)){
                        name = p.getDisplayName();
                        break;
                    }
                }
                if (!name.isEmpty()) {
                    for(Player p : ToolClass.playing_player){
                        p.sendTitle(ChatColor.GREEN + "游戏结束",ChatColor.YELLOW + name + " 赢得了胜利",10,40,10);
                        p.teleport(new Location(p.getWorld(),0,-60,0));
                        p.getInventory().clear();
                        p.getInventory().setItem(0, ToolClass.create);
                        p.getInventory().setItem(1, ToolClass.play);
                        p.getInventory().setItem(2,ToolClass.exercise);
                        p.setGameMode(GameMode.ADVENTURE);
                        ToolClass.is_building = false;
                    }
                }else{
                    for(Player p : ToolClass.playing_player){
                        p.sendTitle(ChatColor.GREEN + "游戏结束",ChatColor.YELLOW + "本局平局",10,40,10);
                        p.teleport(new Location(p.getWorld(),0,-60,0));
                        p.getInventory().clear();
                        p.getInventory().setItem(0, ToolClass.create);
                        p.getInventory().setItem(1, ToolClass.play);
                        p.getInventory().setItem(2,ToolClass.exercise);
                        p.setGameMode(GameMode.ADVENTURE);
                        ToolClass.is_building = false;
                    }

                }
                for(int i = 0;i<ToolClass.playing_player.size();i++){
                    for(int x = 30*i;x<=30*i+14;x++){
                        for(int y = 200;y<=207;y++) {
                            for (int z = 0; z <= 14; z++) {
                                Bukkit.getWorld("world").getBlockAt(x, y, z).setBlockData(Bukkit.createBlockData(Material.AIR),false);
                            }
                        }
                    }
                }
                ToolClass.playing = false;
                ToolClass.correct_player.clear();
                ToolClass.playing_player.clear();
                ToolClass.died_player.clear();
            }
            if(ticks >= 0 && ticks <= 100){
                for(int i = 0;i< ToolClass.playing_player.size();i++){
                    if(ToolClass.playing_player.get(i).isOnline()){
                        ToolClass.playing_player.get(i).teleport(new Location(Bukkit.getWorld("world"),30*i+3,201,3));
                        ToolClass.playing_player.get(i).spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.GREEN + "游戏即将开始：" + ChatColor.YELLOW + (100f-ticks)/20f));
                    }
                }
            }
            if(ticks == 101){
                Path dir = Paths.get("plugins/buildings");
                ToolClass.correct_player.clear();
                List<File> all_structures = new ArrayList<>();
                try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir,"*.nbt")){
                    for(Path entry : stream){
                        if(Files.isRegularFile(entry)){
                            all_structures.add(entry.toFile());
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                Random random = new Random();

                int inx;
                if(ToolClass.problems.size() >= all_structures.size()){
                    ToolClass.problems.clear();
                }
                while(true){
                    inx = random.nextInt(all_structures.size());
                    if(!ToolClass.problems.contains(ToolClass.getBaseName(all_structures.get(inx)))){
                        ToolClass.problems.add(ToolClass.getBaseName(all_structures.get(inx)));
                        break;
                    }
                }
                try {
                    ToolClass.now_structure = Bukkit.getStructureManager().loadStructure(all_structures.get(inx));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for(int i = 0;i< ToolClass.playing_player.size();i++){
                    if(ToolClass.died_player.contains(ToolClass.playing_player.get(i))){
                        continue;
                    }
                    for(int x = 30*i;x<=30*i+14;x++){
                        for(int y = 201;y<=207;y++) {
                            for (int z = 0; z <= 14; z++) {
                                Bukkit.getWorld("world").getBlockAt(x, y, z).setBlockData(Bukkit.createBlockData(Material.AIR),false);
                            }
                        }
                    }
                }
                File ground_dir =  new File(Bukkit.getWorldContainer(),"plugins/grounds/" + ToolClass.getBaseName(all_structures.get(inx) ) + ".nbt");
                for(int i = 0;i< ToolClass.playing_player.size();i++){
                    if(ToolClass.died_player.contains(ToolClass.playing_player.get(i))){
                        continue;
                    }

                    if(ground_dir.exists()){

                        try {
                            Structure ground = Bukkit.getStructureManager().loadStructure(ground_dir);
                            ground.place(new Location(Bukkit.getWorld("world"),4+30*i,200,4),false,StructureRotation.NONE,Mirror.NONE,0,1,new Random());

                        } catch (IOException e) {
                            for(int x = 4 + 30*i;x<=10 + 30*i;x++){
                                for(int z = 4;z<=10;z++){
                                    Bukkit.getWorld("world").getBlockAt(x,200,z).setBlockData(Bukkit.createBlockData(Material.WHITE_CONCRETE));
                                }
                            }
                        }

                    }else{
                        for(int x = 4 + 30*i;x<=10 + 30*i;x++){
                            for(int z = 4;z<=10;z++){
                                Bukkit.getWorld("world").getBlockAt(x,200,z).setBlockData(Bukkit.createBlockData(Material.WHITE_CONCRETE));
                            }
                        }
                    }
                    ToolClass.now_structure.place(new Location(Bukkit.getWorld("world"),4+30*i,201,4),false, StructureRotation.NONE, Mirror.NONE,0,1.0f,new Random());
                    ToolClass.playing_player.get(i).getInventory().clear();
                    ToolClass.playing_player.get(i).sendTitle(ChatColor.GREEN + "第" + ChatColor.YELLOW + ToolClass.round + ChatColor.GREEN +  "轮",ToolClass.getBaseName(all_structures.get(inx)),10,40,10);


                }
                for(int x = 54;x<=60;x++){
                    for(int z = 54;z<=60;z++){
                        for(int y = 201;y<=207;y++){
                            Bukkit.getWorld("world").getBlockAt(x,y,z).setBlockData(Bukkit.createBlockData(Material.AIR),false);
                        }
                    }
                }
                if(ground_dir.exists()){
                    try {
                        Structure ground = Bukkit.getStructureManager().loadStructure(ground_dir);
                        ground.place(new Location(Bukkit.getWorld("world"),54,200,54),false,StructureRotation.NONE,Mirror.NONE,0,1,new Random());

                    } catch (IOException e) {
                        for(int x = 54;x<=60;x++){
                            for(int z = 54;z<=60;z++){
                                Bukkit.getWorld("world").getBlockAt(x,200,z).setBlockData(Bukkit.createBlockData(Material.WHITE_CONCRETE));
                            }
                        }
                    }

                }else{
                    for(int x = 54;x<=60;x++){
                        for(int z = 54;z<=60;z++){
                            Bukkit.getWorld("world").getBlockAt(x,200,z).setBlockData(Bukkit.createBlockData(Material.WHITE_CONCRETE));
                        }
                    }
                }
            }
            if(ticks>=102&&ticks<=102+15*20){
                for(int i = 0;i< ToolClass.playing_player.size();i++){
                    ToolClass.playing_player.get(i).spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.GREEN + "剩余记忆时间：" + ChatColor.YELLOW + (102+15*20f-ticks)/20f));
                }
            }
            if(ticks == 403){
                for(int i = 0;i< ToolClass.playing_player.size();i++){
                    if(ToolClass.died_player.contains(ToolClass.playing_player.get(i))){
                        continue;
                    }
                    ToolClass.playing_player.get(i).setGameMode(GameMode.SURVIVAL);
                    ToolClass.playing_player.get(i).sendTitle(ChatColor.GREEN + "开始复原","",10,40,10);
                    ToolClass.playing_player.get(i).getInventory().clear();
                    for(int x = 0;x<=6;x++){
                        for(int y = 0;y<=6;y++){
                            for(int z = 0;z<=6;z++){
                                ToolClass.playing_player.get(i).getWorld().getBlockAt(4+30*i+x,201+y,4+z).setBlockData(Bukkit.createBlockData(Material.AIR),false);
                            }
                        }
                    }
                    List<BlockState> states = new ArrayList<>();
                    for(Palette palette : ToolClass.now_structure.getPalettes()){
                        states.addAll(palette.getBlocks());
                    }
                    for(BlockState state : states){
                        if(state.getBlockData() instanceof Bed b){
                            if(b.getPart() == Bed.Part.HEAD){
                                ToolClass.playing_player.get(i).getInventory().addItem(new ItemStack(state.getType()));
                            }

                        }else if(state.getBlockData() instanceof Bisected b){
                            if(state.getBlockData() instanceof Stairs || state.getBlockData() instanceof TrapDoor || state.getBlockData() instanceof SmallDripleaf){
                                ToolClass.playing_player.get(i).getInventory().addItem(new ItemStack(state.getType()));
                            }else if(b.getHalf() == Bisected.Half.BOTTOM){
                                ToolClass.playing_player.get(i).getInventory().addItem(new ItemStack(state.getType()));
                            }
                        }else{
                            ToolClass.playing_player.get(i).getInventory().addItem(new ItemStack(state.getType()));
                        }

                    }
                }
                ToolClass.is_building = true;

            }
            if(ticks >= 403 && ticks <= 403 + 20*ToolClass.global_time && ToolClass.is_building){
                List<BlockState> states = new ArrayList<>();
                for(Palette palette : ToolClass.now_structure.getPalettes()){
                    states.addAll(palette.getBlocks());
                }
                boolean all_correct = true;
                for(int i = 0;i<ToolClass.playing_player.size();i++){
                    ToolClass.playing_player.get(i).spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.GREEN + "剩余复原时间：" + ChatColor.YELLOW + (403 + 20*ToolClass.global_time -ticks)/20f));
                    if(ToolClass.died_player.contains(ToolClass.playing_player.get(i))){
                        continue;
                    }
                    if(!ToolClass.correct_player.contains(ToolClass.playing_player.get(i))){
                        all_correct = false;
                        boolean OK = true;
                        for(BlockState state : states){
                            int wx = 4 + 30*i + state.getX();
                            int wy = 201 + state.getY();
                            int wz = 4 + state.getZ();
                            Block worldBlock = ToolClass.playing_player.get(i).getWorld().getBlockAt(wx,wy,wz);
                            if(state.getType() != worldBlock.getType()){
                                OK = false;
                                break;
                            }
                            if(state.getBlockData() instanceof Stairs){
                                if(!StairsVisualComparator.Compare(state.getBlockData(),worldBlock.getBlockData())){
                                    OK = false;
                                    break;
                                }
                            }else{
                                if(!worldBlock.getBlockData().matches(state.getBlockData())){
                                    OK = false;
                                    break;
                                }
                            }

                        }
                        if(OK){
                            ToolClass.correct_player.add(ToolClass.playing_player.get(i));
                            ToolClass.playing_player.get(i).setGameMode(GameMode.ADVENTURE);
                            ToolClass.playing_player.get(i).sendTitle(ChatColor.GREEN + "你已完成建筑","",10,40,10);
                            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + ToolClass.playing_player.get(i).getDisplayName() + ChatColor.GREEN + "已完成");
                            ToolClass.playing_player.get(i).playSound(ToolClass.playing_player.get(i),Sound.ENTITY_PLAYER_LEVELUP,1,1);
                        }
                    }

                }
                if(all_correct){
                    for(int i = 0;i<ToolClass.playing_player.size();i++){
                        ToolClass.playing_player.get(i).sendTitle(ChatColor.GREEN + "全部完成","",10,40,10);
                        ToolClass.playing_player.get(i).setGameMode(GameMode.SPECTATOR);
                    }
                    ToolClass.is_building = false;
                    ticks = 403;
                }
            }
            if(ticks == 463 && !ToolClass.is_building){
                for(int i = 0;i<ToolClass.playing_player.size();i++){
                    if(!ToolClass.died_player.contains(ToolClass.playing_player.get(i))){
                        ToolClass.playing_player.get(i).teleport(new Location(Bukkit.getWorld("world"),30*i+3,201,3));
                        ToolClass.playing_player.get(i).setGameMode(GameMode.ADVENTURE);
                    }
                }
                ToolClass.round++;
                ticks = 100;
                ToolClass.global_time = Math.max(20,60 - 2*(ToolClass.round-1));
            }
            if(ticks==404 + 20*ToolClass.global_time && ToolClass.is_building){
                for(int i = 0;i<ToolClass.playing_player.size();i++){
                    if(ToolClass.died_player.contains(ToolClass.playing_player.get(i))){
                        continue;
                    }
                    ToolClass.playing_player.get(i).setGameMode(GameMode.SPECTATOR);
                    ToolClass.playing_player.get(i).sendTitle(ChatColor.YELLOW + "时间结束",ChatColor.GREEN + "进入评分阶段",10,40,10);
                    ToolClass.playing_player.get(i).playSound(ToolClass.playing_player.get(i),Sound.BLOCK_NOTE_BLOCK_HARP,1,1);
                    ToolClass.now_structure.place(new Location(Bukkit.getWorld("world"),54,201,54),false,StructureRotation.NONE,Mirror.NONE,0,1,new Random());
                }
            }
            if(ticks == 405 + 20*ToolClass.global_time + 60 && ToolClass.is_building){
                List<BlockState> states = new ArrayList<>();
                for(Palette palette : ToolClass.now_structure.getPalettes()){
                    states.addAll(palette.getBlocks());
                }
                int total_block = 0;
                for(BlockState state : states){
                    if(state.getType() != Material.AIR){
                        total_block++;
                    }
                }
                for(int i = 0;i<ToolClass.playing_player.size();i++){
                    if(ToolClass.died_player.contains(ToolClass.playing_player.get(i))){
                        continue;
                    }
                    if(ToolClass.correct_player.contains(ToolClass.playing_player.get(i))){
                        ToolClass.playing_player.get(i).sendTitle(ChatColor.GREEN + "你的分数：" + ChatColor.YELLOW + 100,"",10,40,10);
                        continue;
                    }
                    int correct_block = 0;
                    for(BlockState state : states){
                        int wx = 4 + 30*i + state.getX();
                        int wy = 201 + state.getY();
                        int wz = 4 + state.getZ();
                        Block worldBlock = ToolClass.playing_player.get(i).getWorld().getBlockAt(wx,wy,wz);
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
                    ToolClass.playing_player.get(i).sendTitle(ChatColor.GREEN + "你的分数：" + ChatColor.YELLOW + score,"",10,40,10);
                }

            }
            if(ticks == 406 + 20*ToolClass.global_time + 60*2 && ToolClass.is_building){
                int min_score = 101;
                int min_index = -1;
                List<BlockState> states = new ArrayList<>();
                for(Palette palette : ToolClass.now_structure.getPalettes()){
                    states.addAll(palette.getBlocks());
                }
                int total_block = 0;
                for(BlockState state : states){
                    if(state.getType() != Material.AIR){
                        total_block++;
                    }
                }

                for(int i = 0;i<ToolClass.playing_player.size();i++){
                    if(ToolClass.died_player.contains(ToolClass.playing_player.get(i))){
                        continue;
                    }
                    if(ToolClass.correct_player.contains(ToolClass.playing_player.get(i))){
                        continue;
                    }
                    int correct_block = 0;
                    for(BlockState state : states){
                        int wx = 4 + 30*i + state.getX();
                        int wy = 201 + state.getY();
                        int wz = 4 + state.getZ();
                        Block worldBlock = ToolClass.playing_player.get(i).getWorld().getBlockAt(wx,wy,wz);
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
                    if(score < min_score){
                        min_score = score;
                        min_index = i;
                    }
                }
                if(min_index != -1){
                    for(Player player : ToolClass.playing_player){
                        player.sendTitle(ToolClass.playing_player.get(min_index).getDisplayName() + ChatColor.RED + "淘汰","",10,40,10);
                    }
                }
                ToolClass.died_player.add(ToolClass.playing_player.get(min_index));
                for(int x = 30*min_index;x<=30*min_index+14;x++){
                    for(int y = 200;y<=207;y++){
                        for(int z = 0;z<=14;z++){
                            Bukkit.getWorld("world").setBlockData(x,y,z,Bukkit.createBlockData(Material.AIR));
                        }
                    }

                }
            }
            if(ticks == 407 + 20*ToolClass.global_time + 60*3 && ToolClass.is_building){
                ToolClass.is_building = false;
                for(int i = 0;i<ToolClass.playing_player.size();i++){
                    if(!ToolClass.died_player.contains(ToolClass.playing_player.get(i))){
                        ToolClass.playing_player.get(i).teleport(new Location(Bukkit.getWorld("world"),30*i+3,201,3));
                        ToolClass.playing_player.get(i).setGameMode(GameMode.ADVENTURE);
                    }
                }
                ToolClass.round++;
                ticks = 100;
                ToolClass.global_time = Math.max(20,60 - 2*(ToolClass.round-1));
            }
        }else{
            ticks = 0;
            ToolClass.round = 1;
            ToolClass.now_structure = null;
            ToolClass.died_player.clear();
            ToolClass.problems.clear();
            ToolClass.global_time = 60;
        }

    }
}
