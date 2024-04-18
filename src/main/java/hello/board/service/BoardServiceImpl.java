package hello.board.service;

import hello.board.controller.form.BoardSaveForm;
import hello.board.entity.Board;
import hello.board.entity.BoardFile;
import hello.board.entity.Member;
import hello.board.repository.BoardRepository;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    public String escapeContentText(String content) {
        String escapeText = content.replace("<", "&lt").replace(">", "&gt");
        return escapeText.replace("\r\n", "<br>");
    }

    @Override
    @Transactional
    public Board saveBoard(Long memberId, BoardSaveForm form) throws IOException {

        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Board board = new Board(form.getTitle(), escapeContentText(form.getContent()));
        List<BoardFile> boardFiles = fileService.saveBoardFiles(form.getImageFiles());
        for (BoardFile boardFile : boardFiles) {
            board.addFile(boardFile);
        }
        member.writeBoard(board);

        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public void editBoard(Long boardId, BoardSaveForm form) {

        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        board.edit(form.getTitle(), escapeContentText(form.getContent()));

    }


}
