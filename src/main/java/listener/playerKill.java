package listener;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.lkeehl.tagapi.TagAPI;

import data.ConfigEnums;
import data.ConfigManager;
import item.CustomItem;
import main.DisguisePlugin;
import net.md_5.bungee.api.ChatColor;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;

public class playerKill implements Listener {
	Random random = new Random();
	DisguisePlugin plugin;
	SkinsRestorerAPI api = SkinsRestorerAPI.getApi();
	ConfigManager config;
	FileConfiguration fileConfig;

	public playerKill(DisguisePlugin plugin) {
		this.plugin = plugin;
		this.config = plugin.config;
		this.fileConfig = config.getConfig();

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getEntity().setGlowing(false);

		// Unmask on Death
		if (fileConfig.contains("users." + e.getEntity().getName())) {
			try {
				TagAPI.removeTag(e.getEntity());
				api.removeSkin(e.getEntity().getName());
				api.applySkin(new PlayerWrapper(e.getEntity()));
				fileConfig.set("users." + e.getEntity().getName(), null);
				config.saveConfig();

			} catch (SkinRequestException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			e.getEntity().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have lost your mask!"));

		}

		// Random Skull Drop
		if (random
				.nextInt((int) Math.round(100 / fileConfig.getDouble(ConfigEnums.player_skull_chance.defaults))) == 1) {
			CustomItem.createPlayerHead(e.getEntity().getName());
			e.getDrops().add(CustomItem.playerHead);
		}

	}

}
