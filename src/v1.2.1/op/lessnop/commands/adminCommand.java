package op.lessnop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import op.lessnop.MainAndStorage;

public class adminCommand implements CommandExecutor {

	MainAndStorage sc;
	
	public adminCommand(MainAndStorage sc) {
		this.sc = sc;
	}
	
	
	@Override 
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if(cmd.getName().equalsIgnoreCase("otchlanAdmin") || cmd.getName().equalsIgnoreCase("otAdmin")) {
			if(args.length == 2) {
				if(args[0].equals("reload")) {
					if(args[1].equals("config")) {
						if(!sender.hasPermission("otchlan.admin") && !sender.hasPermission("otchlan.admin.config")) {
							sender.sendMessage(sc.sv().getPrefix() + "§cNie masz permisji do tej komendy!");
							return false;
						}
						sc.inv().closeAll(sc.vars(), sc.sv());
						sc.sv().reload();
						
						sender.sendMessage("§aConfig zostal przeladowany. Wszelkie bledy zostaly wyswietlone w konsoli (jezeli sie pojawily).");
					}
					else if(args[1].equals("timer")) {
						if(!sender.hasPermission("otchlan.admin") && !sender.hasPermission("otchlan.admin.timer")) {
							sender.sendMessage(sc.sv().getPrefix() + "§cNie masz permisji do tej komendy!");
							return false;
						}
						sc.inv().closeAll(sc.vars(), sc.sv());
						sc.t().reloadTimer(sc.sv(), sc.inv(), sc.adds(), sc.vars());
						sender.sendMessage("§aZegar zostal zresetowany.");
					}
					else this.help(sender);
				}
				else this.help(sender);
			}
			else this.help(sender);
		}
		return false;
	}
	
	private void help(CommandSender c) {
		c.sendMessage("§6/otAdmin reload config §7- przeladowuje config");
		c.sendMessage("§6/otAdmin reload timer §7- resetuje zegar");
	}

}
