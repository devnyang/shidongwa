package com.stone.rssGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.stone.util.HtmlParseUtil;
import com.stone.vo.MatchResult;
import com.stone.vo.MatchStandings;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.WireFeedOutput;

public class RssBuilder {

	private static final int MAX_TEAM_SIZE = 16;
	private static final String BLANK = " ";
	private static final Logger log = Logger.getLogger(RssBuilder.class.getName());
	
	
	public static String buildMatchResultRss(){
		
		Channel channel = new Channel("rss_2.0");
        channel.setTitle("World Cup 2010");
        channel.setDescription("world cup match result");
        channel.setLink("http://www.fifa.com/worldcup/matches/index.html");
        channel.setEncoding("utf-8");       

        //
        List<Item> items1 = new ArrayList();

        List<MatchResult> results = HtmlParseUtil.fetchMatchResults();
        Iterator<MatchResult> it = results.iterator();
        while(it.hasNext()){
        	MatchResult result = it.next();
        	
            //
            Item item = new Item();
            item.setAuthor("FIFA");
            
            StringBuffer title = new StringBuffer();
            title.append(result.getHomeTeam()).append(" ").append(result.getMark()).append(" ").append(result.getAwayTeam());
            item.setTitle(title.toString());       

            Description description = new Description();
            description.setType("Match Result");
            description.setValue(result.getGroup());
            item.setDescription(description);
            
            items1.add(item);
        }

        
        channel.setItems(items1);

        //
        WireFeedOutput out = new WireFeedOutput();
        String rssString = "";

        try {

        	rssString = out.outputString(channel);
            System.out.println(rssString);

        } catch (IllegalArgumentException e) {

            e.printStackTrace();

        } catch (FeedException e) {

            e.printStackTrace();

        }
		
	    return rssString;		
		
//		return null;
	}
	

public static String buildMatchPointsRss(){
		
		Channel channel = new Channel("rss_2.0");
        channel.setTitle("World Cup 2010");
        channel.setDescription("World Cup Match Points");
        channel.setLink("http://www.fifa.com/worldcup/standings/index.html");
        channel.setEncoding("utf-8");       

        //
        List<Item> items1 = new ArrayList();

        List<MatchStandings> results = HtmlParseUtil.fetchMatchPoints();
        Iterator<MatchStandings> it = results.iterator();
        while(it.hasNext()){
        	MatchStandings result = it.next();
        	
            //
            Item item = new Item();
            item.setAuthor("FIFA");
            
            StringBuffer title = new StringBuffer();
            int length = result.getTeam().length();
            title.append(result.getTeam());
            for (int i=0; i<MAX_TEAM_SIZE-length; i++){
            	title.append(BLANK);
            }
            title.append(result.getPoints());
            item.setTitle(title.toString());       

            Description description = new Description();
            description.setType("Match Points");
            description.setValue(result.getGroup());
            item.setDescription(description);
            
            items1.add(item);
        }

        
        channel.setItems(items1);

        //
        WireFeedOutput out = new WireFeedOutput();
        String rssString = "";

        try {

        	rssString = out.outputString(channel);
            System.out.println(rssString);

        } catch (IllegalArgumentException e) {

            e.printStackTrace();

        } catch (FeedException e) {

            e.printStackTrace();

        }
		
	    return rssString;		
		
//		return null;
	}

	


	public static void main(String[] args){
		//RssBuilder.buildMatchResultRss();
		
//		RssBuilder.buildMatchResultJsonRss();
//		RssBuilder.buildMatchPointsJson();
	}
}
