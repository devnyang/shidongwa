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



import java.io.*;

import java.net.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.farng.mp3.MP3File;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import dalsong.util.GAEUtil;
import dalsong.util.SearchResult;




public class LyricClient {
	private final static String alsongUrl = "http://lyrics.alsong.co.kr/alsongwebservice/service1.asmx";
	private HttpURLConnection httpConn;
    private String fileName;
	private String resultStr = "";
	
	private int tagSize = 0;

	public LyricClient(String fileName) throws Exception {

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

	
	private String getFileMD5_v2(String fileName)  throws Exception {
		String s = "";
	
		MP3File file = new MP3File();
		tagSize = (int)file.getMp3StartByte(new File(fileName));
		
		System.out.println("tag size : " + tagSize);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileInputStream fin = new FileInputStream(fileName);
		
		// 
		DigestInputStream in = new DigestInputStream(fin,md);

		fin.getChannel().position(tagSize);
		byte[] b = new byte[163840];
		in.read(b,0,163840);

		md = in.getMessageDigest();
		s = bytesToHex(md.digest());
		
		//
//		System.out.println("MD5 Checksum : " + s);
//		System.out.println("Tag size : " + tagSize);

		fin.close();
		in.close();
		

		return s;
	}

	private String bytesToHex(byte[] a) {
		StringBuffer s = new StringBuffer();
		for(int i=0;i<a.length;++i) {
			s.append(Character.forDigit((a[i]>>4) & 0x0f, 16));
			s.append(Character.forDigit(a[i] & 0x0f, 16));
		}
		return s.toString();            
	}
	
	public int getTagSize(){
		return tagSize;
	}


}

