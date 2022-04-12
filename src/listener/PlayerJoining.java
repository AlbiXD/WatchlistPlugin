package listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import core.WatchlistPlugin;
import net.md_5.bungee.api.ChatColor;

public class PlayerJoining implements Listener {

	WatchlistPlugin plugin = WatchlistPlugin.getInstance();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		if (plugin.watchlist.exists(e.getPlayer().getUniqueId())) {
			String watchlistedPlayer = e.getPlayer().getName();

			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.hasPermission("watchlist")) {
					p.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l" + watchlistedPlayer
							+ " &ahas joined the server and is currently in the watchlist!"));
				}
			}

		}

	}

}
