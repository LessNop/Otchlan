package op.lessnop.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import op.lessnop.Main;
import op.lessnop.inventory.Inv;
import op.lessnop.math.Adds;
import op.lessnop.variables.StaticVars;
import op.lessnop.variables.Vars;

public class Timer {
	
	private int time;

	public void start(final StaticVars sv, final Inv inv, final Adds adds, final Vars vars) {
		int k = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.inst(), new Runnable() {
			
			@Override
			public void run() {

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.inst(), new Runnable() {

					@Override
					public void run() {
						Display d = new Display(sv.getPrefix() + sv.getMsg(2).replaceAll("%czas%", adds.timeToString(sv.getTimeSend())));
						d.sendToAll();
					}
					
				}, sv.getTime() - sv.getTimeSend());
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.inst(), new Runnable() {

					@Override
					public void run() {
						List<ItemStack> l = new ArrayList<ItemStack>();
						for(World w : sv.getWorlds()) {
							for(Entity e : w.getEntities()) {
								if(e instanceof Item) {
									Item i = (Item) e;
									l.add(i.getItemStack());
									i.remove();
								}
							}
						}

						vars.items.addAll(l);
						vars.isClosed = true;
						Display d = new Display(sv.getPrefix() + sv.getMsg(1).replaceAll("%czaspo%", "" + sv.getTimeAfter()));
						d.sendToAll();
						inv.refreshAll(vars, sv);
					}
					
				}, sv.getTime());
				
				
			if(sv.getTimeAfter()!=-1)
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.inst(), new Runnable() {

					@Override
					public void run() {
						vars.isClosed = false;
						inv.closeAll(vars, sv);
						vars.items = new ArrayList<ItemStack>();
						
					}
				}, sv.getTime() + sv.getTimeAfter());
			
		
		}
			
		}, 0, sv.getTime());
		
		time = k;

	}
	
	public void reloadTimer(StaticVars sv, Inv inv, Adds adds, Vars vars) {
		Bukkit.getScheduler().cancelTask(time);
		vars.isClosed = false;
		start(sv, inv, adds, vars);
	}
	
}
