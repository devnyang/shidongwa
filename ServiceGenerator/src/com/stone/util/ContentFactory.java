package com.stone.util;

import java.util.HashMap;
import java.util.Map;

public class ContentFactory {

	public final static String GROUP_MATCH_RESULT = "group_match_result2";
	public final static String GROUP_MATCH_POINTS = "group_match_points";
	public final static String GOLDEN_BOOT = "golden_boot2";
	public final static String STATISTICS = "statistics";
	public final static String LAST_MATCH = "last_match";
	public final static String ELIMINATION_MATCH_RESULT = "elimination_match_result2";
	
	public final static String MATCH_RESULT_PAGE = "http://www.fifa.com/worldcup/matches/groupstage.html";
	public final static String MATCH_POINT_PAGE = "http://www.fifa.com/worldcup/standings/index.html";
	public final static String GOLDEN_BOOT_PAGE = "http://www.fifa.com/worldcup/awards/goldenboot/ranking.html";
	public final static String STATISTICS_PAGE = "http://www.fifa.com/worldcup/statistics/index.html";
	public final static String ELIMINATION_MATCH_RESULT_PAGE = "http://www.fifa.com/worldcup/matches/index.html";
//	public final static String STATISTICS_PAGE = "statistics.html";
//	public final static String LAST_MATCH_PAGE = "lastMatch.html";
//	public final static String GOLDEN_BOOT_PAGE = "goldenBoot.html";
//	public final static String MATCH_RESULT = "fifa.html";
//	public final static String MATCH_POINT_PAGE = "standings.html";	
//	public final static String ELIMINATION_MATCH_RESULT_PAGE = "match.html";
	
	private HashMap<String,String> urlMap = new HashMap<String,String>();

	private static ContentFactory instance = new ContentFactory();
	
	public static ContentFactory getInstance(){
		return instance;
	}
	
	private ContentFactory(){		
		urlMap.put(GROUP_MATCH_RESULT, MATCH_RESULT_PAGE);
		urlMap.put(GROUP_MATCH_POINTS, MATCH_POINT_PAGE);
		urlMap.put(GOLDEN_BOOT, GOLDEN_BOOT_PAGE);
		urlMap.put(STATISTICS, STATISTICS_PAGE);
		urlMap.put(LAST_MATCH, "");
		urlMap.put(ELIMINATION_MATCH_RESULT, ELIMINATION_MATCH_RESULT_PAGE);
		
	}
	public Map<String, String> getUrlMap(){
		return urlMap;
	}
	
	public CacheContent getContentImp(String key){
	    
		CacheContent contentImp = null;
		if(GROUP_MATCH_RESULT.equalsIgnoreCase(key)){
			contentImp = new MatchResultContentImp();
		}else if(GROUP_MATCH_POINTS.equalsIgnoreCase(key)){
			contentImp = new MatchPointsContentImp();
		}else if(GOLDEN_BOOT.equalsIgnoreCase(key)){
			contentImp = new GoldenBootContentImp();
		}else if(STATISTICS.equalsIgnoreCase(key)){
			contentImp = new StatisticsContentImp();
		}else if(LAST_MATCH.equalsIgnoreCase(key)){
			contentImp = new LastMatchContentImp();
		}else if(ELIMINATION_MATCH_RESULT.equalsIgnoreCase(key)){
			contentImp = new EliminationMatchResultContentImp();
		}
		
		if(contentImp != null){
			contentImp.setWebUrl(urlMap.get(key));
		}
		
		return contentImp;
	}
}
