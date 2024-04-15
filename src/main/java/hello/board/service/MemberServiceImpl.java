package hello.board.service;

import hello.board.controller.form.MemberSaveForm;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public boolean existsMember(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    @Override
    @Transactional
    public void joinMember(MemberSaveForm form) {
        Member savedMember = memberRepository.save(new Member(form.getUserId(), form.getPassword(), form.getUsername()));
    }

    @Override
    public Optional<Member> findUserByUserId(Long userId) {
        //바로 memberRepository 로 접근하는게 더 나은 방법일수도.
        return memberRepository.findById(userId);
    }
}
