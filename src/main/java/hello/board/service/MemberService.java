package hello.board.service;

import hello.board.controller.form.MemberSaveForm;
import hello.board.entity.Member;

import java.util.Optional;

public interface MemberService {


    boolean existsMember(MemberSaveForm form);
    void joinMember(MemberSaveForm form);

    Optional<Member> findUserByUserId(Long userId);
}
