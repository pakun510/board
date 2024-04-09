package hello.board.controller;

import hello.board.controller.form.BoardSaveForm;
import hello.board.dto.BoardDto;
import hello.board.entity.Board;
import hello.board.repository.BoardRepository;
import hello.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final BoardService boardService;

    @GetMapping
    public String boards(@PageableDefault(size = 20) Pageable pageable, Model model) {

        model.addAttribute("boards", boardRepository.findAll(pageable));

        return "boards/boards";
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("board", new BoardSaveForm());

        return "boards/writeForm";
    }

    @PostMapping("/write")
    public String writeBoard(@Validated @ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/writeForm";
        }

        log.info("success={}", form);
        BoardDto boardDto = boardService.saveBoard(form);
        redirectAttributes.addAttribute("boardId", boardDto.getId());

        return "redirect:/boards/{boardId}";

    }

    @GetMapping("/{boardId}")
    public String board(@PathVariable("boardId") Long boardId, Model model) {

        BoardDto findBoardDto = boardService.findByBoardId(boardId);
        model.addAttribute("board", findBoardDto);

        return "boards/board";
    }


}
