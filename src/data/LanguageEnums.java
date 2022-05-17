package data;

public enum LanguageEnums {
	
	INVALIDARGUMENT("invalidArguments", "&cInvalid arguments specify player and reason!"), 
	NOPERMMESSAGE("nopermMessage", "&cYou have insufficient permissions"),
	INCORRECTUSAGE("incorrectUsage", "&cIncorrect usage please type /watchlist help for more commands"),
	ONLYPLAYERMESSAGE("onlyPlayerMessage", "&cOnly players can use this command"),
	PLAYERNOTFOUND("playerNotFound", "&cPlayer &l&c%target% &cdoes not exist!"),
	PLAYEREXISTS("playerExists", "&cPlayer &l&c%target% &calready is in watchlist!"),
	PLAYERADDED("playerAdded", "&aPlayer &l&c%target%&c has been added to the watchlist!"),
	STAFFADDEDMESSAGE("staffAddedMessage", "&a&l%target% &ahas been added to the watchlisted by &a&l%staff%&a: %reason%"),
	PLAYERNOTINLIST("playerNotInList", "&cPlayer &l&c%target% &cis not watchlisted"),
	PLAYERREMOVED("playerRemoved", "&aPlayer &l&a%target% &asuccessfully removed from watchlist!"),
	WATCHLISTEMPTY("watchlistEmpty", "&cThe Watch List is currently empty"),
	INVALIDINTEGER("invalidInteger", "&c&l%integer% &cis an invalid integer!"), 
	CANNOTADDSELF("cannotADDSELF", "&cYou &c&lCANNOT &cadd yourself to the watchlist!"),
	PLAYERJOIN("playerJOIN", "&a&l%target% &ahas joined the server and is currently in the watchlist: &a&l%reason%");

	public final String defaults;
	public final String value;

	LanguageEnums(String d, String v) {
		this.defaults = d;
		this.value = v;
	}
	
	
	
	
	

}
