package dalsong.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dalsong.util.Sentence;



public class Lyric {
	private static final String patternStr = "\\[\\d{2}:\\d{2}\\.\\d{2}\\]";
//	private LyricSOAPClient cli;
	private LyricClient cli;
	
	private String[] splitStr;
	private String[] splitTime;
	
	private String strLyric;
	
	public static void  main(String[] args){
		
		new Lyric().initLyric("C:\\Users\\shidong\\Music\\Õü¾È.mp3");
	}
	
	public int getTagSize(){
		if(cli != null){
			return cli.getTagSize();
		} else {
			return 0;
		}
	}
	
	public boolean initLyric(String fileName){
		if(fileName == null){ return false; }
		
		try{
//			String[] result;
			String result;
//			cli = new LyricSOAPClient(fileName); 
			cli = new LyricClient(fileName);
			result = cli.getResultXmlParsing();

			//result[8] lyrics part
//			strLyric = result[8];
			strLyric = result;
			

			
			String patStr = "\\d{2}:\\d{2}\\.\\d{2}";
			
			Pattern pattern = Pattern.compile(patStr);
			Matcher matcher = pattern.matcher(strLyric);
			
			//find the lyric start point
			if(matcher.find()){
				String match = matcher.group();
				int start = strLyric.indexOf("[" + match + "]");
				strLyric = strLyric.substring(start);
			}
			
			splitStr = strLyric.split(patternStr);
			splitTime = new String[splitStr.length];
			
			System.out.println(strLyric);
//			System.out.println(splitStr[1]);
			
			//List storing time and lyric mapping
			Collection <Sentence> list = new ArrayList<Sentence>();
			
			int index = 0;						
			// match again from the new start point
			matcher = pattern.matcher(strLyric);
			while(matcher.find()){
				String match = matcher.group();
				match = match.replace(":", "");
				match = match.replace(".", "");
				
				splitTime[index+1] = match;
				index++;
				
				list.add(new Sentence(splitStr[index], match));
			}
			
			// store the same value for sharing lyric contents
			Collections.reverse((ArrayList)list);
			Iterator<Sentence> it = list.iterator();
			Sentence tmpSentence = null;
			String content = "";
			while(it.hasNext()){
				Sentence sentence = it.next();
				content = sentence.getContent();
				if(content != null &&  !"".equalsIgnoreCase(content)){
					tmpSentence = sentence;
				}else{
					sentence.setContent(tmpSentence.getContent());
				}				
			}
			
			//sort the contents
			Collections.sort((ArrayList<Sentence>)list, new Comparator<Sentence>() {

                public int compare(Sentence o1, Sentence o2) {
                    return (int) (Integer.parseInt(o1.getFromTime()) - Integer.parseInt(o2.getFromTime()));
                }
            });
			
			//reconstruct splitStr, splitTime
			it = list.iterator();
			index = 0;
			
			System.out.println("***********************************************************");
			while(it.hasNext()){
				Sentence sentence = it.next();
				splitStr[index] = sentence.getContent();
				splitTime[index] = sentence.getFromTime();
				System.out.println("[" + splitTime[index] + "]" + splitStr[index]);
				index++;

			}
			
			return true;
			
		}catch(Exception ex){
			System.out.println(ex);
			return false;
		}
		
	}
	
	public String[] getSplitLyric(){
		return splitStr;
	}
	
	public String[] getSplitTime(){
		return splitTime;
	}

}
