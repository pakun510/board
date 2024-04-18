package hello.board.controller;

import hello.board.config.SessionUtils;
import hello.board.controller.form.BoardEditForm;
import hello.board.controller.form.BoardSaveForm;
import hello.board.dto.MemberSessionDto;
import hello.board.entity.Board;
import hello.board.repository.BoardRepository;
import hello.board.service.FileService;
import hello.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final FileService fileService;

    @GetMapping
    public String boards(Model model,
                         @RequestParam(name = "keyword", required = false) String keyword,
                         @RequestParam(name = "page", defaultValue = "1") int page,
                         @RequestParam(name = "size", defaultValue = "10") int size) {

        //TODO 제목만 검색은 완료, A 또는 B 검색 수정필요
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> boardPage = boardRepository.findAllWithMemberAndContainsKeyword(keyword, pageable);

        model.addAttribute("keyword", keyword);
        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("currentPage", boardPage.getNumber() + 1);
        model.addAttribute("totalItems", boardPage.getTotalElements());
        model.addAttribute("totalPages", boardPage.getTotalPages());
        model.addAttribute("pageSize", size);
        return "boards/boards";
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("board", new BoardSaveForm());

        return "boards/writeForm";
    }

    @PostMapping("/write")
    public String writeBoard(@Validated @ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {
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

        Optional<Board> findBoardOptional = boardRepository.findByIdJoinFetchMemberAndFiles(boardId);
        if (findBoardOptional.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        model.addAttribute("board", findBoardOptional.get());
        return "boards/board";
    }

    @GetMapping("/{boardId}/edit")
    public String editForm(@PathVariable("boardId") Long boardId, Model model) {

        Board findBoard = boardRepository.findById(boardId).get();
        BoardEditForm boardEditForm = new BoardEditForm(findBoard.getTitle(), findBoard.getContent().replace("<br>", "\r\n"));

        model.addAttribute("board", boardEditForm);
        return "boards/editForm";
    }



    @PostMapping("/{boardId}/edit")
    public String editBoard(@PathVariable("boardId") Long boardId, @Validated @ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/editForm";
        }

        boardService.editBoard(boardId, form);

        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/boards/{boardId}";
    }

    @PostMapping("/{boardId}/delete")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {

        boardRepository.deleteById(boardId);

        return "redirect:/boards";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {
        String path = "file:" + fileService.getFullPath(filename);
        log.info("filePath = {}", path);
        return new UrlResource(path);
    }


    //TODO 지도API
}
