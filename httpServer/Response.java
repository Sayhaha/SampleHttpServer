package cn.czy.httpServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
     private StringBuilder headerInfo;   // ��Ӧ����
     private StringBuilder content;    // ��Ӧ�����Ĳ���
     private int code;
	 private final String BLANK = " ";    
	 private final String CRLF = "\r\n";  // �س�����
	 private int len;   // ���ݳ���
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
		// ������ƴ�ӵ�response��
		headerInfo.append(responseFirstLine);
		
		// ��Ӧͷ��
		StringBuilder responseHeader = new StringBuilder();
		responseHeader.append("Server:Apache Tomcat/7.0").append(CRLF);
		responseHeader.append("Date: ").append(new Date()).append(CRLF);
		responseHeader.append("Content-Type: text/html;").append("charset=GBK").append(CRLF);
		// ��Ӧͷ���е����ĳ��ȣ�Ϊ�ֽڳ���
		responseHeader.append("Content-Length: ").append(len).append(CRLF);
		// ��ͷ����Ϣƴ�ӵ�response
		headerInfo.append(responseHeader);
		
		// ������֮���и�����
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
