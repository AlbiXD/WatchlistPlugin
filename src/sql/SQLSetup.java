package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.bukkit.configuration.file.FileConfiguration;
import core.WatchlistPlugin;

public class SQLSetup {
	FileConfiguration cfg = WatchlistPlugin.plugin.data.getConfig();

	private String host = cfg.getString("SQL.host");
	private String port = cfg.getString("SQL.port");
	private String database = cfg.getString("SQL.database");
	private String username = cfg.getString("SQL.username");
	private String password = cfg.getString("SQL.password");

	private Connection connection;

	public SQLSetup() {
	}

	public boolean isConnected() {
		return (connection == null) ? false : true;
	}

	public void connect() throws ClassNotFoundException, SQLException {
		if (!isConnected()) {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database
					+ "?connectTimeout=0&socketTimeout=0&autoReconnect=true&useSSL=false", username, password);
		}
	}

	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public Connection getConnection() {
		return connection;
	}

}
