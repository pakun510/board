package hello.board.controller;

import hello.board.config.SessionUtils;
import hello.board.controller.form.CommentSaveRequest;
import hello.board.dto.MemberSessionDto;
import hello.board.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    //TODO API이기에 리턴값 DTO로 수정해야함.
    @GetMapping("/{boardId}")
    public String Comment() {
        return null;
    }


    @PostMapping
    public ResponseEntity writeComment(@Validated @RequestBody CommentSaveRequest form, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.info("Binding Error={}", bindingResult);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MemberSessionDto memberSessionDto = SessionUtils.getMemberSessionDto(request);

        //TODO 대댓글 기능 구현해야함.
        commentService.saveComment(memberSessionDto.getId(), form);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
