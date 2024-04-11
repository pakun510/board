package hello.board.service;

import hello.board.controller.form.LoginForm;
import hello.board.dto.MemberSessionDto;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;

    @Override
    public MemberSessionDto tryLogin(LoginForm form) {

        Member findMember = memberRepository.findByUserIdAndPassword(form.getUserId(), form.getPassword());

        return findMember == null ? null : MemberSessionDto.builder()
                .id(findMember.getId())
                .username(findMember.getUsername())
                .build();
    }
}
