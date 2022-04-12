package sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import core.WatchlistPlugin;

public class WatchlistSQL {

	private WatchlistPlugin plugin;

	public WatchlistSQL(WatchlistPlugin plugin) {
		this.plugin = plugin;
	}

	public void createTable() {
		PreparedStatement ps;

		try {
			ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS watchlist "
					+ "(playerNAME VARCHAR(16),playerUUID VARCHAR(36),REASON VARCHAR(300),staffNAME VARCHAR(16),staffUUID VARCHAR(36), PRIMARY KEY (playerNAME))");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean exists(UUID uuid) {

		try {
			PreparedStatement ps = plugin.SQL.getConnection()
					.prepareStatement("SELECT * FROM watchlist WHERE playerUUID=?");
			ps.setString(1, uuid.toString());

			ResultSet result = ps.executeQuery();

			if (result.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public void addWatchlist(OfflinePlayer player, String reason, Player staff) {
		UUID playerUUID = player.getUniqueId();
		UUID staffUUID = staff.getUniqueId();

		try {
			if (!exists(playerUUID)) {
				PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO watchlist"
						+ " (playerName,playerUUID,REASON,staffName,staffUUID) VALUES (?,?,?,?,?)");
				ps.setString(1, player.getName());
				ps.setString(2, playerUUID.toString());
				ps.setString(3, reason);
				ps.setString(4, staff.getName());
				ps.setString(5, staffUUID.toString());
				ps.executeUpdate();

				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void removeWatchlist(String player) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection()
					.prepareStatement("DELETE FROM watchlist WHERE playerName = '" + player + "';");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<String> banList() {
		List<String> playerList = new ArrayList<String>();
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM watchlist");
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				String banMSG = "";
				String staff = result.getString("staffNAME");
				String player = result.getString("playerNAME");
				String reason = result.getString("REASON");
				;

				banMSG = "&a&l" + player + " &awas added by &a&l" + staff + ": &a" + reason;
				playerList.add(banMSG);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return playerList;
	}

	public List<String> playerList() {
		List<String> playerList = new ArrayList<String>();
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM watchlist");
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				String player = result.getString("playerNAME");
				playerList.add(player);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return playerList;
	}

}
