package op.lessnop.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import op.lessnop.events.Display;
import op.lessnop.variables.StaticVars;
import op.lessnop.variables.Vars;




public class Inv {
	
	
	public void open(Vars vars, StaticVars sv, Player p, int i) {
		if(sv.getTimeAfter()!=-1) 
			if(!vars.isClosed) {
				Display d = new Display(sv.getPrefix() + sv.getMsg(4));
				d.sendToPlayer(p);
				return;
			}
		
		p.closeInventory();
		
		if(vars.items.isEmpty()) i = 1;
		else while((i-1) * 45 > vars.items.size()) i--;

		Inventory inv = Bukkit.createInventory(null, 54, sv.getInvName().replaceAll("%strona%", "" + i));
		i--; 
		for(int j = 0; j < 45; j++) {
			if(i*45 + j >= vars.items.size()) break;
			inv.setItem(j, vars.items.get(i*45 + j));
		}
		
		inv.setItem(53, sv.getClose());
		inv.setItem(49, sv.getMain());
		if(i != 0) inv.setItem(48, sv.getPrev());
		if( (i+1) * 45 < vars.items.size()) inv.setItem(50, sv.getNext());
		
		p.openInventory(inv);
		vars.list.add(p);
	}
	
	public void close(Vars vars, Player p) {
		if(!vars.list.contains(p)) return;
		p.closeInventory();
		vars.list.remove(p);
	}
	
	public void closeAll(Vars vars, StaticVars sv) {
		Display d = new Display(sv.getPrefix() + sv.getMsg(0));
		for(Player p : vars.list) {
			if(!vars.list.contains(p)) continue;
			p.closeInventory();
			d.sendToPlayer(p);
		}
		vars.list.clear();

	}
	
	public void refreshAll(Vars vars, StaticVars sv) {
		for(Player p : vars.list) {
			int nr = get(p.getOpenInventory().getTitle());
			if(vars.items.isEmpty()) {
				nr = 1;
				if(nr != get(p.getOpenInventory().getTitle())) {
					this.close(vars, p);
					this.open(vars, sv, p, nr);
				}
				else for(int j = 0; j < 45; j++) p.getOpenInventory().setItem(j, new ItemStack(Material.AIR));
				nr--;
			}
			else {
				while((nr-1)*45 >= vars.items.size()) nr--;
				if(nr != get(p.getOpenInventory().getTitle())) {
					this.close(vars, p);
					this.open(vars, sv, p, nr);
				}
				nr--; 
				for(int i = 0; i<45;i++) {
					if(nr * 45 + i >= vars.items.size()) {
						for(int j = i; j < 45; j++) p.getOpenInventory().setItem(j, new ItemStack(Material.AIR));
						break;
					}
					p.getOpenInventory().setItem(i, vars.items.get(nr*45 + i));
				}
			}
			if(nr != 0) p.getOpenInventory().setItem(48, sv.getPrev());
			else p.getOpenInventory().setItem(48, new ItemStack(Material.AIR));
			if( (nr+1) * 45 < vars.items.size()) p.getOpenInventory().setItem(50, sv.getNext());
			else p.getOpenInventory().setItem(50, new ItemStack(Material.AIR));
			
		}
	}
	

	
	public int get(String str) {
		int k = 1;
		for(int i =str.length() -1; i >= 0;i--) {
			if(str.toCharArray()[i] == ' ') {
				str = str.substring(i+1);
				k = Integer.parseInt(str);
				return k;
			}
		}
		return k;
	}
	
}
