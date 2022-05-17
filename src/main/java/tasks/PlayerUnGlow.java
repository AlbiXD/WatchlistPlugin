package tasks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import data.ConfigManager;
import main.DisguisePlugin;

public class PlayerUnGlow extends BukkitRunnable{

	Player player;
	DisguisePlugin plugin;
	ConfigManager config;
	FileConfiguration fileConfig;
	
	
	public PlayerUnGlow(DisguisePlugin plugin, Player player) {
		this.player = player;
		this.plugin = plugin;
		this.config = plugin.config;
		this.fileConfig = config.getConfig();
	}
	
	@Override
	public void run() {
		
		player.setGlowing(false);		
		
		
		
	}

}
