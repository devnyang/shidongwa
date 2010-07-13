package com.stone.jsonGenerator;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonGeneratorServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MATCH_POINTS = "matchPoints";
	private static final String GOLDEN_BOOT = "goldenBoot";
	private static final String LAST_MATCH = "lastMatch";
	private static final String GROUP_MATCH = "matchResult";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		String method = req.getParameter("method");
		
		resp.setContentType("text/plain");
		if (MATCH_POINTS.equalsIgnoreCase(method)){
			resp.getWriter().print(JsonBuilder.buildMatchPointsJson());			
		}else if(GOLDEN_BOOT.equalsIgnoreCase(method)){
			resp.getWriter().print(JsonBuilder.buildGoldenBootJson());
		}else if(LAST_MATCH.equalsIgnoreCase(method)){
			resp.getWriter().print(JsonBuilder.buildLastMatchJson());
		}else if(GROUP_MATCH.equalsIgnoreCase(method)){
			resp.getWriter().print(JsonBuilder.buildMatchResultJson());
		}else{// elimination match result
			resp.getWriter().print(JsonBuilder.buildEliminationMatchResultJson(method));
		}

	}
}
