package sql;

import java.sql.SQLException;

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

		if (plugin.SQL.isConnected() == false) {
			try {
				plugin.SQL.connect();
				Bukkit.getConsoleSender().sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&a&lCONSOLE: &a&lHello?, database are you still awake?"));
				Bukkit.getConsoleSender()
						.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lDATABASE: &a&lHi, I am still here!"));

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
