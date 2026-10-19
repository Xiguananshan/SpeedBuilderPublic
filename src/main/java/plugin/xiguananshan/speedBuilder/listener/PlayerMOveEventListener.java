package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMOveEventListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            if (player.isOnGround()) {
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                }
            }
        }
    }
}
