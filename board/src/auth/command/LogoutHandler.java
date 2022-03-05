package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;

public class LogoutHandler implements CommandHandler {
//로그아웃 기능구현
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		res.sendRedirect(req.getContextPath() + "/index.jsp"); //로그아웃 처리 후 index.jsp로 redirect함.
		return null;
	}

}
//LogoutHandler는 session이 존재하면 session을 종료한다.
//session을 종료하면 session에 저장된 "authUser"속성도 함께 삭제되므로 로그아웃 처리된다.