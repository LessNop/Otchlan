package op.lessnop;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StaticVars {
	public static String InvName;
	public static int time, timeAfter, timeSend;
	public static List<World> swiaty;
	public static boolean itemy;

	public static ItemStack main = new ItemStack(Material.DIAMOND_BLOCK);
	public static ItemStack prev = new ItemStack(Material.IRON_INGOT), next = new ItemStack(Material.GOLD_INGOT); 
	public static ItemStack close = new ItemStack(Material.BARRIER);
	
	public static void load() {
		ItemMeta im = main.getItemMeta();
		im.setDisplayName("§b§lPowrot do 1 strony");
		main.setItemMeta(im);
		
		im = prev.getItemMeta();
		im.setDisplayName("§c§lPoprzednia strona");
		prev.setItemMeta(im);
		
		im = next.getItemMeta();
		im.setDisplayName("§a§lNastepna strona");
		next.setItemMeta(im);
		
		im = close.getItemMeta();
		im.setDisplayName("§4§lWYJSCIE");
		close.setItemMeta(im);
	}
	
}
