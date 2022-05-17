package listener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;
import com.lkeehl.tagapi.api.Tag;

import data.ConfigManager;
import main.DisguisePlugin;
import net.md_5.bungee.api.ChatColor;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;

public class playerInventory implements Listener {

	SkinsRestorerAPI api = SkinsRestorerAPI.getApi();
	ItemStack skull = null;
	DisguisePlugin plugin;
	ConfigManager config;
	FileConfiguration fileConfig;
	public Tag tag;

	List<PlayerInfoData> pd = new ArrayList<PlayerInfoData>();

	public playerInventory(DisguisePlugin plugin) {
		this.plugin = plugin;
		config = plugin.config;
		fileConfig = config.getConfig();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerInventoryEvent(InventoryClickEvent e) {

		ItemStack[] armorContents = e.getWhoClicked().getInventory().getArmorContents();

		if (armorContents[3] == null && e.getCursor().getType().equals(Material.PLAYER_HEAD)
				&& e.getSlotType().equals(SlotType.ARMOR)) {
			e.getWhoClicked().getInventory().setHelmet(new ItemStack(Material.AIR));
			skull = e.getCursor();

			e.setCursor(null);
			


			try {

				final SkullMeta itemMeta = (SkullMeta) skull.getItemMeta();
				api.setSkin(e.getWhoClicked().getName(), itemMeta.getOwner());
				api.applySkin(new PlayerWrapper(e.getWhoClicked()));
				e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&aYou are currently disguised as &a&l" + itemMeta.getOwner()));

				tag = Tag.create(e.getWhoClicked()); // Create a new Tag
				tag.addTagLine(10).setGetName(pl -> itemMeta.getOwner());
				tag.giveTag();

				fileConfig.set("users." + e.getWhoClicked().getName(), "d."+itemMeta.getOwner());

				config.saveConfig();

			} catch (SkinRequestException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public ItemStack getSkull() {
		return skull;
	}

	public void nullSkull() {
		skull = null;
	}
	
}
