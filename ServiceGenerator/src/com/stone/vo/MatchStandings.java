package com.stone.vo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/*	
<thead>
<tr>
<td class="t">Team</td>
<td class="p"><abbr title="Played">MP</abbr></td>
<td class="w"><abbr style="display: inline;" title="Won">W</abbr></td>
<td class="d"><abbr style="display: inline;" title="Draw">D</abbr></td>
<td class="l"><abbr style="display: inline;" title="Lost">L</abbr></td>
<td class="for"><abbr style="display:inline;" title="Goals For">GF</abbr></td>
<td class="ag"><abbr style="display: inline;" title="Goals Against">GA</abbr></td>
<td class="pts"><abbr style="display: inline;" title="Points">Pts</abbr></td>
</tr>
</thead>
*/

public class MatchStandings {
	private String team;
	private String played;
	private String won;
	private String draw;
	private String lost;
	private String goalsFor;
	private String goalsAgainst;
	private String points;
	private String group;

	

	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}


	public String getPlayed() {
		return played;
	}


	public void setPlayed(String played) {
		this.played = played;
	}


	public String getWon() {
		return won;
	}


	public void setWon(String won) {
		this.won = won;
	}


	public String getDraw() {
		return draw;
	}


	public void setDraw(String draw) {
		this.draw = draw;
	}


	public String getLost() {
		return lost;
	}


	public void setLost(String lost) {
		this.lost = lost;
	}


	public String getGoalsFor() {
		return goalsFor;
	}


	public void setGoalsFor(String goalsFor) {
		this.goalsFor = goalsFor;
	}


	public String getGoalsAgainst() {
		return goalsAgainst;
	}


	public void setGoalsAgainst(String goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}


	public String getPoints() {
		return points;
	}


	public void setPoints(String points) {
		this.points = points;
	}


	public String getGroup() {
		return group;
	}


	public void setGroup(String group) {
		this.group = group;
	}
	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}
}
