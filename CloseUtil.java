package cn.czy.httpServer;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CloseUtil {
    public static void closeIO(Closeable... io) {
    	for(Closeable temp:io) {
    		if(null != temp) {
    			try {
					temp.close();
				} catch (IOException e) {}
    		}
    	}
    }
    
    public static void closeSocket(Socket socket) {
    	if(null != socket) {
    		try {
				socket.close();
			} catch (IOException e) {}
    	}
    }

	public static void closeSocket(ServerSocket server) {
		if(null != server) {
    		try {
    			server.close();
			} catch (IOException e) {}
    	}
		
	}
}
