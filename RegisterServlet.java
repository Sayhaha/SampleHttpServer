package cn.czy.httpServer;

public class RegisterServlet extends Servlet{

	@Override
	public void doPost(Request req, Response rep) {
		String name = req.getParameterValue("name");
		String paword = req.getParameterValue("pwd");
		if(name.trim().equals("caozeyuan") && paword.trim().equals("123")) {
			rep.println("�û�" + name + "��½�ɹ�");
		}else {
			rep.println("��¼ʧ��");
		}
		
	}

	@Override
	public void doGet(Request req, Response rep) {
		// TODO Auto-generated method stub
		
	}

	

}
