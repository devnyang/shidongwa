package dalsong.engine;
/** 

 * SOAPClient4XG. Read the SOAP envelope file passed as the second

 * parameter, pass it to the SOAP endpoint passed as the first parameter, and

 * print out the SOAP envelope passed as a response.  with help from Michael

 * Brennan 03/09/01

 * 

 *

 * @author  Bob DuCharme

 * @version 1.1

 * @param   SOAPUrl      URL of SOAP Endpoint to send request.

 * @param   xmlFile2Send A file with an XML document of the request.  

 *

 * 5/23/01 revision: SOAPAction added

 */



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import org.farng.mp3.MP3File;

import dalsong.util.GAEUtil;
import dalsong.util.SearchResult;




public class LyricClient {
	private final static String alsongUrl = "http://lyrics.alsong.co.kr/alsongwebservice/service1.asmx";
	private HttpURLConnection httpConn;
    private String fileName;
    private String originalName;
	private String resultStr = "";
	
	private int tagSize = 0;

	public LyricClient(String fileName) throws Exception {
        originalName = fileName;
		String name = fileName;
		int pos = name.lastIndexOf("\\");
		name = name.substring(pos+1);
		pos = name.indexOf('.');
		name = name.substring(0, pos);
		
		this.fileName = name;
	}





	//XML
	public String getResultXmlParsing(){

        String content = "";
        try {
            //�ȰѸ�����͸���һ����
            List<SearchResult> list = GAEUtil.getSearchResult("", fileName);
            content = list.get(0).getContent();
        } catch (Exception ex) {

        }

        
        return content;
        
	}
	
	public int getTagSize(){
		
		MP3File file = new MP3File();
		try {
			tagSize = (int)file.getMp3StartByte(new File(originalName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		
		return tagSize;
	}


}

