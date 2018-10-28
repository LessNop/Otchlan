package op.lessnop.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import op.lessnop.MainAndStorage;

public class playerCommand implements CommandExecutor {

	MainAndStorage sc;
	
	public playerCommand(MainAndStorage sc) {
		this.sc = sc;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if(cmd.getName().equalsIgnoreCase("Otchlan")) {
			if(sender instanceof Player) {
				if(args.length == 0) {
					if(!sender.hasPermission("otchlan.gracz.otworz") || !sender.hasPermission("otchlan.gracz.strona")) {
						sender.sendMessage(sc.sv().getPrefix() + "§cNie masz permisji do tej komendy!");
						return false;
					}
					Player p = (Player) sender;
					sc.inv().open(sc.vars(), sc.sv(), p, 1);
				}
				else if(args.length == 1) {
					if(!sender.hasPermission("otchlan.gracz.strona")) {
						sender.sendMessage(sc.sv().getPrefix() + "§cNie masz permisji do tej komendy!");
						return false;
					}
					if(StringUtils.isNumeric(args[0])) {
						Player p = (Player) sender;
						sc.inv().open(sc.vars(), sc.sv(), p, Integer.parseInt(args[0]));
					}
					else sender.sendMessage(sc.sv().getPrefix() + "§cArgument 1 nie jest liczba - uzycie: /ot <nr strony>");
				}
				else sender.sendMessage(sc.sv().getPrefix() + "§cUzycie: /ot §6lub §c/ot <nr strony>");
			}
			else sender.sendMessage("§cTa komende moze uzywac jedynie gracz!");

		}
		return false;
	}

}
