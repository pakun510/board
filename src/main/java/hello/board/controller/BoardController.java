package hello.board.controller;

import hello.board.config.SessionUtils;
import hello.board.controller.form.BoardSaveForm;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

import static hello.board.config.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping
    public String boards(Model model,
                         @RequestParam(name = "keyword", required = false) String keyword,
                         @RequestParam(name = "page", defaultValue = "1") int page,
                         @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("keyword={}", keyword);
        log.info("page={}", page);
        log.info("size={}", size);

        //TODO 제목만 검색은 완료, A 또는 B 검색 수정필요
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> boardPage = boardRepository.findAllWithMemberAndContainsKeyword(keyword, pageable);

        model.addAttribute("keyword", keyword);
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
    public String writeBoard(@Validated @ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/writeForm";
        }

        MemberSessionDto memberSessionDto = SessionUtils.getMemberSessionDto(request);

        log.info("success={}", form);
        Board savedBoard = boardService.saveBoard(memberSessionDto.getId(), form);
        redirectAttributes.addAttribute("boardId", savedBoard.getId());

        return "redirect:/boards/{boardId}";

    }

    @GetMapping("/{boardId}")
    public String board(@PathVariable("boardId") Long boardId, Model model, HttpServletResponse response) throws IOException {

        Optional<Board> findBoardOptional = boardRepository.findByIdJoinFetchMember(boardId);
        if (findBoardOptional.isPresent()) {
            model.addAttribute("board", findBoardOptional.get());
            return "boards/board";
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @GetMapping("/{boardId}/edit")
    public String editForm(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Board findBoard = boardRepository.findByIdJoinFetchMember(boardId).get();

        model.addAttribute("board", findBoard);
        return "boards/editForm";
    }



    @PostMapping("/{boardId}/edit")
    public String editBoard(@PathVariable("boardId") Long boardId, @Validated @ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/editForm";
        }

        boardService.editBoard(boardId, form);

        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/boards/{boardId}";
    }

    @PostMapping("/{boardId}/delete")
    public String deleteBoard(@PathVariable("boardId") Long boardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        boardRepository.deleteById(boardId);

        return "redirect:/boards";
    }





    //TODO 지도API, 이미지,
}
