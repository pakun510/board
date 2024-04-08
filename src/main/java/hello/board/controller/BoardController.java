package hello.board.controller;

import hello.board.controller.form.BoardSaveForm;
import hello.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardRepository boardRepository;

    @GetMapping
    public String boards(Model model) {

        model.addAttribute("boards", boardRepository.findAll());

        return "boards/list";
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("board", new BoardSaveForm());

        return "boards/writeForm";
    }

    @GetMapping("{boardId}")
    public String board(@PathVariable("boardId") Long boardId, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/boards";
        }


        return "";
    }

    @PostMapping("/write")
    public String writeBoard(@Validated @ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/writeForm";
        }

        log.info("success={}", form);

        return null;

    }


}
