package hello.board.service;

import hello.board.controller.form.MemberSaveForm;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void joinMember(MemberSaveForm form) {
        //TODO 메소드 뭔가 괜찮은거 같긴한데 수정바람.
        memberRepository.save(Member.joinMember(form.getUserId(), form.getPassword(), form.getUsername()));


    }
}
