package cn.czy.httpServer;

public class RegisterServlet extends Servlet{

	@Override
	public void doPost(Request req, Response rep) {
		String name = req.getParameterValue("name");
		String paword = req.getParameterValue("pwd");
		if(name.trim().equals("caozeyuan") && paword.trim().equals("123")) {
			rep.println("用户" + name + "登陆成功");
		}else {
			rep.println("登录失败");
		}
		
	}

	@Override
	public void doGet(Request req, Response rep) {
		// TODO Auto-generated method stub
		
	}

	

}
