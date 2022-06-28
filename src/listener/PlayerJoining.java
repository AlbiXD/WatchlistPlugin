package listener;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import core.WatchlistPlugin;
import data.ConfigEnums;
import data.LanguageEnums;
import net.md_5.bungee.api.ChatColor;

public class PlayerJoining implements Listener {

	WatchlistPlugin plugin = WatchlistPlugin.getInstance();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		plugin.watchlist.playerChangedName(e.getPlayer());

		if (plugin.watchlist.exists(e.getPlayer().getUniqueId())) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.hasPermission("watchlist")) {
					p.getPlayer()
							.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageEnums.PLAYERJOIN.value)
									.replace("%target%", e.getPlayer().getName())
									.replace("%reason%", plugin.watchlist.getReason(e.getPlayer())));

					if ((boolean) ConfigEnums.DISABLESOUND.value == false) {
						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.0f);
					}
				}
			}

		}

	}

}
