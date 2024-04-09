package hello.board.service;

import hello.board.controller.form.MemberSaveForm;

public interface MemberService {


    boolean existsMember(MemberSaveForm form);
    void joinMember(MemberSaveForm form);

}
