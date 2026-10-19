package plugin.xiguananshan.speedBuilder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.structure.Structure;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.VoxelShape;

import java.io.File;
import java.util.*;

public class ToolClass {
    public static ItemStack create = new ItemStack(Material.SLIME_BALL);
    public static ItemStack play = new ItemStack(Material.MAGMA_CREAM);
    public static ItemStack cancel = new ItemStack(Material.STICK);
    public static Player creating_player = null;
    public static Structure create_structure = null;
    public static List<Player> wait_player = new ArrayList<>();
    public static Map<Player,Integer> destroy_tick = new HashMap<>();
    public static int start_time = 30;
    public static List<Player> playing_player = new ArrayList<>();
    public static boolean playing = false;
    public static boolean is_building = false;
    public static Structure now_structure = null;
    public static int round = 1;
    public static HashSet<Player> correct_player = new HashSet<>();
    public static HashSet<Player> died_player = new HashSet<>();
    public static HashSet<String> problems = new HashSet<>();
    public static int global_time = 60;
    public static List<Material> grounds_list = new ArrayList<>();
    public static void Init(){
        ItemMeta meta = create.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.YELLOW + "出题");
        meta.setLore(List.of(ChatColor.GREEN + "右键前往出题"));
        meta.getPersistentDataContainer().set(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class),"target"), PersistentDataType.BOOLEAN,true);
        create.setItemMeta(meta);

        ItemMeta meta1 = play.getItemMeta();
        assert meta1 != null;
        meta1.setDisplayName(ChatColor.YELLOW + "游玩");
        meta1.setLore(List.of(ChatColor.GREEN + "右键进行准备"));
        meta1.getPersistentDataContainer().set(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class),"target"), PersistentDataType.BOOLEAN,true);
        play.setItemMeta(meta1);

        meta1 = cancel.getItemMeta();
        assert meta1 != null;
        meta1.setDisplayName(ChatColor.YELLOW + "退出队列");
        meta1.setLore(List.of(ChatColor.GREEN + "右键进行退出"));
        meta1.getPersistentDataContainer().set(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class),"target"), PersistentDataType.BOOLEAN,true);
        cancel.setItemMeta(meta1);
        for(Material material : Material.values()){
            if(!material.isAir()){
                if(material.isBlock()){
                    BlockData blockData = Bukkit.createBlockData(material);
                    Bukkit.getWorld("world").setBlockData(1000,0,1000,blockData);
                    VoxelShape shape = Bukkit.getWorld("world").getBlockAt(1000,0,1000).getCollisionShape();
                    if(shape.getBoundingBoxes().size() == 1){
                        BoundingBox boundingBox = Bukkit.getWorld("world").getBlockAt(1000,0,1000).getBoundingBox();
                        if(boundingBox.getWidthX() == 1.0 && boundingBox.getHeight() == 1.0 && boundingBox.getWidthZ() == 1.0){
                            if(!(Bukkit.getWorld("world").getBlockAt(1000,0,1000).getState() instanceof TileState)){
                                grounds_list.add(material);
                            }

                        }

                    }
                }
            }
        }
    }
    public static String getBaseName(File file) {
        String name = file.getName();
        int dot = name.lastIndexOf('.');
        return (dot > 0) ? name.substring(0, dot) : name;
    }
}
