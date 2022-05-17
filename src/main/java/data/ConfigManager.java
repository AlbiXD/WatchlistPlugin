package data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import main.DisguisePlugin;

public class ConfigManager {
	private DisguisePlugin plugin;
	private FileConfiguration dataConfig = null;
	private File configFile = null;
	private String configName;

	public ConfigManager(DisguisePlugin plugin, String configName) {
		this.plugin = plugin;
		this.configName = configName;

		// saves/initializes the config
		saveDefaultConfig();

	}

	// reloads the config
	public void reloadConfig() {
		if (this.configFile == null)// if config file does not exist create it
			this.configFile = new File(this.plugin.getDataFolder(), configName);

		this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

		InputStream defaultStream = this.plugin.getResource(configName);
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);

		}

	}

	public FileConfiguration getConfig() {
		if (this.dataConfig == null) {
			reloadConfig();

		}
		return this.dataConfig;

	}

	public void saveConfig() {
		if (this.dataConfig == null || this.configFile == null)
			return;
		try {
			this.getConfig().save(this.configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveDefaultConfig() {
		if (this.configFile == null) {
			this.configFile = new File(this.plugin.getDataFolder(), configName);

		}
		if (!this.configFile.exists()) {
			this.plugin.saveResource(configName, false);
		}
	}

}
