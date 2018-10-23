package op.lessnop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class DataHandler {

	static String przed = "§7[§bOtchlan§7] ";
	
	@SuppressWarnings("unchecked")
	public static void load() {
		
		

		StaticVars.InvName = (String) Main.inst().getConfig().get("settings.nazwaEkwipunku");
		StaticVars.itemy = (boolean) Main.inst().getConfig().get("settings.zapisItemow");
		
		if(StaticVars.itemy) {
			Main.zapis = new MainData(Main.inst());
			if(!Main.zapis.config.getKeys(true).isEmpty()) {
				for(String s : Main.zapis.config.getConfigurationSection("itemy").getKeys(false)) {
					Vars.itemy.add(Main.zapis.config.getItemStack("itemy." + s));
				}
			}
		}
		
		int i = Adds.stringToTime((String) Main.inst().getConfig().get("settings.czasOgolny")); 
		if(i < 1) Bukkit.getConsoleSender().sendMessage(przed + "§cBlad! Czas ogolny nie jest liczba dodatnia!");
		else StaticVars.time = i;
		
		i = Adds.stringToTime((String) Main.inst().getConfig().get("settings.czasWiadomosci")); 
		if(i < 1) Bukkit.getConsoleSender().sendMessage(przed + "§cBlad! Czas wiadomosci nie jest liczba dodatnia!");
		else if(i > StaticVars.time) {
			Bukkit.getConsoleSender().sendMessage(przed + "§cBlad! Czas wiadomosci jest wiekszy od czasu ogolnego! Zostal on ustawiony na polowe czasu ogolnego!");
			i = StaticVars.time /2;
		}
		else StaticVars.timeSend = i;
		
		i = Adds.stringToTime((String) Main.inst().getConfig().get("settings.czasOtwarcia")); 
		if(i < 1 && i != -1) Bukkit.getConsoleSender().sendMessage(przed + "§cBlad! Czas otwarcia nie jest liczba dodatnia! ");
		else if(i > StaticVars.time) {
			Bukkit.getConsoleSender().sendMessage(przed + "§cBlad! Czas otwarcia jest wiekszy od czasu ogolnego! Zostal on wylaczony (-1)");
			i = -1;
		}
		else StaticVars.timeAfter = i;
		

		
		List<String> l = new ArrayList<String>();
		StaticVars.swiaty = new ArrayList<World>();
		for(World w : Bukkit.getWorlds()) l.add(w.getName());

		l = (List<String>) Main.inst().getConfig().getList("settings.swiaty");
		for(String s : l) if(Bukkit.getWorld(s) != null) StaticVars.swiaty.add(Bukkit.getWorld(s));
		else Bukkit.getConsoleSender().sendMessage(przed + "§cBlad! Swiat " + s + " nie istnieje!");
		
		Wiadomosci.prefix = ((String) Main.inst().getConfig().get("wiadomosci.prefix")).replaceAll("/&", "§");
		
		for(int j = 0; j < 4; j++) Wiadomosci.msg[j] = ((String) Main.inst().getConfig().get("wiadomosci." + (j+1))).replaceAll("/&", "§");
		
		StaticVars.load();
		

		//Main.inst().getConfig().reload("nazwaEkwipunku", "Nazwa GUI ktore pojawia sie po uzyciu /otchlan");
		
		//Main.inst().saveConfig();
		

		
	}
	
	
	public static void unload() {
		if(!Main.zapis.config.getKeys(false).isEmpty())
		for(String s : Main.zapis.config.getConfigurationSection("itemy").getKeys(false)) {
			Main.zapis.config.set("itemy." + s, null);
		}
		int i = 0;
		for(ItemStack k : Vars.itemy) {
			Main.zapis.config.set("itemy." + i, k);
			i++;
		}
		Main.zapis.save();
	}
	

	

	
}
