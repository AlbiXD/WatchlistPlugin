package sql;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import core.WatchlistPlugin;
import net.md_5.bungee.api.ChatColor;

public class Task extends BukkitRunnable {

	private WatchlistPlugin plugin;

	public Task(WatchlistPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		plugin.watchlist.getRandom();
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPinging to database"));
	}

}
