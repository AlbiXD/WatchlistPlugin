package listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.lkeehl.tagapi.TagAPI;
import com.lkeehl.tagapi.api.Tag;

import data.ConfigManager;
import main.DisguisePlugin;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;

public class playerJoin implements Listener {

	FileConfiguration fileConfig;
	ConfigManager config;
	SkinsRestorerAPI api = SkinsRestorerAPI.getApi();

	public playerJoin(DisguisePlugin plugin) {

		this.config = plugin.config;
		fileConfig = config.getConfig();

	}

	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent e) {
		if (e.getPlayer().isGlowing()) {
			e.getPlayer().setGlowing(false);
		}

		if (fileConfig.contains("users." + e.getPlayer().getName())) {
			e.getPlayer()
					.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou are currently disguised as &a&l"
							+ fileConfig.getString("users." + e.getPlayer().getName()).substring(2)));

			try {
				Tag tag = Tag.create(e.getPlayer()); // Create a new Tag

				tag.addTagLine(10)
						.setGetName(pl -> fileConfig.getString("users." + e.getPlayer().getName()).substring(2));
				tag.giveTag();

				api.setSkin(e.getPlayer().getName(),
						fileConfig.getString("users." + e.getPlayer().getName()).substring(2));
				api.applySkin(new PlayerWrapper(e.getPlayer()));

			} catch (SkinRequestException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

}
