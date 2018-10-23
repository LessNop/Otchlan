package op.lessnop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class Timer {
	
	private static int time;

	public static void timer() {
		int k = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.inst(), new Runnable() {
			
			@Override
			public void run() {

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.inst(), new Runnable() {

					@Override
					public void run() {
						Display d = new Display(Wiadomosci.prefix + Wiadomosci.msg[2].replaceAll("%czas%", Adds.timeToString(StaticVars.timeSend)));
						d.sendToAll();
					}
					
				}, StaticVars.time - StaticVars.timeSend);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.inst(), new Runnable() {

					@Override
					public void run() {
						List<ItemStack> l = new ArrayList<ItemStack>();
						for(World w : StaticVars.swiaty) {
							for(Entity e : w.getEntities()) {
								if(e instanceof Item) {
									Item i = (Item) e;
									l.add(i.getItemStack());
									i.remove();
								}
							}
						}

						Vars.itemy.addAll(l);
						Vars.b = true;
						Display d = new Display(Wiadomosci.prefix + Wiadomosci.msg[1].replaceAll("%czaspo%", "" + StaticVars.timeAfter));
						d.sendToAll();
						Inv.refreshAll();
					}
					
				}, StaticVars.time);
				
				
			if(StaticVars.timeAfter!=-1)
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.inst(), new Runnable() {

					@Override
					public void run() {
						Vars.b = false;
						Inv.closeAll();
						Vars.itemy = new ArrayList<ItemStack>();
						
					}
				}, StaticVars.time + StaticVars.timeAfter);
			
		
		}
			
		}, 0, StaticVars.time);
		
		time = k;

	}
	
	public static void reloadTimer() {
		Bukkit.getScheduler().cancelTask(time);
		Vars.b = false;
		timer();
	}
	
}
