package cn.czy.httpServer;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {
	// servlet-->servlet-class
    private Map<String, Servlet> servlet;
    // url-->servlet
    private Map<String, String> mapping;
    
    public ServletContext() {
    	servlet = new HashMap<String, Servlet>();
    	mapping = new HashMap<String, String>();
    }

	public Map<String, Servlet> getServlet() {
		return servlet;
	}

	public void setServlet(Map<String, Servlet> servlet) {
		this.servlet = servlet;
	}

	public Map<String, String> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
    
    
}
