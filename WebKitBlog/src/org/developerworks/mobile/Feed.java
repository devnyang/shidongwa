package org.developerworks.mobile;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

@Path("/feed")
public class Feed {
//	String surfinSafari = "http://webkit.org/blog/feed/";
//	String surfinSafari = "http://www.infoq.com/cn/rss/rss.action?token=3Rzk7zXVs3I5msngPdbNU682NZxBVuUN";
	String surfinSafari = "http://www.nba.com/rss/nba_rss.xml";
	
	
		private static final Logger log = Logger.getLogger(Feed.class.getName());
	
	@GET @Produces("application/json")
	public News getNews(@DefaultValue("0") @QueryParam("after") long after) 
	throws Exception{
		log.info("feedUrl=" + surfinSafari);
		
		URL feedUrl = new URL(surfinSafari);
		SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));
        List<Item> entries = new ArrayList<Item>(feed.getEntries().size());
        for (Object obj : feed.getEntries()){
        	SyndEntry entry = (SyndEntry) obj;
        	if (entry.getPublishedDate().getTime() > after){
        		Item item = new Item(entry.getTitle(), entry.getLink(), 
	        				entry.getDescription().getValue(), 
	        				entry.getPublishedDate().getTime());
        		entries.add(item);
        	}
        }
        return new News(feed.getTitle(), entries);
	}
}
