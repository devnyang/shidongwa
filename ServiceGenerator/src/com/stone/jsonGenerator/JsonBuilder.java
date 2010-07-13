package com.stone.jsonGenerator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.stone.util.HtmlParseUtil;
import com.stone.util.StringUtil;
import com.stone.vo.GoldenBoot;
import com.stone.vo.LastMatch;
import com.stone.vo.MatchResult;
import com.stone.vo.MatchStandings;

public class JsonBuilder {

	private static final int MAX_TEAM_SIZE = 16;
	private static final String BLANK = " ";
	private static final Logger log = Logger.getLogger(JsonBuilder.class.getName());
    
	private static final Map<String,Integer> map = new HashMap<String, Integer>();
    static{
    	map.put("roundOf16", 0);
    	map.put("quarterFinals", 1);
    	map.put("semiFinals", 2);
    	map.put("matchForThe3rdPlace", 3);
    	map.put("final", 4);
    }		
	/**
	 * build json format match result
	 * @return
	 */
	public static String buildMatchResultJsonRss(){
		String jsonRss = "";
		
		StringBuffer buf = new StringBuffer();
		buf.append("{'results':[");
		//jsonRss = "{'results':[{'1','South Africa 1:1 Mexico'},{'2','Uruguay 0:0 France'}]}";
		
		
        List<MatchResult> results = HtmlParseUtil.fetchMatchResults();
        Iterator<MatchResult> it = results.iterator();
        while(it.hasNext()){
        	MatchResult result = it.next();
            
        	buf.append("{'").append(result.getMatchNum()).append("','").append(result.getHomeTeam())
        	.append(" ").append(result.getMark()).append(" ").append(result.getAwayTeam()).append("'},");
        }
        int pos =  buf.lastIndexOf(",");
        if(pos != -1){
        	buf.deleteCharAt(pos);
        }
        buf.append("]}");
        
        jsonRss = buf.toString();
        System.out.println("json=" + jsonRss);
        
		return jsonRss;
	}
	
	public static String buildMatchResultJson(){
	    log.info("enter into buildMatchResultJson");
        
	    StringBuffer buf = new StringBuffer();
	    String jsonResult = "";
	    List<MatchResult> results = HtmlParseUtil.fetchMatchResults();

		buf.append("[");		
        Iterator<MatchResult> it = results.iterator();
        while(it.hasNext()){
        	MatchResult result = it.next();
            
        	buf.append("{\"homeTeam\":\"").append(result.getHomeTeam()).append("\",\"mark\":\"").append(result.getMark())      	
        	   .append("\",\"awayTeam\":\"").append(result.getAwayTeam())
        	   .append("\",\"matchNum\":\"").append(result.getMatchNum()).append("\",\"matchTime\":\"").append(result.getMatchTime())
        	   .append("\",\"venue\":\"").append(result.getVenue()).append("\",\"group\":\"").append(result.getGroup())
        	   .append("\"}")
        	   .append(",");
        }
        int pos =  buf.lastIndexOf(",");
        if(pos != -1){
        	buf.deleteCharAt(pos);
        }
        buf.append("]");
        
/*        JSONArray jsonArray = JSONArray.fromObject( results );   
        log.info("json points=" + jsonArray );  
*/
        
        
        jsonResult = buf.toString();        
        log.info("json points=" + jsonResult );

        log.info("exit from buildMatchResultJson");
        return jsonResult;
	}
	
	
	public static String buildMatchPointsJson(){
		
	    log.info("enter into buildMatchPointsJson");
        
	    StringBuffer buf = new StringBuffer();
	    String jsonStandings = "";
	    List<MatchStandings> results = HtmlParseUtil.fetchMatchPoints();
        /**
         *  json points=[
         *                {"draw":"1","goalsAgainst":"0","goalsFor":"3","group":"Group A","lost":"0","played":"2","points":"4","team":"Uruguay","won":"1"},
         *                {"draw":"1","goalsAgainst":"1","goalsFor":"3","group":"Group A","lost":"0","played":"2","points":"4","team":"Mexico","won":"1"},
         *                {"draw":"1","goalsAgainst":"2","goalsFor":"0","group":"Group A","lost":"1","played":"2","points":"1","team":"France","won":"0"},{"draw":"1","goalsAgainst":"4","goalsFor":"1","group":"Group A","lost":"1","played":"2","points":"1","team":"South Africa","won":"0"},{"draw":"0","goalsAgainst":"1","goalsFor":"5","group":"Group B","lost":"0","played":"2","points":"6","team":"Argentina","won":"2"},{"draw":"0","goalsAgainst":"4","goalsFor":"3","group":"Group B","lost":"1","played":"2","points":"3","team":"Korea Republic","won":"1"},{"draw":"0","goalsAgainst":"3","goalsFor":"2","group":"Group B","lost":"1","played":"2","points":"3","team":"Greece","won":"1"},{"draw":"0","goalsAgainst":"3","goalsFor":"1","group":"Group B","lost":"2","played":"2","points":"0","team":"Nigeria","won":"0"},{"draw":"1","goalsAgainst":"2","goalsFor":"3","group":"Group C","lost":"0","played":"2","points":"4","team":"Slovenia","won":"1"},{"draw":"2","goalsAgainst":"3","goalsFor":"3","group":"Group C","lost":"0","played":"2","points":"2","team":"USA","won":"0"},{"draw":"2","goalsAgainst":"1","goalsFor":"1","group":"Group C","lost":"0","played":"2","points":"2","team":"England","won":"0"},{"draw":"1","goalsAgainst":"1","goalsFor":"0","group":"Group C","lost":"1","played":"2","points":"1","team":"Algeria","won":"0"},{"draw":"1","goalsAgainst":"1","goalsFor":"2","group":"Group D","lost":"0","played":"2","points":"4","team":"Ghana","won":"1"},{"draw":"0","goalsAgainst":"1","goalsFor":"4","group":"Group D","lost":"1","played":"2","points":"3","team":"Germany","won":"1"},{"draw":"0","goalsAgainst":"1","goalsFor":"1","group":"Group D","lost":"1","played":"2","points":"3","team":"Serbia","won":"1"},{"draw":"1","goalsAgainst":"5","goalsFor":"1","group":"Group D","lost":"1","played":"2","points":"1","team":"Australia","won":"0"},{"draw":"0","goalsAgainst":"0","goalsFor":"3","group":"Group E","lost":"0","played":"2","points":"6","team":"Netherlands","won":"2"},{"draw":"0","goalsAgainst":"1","goalsFor":"1","group":"Group E","lost":"1","played":"2","points":"3","team":"Japan","won":"1"},{"draw":"0","goalsAgainst":"3","goalsFor":"2","group":"Group E","lost":"1","played":"2","points":"3","team":"Denmark","won":"1"},{"draw":"0","goalsAgainst":"3","goalsFor":"1","group":"Group E","lost":"2","played":"2","points":"0","team":"Cameroon","won":"0"},{"draw":"1","goalsAgainst":"1","goalsFor":"1","group":"Group F","lost":"0","played":"1","points":"1","team":"Paraguay","won":"0"},{"draw":"1","goalsAgainst":"1","goalsFor":"1","group":"Group F","lost":"0","played":"1","points":"1","team":"Italy","won":"0"},{"draw":"1","goalsAgainst":"1","goalsFor":"1","group":"Group F","lost":"0","played":"1","points":"1","team":"New Zealand","won":"0"},{"draw":"1","goalsAgainst":"1","goalsFor":"1","group":"Group F","lost":"0","played":"1","points":"1","team":"Slovakia","won":"0"},{"draw":"0","goalsAgainst":"1","goalsFor":"2","group":"Group G","lost":"0","played":"1","points":"3","team":"Brazil","won":"1"},{"draw":"1","goalsAgainst":"0","goalsFor":"0","group":"Group G","lost":"0","played":"1","points":"1","team":"Côte d'Ivoire","won":"0"},{"draw":"1","goalsAgainst":"0","goalsFor":"0","group":"Group G","lost":"0","played":"1","points":"1","team":"Portugal","won":"0"},{"draw":"0","goalsAgainst":"2","goalsFor":"1","group":"Group G","lost":"1","played":"1","points":"0","team":"Korea DPR","won":"0"},{"draw":"0","goalsAgainst":"0","goalsFor":"1","group":"Group H","lost":"0","played":"1","points":"3","team":"Chile","won":"1"},{"draw":"0","goalsAgainst":"0","goalsFor":"1","group":"Group H","lost":"0","played":"1","points":"3","team":"Switzerland","won":"1"},{"draw":"0","goalsAgainst":"1","goalsFor":"0","group":"Group H","lost":"1","played":"1","points":"0","team":"Honduras","won":"0"},
         *                {"draw":"0","goalsAgainst":"1","goalsFor":"0","group":"Group H","lost":"1","played":"1","points":"0","team":"Spain","won":"0"}
         *              ]
         */

		buf.append("[");		
        Iterator<MatchStandings> it = results.iterator();
        while(it.hasNext()){
        	MatchStandings result = it.next();
            
        	buf.append("{\"team\":\"").append(result.getTeam()).append("\",\"played\":\"").append(result.getPlayed())      	
        	   .append("\",\"points\":\"").append(result.getPoints())
        	   .append("\",\"won\":\"").append(result.getWon()).append("\",\"draw\":\"").append(result.getDraw())
        	   .append("\",\"lost\":\"").append(result.getLost()).append("\",\"gaolsFor\":\"").append(result.getGoalsFor())
        	   .append("\",\"goalsAgainst\":\"").append(result.getGoalsAgainst()).append("\",\"group\":\"").append(result.getGroup())
        	   .append("\"}")
        	   .append(",");
        }
        int pos =  buf.lastIndexOf(",");
        if(pos != -1){
        	buf.deleteCharAt(pos);
        }
        buf.append("]");
        
/*        JSONArray jsonArray = JSONArray.fromObject( results );   
        log.info("json points=" + jsonArray );  
*/
        
        
        jsonStandings = buf.toString();        
        log.info("json points=" + jsonStandings );
        
        log.info("exit from buildMatchPointsJson");
        return jsonStandings;
	}

	public static String buildGoldenBootJson(){
	
	    log.info("enter into buildGoldenBootJson");
	    
	    StringBuffer buf = new StringBuffer();
	    String jsonGoldenBoot = "";
	    List<GoldenBoot> results = HtmlParseUtil.fetchGoldenBoot();
	    /**
	     *  json points=[
	     *                {"player":"Gonzalo HIGUAIN","goalsFor":"3","assists":"0","minutesPlayed":"161","penaltyGoal":"0","matchesPlayed":"2"}
	     *              ]
	     */
	
		buf.append("[");		
	    Iterator<GoldenBoot> it = results.iterator();
	    while(it.hasNext()){
	    	GoldenBoot result = it.next();
	        
	    	buf.append("{\"player\":\"").append(result.getPlayer()).append("\",\"goalsFor\":\"").append(result.getGoalsFor())      	
	    	   .append("\",\"assists\":\"").append(result.getAssists())
	    	   .append("\",\"minutesPlayed\":\"").append(result.getMinutesPlayed()).append("\",\"penaltyGoal\":\"").append(result.getPenaltyGoal())
	    	   .append("\",\"matchesPlayer\":\"").append(result.getMatchesPlayed())
	    	   .append("\"}")
	    	   .append(",");
	    }
	    int pos =  buf.lastIndexOf(",");
	    if(pos != -1){
	    	buf.deleteCharAt(pos);
	    }
	    buf.append("]");
	    
	/*        JSONArray jsonArray = JSONArray.fromObject( results );   
	    log.info("json points=" + jsonArray );  
	*/
	    
	    
	    jsonGoldenBoot = buf.toString();        
	    log.info("json points=" + jsonGoldenBoot );
	    
	    log.info("exit from buildGoldenBootJson");
	    return jsonGoldenBoot;
	}
	
	public static String buildLastMatchJson(){
		
	    log.info("enter into buildLastMatchJson");
	    
	    StringBuffer buf = new StringBuffer();
	    String jsonLastMatch = "";
	    List<LastMatch> results = HtmlParseUtil.fetchLastMatch();
	    /**

	     */
	
		buf.append("[");		
	    Iterator<LastMatch> it = results.iterator();
	    while(it.hasNext()){
	    	LastMatch result = it.next();
	        
	    	buf.append("{\"homeTeam\":\"").append(result.getHomeTeam()).append("\",\"awayTeam\":\"").append(result.getAwayTeam())      	
	    	   .append("\",\"mark\":\"").append(result.getMark())
	    	   .append("\",\"matchInfo\":\"").append(result.getMatchInfo()).append("\",\"referee\":\"").append(result.getReferee())
	    	   .append("\",\"location\":\"").append(result.getLocation())
	    	   
	    	   .append("\",\"shots1\":\"").append(result.getShots1())
	    	   .append("\",\"shotsOnGoal1\":\"").append(result.getShotsOnGoal1())
	    	   .append("\",\"goalsScored1\":\"").append(result.getGoalsScored1())
	    	   .append("\",\"foulsCommitted1\":\"").append(result.getFoulsCommitted1())
	    	   .append("\",\"foulsSuffered1\":\"").append(result.getFoulsSuffered1())
	    	   .append("\",\"cornerKicks1\":\"").append(result.getCornerKicks1())
	    	   .append("\",\"freeKicksShots1\":\"").append(result.getFreeKicksShots1())
	    	   .append("\",\"penalityKicks1\":\"").append(result.getPenalityKicks1())
	    	   .append("\",\"offsides1\":\"").append(result.getOffsides1())
	    	   .append("\",\"ownGoals1\":\"").append(result.getOwnGoals1())
	    	   .append("\",\"yellowCards1\":\"").append(result.getYellowCards1())
	    	   .append("\",\"redCards1\":\"").append(result.getRedCards1())
	    	   .append("\",\"pocession1\":\"").append(result.getPocession1())
	    	   
	    	   .append("\",\"shots2\":\"").append(result.getShots2())
	    	   .append("\",\"shotsOnGoal2\":\"").append(result.getShotsOnGoal2())
	    	   .append("\",\"goalsScored2\":\"").append(result.getGoalsScored2())
	    	   .append("\",\"foulsCommitted2\":\"").append(result.getFoulsCommitted2())
	    	   .append("\",\"foulsSuffered2\":\"").append(result.getFoulsSuffered2())
	    	   .append("\",\"cornerKicks2\":\"").append(result.getCornerKicks2())
	    	   .append("\",\"freeKicksShots2\":\"").append(result.getFreeKicksShots2())
	    	   .append("\",\"penalityKicks2\":\"").append(result.getPenalityKicks2())
	    	   .append("\",\"offsides2\":\"").append(result.getOffsides2())
	    	   .append("\",\"ownGoals2\":\"").append(result.getOwnGoals2())
	    	   .append("\",\"yellowCards2\":\"").append(result.getYellowCards2())
	    	   .append("\",\"redCards2\":\"").append(result.getRedCards2())
	    	   .append("\",\"pocession2\":\"").append(result.getPocession2())
	    	   
	    	   .append("\",\"homeCardsStats\":\"").append(result.getHomeCardsStats())
	    	   .append("\",\"awayCardsStats\":\"").append(result.getAwayCardsStats())
	    	   
	    	   .append("\",\"homeScorers\":\"").append(result.getHomeScorers())
	    	   .append("\",\"awayScorers\":\"").append(result.getAwayScorers())
	    	   
	    	   .append("\"}")
	    	   .append(",");
	    }
	    int pos =  buf.lastIndexOf(",");
	    if(pos != -1){
	    	buf.deleteCharAt(pos);
	    }
	    buf.append("]");
	    
	/*        JSONArray jsonArray = JSONArray.fromObject( results );   
	    log.info("json points=" + jsonArray );  
	*/
	    
	    
	    jsonLastMatch = buf.toString();    
	    
	    jsonLastMatch = StringUtil.removeBlankString(jsonLastMatch);


	    log.info("last Match=" + jsonLastMatch );
	    
	    
	    log.info("exit from buildLastMatchJson");
	    return jsonLastMatch;
	}


    
	public static String buildEliminationMatchResultJson(String type){
	    log.info("enter into buildEliminationMatchResultJson");
        

	    
	    StringBuffer buf = new StringBuffer();
	    String jsonResult = "";
	    Integer flag = map.get(type);
	    if( flag == null)
	    	return "";
	    
	    List<MatchResult> results = HtmlParseUtil.fetchEliminationMatchResults(map.get(type));

		buf.append("[");		
        Iterator<MatchResult> it = results.iterator();
        while(it.hasNext()){
        	MatchResult result = it.next();
            
        	buf.append("{\"homeTeam\":\"").append(result.getHomeTeam()).append("\",\"mark\":\"").append(result.getMark())      	
        	   .append("\",\"awayTeam\":\"").append(result.getAwayTeam())
        	   .append("\",\"matchNum\":\"").append(result.getMatchNum()).append("\",\"matchTime\":\"").append(result.getMatchTime())
        	   .append("\",\"venue\":\"").append(result.getVenue()).append("\",\"group\":\"").append(result.getGroup())
        	   .append("\"}")
        	   .append(",");
        }
        int pos =  buf.lastIndexOf(",");
        if(pos != -1){
        	buf.deleteCharAt(pos);
        }
        buf.append("]");
        
/*        JSONArray jsonArray = JSONArray.fromObject( results );   
        log.info("json points=" + jsonArray );  
*/
        
        
        jsonResult = buf.toString();        
        log.info("json points=" + jsonResult );

        log.info("exit from buildEliminationMatchResultJson");
        return jsonResult;
	}
	
	public static void main(String[] args){
		//RssBuilder.buildMatchResultRss();
		
//		RssBuilder.buildMatchResultJsonRss();
//		JsonBuilder.buildMatchPointsJson();
		JsonBuilder.buildGoldenBootJson();
//		JsonBuilder.buildMatchResultJson();
//		JsonBuilder.buildLastMatchJson();
	}
}
