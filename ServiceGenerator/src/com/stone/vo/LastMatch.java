package com.stone.vo;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class LastMatch {
	
	private String homeTeam;
	private String awayTeam;
	private String mark;
	
	private String matchInfo;  // match, group, time
	private String referee;
	private String location;
	
	private String shots1;
	private String shotsOnGoal1;
	private String goalsScored1;
	private String foulsCommitted1;
	private String foulsSuffered1;
	private String cornerKicks1;
	private String freeKicksShots1;
	private String penalityKicks1;
	private String offsides1;
	private String ownGoals1;
	private String yellowCards1;
	private String redCards1;
	private String pocession1;
	
	private String shots2;
	private String shotsOnGoal2;
	private String goalsScored2;
	private String foulsCommitted2;
	private String foulsSuffered2;
	private String cornerKicks2;
	private String freeKicksShots2;
	private String penalityKicks2;
	private String offsides2;
	private String ownGoals2;
	private String yellowCards2;
	private String redCards2;
	private String pocession2;
	
	private String homeScorers ;
	private String awayScorers ;
	private String homeCardsStats ;
	private String awayCardsStats ;
	
	
	public String getPocession1() {
		return pocession1;
	}


	public void setPocession1(String pocession1) {
		this.pocession1 = pocession1;
	}


	public String getPocession2() {
		return pocession2;
	}


	public void setPocession2(String pocession2) {
		this.pocession2 = pocession2;
	}



	
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}


	public String getHomeTeam() {
		return homeTeam;
	}


	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}


	public String getAwayTeam() {
		return awayTeam;
	}


	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}


	public String getMark() {
		return mark;
	}


	public void setMark(String mark) {
		this.mark = mark;
	}


	public String getMatchInfo() {
		return matchInfo;
	}


	public void setMatchInfo(String matchInfo) {
		this.matchInfo = matchInfo;
	}


	public String getReferee() {
		return referee;
	}


	public void setReferee(String referee) {
		this.referee = referee;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}




	public void setHomeScorers(String homeScorers) {
		this.homeScorers = homeScorers;
	}


	public void setAwayScorers(String awayScorers) {
		this.awayScorers = awayScorers;
	}


	public String getShots1() {
		return shots1;
	}


	public void setShots1(String shots1) {
		this.shots1 = shots1;
	}


	public String getShotsOnGoal1() {
		return shotsOnGoal1;
	}


	public void setShotsOnGoal1(String shotsOnGoal1) {
		this.shotsOnGoal1 = shotsOnGoal1;
	}


	public String getGoalsScored1() {
		return goalsScored1;
	}


	public void setGoalsScored1(String goalsScored1) {
		this.goalsScored1 = goalsScored1;
	}


	public String getFoulsCommitted1() {
		return foulsCommitted1;
	}


	public void setFoulsCommitted1(String foulsCommitted1) {
		this.foulsCommitted1 = foulsCommitted1;
	}


	public String getFoulsSuffered1() {
		return foulsSuffered1;
	}


	public void setFoulsSuffered1(String foulsSuffered1) {
		this.foulsSuffered1 = foulsSuffered1;
	}


	public String getCornerKicks1() {
		return cornerKicks1;
	}


	public void setCornerKicks1(String cornerKicks1) {
		this.cornerKicks1 = cornerKicks1;
	}


	public String getFreeKicksShots1() {
		return freeKicksShots1;
	}


	public void setFreeKicksShots1(String freeKicksShots1) {
		this.freeKicksShots1 = freeKicksShots1;
	}


	public String getPenalityKicks1() {
		return penalityKicks1;
	}


	public void setPenalityKicks1(String penalityKicks1) {
		this.penalityKicks1 = penalityKicks1;
	}


	public String getOffsides1() {
		return offsides1;
	}


	public void setOffsides1(String offsides1) {
		this.offsides1 = offsides1;
	}


	public String getOwnGoals1() {
		return ownGoals1;
	}


	public void setOwnGoals1(String ownGoals1) {
		this.ownGoals1 = ownGoals1;
	}


	public String getYellowCards1() {
		return yellowCards1;
	}


	public void setYellowCards1(String yellowCards1) {
		this.yellowCards1 = yellowCards1;
	}


	public String getRedCards1() {
		return redCards1;
	}


	public void setRedCards1(String redCards1) {
		this.redCards1 = redCards1;
	}




	public String getShots2() {
		return shots2;
	}


	public void setShots2(String shots2) {
		this.shots2 = shots2;
	}


	public String getShotsOnGoal2() {
		return shotsOnGoal2;
	}


	public void setShotsOnGoal2(String shotsOnGoal2) {
		this.shotsOnGoal2 = shotsOnGoal2;
	}


	public String getGoalsScored2() {
		return goalsScored2;
	}


	public void setGoalsScored2(String goalsScored2) {
		this.goalsScored2 = goalsScored2;
	}


	public String getFoulsCommitted2() {
		return foulsCommitted2;
	}


	public void setFoulsCommitted2(String foulsCommitted2) {
		this.foulsCommitted2 = foulsCommitted2;
	}


	public String getFoulsSuffered2() {
		return foulsSuffered2;
	}


	public void setFoulsSuffered2(String foulsSuffered2) {
		this.foulsSuffered2 = foulsSuffered2;
	}


	public String getCornerKicks2() {
		return cornerKicks2;
	}


	public void setCornerKicks2(String cornerKicks2) {
		this.cornerKicks2 = cornerKicks2;
	}


	public String getFreeKicksShots2() {
		return freeKicksShots2;
	}


	public void setFreeKicksShots2(String freeKicksShots2) {
		this.freeKicksShots2 = freeKicksShots2;
	}


	public String getPenalityKicks2() {
		return penalityKicks2;
	}


	public void setPenalityKicks2(String penalityKicks2) {
		this.penalityKicks2 = penalityKicks2;
	}


	public String getOffsides2() {
		return offsides2;
	}


	public void setOffsides2(String offsides2) {
		this.offsides2 = offsides2;
	}


	public String getOwnGoals2() {
		return ownGoals2;
	}


	public void setOwnGoals2(String ownGoals2) {
		this.ownGoals2 = ownGoals2;
	}


	public String getYellowCards2() {
		return yellowCards2;
	}


	public void setYellowCards2(String yellowCards2) {
		this.yellowCards2 = yellowCards2;
	}


	public String getRedCards2() {
		return redCards2;
	}


	public void setRedCards2(String redCards2) {
		this.redCards2 = redCards2;
	}


	public String getHomeScorers() {
		return homeScorers;
	}


	public String getAwayScorers() {
		return awayScorers;
	}


	public void setHomeCardsStats(String homeCardsStats) {
		this.homeCardsStats = homeCardsStats;
	}


	public void setAwayCardsStats(String awayCardsStats) {
		this.awayCardsStats = awayCardsStats;
	}


	public String getHomeCardsStats() {
		return homeCardsStats;
	}


	public String getAwayCardsStats() {
		return awayCardsStats;
	}





}
