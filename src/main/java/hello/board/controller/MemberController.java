package hello.board.controller;

import hello.board.controller.form.MemberSaveForm;
import hello.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

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




}
