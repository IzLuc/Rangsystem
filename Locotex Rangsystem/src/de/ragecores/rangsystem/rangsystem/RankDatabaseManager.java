package de.ragecores.rangsystem.rangsystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.ragecores.rangsystem.main.Main;
import de.ragecores.rangsystem.rangsystem.Ranks.Rank;

public class RankDatabaseManager {

    public static String RANKS_DATABASENAME = "Ranks";
    public static String PLAYERRANKS_DATABASENAME = "PlayerRanks";

    public static void createTable() {

	Main.getSQLManager().executeUpdate("CREATE TABLE IF NOT EXISTS " + RANKS_DATABASENAME
		+ " (rank VARCHAR(32) NOT NULL, chatrank VARCHAR(64) NOT NULL,  rankcolor VARCHAR(10) NOT NULL, chatcolor VARCHAR(10) NOT NULL, UNIQUE KEY(rank))");
	Main.getSQLManager().executeUpdate("CREATE TABLE IF NOT EXISTS " + PLAYERRANKS_DATABASENAME
		+ " (player VARCHAR(16), rank VARCHAR(32), expiration_date TEXT, UNIQUE KEY(player))");

    }

    public static void updateRanks() {
	for (Rank rank : Rank.values()) {
	    Ranks.rankcolor.put(rank, getRankInformation(rank).getRankColor().toString());
	    Ranks.chatrank.put(rank, getRankInformation(rank).getChatrank().toString());
	    Ranks.chatcolor.put(rank, getRankInformation(rank).getChatcolor().toString());

	}
    }

    public static boolean existsPlayer(String playername) {
	try {

	    PreparedStatement st = Main.getSQLManager().getConnection()
		    .prepareStatement("SELECT * FROM `" + PLAYERRANKS_DATABASENAME + "` WHERE `player` = ?");
	    st.setString(1, playername);
	    ResultSet rs = Main.getSQLManager().executeQuery(st);
	    boolean b = rs.next();
	    Main.getSQLManager().close(st, rs);
	    return b;
	} catch (Exception localException) {
	}
	return false;
    }

    public static RankInformation getRankInformation(Rank rank) {

	try {
	    PreparedStatement st = Main.getSQLManager().getConnection()
		    .prepareStatement("SELECT * FROM " + RANKS_DATABASENAME + " WHERE `rank` = ?");
	    st.setString(1, rank.toString().toUpperCase());
	    ResultSet rs = Main.getSQLManager().executeQuery(st);
	    if (!rs.next()) {
		return null;
	    }
	    String chatrank = rs.getString("chatrank");
	    String rankcolor = rs.getString("rankcolor");
	    String chatcolor = rs.getString("chatcolor");

	    return new RankInformation(chatrank, rankcolor, chatcolor);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;

    }

 

    public static Rank getRankFromDatabase(String playername) {

	try {
	    PreparedStatement st = Main.getSQLManager().getConnection()
		    .prepareStatement("SELECT * FROM " + PLAYERRANKS_DATABASENAME + " WHERE `player` = ?");
	    st.setString(1, "" + playername);
	    ResultSet rs = Main.getSQLManager().executeQuery(st);
	    if (!rs.next()) {
		return null;
	    }
	    String rank = rs.getString("rank");

	    return Rank.valueOf(rank);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static ArrayList<String> getAllRankMembers(Rank rank) throws SQLException {

	ArrayList<String> members = new ArrayList();

	PreparedStatement st = Main.getSQLManager().getConnection()
		.prepareStatement("SELECT * FROM " + PLAYERRANKS_DATABASENAME + " WHERE `rank` = ?");
	st.setString(1, "" + rank.toString().toUpperCase());
	ResultSet rs = Main.getSQLManager().executeQuery(st);
	while (rs.next()) {
	    members.add(rs.getString("player"));
	}
	return members;
    }

    public static long getExpirationTimestampFromDatabase(String playername) {

	long timestamp = 0;
	PreparedStatement st;
	try {
	    st = Main.getSQLManager().getConnection()
		    .prepareStatement("SELECT * FROM " + PLAYERRANKS_DATABASENAME + " WHERE `player` = ?");

	    st.setString(1, "" + playername);
	    ResultSet rs = Main.getSQLManager().executeQuery(st);
	    while (rs.next()) {
		timestamp = Long.valueOf(rs.getString("expiration_date"));
	    }

	} catch (SQLException e) {

	    e.printStackTrace();
	}

	return Long.valueOf(timestamp);
    }

    public static class RankInformation {

	String chatrank;
	String rankcolor;
	String chatcolor;

	public RankInformation(String chatrank, String rankcolor, String chatcolor) {
	    this.chatrank = chatrank;
	    this.rankcolor = rankcolor;
	    this.chatcolor = chatcolor;
	}

	public String getChatrank() {
	    if (chatrank == null) {
		return null;
	    } else
		return this.chatrank;
	}

	public String getRankColor() {
	    if (rankcolor == null) {
		return null;
	    } else
		return this.rankcolor;
	}

	public String getChatcolor() {
	    if (chatcolor == null) {
		return null;
	    } else
		return this.chatcolor;
	}

    }

    public boolean putPlayerRankIntoDatabase(String playername, Rank rank, long timestamp) {
	if (!existsPlayer(playername)) {
	    try {
		PreparedStatement st = Main.getSQLManager().getConnection().prepareStatement("INSERT INTO `"
			+ PLAYERRANKS_DATABASENAME + "` (player, rank, expiration_date) VALUES(?,?,?)");
	
		st.setString(1, "" + playername);
		st.setString(2, rank.toString().toUpperCase());
		st.setString(3, "" + timestamp);
		Main.getSQLManager().executeUpdate(st);
		return true;

	    } catch (Exception e) {
		e.printStackTrace();
		return false;
	    }
	} else {
	    removePlayerFromDatabase(playername);
	    putPlayerRankIntoDatabase(playername, rank, timestamp);
	}
	return false;

    }

    public boolean removePlayerFromDatabase(String playername) {
	if (existsPlayer(playername)) {
	    try {
		PreparedStatement st = Main.getSQLManager().getConnection()
			.prepareStatement("DELETE FROM `" + PLAYERRANKS_DATABASENAME + "` WHERE player=?");
		st.setString(1, "" + playername);
		Main.getSQLManager().executeUpdate(st);
		return true;

	    } catch (Exception e) {
		e.printStackTrace();
		return false;
	    }
	}
	return false;

    }

}
