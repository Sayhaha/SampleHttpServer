package cn.czy.httpServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
     private StringBuilder headerInfo;   // 响应报文
     private StringBuilder content;    // 响应的正文部分
     private int code;
	 private final String BLANK = " ";    
	 private final String CRLF = "\r\n";  // 回车换行
	 private int len;   // 内容长度
	 private BufferedWriter bw;
	 public Response() {
		 headerInfo = new StringBuilder();
		 content = new StringBuilder();
		 len = 0;
	 }
	 
	 public Response(Socket client) {
		 this();
		 try {
			bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			headerInfo = null;
			code = 500;
			close();
			return;
		}
		 
	 }
	 
	 public Response print(String info) {
		 content.append(info);
		 len += info.getBytes().length;
		 return this;
	 }
	 
	 public Response println(String info) {
		 content.append(info).append(CRLF);
		 len += (info+CRLF).getBytes().length;
		 return this;
	 }
	 
	 private void createHeader(int cd) {
		 code = cd;
		StringBuilder responseFirstLine = new StringBuilder();
		responseFirstLine.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch(code){
		    case 200:
		    	responseFirstLine.append("Accepted");
		    	break;
		    case 400:
		    	responseFirstLine.append("Bad Requst");
		    	break;
		    case 404:
		    	responseFirstLine.append("Not Found");
		    	break;
		    case 500:
		    	responseFirstLine.append("Server Error");
		    	break;
		}
		responseFirstLine.append(CRLF);
		// 将首行拼接到response中
		headerInfo.append(responseFirstLine);
		
		// 响应头部
		StringBuilder responseHeader = new StringBuilder();
		responseHeader.append("Server:Apache Tomcat/7.0").append(CRLF);
		responseHeader.append("Date: ").append(new Date()).append(CRLF);
		responseHeader.append("Content-Type: text/html;").append("charset=GBK").append(CRLF);
		// 响应头部中的正文长度，为字节长度
		responseHeader.append("Content-Length: ").append(len).append(CRLF);
		// 将头部信息拼接到response
		headerInfo.append(responseHeader);
		
		// 与正文之间有个空行
		headerInfo.append(CRLF);
		
		
		
	}
	 
	public void push(int cd) {
		code = cd;
		if(null == headerInfo) {
			code = 500;
			headerInfo = new StringBuilder();
		}
		createHeader(code);
		try {
			bw.append(headerInfo.toString());
			bw.append(content.toString());
			bw.flush();
		} catch (IOException e) {
			close();
			//e.printStackTrace();
		}
		
	}
	
	private void close() {
		CloseUtil.closeIO(bw);
	}
}
