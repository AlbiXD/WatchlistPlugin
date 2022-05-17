package main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import commands.DisguiseCommand;
import commands.SpyGlassCommand;
import commands.UnmaskCommand;
import data.ConfigEnums;
import data.ConfigManager;
import item.CustomItem;
import listener.playerInventory;
import listener.playerJoin;
import listener.playerKill;
import listener.playerPVPEvent;

public class DisguisePlugin extends JavaPlugin {
	public static DisguisePlugin plugin;
	public playerInventory inventory;
	public ConfigManager config;

	@Override
	public void onEnable() {
		config = new ConfigManager(this, "config.yml");
		CustomItem.createSpyGlass();

		for (ConfigEnums cfg : ConfigEnums.values()) {
			if (!config.getConfig().contains(cfg.defaults)) {
				config.getConfig().set(cfg.defaults, cfg.values);
			}
			config.saveConfig();
		}

		plugin = this;

		inventory = new playerInventory(this);

		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lDisguise Plugin Enabled"));

		// Events
		Bukkit.getPluginManager().registerEvents(inventory, this);
		Bukkit.getPluginManager().registerEvents(new playerJoin(this), this);
		Bukkit.getPluginManager().registerEvents(new playerKill(this), this);// playerPVPEvent
		Bukkit.getPluginManager().registerEvents(new playerPVPEvent(this), this);

		// Commands
		Bukkit.getPluginCommand("disguise").setExecutor(new DisguiseCommand());
		Bukkit.getPluginCommand("unmask").setExecutor(new UnmaskCommand(this));
		Bukkit.getPluginCommand("glass").setExecutor(new SpyGlassCommand());

	}

	@Override
	public void onDisable() {

		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lDisguise Plugin Disabled"));

	}

	public static DisguisePlugin getInstance() {
		return plugin;
	}

}
