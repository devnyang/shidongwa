package com.stone.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class StatisticsContentImp implements CacheContent {

	private static final Logger log = Logger.getLogger(StatisticsContentImp.class.getName());
	private String statisticsPage = "http://www.fifa.com/worldcup/statistics/index.html";
	@Override
	public byte[] fetchCacheContentFromWeb()  {
		// TODO Auto-generated method stub
        log.info("enter into fetchCacheContentFromWeb");
		
		byte[] content = new byte[0];
		
		Parser parser = new Parser();
        try {
			parser.setURL(statisticsPage);
    		parser.setEncoding("UTF-8");			
    		
			HasAttributeFilter filter = new HasAttributeFilter("id", "teamComparisonTeaserMoreLink");    
            NodeList list = parser.extractAllNodesThatMatch(filter);
            
            String html = "";
            if(list.size()>0){
            	html = list.elementAt(0).toHtml();            
            	log.info("content found:" + html);
            	content = html.getBytes("UTF-8");
            }                       
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        	log.severe(e.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        	log.severe(e.toString());
		}
		
        log.info("exist from fetchCacheContentFromWeb");
		return content;
	}

	public void setWebUrl(String url){
		statisticsPage = url;
	}
	
	public static void main(String[] args){
		System.out.println(new StatisticsContentImp().fetchCacheContentFromWeb());
	}

}
