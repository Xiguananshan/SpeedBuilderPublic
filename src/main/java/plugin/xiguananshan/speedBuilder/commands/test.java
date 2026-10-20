package plugin.xiguananshan.speedBuilder.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.VoxelShape;
import org.jspecify.annotations.NonNull;
import plugin.xiguananshan.speedBuilder.SpeedBuilder;

import java.util.ArrayList;
import java.util.List;

public class test implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        for (Material material : Material.values()){
            if(material.isAir()){
                continue;
            }
            List<String> blocks = new ArrayList<>();
            if(material.isBlock() && material.isItem()){
                BlockData data = Bukkit.createBlockData(material);
                Bukkit.getWorld("world").getBlockAt(1000,0,1000).setBlockData(data,false);
                Block block = Bukkit.getWorld("world").getBlockAt(1000,0,1000);
                VoxelShape shape = block.getCollisionShape();
                if(shape.getBoundingBoxes().size() == 1){
                    BoundingBox boundingBox = block.getBoundingBox();
                    if(boundingBox.getWidthX() == 1.0 && boundingBox.getWidthZ() == 1.0 && boundingBox.getHeight() == 1.0){
                        blocks.add(material.toString());
                    }
                }
            }
            SpeedBuilder.getPlugin(SpeedBuilder.class).getConfig().set("ground_blocks",blocks);
            SpeedBuilder.getPlugin(SpeedBuilder.class).saveConfig();
            SpeedBuilder.getPlugin(SpeedBuilder.class).reloadConfig();
        }
        return false;
    }
}
