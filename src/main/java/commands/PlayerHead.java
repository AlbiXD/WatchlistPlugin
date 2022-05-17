package commands;

import java.util.ArrayList;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import item.CustomItem;
import net.md_5.bungee.api.ChatColor;
import xyz.scyllasrock.ScyUtility.objects.SubCommand;

public class PlayerHead extends SubCommand {

	public PlayerHead() {
		// super(baseCommand, firstArgument, aliases, permission, usage, description);

		super("disguise", "head", new ArrayList<String>(), "disguise.head", "", "");

	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {

		// Checks if it is a player or console
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer's can only use this command"));
			return true;
		}

		Player player = (Player) sender;

//		Player target = Bukkit.getOfflinePlayer(args[1]).getPlayer();

		player.sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&aYou Have Recieved &a&l" + args[1] + "'s &aHead"));

		CustomItem.createPlayerHead(args[1]);

//		CustomItem.init(target);

		player.getInventory().addItem(CustomItem.playerHead);

		return true;
	}

	@Override
	public List<String> getTabCompletions(Player player, String[] args) {
		return null;
	}

}
