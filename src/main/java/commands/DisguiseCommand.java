package commands;

import org.bukkit.command.Command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import xyz.scyllasrock.ScyUtility.objects.BaseCommand;
import xyz.scyllasrock.ScyUtility.objects.SubCommand;

public class DisguiseCommand extends BaseCommand implements CommandExecutor {
	public DisguiseCommand() {

		super.addSubCommand(new PlayerHead());

	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		/*
		 * Checks if the command arguments is less than 0
		 */
		if (args.length == 0) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lInvalid Usage!"));
			return true;
		}

		/*
		 * Checks if the command arguments is less than 0
		 */

		if (args.length > 0) {
			for (SubCommand sub : super.getSubCommands()) {
				if (!sender.hasPermission(sub.getPermission())) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo Permission"));
					return true;
				}

				if (sub.getFirstArgument().equalsIgnoreCase(args[0]))
					return sub.execute(sender, args);
			}

			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCommand does not exist!"));
		}

		return true;
	}

}
