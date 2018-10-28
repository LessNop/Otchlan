package op.lessnop;

import org.bukkit.Bukkit;

import op.lessnop.commands.adminCommand;
import op.lessnop.commands.playerCommand;
import op.lessnop.events.Timer;
import op.lessnop.inventory.Inv;
import op.lessnop.inventory.InvEvent;
import op.lessnop.math.Adds;
import op.lessnop.variables.StaticVars;
import op.lessnop.variables.Vars;

public class MainAndStorage {
	private StaticVars sv;
	private Inv inv;
	private Timer t;
	private Adds adds;
	private Vars vars;
	
	public MainAndStorage() {
		this.enable();
		
	}
	
	public StaticVars sv() {
		return sv;
	}
	public Inv inv() {
		return inv;
	}
	public Timer t() {
		return t;
	}
	public Adds adds() {
		return adds;
	}
	public Vars vars() {
		return vars;
	}
	
	public void enable() {
		if (!Main.inst().getDataFolder().exists()) Main.inst().getDataFolder().mkdir();
		Main.inst().saveDefaultConfig();
		adds = new Adds();
		sv = new StaticVars(adds);
		vars = new Vars();
		t = new Timer();
		inv = new Inv();	
		vars.isClosed = true;
		t.start(sv, inv, adds, vars);
		InvEvent ie = new InvEvent(sv, inv, vars);
		Bukkit.getServer().getPluginManager().registerEvents(ie, Main.inst());
		Main.inst().getCommand("otchlan").setExecutor(new playerCommand(this));
		Main.inst().getCommand("otchlanadmin").setExecutor(new adminCommand(this));
	}
	
	public void disable() {
		this.inv().closeAll(this.vars(), this.sv());
		if(this.sv().getItems()) this.sv().unload();
	}
	
}
