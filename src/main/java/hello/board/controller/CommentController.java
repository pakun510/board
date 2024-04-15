package hello.board.controller;

import hello.board.config.SessionUtils;
import hello.board.controller.form.CommentSaveForm;
import hello.board.dto.MemberSessionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
public class CommentController {



    @PostMapping("/write/{boardId}")
    public String writeComment(@Validated @ModelAttribute("comment") CommentSaveForm form, BindingResult bindingResult,
                               @PathVariable("boardId") Long boardId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //TODO 어떻게 BindingResult Error를 보내야할지 모르겠음.
        if (bindingResult.hasErrors()) {
            log.info("Binding Error={}", bindingResult);
            redirectAttributes.addAttribute("boardId", boardId);

            return "redirect:/boards/{boardId}";
        }

        MemberSessionDto memberSessionDto = SessionUtils.getMemberSessionDto(request);
        Long memberId = memberSessionDto.getId();



        return null;
    }
}
