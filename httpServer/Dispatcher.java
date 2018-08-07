package cn.czy.httpServer;

import java.net.Socket;

public class Dispatcher implements Runnable{
    private Request req;
    private Response rep;
    private Socket client;
    
    public Dispatcher() {}
    
    public Dispatcher(Socket client) {
    	req = new Request(client);
    	rep = new Response(client);
    	
    	this.client = client;
    }
	
	
	@Override
	public void run() {
		int code = 200;
		Servlet servlet = WebApp.getServlet(req.getUri());
		if(null == servlet) {
			code = 404;
		}else {
			servlet.service(req, rep);
		}
		
		rep.push(code);
		CloseUtil.closeSocket(client);
	}
	
}
