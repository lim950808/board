package auth.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.LoginFailException;
import auth.service.LoginService;
import auth.service.User;
import mvc.command.CommandHandler;

public class LoginHandler implements CommandHandler {
//Get방식 요청이 오면 폼을 위한 뷰를 리턴하고, Post방식 요청이 오면 LoginService를 이용하여 로그인 처리함.
	private static final String FORM_VIEW = "/WEB-INF/view/loginForm.jsp";
	private LoginService loginService = new LoginService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		String id = trim(req.getParameter("id")); //폼에서 전송한 id와 password의 파라미터 값을 구한다.
		String password = trim(req.getParameter("password"));

		Map<String, Boolean> errors = new HashMap<>(); //에러를 담는 Map객체를 생성하고 errors이라는 속성에 저장함.
		req.setAttribute("errors", errors);

		if (id == null || id.isEmpty()) //id나 password 값이 없을 경우 에러.
			errors.put("id", Boolean.TRUE);
		if (password == null || password.isEmpty())
			errors.put("password", Boolean.TRUE);

		if (!errors.isEmpty()) { //에러가 존재하면 폼 뷰를 리턴함.
			return FORM_VIEW;
		}

		try {
			User user = loginService.login(id, password); //loginService.login()을 이용하여 인증을 수행함. 로그인 성공시 User객체를 리턴함.
			req.getSession().setAttribute("authUser", user); //User객체를 session의 authUser 속성에 저장함.
			res.sendRedirect(req.getContextPath() + "/index.jsp"); //올바르게 입력했다면 index.jsp로 redirect함.
			return null;
		} catch (LoginFailException e) { //로그인이 실패해서 LoginFailException이 발생하면 해당 에러를 추가하고, 폼을 위한 뷰를 리턴함.
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			return FORM_VIEW;
		}
	}

	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}
