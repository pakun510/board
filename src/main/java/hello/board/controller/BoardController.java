package hello.board.controller;

import hello.board.controller.form.BoardSaveForm;
import hello.board.dto.BoardDto;
import hello.board.dto.MemberSessionDto;
import hello.board.entity.Board;
import hello.board.repository.BoardRepository;
import hello.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static hello.board.config.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping
    public String boards(Model model, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "20") int size) {

        //TODO initialize proxy - no session 해결해야함
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> boardPage = boardRepository.findAll(pageable);
        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("currentPage", boardPage.getNumber() + 1);
        model.addAttribute("totalItems", boardPage.getTotalElements());
        model.addAttribute("totalPages", boardPage.getTotalPages());
        model.addAttribute("pageSize", size);
        //TODO 날짜 포맷팅해야함.
        return "boards/boards";
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("board", new BoardSaveForm());

        return "boards/writeForm";
    }

    @PostMapping("/write")
    public String writeBoard(@Validated @ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/writeForm";
        }

        HttpSession session = request.getSession(false);
        MemberSessionDto memberSessionDto = (MemberSessionDto) session.getAttribute(LOGIN_MEMBER);

        log.info("success={}", form);
        BoardDto boardDto = boardService.saveBoard(memberSessionDto.getId(), form);
        redirectAttributes.addAttribute("boardId", boardDto.getId());

        return "redirect:/boards/{boardId}";

    }

    @GetMapping("/{boardId}")
    public String board(@PathVariable("boardId") Long boardId, Model model, HttpServletResponse response) throws IOException {

        BoardDto findBoardDto = boardService.findByBoardId(boardId);
        if (findBoardDto != null) {

            model.addAttribute("board", findBoardDto);
            return "boards/board";
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    //TODO 지도API, 이미지,
}
