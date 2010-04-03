package dalsong.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Lyric {
	private static final String patternStr = "\\[\\d{2}:\\d{2}\\.\\d{2}\\]";
	private LyricSOAPClient cli;
	
	private String[] splitStr;
	private String[] splitTime;
	
	private String strLyric;
	
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
			String[] result;
			cli = new LyricSOAPClient(fileName); 
			
			result = cli.getResultXmlParsing();

			//result[8]에서 가사가 들어있다.
			strLyric = result[8];
			
			
			int index = 0;
			
			splitStr = strLyric.split(patternStr);
			splitTime = new String[splitStr.length-1];
			
			System.out.println(strLyric);
			System.out.println(splitStr[1]);
			
			
			String patStr = "\\d{2}:\\d{2}\\.\\d{2}";
			
			Pattern pattern = Pattern.compile(patStr);
			Matcher matcher = pattern.matcher(strLyric);
			while(matcher.find()){
				String match = matcher.group();
				match = match.replace(":", "");
				match = match.replace(".", "");
				
				splitTime[index] = match;
				index++;
			}
			
			return true;
			
		}catch(Exception ex){
			System.out.println(ex);
//			System.out.println("예외발생!!!!!!!!!!!!!!!!!!!!!!");
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
