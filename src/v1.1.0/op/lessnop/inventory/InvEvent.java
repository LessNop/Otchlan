package op.lessnop.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import op.lessnop.variables.StaticVars;
import op.lessnop.variables.Vars;

public class InvEvent implements Listener {

	private static StaticVars sv;
	
	public static void load(StaticVars sv2) {
		sv = sv2;
	}
	
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
				Inv.open(sv, (Player) e.getWhoClicked(), 1);
			}
			else if(e.getSlot() == 48 && e.getClickedInventory().getItem(48)!=null) {
				Inv.close((Player) e.getWhoClicked());
				Inv.open(sv, (Player) e.getWhoClicked(), Inv.get(e.getClickedInventory().getName()) - 1);
			}
			else if(e.getSlot() == 50 && e.getClickedInventory().getItem(50)!=null) {
				Inv.close((Player) e.getWhoClicked());
				Inv.open(sv, (Player) e.getWhoClicked(), Inv.get(e.getClickedInventory().getName()) + 1);
			}
			if(e.getSlot() > 44) return;
			
				if(Vars.items.contains(e.getCurrentItem())) {
					Vars.items.remove((Inv.get(e.getClickedInventory().getName()) -1)*45 + e.getSlot());
					for(int i = 0; i < 36; i++) {
						if(e.getWhoClicked().getInventory().getItem(i)==null) {
							e.getWhoClicked().getInventory().setItem(i, e.getCurrentItem());
							break;
						}
					}

					Inv.refreshAll(sv);
					
				}
			


		
			
		}
	}
	
	
	
}
