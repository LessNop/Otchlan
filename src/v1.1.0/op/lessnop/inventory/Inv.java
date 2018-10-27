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
	
	
	public static void open(StaticVars sv, Player p, int i) {
		if(sv.getTimeAfter()!=-1) 
			if(!Vars.isClosed) {
				Display d = new Display(sv.getPrefix() + sv.getMsg(4));
				d.sendToPlayer(p);
				return;
			}
		
		p.closeInventory();
		
		if(Vars.items.isEmpty()) i = 1;
		else while((i-1) * 45 > Vars.items.size()) i--;

		Inventory inv = Bukkit.createInventory(null, 54, sv.getInvName().replaceAll("%strona%", "" + i));
		i--; 
		for(int j = 0; j < 45; j++) {
			if(i*45 + j >= Vars.items.size()) break;
			inv.setItem(j, Vars.items.get(i*45 + j));
		}
		
		inv.setItem(53, sv.getClose());
		inv.setItem(49, sv.getMain());
		if(i != 0) inv.setItem(48, sv.getPrev());
		if( (i+1) * 45 < Vars.items.size()) inv.setItem(50, sv.getNext());
		
		p.openInventory(inv);
		Vars.list.add(p);
	}
	
	public static void close(Player p) {
		if(!Vars.list.contains(p)) return;
		p.closeInventory();
		Vars.list.remove(p);
	}
	
	public static void closeAll(StaticVars sv) {
		Display d = new Display(sv.getPrefix() + sv.getMsg(0));
		for(Player p : Vars.list) {
			if(!Vars.list.contains(p)) continue;
			p.closeInventory();
			d.sendToPlayer(p);
		}
		Vars.list.clear();

	}
	
	public static void refreshAll(StaticVars sv) {
		for(Player p : Vars.list) {
			int nr = get(p.getOpenInventory().getTitle());
			while((nr-1)*45 >= Vars.items.size()) nr--;
			if(nr != get(p.getOpenInventory().getTitle())) {
				Inv.close(p);
				Inv.open(sv, p, nr);
			}
			nr--; 
			for(int i = 0; i<45;i++) {
				if(nr * 45 + i >= Vars.items.size()) {
					for(int j = i; j < 45; j++) p.getOpenInventory().setItem(j, new ItemStack(Material.AIR));;
					break;
				}
				p.getOpenInventory().setItem(i, Vars.items.get(nr*45 + i));
				
			}
			if(nr != 0) p.getOpenInventory().setItem(48, sv.getPrev());
			else p.getOpenInventory().setItem(48, new ItemStack(Material.AIR));
			if( (nr+1) * 45 < Vars.items.size()) p.getOpenInventory().setItem(50, sv.getNext());
			else p.getOpenInventory().setItem(50, new ItemStack(Material.AIR));
			
		}
	}
	

	
	public static int get(String str) {
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
