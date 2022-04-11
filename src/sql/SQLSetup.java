package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLSetup {
	private String host = "localhost";
	private String port = "3306";
	private String database = "watchlistplugin";
	private String username = "root";
	private String password = "";

	private Connection connection;

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
