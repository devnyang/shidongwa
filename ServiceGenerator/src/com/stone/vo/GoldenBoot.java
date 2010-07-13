package com.stone.vo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/*
 *  <td>Player</td>
 <td class="c info"><abbr title="Goals for">GF</abbr></td>
 <td class="c info"><abbr title="Assists">ASS</abbr></td>
 <td class="c info"><abbr title="Minutes played">MinP</abbr></td>
 <td class="c info"><abbr title="Penalty goal">PEN</abbr></td>
 <td class="c info"><abbr title="Matches Played">MP</abbr></td>
 */
public class GoldenBoot {

	private String player;
	private String goalsFor;
	private String assists;
	private String minutesPlayed;
	private String penaltyGoal;
	private String matchesPlayed;
	
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getGoalsFor() {
		return goalsFor;
	}
	public void setGoalsFor(String goalsFor) {
		this.goalsFor = goalsFor;
	}
	public String getAssists() {
		return assists;
	}
	public void setAssists(String assists) {
		this.assists = assists;
	}
	public String getMinutesPlayed() {
		return minutesPlayed;
	}
	public void setMinutesPlayed(String minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}
	public String getPenaltyGoal() {
		return penaltyGoal;
	}
	public void setPenaltyGoal(String penaltyGoal) {
		this.penaltyGoal = penaltyGoal;
	}
	public String getMatchesPlayed() {
		return matchesPlayed;
	}
	public void setMatchesPlayed(String matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}

	
}
