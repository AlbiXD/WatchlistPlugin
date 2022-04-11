package commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import core.WatchlistPlugin;
import listener.PlayerJoining;

public class WatchlistCommand implements CommandExecutor, TabCompleter {
	public static WatchlistPlugin plugin = WatchlistPlugin.getInstance();
	private OfflinePlayer target;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender.hasPermission("watchlist")) {
			if (args.length > 0) {
				/*
				 * Adds player to the watchlist
				 * 
				 * @commands: /db add playerName reason
				 */
				if (args[0].toLowerCase().equals("add")) {
					if (sender instanceof Player) {
						// If arguments is 3 or greater than
						if (args.length >= 3) {
							// Checks if the player exists in the server
							if (Bukkit.getOfflinePlayer(args[1]) != null) {
								target = Bukkit.getOfflinePlayer(args[1]);
								if (!plugin.watchlist.exists(target.getUniqueId())) {
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
											"&aPlayer " + args[1] + " has been added to the watchlist!"));
									String reason = "";

									for (int i = 2; i < args.length; i++) {
										reason += args[i] + " ";
									}

									plugin.watchlist.addWatchlist(target, reason, (Player) sender);

									for (int i = 0; i < PlayerJoining.staffPlayers.size(); i++) {
										Player staff = PlayerJoining.staffPlayers.get(i);
										if (!staff.getName().equals(sender.getName())) {
											staff.sendMessage(ChatColor.translateAlternateColorCodes('&',
													"&a&l" + target.getName() + " &ahas been added to the watchlisted by "
															+ sender.getName() + "!"));
										}
									}

								} else
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
											"&cPlayer " + args[1] + " already is in watchlist!"));
							} else
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
										"&cPlayer " + args[1] + " does not exist!"));

						} else
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
									"&cInvalid arguments specify player and reason!"));
					} else
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', "&cOnly players can use this command"));
				}

				/*
				 * Removes player from the watchlist
				 * 
				 * @commands: /db remove playerName
				 */
				else if (args[0].toLowerCase().equals("remove")) {
					if (args.length == 2) {
						if (Bukkit.getOfflinePlayer(args[1]) != null) {
							target = Bukkit.getOfflinePlayer(args[1]);
							if (!plugin.watchlist.exists(target.getUniqueId())) {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
										"&cPlayer " + args[1] + " is not watchlisted"));
							} else {
								plugin.watchlist.removeWatchlist(args[1]);
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
										"&aPlayer " + args[1] + " successfully removed from watchlist!"));
							}
						} else
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
									"&cPlayer " + args[1] + " does not exist"));

					} else
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', "&cInvalid arguments specify a player!"));
				}
				/*
				 * Shows the sender a variety of commands
				 * 
				 * @commands: /db help
				 */
				else if (args[0].toLowerCase().equals("help")) {

				} else if (args[0].toLowerCase().equals("list")) {
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

					if (totalPages > 0) {

						if (args.length == 2) {

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
											"&7-----&c &lPAGE &c" + "" + pageNumber + "/" + totalPages + " &7-----"));
									for (int i = startingIndex; i < value; i++) {
										sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
												plugin.watchlist.banList().get(i)));
									}
								}

							} catch (NumberFormatException e) {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
										"&c&l" + args[1] + "&c is an invalid integer!"));

							}

						} else {
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
									"&7-----&c &lPAGE &c" + "1" + "/" + totalPages + " &7-----"));
							for (int i = 0; i < listSize; i++) {
								sender.sendMessage(
										ChatColor.translateAlternateColorCodes('&', plugin.watchlist.banList().get(i)));
							}
						}
					} else {
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', "&cThe Watch List is currently empty"));
					}
				}

				else
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&cIncorrect usage please type /watchlist help for more commands"));

			} else
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&cIncorrect usage please type /watchlist help for more commands"));
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have insufficient permissions"));
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

}
