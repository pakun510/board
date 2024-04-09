package hello.board.service;

import hello.board.controller.form.LoginForm;
import hello.board.dto.MemberDto;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDto login(LoginForm form) {

        Member findMember = memberRepository.findByUserIdAndPassword(form.getUserId(), form.getPassword());

        return MemberDto.builder()
                .id(findMember.getId())
                .userId(findMember.getUserId())
                .username(findMember.getUsername())
                .build();

    }
}
