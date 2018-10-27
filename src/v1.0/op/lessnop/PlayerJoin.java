package op.lessnop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;





public class PlayerJoin implements Listener{
	
	@EventHandler
	public void onBreak(PlayerItemDamageEvent e) {
		e.setCancelled(true);
	}
	
}
