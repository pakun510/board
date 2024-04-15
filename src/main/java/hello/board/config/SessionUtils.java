package hello.board.config;

import hello.board.dto.MemberSessionDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static hello.board.config.SessionConst.LOGIN_MEMBER;

public class SessionUtils {

    public static MemberSessionDto getMemberSessionDto(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (MemberSessionDto) session.getAttribute(LOGIN_MEMBER);
    }
}
