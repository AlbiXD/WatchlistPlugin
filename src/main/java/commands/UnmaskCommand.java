package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lkeehl.tagapi.TagAPI;

import data.ConfigManager;
import main.DisguisePlugin;
import net.md_5.bungee.api.ChatColor;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;

public class UnmaskCommand implements CommandExecutor {

	DisguisePlugin plugin;
	SkinsRestorerAPI api = SkinsRestorerAPI.getApi();
	ConfigManager config;
	FileConfiguration fileConfig;

	public UnmaskCommand(DisguisePlugin plugin) {
		this.plugin = plugin;
		this.config = plugin.config;
		this.fileConfig = config.getConfig();

	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You cannot use this command");
			return true;

		}

		Player player = (Player) sender;
		ItemStack skull = plugin.inventory.getSkull();

		// Check if they have a mask

		if (!fileConfig.contains("users." + player.getName())) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not disguised"));
			return true;
		}

		// Remove the mask
		try {
			api.removeSkin(player.getName());
			api.applySkin(new PlayerWrapper(player));
			fileConfig.set("users." + player.getName(), null);
			config.saveConfig();
			TagAPI.removeTag(player);

		} catch (SkinRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have unmasked yourself!"));

		return true;
	}

}
