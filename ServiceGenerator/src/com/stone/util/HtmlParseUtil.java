package com.stone.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.cache.CacheStatistics;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import com.stone.vo.GoldenBoot;
import com.stone.vo.LastMatch;
import com.stone.vo.MatchResult;
import com.stone.vo.MatchStandings;

public class HtmlParseUtil {

	public final static String MATCH_RESULT_PAGE = "http://www.fifa.com/worldcup/matches/groupstage.html";
	public final static String MATCH_POINT_PAGE = "http://www.fifa.com/worldcup/standings/index.html";
	public final static String GOLDEN_BOOT_PAGE = "http://www.fifa.com/worldcup/awards/goldenboot/ranking.html";
	public final static String STATISTICS_PAGE = "http://www.fifa.com/worldcup/statistics/index.html";
//	public final static String STATISTICS_PAGE = "statistics.html";
//	public final static String LAST_MATCH_PAGE = "lastMatch.html";
//	public final static String GOLDEN_BOOT_PAGE = "goldenBoot.html";
//	public final static String MATCH_RESULT = "fifa.html";
//	public final static String MATCH_POINT_PAGE = "standings.html";	
	public final static String GROUP_MATCH_RESULT = "group_match_result2";
	public final static String GROUP_MATCH_POINTS = "group_match_points";
	public final static String GOLDEN_BOOT = "golden_boot2";
	public final static String STATISTICS = "statistics";
	public final static String LAST_MATCH = "last_match";
	
	private static final Logger log = Logger.getLogger(HtmlParseUtil.class.getName());
	private static HashMap<String, String> fifa_map = new HashMap<String, String>();
	
	
	static{
		fifa_map.put(GROUP_MATCH_RESULT, MATCH_RESULT_PAGE);
		fifa_map.put(GROUP_MATCH_POINTS, MATCH_POINT_PAGE);
		fifa_map.put(GOLDEN_BOOT, GOLDEN_BOOT_PAGE);
		fifa_map.put(STATISTICS, STATISTICS_PAGE);
		fifa_map.put(LAST_MATCH, "");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		fetchMatchResults();
//		fetchMatchPoints();
//		fetchGoldenBoot();
//		fetchLastMatch();
		fetchEliminationMatchResults(4);
	}

	protected static byte[] getHtmlBytesFromWeb(String key) throws UnsupportedEncodingException{
		//get the html content from fifa web page
        log.info("enter getHtmlBytesFromWeb.");

        byte[] content = ContentFactory.getInstance().getContentImp(key).fetchCacheContentFromWeb();

        log.info("exit getHtmlBytesFromWeb");
        
        return content;
	}

	public static String getHtmlFromCache(String key){
		log.info("enter getHtmlFromCache");
		
		String html = "";
		
		//put into memcache
        Cache cache;
        Map<String, Integer> props = new HashMap<String, Integer>(); 
        props.put(GCacheFactory.EXPIRATION_DELTA, 180);  //cache time is 3 mins
        
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(props);

            // Get the value from the cache.
            byte[] htmlBytes = (byte[]) cache.get(key);
            if ( htmlBytes == null || htmlBytes.length == 0){
            	log.info("not hit any cache.");
            	
            	htmlBytes = getHtmlBytesFromWeb(key);    // ...

            	// Put the value into the cache.
            	cache.remove(key);
            	cache.put(key, htmlBytes);     
            	CacheStatistics stats = cache.getCacheStatistics(); 
            	log.info("[CacheLogic] New entry added. Count=" + stats.getObjectCount()); 
            	log.info("[CacheLogic] New entry added. size=" + cache.size()); 
                               
            }else{
            	log.info("hit the cache.");
            }
            html = new String(htmlBytes,"UTF-8");
        
            log.info("key=" + key + " content=" + html);
            
        } catch (CacheException e) {
            // ...
//        	e.printStackTrace();
        	log.severe(e.toString());
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			log.severe(e.toString());
		}

        
        log.info("html from cache:" + html);
		return html;
	}
	
	
	/**
	 * fetch the workd cup match results
	 * @return
	 */
    public static List<MatchResult> fetchMatchResults()
    {
    	log.info("enter fetchMatchResults");
    	List<MatchResult> results = new ArrayList<MatchResult>();
    	
    	try 
        {
            Parser parser = new Parser();
            
//            parser.setURL(MATCH_RESULT_PAGE);
            parser.setInputHTML(getHtmlFromCache(GROUP_MATCH_RESULT));
            parser.setEncoding(parser.getEncoding());
            
            CssSelectorNodeFilter cssFilter1 = new CssSelectorNodeFilter(".odd");
            CssSelectorNodeFilter cssFilter2 = new CssSelectorNodeFilter(".even");
            OrFilter filter = new OrFilter(cssFilter1, cssFilter2);
            NodeList list = parser.extractAllNodesThatMatch(filter);  
            
            String html = "";
            int size = list.size();
            log.info("total size=" + size);            
            for (int i = 0; i < size; i++) {   
            	html = list.elementAt(i).toHtml();
            	//System.out.println("testNodeFilter() " + html);   
            	MatchResult result = convertHtml2Bean(html);
            	
            	int pos = i/6;  // for position which group belongs
            	if(pos==0){
            		result.setGroup("Group A");
            	}else if(pos == 1){
            		result.setGroup("Group B");
            	}else if(pos == 2){
            		result.setGroup("Group C");
            	}else if(pos == 3){
            		result.setGroup("Group D");
            	}else if(pos == 4){
            		result.setGroup("Group E");
            	}else if(pos == 5){
            		result.setGroup("Group F");
            	}else if(pos == 6){
            		result.setGroup("Group G");
            	}else if(pos == 7){
            		result.setGroup("Group H");
            	}
            		
            	results.add(result);
            }   
            
        } catch (ParserException e)
        {
            e.printStackTrace();
        }
        
        log.info("exit fetchMatchResults");
        return results;
    }
/*
    <tr class="odd">
    <td class="c mNum">1</td>
    <td class="l dt"><span class="matchTimeConvertible" title="11/06 16:00,201006111400">11/06 16:00</span></td>
    <td class="l v"><a href="/worldcup/destination/cities/city=1268/index.html">Johannesburg  - JSC</a></td>
    <td class="c flag"><a href="/worldcup/teams/team=43883/index.html"><img class="flagSmall" width="19" height="13" src="http://img.fifa.com/imgml/flags/s/rsa.gif" alt="South Africa" /></a></td>
    <td class="l homeTeam"><a href="/worldcup/teams/team=43883/index.html">South Africa</a></td>
    <td style="width:100px" class="c "><span style="width:100%"><a href="/worldcup/matches/round=249722/match=300061454/index.html">1:1 (0:0)</a><a href="/worldcup/matches/round=249722/match=300061454/index.html" title="Summary"><img src="/imgml/worldcup/matches/matchmore.png" style="margin-left:2px;margin-right:2px;" alt="Summary" /></a>
    </span><a href="/worldcup/matches/round=249722/match=300061454/index.html" title="Highlights"><img src="/imgml/worldcup/matches/matchcamera.png" height="13px" width="15px" alt="Highlights" />
    </a></td>
    <td class="r awayTeam"><a href="/worldcup/teams/team=43911/index.html">Mexico</a></td>
    <td class="c flag"><a class="_test53_" href="/worldcup/teams/team=43911/index.html"><img class="flagSmall" width="19" height="13" src="http://img.fifa.com/imgml/flags/s/mex.gif" alt="Mexico" /></a></td>
    </tr>
    
   */ 
    public static MatchResult convertHtml2Bean(String html) throws ParserException{
//        log.info("enter convertHtml2Bean");
    	
    	MatchResult result = new MatchResult();
        
        Parser parser = new Parser();  
        parser.setInputHTML(html);
        parser.setEncoding(parser.getEncoding());  
        
        TagNameFilter filter = new TagNameFilter("TD");
        NodeList list = parser.extractAllNodesThatMatch(filter); 
        String tdString = "";
        for (int i = 0; i < list.size(); i++) {  
        	tdString = list.elementAt(i).toPlainTextString();

    		tdString = tdString.replaceAll("&#xD;", "");
    		tdString = tdString.replaceAll("\\n", "");
    		
        	switch(i){
        	case 0:
        		result.setMatchNum(tdString);
        		break;
        	case 1:
        		result.setMatchTime(tdString);
        		break;
        	case 2:
        		result.setVenue(tdString);
        		break;
        	case 4:
        		result.setHomeTeam(tdString);
        		break;
        	case 5:

        		
        		int pos = tdString.indexOf("("); //remove this part 
        		if (pos > 0){
        			tdString = tdString.substring(0, pos);
        		}       		
        		result.setMark(tdString);
        		                       
/*        		pos = tdString.indexOf("Background");  // change the output format for the match not begining
        		if(pos != -1){
        			result.setMark(" - ");
        		}
        		
        		pos = tdString.indexOf("Preview");  // change the output format for the match not begining
        		if(pos != -1){
        			result.setMark(" - ");
        		}*/
        		
        		break;
        	case 6:
        		result.setAwayTeam(tdString);
        		break;
        	default:
        		;
        	}
        }
            
//        log.info(result.toString());
//        log.info("exit convertHtml2Bean");
        return result;
    }
    
    
    public static List<MatchStandings> fetchMatchPoints()
    {
    	log.info("enter fetchMatchPoints");
    	List<MatchStandings> results = new ArrayList<MatchStandings>();
    	
    	try 
        {
            Parser parser = new Parser();
            
//            parser.setURL(MATCH_POINT_PAGE);
            parser.setInputHTML(getHtmlFromCache(GROUP_MATCH_POINTS));
//            parser.setEncoding(parser.getEncoding());
            parser.setEncoding("UTF-8");
            
            CssSelectorNodeFilter cssFilter1 = new CssSelectorNodeFilter(".odd");
            CssSelectorNodeFilter cssFilter2 = new CssSelectorNodeFilter(".even");
            OrFilter filter = new OrFilter(cssFilter1, cssFilter2);
            NodeList list = parser.extractAllNodesThatMatch(filter);  
            
            String html = "";
            int size = list.size();
            log.info("total size=" + size);            
            for (int i = 0; i < size; i++) {   
            	html = list.elementAt(i).toHtml();
            	//System.out.println("testNodeFilter() " + html);   
            	MatchStandings result = convertHtml2StandingsBean(html);
            	
            	int pos = i/4;  // for position which group belongs
            	if(pos==0){
            		result.setGroup("Group A");
            	}else if(pos == 1){
            		result.setGroup("Group B");
            	}else if(pos == 2){
            		result.setGroup("Group C");
            	}else if(pos == 3){
            		result.setGroup("Group D");
            	}else if(pos == 4){
            		result.setGroup("Group E");
            	}else if(pos == 5){
            		result.setGroup("Group F");
            	}else if(pos == 6){
            		result.setGroup("Group G");
            	}else if(pos == 7){
            		result.setGroup("Group H");
            	}
            		
            	results.add(result);
            }   
            
        } catch (ParserException e)
        {
            e.printStackTrace();
        }
        
        log.info("exit fetchMatchPoints");
        return results;
    }
    
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
    public static MatchStandings convertHtml2StandingsBean(String html) throws ParserException{
        log.info("enter convertHtml2StandingsBean");
    	
    	MatchStandings result = new MatchStandings();
        
        Parser parser = new Parser();  
        parser.setInputHTML(html);
        parser.setEncoding(parser.getEncoding());  
        
        TagNameFilter filter = new TagNameFilter("TD");
        NodeList list = parser.extractAllNodesThatMatch(filter); 
        String tdString = "";
        for (int i = 0; i < list.size(); i++) {  
        	tdString = list.elementAt(i).toPlainTextString();
        	
        	switch(i){
        	case 0:
        		result.setTeam(tdString);
        		break;
        	case 1:
        		result.setPlayed(tdString);
        		break;
        	case 2:
        		result.setWon(tdString);
        		break;
        	case 3:
        		result.setDraw(tdString);
        		break;
        	case 4:
        		result.setLost(tdString);
        		break;
        	case 5:
        		result.setGoalsFor(tdString);        		
        		break;
        	case 6:
        		result.setGoalsAgainst(tdString);
        		break;
        	case 7:
        		result.setPoints(tdString);
        		break;        		
        	default:
        		;
        	}
        }
            
        log.info(result.toString());
        log.info("exit convertHtml2StandingsBean");
        return result;
    }
    
    
    public static List<GoldenBoot> fetchGoldenBoot()
    {
    	log.info("enter fetchGoldenBoot");
    	List<GoldenBoot> results = new ArrayList<GoldenBoot>();
    	
    	try 
        {
            Parser parser = new Parser();
            
//            parser.setURL(GOLDEN_BOOT_PAGE);
            parser.setInputHTML(getHtmlFromCache(GOLDEN_BOOT));
//            parser.setEncoding(parser.getEncoding());
            parser.setEncoding("UTF-8");

            
            //filter from the table
            CssSelectorNodeFilter cssFilter1 = new CssSelectorNodeFilter(".highlightThree");
            NodeList list = parser.extractAllNodesThatMatch(cssFilter1);
            String html = "";
            if(list.size()>0){
            	html = list.elementAt(0).toHtml();            
            }
            
        	parser.setInputHTML(html);
            parser.setEncoding("UTF-8");  
          
            TagNameFilter filter = new TagNameFilter("TR");    
            list = parser.extractAllNodesThatMatch(filter);
           
            int size = list.size();
            log.info("total size=" + size);            
            for (int i = 1; i < size; i++) { //ignore the first TR   
            	           	
            	html = list.elementAt(i).toHtml();
            	
            	GoldenBoot result = null;
            	if(i<4){  //The first three player is special
            		result = convertFirst3PlayerHtml2GoldenBoot(html);
            	}else{
                	result = convertHtml2GoldenBoot(html);            	            		            		
            	}

            	results.add(result);
            }   
            
        } catch (ParserException e)
        {
            e.printStackTrace();
        }
        
        log.info("exit fetchGoldenBoot");
        return results;
    }    
    
    /*
     * <td></td>
     *  <td>Player</td>
     <td class="c info"><abbr title="Goals for">GF</abbr></td>
     <td class="c info"><abbr title="Assists">ASS</abbr></td>
     <td class="c info"><abbr title="Minutes played">MinP</abbr></td>
     <td class="c info"><abbr title="Penalty goal">PEN</abbr></td>
     <td class="c info"><abbr title="Matches Played">MP</abbr></td>
     */
    
    public static GoldenBoot convertHtml2GoldenBoot(String html) throws ParserException{
        log.info("enter convertHtml2GoldenBoot");
    	
    	GoldenBoot result = new GoldenBoot();
        
        Parser parser = new Parser();  
        parser.setInputHTML(html);
        parser.setEncoding(parser.getEncoding());  
        
        TagNameFilter filter = new TagNameFilter("TD");
        NodeList list = parser.extractAllNodesThatMatch(filter); 
        String tdString = "";
        for (int i = 0; i < list.size(); i++) {  
        	tdString = list.elementAt(i).toPlainTextString();
        	
        	switch(i){
        	case 0:        		
        		break;
        	case 1:
        		result.setPlayer(tdString);
        		break;
        	case 2:
        		result.setGoalsFor(tdString);
        		break;
        	case 3:
        		result.setAssists(tdString);
        		break;
        	case 4:
        		result.setMinutesPlayed(tdString);
        		break;
        	case 5:
        		result.setPenaltyGoal(tdString);        		
        		break;
        	case 6:
        		result.setMatchesPlayed(tdString);
        		break;
        	default:
        		;
        	}
        }
            
        log.info(result.toString());
        log.info("exit convertHtml2GoldenBoot");
        return result;
    }
   
    public static GoldenBoot convertFirst3PlayerHtml2GoldenBoot(String html) throws ParserException{
        log.info("enter convertFirst3PlayerHtml2GoldenBoot");
    	
    	GoldenBoot result = new GoldenBoot();
        
        Parser parser = new Parser();  
        parser.setInputHTML(html);
        parser.setEncoding("UTF-8");  
        
        TagNameFilter filter = new TagNameFilter("TD");
        NodeList list = parser.extractAllNodesThatMatch(filter); 
        String tdString = "";
        for (int i = 0; i < list.size(); i++) {  
        	if(i == 1){  // the second column of the first three players
                parser.setInputHTML(list.elementAt(i).toHtml());                
                filter = new TagNameFilter("A");
                NodeList list2 = parser.extractAllNodesThatMatch(filter); 
                tdString = list2.elementAt(1).toPlainTextString();
        	}else{
            	tdString = list.elementAt(i).toPlainTextString();        		
        	}

        	
        	switch(i){
        	case 0:        		
        		break;
        	case 1:
        		result.setPlayer(tdString);
        		break;
        	case 2:
        		result.setGoalsFor(tdString);
        		break;
        	case 3:
        		result.setAssists(tdString);
        		break;
        	case 4:
        		result.setMinutesPlayed(tdString);
        		break;
        	case 5:
        		result.setPenaltyGoal(tdString);        		
        		break;
        	case 6:
        		result.setMatchesPlayed(tdString);
        		break;
        	default:
        		;
        	}
        }
            
        log.info(result.toString());
        log.info("exit convertFirst3PlayerHtml2GoldenBoot");
        return result;
    }

    public static List<LastMatch> fetchLastMatch(){
    	log.info("enter fetchLastMatch");
    	List<LastMatch> results = new ArrayList<LastMatch>();
    	
    	try 
        {
            Parser parser = new Parser();
            
//            parser.setURL(STATISTICS_PAGE);
            parser.setInputHTML(getHtmlFromCache(STATISTICS));
//            parser.setEncoding(parser.getEncoding());
            parser.setEncoding("UTF-8");

            //filter by the "more" link on this page
            LinkStringFilter filter = new LinkStringFilter("/worldcup/statistics/matches/round");
            NodeList list = parser.extractAllNodesThatMatch(filter);
            String lastMatchPage = "";
           
            if(list.size()>0 ){
            	Node element = list.elementAt(0);
            	if(element instanceof LinkTag){
                	LinkTag link = (LinkTag)list.elementAt(0);
                	lastMatchPage = link.extractLink();
                	log.info("link=" + lastMatchPage);	
            	}
            }
            
            if ("".equalsIgnoreCase(lastMatchPage)){
            	return results;
            }else if(lastMatchPage.indexOf("www.fifa.com") == -1){
            	lastMatchPage = "http://www.fifa.com" + lastMatchPage;
            }
            
            //for testing
//            lastMatchPage = "lastMatch.html";
//            lastMatchPage = "http://www.fifa.com/worldcup/statistics/matches/round=249722/match=300061468/index.html";
//            parser.setURL(lastMatchPage);
//            fifa_map.put(LAST_MATCH, lastMatchPage);  // for cache used
            ContentFactory.getInstance().getUrlMap().put(LAST_MATCH, lastMatchPage);
            
            parser.reset();
//        	parser.setInputHTML(getHtmlFromCache(LAST_MATCH));
            parser.setURL(lastMatchPage);
            parser.setEncoding("UTF-8");  
          
            LastMatch lastMatch = new LastMatch();
            LastMatchBuilder.buildScorers(parser, lastMatch);
            LastMatchBuilder.buildMatchInfo(parser, lastMatch);
            LastMatchBuilder.buildReferee(parser, lastMatch);
            LastMatchBuilder.buildLocation(parser, lastMatch);
            LastMatchBuilder.buildStatistics(parser, lastMatch);
            LastMatchBuilder.buildCardStat(parser, lastMatch);
            
            results.add(lastMatch);

        } catch (ParserException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        log.info("exit fetchLastMatch");
        return results;
    }   
    
    /**
     * flag = 0: Round of 16, 1: Quarter-finals, 2: Semi-finals 3. Match for third place 4. Final
     * @param flag
     * @return
     */
    public static List<MatchResult> fetchEliminationMatchResults(int flag)
    {
    	log.info("enter fetchEliminationMatchResults");
    	List<MatchResult> results = null;
    	
    	try 
        {
            Parser parser = new Parser();
            
//            parser.setURL(ContentFactory.ELIMINATION_MATCH_RESULT_PAGE);
            parser.setInputHTML(getHtmlFromCache(ContentFactory.ELIMINATION_MATCH_RESULT));
            parser.setEncoding("UTF-8");
            
            CssSelectorNodeFilter filter = new CssSelectorNodeFilter(".fixture");
            NodeList list = parser.extractAllNodesThatMatch(filter);  
            
            String html = "";
            int size = list.size();
            log.info("total size=" + size);   
            
	   		TableTag table = null;
			if(list.elementAt(flag) instanceof TableTag){
				table = (TableTag)list.elementAt(flag);
				results = convertHtml2MatchResults(table, flag);
			}
            
        } catch (ParserException e)
        {
            e.printStackTrace();
        }
        
        log.info("exit fetchEliminationMatchResults");
        return results;
    }
    
    public static List<MatchResult> convertHtml2MatchResults(TableTag table, int flag) throws ParserException{
//      log.info("enter convertHtml2Bean");
  	
    	List<MatchResult> results = new ArrayList<MatchResult>();
	      
        TableRow[] rows = table.getRows();   
        
        for (int j = 1; j < rows.length; j++) { //ignore the table head   
          TableRow tr = (TableRow) rows[j];   
          
          TableColumn[] tds = tr.getColumns();
          
          MatchResult result = mapRow2MatchResult(tds);
          
          switch(flag){
          case 0:
        	  result.setGroup("Round of 16");
        	  break;
          case 1:
        	  result.setGroup("Quarter-finals");
        	  break;
          case 2:
        	  result.setGroup("Semi-finals");
        	  break;
          case 3:
        	  result.setGroup("Match for third place");
        	  break;
          case 4:
        	  result.setGroup("Final");
        	  break;
          default:
        	  result.setGroup("");
        	  break;
          }
          
          
          results.add(result);
          
		 }
	    	          
	      log.info(results.toString());
	//      log.info("exit convertHtml2Bean");
	      return results;
    }
    
    public static MatchResult mapRow2MatchResult(TableColumn[] cols){
    	MatchResult result = new MatchResult();
    	
    	result.setMatchNum(StringUtil.removeBlankString(cols[0].toPlainTextString()));
    	result.setHomeTeam(StringUtil.removeBlankString(cols[4].toPlainTextString()));
    	result.setAwayTeam(StringUtil.removeBlankString(cols[6].toPlainTextString()));
    	result.setMark(StringUtil.removeBlankString(cols[5].toPlainTextString()));
    	result.setVenue(StringUtil.removeBlankString(cols[2].toPlainTextString()));
    	result.setMatchTime(StringUtil.removeBlankString(cols[1].toPlainTextString()));
    	result.setGroup("Round of 16");
    	
    	return result;
    }
}


