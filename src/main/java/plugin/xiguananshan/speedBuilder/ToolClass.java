package plugin.xiguananshan.speedBuilder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import org.bukkit.entity.Display;
import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.structure.Structure;
import plugin.xiguananshan.speedBuilder.ticks.PlayerExerciseTimer;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ToolClass {
    public static ItemStack create = new ItemStack(Material.SLIME_BALL);
    public static ItemStack play = new ItemStack(Material.MAGMA_CREAM);
    public static ItemStack cancel = new ItemStack(Material.STICK);
    public static Player creating_player = null;
    public static Structure create_structure = null;
    public static List<Player> wait_player = new ArrayList<>();
    public static Map<Player,Integer> destroy_tick = new HashMap<>();
    public static Map<Player,Integer> exercise_player = new HashMap<>();
    public static Map<Player,Integer> exercise_player_page = new HashMap<>();
    public static int start_time = 30;
    public static int ground_page = 1;
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
    public static ItemStack deny = new ItemStack(Material.BARRIER);
    public static ItemStack prev = new ItemStack(Material.ARROW);
    public static ItemStack next = new ItemStack(Material.PAPER);
    public static ItemStack exercise = new ItemStack(Material.SNOWBALL);
    public static SpeedBuilder PLUGIN = SpeedBuilder.getPlugin(SpeedBuilder.class);
    public static Map<Player,Structure> exercise_structure = new HashMap<>();
    public static Map<Player,Structure> exercise_ground = new HashMap<>();
    public static Map<Player, PlayerExerciseTimer> exercise_timer = new HashMap<>();
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


        meta1 = deny.getItemMeta();
        assert meta1 != null;
        meta1.setDisplayName(" ");
        meta1.getPersistentDataContainer().set(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class),"target"), PersistentDataType.BOOLEAN,true);
        deny.setItemMeta(meta1);

        meta1 = prev.getItemMeta();
        assert meta1 != null;
        meta1.setDisplayName(ChatColor.YELLOW + "上一页");
        meta1.getPersistentDataContainer().set(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class),"target"), PersistentDataType.BOOLEAN,true);
        prev.setItemMeta(meta1);

        meta1 = exercise.getItemMeta();
        assert meta1 != null;
        meta1.setDisplayName(ChatColor.YELLOW + "练习");
        meta1.setLore(List.of(ChatColor.GREEN + "右键前往练习"));
        meta1.getPersistentDataContainer().set(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class),"target"), PersistentDataType.BOOLEAN,true);
        exercise.setItemMeta(meta1);

        meta1 = next.getItemMeta();
        assert meta1 != null;
        meta1.setDisplayName(ChatColor.YELLOW + "下一页");
        meta1.getPersistentDataContainer().set(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class),"target"), PersistentDataType.BOOLEAN,true);
        next.setItemMeta(meta1);
        List<String> names = SpeedBuilder.getPlugin(SpeedBuilder.class).getConfig().getStringList("ground_blocks");
        for(String m : names){
            try {
                grounds_list.add(Material.valueOf(m));
            }catch (Exception e){

            }
        }
    }
    public static String getBaseName(File file) {
        String name = file.getName();
        int dot = name.lastIndexOf('.');
        return (dot > 0) ? name.substring(0, dot) : name;
    }
    public static List<Material> GetPage(int index){
        List<Material> materials = new ArrayList<>();
        for(int i = 45*(index-1);i<=45*(index-1)+44;i++){
            if(i<grounds_list.size()){
                materials.add(grounds_list.get(i));
            }else{
                break;
            }
        }
        return materials;
    }
    public static Inventory GetGroundInv(int page){
        List<Material> materials = GetPage(page);
        Inventory ans = Bukkit.createInventory(null,54,"选择地板方块");
        for(int i = 0;i<54;i++){
            ans.setItem(i,deny);
        }
        for(int i = 0;i<materials.size();i++){
            ans.setItem(i,BuildGroundItem(materials.get(i)));
        }
        if(page >= 2){
            ans.setItem(45,prev);
        }
        if(materials.size() == 45){
            ans.setItem(53,next);
        }
        return ans;
    }

    public static Inventory GetProblemInv(int page){
        Inventory ans = Bukkit.createInventory(null,54,"选择练习题目");
        List<String> all = GetPageProblem(page);
        for(int i = 0;i<54;i++){
            ans.setItem(i,deny);
        }
        for(int i = 0;i<all.size();i++){
            ans.setItem(i,BuildProblemItem(all.get(i)));
        }
        ItemStack prev = new ItemStack(Material.ARROW),next = new ItemStack(Material.PAPER);
        ItemMeta m1 =prev.getItemMeta(),m2 = next.getItemMeta();
        assert m1!=null && m2 != null;
        m1.setDisplayName(ChatColor.YELLOW + "上一页");
        m2.setDisplayName(ChatColor.YELLOW + "下一页");
        m1.getPersistentDataContainer().set(new NamespacedKey(PLUGIN,"problem_target"),PersistentDataType.BOOLEAN,true);
        m2.getPersistentDataContainer().set(new NamespacedKey(PLUGIN,"problem_target"),PersistentDataType.BOOLEAN,true);
        prev.setItemMeta(m1);
        next.setItemMeta(m2);
        if(page >= 2){
            ans.setItem(45,prev);
        }
        if(all.size() == 45){
            ans.setItem(53,next);
        }
        return ans;
    }

    public static ItemStack BuildGroundItem(Material material){
        ItemStack ans = new ItemStack(material);
        ItemMeta meta = ans.getItemMeta();
        assert meta != null;
        meta.setLore(List.of(ChatColor.YELLOW + "点击选择"));
        meta.getPersistentDataContainer().set(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class),"ground"),PersistentDataType.BOOLEAN,true);
        ans.setItemMeta(meta);
        return ans;
    }

    public static ItemStack BuildProblemItem(String name){
        ItemStack ans = new ItemStack(Material.PAPER);
        ItemMeta meta = ans.getItemMeta();
        assert meta != null;
        meta.getPersistentDataContainer().set(new NamespacedKey(SpeedBuilder.getPlugin(SpeedBuilder.class),"problem"),PersistentDataType.STRING,name);
        meta.setDisplayName(ChatColor.WHITE + name);
        meta.setLore(List.of(ChatColor.YELLOW + "点击选择"));
        ans.setItemMeta(meta);
        return ans;
    }

    public static List<String> GetPageProblem(int page){
        Path dir = Paths.get("plugins/buildings");
        List<File> all_structures = new ArrayList<>();
        List<String> ans = new ArrayList<>();
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir,"*.nbt")){
            for(Path entry : stream){
                if(Files.isRegularFile(entry)){
                    all_structures.add(entry.toFile());
                }
            }
            System.out.println(all_structures.size());
            for(int i = 45*(page-1);i<=45*(page-1)+44;i++){
                if(i<all_structures.size()){
                    ans.add(getBaseName(all_structures.get(i)));
                }else{
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return ans;
    }
}
