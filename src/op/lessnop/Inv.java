package op.lessnop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;




public class Inv {
	
	
	public static void open(Player p, int i) {
		if(StaticVars.timeAfter!=-1) 
			if(!Vars.b) {
				Display d = new Display(Wiadomosci.prefix + Wiadomosci.msg[4]);
				d.sendToPlayer(p);
				return;
			}
		
		p.closeInventory();
		
		if(Vars.itemy.isEmpty()) i = 1;
		else while((i-1) * 45 > Vars.itemy.size()) i--;

		Inventory inv = Bukkit.createInventory(null, 54, StaticVars.InvName.replaceAll("%strona%", "" + i));
		i--; 
		for(int j = 0; j < 45; j++) {
			if(i*45 + j >= Vars.itemy.size()) break;
			inv.setItem(j, Vars.itemy.get(i*45 + j));
		}
		
		inv.setItem(53, StaticVars.close);
		inv.setItem(49, StaticVars.main);
		if(i != 0) inv.setItem(48, StaticVars.prev);
		if( (i+1) * 45 < Vars.itemy.size()) inv.setItem(50, StaticVars.next);
		
		p.openInventory(inv);
		Vars.list.add(p);
	}
	
	public static void close(Player p) {
		if(!Vars.list.contains(p)) return;
		p.closeInventory();
		Vars.list.remove(p);
	}
	
	public static void closeAll() {
		Display d = new Display(Wiadomosci.prefix + Wiadomosci.msg[0]);
		for(Player p : Vars.list) {
			if(!Vars.list.contains(p)) continue;
			p.closeInventory();
			d.sendToPlayer(p);
		}
		Vars.list.clear();

	}
	
	public static void refreshAll() {
		for(Player p : Vars.list) {
			int nr = get(p.getOpenInventory().getTitle());
			while((nr-1)*45 >= Vars.itemy.size()) nr--;
			if(nr != get(p.getOpenInventory().getTitle())) {
				Inv.close(p);
				Inv.open(p, nr);
			}
			nr--; 
			for(int i = 0; i<45;i++) {
				if(nr * 45 + i >= Vars.itemy.size()) {
					for(int j = i; j < 45; j++) p.getOpenInventory().setItem(j, new ItemStack(Material.AIR));;
					break;
				}
				p.getOpenInventory().setItem(i, Vars.itemy.get(nr*45 + i));
				
			}
			if(nr != 0) p.getOpenInventory().setItem(48, StaticVars.prev);
			else p.getOpenInventory().setItem(48, new ItemStack(Material.AIR));
			if( (nr+1) * 45 < Vars.itemy.size()) p.getOpenInventory().setItem(50, StaticVars.next);
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
