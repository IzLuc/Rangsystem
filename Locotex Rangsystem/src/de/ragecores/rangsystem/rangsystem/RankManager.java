package de.ragecores.rangsystem.rangsystem;

import java.util.HashMap;

import org.bukkit.entity.Player;

import de.ragecores.rangsystem.rangsystem.Ranks.Rank;


public class RankManager {


    public static HashMap<String, Rank> player_rank = new HashMap<>();
    public static HashMap<String, Long> player_rank_expiration = new HashMap<>();

    public static void updateRank(String playername) {

	if (RankDatabaseManager.getRankFromDatabase(playername) != null) {
	    player_rank.put(playername, RankDatabaseManager.getRankFromDatabase(playername));
	    updateRankExpiration(playername);
	   
	
	} else {
	    player_rank.put(playername, Rank.PLAYER);

	}

    }

    
    public static void updateRankExpiration(String playername){
	 player_rank_expiration.put(playername, RankDatabaseManager.getExpirationTimestampFromDatabase(playername));
    }
    public static boolean hasMinimumRank(Player p, Rank rank) {
	Rank playerrank = getRank(p.getName());

	if (playerrank.getId() >= rank.getId()) {
	    return true;
	} else {
	    return false;
	}

    }

    public static long getExpirationTimestamp(String playername) {


	return player_rank_expiration.get(playername);
    }

    public static void updateExpirationTimestamp(String playername) {

	player_rank_expiration.put(playername, RankDatabaseManager.getExpirationTimestampFromDatabase(playername));

    }

    public static Rank getRank(Player p) {
	if (!player_rank.containsKey(p.getName())) {
	    updateRank(p.getName());
	}

	return player_rank.get(p);

    }
    
    public static Rank getRank(String playername) {
   	if (!player_rank.containsKey(playername)) {
   	    updateRank(playername);
   	}

   	return player_rank.get(playername);

       }

   

}
