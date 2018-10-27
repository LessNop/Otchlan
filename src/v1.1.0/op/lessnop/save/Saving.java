package op.lessnop.save;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import op.lessnop.Main;

public class Saving {
	protected Main main;
	private File file;
	public FileConfiguration config;
	
	public Saving(Main main, String fileName) {
		this.main = main;
		this.file = new File(main.getDataFolder(), fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		this.config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void save() {
		try {
			config.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
