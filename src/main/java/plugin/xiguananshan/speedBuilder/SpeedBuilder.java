package plugin.xiguananshan.speedBuilder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;
import plugin.xiguananshan.speedBuilder.commands.*;
import plugin.xiguananshan.speedBuilder.listener.*;
import plugin.xiguananshan.speedBuilder.ticks.CreateMoveCheck;
import plugin.xiguananshan.speedBuilder.ticks.GameMain;
import plugin.xiguananshan.speedBuilder.ticks.PlayQueueManager;

public final class SpeedBuilder extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(),this);
        getServer().getPluginManager().registerEvents(new ExplodeEventListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerBreakBlockEventListener(),this);
        getServer().getPluginManager().registerEvents(new PlayCreateEvent(),this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveEventListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerChatEventListener(),this);
        getServer().getPluginManager().registerEvents(new BlockPlaceEventListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerDestroyBlockListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerPlayEventListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerQuitPlayingEventListener(),this);
        getServer().getPluginManager().registerEvents(new DoubleJumpListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerMOveEventListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerDamageEventListener(),this);
        getServer().getPluginManager().registerEvents(new BlockPositionEventListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerClickInvEventListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerClickBlockEventListener(),this);
        getCommand("back").setExecutor(new BackCommand());
        getCommand("end").setExecutor(new EndCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("set_ground").setExecutor(new SetGroundCommand());
        getCommand("test").setExecutor(new test());
        getServer().getScheduler().runTaskTimer(this,new CreateMoveCheck(),0,1);
        getServer().getScheduler().runTaskTimer(this,new PlayQueueManager(),0,1);
        getServer().getScheduler().runTaskTimer(this,new GameMain(),0,1);
        saveDefaultConfig();
        ToolClass.Init();
        //System.out.println(ToolClass.grounds_list.size());
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for(Player p : Bukkit.getOnlinePlayers()){
            p.kickPlayer(ChatColor.RED + "插件热重载，请重新进服");
        }
    }
}
