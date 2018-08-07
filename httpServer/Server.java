package cn.czy.httpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cn.czy.httpServer.CloseUtil;
import cn.czy.httpServer.Dispatcher;
import cn.czy.httpServer.Server;

public class Server {
	ServerSocket server = null;
    private boolean isShutDown = false;
   
	
	public void start(int port) {
		try {
			server = new ServerSocket(port);
			receive();
		} catch (IOException e1) {
			stop();
		}
			
	}
	
	@SuppressWarnings("unused")
	private void receive() {
			
		while(!isShutDown) {
			
			try {
				Socket client = server.accept();
				//System.out.println("一个新的请求接入");
				new Thread(new Dispatcher(client)).start();
				//System.out.println("新连接");
			} catch (IOException e) {
				stop();
			}
		}	
	}
	
	private void stop() {
		isShutDown = true;
		CloseUtil.closeSocket(server);
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start(8888);
		

	}
}
