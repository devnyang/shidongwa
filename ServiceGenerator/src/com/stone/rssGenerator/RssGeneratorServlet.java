package com.stone.rssGenerator;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class RssGeneratorServlet extends HttpServlet {
	
	private static final String MATCH_POINTS = "matchPoints";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String method = req.getParameter("method");

		resp.setContentType("text/plain;charset=utf-8");		
		if (MATCH_POINTS.equalsIgnoreCase(method)){
			resp.getWriter().println(RssBuilder.buildMatchPointsRss());
		}else{
			resp.getWriter().println(RssBuilder.buildMatchResultRss());			
		}
	}
}
