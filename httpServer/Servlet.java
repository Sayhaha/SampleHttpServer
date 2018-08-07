package cn.czy.httpServer;

public abstract class Servlet {
	public void service(Request req, Response rep) {
		if(req.getMethod().equalsIgnoreCase("post")) {
			doPost(req, rep);
		}
		if(req.getMethod().equalsIgnoreCase("get")) {
			doGet(req, rep);
		}
		
	}
	
	public abstract void doPost(Request req, Response rep);
	
	public abstract void doGet(Request req, Response rep);
	
}