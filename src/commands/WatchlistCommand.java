package commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import core.WatchlistPlugin;
import data.LanguageEnums;
import listener.PlayerJoining;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class WatchlistCommand implements CommandExecutor, TabCompleter {
	public static WatchlistPlugin plugin = WatchlistPlugin.getInstance();
	private OfflinePlayer target;
	FileConfiguration config = plugin.language.getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// Checks if player has permission
		if (!sender.hasPermission("watchlist")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("nopermMessage")));
			return true;

		}

		// Checks if the arguments are less than 0 if not then we continue to other
		// subcommands
		if (args.length == 0) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					config.getString(LanguageEnums.INCORRECTUSAGE.defaults)));

			return true;
		}

		/*
		 * Adds player to the watchlist
		 * 
		 * @commands: /db add playerName reason
		 */
		if (args[0].toLowerCase().equals("add")) {
			if (!(sender instanceof Player)) {
				// If arguments is 3 or greater than
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						config.getString(LanguageEnums.ONLYPLAYERMESSAGE.defaults)));
				return true;

			}
			// Checks if the arugment is less than 3
			if (args.length < 3) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						config.getString(LanguageEnums.INVALIDARGUMENT.defaults)));
				return true;
			}

			// Checks if the player exists in the server
			if (Bukkit.getOfflinePlayer(args[1]) == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						config.getString(LanguageEnums.PLAYERNOTFOUND.defaults).replace("%target%", args[1])));

				return true;
			}
			target = Bukkit.getOfflinePlayer(args[1]);

			// Checks if player is in database
			if (plugin.watchlist.exists(target.getUniqueId())) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						config.getString(LanguageEnums.PLAYEREXISTS.defaults).replace("%target%", args[1])));
				return true;

			}

			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					config.getString(LanguageEnums.PLAYERADDED.defaults).replace("%target%", args[1])));

			// Converts reason into a single string
			String reason = "";
			for (int i = 2; i < args.length; i++) {
				reason += args[i] + " ";
			}

			// Adds player to database
			plugin.watchlist.addWatchlist(target, reason, (Player) sender);

			// Announces to other staff that player has been added to database
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.hasPermission("watchlist") && !p.equals((Player) sender)) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							config.getString(LanguageEnums.STAFFADDEDMESSAGE.defaults).replace("%target%", args[1])
									.replace("%staff%", sender.getName())));
					p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.0f);
				}
			}
			return true;

		} // ending of the add subcommand

		/*
		 * Removes player from the watchlist
		 * 
		 * @commands: /db remove playerName
		 */
		else if (args[0].toLowerCase().equals("remove")) {
			if (args.length < 2) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						config.getString(config.getString(LanguageEnums.INVALIDARGUMENT.defaults))));
				return true;
			}
			// Checks if player exists or not
			if (Bukkit.getOfflinePlayer(args[1]) == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						config.getString(LanguageEnums.PLAYERNOTFOUND.defaults).replace("%target%", args[1])));
				return true;
			}

			// if player exists then will be set as target
			target = Bukkit.getOfflinePlayer(args[1]);

			// checks if UUID is within the database
			if (!plugin.watchlist.exists(target.getUniqueId())) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						config.getString(LanguageEnums.PLAYERNOTINLIST.defaults).replace("%target%", args[1])));
				return true;
			}

			// Successfully removed the player
			plugin.watchlist.removeWatchlist(args[1]);
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					config.getString(LanguageEnums.PLAYERREMOVED.defaults).replace("%target%", args[1])));
			return true;

		} // ending of the remove subcommand
		/*
		 * Shows the sender a variety of commands
		 * 
		 * @commands: /db help
		 */
		else if (args[0].equalsIgnoreCase("help")) {
			helpCommands(sender);
			return true;

		} // ending of the help subcommand
		else if (args[0].toLowerCase().equals("list")) {
			// page System
			int listSize = plugin.watchlist.banList().size();
			int totalPages = 0;
			int itemsPerpage = 5;
			int pageNumber = 0;

			if (listSize % 5 > 0) {
				totalPages += (listSize / itemsPerpage) + 1;
			} else {
				totalPages += listSize / itemsPerpage;
			}

			// Checks if list is empty
			if (totalPages == 0) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						config.getString(LanguageEnums.WATCHLISTEMPTY.defaults)));
				return true;
			}

			// Checks if list is set to one page
			if (args.length == 1) {
				if (listSize <= 5) {

					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&7-----&a &lPAGE &a" + "1" + "/" + totalPages + " &7-----"));
					for (int i = 0; i < listSize; i++) {
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', plugin.watchlist.banList().get(i)));

					}
				} else {

					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&7-----&a &lPAGE &a" + "1" + "/" + totalPages + " &7-----"));
					for (int i = 0; i < itemsPerpage; i++) {
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', plugin.watchlist.banList().get(i)));

					}
				}
				return true;

			}

			try {
				pageNumber = Integer.valueOf(args[1]);
				System.out.println(pageNumber);

				int startingIndex = itemsPerpage * pageNumber - itemsPerpage;
				int value = 0;

				if (pageNumber == totalPages) {
					value = listSize;
				} else {
					value = pageNumber * itemsPerpage;
				}

				if (pageNumber > totalPages || pageNumber < 1) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&c&l" + args[1] + "&c is an invalid integer!"));
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&7-----&a &lPAGE &a" + "" + pageNumber + "/" + totalPages + " &7-----"));
					for (int i = startingIndex; i < value; i++) {
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', plugin.watchlist.banList().get(i)));
					}
				}
				return true;

			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						config.getString(LanguageEnums.INVALIDINTEGER.defaults).replace("%integer%", args[1])));
				return true;

			}

		} // ending of the list subcommand
		else if (args[0].toLowerCase().equals("reload")) {
			plugin.language.reloadConfig();
			config = plugin.language.getConfig();
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfig has been reloaded"));
			return true;

		}

		// Incase of wrong arguments
		sender.sendMessage(
				ChatColor.translateAlternateColorCodes('&', config.getString(LanguageEnums.INCORRECTUSAGE.defaults)));

		return true;

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		// Arguments for /watchlist
		if (args.length == 1) {
			List<String> arguments = new ArrayList<String>();
			arguments.add("add");
			arguments.add("remove");
			arguments.add("list");
			arguments.add("help");

			return arguments;
		}

		// Arguments set to empty array for reasons /watchlist add player reasons
		if (args[0].equals("add") && args.length >= 3) {

			return new ArrayList<String>();
		}
		// Arguments of players in the database to remove /watchlist remove
		if (args[0].equals("remove")) {

			return plugin.watchlist.playerList();
		}

		return null;
	}

	private void helpCommands(CommandSender sender) {

		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aList Of All WatchList Commands:"));

		BaseComponent[] helpCMD = new ComponentBuilder(
				ChatColor.translateAlternateColorCodes('&', "  &a-&a&l/watchlist &ahelp"))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new Text(ChatColor.translateAlternateColorCodes('&', "&a&lCLICK TO COPY"))))
						.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/watchlist help"))
						.append(ChatColor.translateAlternateColorCodes('&', "&7 help Lists all Watch List commands!"))
						.create();

		BaseComponent[] addCMD = new ComponentBuilder(
				ChatColor.translateAlternateColorCodes('&', "  &a-&a&l/watchlist&a add <player> <reason>"))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new Text(ChatColor.translateAlternateColorCodes('&', "&a&lCLICK TO COPY"))))
						.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/watchlist add"))
						.append(ChatColor.translateAlternateColorCodes('&', "&7Adds a player to the Watch List!"))
						.create();

		BaseComponent[] removeCMD = new ComponentBuilder(
				ChatColor.translateAlternateColorCodes('&', "  &a-&a&a&l/watchlist &aremove <player>"))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new Text(ChatColor.translateAlternateColorCodes('&', "&a&lCLICK TO COPY"))))
						.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/watchlist remove"))
						.append(ChatColor.translateAlternateColorCodes('&', "&7Removes a player from the Watch List!"))
						.create();

		BaseComponent[] listCMD = new ComponentBuilder(
				ChatColor.translateAlternateColorCodes('&', "  &a-&a&l/watchlist &alist <pageNumber>"))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new Text(ChatColor.translateAlternateColorCodes('&', "&a&lCLICK TO COPY"))))
						.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/watchlist list"))
						.append(ChatColor.translateAlternateColorCodes('&', "&7A list of the players who are in the WatchList!"))
						.create();

		sender.spigot().sendMessage(helpCMD);
		sender.spigot().sendMessage(addCMD);
		sender.spigot().sendMessage(removeCMD);
		sender.spigot().sendMessage(listCMD);

	}

}
