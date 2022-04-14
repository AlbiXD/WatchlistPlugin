package data;

public enum LanguageEnums {
	
	INVALIDARGUMENT("invalidArguments", "&cInvalid arguments specify player and reason!"), 
	NOPERMMESSAGE("nopermMessage", "&cYou have insufficient permissions"),
	INCORRECTUSAGE("incorrectUsage", "&cIncorrect usage please type /watchlist help for more commands"),
	ONLYPLAYERMESSAGE("onlyPlayerMessage", "&cOnly players can use this command"),
	PLAYERNOTFOUND("playerNotFound", "&cPlayer %target% does not exist!"),
	PLAYEREXISTS("playerExists", "&cPlayer %target% already is in watchlist!"),
	PLAYERADDED("playerAdded", "&aPlayer %target% has been added to the watchlist!"),
	STAFFADDEDMESSAGE("staffAddedMessage", "&a&l%target% &ahas been added to the watchlisted by &a&l%staff%!"),
	PLAYERNOTINLIST("playerNotInList", "&cPlayer %target% is not watchlisted"),
	PLAYERREMOVED("playerRemoved", "&aPlayer %target% successfully removed from watchlist!"),
	WATCHLISTEMPTY("watchlistEmpty", "&cThe Watch List is currently empty"),
	INVALIDINTEGER("invalidInteger", "&c&l%integer% &cis an invalid integer!"),;
	
	public final String defaults;
	public final String value;

	LanguageEnums(String d, String v) {
		this.defaults = d;
		this.value = v;
	}
	
	
	
	
	

}
