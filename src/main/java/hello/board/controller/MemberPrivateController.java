package hello.board.controller;

import hello.board.dto.MemberSessionDto;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static hello.board.config.SessionConst.LOGIN_MEMBER;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberPrivateController {

    private final MemberRepository memberRepository;

    @GetMapping("member/details")
    public String myDetailsInfo(Model model, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        MemberSessionDto loginMember = (MemberSessionDto) session.getAttribute(LOGIN_MEMBER);

        Member findMember = memberRepository.findById(loginMember.getId()).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("member", findMember);

        return "members/myDetailsInfo";

    }
}
