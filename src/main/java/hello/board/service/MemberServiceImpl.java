package hello.board.service;

import hello.board.controller.form.MemberSaveForm;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public boolean existsMember(MemberSaveForm form) {
        return memberRepository.existsByUserId(form.getUserId());
    }

    @Override
    public void joinMember(MemberSaveForm form) {

        Member savedMember = memberRepository.save(new Member(form.getUserId(), form.getPassword(), form.getUsername()));

        //TODO 가입한 회원 리턴시켜서 보여주기 근데 굳이 그럴필요가 있는지 고려.

    }

    @Override
    public Optional<Member> findUserByUserId(Long userId) {
        return memberRepository.findById(userId);
    }
}
