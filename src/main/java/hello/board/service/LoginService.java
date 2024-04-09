package hello.board.service;

import hello.board.controller.form.LoginForm;
import hello.board.dto.MemberDto;

public interface LoginService {

    MemberDto login(LoginForm form);

}
