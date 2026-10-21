package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;
import plugin.xiguananshan.speedBuilder.ToolClass;

public class DoubleJumpListener implements Listener {
    @EventHandler
    public void run(PlayerToggleFlightEvent e){
        Player p = e.getPlayer();
        if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR){
            return;
        }

        e.setCancelled(true);
        p.setAllowFlight(false);
        p.setFlying(false);
        if(!ToolClass.playing_player.contains(p) && !ToolClass.exercise_player.containsKey(p)){
            return;
        }

        Vector vector = p.getVelocity();
        vector.setY(1f);
        p.setVelocity(vector);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1f, 1f);
    }
}
