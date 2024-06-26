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
    private final FileValidator fileValidator;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("member") MemberSaveForm form) {
        return "members/joinForm";
    }

    @PostMapping("/join")
    public String joinMember(@Validated @ModelAttribute("member") MemberSaveForm form, BindingResult bindingResult) throws IOException {
        if (form.passwordNotEqualsConfirm()) {
            bindingResult.addError(new FieldError("member", "confirmPassword", form.getConfirmPassword(), false, new String[]{"confirmPassword"}, null, null));
        }
        if (memberService.existsMember(form.getUserId())) {
            bindingResult.addError(new FieldError("member", "userId", form.getUserId(), false, new String[]{"existsUserId"}, null, null));
        }
        if (!form.getProfileImage().isEmpty()) {
            log.info("ContentType = {}", form.getProfileImage().getContentType());
            if (!fileValidator.isSupportedContentType(form.getProfileImage().getContentType())) {
                bindingResult.reject("supportImage");
            }
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

        Optional<Member> findMember = memberRepository.findById(userId);
        if (findMember.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        model.addAttribute("member", findMember.get());
        return "members/memberInfo";


    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        MemberSessionDto memberSessionDto = (MemberSessionDto) session.getAttribute(LOGIN_MEMBER);

        memberRepository.deleteById(memberSessionDto.getId());
        session.invalidate();

        return "redirect:/";
    }


}
