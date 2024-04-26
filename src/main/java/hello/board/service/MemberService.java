package hello.board.service;

import hello.board.controller.form.MemberSaveForm;
import hello.board.entity.Member;

import java.io.IOException;
import java.util.Optional;

public interface MemberService {

    boolean existsMember(String userId);
    void joinMember(MemberSaveForm form) throws IOException;


}
