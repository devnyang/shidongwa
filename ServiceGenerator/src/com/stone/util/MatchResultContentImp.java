package com.stone.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class MatchResultContentImp implements CacheContent {

	private static final Logger log = Logger.getLogger(MatchResultContentImp.class.getName());
	private String matchResultPage = "http://www.fifa.com/worldcup/matches/groupstage.html";
	@Override
	public byte[] fetchCacheContentFromWeb()  {
		// TODO Auto-generated method stub
        log.info("enter into fetchCacheContentFromWeb");
		
		byte[] content = new byte[0];
		
		Parser parser = new Parser();
        try {
			parser.setURL(matchResultPage);
    		parser.setEncoding("UTF-8");			
			CssSelectorNodeFilter filter = new CssSelectorNodeFilter(".tGroupDetail");    
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
		matchResultPage = url;
	}
	
	public static void main(String[] args){
		System.out.println(new MatchResultContentImp().fetchCacheContentFromWeb());
	}
}
