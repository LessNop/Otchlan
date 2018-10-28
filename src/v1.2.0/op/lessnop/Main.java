package op.lessnop;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import op.lessnop.events.Timer;
import op.lessnop.inventory.Inv;
import op.lessnop.inventory.InvEvent;
import op.lessnop.math.Adds;
import op.lessnop.save.MainData;
import op.lessnop.variables.StaticVars;
import op.lessnop.variables.Vars;





public class Main extends JavaPlugin {
	public Main(){}
	
	public static MainData zapis;
	private static Main instance;
	private StaticVars sv;
	private Inv inv = new Inv();
	private Timer t = new Timer();
	private Adds adds = new Adds();
	private Vars vars = new Vars();
	
	public void onEnable() {
		instance = this;
		if (!getDataFolder().exists()) getDataFolder().mkdir();
		
		saveDefaultConfig();
		
		
		
		vars.isClosed = true;
		sv = new StaticVars(adds);
		t.start(sv, inv, adds, vars);
		InvEvent ie = new InvEvent(sv, inv, vars);
		Bukkit.getServer().getPluginManager().registerEvents(ie, this);
			 
	}
	
	public void onDisable() {
		inv.closeAll(vars, sv);
		if(sv.getItems()) sv.unload();
	}
	
	public static Main inst() {
		  return instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {

			if(cmd.getName().equalsIgnoreCase("Otchlan")) {
				if(sender instanceof Player) {
					if(args.length == 0) {
						if(!sender.hasPermission("otchlan.gracz.otworz") || !sender.hasPermission("otchlan.gracz.strona")) {
							sender.sendMessage(sv.getPrefix() + "§cNie masz permisji do tej komendy!");
							return false;
						}
						Player p = (Player) sender;
						inv.open(vars, sv, p, 1);
					}
					else if(args.length == 1) {
						if(!sender.hasPermission("otchlan.gracz.strona")) {
							sender.sendMessage(sv.getPrefix() + "§cNie masz permisji do tej komendy!");
							return false;
						}
						if(StringUtils.isNumeric(args[0])) {
							Player p = (Player) sender;
							inv.open(vars, sv, p, Integer.parseInt(args[0]));
						}
						else sender.sendMessage(sv.getPrefix() + "§cArgument 1 nie jest liczba - uzycie: /ot <nr strony>");
					}
					else sender.sendMessage(sv.getPrefix() + "§cUzycie: /ot §6lub §c/ot <nr strony>");
				}
				else sender.sendMessage("§cTa komende moze uzywac jedynie gracz!");

			}
			if(cmd.getName().equalsIgnoreCase("otchlanAdmin") || cmd.getName().equalsIgnoreCase("otAdmin")) {
				if(args.length == 2) {
					if(args[0].equals("reload")) {
						if(args[1].equals("config")) {
							if(!sender.hasPermission("otchlan.admin") && !sender.hasPermission("otchlan.admin.config")) {
								sender.sendMessage(sv.getPrefix() + "§cNie masz permisji do tej komendy!");
								return false;
							}
							inv.closeAll(vars, sv);
							sv.reload();
							
							sender.sendMessage("§aConfig zostal przeladowany. Wszelkie bledy zostaly wyswietlone w konsoli (jezeli sie pojawily).");
						}
						else if(args[1].equals("timer")) {
							if(!sender.hasPermission("otchlan.admin") && !sender.hasPermission("otchlan.admin.timer")) {
								sender.sendMessage(sv.getPrefix() + "§cNie masz permisji do tej komendy!");
								return false;
							}
							inv.closeAll(vars, sv);
							t.reloadTimer(sv, inv, adds, vars);
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
	
	void help(CommandSender c) {
		c.sendMessage("§6/otAdmin reload config §7- przeladowuje config");
		c.sendMessage("§6/otAdmin reload timer §7- resetuje zegar");
	}
	



}
