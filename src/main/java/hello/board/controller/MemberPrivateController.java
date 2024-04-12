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

import java.io.IOException;
import java.util.Optional;

import static hello.board.config.SessionConst.LOGIN_MEMBER;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberPrivateController {

    private final MemberRepository memberRepository;

    @GetMapping("member/details")
    public String myDetailsInfo(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        MemberSessionDto loginMember = (MemberSessionDto) session.getAttribute(LOGIN_MEMBER);

        Optional<Member> findMemberOptional = memberRepository.findById(loginMember.getId());
        if (findMemberOptional.isPresent()) {
            model.addAttribute("member", findMemberOptional.get());
            return "members/myDetailsInfo";
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }


    }
}
