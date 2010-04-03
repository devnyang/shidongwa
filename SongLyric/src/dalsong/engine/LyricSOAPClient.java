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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.farng.mp3.MP3File;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class LyricSOAPClient {
	private final static String alsongUrl = "http://lyrics.alsong.co.kr/alsongwebservice/service1.asmx";
	private HttpURLConnection httpConn;

	private String resultStr = "";
	
	private int tagSize = 0;

	public LyricSOAPClient(String fileName) throws Exception {
		String checkSum;
		connServer(alsongUrl);

//		checkSum = getFileMD5(fileName);
		checkSum = getFileMD5_v2(fileName);
		sendXml(checkSum);
	}

	private void connServer(String SOAPUrl) throws Exception {
		// Create the connection where we're going to send the file.
		URL url = new URL(SOAPUrl);
		URLConnection connection = url.openConnection();

		httpConn = (HttpURLConnection) connection;
	}

	private void sendXml(String checkSum) throws IOException{
		String inStr = "<?xml version='1.0' encoding='UTF-8'?> " + 
		"<SOAP-ENV:Envelope xmlns:SOAP-ENV='http://www.w3.org/2003/05/soap-envelope' xmlns:SOAP-ENC='http://www.w3.org/2003/05/soap-encoding' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:ns2='ALSongWebServer/Service1Soap' xmlns:ns1='ALSongWebServer' xmlns:ns3='ALSongWebServer/Service1Soap12'> " + 
		"<SOAP-ENV:Body> " +
		"<ns1:GetLyric5> " +
		"<ns1:stQuery> " +
		"<ns1:strChecksum>" +
		checkSum +
		"</ns1:strChecksum> " +
		"<ns1:strVersion>1.93</ns1:strVersion> " +
		"<ns1:strMACAddress></ns1:strMACAddress> " +
		"<ns1:strIPAddress></ns1:strIPAddress> " +
		"</ns1:stQuery> " +
		"</ns1:GetLyric5> " +
		"</SOAP-ENV:Body> " +
		"</SOAP-ENV:Envelope>";

		InputStream is = new ByteArrayInputStream( inStr.getBytes() );
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		// Copy the SOAP file to the open connection.
		copy(is,bout);
		is.close();

		byte[] b = bout.toByteArray();

		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty( "Content-Length", String.valueOf( b.length ) );
		httpConn.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
		httpConn.setRequestMethod( "POST" );
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		// Everything's set up; send the XML that was read in to b.
		OutputStream out = httpConn.getOutputStream();
		out.write( b );    
		out.close();

		// Read the response and write it to standard out.
		InputStreamReader isr =
			new InputStreamReader(httpConn.getInputStream(), "UTF-8");
		BufferedReader in = new BufferedReader(isr);

		String inputLine;

		while ((inputLine = in.readLine()) != null){
			resultStr += inputLine;
			//			System.out.println(inputLine);
		}

		in.close();
		bout.close();
		isr.close();
	}

	// copy method from From E.R. Harold's book "Java I/O"
	private static void copy(InputStream in, OutputStream out) throws IOException {

		// do not allow other threads to read from the
		// input or write to the output while copying is
		// taking place

		synchronized (in) {
			synchronized (out) {

				byte[] buffer = new byte[256];
				while (true) {
					int bytesRead = in.read(buffer);
					if (bytesRead == -1) break;
					out.write(buffer, 0, bytesRead);
				}
			}
		}
	} 


	//XML을 파싱해서 필요한 부분을 반환한다.
	public String[] getResultXmlParsing(){
		String xml = resultStr;

		String[] str = new String[18];

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// parse() 메소드에 들어갈 수 있는 인자 중, 아래처럼 URI String 도 있습니다.

			InputStream is = new ByteArrayInputStream( xml.getBytes("UTF-8") );

			Document doc = builder.parse(is);

			NodeList channel = doc.getElementsByTagName("GetLyric5Result");
			// _n 변수에 <GetLyric5Result> ~~~ </GetLyric5Result> 속 노드들 정보가 들어갑니다.
			NodeList _n = channel.item(0).getChildNodes();

			for (int i=0; i<18; i++) {
				str[i] = new String(_n.item(i).getTextContent());
				//				System.out.println("test : [" + i + "] " + _n.item(i).getTextContent());
			} 


			is.close();
		} catch(Exception e) {
			System.out.println(e.toString());
		}

		return str;
	}

	
	private String getFileMD5_v2(String fileName)  throws Exception {
		String s = "";
	
		MP3File file = new MP3File();
		tagSize = (int)file.getMp3StartByte(new File(fileName));
		
//		System.out.println("진짜 태그 : " + tagSize);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileInputStream fin = new FileInputStream(fileName);
		
		// 메시지 축약을 파일로부터 읽어올 객체를 생성
		DigestInputStream in = new DigestInputStream(fin,md);

		fin.getChannel().position(tagSize);
		byte[] b = new byte[163840];
		in.read(b,0,163840);

		md = in.getMessageDigest();
		s = bytesToHex(md.digest());
		
		//정보보호를 위한 주석처리
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

