package op.lessnop.variables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import op.lessnop.Main;
import op.lessnop.math.Adds;
import op.lessnop.save.MainData;

public class StaticVars {
	private String mainPrefix = "§7[§bOtchlan§7] ", InvName, prefix, msg[] = new String[5];
	private List<ItemStack> itemList = new ArrayList<ItemStack>();
	private int time, timeAfter, timeSend;
	private List<World> worlds;
	private boolean items;

	private ItemStack main = new ItemStack(Material.DIAMOND_BLOCK),
			prev = new ItemStack(Material.IRON_INGOT),
			next = new ItemStack(Material.GOLD_INGOT),
			close = new ItemStack(Material.BARRIER);
	

	public StaticVars() {
		this.load();
	}
	
	@SuppressWarnings("unchecked")
	private void load() {
		this.InvName = (String) Main.inst().getConfig().get("settings.nazwaEkwipunku");
		this.items = (boolean) Main.inst().getConfig().get("settings.zapisItemow");
		
		if(this.items) {
			Main.zapis = new MainData(Main.inst());
			if(!Main.zapis.config.getKeys(true).isEmpty()) {
				for(String s : Main.zapis.config.getConfigurationSection("itemy").getKeys(false)) {
					this.itemList.add(Main.zapis.config.getItemStack("itemy." + s));
				}
			}
		}
		
		int i = Adds.stringToTime((String) Main.inst().getConfig().get("settings.czasOgolny")); 
		if(i < 1) Bukkit.getConsoleSender().sendMessage(mainPrefix + "§cBlad! Czas ogolny nie jest liczba dodatnia!");
		else this.time = i;
		
		i = Adds.stringToTime((String) Main.inst().getConfig().get("settings.czasWiadomosci")); 
		if(i < 1) Bukkit.getConsoleSender().sendMessage(mainPrefix + "§cBlad! Czas wiadomosci nie jest liczba dodatnia!");
		else if(i > this.time) {
			Bukkit.getConsoleSender().sendMessage(mainPrefix + "§cBlad! Czas wiadomosci jest wiekszy od czasu ogolnego! Zostal on ustawiony na polowe czasu ogolnego!");
			i = this.time /2;
		}
		else this.timeSend = i;
		
		i = Adds.stringToTime((String) Main.inst().getConfig().get("settings.czasOtwarcia")); 
		if(i < 1 && i != -1) Bukkit.getConsoleSender().sendMessage(mainPrefix + "§cBlad! Czas otwarcia nie jest liczba dodatnia! ");
		else if(i > this.time) {
			Bukkit.getConsoleSender().sendMessage(mainPrefix + "§cBlad! Czas otwarcia jest wiekszy od czasu ogolnego! Zostal on wylaczony (-1)");
			i = -1;
		}
		else this.timeAfter = i;
		
		List<String> l = new ArrayList<String>();
		this.worlds = new ArrayList<World>();

		l = (List<String>) Main.inst().getConfig().getList("settings.swiaty");
		for(String s : l) if(Bukkit.getWorld(s) != null) this.worlds.add(Bukkit.getWorld(s));
		else Bukkit.getConsoleSender().sendMessage(mainPrefix + "§cBlad! Swiat " + s + " nie istnieje!");
		
		this.prefix = ((String) Main.inst().getConfig().get("wiadomosci.prefix")).replaceAll("/&", "§");
		
		for(int j = 0; j < 4; j++) this.msg[j] = ((String) Main.inst().getConfig().get("wiadomosci." + (j+1))).replaceAll("/&", "§");
		
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
	
	public String getInvName() {
		return this.InvName;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public int getTimeAfter() {
		return this.timeAfter;
	}
	
	public int getTimeSend() {
		return this.timeSend;
	}
	
	public List<World> getWorlds() {
		return this.worlds;
	}
	
	public boolean getItems() {
		return this.items;
	}
	
	public void setItems(boolean b) {
		this.items = b;
	}
	
	public ItemStack getMain() {
		return this.main;
	}
	public ItemStack getPrev() {
		return this.prev;
	}
	public ItemStack getNext() {
		return this.next;
	}
	public ItemStack getClose() {
		return this.close;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public String getMsg(int i) {
		return this.msg[i];
	}
	
	public void unload() {
		if(!Main.zapis.config.getKeys(false).isEmpty())
			for(String s : Main.zapis.config.getConfigurationSection("itemy").getKeys(false)) {
				Main.zapis.config.set("itemy." + s, null);
			}
			int i = 0;
			for(ItemStack k : this.itemList) {
				Main.zapis.config.set("itemy." + i, k);
				i++;
			}
			Main.zapis.save();
	}
	
	public void reload() {
		this.unload();
		this.load();
	}
	
	
}
