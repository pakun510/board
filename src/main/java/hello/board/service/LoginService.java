package hello.board.service;

import hello.board.controller.form.LoginForm;
import hello.board.dto.MemberSessionDto;

public interface LoginService {

    MemberSessionDto tryLogin(LoginForm form);

}
