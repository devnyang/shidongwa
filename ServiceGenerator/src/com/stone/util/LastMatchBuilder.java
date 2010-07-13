package com.stone.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;

import com.stone.vo.LastMatch;

public class LastMatchBuilder {

	private static final Logger log = Logger.getLogger(LastMatchBuilder.class.getName());
	
	/**
	 * <div class="scorer">    
	        <div class="listScorer">        
	 	       <div class="home"><ul> </ul> </div>
	        
	    	    <div class="away">
			        <ul>         
						<li><a href="/worldcup/players/player=305036/index.html">Mesut OEZIL</a> (60')<a href="/worldcup/goals/video/video=1256472/index.html" title="GHA:GER - Mesut OEZIL"><img src="/imgml/icons/goalplay.png" height="18" width="18" alt="GHA:GER - Mesut OEZIL" /></a>
						</li>
					</ul> 
				</div>			
			</div> 
		
		</div>
	 * @param parser
	 * @param lastMatch
	 * @throws ParserException
	 */
	public static void buildScorers(Parser parser, LastMatch lastMatch) throws ParserException{
		log.info("enter into buildScorers");
		parser.reset();  //need this step, otherwise fails filter
		parser.setEncoding("UTF-8");  
		 CssSelectorNodeFilter cssFilter1 = new CssSelectorNodeFilter(".home");
		 NodeList list = parser.extractAllNodesThatMatch(cssFilter1);
		 if(list.elementAt(0) != null){
			 log.info("string=" + ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),""));
			 lastMatch.setHomeScorers(StringUtil.removeBlankString(ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),"")));
		 }
		parser.reset();  //need this step, otherwise fails filter
		cssFilter1 = new CssSelectorNodeFilter(".away");
		list = parser.extractAllNodesThatMatch(cssFilter1);		 
		 if(list.elementAt(0) != null){
			 log.info("string=" + ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),""));	 
			 lastMatch.setAwayScorers(StringUtil.removeBlankString(ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),"")));
		 }
         
         log.info("LastMatch=" + lastMatch.toString());
         log.info("exit from buildScorers");
	}
	
	public static void buildMatchInfo(Parser parser, LastMatch lastMatch) throws ParserException{
		log.info("enter into buildMatchInfo");
		
		 parser.reset();  //need this step, otherwise fails filter
		 parser.setEncoding("UTF-8");  
		 CssSelectorNodeFilter cssFilter1 = new CssSelectorNodeFilter(".info");
		 NodeList list = parser.extractAllNodesThatMatch(cssFilter1);
		 if(list.elementAt(0) != null){
			 log.info("string=" + ParserUtils.trimSpaces(list.elementAt(0).toPlainTextString(),""));
			 lastMatch.setMatchInfo(StringUtil.removeBlankString(ParserUtils.trimSpaces(list.elementAt(0).toPlainTextString(),"")));
		 }
		
         
         log.info("LastMatch=" + lastMatch.toString());
         log.info("exit from buildMatchInfo");
	}
	
	public static void buildReferee(Parser parser, LastMatch lastMatch) throws ParserException{
		log.info("enter into buildReferee");
		
		 parser.reset();  //need this step, otherwise fails filter
		 parser.setEncoding("UTF-8");  
		 CssSelectorNodeFilter cssFilter1 = new CssSelectorNodeFilter(".referee");
		 NodeList list = parser.extractAllNodesThatMatch(cssFilter1);
		 if(list.elementAt(0) != null){
			 log.info("string=" + ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),""));
			 lastMatch.setReferee(StringUtil.removeBlankString(ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),"")));
		 }
		
         
         log.info("LastMatch=" + lastMatch.toString());
         log.info("exit from buildReferee");
	}
	
	public static void buildLocation(Parser parser, LastMatch lastMatch) throws ParserException, UnsupportedEncodingException{
		log.info("enter into buildLocation");
		
		 parser.reset();  //need this step, otherwise fails filter
		 parser.setEncoding("UTF-8");  
		 CssSelectorNodeFilter cssFilter1 = new CssSelectorNodeFilter(".location");
		 NodeList list = parser.extractAllNodesThatMatch(cssFilter1);
		 if(list.elementAt(0) != null){
			 String location = ParserUtils.trimTags(list.elementAt(0).toHtml(),new TagNameFilter("script"));
			 log.info("string=" + location);
			 Parser parser2 = ParserUtils.createParserParsingAnInputString(location);
			 NodeList nl = parser2.extractAllNodesThatMatch(new TagNameFilter("div"));
			 lastMatch.setLocation(StringUtil.removeBlankString(ParserUtils.trimSpacesBeginEnd(nl.elementAt(0).toPlainTextString(),"")));
		 }
		
         
         log.info("LastMatch=" + lastMatch.toString());
         log.info("exit from buildLocation");
	}
	
	
	public static void buildStatistics(Parser parser, LastMatch lastMatch) throws ParserException{
		log.info("enter into buildStatistics");
		
		 parser.reset();  //need this step, otherwise fails filter
		 parser.setEncoding("UTF-8");  
		 CssSelectorNodeFilter cssFilter1 = new CssSelectorNodeFilter(".summaryStatistics");
		 NodeList list = parser.extractAllNodesThatMatch(cssFilter1);
		 TableTag table = null;
		 if(list.elementAt(0) instanceof TableTag){
			 table = (TableTag)list.elementAt(0);
		 }
		 
         TableRow[] rows = table.getRows();   
         
         for (int j = 0; j < rows.length; j++) {   
           TableRow tr = (TableRow) rows[j];   
           
           TableColumn[] tds = tr.getColumns();   
           mapTable2Bean(j, tds, lastMatch);
           
		 }
         
//         lastMatch.setMark(lastMatch.getGoalsScored1()+":" +lastMatch.getGoalsScored2());
         
         
         log.info("LastMatch=" + lastMatch.toString());
         log.info("exit from buildStatistics");
	}
	
	public static void buildCardStat(Parser parser, LastMatch lastMatch) throws ParserException{
		log.info("enter into buildCardStat");
		parser.reset();  //need this step, otherwise fails filter
		parser.setEncoding("UTF-8");  
		 CssSelectorNodeFilter cssFilter1 = new CssSelectorNodeFilter(".homeCardsStats");
		 NodeList list = parser.extractAllNodesThatMatch(cssFilter1);
		 if(list.elementAt(0) != null){
			 log.info("string=" + ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),""));
			 lastMatch.setHomeCardsStats(ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),""));
		 }
		parser.reset();  //need this step, otherwise fails filter
		cssFilter1 = new CssSelectorNodeFilter(".awayCardsStats");
		list = parser.extractAllNodesThatMatch(cssFilter1);		 
		 if(list.elementAt(0) != null){
			 log.info("string=" + ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),""));	 
			 lastMatch.setAwayCardsStats(StringUtil.removeBlankString(ParserUtils.trimSpacesBeginEnd(list.elementAt(0).toPlainTextString(),"")));
		 }
         
         
         log.info("LastMatch=" + lastMatch.toString());
         log.info("exit from buildCardStat");
	}
	
	private static void mapTable2Bean(int j, TableColumn[] tds, LastMatch lastMatch){
		String tds1 = StringUtil.removeBlankString(tds[0].toPlainTextString());
		String tds2 = StringUtil.removeBlankString(tds[2].toPlainTextString());
		
		switch(j){
		case 0:
			lastMatch.setHomeTeam(tds1);
			lastMatch.setAwayTeam(tds2);
			break;
		case 1:
			lastMatch.setShots1(tds1);
			lastMatch.setShots2(tds2);
			break;
		case 2:
			lastMatch.setShotsOnGoal1(tds1);
			lastMatch.setShotsOnGoal2(tds2);
			break;
		case 3:
			lastMatch.setGoalsScored1(tds1);
			lastMatch.setGoalsScored2(tds2);
			break;
		case 4:
			lastMatch.setFoulsCommitted1(tds1);
			lastMatch.setFoulsCommitted2(tds2);
			break;
		case 5:
			lastMatch.setFoulsSuffered1(tds1);
			lastMatch.setFoulsSuffered2(tds2);
			break;
		case 6:
			lastMatch.setCornerKicks1(tds1);
			lastMatch.setCornerKicks2(tds2);
			break;
		case 7:
			lastMatch.setFreeKicksShots1(tds1);
			lastMatch.setFreeKicksShots2(tds2);
			break;
		case 8:
			lastMatch.setPenalityKicks1(tds1);
			lastMatch.setPenalityKicks2(tds2);
			break;
		case 9:
			lastMatch.setOffsides1(tds1);
			lastMatch.setOffsides2(tds2);
			break;
		case 10:
			lastMatch.setOwnGoals1(tds1);
			lastMatch.setOwnGoals2(tds2);
			break;
		case 11:
			lastMatch.setYellowCards1(tds1);
			lastMatch.setYellowCards2(tds2);
			break;
		case 12:
			break;
		case 13:
			lastMatch.setRedCards1(tds1);
			lastMatch.setRedCards2(tds2);
			break;
		case 14:
			break;
		case 15:
			lastMatch.setPocession1(tds1);
			lastMatch.setPocession2(tds2);
			break;
	    default:
	    	
		
		}
	}
}
