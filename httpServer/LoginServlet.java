package cn.czy.httpServer;

public class LoginServlet extends Servlet{

	@Override
	public void doPost(Request req, Response rep) {
		 
		
	}

	@Override
	public void doGet(Request req, Response rep) {
		rep.print("<html><head><title>http��Ӧ</title></head>"
				+ "<body><h3>hello " + req.getParameterValue("name") + "</h3>"
				+ "</body></html>");
		
	}
}
