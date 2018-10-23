package op.lessnop;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;





public class Main extends JavaPlugin {
	public Main(){}
	
	public static MainData zapis;
	private static Main instance;

	
	public void onEnable() {
		instance = this;
		if (!getDataFolder().exists()) getDataFolder().mkdir();
		
		saveDefaultConfig();
		DataHandler.load();
		
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InvEvent(), this);
		Vars.b = true;
		Timer.timer();
			 
	}
	
	public void onDisable() {
		Inv.closeAll();
		if(StaticVars.itemy) DataHandler.unload();
	}
	
	public static Main inst() {
		  return instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {

			if(cmd.getName().equalsIgnoreCase("Otchlan")) {
				if(sender instanceof Player) {
					if(args.length == 0) {
						if(!sender.hasPermission("otchlan.gracz.otworz") || !sender.hasPermission("otchlan.gracz.numer")) {
							sender.sendMessage(Wiadomosci.prefix + "§cNie masz permisji do tej komendy!");
							return false;
						}
						Player p = (Player) sender;
						Inv.open(p, 1);
					}
					else if(args.length == 1) {
						if(!sender.hasPermission("otchlan.gracz.numer")) {
							sender.sendMessage(Wiadomosci.prefix + "§cNie masz permisji do tej komendy!");
							return false;
						}
						if(StringUtils.isNumeric(args[0])) {
							Player p = (Player) sender;
							Inv.open(p, Integer.parseInt(args[0]));
						}
						else sender.sendMessage(Wiadomosci.prefix + "§cArgument 1 nie jest liczba - uzycie: /ot <nr strony>");
					}
					else sender.sendMessage(Wiadomosci.prefix + "§cUzycie: /ot §6lub §c/ot <nr strony>");
				}
				else sender.sendMessage("§cTa komende moze uzywac jedynie gracz!");

			}
			if(cmd.getName().equalsIgnoreCase("otchlanAdmin") || cmd.getName().equalsIgnoreCase("otAdmin")) {
				if(args.length == 2) {
					if(args[0].equals("reload")) {
						if(args[1].equals("config")) {
							if(!sender.hasPermission("otchlan.admin") && !sender.hasPermission("otchlan.admin.config")) {
								sender.sendMessage(Wiadomosci.prefix + "§cNie masz permisji do tej komendy!");
								return false;
							}
							Inv.closeAll();
							DataHandler.unload();
							DataHandler.load();
							sender.sendMessage("§aConfig zostal przeladowany. Wszelkie bledy zostaly wyswietlone w konsoli (jezeli sie pojawily).");
						}
						else if(args[1].equals("timer")) {
							if(!sender.hasPermission("otchlan.admin") && !sender.hasPermission("otchlan.admin.timer")) {
								sender.sendMessage(Wiadomosci.prefix + "§cNie masz permisji do tej komendy!");
								return false;
							}
							Inv.closeAll();
							Timer.reloadTimer();
							sender.sendMessage("§aZegar zostal zresetowany.");
						}
						else help(sender);
					}
					else help(sender);
				}
				else help(sender);
			}
		
		return true;
	}
	
	private static void help(CommandSender c) {
		c.sendMessage("§6/otAdmin reload config §7- przeladowuje config");
		c.sendMessage("§6/otAdmin reload timer §7- resetuje zegar");
	}
	

	

}
