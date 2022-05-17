package listener;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;
import com.lkeehl.tagapi.TagAPI;

import data.ConfigManager;
import main.DisguisePlugin;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;
import tasks.PlayerUnGlow;

public class playerPVPEvent implements Listener {

	DisguisePlugin plugin;
	ConfigManager config;
	FileConfiguration fileConfig;
	SkinsRestorerAPI api = SkinsRestorerAPI.getApi();

	public playerPVPEvent(DisguisePlugin plugin) {
		this.plugin = plugin;
		this.config = plugin.config;
		this.fileConfig = config.getConfig();
	}

	@EventHandler
	public void playerPVP(EntityDamageByEntityEvent e) {
		// If attacked is masked then glow for x amount of seconds

		if (!e.getEntity().isGlowing()) {
			if (e.getDamager() instanceof Player) {
				if (e.getEntity() instanceof Player) {
					if (fileConfig.contains("users." + e.getEntity().getName())
							&& ((Player) e.getEntity()).getHealth() >= 10) {
						e.getEntity().setGlowing(true);
						PlayerUnGlow task = new PlayerUnGlow(plugin, (Player) e.getEntity());
						task.runTaskLater(plugin, 100);

						return;
					}

				}
			}
		}
		if (fileConfig.contains("users." + e.getEntity().getName()) && ((Player) e.getEntity()).getHealth() < 10) {
			api.removeSkin(e.getEntity().getName());

			fileConfig.set("users." + e.getEntity().getName(), null);
			config.saveConfig();
			try {
				api.applySkin(new PlayerWrapper((Player) e.getEntity()));
				TagAPI.removeTag(e.getEntity());

			} catch (SkinRequestException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
