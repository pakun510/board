package hello.board.controller;

import hello.board.controller.form.MemberSaveForm;
import hello.board.dto.MemberSessionDto;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import hello.board.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

import static hello.board.config.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("member") MemberSaveForm form) {
        return "members/joinForm";
    }

    @PostMapping("/join")
    public String joinMember(@Validated @ModelAttribute("member") MemberSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (form.passwordNotEqualsConfirm()) {
            //TODO 메세지 국제화 수정, 입력한 데이터 다시 리턴 수정
            bindingResult.addError(new FieldError("member", "confirmPassword", "비밀번호가 일치하지 않습니다."));
        }
        if (memberService.existsMember(form)) {
            //TODO 메세지 국제화 수정, 입력한 데이터 다시 리턴 수정
            bindingResult.addError(new FieldError("member", "userId", "이미 존재하는 아이디입니다."));
        }

        if (bindingResult.hasErrors()) {
            return "members/joinForm";
        }

        memberService.joinMember(form);

        return "redirect:/login";
    }

    @GetMapping("/{userId}")
    public String memberInfo(@PathVariable("userId") Long userId, Model model, HttpServletResponse response) throws IOException {
        log.info("userId={}", userId);

        Optional<Member> findMember = memberService.findUserByUserId(userId);
        if (findMember.isPresent()) {
            model.addAttribute("member", findMember.get());
            return "members/memberInfo";
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        MemberSessionDto memberSessionDto = (MemberSessionDto) session.getAttribute(LOGIN_MEMBER);

        memberRepository.deleteById(memberSessionDto.getId());

        return "redirect:/";
    }


}
