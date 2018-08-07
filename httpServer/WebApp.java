package cn.czy.httpServer;

import java.util.Map;

public class WebApp {
    private static ServletContext context;
    
    static {
    	context = new ServletContext();
    	
    	Map<String, Servlet> servlet= context.getServlet();
    	servlet.put("login", new LoginServlet());
    	servlet.put("register", new RegisterServlet());
    	
    	
    	
    	Map<String, String> map = context.getMapping();
    	map.put("/login", "login");
    	map.put("/lg", "login");
    	map.put("/reg", "register");
    }
    
    public static Servlet getServlet(String url) {
    	if(context.getMapping().containsKey(url)) {
    		return context.getServlet().get(context.getMapping().get(url));
    	}
    	return null;
    }
}
