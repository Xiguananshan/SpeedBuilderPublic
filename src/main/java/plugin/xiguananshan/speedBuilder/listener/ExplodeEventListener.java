package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplodeEventListener implements Listener {
    @EventHandler
    public void run(EntityExplodeEvent e){
        e.setCancelled(true);
    }
}
