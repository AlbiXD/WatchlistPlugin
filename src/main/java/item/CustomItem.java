package item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;


public class CustomItem {
	public static ItemStack glass;
	public static ItemStack playerHead;


	public static void createPlayerHead(String player) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
		itemMeta.setOwner(player);
		itemMeta.setDisplayName(player + "'s Skull!");
		item.setItemMeta(itemMeta);
		playerHead = item;

	}
	public static void createSpyGlass() {
		ItemStack item = new ItemStack(Material.SPYGLASS);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cSPYGLASS"));
		
		glass = item;

	}
}
