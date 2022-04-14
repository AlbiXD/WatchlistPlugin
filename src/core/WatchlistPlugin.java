package core;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import commands.WatchlistCommand;
import data.ConfigEnums;
import data.ConfigManager;
import data.LanguageEnums;
import listener.PlayerJoining;
import sql.SQLSetup;
import sql.WatchlistSQL;

public class WatchlistPlugin extends JavaPlugin {

	public static WatchlistPlugin plugin;
	public SQLSetup SQL;
	public WatchlistSQL watchlist;

	private ConsoleCommandSender console = Bukkit.getConsoleSender();
	public ConfigManager data;
	public ConfigManager language;

	@Override
	public void onEnable() {
		// Initiating the plugin instance to this class
		plugin = this;

		data = new ConfigManager(this, "config.yml");
		language = new ConfigManager(this, "lang.yml");
		
		for (ConfigEnums cfg : ConfigEnums.values()) {
			if (!data.getConfig().contains(cfg.defaults)) {
				data.getConfig().set(cfg.defaults, cfg.value);
			}
			data.saveConfig();
		}
		
		
		for (LanguageEnums lan : LanguageEnums.values()) {
			if (!language.getConfig().contains(lan.defaults)) {
				System.out.println(lan);
				language.getConfig().set(lan.defaults, lan.value);
			}
			language.saveConfig();
		}

		// Enable Message
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lPlugin is enabled!"));

		// initiates the SQL setup and the watchList Table SQL set up
		SQL = new SQLSetup();
		watchlist = new WatchlistSQL(this);

		// setting the command executor
		getCommand("watchlist").setExecutor(new WatchlistCommand());

		// setting up eventlistener for on join
		Bukkit.getPluginManager().registerEvents(new PlayerJoining(), this);

		//
		// attempting to connect to the SQL database and creating the table
		try {
			SQL.connect();
			watchlist.createTable();

		} catch (ClassNotFoundException | SQLException e) {
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&4&lERROR: &4Unable to connect to database"));
		}

		// Checks if we connected to the SQL datasbase
		if (SQL.isConnected()) {
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lDatabase is connected!"));
		}

	}

	@Override
	public void onDisable() {
		// disconnects from SQL database
		SQL.disconnect();
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lPlugin is disabled!"));

	}

	public static WatchlistPlugin getInstance() {
		return plugin;
	}
}
