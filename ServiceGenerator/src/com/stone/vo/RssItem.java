package com.stone.vo;

import java.util.Date;

public class RssItem {

	private String stage;
	private Date   updateDate;
	private String group;
	private String source;
    private String score;
    private String party1;
    private String party2;
    private String content;
    
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getParty1() {
		return party1;
	}
	public void setParty1(String party1) {
		this.party1 = party1;
	}
	public String getParty2() {
		return party2;
	}
	public void setParty2(String party2) {
		this.party2 = party2;
	}
	
	
	
}
