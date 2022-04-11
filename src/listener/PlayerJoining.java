package listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import core.WatchlistPlugin;
import net.md_5.bungee.api.ChatColor;

public class PlayerJoining implements Listener {

	public static List<Player> staffPlayers = new ArrayList<Player>();

	WatchlistPlugin plugin = WatchlistPlugin.getInstance();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("watchlist")) {
			staffPlayers.add(e.getPlayer());
		}

		if (plugin.watchlist.exists(e.getPlayer().getUniqueId())) {
			String watchlistedPlayer = e.getPlayer().getName();

			for (int i = 0; i < staffPlayers.size(); i++) {
				Player staff = staffPlayers.get(i);
				
				staff.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&a&l" + watchlistedPlayer + " &ahas joined the server and is currently in the watchlist!"));

			}
			
		}

	}

}
