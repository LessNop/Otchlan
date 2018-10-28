package op.lessnop.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import op.lessnop.variables.StaticVars;
import op.lessnop.variables.Vars;

public class InvEvent implements Listener {

	private StaticVars sv;
	private Inv inv;
	private Vars vars;
	
	public InvEvent(StaticVars sv2, Inv inv2, Vars vars2) {
		sv = sv2; inv = inv2; vars = vars2;
	}
	

	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(vars.list.contains(e.getPlayer())) {
			vars.list.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(vars.list.contains(e.getWhoClicked())) {
			e.setCancelled(true);
			if(e.getSlot() == 53) inv.close(vars, (Player) e.getWhoClicked());
			else if(e.getSlot() == 49) {
				inv.close(vars, (Player) e.getWhoClicked());
				inv.open(vars, sv, (Player) e.getWhoClicked(), 1);
			}
			else if(e.getSlot() == 48 && e.getClickedInventory().getItem(48)!=null) {
				inv.close(vars, (Player) e.getWhoClicked());
				inv.open(vars, sv, (Player) e.getWhoClicked(), inv.get(e.getClickedInventory().getName()) - 1);
			}
			else if(e.getSlot() == 50 && e.getClickedInventory().getItem(50)!=null) {
				inv.close(vars, (Player) e.getWhoClicked());
				inv.open(vars, sv, (Player) e.getWhoClicked(), inv.get(e.getClickedInventory().getName()) + 1);
			}
			if(e.getSlot() > 44) return;
			
				if(vars.items.contains(e.getCurrentItem())) {
					vars.items.remove((inv.get(e.getClickedInventory().getName()) -1)*45 + e.getSlot());
					for(int i = 0; i < 36; i++) {
						if(e.getWhoClicked().getInventory().getItem(i)==null) {
							e.getWhoClicked().getInventory().setItem(i, e.getCurrentItem());
							break;
						}
					}

					inv.refreshAll(vars, sv);
					
				}
			


		
			
		}
	}
	
	
	
}
