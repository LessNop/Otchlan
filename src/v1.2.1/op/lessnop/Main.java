package op.lessnop;

import org.bukkit.plugin.java.JavaPlugin;

import op.lessnop.save.MainData;





public class Main extends JavaPlugin {
	public Main(){}
	
	public static MainData zapis;
	private static Main instance;
	private MainAndStorage sc;
	
	public void onEnable() {
		instance = this;
		sc = new MainAndStorage();	 
	}
	
	public void onDisable() {
		sc.disable();
	}
	
	public static Main inst() {
		  return instance;
	}
}
