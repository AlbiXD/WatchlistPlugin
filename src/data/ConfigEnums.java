package data;

public enum ConfigEnums {

	CONFIG_VERSION("config_version", 0.1), DISABLESOUND("disablesound", false), MYSQL_HOST("SQL.host", "localhost"),
	MYSQL_PORT("SQL.port", "3306"), MYSQL_DATABASE("SQL.database", "watchlistplugin"),
	MYSQL_USERNAME("SQL.username", "root"), MYSQL_PASSWORD("SQL.password", ""),;

	public final String defaults;

	public final Object value;

	ConfigEnums(String defaults, Object value) {
		this.value = value;
		this.defaults = defaults;

	}
}
