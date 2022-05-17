package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import item.CustomItem;

public class SpyGlassCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			
			
			
			p.getInventory().addItem(CustomItem.glass);
			
			
			
			
		}
		
		
		
		return true;
	}

}
