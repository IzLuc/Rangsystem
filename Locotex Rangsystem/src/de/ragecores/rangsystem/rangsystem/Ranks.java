package de.ragecores.rangsystem.rangsystem;

import java.util.HashMap;

public class Ranks {

    public static HashMap<Rank, String> chatrank = new HashMap<>();
    public static HashMap<Rank, String> rankcolor = new HashMap<>();
    public static HashMap<Rank, String> chatcolor = new HashMap<>();

    public enum Rank {

	PLAYER(0, "Spieler"),
	PREMIUM(1, "Premium"),
	PREMIUM_PLUS(2, "Premium+"),
	YOUTUBER(3, "YouTuber"),
	BUILDER(4,"Builder"),
	HEAD_BUILDER(5,"Head Builder"),
	SUPPORTER(6, "Supporter"), 
	MODERATOR(7,"Moderator"),
	SENIOR_MODERATOR(8,"SrModerator"),
	DEVELOPER(9, "Developer"),
	ADMINISTRATOR(10, "Admin");

	private int rank_id;
	private String rangname;

	private Rank(int rank_id, String rangname) {
	    this.rank_id = rank_id;
	    this.rangname = rangname;
	}

	public int getId() {
	    return rank_id;
	}

	public String getRankName() {
	    return this.rangname;
	}
	
	public String getRankColor() {
	    return rankcolor.get(this).toString();
	}

	public String getChatPrefix() {

	    return chatrank.get(this).toString();
	}

	public String getChatcolor() {
	    return chatcolor.get(this).toString();
	}

    }
}
