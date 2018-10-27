package op.lessnop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InvEvent implements Listener {

	@EventHandler
	public static void onClose(InventoryCloseEvent e) {
		if(Vars.list.contains(e.getPlayer())) {
			Vars.list.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	public static void onClick(InventoryClickEvent e) {
		if(Vars.list.contains(e.getWhoClicked())) {
			e.setCancelled(true);
			if(e.getSlot() == 53) Inv.close((Player) e.getWhoClicked());
			else if(e.getSlot() == 49) {
				Inv.close((Player) e.getWhoClicked());
				Inv.open((Player) e.getWhoClicked(), 1);
			}
			else if(e.getSlot() == 48 && e.getClickedInventory().getItem(48)!=null) {
				Inv.close((Player) e.getWhoClicked());
				Inv.open((Player) e.getWhoClicked(), Inv.get(e.getClickedInventory().getName()) - 1);
			}
			else if(e.getSlot() == 50 && e.getClickedInventory().getItem(50)!=null) {
				Inv.close((Player) e.getWhoClicked());
				Inv.open((Player) e.getWhoClicked(), Inv.get(e.getClickedInventory().getName()) + 1);
			}
			if(e.getSlot() > 44) return;
			
				if(Vars.itemy.contains(e.getCurrentItem())) {
					Vars.itemy.remove((Inv.get(e.getClickedInventory().getName()) -1)*45 + e.getSlot());
					for(int i = 0; i < 36; i++) {
						if(e.getWhoClicked().getInventory().getItem(i)==null) {
							e.getWhoClicked().getInventory().setItem(i, e.getCurrentItem());
							break;
						}
					}

					Inv.refreshAll();
					
				}
			


		
			
		}
	}
	
	
	
}
