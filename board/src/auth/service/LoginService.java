package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class LoginService {

	private MemberDao memberDao = new MemberDao();

	public User login(String id, String password) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			Member member = memberDao.selectById(conn, id);
			if (member == null) {
				throw new LoginFailException();
			}
			if (!member.matchPassword(password)) {
				throw new LoginFailException();
			}
			return new User(member.getId(), member.getName());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
//LoginService는 MemberDao를 이용해서 아이디에 해당하는 회원 데이터가 존재하는지 확인함.
//암호가 일치하면 회원 아이디와 이름을 담은 User객체를 생성하여 리턴함.

//JoinRequest를 이용해 필요한 데이터를 전달 받았던 JoinService의 join()와는 달리
//LoginService의 login()은 필요한 데이터가 id와 password뿐이라
//데이터를 담기위한 별도의 클래스를 만들지 않고 아이디와 암호를 파라미터로 전달받음.