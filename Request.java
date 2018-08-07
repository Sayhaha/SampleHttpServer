package cn.czy.httpServer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
	private final String CRLF = "\r\n";  // 回车换行
    private String method;
    private String uri;
    private String requestInfo;
    private Map<String, List<String>> parameterMap;
    
    public Request() {
    	this.method = "";
    	this.uri = "";
    	this.parameterMap = new HashMap<String,List<String>>();
    }
    
    
	public String getUri() {
		return uri;
	}


	public String getMethod() {
		return method;
	}

	public Request(Socket client) {
    	this();
    	byte[] box = new byte[20480];
		int len = 0;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(
					client.getInputStream()
					);
			//System.out.println("read before");
			len = bis.read(box);
			
			//System.out.println("read after");
			//System.out.println(len);
			requestInfo = new String(box, 0, len).trim();
		} catch (IOException e) {
			CloseUtil.closeIO(bis);
			return;
		}
		parseRequestInfo();
		//System.out.println(requestInfo);
    	
    }
    
    // 分析请求信息头部
    private void parseRequestInfo() {
    	if(null == requestInfo || requestInfo.trim().equals("")) {
    		return;
    	}
    	String headLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));
//    	System.out.println(headLine);
    	int idx = headLine.indexOf("/");
    	this.uri = headLine.substring(idx, headLine.indexOf("HTTP/")).trim();
    	String parameter = "";
    	this.method = headLine.substring(0, idx).trim();
    	if(method.equalsIgnoreCase("get")) {
    		if(this.uri.contains("?")) {
    			String[] urlArray = this.uri.split("\\?");
    			parameter = urlArray[1];
    			this.uri = urlArray[0];
    		}
    		
    	}else if(method.equalsIgnoreCase("post")){
    		String[] arr = requestInfo.split(CRLF);
    		int len = arr.length;
    		parameter = arr[len-1];
    		//System.out.println(parameterMap);
    	}
    	
    	parseParam(parameter);
    }
    
    // 分析请求信息的参数
    private void parseParam(String parameter) {
    	if(parameter.equals("")) {
    		return;
    	}
		String[] parameterKeyValue = parameter.split("&");
    	for(String pkv:parameterKeyValue) {
    		String[] keyValue = pkv.split("=");
    		String key = keyValue[0].trim();
    		if(1 == keyValue.length) {
    			keyValue = Arrays.copyOf(keyValue, 2);
    		}
    		if(!parameterMap.containsKey(key)) {
    			parameterMap.put(key, new ArrayList<String>());
    		}
    		List<String> values = parameterMap.get(key);
    		values.add(decode(keyValue[1],"utf-8"));
    	}
	}
    
    private String decode(String value, String code) {
    	try {
			return java.net.URLDecoder.decode(value,code);
		} catch (UnsupportedEncodingException e) {
			
		}
    	return "";
    }
    
    // 根据键获取对应的多个值
    public String[] getParameterValues(String key) {
		List<String> value = null;
		if((value = parameterMap.get(key)) == null) {
			return null;
		}		
		return value.toArray(new String[0]);
	}

	/**
	 * 根据键获取对应的单个值
	 */
	public String getParameterValue(String key) {
		String[] values = getParameterValues(key);
		if(values == null) {
			return null;
		}		
		return values[0];
	}
     
  
}
